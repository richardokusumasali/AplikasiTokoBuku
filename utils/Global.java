/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package book.shelves.utils;

import book.shelves.model.Book;
import book.shelves.model.User;

/**
 *
 * @author chilly98
 */
public class Global {
    public static User signedUser = new User();
    public static Book viewedBook = new Book();
    public static String lastOpened = "";
}


