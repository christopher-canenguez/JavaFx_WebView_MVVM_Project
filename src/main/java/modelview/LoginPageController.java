package modelview;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */

import com.mycompany.mvvmexample.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author chriscanenguez
 */
public class LoginPageController implements Initializable 
{

    @FXML
    private TextField emailTextField;
    
    @FXML
    private TextField passwordTextField;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
    }    
    
    @FXML
    private void switchToRegisterPage() throws IOException 
    {
        App.setRoot("RegisterMenu.fxml");
    }
    
    @FXML
    private void switchToMenu() throws IOException 
    {
        App.setRoot("MainMenu.fxml");
    }
    
}
