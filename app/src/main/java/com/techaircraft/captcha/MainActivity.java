package com.techaircraft.captcha;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        startLogin();
    }

    private void startLogin() {

        startActivity(new Intent(MainActivity.this, Login.class));
    }
}
