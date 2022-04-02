package com.app.gearapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.gearapp.Model.RemarkModel;
import com.app.gearapp.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;

public class AddMarkAdapter extends RecyclerView.Adapter<AddMarkAdapter.ViewHolder> {

    Context context;
    ArrayList<RemarkModel> remarkModels;

    public AddMarkAdapter(Context context, ArrayList<RemarkModel> remarkModels) {
        this.context = context;
        this.remarkModels = remarkModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.addmark_recylview,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.remarkText.setText(remarkModels.get(position).getRemarks());

    }

    @Override
    public int getItemCount() {
        return remarkModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MaterialButton remarkText;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            remarkText=itemView.findViewById(R.id.remarkText);
        }
    }
}
