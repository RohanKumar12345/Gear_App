package com.app.gearapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.gearapp.Activity.ScannerActivity;
import com.app.gearapp.Model.TodoModel;
import com.app.gearapp.R;

import java.util.ArrayList;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    Context context;
    ArrayList<TodoModel> todoModels;

    public ToDoAdapter(Context context, ArrayList<TodoModel> todoModels) {
        this.context = context;
        this.todoModels = todoModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.todo_recylview,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.id.setText(todoModels.get(position).getId());
        holder.address.setText(todoModels.get(position).getAddress());

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ScannerActivity.class);
                intent.putExtra("id",todoModels.get(position).getId());
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
        LinearLayout card;
        TextView id,address;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            card=itemView.findViewById(R.id.card);
            id=itemView.findViewById(R.id.id);
            address=itemView.findViewById(R.id.address);
        }


    }
}
