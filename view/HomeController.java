package book.shelves.view;

import java.io.IOException;
import java.sql.SQLException;

import book.shelves.BookShelves;
import book.shelves.model.Book;
import book.shelves.utils.DBUtils;
import book.shelves.utils.Global;
import book.shelves.utils.ViewUtils;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class HomeController {
    @FXML 
    GridPane booksContent;
    @FXML
    ComboBox catCombo;
    
    private final int MAX_COL = 4;
    private int rowCount = 0, colCount = 0;
	
    @FXML 
    public void initialize() throws IOException, SQLException{
        catCombo.getItems().addAll("manual", "biography", "novel", 
            "enyclopedia", "dictionary", "other");
	 
        catCombo.setOnAction((event)->{
            String cat = String.valueOf(
                catCombo.getSelectionModel().getSelectedItem()   
            );
            try {
                updateBook(cat);
            } catch (IOException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SQLException ex) {
                Logger.getLogger(HomeController.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Success");
        });
        
        updateBook("");
        
        //set the last opened to this window
        Global.lastOpened = "home";
    }
    
    public void updateBook(String cat) throws IOException, SQLException{
        //manual books
        booksContent.getChildren().clear();
        rowCount = 0;
        colCount = 0;
        ObservableList<Book> books= null;
        
        if(cat == ""){
            books = DBUtils.selectBook(BookShelves.conn);
        }else{
            books = DBUtils.selectBookCategorized(BookShelves.conn, cat);
        }
            
        for(Book book : books){
            AnchorPane pane = ViewUtils.showBookCard(
                book.getBookTitle(), String.valueOf(book.getBookId()), 
                book.getBookIconSrc(),true
            );

            if(colCount == MAX_COL){
                colCount = 0;
                rowCount++;               
            }
            colCount++;
            booksContent.add(pane, colCount, rowCount);

        }
        
    }
    
    public void showAllEvent() throws IOException, SQLException{
        catCombo.setValue("");
        updateBook("");
    }
}
