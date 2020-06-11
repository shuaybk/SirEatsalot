package com.shoobyman.sireatsalot.Utils;

import android.util.Log;

import com.shoobyman.sireatsalot.POJOs.Food;
import com.shoobyman.sireatsalot.POJOs.Serving;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONUtils {
    private final static String TAG = "JSONUtils";

    private final static String JSON_FOODS_OBJECT_KEY = "foods";
    private final static String JSON_FOOD_OBJECT_KEY = "food";
    private final static String JSON_FOOD_ARRAY_KEY = "food";
    private final static String JSON_FOOD_SERVINGS_KEY = "servings";
    private final static String JSON_FOOD_ID_KEY = "food_id";
    private final static String JSON_FOOD_NAME_KEY = "food_name";
    private final static String JSON_FOOD_TYPE_KEY = "food_type";
    private final static String JSON_FOOD_BRAND_KEY = "brand_name";
    private final static String JSON_FOOD_DESCRIPTION_KEY = "food_description";
    private final static String JSON_FOOD_URL_KEY = "food_url";

    private final static String JSON_SERVINGS_ARRAY_KEY = "serving";
    private final static String JSON_SERVING_ID_KEY = "serving_id";
    private final static String JSON_SERVING_MEASURE_DESCR_KEY = "measurement_description";
    private final static String JSON_SERVING_NUM_UNITS_KEY = "number_of_units";
    private final static String JSON_SERVING_SERVING_DESCR_KEY = "serving_description";
    private final static String JSON_SERVING_CALORIES_KEY = "calories";
    private final static String JSON_SERVING_CARB_KEY = "carbohydrate";
    private final static String JSON_SERVING_FAT_KEY = "fat";
    private final static String JSON_SERVING_PROTEIN_KEY = "protein";

    public static ArrayList<Food> jsonListToFood(String jsonData) {
        ArrayList<Food> foodList = new ArrayList<>();

        try {
            JSONObject foodsListObject = new JSONObject(jsonData).getJSONObject(JSON_FOODS_OBJECT_KEY);
            JSONArray jsonFoodArray = foodsListObject.getJSONArray(JSON_FOOD_ARRAY_KEY);

            for (int i = 0; i < jsonFoodArray.length(); i++) {
                JSONObject jsonFoodObject = jsonFoodArray.getJSONObject(i);
                foodList.add(jsonToBasicFood(jsonFoodObject));
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception in jsonListToFood: " + e.toString());
        }
        return foodList;
    }

    public static Food jsonToBasicFood(JSONObject jsonFoodObject){
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
            Log.e(TAG, "Exception in jsonToBasicFood: " + e.toString());
        }

        return food;
    }

    public static Food jsonToFullFood(String jsonData) {
        System.out.println(jsonData);
        Food food = null;
        try {
            JSONObject jsonFoodObject = new JSONObject(jsonData).getJSONObject(JSON_FOOD_OBJECT_KEY);
            ArrayList<Serving> servingsList = new ArrayList<>();

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
            if (jsonFoodObject.has(JSON_FOOD_SERVINGS_KEY)) {
                JSONObject servingsObj = jsonFoodObject.getJSONObject(JSON_FOOD_SERVINGS_KEY);
                System.out.println("THE JSON FOR THE SERVINGS ISSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
                if (servingsObj.toString().length() > 2000) {
                    Log.e(TAG, servingsObj.toString().substring(0, 2000));
                    Log.e(TAG, servingsObj.toString().substring(2000));
                } else {
                    Log.e(TAG, servingsObj.toString());
                }
                JSONArray servingsArray = servingsObj.optJSONArray(JSON_SERVINGS_ARRAY_KEY);
                //If there is only one serving type, it's a JSONObject.  If there's more, it's a JSONArray
                if (servingsArray == null) { //Sometimes its an array, sometimes it isn't.  So complicate :(
                    Serving serving = jsonToServing(servingsObj.optJSONObject(JSON_SERVINGS_ARRAY_KEY));
                    servingsList.add(serving);
                } else {
                    servingsList = jsonToServingsList(servingsArray);
                }
            }

            food = new Food(id, name, type, brand, description, url);
            food.setServingList(servingsList);
        } catch (Exception e) {
            Log.e(TAG, "Exception in jsonToFullFood: " + e.toString());
        }

        return food;
    }

    private static ArrayList<Serving> jsonToServingsList(JSONArray servingsArray) {
        ArrayList<Serving> servingsList = new ArrayList<>();

        try {
            for (int i = 0; i < servingsArray.length(); i++) {
                JSONObject jsonServing = servingsArray.getJSONObject(i);
                servingsList.add(jsonToServing(jsonServing));
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception in jsonToServingsList: " + e.toString());
        }

        return servingsList;
    }

    private static Serving jsonToServing(JSONObject jsonServing) {
        Serving serving = null;
        try {
            String id = jsonServing.getString(JSON_SERVING_ID_KEY);
            String measure_descrip = "";
            String num_units = "1";
            String serving_descrip = "";
            String calories = "0";
            String carb = "0";
            String fat = "0";
            String protein = "0";

            if (jsonServing.has(JSON_SERVING_MEASURE_DESCR_KEY)) {
                measure_descrip = jsonServing.getString(JSON_SERVING_MEASURE_DESCR_KEY);
            }
            if (jsonServing.has(JSON_SERVING_NUM_UNITS_KEY)) {
                num_units = jsonServing.getString(JSON_SERVING_NUM_UNITS_KEY);
            }
            if (jsonServing.has(JSON_SERVING_SERVING_DESCR_KEY)) {
                serving_descrip = jsonServing.getString(JSON_SERVING_SERVING_DESCR_KEY);
            }
            if (jsonServing.has(JSON_SERVING_CALORIES_KEY)) {
                calories = jsonServing.getString(JSON_SERVING_CALORIES_KEY);
            }
            if (jsonServing.has(JSON_SERVING_CARB_KEY)) {
                carb = jsonServing.getString(JSON_SERVING_CARB_KEY);
            }
            if (jsonServing.has(JSON_SERVING_FAT_KEY)) {
                fat = jsonServing.getString(JSON_SERVING_FAT_KEY);
            }
            if (jsonServing.has(JSON_SERVING_PROTEIN_KEY)) {
                protein = jsonServing.getString(JSON_SERVING_PROTEIN_KEY);
            }
            serving = new Serving(id, measure_descrip, num_units, serving_descrip, calories, carb, fat, protein);
        } catch (Exception e) {
            Log.e(TAG, "Exception in jsonToServing: " + e.toString());
        }
        return serving;
    }
}
