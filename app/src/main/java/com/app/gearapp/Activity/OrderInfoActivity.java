package com.app.gearapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.gearapp.Model.TodoModel;
import com.app.gearapp.R;
import com.app.gearapp.databinding.ActivityOrderInfoBinding;

public class OrderInfoActivity extends AppCompatActivity {
    ActivityOrderInfoBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                startActivity(intent);
            }
        });
        binding.addmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddMarkActivity.class);
                startActivity(intent);
            }
        });


        binding.id.setText(getIntent().getStringExtra("id"));
        binding.address.setText(getIntent().getStringExtra("address"));
        binding.mobile.setText("Tel: "+getIntent().getStringExtra("mobile"));
        binding.deliveredTime.setText(getIntent().getStringExtra("delivery_date"));

    }
}