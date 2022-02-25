package com.techaircraft.captcha;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.techaircraft.captcha.Adapters.MessageAdapter;
import com.techaircraft.captcha.Api.RetrofitClient;
import com.techaircraft.captcha.Models.Message;
import com.techaircraft.captcha.Models.MessageResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewMessages extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_messages);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("\t\tView Messages");

        getData();
    }

    private void getData() {

        Call<MessageResponse> call = RetrofitClient.getInstance().getApi().getMessages();
        final ProgressDialog progressDialog = new ProgressDialog(ViewMessages.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        call.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {
                progressDialog.dismiss();
                ArrayList<Message> messageArrayList = response.body().getMessages();
                if(!messageArrayList.isEmpty()){
                    setDataInRecyclerView(messageArrayList);
                }
                else {
                    AlertDialog alertDialog = new AlertDialog.Builder(ViewMessages.this)
                            .setCancelable(false)
                            .setMessage("No Messages yet.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    finish();
                                }
                            }).create();
                    alertDialog.show();
                }
            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {

                progressDialog.dismiss();
                Log.d("niraj", "Failed");
            }
        });
    }

    private void setDataInRecyclerView(ArrayList<Message> messageArrayList) {

        MessageAdapter adapter = new MessageAdapter(messageArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewMessages.this));
        recyclerView.setAdapter(adapter);
    }
}
