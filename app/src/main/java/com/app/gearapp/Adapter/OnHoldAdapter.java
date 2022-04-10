package com.app.gearapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.gearapp.Activity.OnHoldActivity;
import com.app.gearapp.Fragment.On_Hold_Fragment;
import com.app.gearapp.Model.TodoModel;
import com.app.gearapp.R;

import java.util.ArrayList;

public class OnHoldAdapter extends RecyclerView.Adapter<OnHoldAdapter.ViewHolder> {
    Context context;
    ArrayList<TodoModel> todoModels;

    public OnHoldAdapter(Context context, ArrayList<TodoModel> todoModels) {
        this.context = context;
        this.todoModels = todoModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.onhold_recylerview, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       holder.address.setText(todoModels.get(position).getAddress());
       holder.id.setText(todoModels.get(position).getId());

        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OnHoldActivity.class);
                intent.putExtra("onhold","onholdactivity");
                intent.putExtra("mobile",todoModels.get(position).getContact_phone());
                intent.putExtra("createDate",todoModels.get(position).getCreated_at());
                intent.putExtra("upDateTime",todoModels.get(position).getUpdated_at());
                intent.putExtra("id",todoModels.get(position).getId());
                intent.putExtra("address",todoModels.get(position).getAddress());
                intent.putExtra("name",todoModels.get(position).getContact_name());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return todoModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout linear;
        TextView id,address;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            linear = itemView.findViewById(R.id.linear);
            address = itemView.findViewById(R.id.address);
            id = itemView.findViewById(R.id.id);
        }
    }
}
