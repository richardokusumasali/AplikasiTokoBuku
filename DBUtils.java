/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package book.shelves.utils;

import book.shelves.BookShelves;
import book.shelves.model.BankAccount;
import book.shelves.model.Book;
import book.shelves.model.Purchase;
import book.shelves.model.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 *
 * @author chilly98
 */
public class DBUtils {
    public static int insertUser(Connection conn, User user) throws SQLException {
	String sql = "insert into user(user_password, user_email, user_name, "
		+ "user_category, user_birth_date, user_address, user_phone_number)"
		+ "values(?, ?, ?, 2, ?, ?, ?)";
	PreparedStatement pstm = conn.prepareStatement(sql);
	pstm.setString(1, user.getPassword());
	pstm.setString(2, user.getEmail());
	pstm.setString(3, user.getName());
	pstm.setString(4, user.getBirthDate());
	pstm.setString(5, user.getAddress());
	pstm.setString(6, user.getPhoneNumber());
	return pstm.executeUpdate();
    }
		
    public static int updateUser(Connection conn, User user) throws SQLException{
	String sql = "update table user set user_password = ?, user_email = ?, user_name = ?,"
            + "user_birth_date = ?, user_address = ?, user_phone_number = ? where user_id = ?";
	PreparedStatement pstm = conn.prepareStatement(sql);
	pstm.setString(1, user.getPassword());
	pstm.setString(2, user.getEmail());
	pstm.setString(3, user.getName());
	pstm.setString(4, user.getBirthDate());
	pstm.setString(5, user.getAddress());
	pstm.setString(6, user.getPhoneNumber());
	pstm.setInt(7, user.getId());
	return pstm.executeUpdate();
    }
	    
    public static int updateUserPassword(Connection conn, String userName, 
	String phoneNumber, String newPassword) throws SQLException{
	String sql = "update user set user_password = ? where user_phone_number = ? and user_name = ?";
	PreparedStatement pstm = conn.prepareStatement(sql);
	pstm.setString(1, newPassword);
	pstm.setString(2, phoneNumber);
	pstm.setString(3, userName);
	return pstm.executeUpdate();
    }
		
    public static void deleteUser(Connection conn, int userId) throws SQLException {
	String sql = "delete from user where user_id = ?";
	PreparedStatement pstm = conn.prepareStatement(sql);
	pstm.setInt(1, userId);
	pstm.executeUpdate();
    }
	        
    public static boolean findUser(Connection conn, String userName, String password) throws SQLException{           
	String sql = "Select * from user where user_name = ?";
	PreparedStatement pstm = conn.prepareStatement(sql);
	pstm.setString(1, userName);
	            
	ResultSet rs = pstm.executeQuery();
	            
	if(rs.next()){
	    String validator = rs.getString("user_password");
	    if(password.equals(validator)){
                Global.signedUser.setId(rs.getInt("user_id"));
                System.out.println(Global.signedUser.getId());
                Global.signedUser.setName(rs.getString("user_name"));
                if(rs.getInt("user_category") == 2){
                    System.out.println("succes 2");
                    ViewUtils.loadView("view/HomeOverview.fxml");
                    BookShelves.mainCtrl.showLibraryMenu(true);
                    BookShelves.mainCtrl.showAdminMenu(false);
                }else{
                    ViewUtils.loadView("view/BookAdminOverview.fxml");
                    System.out.println("success 1");
                    BookShelves.mainCtrl.showLibraryMenu(false);
                    BookShelves.mainCtrl.showAdminMenu(true);
                }
	        return true;
	    }
	}
	return false;
    }
	    
