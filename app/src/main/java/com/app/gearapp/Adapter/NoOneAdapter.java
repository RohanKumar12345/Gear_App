package com.app.gearapp.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.gearapp.Activity.ProofOfDeliveryActivity;
import com.app.gearapp.Model.NoOneModel;
import com.app.gearapp.Model.RecipientModel;
import com.app.gearapp.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class NoOneAdapter extends RecyclerView.Adapter<NoOneAdapter.ViewHolder> {
    Context context;
    ArrayList<NoOneModel> noOneModels;

    public NoOneAdapter(Context context, ArrayList<NoOneModel> noOneModels) {
        this.context = context;
        this.noOneModels = noOneModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view1=inflater.inflate(R.layout.recipient_reylerview,null);
        return new ViewHolder(view1);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        if (noOneModels.get(position).getRecipient_type().equalsIgnoreCase("No One")){
            holder.card.setText(noOneModels.get(position).getRecipient());
        }else {
            holder.card.setVisibility(View.GONE);
        }
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ProofOfDeliveryActivity.class);
                intent.putExtra("recipient_type",noOneModels.get(position).getRecipient());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return noOneModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MaterialButton card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            card=itemView.findViewById(R.id.card);
        }
    }
}
