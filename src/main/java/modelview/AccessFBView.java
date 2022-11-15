package modelview;

import com.mycompany.mvvmexample.App;
import viewmodel.AccessDataViewModel;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.mycompany.mvvmexample.FirestoreContext;
import com.mycompany.mvvmexample.FirestoreContext;
import com.mycompany.mvvmexample.FirestoreContext;
import com.mycompany.mvvmexample.FirestoreContext;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.Callback;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;
import models.Person;

public class AccessFBView implements Initializable {

    @FXML
    private TextField nameField;
    @FXML
    private TextField majorField;
    @FXML
    private TextField ageField;
    @FXML
    private Button writeButton;
    @FXML
    private TextArea outputField;

    @FXML
    private TableView<Person> tableView;
    @FXML
    private TableColumn<Person, String> nameColumn;
    @FXML
    private TableColumn<Person, String> majorColumn;
    @FXML
    private TableColumn<Person, Integer> ageColumn;

    @FXML
    private MenuItem deleteMenuItem;

    private boolean key;
    private ObservableList<Person> listOfUsers = FXCollections.observableArrayList();
    private Person person;

    public ObservableList<Person> getListOfUsers() 
    {
        return listOfUsers;
    } // End getListOfUsers.

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
        AccessDataViewModel accessDataViewModel = new AccessDataViewModel();
        nameField.textProperty().bindBidirectional(accessDataViewModel.userNameProperty());
        majorField.textProperty().bindBidirectional(accessDataViewModel.userMajorProperty());
        writeButton.disableProperty().bind(accessDataViewModel.isWritePossibleProperty().not());

        nameColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("name"));
        majorColumn.setCellValueFactory(new PropertyValueFactory<Person, String>("major"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<Person, Integer>("age"));

        // Update the table to allow for the name, major and age fields to be editable.
        tableView.setEditable(true);
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        majorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        ageColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
    } // End initialize.

    @FXML
    private void addRecord(ActionEvent event) 
    {
        addData();
    } // End addRecord.

    @FXML
    private void readRecord(ActionEvent event) 
    {
        readFirebase();
    } // End readRecord.

    @FXML
    private void deleteRecord(ActionEvent event) 
    {
        deleteDocument();
    } // End deleteRecord.

    public void addData() 
    {

        DocumentReference docRef = App.fstore.collection("References").document(UUID.randomUUID().toString());
        // Add document data  with id "alovelace" using a hashmap
        Map<String, Object> data = new HashMap<>();
        data.put("Name", nameField.getText());
        data.put("Major", majorField.getText());
        data.put("Age", Integer.parseInt(ageField.getText()));

        clearFields(); // Clear textfields once data is entered.

        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(data);
    }

    public void changeNameCellEvent(CellEditEvent editedCell) 
    {
        Person p = tableView.getSelectionModel().getSelectedItem();
        System.out.println("Name before: " + p.getName());
        p.setName(editedCell.getNewValue().toString());
        System.out.println("After before: " + p.getName());

        // Update an existing document
        DocumentReference docRef = App.fstore.collection("References").document(p.getId());
        // (async) Update one field
        ApiFuture<WriteResult> future = docRef.update("Name", p.getName());
    }

    public void changeMajorCellEvent(CellEditEvent editedCell) 
    {
        Person p = tableView.getSelectionModel().getSelectedItem();
        p.setMajor(editedCell.getNewValue().toString());
        
        // Update an existing document
        DocumentReference docRef = App.fstore.collection("References").document(p.getId());
        // (async) Update one field
        ApiFuture<WriteResult> future = docRef.update("Major", p.getMajor());
    }

    public void changeAgeCellEvent(CellEditEvent editedCell) 
    {
        Person p = tableView.getSelectionModel().getSelectedItem();
        p.setAge(Integer.valueOf(editedCell.getNewValue().toString()));
        
        // Update an existing document
        DocumentReference docRef = App.fstore.collection("References").document(p.getId());
        // (async) Update one field
        ApiFuture<WriteResult> future = docRef.update("Age", p.getAge());
    }

    /**
     * version of method that will print into table rather than the original
     * textArea.
     *
     * @return
     */
    public boolean readFirebase() 
    {
        try 
        {
            key = false;
            tableView.getItems().clear(); // Clears the table when reading.

            // Asynchronously retrieve all documents.
            ApiFuture<QuerySnapshot> future = App.fstore.collection("References").get();

            // future.get() blocks on response
            List<QueryDocumentSnapshot> documents;

            // Go through the firebase database, create a Person object for every document.
            documents = future.get().getDocuments();
            if (documents.size() > 0) 
            {
                System.out.println("Outing....");
                for (QueryDocumentSnapshot document : documents) 
                {
                    person = new Person(String.valueOf(document.getData().get("Name")),
                            document.getData().get("Major").toString(),
                            Integer.parseInt(document.getData().get("Age").toString()), document.getId());
                    //person.setId(document.getId());
                    System.out.println(person);

                    listOfUsers.add(person);

                    System.out.println(document.getId() + " => " + document.getData().get("Name"));
                } // End for.
            } // End if.

            tableView.setItems(listOfUsers);

        } catch (InterruptedException ex) 
        {
            Logger.getLogger(AccessFBView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ExecutionException ex) 
        {
            Logger.getLogger(AccessFBView.class.getName()).log(Level.SEVERE, null, ex);
        }
        return key;
    }

    @FXML
    private void switchToWeb() throws IOException 
    {
        App.setRoot("WebContainer.fxml");
    } // End switchToWeb.
    
    @FXML
    private void switchToMenu() throws IOException 
    {
        App.setRoot("MainMenu.fxml");
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

    public void clearFields() 
    {
        nameField.clear();
        majorField.clear();
        ageField.clear();
    }

    @FXML
    private void deleteDocument() 
    {
        Person p = tableView.getSelectionModel().getSelectedItem();
        tableView.getItems().remove(p);

        // asynchronously delete a document
        ApiFuture<WriteResult> writeResult = App.fstore.collection("References").document(p.getId()).delete();
    }
}
