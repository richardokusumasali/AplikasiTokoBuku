/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package book.shelves.model;

/**
 *
 * @author chilly98
 */
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Book {
    private IntegerProperty bookId = new SimpleIntegerProperty(0);
    private StringProperty bookTitle = new SimpleStringProperty("");
    private StringProperty bookAuthor = new SimpleStringProperty("some author");
    private StringProperty bookPublisher = new SimpleStringProperty("");
    private StringProperty bookReleasedDate = new SimpleStringProperty("2018-11-11");
    private IntegerProperty bookPrice = new SimpleIntegerProperty(0);
    private StringProperty bookCategory = new SimpleStringProperty("novel");
    private StringProperty bookDescription = new SimpleStringProperty("");
    private StringProperty bookIconSrc = new SimpleStringProperty("");
	
    public Book(){
		
    }
	
    public Book(int bId, String bTitle, String bAuthor, String bPublisher, 
	String bRelease, int bPrice, String bCat, String bDesc, String bSrc){
        setBookId(bId);
        setBookTitle(bTitle);
	setBookAuthor(bAuthor);
	setBookPublisher(bPublisher);
	setBookReleasedDate(bRelease);
	setBookPrice(bPrice);
	setBookCategory(bCat);
	setBookDescription(bDesc);
        setBookIconSrc(bSrc);
    }
	
    public void setBookId(int bId){
	bookId.set(bId);
    }
	
    public int getBookId(){
	return bookId.get();
    }
	
    public IntegerProperty bookIdroperty(){
        return bookId;
    }
	
    public void setBookTitle(String bTitle){
	bookTitle.set(bTitle);
    }
	
    public String getBookTitle(){
	return bookTitle.get();
    }
	
    public StringProperty bookTitleProperty(){
	return bookTitle;
    }
	
    public void setBookPublisher(String bPublisher){
	bookPublisher.set(bPublisher);
    }
	
    public String getBookPublisher(){
	return bookPublisher.get();
    }
	
    public StringProperty bookPublisherProperty(){
	return bookPublisher;
    }
	
    public void setBookAuthor(String bAuthor){
	bookPublisher.set(bAuthor);
    }
	
    public String getBookAuthor(){
	return bookAuthor.get();
    }
	
    public StringProperty bookAuthorProperty(){
	return bookAuthor;
    }
	
    public void setBookReleasedDate(String bRelease){
	bookReleasedDate.set(bRelease);
    }
	
    public String getBookReleasedDate(){
	return bookReleasedDate.get();
    }
	
    public StringProperty bookReleasedDateProperty(){
	return bookReleasedDate;
    }
	
    public void setBookPrice(int bPrice){
	bookPrice.set(bPrice);
    }
	
    public int getBookPrice(){
	return bookPrice.get();
    }
	
    public IntegerProperty bookPriceProperty(){
	return bookPrice;
    }	
	
    public void setBookCategory(String bCat){
	bookCategory.set(bCat);
    }
	
    public String getBookCategory(){
	return bookCategory.get();
    }
	
    public StringProperty bookCategoryProperty(){
	return bookCategory;
    }
	
    public void setBookDescription(String bDesc){
	bookDescription.set(bDesc);
    }
	
    public String getBookDescription(){
	return bookDescription.get();
    }
	
    public StringProperty bookDescriptionProperty(){
	return bookDescription;
    }
    
    public void setBookIconSrc(String iconSrc){
        bookIconSrc.set(iconSrc);
    }
    
    public String getBookIconSrc(){
        return bookIconSrc.get();
    }
    
    public StringProperty bookIconSrcProperty(){
        return bookIconSrc;
    }
}
