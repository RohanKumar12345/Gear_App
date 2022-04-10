package com.app.gearapp.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.gearapp.Adapter.ToDoAdapter;
import com.app.gearapp.Helpr.Contraints;
import com.app.gearapp.Helpr.GetDataService;
import com.app.gearapp.Helpr.PrefrenceManager;
import com.app.gearapp.Helpr.RetrofitClintanse;
import com.app.gearapp.Model.TodoModel;
import com.app.gearapp.R;
import com.app.gearapp.databinding.ActivityOnHoldBinding;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.locks.Condition;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OnHoldActivity extends AppCompatActivity {
    ActivityOnHoldBinding binding;
    public static Activity activity;
    String id;
    PrefrenceManager prefrenceManager;
    String onHold = "",number="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnHoldBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;

        prefrenceManager = new PrefrenceManager(getApplicationContext());

        onHold = getIntent().getStringExtra("onhold");

        binding.addmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddMarkActivity.class);
                intent.putExtra("addmarks", "onholder");
                startActivity(intent);
            }
        });

        binding.UpdateDeliveryStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),ScannerActivity.class);
                startActivity(intent);
            }
        });


        if (onHold.equals("onholdactivity")) {

            id = getIntent().getStringExtra("id");
            prefrenceManager.setProductid(id);

            boolean connected = false;
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                jobDetails(new PrefrenceManager(getApplicationContext()).getToken(),new PrefrenceManager(getApplicationContext()).getProductid());


                connected = true;
            } else

                alirtDilog();
            connected = false;


        } else if (onHold.equals("remarks")) {

            boolean connected = false;
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                    connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                jobDetails(new PrefrenceManager(getApplicationContext()).getToken(),new PrefrenceManager(getApplicationContext()).getProductid());


                connected = true;
            } else

                alirtDilog();
            connected = false;



        }

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
                intent.putExtra("orderinfor", "onhold");
                intent.putExtra("id_onhold", new PrefrenceManager(getApplicationContext()).getProductid());
                startActivity(intent);
            }
        });


        binding.message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
            }
        });
        binding.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri u = Uri.parse("tel:" + number);
                Intent i = new Intent(Intent.ACTION_DIAL, u);
                try {
                    startActivity(i);
                } catch (SecurityException s) {
                    Toast.makeText(OnHoldActivity.this, "An error occurred", Toast.LENGTH_LONG).show();
                }
            }
        });



    }

    public void alirtDilog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(OnHoldActivity.this);
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



    public void jobDetails(String token,String id){
        GetDataService getDataService = RetrofitClintanse.getClient(token).create(GetDataService.class);
        Call<JsonObject> call = getDataService.jobDetail(id);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    Log.e("jobdetails_res",response.body().toString());

                    JSONObject jsonObject=new JSONObject(new Gson().toJson(response.body()));
                    String res=jsonObject.getString("message");
                    Log.e("d",res);
                    if (res.equals("Success")){
                        JSONObject jsonObject1=jsonObject.getJSONObject("data");
                        Log.e("jkdf",jsonObject1.toString());


                        binding.address.setText(jsonObject1.getString("address"));
                        number=jsonObject1.getString("contact_phone");
                        binding.mobile.setText("Tel: " + number);
                        binding.createTime.setText(jsonObject1.getString("created_at"));
                        binding.upDateTime.setText(jsonObject1.getString("updated_at"));
                        binding.name.setText(jsonObject1.getString("contact_name")+" "+jsonObject1.getString("contact_name"));
                        binding.id.setText(jsonObject1.getString("gearId"));
                        binding.add.setText(jsonObject1.getString("remarks"));



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("jobdetails_ex",e.getMessage());

                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("jobdetails_error",t.getMessage());

            }
        });
    }

}