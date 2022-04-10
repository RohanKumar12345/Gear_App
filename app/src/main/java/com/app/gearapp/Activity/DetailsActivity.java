package com.app.gearapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.app.gearapp.Helpr.GetDataService;
import com.app.gearapp.Helpr.PrefrenceManager;
import com.app.gearapp.Helpr.RetrofitClintanse;
import com.app.gearapp.R;
import com.app.gearapp.databinding.ActivityDetailsBinding;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {
ActivityDetailsBinding binding;

String ActivityName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    binding=ActivityDetailsBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    ActivityName=getIntent().getStringExtra("orderinfor");
    Log.e("fjd",ActivityName);

    if (ActivityName.equals("dilveryinfor")){
        jobDetails(new PrefrenceManager(getApplicationContext()).getToken(),getIntent().getStringExtra("id"));

    }else if (ActivityName.equals("onhold")){
        //getIntent().getStringExtra("id_onhold")
        jobDetails(new PrefrenceManager(getApplicationContext()).getToken(),new PrefrenceManager(getApplicationContext()).getProductid());

    }


    binding.back.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    });
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
                        binding.deliveredTime.setText(jsonObject1.getString("delivery_date"));
                        binding.recipentName.setText(jsonObject1.getString("recipient_name"));
                        binding.recipentRelationship.setText(jsonObject1.getString("recipient_type"));
                        binding.remark.setText(jsonObject1.getString("remarks"));
                        if (jsonObject1.getString("image").equals("")){

                        }else {

                         //   Log.e("fdj",Picasso.get().load(jsonObject1.getString("image")).into(binding.image1));
                            Picasso.get().load(jsonObject1.getString("image")).into(binding.image1);
                            Log.e("fk",jsonObject1.getString("image"));

                        }
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