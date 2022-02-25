package com.techaircraft.captcha;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.techaircraft.captcha.Api.RetrofitClient;
import com.techaircraft.captcha.FirebaseMessaging.Constants;
import com.techaircraft.captcha.ModelsNew.LoginResponse;
import com.techaircraft.captcha.ModelsNew.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.FirebaseMessaging;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.user_id)
    EditText user_id;

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.login)
    Button login;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.terms_checkbox)
    CheckBox termsCheckbox;

    public String str_user_id, str_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("\t\tLogin");

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(Constants.CHANNEL_ID, Constants.CHANNEL_NAME, importance);
            mChannel.setDescription(Constants.CHANNEL_DESCRIPTION);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(mChannel);
        }

        FirebaseMessaging.getInstance().subscribeToTopic("messages");
        //MyNotificationManager.getInstance(this).displayNotification("Greetings", "Hello how are you?");

        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        str_user_id = user_id.getText().toString().trim();
        str_password = password.getText().toString().trim();

        if (TextUtils.isEmpty(str_user_id)) {

            user_id.setError("Please Enter User Id.");
            user_id.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(str_password)) {

            password.setError("Please Enter Password");
            password.requestFocus();
            return;
        }

        if (!termsCheckbox.isChecked()) {
            //termsCheckbox.setError("Please agree our terms and conditions to continue");
            termsCheckbox.requestFocus();
            new AlertDialog.Builder(Login.this)
                    .setMessage("Please agree our terms and conditions to continue.")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create()
                    .show();
            return;
        }

        final ProgressDialog dialog = new ProgressDialog(Login.this);
        dialog.setMessage("Please Wait...");
        dialog.setCancelable(false);
        dialog.show();

        String imei = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);

        Call<LoginResponse> call = RetrofitClient.getInstance().getApi().login(str_user_id, str_password, imei);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                dialog.dismiss();
                if (response.code() == 201) {

                    User user = response.body().getMessage();
                    if (user.getMessage().equalsIgnoreCase("Device Not Matched")) {

                        Snackbar.make(findViewById(R.id.main_layout), "User id already registered with another device", Snackbar.LENGTH_LONG).show();
                    } else {

                        String message = "Id already registered and activated.";
                        if (getSharedPreferences("my_prefs", MODE_PRIVATE).getInt("login_msg", 0) != 1)
                            message = "This id is registered on this device and cannot be used on any other device. If you want to use this id on another device, contact company.";
                        new AlertDialog.Builder(Login.this)
                                .setMessage(message)
                                .setCancelable(false)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

                                        SharedPreferences preferences = getSharedPreferences("my_prefs", MODE_PRIVATE);
                                        preferences.edit().putInt("login_msg", 1).apply();

                                        String captcha_time = user.getCaptcha_time();
                                        String captcha_count = user.getCaptcha_count();
                                        String captcha_rate = user.getCaptcha_rate();
                                        String auto_approve = user.getAuto_approve();
                                        String total_earning = user.getTotal_earning();

                                        Intent intent = new Intent(Login.this, CaptchaActivity.class);
                                        intent.putExtra("user_id", str_user_id);
                                        intent.putExtra("captcha_time", captcha_time);
                                        intent.putExtra("extra_time", user.getExtra_time());
                                        intent.putExtra("captcha_count", captcha_count);
                                        intent.putExtra("captcha_rate", captcha_rate);
                                        intent.putExtra("auto_approve", auto_approve);
                                        intent.putExtra("total_earning", total_earning);
                                        startActivity(intent);
                                        finish();
                                    }
                                }).create().show();
                    }
                } else {

                    Snackbar.make(findViewById(R.id.main_layout), "Invalid username or password Please try again!!", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

                Log.d("niraj", t.getMessage());
                dialog.cancel();
                Snackbar.make(findViewById(R.id.main_layout), "Something went wrong Please check your internet!!", Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
