package com.shoobyman.sireatsalot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.shoobyman.sireatsalot.POJOs.Food;
import com.shoobyman.sireatsalot.POJOs.FoodEntry;
import com.shoobyman.sireatsalot.POJOs.Serving;
import com.shoobyman.sireatsalot.databinding.ActivityAddFoodBinding;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class AddFoodActivity extends AppCompatActivity implements MainViewModel.DbActionListener {

    private final String TAG = this.getClass().getSimpleName();

    ActivityAddFoodBinding mBinding;
    private MainViewModel mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_food);
        mData = new ViewModelProvider(this).get(MainViewModel.class);

        init(getIntent().getStringExtra(MainViewModel.INTENT_EXTRA_MEAL_TYPE));
    }

    private void init(String mealType) {
        String foodId = getIntent().getStringExtra(SearchFoodActivity.INTENT_EXTRA_FOOD_ID);
        System.out.println("The extra ID is " + foodId);
        if (mData.currFood == null || !mData.currFood.getId().equals(foodId)) {
            //Below method will update the view on completion by calling displayFoodDetails()
            mData.currMealType = mealType;
            mData.getFoodById(foodId, this);
        } else {
            displayFoodDetails();
        }

        mBinding.buttonAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Serving serving = mData.currFood.getServingList().get(mData.currServingType);

                String date = formatter.format(new Date());
                String meal = getString(R.string.breakfast_item);
                String foodId = mData.currFood.getId();
                String foodName = mData.currFood.getName();
                String servingId = serving.getId();
                String servingSummary;
                float amount = Float.parseFloat(mBinding.servingEntry.getText().toString());

                servingSummary = Float.toString(Float.parseFloat(serving.getNum_units())*amount) + " " + serving.getMeasure_descrip();

                FoodEntry entry = new FoodEntry(
                        date,
                        meal,
                        foodId,
                        foodName,
                        servingId,
                        servingSummary,
                        amount,
                        0,
                        0,
                        0,
                        0
                );
                mData.addFoodToDb(entry, AddFoodActivity.this);
            }
        });
    }


    public void displayFoodDetails() {
        final ArrayList<String> mealType = new ArrayList<String>() {
            {
                add(getString(R.string.breakfast_item));
                add(getString(R.string.lunch_item));
                add(getString(R.string.dinner_item));
                add(getString(R.string.snack_item));
            }
        };
        ArrayList<String> servingType = new ArrayList<>();
        for (Serving serving: mData.currFood.getServingList()) {
            servingType.add(serving.getServing_descrip());
        }

        mBinding.foodDetailsText.setText("Name = " + mData.currFood.getName() + ", Type = " + mData.currFood.getType());
        ArrayAdapter<String> mealAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, mealType);
        mealAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBinding.mealEntrySpinner.setAdapter(mealAdapter);

        if (mData.currMealType.equals(getString(R.string.breakfast_item))) {
            mBinding.mealEntrySpinner.setSelection(0);
        } else if (mData.currMealType.equals(getString(R.string.lunch_item))) {
            mBinding.mealEntrySpinner.setSelection(1);
        } else if (mData.currMealType.equals(getString(R.string.dinner_item))) {
            mBinding.mealEntrySpinner.setSelection(2);
        } else if (mData.currMealType.equals(getString(R.string.snack_item))) {
            mBinding.mealEntrySpinner.setSelection(3);
        }
        
        ArrayAdapter<String> servingAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, servingType);
        servingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mBinding.servingTypeSpinner.setAdapter(servingAdapter);

        mBinding.mealEntrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mData.currMealType = mealType.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mBinding.servingTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mData.currServingType = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Do nothing
            }
        });
    }

    @Override
    public void onFoodAdded() {
        Toast.makeText(this, "Food has been added to DB", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        setResult(MainViewModel.RESULT_CODE_ADDED_FOOD, intent);
        finish();
    }

    @Override
    public void onErrorAddingFood() {
        Toast.makeText(this, "Error adding food!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        setResult(MainViewModel.RESULT_CODE_NOT_ADDED_FOOD, intent);
        finish();
    }
}
