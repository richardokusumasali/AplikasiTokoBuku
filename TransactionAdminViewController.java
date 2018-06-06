/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package book.shelves.view;

import book.shelves.BookShelves;
import book.shelves.model.Book;
import book.shelves.model.Purchase;
import book.shelves.utils.DBUtils;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * FXML Controller class
 *
 * @author chilly98
 */
public class TransactionAdminViewController{

    /**
     * Initializes the controller class.
     */
    @FXML
    private TableView<Purchase> transactionTable;
    @FXML
    private TableColumn<Purchase, String> nameColumn;
    @FXML
    private TableColumn<Purchase, String> titleColumn;
    @FXML
    private TableColumn<Purchase, String> dateColumn;
    @FXML
    private Label nameLabel;
    @FXML
    private Label bankAccountLabel;
    @FXML
    private Label statusLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private Label dateLabel;
    private Purchase loadedPsc;
    
    
    
    @FXML
    public void initialize() throws SQLException{
        updateTable();
        transactionTable.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> showDetails(newValue)
        );
    }
    
    
    public void showDetails(Purchase psc){
        loadedPsc = psc;
        if(psc != null){
            nameLabel.setText(psc.getUserName());
            titleLabel.setText(psc.getBookName());
            dateLabel.setText(psc.getDate());
            bankAccountLabel.setText(psc.getAccountNumber());
            statusLabel.setText(psc.getStatus() == 0 ? "unapproved" : "approved");
        }else{
            nameLabel.setText("");
            titleLabel.setText("");
            dateLabel.setText("");
            bankAccountLabel.setText("");
            statusLabel.setText("");
        }
    }
    
    public void approveBtnEvent() throws SQLException{
        String sql = "update purchase_tsc set status = 1 where purchase_id = ?";
        PreparedStatement pstm = BookShelves.conn.prepareStatement(sql);
        pstm.setInt(1, loadedPsc.getId());
        pstm.executeUpdate();
        updateTable();
    }
    
    public void unapproveBtnEvent() throws SQLException{
        String sql = "update purchase_tsc set status = 0 where purchase_id = ?";
        PreparedStatement pstm = BookShelves.conn.prepareStatement(sql);
        pstm.setInt(1, loadedPsc.getId());
        pstm.executeUpdate();
        updateTable();
    }
    
    private void updateTable() throws SQLException{
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().userNameProperty());
	titleColumn.setCellValueFactory(cellData -> cellData.getValue().bookNameProperty());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
		
	ObservableList<Purchase> data = FXCollections.observableArrayList();
	data = DBUtils.selectPurchase(BookShelves.conn);
        System.out.println(data.get(0).getBookName());
	transactionTable.setItems(data);
    }
    
}
