/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package book.shelves.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author chilly98
 */
public class RatingViewController{
    /**
     * Initializes the controller class.
     */
    @FXML
    Label userNameLabel;
    @FXML
    Label ratingLabel;
    @FXML
    Label commentLabel;
    
    @FXML
    public void initialize(){
        
    }
    
    public void setRatingView(String name, String rating, String comment){
        userNameLabel.setText(name);
        ratingLabel.setText(rating);
        commentLabel.setText(comment);
    }
}
