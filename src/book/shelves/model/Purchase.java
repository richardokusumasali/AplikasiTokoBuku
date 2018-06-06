/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package book.shelves.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author chilly98
 */
public class Purchase {
    private IntegerProperty id = new SimpleIntegerProperty(0);
    private StringProperty date = new SimpleStringProperty("");
    private StringProperty userName = new SimpleStringProperty("");
    private StringProperty bookName = new SimpleStringProperty("");
    private StringProperty accountNumber = new SimpleStringProperty("");
    private IntegerProperty status= new SimpleIntegerProperty(0);
    
    public Purchase(int id, String date, String userName, String bookName,
        String accountNumber, int status){
        setId(id);
        setDate(date);
        setUserName(userName);
        setBookName(bookName);
        setAccountNumber(accountNumber);
        setStatus(status);
    }
    
    public void setId(int id){
        this.id.set(id);
    }
    
    public int getId(){
        return id.get();
    }
    
    public IntegerProperty idProperty(){
        return id;
    }
    
    public void setUserName(String userName){
        this.userName.set(userName);
    }
    
    public String getUserName(){
        return userName.get();
    }
    
    public StringProperty userNameProperty(){
        return userName;
    }
    
    public void setBookName(String bookName){
        this.bookName.set(bookName);
    }
    
    public String getBookName(){
        return bookName.get();
    }
    
    public StringProperty bookNameProperty(){
        return bookName;
    }
    
    public void setAccountNumber(String aNumber){
        accountNumber.set(aNumber);
    }
    
    public String getAccountNumber(){
        return accountNumber.get();
    }
    
    public StringProperty accountNumberProperty(){
        return accountNumber;
    }
    
    public void setStatus(int status){
        this.status.set(status);
    }
    
    public int getStatus(){
        return status.get();
    }
    
    public IntegerProperty statusProperty(){
        return status;
    }
    
    public void setDate(String date){
        this.date.set(date);
    }
    
    public String getDate(){
        return date.get();
    }
    
    public StringProperty dateProperty(){
        return date;
    }
}
