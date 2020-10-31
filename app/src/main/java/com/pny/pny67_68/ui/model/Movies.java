package com.pny.pny67_68.ui.model;

public class Movies {

    private int id ;
    private String name;
    private String image;
    private double ratting;

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setRatting(double ratting) {
        this.ratting = ratting;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public double getRatting() {
        return ratting;
    }
}
