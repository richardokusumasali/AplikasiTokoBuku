/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package book.shelves.utils;

import book.shelves.BookShelves;
import book.shelves.view.BookDetailController;
import book.shelves.view.BookCardController;
import book.shelves.view.RatingViewController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author chilly98
 */
public class ViewUtils {
    public static void loadView(String location) {
	try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(BookShelves.class.getResource(location));
            AnchorPane signinOverview;
            signinOverview = (AnchorPane) loader.load();
            BookShelves.rootLayout.setCenter(signinOverview);
	} catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
	}
    }
    
    public static AnchorPane showBookCard(String title, String id, String src, boolean vis) 
            throws IOException{
	FXMLLoader loader = new FXMLLoader();
	loader.setLocation(BookShelves.class.getResource("view/BookCard.fxml"));
	AnchorPane view = (AnchorPane) loader.load();
		
	BookCardController controller = 
                (BookCardController) loader.getController();
	controller.setTitle(title, id);
        controller.setImage(src);
        controller.setDetailVisible(vis);
	return view;
    }
    
    public static AnchorPane showRatingCard(String name, 
        String rating, String comment) throws IOException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(BookShelves.class.getResource("view/RatingView.fxml"));
	AnchorPane view = (AnchorPane) loader.load();
        
        RatingViewController controller = loader.getController();
        controller.setRatingView(name, rating, comment);
        return view;
    }
    
    public static void showAlert(String title, String content){
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
