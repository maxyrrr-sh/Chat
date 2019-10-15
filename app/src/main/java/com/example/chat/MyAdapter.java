package com.example.chat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.LinkedList;

class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private LinkedList<UserMessage> messages;
    private LayoutInflater inflater;

    public MyAdapter(LinkedList<UserMessage> messages) {
        this.messages = messages;
    }

    public void setMessages(LinkedList<UserMessage> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.massageitem, parent,false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.sender.setText(messages.get(position).name);
        holder.message.setText(messages.get(position).message);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView sender;
        TextView message;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            sender = itemView.findViewById(R.id.textView);
            message = itemView.findViewById(R.id.textView1);
        }
    }
}
