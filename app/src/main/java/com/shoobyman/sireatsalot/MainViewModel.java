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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.shoobyman.sireatsalot.POJOs.Food;
import com.shoobyman.sireatsalot.POJOs.FoodEntry;
import com.shoobyman.sireatsalot.Utils.JSONUtils;
import com.shoobyman.sireatsalot.Utils.NetworkUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Nullable;

public class MainViewModel extends ViewModel {

    private final String TAG = this.getClass().getSimpleName();
    public static final String DATE_FORMAT_PATTERN = "dd/MM/yyyy";
    public static final String INTENT_EXTRA_MEAL_TYPE = "meal_type_extra";
    public static final String INTENT_EXTRA_DATE = "date_extra";
    public static final int REQUEST_CODE_ADD_FOOD = 13;
    public static final int RESULT_CODE_ADDED_FOOD = 14;
    public static final int RESULT_CODE_NOT_ADDED_FOOD = 15;
    public static final String FB_COLLECTION_USERS = "users";
    public static final String FB_COLLECTION_FOOD_DIARY = "food_diary";


    private FirebaseAuth fbAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore fbDb = FirebaseFirestore.getInstance();
    private int currentFragment = ContentActivity.FRAG_DIARY;
    private EventListener<QuerySnapshot> diaryEntryListener;
    public FirebaseUser getUser() {
        return fbAuth.getCurrentUser();
    }
    public ArrayList<Food> searchResultList = new ArrayList<>();
    public Food currFood = null;
    public int calorieTotal = 0;
    public int currServingType = 0;
    public String currMealType = null;
    public DbActionListener dbActionListener = null;
    public DbDataListener dbDataListener = null;
    public ArrayList<FoodEntry> breakfastList = new ArrayList<>();
    public ArrayList<FoodEntry> lunchList = new ArrayList<>();
    public ArrayList<FoodEntry> dinnerList = new ArrayList<>();
    public ArrayList<FoodEntry> snackList = new ArrayList<>();
    private Date currDate;
    private ListenerRegistration mealsListenerRegistration;

    public interface DbActionListener {
        public void onFoodAdded();
        public void onErrorAddingFood();
    }
    public interface DbDataListener {
        public void onRetrievedMealDataSuccess(ArrayList<FoodEntry> breakfastEntries, ArrayList<FoodEntry> lunchEntries, ArrayList<FoodEntry> dinnerEntries, ArrayList<FoodEntry> snackEntries);
        public void onRetrievedMealDataFailed();
    }

    public void initForDiaryFragment(DbDataListener dbDataListener) {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_PATTERN);
        currDate = new Date();
        initDbListener(dbDataListener);
    }

    public Date getDate() {
        return currDate;
    }

    public void setDate(Date date) {
        currDate = date;
    }

    public String getFormattedDate() {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_PATTERN);
        String date = formatter.format(currDate);
        return date;
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

    public void deleteFoodByDocumentId(String documentId) {
        fbDb.collection(FB_COLLECTION_USERS)
                .document(fbAuth.getCurrentUser().getUid())
                .collection(FB_COLLECTION_FOOD_DIARY)
                .document(documentId)
                .delete()
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error in deleteFoodByDocumentId(): " + e.toString());
                    }
                });
    }

    public void initDbListener(final DbDataListener dbDataListener) {
        this.dbDataListener = dbDataListener;
        diaryEntryListener = new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {
                if (queryDocumentSnapshots != null) {
                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                    breakfastList.clear();
                    lunchList.clear();
                    dinnerList.clear();
                    snackList.clear();
                    calorieTotal = 0;
                    for (DocumentSnapshot d: list) {
                        FoodEntry entry = d.toObject(FoodEntry.class);
                        entry.setDocumentId(d.getId());
                        calorieTotal += entry.getCalories();
                        if (entry.getMeal().equals(App.BREAKFAST_KEY)) {
                            breakfastList.add(entry);
                        } else if (entry.getMeal().equals(App.LUNCH_KEY)) {
                            lunchList.add(entry);
                        } else if (entry.getMeal().equals(App.DINNER_KEY)) {
                            dinnerList.add(entry);
                        } else if (entry.getMeal().equals(App.SNACK_KEY)) {
                            snackList.add(entry);
                        }
                    }
                    dbDataListener.onRetrievedMealDataSuccess(breakfastList, lunchList, dinnerList, snackList);
                }
            }
        };
        setListenerDateFilter(dbDataListener, getFormattedDate());
    }

    public void setListenerDateFilter(DbDataListener dbDataListener, String newDate) {
        this.dbDataListener = dbDataListener;
        if (mealsListenerRegistration != null) {
            mealsListenerRegistration.remove();
        }
        mealsListenerRegistration = fbDb.collection(FB_COLLECTION_USERS)
                .document(fbAuth.getCurrentUser().getUid())
                .collection(FB_COLLECTION_FOOD_DIARY)
                .whereEqualTo("date", newDate)
                .addSnapshotListener(diaryEntryListener);
    }


    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
