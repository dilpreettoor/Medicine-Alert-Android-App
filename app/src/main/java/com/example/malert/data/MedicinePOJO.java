package com.example.malert.data;

public class MedicinePOJO {
    private int id;
    private String name;
    private String dateAndTime;
    private String description;

    public MedicinePOJO(int id, String name, String dateAndTime, String description)
    {
        this.id = id;
        this.name = name;
        this.dateAndTime = dateAndTime;
        this.description = description;
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

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
