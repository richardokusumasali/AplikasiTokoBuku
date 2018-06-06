package book.shelves.view;

import java.sql.SQLException;
import book.shelves.BookShelves;
import book.shelves.model.User;
import book.shelves.utils.DBUtils;
import book.shelves.utils.ViewUtils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Control;
import javafx.scene.layout.AnchorPane;

public class SignInController {
    @FXML
    private JFXTextField nameField;
    @FXML
    private JFXTextField phoneNumberField;
    @FXML
    private DatePicker birthDateField;
    @FXML
    private JFXTextField addressField;
    @FXML
    private JFXTextField emailField;
    @FXML
    private JFXPasswordField passwordField;
    @FXML
    private JFXCheckBox agreeCheckBox;
    @FXML
    private JFXButton signInButton;
    @FXML
    AnchorPane pane;
    private int order;
	
    public SignInController() {
		
    }
    
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
        
        emailField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if(!newValue){
                Pattern pat = Pattern.compile("^[\\w-\\+](\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$");
                if(emailField.getText().matches(pat.pattern())){
                    System.out.println("Error its not valid email");
                }else{
                    System.out.println("Its valid email");
                }
            }
        });
        
        //set for focus order on enter
        Control[] focusOrder = new Control[] {
            nameField, phoneNumberField, birthDateField,
            addressField, emailField, passwordField, signInButton
        };
        
        pane.setOnKeyPressed(e -> {
            switch(e.getCode()){
                case ENTER:
                    if(order < focusOrder.length-1){
                        order++;
                        focusOrder[order].requestFocus();
                    }else{ 
                        try { signOnClick();}
                        catch (ClassNotFoundException ex) {}
                        catch (SQLException ex) {}
                   }
                   break;
            }
        });
    }
	
    public void signOnClick() throws ClassNotFoundException, SQLException {
		
	if(!nameField.getText().isEmpty() && !phoneNumberField.getText().isEmpty() 
            && birthDateField.getValue() != null && !addressField.getText().isEmpty()
            && !emailField.getText().isEmpty()&& !passwordField.getText().isEmpty()
            && agreeCheckBox.isSelected()){
            //id name pass zip_code address email book_purchased_id
            //User user = new User();
            if(!checkUserAvailibilty()){
                User user = new User(
                    0, passwordField.getText(), emailField.getText(), nameField.getText(),
                    String.valueOf(birthDateField.getValue()), addressField.getText(),
                    phoneNumberField.getText()
                );
                int res = DBUtils.insertUser(BookShelves.conn, user);
                if(res == 1) ViewUtils.loadView("view/LibraryOverview.fxml");
                else{
                    ViewUtils.showAlert(
                        "Error in Connecting Server", 
                        "It seems error while submitting data. "
                            + "Please restart app"
                    );
                }
            }else{
                ViewUtils.showAlert(
                    "The user already signed", 
                    "It seems the user is already signed"
                );
            }			
	}else{
            ViewUtils.showAlert(
                "Error Submitting Data", 
                "You must agree the terms and condition"
                + " and also fill out all required data"
            );
	}
    }
    
    public void cancelEvent(){
        ViewUtils.loadView("view/LogInOverview.fxml");
    }
    
    public boolean checkUserAvailibilty() throws SQLException{
        String sql = "select user_id from user "
            + "where user_name = ?";
        PreparedStatement pstm = BookShelves.conn.prepareStatement(sql);
        pstm.setString(1, nameField.getText());
        ResultSet rs = pstm.executeQuery();
        if(rs.next()) return true;
        else return false;
    }
}
