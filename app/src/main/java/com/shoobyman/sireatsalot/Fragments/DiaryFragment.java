package com.shoobyman.sireatsalot.Fragments;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.shoobyman.sireatsalot.ContentActivity;
import com.shoobyman.sireatsalot.DiaryViewModel;
import com.shoobyman.sireatsalot.SearchFoodActivity;
import com.shoobyman.sireatsalot.Utils.NetworkUtils;
import com.shoobyman.sireatsalot.databinding.FragmentDiaryBinding;

import org.json.JSONObject;

import java.util.Random;

public class DiaryFragment extends Fragment {

    private final String TAG = this.getClass().getSimpleName();

    FragmentDiaryBinding mBinding;
    private DiaryViewModel mData;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentDiaryBinding.inflate(inflater, container, false);
        mData = new ViewModelProvider(getActivity()).get(DiaryViewModel.class);
        init();

        return mBinding.getRoot();
    }

    public void init() {
        mBinding.buttonAddBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.getFoodById("", getContext());
                startFoodSearch();
            }
        });
        mBinding.buttonAddLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFoodSearch();
            }
        });
        mBinding.buttonAddDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFoodSearch();
            }
        });
        mBinding.buttonAddSnack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFoodSearch();
            }
        });
    }

    private String nonce(){
        int randomNum = new Random().nextInt((10000000 - 1) + 1) + 1;
        return Integer.toString(randomNum);
    }


    public void startFoodSearch() {
        Intent intent = new Intent(getContext(), SearchFoodActivity.class);
        startActivityForResult(intent, ContentActivity.REQUEST_CODE_FIND_FOOD);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ContentActivity.RESULT_CODE_FOUND_FOOD) {
            Toast.makeText(getContext(), data.getStringExtra("MESSAGE"), Toast.LENGTH_LONG).show();
        } else if (resultCode == ContentActivity.RESULT_CODE_FOOD_NOT_FOUND) {
            //Dunno lol
        }
    }
}
