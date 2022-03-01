package com.app.gearapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.gearapp.R;
import com.app.gearapp.databinding.ActivityOrderInfoBinding;

public class OrderInfoActivity extends AppCompatActivity {
    ActivityOrderInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOrderInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),DetailsActivity.class);
                startActivity(intent);
            }
        });

    }
}