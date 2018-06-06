package book.shelves.view;

import java.sql.SQLException;

import book.shelves.BookShelves;
import book.shelves.utils.DBUtils;
import book.shelves.utils.ViewUtils;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Control;
import javafx.scene.layout.AnchorPane;

public class ForgotController {
    @FXML
    JFXTextField nameField;
    @FXML
    JFXTextField phoneNumberField;
    @FXML
    JFXPasswordField passwordField;
    @FXML
    AnchorPane pane;
    private int order;
    
    @FXML
    public void initialize(){
        phoneNumberField.textProperty().addListener(
            new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable,
                String oldValue, String newValue) {
                if(!newValue.matches("\\d*")){
                    phoneNumberField.setText(newValue.replaceAll("\\D", ""));
                }
            }
                
        });
        
        //set for focus order on enter
        Control[] focusOrder = new Control[] {
            nameField, phoneNumberField, passwordField
        };
        
        pane.setOnKeyPressed(e -> {
            switch(e.getCode()){
                case ENTER:
                    if(order < focusOrder.length-1){
                        order++;
                        focusOrder[order].requestFocus();
                    }else{ 
                        try { confirmBtnEvent();}
                        catch (SQLException ex) {}
                   }
                   break;
            }
        });
    }
	
    public void confirmBtnEvent() throws SQLException{
	if(!nameField.getText().isEmpty() && !phoneNumberField.getText().isEmpty()
            && !passwordField.getText().isEmpty()){
            int res = DBUtils.updateUserPassword(BookShelves.conn, nameField.getText(), phoneNumberField.getText(), 
		passwordField.getText());
            if(res == 1) ViewUtils.loadView("view/LogInOverview.fxml");
            else{
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error Submitting Data");
		alert.setHeaderText("Cannot Find user width name " + nameField.getText());
		alert.setContentText("It\'s seem you are not signed yet");
		alert.showAndWait();
            }
	}else{
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Changing Password");
            alert.setHeaderText("Input Field can\'t be empty");
            alert.setContentText("You must fill out all required data");
            alert.showAndWait();
	}
    }
	
    public void cancelBtnEvent(){
	ViewUtils.loadView("view/LogInOverview.fxml");
    }
}
