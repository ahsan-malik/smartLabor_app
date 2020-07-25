package com.example.smartlabour.models;

public class Experience {
    private String type_work;
    private String duration;

    public Experience() {
        this.type_work = "not-set";
        this.duration = "";
    }

    public Experience(String type_work, String duration) {
        this.type_work = type_work;
        this.duration = duration;
    }

    public Experience(Experience experience){
        this.type_work = experience.getType_work();
        this.duration = experience.duration;
    }

    public String getType_work() {
        return type_work;
    }

    public void setType_work(String type_work) {
        this.type_work = type_work;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

}
