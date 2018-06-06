/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package book.shelves.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author chilly98
 */
public class MySQLConnUtils {
    public static Connection getMySQLConnection() throws ClassNotFoundException, SQLException{
	String hostName = "localhost";
	String dbName = "book_shelves";
	String userName = "root";
	String password = "chilly98";
	return getMySQLConnection(hostName, dbName, userName, password);
    }
	
    public static Connection getMySQLConnection(String hostName, String dbName, String userName, String password) throws SQLException, ClassNotFoundException{
	Class.forName("com.mysql.jdbc.Driver");
	String connURL= "jdbc:mysql://"+hostName+":3306/"+dbName;
	Connection conn = DriverManager.getConnection(connURL, userName, password);
	return conn;
    }
	
    public static Connection getConnection() throws ClassNotFoundException, SQLException{
	return getMySQLConnection();
    }
	
    public static void closeQuietly(Connection conn) {
	try {
            conn.close();
	}catch(Exception e){
			
	}
    }
	
    public static void rollbackQuietly(Connection conn) {
	try {
            conn.rollback();
	}catch(Exception e) {
			
	}
    }
}
