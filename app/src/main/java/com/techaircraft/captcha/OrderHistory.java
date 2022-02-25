package com.techaircraft.captcha;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.techaircraft.captcha.Adapters.OrderHistoryAdapter;
import com.techaircraft.captcha.Api.RetrofitClient;
import com.techaircraft.captcha.Models.Order;
import com.techaircraft.captcha.Models.OrderHistoryResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderHistory extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    String user_id, total_earning;
    TextView txt_user_id, txt_total_earning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        user_id = getIntent().getStringExtra("user_id");
        total_earning = getIntent().getStringExtra("total_earning");


        txt_user_id = toolbar.findViewById(R.id.user_id);
        txt_total_earning = toolbar.findViewById(R.id.total_earning);

        txt_user_id.setText(user_id);
        txt_total_earning.setText("Total Earning - "+total_earning+" $");
        getData();
    }

    private void getData() {

        Call<OrderHistoryResponse> call = RetrofitClient.getInstance().getApi().getOrderHistory(user_id);
        final ProgressDialog dialog = new ProgressDialog(OrderHistory.this);
        dialog.setCancelable(false);
        dialog.setMessage("Please Wait...");
        dialog.show();

        call.enqueue(new Callback<OrderHistoryResponse>() {
            @Override
            public void onResponse(Call<OrderHistoryResponse> call, Response<OrderHistoryResponse> response) {
                dialog.cancel();
                ArrayList<Order> dataList = response.body().getOrders();
                if(!dataList.isEmpty())
                {
                    setDataInRecyclerView(dataList);
                }
                else {
                    AlertDialog alertDialog = new AlertDialog.Builder(OrderHistory.this)
                            .setCancelable(false)
                            .setMessage("No orders completed yet.")
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
            public void onFailure(Call<OrderHistoryResponse> call, Throwable t) {

                dialog.dismiss();
                dialog.cancel();
                Log.d("niraj", "Failed");
            }
        });
    }

    private void setDataInRecyclerView(ArrayList<Order> dataList) {

        OrderHistoryAdapter adapter = new OrderHistoryAdapter(dataList, OrderHistory.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(OrderHistory.this));
        recyclerView.setAdapter(adapter);
    }
}
