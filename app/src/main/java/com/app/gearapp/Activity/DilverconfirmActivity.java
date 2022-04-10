package com.app.gearapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.gearapp.Helpr.GetDataService;
import com.app.gearapp.Helpr.PrefrenceManager;
import com.app.gearapp.Helpr.RetrofitClintanse;
import com.app.gearapp.R;
import com.app.gearapp.databinding.ActivityDilverconfirmBinding;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DilverconfirmActivity extends AppCompatActivity {
    public static Activity activity;
    ActivityDilverconfirmBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDilverconfirmBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;

        binding.id.setText(new PrefrenceManager(getApplicationContext()).getQrId());
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.reciver.setText(getIntent().getStringExtra("remarks"));

        binding.backtolist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DasbordActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });

        binding.deliverydetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OrderInfoActivity.class);
                intent.putExtra("orderInfor","dilveryconfirm");
                startActivity(intent);
                finishAffinity();
            }
        });

        binding.addmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddMarkActivity.class);
                intent.putExtra("addmarks", "dilverconfirm");
                startActivity(intent);
            }
        });
    }

}