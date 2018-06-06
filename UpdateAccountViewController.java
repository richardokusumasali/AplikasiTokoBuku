/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package book.shelves.view;

import book.shelves.BookShelves;
import book.shelves.model.BankAccount;
import book.shelves.utils.Global;
import book.shelves.utils.ViewUtils;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Yunika
 */
public class UpdateAccountViewController{

    /**
     * Initializes the controller class.
     */
    @FXML
    JFXTextField nameField;
    @FXML
    JFXTextField phoneNumberField;
    @FXML
    DatePicker birthDateField;
    @FXML
    JFXTextField addressField;
    @FXML
    JFXTextField emailField;
    @FXML
    JFXTextField bankIdField;
    @FXML
    JFXTextField bankNameField;
    @FXML
    TableView<BankAccount> bankTable;
    @FXML
    TableColumn<BankAccount, String> idColumn;
    @FXML
    TableColumn<BankAccount, String> bankNameColumn;
    @FXML
    AnchorPane pane;
    private int order;
    private String selectedId;
    
    @FXML
    public void initialize() throws SQLException {
        // TODO
        System.out.println("Success to Init");
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
        
        System.out.println(Global.viewedBook.getBookId());
        String sql = "select * from user where user_id = ?";
        PreparedStatement pstm = BookShelves.conn.prepareStatement(sql);
        pstm.setInt(1, Global.signedUser.getId());
        ResultSet rs = pstm.executeQuery();
        if(rs.next()){
            System.out.println("succes sql");
            nameField.setText(rs.getString("user_name"));
            phoneNumberField.setText(rs.getString("user_phone_number"));
            birthDateField.setValue(LocalDate.parse(rs.getString("user_birth_date")));
            addressField.setText(rs.getString("user_address"));
            emailField.setText(rs.getString("user_email"));
        }  
      
        updateBankTable();
        
        bankTable.getSelectionModel().selectedItemProperty().addListener(
            (ov, oldValue, newValue)->{
                showDetails(newValue);
            }
        );
        
        Control[] focusOrder = new Control[] {
            nameField, phoneNumberField, addressField, emailField
        };

        pane.setOnKeyPressed(e -> {
            switch(e.getCode()){
                case ENTER:
                    if(order < focusOrder.length-1){
                        order++;
                        focusOrder[order].requestFocus();
                    }
                    break;
            }
        });
    }  
    
    private void showDetails(BankAccount account){
        if(account != null){
            selectedId = account.getAccountId();
            bankIdField.setText(account.getAccountId());
            bankNameField.setText(account.getBankName());
        }else{
            bankIdField.setText("");
            bankNameField.setText("");
        }
    }
    
    public void updateBtnEvent() throws SQLException{
        String sql = "update user set user_name = ?, user_phone_number = ?, "
                + "user_address = ?, user_birth_date = ?, user_email = ?"
                + " where user_id = ?";
        PreparedStatement pstm = BookShelves.conn.prepareStatement(sql);
        pstm.setString(1, nameField.getText());
        pstm.setString(2, phoneNumberField.getText());
        pstm.setString(3, addressField.getText());
        pstm.setString(4, birthDateField.getValue().toString());
        pstm.setString(5, emailField.getText());
        pstm.setInt(6, Global.signedUser.getId());
        int res = pstm.executeUpdate();
        if(res == 1){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText("Update Information");
            alert.setContentText("Update succesful");
            alert.showAndWait();
            ViewUtils.loadView("view/LibraryOverview.fxml");
        }else{
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText("Update Information");
            alert.setContentText("Update unsuccesful");
            alert.showAndWait();
        }
    }
    
    public void cancelBtnEvent(){
        ViewUtils.loadView("view/LibraryOverview.fxml");
    }
    
    private void updateBankTable() throws SQLException{
        ObservableList<BankAccount> bankAccount = FXCollections.observableArrayList();
        String sql = "select * from bank_account where user_id = ?";
        PreparedStatement pstm = BookShelves.conn.prepareStatement(sql);
        pstm.setInt(1, Global.signedUser.getId());
        ResultSet rs = pstm.executeQuery();
        while(rs.next()){
           BankAccount account = new BankAccount(
              rs.getString("bank_account_id"),
              rs.getInt("user_id"),
              rs.getString("bank_name")
           );
           bankAccount.add(account);
        }
        bankTable.setItems(bankAccount);
        
        idColumn.setCellValueFactory(celldata -> celldata.getValue().idProperty());
        bankNameColumn.setCellValueFactory(celldata -> celldata.getValue().bankNameProperty());
    }
    
    public void btnBankUpdateEvent() throws SQLException{
        if(!selectedId.isEmpty()){
            if(!bankNameField.getText().isEmpty() || !bankIdField.getText().isEmpty()){
                String sql = "update bank_account set bank_name = ?"
                        + "where bank_account_id = ?";
                PreparedStatement pstm = BookShelves.conn.prepareStatement(sql);
                pstm.setString(1, bankNameField.getText());
                pstm.setString(2, bankIdField.getText());
                pstm.executeUpdate();
                updateBankTable();
            }else{
                ViewUtils.showAlert(
                    "Can't update data", 
                    "Please fill out required data before update");
            }
        }
    }
    
    public void btnBankDeleteEvent() throws SQLException{
        if(!selectedId.isEmpty()){
            String sql = "delete from bank_account where bank_account_id = ?";
            PreparedStatement pstm = BookShelves.conn.prepareStatement(sql);
            pstm.setString(1, bankIdField.getText());
            pstm.executeUpdate();
            updateBankTable();
        }
    }
    
}
