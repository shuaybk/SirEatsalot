package com.shoobyman.sireatsalot.POJOs;

import java.util.ArrayList;

public class Food {
    private String id;
    private String name;
    private String type;
    private String brand;
    private String description;
    private String url;
    private ArrayList<Serving> servingList;

    public Food(String id, String name, String type, String brand, String description, String url) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.brand = brand;
        this.description = description;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ArrayList<Serving> getServingList() {
        return servingList;
    }

    public void setServingList(ArrayList<Serving> servingList) {
        this.servingList = servingList;
    }
}
