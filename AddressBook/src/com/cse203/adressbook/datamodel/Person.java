package com.cse203.adressbook.datamodel;

public class Person {
    private int id;
    private String name;
    private String street;
    private String city;
    private char gender;
    private String zip;

    public Person(String name, String street, String city, char gender, String zip) {
        this.name = name;
        this.street = street;
        this.city = city;
        this.gender = gender;
        this.zip = zip;
    }

    public Person(int id, String name, String street, String city, char gender, String zip) {
        this.id = id;
        this.name = name;
        this.street = street;
        this.city = city;
        this.gender = gender;
        this.zip = zip;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
