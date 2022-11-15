/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author MoaathAlrajab
 */
public class Person {

    private SimpleStringProperty name;
    private SimpleStringProperty major;
    private String id;
    private int age;

    public Person(String name, String major, int age, String id) {
        this.name = new SimpleStringProperty(name);
        this.major = new SimpleStringProperty(major);
        this.age = age;
        this.id = id;
    } // End Person.

    public String getId() {
        return id;
    } // End getId.

    public void setId(String id) {
        this.id = id;
    } // End setId.

    public String getName() {
        return name.get();
    } // End getName.

    public void setName(String newName) {
        name.set(newName);
    } // End setName.

    public String getMajor() {
        return major.get();
    } // End getMajor.

    public void setMajor(String newMajor) {
        major.set(newMajor);
    } // End setMajor.

    public int getAge() {
        return age;
    } // End getAge.

    public void setAge(int age) {
        this.age = age;
    } // End setAge.
    
    public String toString() {
        String result = 
                "Name: " + getName() + "\n" + 
                "Major: " + getMajor() + "\n" + 
                "Age: " + getAge() + "\n" + 
                "Id: " + getId();
        
        return result;
    } // End toString.
} // End Person.
