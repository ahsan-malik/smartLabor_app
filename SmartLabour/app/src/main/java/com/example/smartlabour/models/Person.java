package com.example.smartlabour.models;

public abstract class Person {
    private String name;
    private ContactDetails contactDetails;

    public Person(){
        this.name = "";
        this.contactDetails = new ContactDetails();
    }

    public Person(String name, ContactDetails contactDetails) {
        this.name = name;
        this.contactDetails = new ContactDetails(contactDetails);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContactDetails getContactDetails() {
        if(contactDetails == null)
            contactDetails = new ContactDetails();
        return contactDetails;
    }

    public void setContactDetails(ContactDetails contactDetails) {
        this.contactDetails = new ContactDetails(contactDetails);
    }
}
