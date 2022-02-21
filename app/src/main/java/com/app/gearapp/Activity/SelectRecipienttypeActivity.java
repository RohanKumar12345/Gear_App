package com.app.gearapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.app.gearapp.Adapter.RecipientAdapter;
import com.app.gearapp.Adapter.ToDoAdapter;
import com.app.gearapp.R;
import com.app.gearapp.databinding.ActivitySelectRecipienttypeBinding;

public class SelectRecipienttypeActivity extends AppCompatActivity {


    ActivitySelectRecipienttypeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectRecipienttypeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        binding.recipietnRecyclerview.setLayoutManager(layoutManager);
        binding.recipietnRecyclerview.setHasFixedSize(true);
        RecipientAdapter adapter = new RecipientAdapter(getApplicationContext());
        binding.recipietnRecyclerview.setAdapter(adapter);

        RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(getApplicationContext(), 3);
        binding.ononeRecyclerview.setLayoutManager(layoutManager1);
        binding.ononeRecyclerview.setHasFixedSize(true);
        RecipientAdapter adapter1 = new RecipientAdapter(getApplicationContext());
        binding.ononeRecyclerview.setAdapter(adapter1);


    }
}