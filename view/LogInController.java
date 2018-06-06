package book.shelves.view;

import java.sql.SQLException;

import book.shelves.BookShelves;
import book.shelves.utils.DBUtils;
import book.shelves.utils.ViewUtils;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Control;
import javafx.scene.layout.AnchorPane;

public class LogInController {
    @FXML
    JFXTextField nameField;
    @FXML
    JFXPasswordField passwordField;
    @FXML
    AnchorPane pane;
    private int order;
    
    @FXML
    public void initialize(){
        //set for focus order on enter
        Control[] focusOrder = new Control[] {
            nameField, passwordField
        };
        
        pane.setOnKeyPressed(e -> {
            switch(e.getCode()){
                case ENTER:
                    if(order < focusOrder.length-1){
                        order++;
                        focusOrder[order].requestFocus();
                    }else{ 
                        try { logInBtnEvent();}
                        catch (SQLException ex) {}
                   }
                   break;
            }
        });
    }
	
    public void signInBtnEvent(){
	ViewUtils.loadView("view/SignInOverview.fxml");
    }
	
    public void logInBtnEvent() throws SQLException{
        if(BookShelves.mainCtrl.getLogInMenuItem().getText().equals("Log In")){
            if(!nameField.getText().isEmpty() && !passwordField.getText().isEmpty()){
                boolean res = DBUtils.findUser(BookShelves.conn, nameField.getText(), 
                    passwordField.getText());
                if(res){
                    BookShelves.mainCtrl.getLogInMenuItem().setText("Log Out");
                }
                else{
                    Alert alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Submitting Data");
                    alert.setHeaderText("User name or password error");
                    alert.setContentText("Check out you user name and password");
                    alert.showAndWait();
                }
            }else{
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Submitting Data");
                alert.setHeaderText("Input Field can\'t be empty");
                alert.setContentText("You must fill out all required data");
                alert.showAndWait();
            }
        }
    }
	
    public void forgotBtnEvent(){
	ViewUtils.loadView("view/ForgotView.fxml");
    }
}
