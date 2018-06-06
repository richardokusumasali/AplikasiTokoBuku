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
public class BankAccount {
    private StringProperty accountId = new SimpleStringProperty("");
    private IntegerProperty userId = new SimpleIntegerProperty(0);
    private StringProperty bankName = new SimpleStringProperty("");
    
    public BankAccount(String aId, int uId, String bName){
        setAccountId(aId);
        setBankName(bName);
        setUserId(uId);
    }
    
    public void setAccountId(String aId){
        this.accountId.set(aId);
    }
    
    public String getAccountId(){
        return accountId.get();
    }
    
    public StringProperty idProperty(){
        return accountId;
    }
    
    public void setUserId(int uId){
        this.userId.set(uId);
    }
    
    public int getUserId(){
        return userId.get();
    }
    
    public IntegerProperty userIdProperty(){
        return userId;
    }
    
    public void setBankName(String bName){
        bankName.set(bName);
    }
    
    public String getBankName(){
        return bankName.get();
    }
    
    public StringProperty bankNameProperty(){
        return bankName;
    }
    
}
