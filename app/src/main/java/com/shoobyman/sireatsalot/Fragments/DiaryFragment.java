package com.shoobyman.sireatsalot.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.shoobyman.sireatsalot.Adapters.MealSummaryRecyclerViewAdapter;
import com.shoobyman.sireatsalot.MainViewModel;
import com.shoobyman.sireatsalot.POJOs.FoodEntry;
import com.shoobyman.sireatsalot.R;
import com.shoobyman.sireatsalot.SearchFoodActivity;
import com.shoobyman.sireatsalot.databinding.FragmentDiaryBinding;

import java.util.ArrayList;
import java.util.Date;

public class DiaryFragment extends Fragment
        implements MealSummaryRecyclerViewAdapter.MealItemClickListener,
                MainViewModel.DbDataListener,
                CalendarDialog.DateChangeListener {

    private final String TAG = this.getClass().getSimpleName();

    FragmentDiaryBinding mBinding;
    private MainViewModel mData;
    MealSummaryRecyclerViewAdapter breakfastAdapter;
    MealSummaryRecyclerViewAdapter lunchAdapter;
    MealSummaryRecyclerViewAdapter dinnerAdapter;
    MealSummaryRecyclerViewAdapter snackAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentDiaryBinding.inflate(inflater, container, false);
        mData = new ViewModelProvider(getActivity()).get(MainViewModel.class);
        init();

        return mBinding.getRoot();
    }

    public void init() {
        mData.initForDiaryFragment(this);

        mBinding.selectedDate.setText(mData.getFormattedDate());
        mBinding.selectedDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment calendarFrag = new CalendarDialog(mData.getDate(), DiaryFragment.this);
                calendarFrag.show(getParentFragmentManager(), "datePicker");
            }
        });

        breakfastAdapter = new MealSummaryRecyclerViewAdapter(getContext(), mData.breakfastList, this);
        lunchAdapter = new MealSummaryRecyclerViewAdapter(getContext(), mData.lunchList, this);
        dinnerAdapter = new MealSummaryRecyclerViewAdapter(getContext(), mData.dinnerList, this);
        snackAdapter = new MealSummaryRecyclerViewAdapter(getContext(), mData.snackList, this);
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
                startFoodSearch(getString(R.string.breakfast_item), mData.getFormattedDate());
            }
        });
        mBinding.buttonAddLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFoodSearch(getString(R.string.lunch_item), mData.getFormattedDate());
            }
        });
        mBinding.buttonAddDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFoodSearch(getString(R.string.dinner_item), mData.getFormattedDate());
            }
        });
        mBinding.buttonAddSnack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFoodSearch(getString(R.string.snack_item), mData.getFormattedDate());
            }
        });
    }

    public void startFoodSearch(String mealType, String date) {
        Intent intent = new Intent(getContext(), SearchFoodActivity.class);
        intent.putExtra(MainViewModel.INTENT_EXTRA_MEAL_TYPE, mealType);
        intent.putExtra(MainViewModel.INTENT_EXTRA_DATE, date);
        startActivity(intent);
    }

    @Override
    public void onMealItemClick(FoodEntry foodEntry) {
        Toast.makeText(getContext(), "Clicked on " + foodEntry.getFoodName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onMealItemLongClick(FoodEntry foodEntry) {
        String[] options = {"Delete Item", "Move Item"};
        new MaterialAlertDialogBuilder(getContext())
                .setTitle("Actions")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getContext(), "Selected " + which, Toast.LENGTH_SHORT).show();
                    }
                })
                .show();
    }

    @Override
    public void onRetrievedMealDataSuccess(ArrayList<FoodEntry> breakfastEntries, ArrayList<FoodEntry> lunchEntries, ArrayList<FoodEntry> dinnerEntries, ArrayList<FoodEntry> snackEntries) {
        breakfastAdapter.updateMealEntries(breakfastEntries);
        lunchAdapter.updateMealEntries(lunchEntries);
        dinnerAdapter.updateMealEntries(dinnerEntries);
        snackAdapter.updateMealEntries(snackEntries);
        breakfastAdapter.notifyDataSetChanged();
        lunchAdapter.notifyDataSetChanged();
        dinnerAdapter.notifyDataSetChanged();
        snackAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRetrievedMealDataFailed() {
        Toast.makeText(getContext(), "Failed retrieving meal entry data", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDateChanged(Date date) {
        mData.setDate(date);
        mData.setListenerDateFilter(this, mData.getFormattedDate());
        mBinding.selectedDate.setText(mData.getFormattedDate());
    }
}
