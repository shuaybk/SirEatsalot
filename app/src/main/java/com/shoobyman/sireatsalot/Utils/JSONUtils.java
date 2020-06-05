package com.shoobyman.sireatsalot.Utils;

import android.util.Log;

import com.shoobyman.sireatsalot.POJOs.Food;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONUtils {
    private final static String TAG = "JSONUtils";
    private final static String JSON_FOODS_OBJECT_KEY = "foods";
    private final static String JSON_FOOD_ARRAY_KEY = "food";
    private final static String JSON_FOOD_ID_KEY = "food_id";
    private final static String JSON_FOOD_NAME_KEY = "food_name";
    private final static String JSON_FOOD_TYPE_KEY = "food_type";
    private final static String JSON_FOOD_BRAND_KEY = "brand_name";
    private final static String JSON_FOOD_DESCRIPTION_KEY = "food_description";
    private final static String JSON_FOOD_URL_KEY = "food_url";

    public static ArrayList<Food> jsonListToFood(String jsonData) {
        ArrayList<Food> foodList = new ArrayList<>();

        try {
            JSONObject foodsListObject = new JSONObject(jsonData).getJSONObject(JSON_FOODS_OBJECT_KEY);
            JSONArray jsonFoodArray = foodsListObject.getJSONArray(JSON_FOOD_ARRAY_KEY);

            for (int i = 0; i < jsonFoodArray.length(); i++) {
                JSONObject jsonFoodObject = jsonFoodArray.getJSONObject(i);
                foodList.add(jsonToFood(jsonFoodObject));
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception in jsonListToFood: " + e.toString());
        }
        return foodList;
    }

    public static Food jsonToFood(JSONObject jsonFoodObject){
        Food food = null;
        try {
            String id = jsonFoodObject.getString(JSON_FOOD_ID_KEY);
            String name = "";
            String type = "";
            String brand = "";
            String description = "";
            String url = "";

            if (jsonFoodObject.has(JSON_FOOD_NAME_KEY)) {
                name = jsonFoodObject.getString(JSON_FOOD_NAME_KEY);
            }
            if (jsonFoodObject.has(JSON_FOOD_TYPE_KEY)) {
                type = jsonFoodObject.getString(JSON_FOOD_TYPE_KEY);
            }
            if (jsonFoodObject.has(JSON_FOOD_BRAND_KEY)) {
                brand = jsonFoodObject.getString(JSON_FOOD_BRAND_KEY);
            }
            if (jsonFoodObject.has(JSON_FOOD_DESCRIPTION_KEY)) {
                description = jsonFoodObject.getString(JSON_FOOD_DESCRIPTION_KEY);
            }
            if (jsonFoodObject.has(JSON_FOOD_URL_KEY)) {
                url = jsonFoodObject.getString(JSON_FOOD_URL_KEY);
            }

            food = new Food(id, name, type, brand, description, url);
        } catch (Exception e) {
            Log.e(TAG, "Exception in jsonToFood: " + e.toString());
        }

        return food;
    }
}