	    //for Book Model
    public static void insertBook(Connection conn, Book book) throws SQLException {
			//id name pass zip_code address email book_purchased_id
	String sql = "insert into book "
            + "(book_title, book_author, book_publisher, "
            + "book_released_date, book_price, book_category, book_description) values"
            + "(?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstm = conn.prepareStatement(sql);
	pstm.setString(1, book.getBookTitle());
	pstm.setString(2, book.getBookAuthor());
	pstm.setString(3, book.getBookPublisher());
	pstm.setString(4, book.getBookReleasedDate());
	pstm.setInt(5, book.getBookPrice());
	pstm.setString(6, book.getBookCategory());
	pstm.setString(7, book.getBookDescription());
	pstm.executeUpdate();
    }
	    
    public static void updateBook(Connection conn, Book book) throws SQLException{
	String sql = "update book "
        + "set book_title = ?, book_author = ?, book_publisher = ?, "
        + "book_released_date = ?, book_price = ?, book_category = ?, "
        + "book_description = ? where book_id = ?";
	PreparedStatement pstm = conn.prepareStatement(sql);
	pstm.setString(1, book.getBookTitle());
	pstm.setString(2, book.getBookAuthor());
	pstm.setString(3, book.getBookPublisher());
	pstm.setString(4, book.getBookReleasedDate());
	pstm.setInt(5, book.getBookPrice());
	pstm.setString(6, book.getBookCategory());
	pstm.setString(7, book.getBookDescription());
	pstm.setInt(8, book.getBookId());
        pstm.executeUpdate();
    }
	    
    public static ObservableList<Book> selectBook(Connection conn) throws SQLException{
	ObservableList<Book> bookData = FXCollections.observableArrayList();
	String sql = "select * from book";
	PreparedStatement pstm = conn.prepareStatement(sql);
	            
	ResultSet rs = pstm.executeQuery();
	        
	while(rs.next()){
	    int bookId = rs.getInt("book_id");
	    String bookTitle = rs.getString("book_title");
	    String bookAuthor = rs.getString("book_author");
	    String bookPublisher = rs.getString("book_publisher");
	    ///String bookReleaseDate = rs.getString("book_released_date");
	    Date date = rs.getDate("book_released_date");
	    int bookPrice = rs.getInt("book_price");
	    String bookCat = rs.getString("book_category");
	    String bookDesc = rs.getString("book_description");
            String bookImgSrc = rs.getString("book_icon_src");
            System.out.println(bookImgSrc);
            bookData.add(new Book(bookId, bookTitle, bookAuthor, bookPublisher, 
            String.valueOf(date), bookPrice, bookCat, bookDesc, bookImgSrc));
	}
	        
        return bookData;
    }
	    
    public static ObservableList<Book> selectBookCategorized(Connection conn, String category) throws SQLException{
	ObservableList<Book> bookData = FXCollections.observableArrayList();
	String sql = "select * from book where book_category = ?";
	PreparedStatement pstm = conn.prepareStatement(sql);
	pstm.setString(1, category);
	            
	ResultSet rs = pstm.executeQuery();
	        
	while(rs.next()){
	    int bookId = rs.getInt("book_id");
            String bookTitle = rs.getString("book_title");
	    String bookAuthor = rs.getString("book_author");
	    String bookPublisher = rs.getString("book_publisher");
	    Date date = rs.getDate("book_released_date");
	    int bookPrice = rs.getInt("book_price");
	    String bookCat = rs.getString("book_category");
	    String bookDesc = rs.getString("book_description");
            String bookImgSrc = rs.getString("book_icon_src");
            System.out.println(bookImgSrc);
            bookData.add(new Book(bookId, bookTitle, bookAuthor, bookPublisher, 
            String.valueOf(date), bookPrice, bookCat, bookDesc, bookImgSrc));
	}
	        
	return bookData;
    }
	    
