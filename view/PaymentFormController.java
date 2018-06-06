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
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author chilly98
 */
public class PaymentFormController  {

    /**
     * Initializes the controller class.
     */
    @FXML
    Label nameField;
    @FXML
    ComboBox bankAccountField;
    
    @FXML 
    public void initialize(){
        nameField.setText(nameField.getText() +" " + Global.signedUser.getName());
        try {
            for(BankAccount bAccount : DBUtils.getBankAccount(BookShelves.conn, 
                   Global.signedUser.getId())){
                bankAccountField.getItems().addAll(bAccount.getAccountId());
            }
        } catch (SQLException ex) {
            Logger.getLogger(PaymentFormController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void purchasedBtnEvent() throws SQLException{
        if(bankAccountField.getValue() != null){
            DBUtils.insertPurchaseTsc(
            BookShelves.conn, 
            String.valueOf(bankAccountField.getValue()), 
            Global.viewedBook.getBookId());
        }
    }
    
    public void addAccountEvent(){
        ViewUtils.loadView("view/BankAccountForm.fxml");
    }
    
    public void cancelEvent(){
        ViewUtils.loadView("view/BookDetailOverview.fxml");
    }
    
}
