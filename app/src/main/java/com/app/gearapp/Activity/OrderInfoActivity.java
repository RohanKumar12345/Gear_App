package com.app.gearapp.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.gearapp.Helpr.Contraints;
import com.app.gearapp.Helpr.GetDataService;
import com.app.gearapp.Helpr.PrefrenceManager;
import com.app.gearapp.Helpr.RetrofitClintanse;
import com.app.gearapp.Model.TodoModel;
import com.app.gearapp.R;
import com.app.gearapp.databinding.ActivityOrderInfoBinding;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class OrderInfoActivity extends AppCompatActivity {
    ActivityOrderInfoBinding binding;
    public static Activity activity;
    String id = "";
    PrefrenceManager prefrenceManager;
    String dilveryActivty = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderInfoBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;

        prefrenceManager = new PrefrenceManager(getApplicationContext());
        dilveryActivty = getIntent().getStringExtra("orderInfor");


        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                intent.putExtra("id", new PrefrenceManager(getApplicationContext()).getProductid());
                intent.putExtra("orderinfor", "dilveryinfor");
                startActivity(intent);
            }
        });
        binding.addmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddMarkActivity.class);
                intent.putExtra("addmarks", "orderinfor");
                startActivity(intent);
            }
        });

        if (dilveryActivty.equals("dilvery")) {
            id = getIntent().getStringExtra("id");
            prefrenceManager.setProductid(id);

            boolean connected = false;
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                jobDetails(new PrefrenceManager(getApplicationContext()).getToken(), new PrefrenceManager(getApplicationContext()).getProductid());


                connected = true;
            } else

                alirtDilog();
            connected = false;

        } else if (dilveryActivty.equals("remark")) {

            boolean connected = false;
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                jobDetails(new PrefrenceManager(getApplicationContext()).getToken(), new PrefrenceManager(getApplicationContext()).getProductid());


                connected = true;
            } else

                alirtDilog();
            connected = false;
        } else if (dilveryActivty.equals("dilveryconfirm")) {

            boolean connected = false;
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                jobDetails(new PrefrenceManager(getApplicationContext()).getToken(), new PrefrenceManager(getApplicationContext()).getProductid());


                connected = true;
            } else

                alirtDilog();
            connected = false;

        }

    }

    public void alirtDilog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(OrderInfoActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.network_layout, viewGroup, false);
        builder.setView(dialogView);
        builder.setCancelable(false);
        AlertDialog alertDialog = builder.create();

        TextView ok = dialogView.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });

        alertDialog.show();
    }


    public void jobDetails(String token, String id) {
        GetDataService getDataService = RetrofitClintanse.getClient(token).create(GetDataService.class);
        Call<JsonObject> call = getDataService.jobDetail(id);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                    String res = jsonObject.getString("message");

                    if (res.equals("Success")) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        binding.address.setText(jsonObject1.getString("address"));
                        binding.mobile.setText("Tel: " + jsonObject1.getString("contact_phone"));
                        binding.name.setText(jsonObject1.getString("contact_name") + " " + jsonObject1.getString("last_name"));
                        binding.deliveredTime.setText(jsonObject1.getString("delivery_date"));
                        binding.id.setText(jsonObject1.getString("gearId"));

                        if (jsonObject1.getString("remarks").equals("")) {
                            binding.addmarkText.setText("Add");

                        } else {
                            binding.addmarkText.setText(jsonObject1.getString("remarks"));

                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("jobdetails_ex", e.getMessage());

                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("jobdetails_error", t.getMessage());

            }
        });
    }
}