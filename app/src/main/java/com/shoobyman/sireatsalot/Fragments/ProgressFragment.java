package com.shoobyman.sireatsalot.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.shoobyman.sireatsalot.MainViewModel;
import com.shoobyman.sireatsalot.databinding.FragmentProgressBinding;

public class ProgressFragment extends Fragment {

    private final String TAG = this.getClass().getSimpleName();

    FragmentProgressBinding mBinding;
    private MainViewModel mData;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentProgressBinding.inflate(inflater, container, false);
        mData = new ViewModelProvider(getActivity()).get(MainViewModel.class);

        return mBinding.getRoot();
    }
}
