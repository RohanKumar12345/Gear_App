package com.app.gearapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.app.gearapp.R;
import com.app.gearapp.databinding.ActivityDetailsBinding;

public class DetailsActivity extends AppCompatActivity {
ActivityDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    binding=ActivityDetailsBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    binding.back.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    });
    }
}