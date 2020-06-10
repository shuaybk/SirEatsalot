package com.shoobyman.sireatsalot;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shoobyman.sireatsalot.POJOs.Food;
import com.shoobyman.sireatsalot.POJOs.FoodEntry;
import com.shoobyman.sireatsalot.Utils.JSONUtils;
import com.shoobyman.sireatsalot.Utils.NetworkUtils;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {

    private final String TAG = this.getClass().getSimpleName();
    public static final String INTENT_EXTRA_MEAL_TYPE = "meal_type_extra";
    public static final int REQUEST_CODE_ADD_FOOD = 13;
    public static final int RESULT_CODE_ADDED_FOOD = 14;
    public static final int RESULT_CODE_NOT_ADDED_FOOD = 15;
    public static final String FB_COLLECTION_USERS = "users";
    public static final String FB_COLLECTION_FOOD_DIARY = "food_diary";

    private FirebaseAuth fbAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore fbDb = FirebaseFirestore.getInstance();
    private int currentFragment = ContentActivity.FRAG_DIARY;
    public FirebaseUser getUser() {
        return fbAuth.getCurrentUser();
    }
    public ArrayList<Food> searchResultList = new ArrayList<>();
    public Food currFood = null;
    public int currServingType = 0;
    public String currMealType = null;
    public DbActionListener dbActionListener = null;

    public interface DbActionListener {
        public void onFoodAdded();
        public void onErrorAddingFood();
    }


    public void signOut(final Context context) {
        //Display loading UI here!
        AuthUI.getInstance().signOut(context).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(context, "Signed out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, MainActivity.class);
                context.startActivity(intent);
            }
        });
    }

    //Make sure to call this when changing the fragment so it can be restored on activity recreation
    public void saveNewFragmentState(int fragmentId) {
        currentFragment = fragmentId;
    }

    public int getLastFragmentDisplayed() {
        return currentFragment;
    }


    //TO DO: Fix to actually use the id
    public void getFoodById(String id, final Context context) {
        String url = NetworkUtils.getFindFoodByIdQueryUrl(id);
        System.out.println("The url is " + url);
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                currFood = JSONUtils.jsonToFullFood(response);
                ((AddFoodActivity)context).displayFoodDetails();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("There is error in getFoodById: " + error.toString());
            }
        });
        queue.add(stringRequest);
    }

    public void getFoodSearchResults(String searchExp, final Context context) {
        String url = NetworkUtils.getSearchFoodQueryUrl(searchExp);
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(
                Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    searchResultList = JSONUtils.jsonListToFood(response);
                    ((SearchFoodActivity) context).setResultsPreview(searchResultList);
                } catch (Exception e) {
                    Log.e(TAG, "There was an error returning the search results (Activity context changed probably): " + e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("There is error in getFoodSearchResults: " + error.toString());
            }
        });
        queue.add(stringRequest);
    }

    //Add food to Firebase DB and on complete, execute callback function in calling Activity
    public void addFoodToDb(FoodEntry entry, final DbActionListener dbActionListener) {
        this.dbActionListener = dbActionListener;
        CollectionReference dbDiaryRef = fbDb.collection(FB_COLLECTION_USERS).document(fbAuth.getCurrentUser().getUid()).collection(FB_COLLECTION_FOOD_DIARY);

        dbDiaryRef.add(entry)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        dbActionListener.onFoodAdded();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        dbActionListener.onErrorAddingFood();
                    }
        });
    }


    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
