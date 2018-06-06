/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package book.shelves.view;

import book.shelves.utils.Global;
import book.shelves.utils.ViewUtils;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author chilly98
 */
public class BookCardController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML 
    private Label bookTitle;
    @FXML
    private Label bookId;
    @FXML
    private ImageView bookImage;
    @FXML
    private Button detailBtn;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        bookImage.setImage(new Image("http://localhost/BookShelves/img/Category/AI/AI-1.jpg"));
    }  
    
    public void setTitle(String title, String id){
        bookTitle.setText(title);
        bookId.setText(id);
    }
    
    public void setImage(String src){
        if(src == ""){
           
        }
        else{
            System.out.println(src);
            Image img = new Image(src);
            bookImage.setImage(img);
        } 
    }
    
    public void detailBtnEvent(){
        Global.viewedBook.setBookId(Integer.parseInt(bookId.getText()));
        ViewUtils.loadView("view/BookDetailOverview.fxml");
    }
    
    public void setDetailVisible(boolean vis){
       detailBtn.setVisible(vis);
    }
    
}
