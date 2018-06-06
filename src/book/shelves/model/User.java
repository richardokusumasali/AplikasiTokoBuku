/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package book.shelves.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author chilly98
 */
public class User {
    private int id;
    private String password;
    private String email;
    private String name;
    private String birthDate;
    private String address;
    private String phoneNumber;	
    
    public User(){
        
    }
	
    public User(int id, String password, String email, String name,
	String birthDate, String address, String phoneNumber) {
	setId(id);
	setPassword(password);
	setEmail(email);
	setName(name);
	setBirthDate(birthDate);
	setAddress(address);
	setPhoneNumber(phoneNumber);
    }
	
    public void setId(int id){
	this.id = id;
    }
	
    public int getId(){
	return id;
    }
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public String getPassword(){
		return password;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public String getEmail(){
		return email;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void setBirthDate(String birthDate){
		this.birthDate = birthDate;
	}
	
	public String getBirthDate(){
		return birthDate;
	}
	
	public void setAddress(String address){
		this.address = address;
	}
	
	public String getAddress(){
		return address;
	}
	
	public void setPhoneNumber(String phoneNumber){
		this.phoneNumber = phoneNumber;
	}
	
	public String getPhoneNumber(){
		return phoneNumber;
	}
}
