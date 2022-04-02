package com.app.gearapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.app.gearapp.Adapter.ToDoAdapter;
import com.app.gearapp.Helpr.GetDataService;
import com.app.gearapp.Helpr.RetrofitClintanse;
import com.app.gearapp.Model.TodoModel;
import com.app.gearapp.R;
import com.app.gearapp.databinding.ActivityOnHoldBinding;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnHoldActivity extends AppCompatActivity {
    ActivityOnHoldBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityOnHoldBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.addmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),AddMarkActivity.class);
                startActivity(intent);
            }
        });

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.address.setText(getIntent().getStringExtra("address"));
        binding.id.setText(getIntent().getStringExtra("id"));
        binding.mobile.setText("Tel: "+getIntent().getStringExtra("mobile"));
        binding.createTime.setText(getIntent().getStringExtra("createDate"));
        binding.upDateTime.setText(getIntent().getStringExtra("upDateTime"));
        binding.name.setText(getIntent().getStringExtra("name"));

        binding.message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", getIntent().getStringExtra("mobile"), null)));
            }
        });
       binding.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri u = Uri.parse("tel:" + getIntent().getStringExtra("mobile"));
                Intent i = new Intent(Intent.ACTION_DIAL, u);
                try {
                    startActivity(i);
                } catch (SecurityException s) {
                    Toast.makeText(OnHoldActivity.this, "An error occurred", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}