package com.app.gearapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.gearapp.R;
import com.balysv.materialripple.MaterialRippleLayout;

public class RecipientAdapter extends RecyclerView.Adapter<RecipientAdapter.ViewHolder> {
    Context context;

    public RecipientAdapter(Context context) {
        this.context = context;
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

        MaterialRippleLayout.on(holder.card)
                .rippleColor(Color.RED)
                .create();

    }

    @Override
    public int getItemCount() {
        return 6;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            card=itemView.findViewById(R.id.card);
        }
    }
}
