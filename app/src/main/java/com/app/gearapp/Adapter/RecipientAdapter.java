package com.app.gearapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.gearapp.Activity.ProofOfDeliveryActivity;
import com.app.gearapp.Model.RecipientModel;
import com.app.gearapp.R;
import com.balysv.materialripple.MaterialRippleLayout;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class RecipientAdapter extends RecyclerView.Adapter<RecipientAdapter.ViewHolder> {
    Context context;
    ArrayList<RecipientModel> recipientModels;

    public RecipientAdapter(Context context, ArrayList<RecipientModel> recipientModels) {
        this.context = context;
        this.recipientModels = recipientModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view1=inflater.inflate(R.layout.recipient_reylerview,null);
        return new ViewHolder(view1);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (recipientModels.get(position).getRecipient_type().equalsIgnoreCase("Recipient")){
            holder.card.setText(recipientModels.get(position).getRecipient());
        }else {
            holder.card.setVisibility(View.GONE);
        }

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ProofOfDeliveryActivity.class);
                intent.putExtra("recipient_type",recipientModels.get(position).getRecipient());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return recipientModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MaterialButton card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            card=itemView.findViewById(R.id.card);
        }
    }
}
