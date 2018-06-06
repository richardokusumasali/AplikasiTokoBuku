package book.shelves.view;

import book.shelves.utils.ViewUtils;

import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class MainController {
    @FXML
    Menu adminMenu;
    @FXML
    Menu libraryMenu;
    @FXML
    MenuItem logInMenuItem;
	
    public void showLogIn() {
        if(logInMenuItem.getText().matches("Log In")){
            ViewUtils.loadView("view/LogInOverview.fxml");
        }else{
            ViewUtils.loadView("view/LogInOverview.fxml");
            showAdminMenu(false);
            showLibraryMenu(false);
            logInMenuItem.setText("Log In");
        }
    }
        
    public void showAbout() {
        System.out.println("success");
	ViewUtils.loadView("view/AboutOverview.fxml");
    }
        
    public void showBookAdmin(){
	ViewUtils.loadView("view/BookAdminOverview.fxml");
    }
    
    public void showHome(){
	ViewUtils.loadView("view/HomeOverview.fxml");
    }
        
    public void showTransaction(){
        ViewUtils.loadView("view/TransactionAdminView.fxml");
    }
        
    public void showSearch(){
        ViewUtils.loadView("view/SearchView.fxml");
    }
        
    public void showPurchased(){
        ViewUtils.loadView("view/LibraryOverview.fxml");
    }
    
    public void showAdminMenu(boolean isShow){
        adminMenu.setVisible(isShow);
    }
    
    public void showLibraryMenu(boolean isShow){
        libraryMenu.setVisible(isShow);
    }
    
    public MenuItem getLogInMenuItem(){
        return logInMenuItem;
    }
}
