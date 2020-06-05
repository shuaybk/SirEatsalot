package com.shoobyman.sireatsalot.POJOs;

public class Serving {
    private String id;                  //serving_id
    private String measure_descrip;     //ex. cup
    private String num_units;           //ex 1.0
    private String serving_descrip;     //ex. 1 cup
    private String calories;
    private String carb;
    private String fat;
    private String protein;

    public Serving(String id, String measure_descrip, String num_units, String serving_descrip, String calories, String carb, String fat, String protein) {
        this.id = id;
        this.measure_descrip = measure_descrip;
        this.num_units = num_units;
        this.serving_descrip = serving_descrip;
        this.calories = calories;
        this.carb = carb;
        this.fat = fat;
        this.protein = protein;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMeasure_descrip() {
        return measure_descrip;
    }

    public void setMeasure_descrip(String measure_descrip) {
        this.measure_descrip = measure_descrip;
    }

    public String getNum_units() {
        return num_units;
    }

    public void setNum_units(String num_units) {
        this.num_units = num_units;
    }

    public String getServing_descrip() {
        return serving_descrip;
    }

    public void setServing_descrip(String serving_descrip) {
        this.serving_descrip = serving_descrip;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getCarb() {
        return carb;
    }

    public void setCarb(String carb) {
        this.carb = carb;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }
}
