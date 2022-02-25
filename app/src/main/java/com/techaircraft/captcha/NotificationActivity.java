package com.techaircraft.captcha;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NotificationActivity extends AppCompatActivity {

    String title, data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        showNotificationCard();
    }

    private void showNotificationCard() {

        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                if(key.equals("str_title"))
                    title = getIntent().getExtras().getString(key);
                if(key.equals("str_body"))
                    data = getIntent().getExtras().getString(key);
                String value = getIntent().getExtras().getString(key);
                Log.d("niraj", "Key: " + key + " Value: " + value);
            }
        }

        if (title == null || title.equals(""))
        {
            title = getIntent().getStringExtra("title") == null ? "" : getIntent().getStringExtra("title");
            data = getIntent().getStringExtra("data");
        }



        final Dialog dialog = new Dialog(NotificationActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.notification_card);
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();

        TextView txtTitle = dialog.findViewById(R.id.title);
        TextView txtBody = dialog.findViewById(R.id.body);
        TextView txtOk = dialog.findViewById(R.id.ok_btn);
        ImageView close_btn = dialog.findViewById(R.id.close_btn);

        txtTitle.setText(title);
        txtBody.setText(data);

        txtOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                Intent i = new Intent(NotificationActivity.this, Login.class);
                startActivity(i);
                finish();
            }
        });

        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                Intent i = new Intent(NotificationActivity.this, Login.class);
                startActivity(i);
                finish();
            }
        });
    }
}
