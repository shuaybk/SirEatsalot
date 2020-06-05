package com.shoobyman.sireatsalot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.shoobyman.sireatsalot.POJOs.Food;
import com.shoobyman.sireatsalot.databinding.ActivityAddFoodBinding;

public class AddFoodActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    ActivityAddFoodBinding mBinding;
    private MainViewModel mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_food);
        mData = new ViewModelProvider(this).get(MainViewModel.class);

        init();
    }

    private void init() {
        String foodId = getIntent().getStringExtra(SearchFoodActivity.INTENT_EXTRA_FOOD_ID);
        System.out.println("The extra ID is " + foodId);
        if (mData.currFood == null || !mData.currFood.getId().equals(foodId)) {
            mData.getFoodById(foodId, this);
        } else {
            displayFoodDetails();
        }
    }

    public void displayFoodDetails() {
        mBinding.foodDetailsText.setText("Name = " + mData.currFood.getName() + ", Type = " + mData.currFood.getType());
    }


}
