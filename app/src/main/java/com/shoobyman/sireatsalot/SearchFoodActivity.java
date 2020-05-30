package com.shoobyman.sireatsalot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import com.shoobyman.sireatsalot.databinding.ActivitySearchFoodBinding;

public class SearchFoodActivity extends AppCompatActivity {

    ActivitySearchFoodBinding mBinding;
    private DiaryViewModel mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_search_food);
        mData = new ViewModelProvider(this).get(DiaryViewModel.class);
        returnResult();
    }

    public void returnResult() {
        Intent intent = new Intent();
        intent.putExtra("MESSAGE", "Food1 so yummy");
        setResult(ContentActivity.RESULT_CODE_FOUND_FOOD, intent);
        finish();
    }
}
