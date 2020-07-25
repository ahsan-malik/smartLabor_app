package com.example.smartlabour.models;

public class Laboror extends Person {
    private String userName, category, password;
    private Experience experience;
    private boolean isBooked;

    public Laboror(){
        this.userName = "";
        this.category = "";
        this.password = "";
        this.experience = new Experience();
        this.isBooked = false;
    }

    public Laboror(String name, ContactDetails contactDetails, String userName, String category, Experience experience, boolean isBooked) {
        super(name, contactDetails);
        this.userName = userName;
        this.category = category;
        this.experience = new Experience(experience);
        this.isBooked = isBooked;
    }

    public Laboror(Laboror laboror){
        this(laboror.getName(), laboror.getContactDetails(), laboror.getUserName(), laboror.getCategory(), laboror.getExperience(), laboror.isBooked());
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public Experience getExperience() {
        if(experience == null)
            experience = new Experience();
        return experience;
    }

    public void setExperience(Experience experience) {
        this.experience = new Experience(experience);
    }
}
