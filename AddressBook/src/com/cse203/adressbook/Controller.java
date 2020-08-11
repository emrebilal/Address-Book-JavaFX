package com.cse203.adressbook;

import com.cse203.adressbook.datamodel.Person;
import com.cse203.adressbook.datamodel.PersonData;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;

public class Controller {

    @FXML
    private TextField searchTextField;
    @FXML
    private Label idLabel;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField streetTextField;
    @FXML
    private TextField cityTextField;
    @FXML
    private ChoiceBox genderChoiceBox;
    @FXML
    private TextField zipTextField;
    @FXML
    private Label infoLabel;

    @FXML
    public void initialize() {
        firstPerson();
        idLabel.textProperty().addListener((observable, oldValue, newValue) -> {
            searchTextField.clear();
            infoLabel.setText("");
        });
    }

    @FXML
    public void addPerson() {
        String name = nameTextField.getText().trim();
        String street = streetTextField.getText().trim();
        String city = cityTextField.getText();
        String gender = genderChoiceBox.getSelectionModel().getSelectedItem().toString();
        String zip = zipTextField.getText().trim();
        if (!emptyString(name) && !emptyString(street) && !emptyString(city) && !emptyString(zip)) {
            try {
                PersonData.getInstance().addPerson(new Person(name, street, city, gender.charAt(0), zip));
                lastPerson();
                infoLabel.setText("Person has been added. Total record is " + personCount());
            } catch (IOException e) {
                System.out.println("New person could not been added! " + e.getMessage());
                infoLabel.setText("Person could not been added!.");
            } catch (NullPointerException e) {
                infoLabel.setText("Person could not been added!.");
            }
        } else {
            infoLabel.setText("Fill all the fields!");
        }
    }

    @FXML
    public void firstPerson() {
        Person person;
        try {
            person = PersonData.getInstance().firstPerson();
            printPerson(person);
            infoLabel.setText("Total record is " + personCount());
        } catch (IOException e) {
            System.out.println("Could not get the first person!" + e.getMessage());
        }

    }

    @FXML
    public void nextPerson() {
        Person person = null;
        try {
            person = PersonData.getInstance().nextPerson();
        } catch (IOException e) {
            infoLabel.setText("Not any more person!");
            e.printStackTrace();
        }
        printPerson(person);
    }

    @FXML
    public void previousPerson() {
        Person person;
        try {
            person = PersonData.getInstance().previousPerson();
           if(person!=null) {
               printPerson(person);
           }else{
               infoLabel.setText("Not any more person!");
           }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void lastPerson() {
        Person person;
        try {
            person = PersonData.getInstance().lastPerson();
            printPerson(person);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void searchPerson() {
        int id;
        Person person;
        try {
            id = Integer.valueOf(searchTextField.getText().trim());
            person = PersonData.getInstance().searchPerson(id);
            printPerson(person);
        } catch (IOException e) {
            infoLabel.setText("Could not find the person given ID!");
        } catch (NumberFormatException e) {
            infoLabel.setText("You can search by ID only!");
        }
    }

    @FXML
    public void updatePerson() {

        int id = idLabel.getText().equals("") ? 0 : Integer.valueOf(idLabel.getText());
        String name = nameTextField.getText().trim();
        String street = streetTextField.getText().trim();
        String city = cityTextField.getText();
        String gender = genderChoiceBox.getSelectionModel().getSelectedItem().toString();
        String zip = zipTextField.getText().trim();
        if (!emptyString(name) && !emptyString(street) && !emptyString(city) && !emptyString(zip)) {
            try {
                Person person = PersonData.getInstance().updatePerson(new Person(name, street, city, gender.charAt(0), zip), id);
                printPerson(person);
                infoLabel.setText("Person has been updated.");
            } catch (IOException e) {
                System.out.println("Update failed! " + e.getMessage());
                infoLabel.setText("Update failed!");
            } catch (NullPointerException e) {
                infoLabel.setText("Fill all the fields!");
            }
        } else {
            infoLabel.setText("Fill all the fields!");
        }
    }

    //for filling fields in mainWindow
    private void printPerson(Person person) {
        if (person != null) {
            idLabel.setText(String.valueOf(person.getId()));
            nameTextField.setText(person.getName());
            streetTextField.setText(person.getStreet());
            cityTextField.setText(person.getCity());
            if (person.getGender() == 'M') {
                genderChoiceBox.getSelectionModel().select(0);
            } else {
                genderChoiceBox.getSelectionModel().select(1);
            }
            zipTextField.setText(person.getZip());
        } else {
            infoLabel.setText("No person to show!");
        }
    }

    //how many record (person) in file
    private int personCount() {
        return PersonData.getInstance().personCount();
    }

    //check emptyString or null
    private boolean emptyString(final String string) {
        return string == null || string.trim().isEmpty();
    }
}
