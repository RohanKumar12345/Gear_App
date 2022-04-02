package com.app.gearapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.gearapp.Activity.OrderInfoActivity;
import com.app.gearapp.Model.TodoModel;
import com.app.gearapp.R;

import java.util.ArrayList;
import java.util.List;

public class DeliveredAdapter extends RecyclerView.Adapter<DeliveredAdapter.ViewHolder> {
    Context context;
    ArrayList<TodoModel> todoModels;

    public DeliveredAdapter(Context context, ArrayList<TodoModel> todoModels) {
        this.context = context;
        this.todoModels = todoModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.todo_recylview,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.id.setText(todoModels.get(position).getId());
        holder.address.setText(todoModels.get(position).getAddress());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, OrderInfoActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id", todoModels.get(position).getId());
                intent.putExtra("address", todoModels.get(position).getAddress());
                intent.putExtra("name", todoModels.get(position).getContact_name());
                intent.putExtra("mobile", todoModels.get(position).getContact_phone());
                intent.putExtra("name", todoModels.get(position).getContact_name());
                intent.putExtra("delivery_date", todoModels.get(position).getDelivery_date());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return todoModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout card;
        TextView id,address;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            card=itemView.findViewById(R.id.card);
            address=itemView.findViewById(R.id.address);
            id=itemView.findViewById(R.id.id);
        }
    }
}
