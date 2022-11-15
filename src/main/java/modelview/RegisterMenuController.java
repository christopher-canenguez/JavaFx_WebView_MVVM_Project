package modelview;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import com.mycompany.mvvmexample.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author chriscanenguez
 */
public class RegisterMenuController implements Initializable 
{

    @FXML
    TextField nameField;
    @FXML
    TextField numberField;
    @FXML
    TextField passwordField;
    @FXML
    Button registerButton;
    @FXML
    Button returnButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        // TODO
    }

    @FXML
    private void regRecord(ActionEvent event) throws FirebaseAuthException 
    {
        registerUser();
    }

    public void registerUser() throws FirebaseAuthException 
    {
        CreateRequest request = new CreateRequest()
                .setEmail(nameField.getText().replaceAll("\\s", "") + "@email.com")
                .setEmailVerified(false)
                .setPassword(passwordField.getText())
                .setPhoneNumber(numberField.getText())
                .setDisplayName(nameField.getText())
                .setPhotoUrl("http://www.example.com/12345678/photo.png")
                .setDisabled(false);

        clearFields();
        
        UserRecord userRecord = App.fauth.createUser(request);
        System.out.println("Successfully created new user: " + userRecord.getUid());
    }

    public void clearFields() 
    {
        nameField.clear();
        numberField.clear();
        passwordField.clear();
    }
    
    @FXML
    private void switchToLogin() throws IOException 
    {
        App.setRoot("LoginPage.fxml");
    }

}
