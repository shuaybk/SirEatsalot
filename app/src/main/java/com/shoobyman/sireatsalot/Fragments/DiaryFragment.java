package com.shoobyman.sireatsalot.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.shoobyman.sireatsalot.Adapters.MealSummaryRecyclerViewAdapter;
import com.shoobyman.sireatsalot.MainViewModel;
import com.shoobyman.sireatsalot.POJOs.FoodEntry;
import com.shoobyman.sireatsalot.R;
import com.shoobyman.sireatsalot.SearchFoodActivity;
import com.shoobyman.sireatsalot.databinding.FragmentDiaryBinding;

import java.util.ArrayList;
import java.util.Random;

public class DiaryFragment extends Fragment implements MealSummaryRecyclerViewAdapter.MealItemClickListener {

    private final String TAG = this.getClass().getSimpleName();

    FragmentDiaryBinding mBinding;
    private MainViewModel mData;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentDiaryBinding.inflate(inflater, container, false);
        mData = new ViewModelProvider(getActivity()).get(MainViewModel.class);
        init();

        return mBinding.getRoot();
    }

    public void init() {
        MealSummaryRecyclerViewAdapter breakfastAdapter = new MealSummaryRecyclerViewAdapter(getContext(), new ArrayList<FoodEntry>(), this);
        MealSummaryRecyclerViewAdapter lunchAdapter = new MealSummaryRecyclerViewAdapter(getContext(), new ArrayList<FoodEntry>(), this);
        MealSummaryRecyclerViewAdapter dinnerAdapter = new MealSummaryRecyclerViewAdapter(getContext(), new ArrayList<FoodEntry>(), this);
        MealSummaryRecyclerViewAdapter snackAdapter = new MealSummaryRecyclerViewAdapter(getContext(), new ArrayList<FoodEntry>(), this);
        mBinding.breakfastFoodList.setAdapter(breakfastAdapter);
        mBinding.lunchFoodList.setAdapter(lunchAdapter);
        mBinding.dinnerFoodList.setAdapter(dinnerAdapter);
        mBinding.snackFoodList.setAdapter(snackAdapter);
        mBinding.breakfastFoodList.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.lunchFoodList.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.dinnerFoodList.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.snackFoodList.setLayoutManager(new LinearLayoutManager(getContext()));

        mBinding.buttonAddBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFoodSearch(getString(R.string.breakfast_item));
            }
        });
        mBinding.buttonAddLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFoodSearch(getString(R.string.lunch_item));
            }
        });
        mBinding.buttonAddDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFoodSearch(getString(R.string.dinner_item));
            }
        });
        mBinding.buttonAddSnack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFoodSearch(getString(R.string.snack_item));
            }
        });
    }

    private String nonce(){
        int randomNum = new Random().nextInt((10000000 - 1) + 1) + 1;
        return Integer.toString(randomNum);
    }


    public void startFoodSearch(String mealType) {
        Intent intent = new Intent(getContext(), SearchFoodActivity.class);
        intent.putExtra(MainViewModel.INTENT_EXTRA_MEAL_TYPE, mealType);
        startActivity(intent);
    }

    @Override
    public void onMealItemClick(FoodEntry foodEntry) {
        Toast.makeText(getContext(), "Clicked on " + foodEntry.getFoodName(), Toast.LENGTH_SHORT).show();
    }
}
