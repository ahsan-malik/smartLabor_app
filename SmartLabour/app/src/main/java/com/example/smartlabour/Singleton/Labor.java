package com.example.smartlabour.Singleton;

import com.example.smartlabour.models.Laboror;

public class Labor{
    private static Laboror laboror;

    public static Laboror getLaboror(){
        if(laboror == null)
            laboror = new Laboror();
        return laboror;
    }

    public static void copy(Laboror obj){
        if(laboror == null)
            laboror = new Laboror();
        laboror.setName(obj.getName());
        laboror.setContactDetails(obj.getContactDetails());
        laboror.setUserName(obj.getUserName());
        laboror.setPassword(obj.getPassword());
        laboror.setCategory(obj.getCategory());
        laboror.setExperience(obj.getExperience());
        laboror.setBooked(obj.isBooked());
    }
}
