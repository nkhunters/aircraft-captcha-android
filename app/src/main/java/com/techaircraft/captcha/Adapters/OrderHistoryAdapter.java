package com.techaircraft.captcha.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techaircraft.captcha.Models.Order;
import com.techaircraft.captcha.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder> {

    private ArrayList<Order> dataList;
    private Context mContext;

    public OrderHistoryAdapter(ArrayList<Order> dataList, Context mContext) {
        this.dataList = dataList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.order_history_card, parent, false);
        return new OrderHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHistoryViewHolder holder, int position) {

        Order order = dataList.get(position);

        DateFormat format = new SimpleDateFormat("yyyy-MM-dd H:m:s", Locale.US);
        DateFormat simpleFormat = new SimpleDateFormat("dd-MMMM-yyyy h:m:s a", Locale.US);
        Date order_date = null, approval_date = null;
        try {
            order_date = format.parse(order.getOrder_date());
            approval_date = format.parse(order.getApproval_date());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.order_date.setText("Order On - "+simpleFormat.format(order_date));
        holder.total_earning.setText("Total Earning - "+order.getTotal_earning()+" $");
        if(order.getStatus().equals("1"))
        {
            holder.payment_status.setText("Paid");
            holder.payment_status.setTextColor(mContext.getResources().getColor(R.color.green));
        }
        else {
            holder.payment_status.setText("Not Paid");
            holder.payment_status.setTextColor(mContext.getResources().getColor(R.color.red));
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class OrderHistoryViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.order_date)
        TextView order_date;

        @BindView(R.id.total_earning)
        TextView total_earning;

        @BindView(R.id.payment_status)
        TextView payment_status;

        public OrderHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
