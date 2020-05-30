package com.shoobyman.sireatsalot.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.shoobyman.sireatsalot.DiaryViewModel;
import com.shoobyman.sireatsalot.databinding.FragmentDiaryBinding;

public class DiaryFragment extends Fragment {

    private final String TAG = this.getClass().getSimpleName();

    FragmentDiaryBinding mBinding;
    private DiaryViewModel mData;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentDiaryBinding.inflate(inflater, container, false);
        mData = new ViewModelProvider(getActivity()).get(DiaryViewModel.class);

        return mBinding.getRoot();
    }
}
