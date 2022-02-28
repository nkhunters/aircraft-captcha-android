package com.techaircraft.captcha.Fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textview.MaterialTextView;
import com.techaircraft.captcha.Api.RetrofitClient;
import com.techaircraft.captcha.CaptchaActivity;
import com.techaircraft.captcha.Login;
import com.techaircraft.captcha.Models.DefaultResponse;
import com.techaircraft.captcha.ModelsNew.CaptchaResponse;
import com.techaircraft.captcha.R;
import com.techaircraft.captcha.databinding.CaptchaCardFragmentBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class CaptchaCardFragment extends Fragment {

    CaptchaCardFragmentBinding binding;

    private ImageView captchaImage;
    private MaterialTextView skipCount;
    private MaterialTextView rightCount;
    private MaterialTextView wrongCount;
    private MaterialTextView totalEarning;
    private MaterialTextView timerText;
    private MaterialTextView captchaType;
    private EditText captchaText;

    private MaterialButton skipButton;
    private MaterialButton submitButton;
    private MaterialButton nextOrder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = CaptchaCardFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        captchaImage = binding.captchaImage;
        skipCount = binding.skipCaptchaCount;
        rightCount = binding.rightCaptchaCount;
        wrongCount = binding.wrongCaptchaCount;
        totalEarning = binding.balance;
        timerText = binding.timerText;
        captchaType = binding.captchaType;

        skipButton = binding.btnContinue;
        submitButton = binding.btnContinue;
        nextOrder = binding.nextOrder;

        return view;
    }

}