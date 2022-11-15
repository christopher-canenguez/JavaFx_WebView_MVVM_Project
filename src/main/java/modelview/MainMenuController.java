/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package modelview;

import com.mycompany.mvvmexample.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author chriscanenguez
 */
public class MainMenuController implements Initializable 
{

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
    }    
    
    @FXML
    private void switchToDataEntry() throws IOException 
    {
        App.setRoot("AccessFBView.fxml");
    } // End switchToWeb.
    
    @FXML
    private void switchToWeb() throws IOException 
    {
        App.setRoot("WebContainer.fxml");
    } // End switchToWeb.

    @FXML
    private void switchToRegisterPage() throws IOException 
    {
        App.setRoot("RegisterMenu.fxml");
    }
    
    @FXML
    private void switchToLogin() throws IOException 
    {
        App.setRoot("LoginPage.fxml");
    }
    
}
