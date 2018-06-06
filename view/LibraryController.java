/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package book.shelves.view;

import book.shelves.BookShelves;
import book.shelves.model.Book;
import book.shelves.utils.Global;
import book.shelves.utils.ViewUtils;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 *
 * @author chilly98
 */
public class LibraryController {
    @FXML
    Label userLabel;
    @FXML
    GridPane bookGrid;
    
    private final int MAX_COL = 4;
    private int rowCount = 0, colCount = 0;
    
    @FXML
    public void initialize() throws IOException, SQLException{
        userLabel.setText(Global.signedUser.getName());
        updateLibrary();
    }
    
    public void updateLibrary() throws SQLException, IOException{
        bookGrid.getChildren().clear();
        rowCount = 0;
        colCount = 0;
        ArrayList<Book> books = new ArrayList<>();
        
        String sql = "select book_title, book_icon_src from purchase_tsc, book, user,"
                + " bank_account where purchase_tsc.book_id = book.book_id and "
                + "bank_account.bank_account_id = purchase_tsc.bank_account_id "
                + "and bank_account.user_id = user.user_id and user.user_id = ? "
                + "and status = 1";
        PreparedStatement pstm = BookShelves.conn.prepareStatement(sql);
        pstm.setInt(1, Global.signedUser.getId());
        ResultSet rs = pstm.executeQuery();
        while(rs.next()){
            Book book = new Book();
            book.setBookTitle(rs.getString("book_title"));
            book.setBookIconSrc(rs.getString("book_icon_src"));
            books.add(book);
        }      
        
	for(Book book : books){
            AnchorPane pane = ViewUtils.showBookCard(
                book.getBookTitle(), "", 
                book.getBookIconSrc(), false
            );
            
            if(colCount == MAX_COL){
                colCount = 0;
                rowCount++;               
            }
            colCount++;
            bookGrid.add(pane, colCount, rowCount);          
	}
    }
    
    public void updateBtnEvent(){
        ViewUtils.loadView("view/UpdateAccountView.fxml");
    }
}
