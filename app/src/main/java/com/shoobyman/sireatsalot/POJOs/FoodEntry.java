package com.shoobyman.sireatsalot.POJOs;

public class FoodEntry {
    private String date;  //Format dd/mm/yyyy
    private String meal;
    private String foodId;
    private String foodName;
    private String servingId;
    private String servingSummary;
    private float servingAmount;
    private int calories;
    private int proteins;   //in grams
    private int carbs;      //in grams
    private int fats;       //in grams

    public FoodEntry() {}

    public FoodEntry(String date, String meal, String foodId, String foodName, String servingId, String servingSummary, float servingAmount, int calories, int proteins, int carbs, int fats) {
        this.date = date;
        this.meal = meal;
        this.foodId = foodId;
        this.foodName = foodName;
        this.servingId = servingId;
        this.servingSummary = servingSummary;
        this.servingAmount = servingAmount;
        this.calories = calories;
        this.proteins = proteins;
        this.carbs = carbs;
        this.fats = fats;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMeal() {
        return meal;
    }

    public void setMeal(String meal) {
        this.meal = meal;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getServingId() {
        return servingId;
    }

    public void setServingId(String servingId) {
        this.servingId = servingId;
    }

    public float getServingAmount() {
        return servingAmount;
    }

    public void setServingAmount(float servingAmount) {
        this.servingAmount = servingAmount;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public int getProteins() {
        return proteins;
    }

    public void setProteins(int proteins) {
        this.proteins = proteins;
    }

    public int getCarbs() {
        return carbs;
    }

    public void setCarbs(int carbs) {
        this.carbs = carbs;
    }

    public int getFats() {
        return fats;
    }

    public void setFats(int fats) {
        this.fats = fats;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getServingSummary() {
        return servingSummary;
    }

    public void setServingSummary(String servingSummary) {
        this.servingSummary = servingSummary;
    }

    public String toString() {
        String result = ""
                + "date=" + date + ","
                + "meal=" + meal + ","
                + "foodId=" + foodId + ","
                + "foodName=" + foodName + ","
                + "servingId=" + servingId + ","
                + "servingSummary=" + servingSummary + ","
                + "servingAmount=" + servingAmount + ","
                + "calories=" + calories + ","
                + "proteins=" + proteins + ","
                + "carbs=" + carbs + ","
                + "fats=" + fats;
        return result;
    }
}
