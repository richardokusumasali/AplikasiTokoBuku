package book.shelves.view;

import java.io.IOException;
import java.sql.SQLException;

import book.shelves.BookShelves;
import book.shelves.model.Book;
import book.shelves.utils.DBUtils;
import book.shelves.utils.Global;
import book.shelves.utils.ViewUtils;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class SearchController {
    @FXML
    JFXTextField keywordsField;
    @FXML
    ComboBox<String> categoryComboBox;
    @FXML
    GridPane resultGrid;
    
    private final int MAX_COL = 4;
    private int rowCount = 0, colCount = 0;
	
    @FXML
    public void initialize(){
        //set the last opened to this window
        Global.lastOpened = "search";
    }
	
    public void searchBtnEvent() throws SQLException, IOException{
        resultGrid.getChildren().clear();
        rowCount = 0;
        colCount = 0;
        ObservableList<Book> books = DBUtils.searchBook(
            BookShelves.conn, keywordsField.getText()
        );
        for(Book book : books){
            AnchorPane pane = ViewUtils.showBookCard(
                book.getBookTitle(), String.valueOf(book.getBookId()), 
                book.getBookIconSrc(), true
            );

            if(colCount == MAX_COL){
                colCount = 0;
                rowCount++;               
            }
            colCount++;
            System.out.println("Column: "  +colCount);
            System.out.println("Row: " + rowCount);
            resultGrid.add(pane, colCount, rowCount);

        }
        
        if(books.size() == 0){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText("0 Result Found");
            alert.setContentText("Try use different keywords");
            alert.showAndWait();
        }
    }
}