    public static ObservableList<Book> searchBook(Connection conn, 
            String keyword) throws SQLException{
	ObservableList<Book> bookData = FXCollections.observableArrayList();
	String sql = "select * from book where book_title like '%" + keyword +
            "%' or book_author like '%" + keyword + "%' or book_category like "
             + "'%"  + keyword + "%'";
        //keyword = "'%" + keyword + "%'";
	PreparedStatement pstm = conn.prepareStatement(sql);
	
        
	ResultSet rs = pstm.executeQuery();
	while(rs.next()){
            int bookId = rs.getInt("book_id");
            String bookTitle = rs.getString("book_title");
            String bookAuthor = rs.getString("book_author");
            String bookPublisher = rs.getString("book_publisher");
            Date date = rs.getDate("book_released_date");
            int bookPrice = rs.getInt("book_price");
            String bookCat = rs.getString("book_category");
            String bookDesc = rs.getString("book_description");
            String bookImgSrc = rs.getString("book_icon_src");
            bookData.add(new Book(bookId, bookTitle, bookAuthor, bookPublisher, 
            String.valueOf(date), bookPrice, bookCat, bookDesc, bookImgSrc));
        }
	return bookData;
    }
	    
    public static void deleteBook(Connection conn, int idBook) throws SQLException{
        String sql = "delete from book where book_id = ?";
	PreparedStatement pstm = conn.prepareStatement(sql);
	pstm.setInt(1, idBook);
	pstm.execute();
    }
    
    public static void insertBankAccount(Connection conn, BankAccount bAccount) 
            throws SQLException{
        String sql = "insert into bank_account values(?, ?, ?)";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, bAccount.getAccountId());
        pstm.setInt(2, bAccount.getUserId());
        pstm.setString(3, bAccount.getBankName());
        pstm.executeUpdate();
    }
    
    public static ObservableList<BankAccount> getBankAccount(Connection conn, int userId) throws SQLException{
        ObservableList<BankAccount> listAccount = 
                FXCollections.observableArrayList();
        String sql = "select bank_account_id, bank_name from bank_account where user_id = ?";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setInt(1, userId);
        ResultSet rs = pstm.executeQuery();
        while(rs.next()){
            String bId = rs.getString("bank_account_id");
            String bName = rs.getString("bank_name");
            listAccount.add( new BankAccount(bId, 0, bName));
        }
        return listAccount;
    }
    
    public static void insertPurchaseTsc(Connection conn, String accountId, int bookId) throws SQLException{
        String sql = "insert into purchase_tsc (purchase_date, "
                + "bank_account_id, book_id, status) values(curdate(), ?, ?, 0)";
        PreparedStatement pstm = conn.prepareStatement(sql);
        pstm.setString(1, accountId);
        pstm.setInt(2, bookId);
        int res = pstm.executeUpdate();
        if(res == 1){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succes Purchasing Book");
            alert.setHeaderText("Thanks for purchasing");
            alert.setContentText("You have to wait our admin to complete this "
                    + "payment");
            alert.showAndWait();
            if(Global.lastOpened == "home") 
                ViewUtils.loadView("view/HomeOverview.fxml");
            else
                ViewUtils.loadView("view/SearchView.fxml");
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Submitting Data");
            alert.setHeaderText("Some thing wen\'t wrong");
            alert.setContentText("Database disconnected");
            alert.showAndWait();
        }
    }
    
    public static ObservableList<Purchase> selectPurchase(Connection conn) throws SQLException{
        ObservableList<Purchase> list = FXCollections.observableArrayList();
        String sql = "select purchase_id, purchase_date, book_title, "
                + "user_name, status, bank_name from purchase_tsc, "
                + "user join book, bank_account where "
                + "purchase_tsc.book_id = book.book_id and"
                + " purchase_tsc.bank_account_id = bank_account.bank_account_id "
                + "and user.user_id = bank_account.user_id";

        PreparedStatement pstm = conn.prepareStatement(sql);
        ResultSet rs = pstm.executeQuery();
        
        while(rs.next()){
            Purchase psc = new Purchase(
                rs.getInt("purchase_id"),
                rs.getString("purchase_date"),
                rs.getString("user_name"),
                rs.getString("book_title"),
                rs.getString("bank_name"),
                rs.getInt("status")
            );
            list.add(psc);
        }
        
        return list;
    }
}
