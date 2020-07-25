package com.example.smartlabour.models;

public class ContactDetails {
    private String email, phoneNo, address, city;

    //default constructor
    public ContactDetails(){
        this.email = "";
        this.phoneNo = "";
        this.address = "";
        this.city = "";
    }

    //parameterized constructor
    public ContactDetails(String email, String phoneNo, String address, String city) {
        this.email = email;
        this.phoneNo = phoneNo;
        this.address = address;
        this.city = city;
    }

    //copy constructor
    public ContactDetails(ContactDetails contactDetails){
        this.email = contactDetails.getEmail();
        this.phoneNo = contactDetails.getPhoneNo();
        this.address = contactDetails.getAddress();
        this.city = contactDetails.getCity();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
