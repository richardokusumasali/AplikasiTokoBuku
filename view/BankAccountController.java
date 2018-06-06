/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package book.shelves.view;

import book.shelves.BookShelves;
import book.shelves.model.BankAccount;
import book.shelves.utils.DBUtils;
import book.shelves.utils.Global;
import book.shelves.utils.ViewUtils;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author chilly98
 */
public class BankAccountController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    JFXTextField bankNameField;
    @FXML
    JFXTextField accountNumberField;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        accountNumberField.textProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, 
                    String oldValue, String newValue) {
                if(accountNumberField.getText().length() > 15){
                    String replace = accountNumberField.getText().substring(0, 15);
                    accountNumberField.setText(replace);
                }
            }
        });
    } 
    
    public void addAccount() throws SQLException{
        if(!bankNameField.getText().isEmpty() && 
          !accountNumberField.getText().isEmpty()){
            if(accountNumberField.getText().length() < 11){
                ViewUtils.showAlert(
                    "Account Number is Not Valid", 
                    "The minimum length of bank account is 11"
                );
            }else{
                BankAccount account = new BankAccount(
                    accountNumberField.getText(),
                    Global.signedUser.getId(),
                    bankNameField.getText()
                );
                DBUtils.insertBankAccount(BookShelves.conn, account);
                ViewUtils.loadView("view/PaymentForm.fxml");
            }
            
        }else{
            ViewUtils.showAlert(
                "Can't submit data",
                "Input Field can't be empty"
            );
        }
    }
    
    public void cancelEvent(){
        ViewUtils.loadView("view/PaymentForm.fxml");
    }
}
