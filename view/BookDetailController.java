package book.shelves.view;

import book.shelves.BookShelves;
import book.shelves.utils.Global;
import book.shelves.utils.ViewUtils;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class BookDetailController {
    @FXML
    ImageView bookIcon;
    @FXML
    Label titleLabel;
    @FXML
    Label authorLabel;
    @FXML
    Label publisherLabel;
    @FXML
    Label dateLabel;
    @FXML
    Label priceLabel;
    @FXML
    TextArea descriptionLabel;
    @FXML
    Button addCommentBtn;
    @FXML
    ComboBox ratingCombo;
    @FXML
    TextArea ratingArea;
    @FXML
    VBox commentsArea;
    @FXML
    Button buyBtn;
    
    @FXML
    public void initialize() throws SQLException, IOException{
        String sql = "select * from book where book_id = ?";
        PreparedStatement pstm = BookShelves.conn.prepareStatement(sql);
        pstm.setInt(1, Global.viewedBook.getBookId());
        ResultSet rs = pstm.executeQuery();
        if(rs.next()){
            titleLabel.setText(rs.getString("book_title"));
            authorLabel.setText(rs.getString("book_author"));
            publisherLabel.setText(rs.getString("book_publisher"));
            dateLabel.setText(rs.getString("book_released_date"));
            priceLabel.setText("$"+String.valueOf(rs.getInt("book_price")));
            descriptionLabel.setText(rs.getString("book_description"));
        }
        
        //set the rating combo items
        ratingCombo.getItems().addAll("1", "2", "3", "4", "5");
        
        bookIcon.setImage(new Image("file:src/book/shelves/imgsrc/book.png"));
        
        loadComments();
        
        if(checkBookAvailibilty()) buyBtn.setDisable(true);
    } 
    
    public void backBtnEvent(){
        if(Global.lastOpened.matches("home"))
            ViewUtils.loadView("view/HomeOverview.fxml");
        else if(Global.lastOpened.matches("search"))
            ViewUtils.loadView("view/SearchView.fxml");
    }
    
    public void buyBtnEvent(){
        ViewUtils.loadView("view/PaymentForm.fxml");
    }
    
    public void submitComment() throws SQLException, IOException{
        if(!ratingArea.getText().isEmpty() && ratingCombo.getValue()!=null ){
            System.out.println("Test Succesfull");
            String sql = "insert into rating "
                + "(rating_value, book_id, comment, rating_date, user_id)"
                + " values(?, ?, ?, curdate(), ?)";
            PreparedStatement pstm = BookShelves.conn.prepareStatement(sql);
            pstm.setInt(1, Integer.valueOf(ratingCombo.getValue().toString()));
            pstm.setInt(2, Global.viewedBook.getBookId());
            pstm.setString(3, ratingArea.getText());
            pstm.setInt(4, Global.signedUser.getId());
            pstm.executeUpdate();
            loadComments();
        }else{
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText("Can\'t send your comments");
            alert.setContentText("You must fill the comment area before submit it");
            alert.showAndWait();
        }
    }
    
    public void loadComments() throws SQLException, IOException{
        commentsArea.getChildren().clear();
        String sql = "select user_name, rating_value, rating_date, comment "
                + "from rating join user where user.user_id = rating.user_id "
                + "and book_id = ?";
        PreparedStatement pstm = BookShelves.conn.prepareStatement(sql);
        pstm.setInt(1, Global.viewedBook.getBookId());
        ResultSet rs = pstm.executeQuery();
        while(rs.next()){
            commentsArea.getChildren().add(ViewUtils.showRatingCard(
                rs.getString("user_name"), 
                "Rating: "+rs.getString("rating_value") +" "+rs.getString("rating_date"),
                rs.getString("comment")
            ));
        }
    }
    
    public boolean checkBookAvailibilty() throws SQLException{
        String sql = "select book_id from purchase_tsc "
            + "where book_id = ?";
        PreparedStatement pstm = BookShelves.conn.prepareStatement(sql);
        pstm.setInt(1, Global.viewedBook.getBookId());
        ResultSet rs = pstm.executeQuery();
        if(rs.next()) return true;
        else return false;
    }
        
}
