package com.techaircraft.captcha.Fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.techaircraft.captcha.databinding.CaptchaCardFragmentBinding;
import com.techaircraft.captcha.databinding.OrderHistoryFragmentBinding;

public class OrderHistoryFragment extends Fragment {

    OrderHistoryFragmentBinding binding;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = OrderHistoryFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();


        return view;
    }
}