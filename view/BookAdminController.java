package book.shelves.view;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import book.shelves.BookShelves;
import book.shelves.model.Book;
import book.shelves.utils.DBUtils;
import book.shelves.utils.Global;
import book.shelves.utils.ViewUtils;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import org.apache.commons.io.FileUtils;

public class BookAdminController {
	@FXML
	TableView<Book> bookTable;
	@FXML
	TableColumn<Book, String> bookTitleColumn;
	@FXML
	TableColumn<Book, String> bookPublisherColumn;
	@FXML
	JFXTextField bookTitleField;
	@FXML
	JFXTextField bookAuthorField;
	@FXML
	JFXTextField bookPublisherField;
	@FXML
	JFXTextField bookPriceField;
	@FXML
	ComboBox<String> bookCategoryField;
	@FXML
	DatePicker releaseDatePicker;
	@FXML
	TextArea descriptionField;
        @FXML
        AnchorPane pane;
        @FXML
        ImageView bookIcon;
        private int order;
        
	private Book loadedBook = new Book();
	
	@FXML
	public void initialize() throws SQLException{
            updateTable();
            bookTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showDetails(newValue)
            );
            bookCategoryField.getItems().addAll("novel", "manual", 
                "encyclopedia", "dictionary", "biography", "other");
            bookPriceField.textProperty().addListener(
                (ov, oldValue, newValue) -> {
                    if(!newValue.matches("\\d*")){
                        bookPriceField.setText(newValue.replaceAll("\\D", ""));
                    }
            });
            
            //set for focus order on enter
            Control[] focusOrder = new Control[] {
                bookTitleField, bookAuthorField,
                bookPublisherField, bookPriceField, descriptionField
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
	
	public void showDetails(Book book){
            loadedBook = book;
            if(book != null){
		bookTitleField.setText(book.getBookTitle());
		bookAuthorField.setText(book.getBookAuthor());
		bookPublisherField.setText(book.getBookPublisher());
		bookPriceField.setText(String.valueOf(book.getBookPrice()));
		bookCategoryField.setValue(book.getBookCategory());
		releaseDatePicker.setValue(LocalDate.parse(book.getBookReleasedDate(), DateTimeFormatter.ISO_LOCAL_DATE));
		descriptionField.setText(book.getBookDescription());
                bookIcon.setImage(new Image(book.getBookIconSrc()));
            }else{
		bookTitleField.setText("");
		bookAuthorField.setText("");
		bookPublisherField.setText("");
		bookPriceField.setText("");
		bookCategoryField.setValue("");
		releaseDatePicker.setValue(null);
		descriptionField.setText("");
                bookIcon.setImage(null);
            }
	}
	
	public void numOnly(KeyEvent key){
		KeyCode code = key.getCode();
		switch(key.getCode()){
		case TAB:
		case BACK_SPACE:
		case DELETE:
                    break;
		default:
                    if(code.isDigitKey()) key.consume();
		}
	}
	
	public void addPressed() throws ClassNotFoundException, SQLException{
		//book_title, book_author, book_publisher,book_released_date, book_price, book_category
            if(!bookTitleField.getText().isEmpty() && !bookAuthorField.getText().isEmpty() && !bookPublisherField.getText().isEmpty()
                && releaseDatePicker.getValue() != null && !bookPriceField.getText().isEmpty()){
                
                if(!isBookAvailable()){
                    Book book = new Book(0,
                        bookTitleField.getText(),
                        bookAuthorField.getText(),
                        bookPublisherField.getText(),
                        String.valueOf(releaseDatePicker.getValue()),
                        Integer.parseInt(bookPriceField.getText()),
                        String.valueOf(bookCategoryField.getValue()), 
                        descriptionField.getText(), ""
                    );
                    DBUtils.insertBook(BookShelves.conn, book);
                }else{
                    ViewUtils.showAlert("Can't input duplicate book",
                            "The book data your'e trying to submit is "
                            + "has submitted");
                }
		
            }else{
                ViewUtils.showAlert("Error Submitting Data", 
                        "You mus\'t fill out all form input");
            }
		
            updateTable();
	}
	
	public void deletePressed() throws ClassNotFoundException, SQLException{
		if(loadedBook.getBookId() != 0){
			DBUtils.deleteBook(BookShelves.conn, loadedBook.getBookId());
			updateTable();
		}
	}
	
	public void updatePressed() throws ClassNotFoundException, SQLException{
		if(loadedBook.getBookId() != 0){
                    loadedBook.setBookTitle(bookTitleField.getText());
                    loadedBook.setBookAuthor(bookAuthorField.getText());
                    loadedBook.setBookPublisher(bookPublisherField.getText());
                    loadedBook.setBookReleasedDate(String.valueOf(releaseDatePicker.getValue()));
                    loadedBook.setBookPrice(Integer.parseInt(bookPriceField.getText()));
                    loadedBook.setBookCategory(String.valueOf(bookCategoryField.getValue()));
                    loadedBook.setBookDescription(descriptionField.getText());
                    DBUtils.updateBook(BookShelves.conn, loadedBook);
                    updateTable();
		}
	}
	
	private void updateTable() throws SQLException{
		bookTitleColumn.setCellValueFactory(
                    cellData -> cellData.getValue().bookTitleProperty()
                );
		bookPublisherColumn.setCellValueFactory(
                    cellData -> cellData.getValue().bookPublisherProperty()
                );
		
		ObservableList<Book> bookData = FXCollections.observableArrayList();
		bookData = DBUtils.selectBook(BookShelves.conn);
		
		bookTable.setItems(bookData);
	}
	
        private boolean isBookAvailable() throws SQLException{
            String sql = "select * from book where book_title = ?";
            PreparedStatement pstm = BookShelves.conn.prepareStatement(sql);
            pstm.setString(1, bookTitleField.getText());
            ResultSet rs = pstm.executeQuery();
            if(rs.next()) return true;
            return false;
        }
        
        public void uploadEvent() throws IOException, SQLException{
            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter jpgFilter = 
                    new FileChooser.ExtensionFilter("*.)jpg", "* JPG");
            FileChooser.ExtensionFilter pngFilter = 
                    new FileChooser.ExtensionFilter("*.)png", "* PNG");
            File file = fileChooser.showOpenDialog(null);
            File dest = new File("C:\\xampp\\htdocs\\BookShelves\\img");
            FileUtils.copyFileToDirectory(file, dest);
            
            String fileSrc = "http://localhost/BookShelves/img/" 
                + file.getName();
            
            String sql = "update book set book_icon_src = ? where book_id = ?";
            PreparedStatement pstm = BookShelves.conn.prepareStatement(sql);
            pstm.setString(1, fileSrc);
            pstm.setInt(2, loadedBook.getBookId());
            int res = pstm.executeUpdate();
            if(res == 1) System.out.println("Success uploading");
            else System.out.println("Unsuccess uploading");
            System.out.println(fileSrc);
            updateTable();
        }
}
