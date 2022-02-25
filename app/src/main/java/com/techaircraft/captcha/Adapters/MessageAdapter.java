package com.techaircraft.captcha.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.techaircraft.captcha.Models.Message;
import com.techaircraft.captcha.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

    private ArrayList<Message> dataList;

    public MessageAdapter(ArrayList<Message> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.message_card, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {

        Message message = dataList.get(position);
        holder.title.setText(message.getTitle());
        holder.body.setText(message.getBody());
        holder.date_time.setText(message.getDate_time());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView title;

        @BindView(R.id.body)
        TextView body;

        @BindView(R.id.date_time)
        TextView date_time;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
