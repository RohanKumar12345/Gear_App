package com.app.gearapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.app.gearapp.Adapter.NoOneAdapter;
import com.app.gearapp.Adapter.OnHoldAdapter;
import com.app.gearapp.Adapter.RecipientAdapter;
import com.app.gearapp.Adapter.ToDoAdapter;
import com.app.gearapp.Helpr.GetDataService;
import com.app.gearapp.Helpr.PrefrenceManager;
import com.app.gearapp.Helpr.RetrofitClintanse;
import com.app.gearapp.Model.NoOneModel;
import com.app.gearapp.Model.RecipientModel;
import com.app.gearapp.Model.TodoModel;
import com.app.gearapp.R;

import com.app.gearapp.databinding.ActivitySelectRecipienttypeBinding;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectRecipienttypeActivity extends AppCompatActivity {
    ArrayList<RecipientModel> recipientModels = new ArrayList<>();
    ArrayList<NoOneModel> noOneModels = new ArrayList<>();
    ActivitySelectRecipienttypeBinding binding;
    String recipient_type = "", no_recipient = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySelectRecipienttypeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recipient(new PrefrenceManager(getApplicationContext()).getToken());
        noOne(new PrefrenceManager(getApplicationContext()).getToken());
    }


    public void recipient(String token) {
        recipientModels.clear();
        GetDataService getDataService = RetrofitClintanse.getClient(token).create(GetDataService.class);
        Call<JsonObject> call = getDataService.recipient();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    Log.e("recipientd_res", response.body().toString());
                    JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                    String res = jsonObject.getString("message");
                    if (res.equals("Successfull")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        String recipientdata = "";
                        for (int i = 0; i < jsonArray.length(); i++) {
                            RecipientModel recipientModel = new Gson().fromJson(jsonArray.getString(i).toString(), RecipientModel.class);
                            if (!recipientdata.equals(recipientModel.getRecipient())) {
                                Log.e("djhdj", recipientModel.getRecipient());
                                recipientModels.add(recipientModel);
                                no_recipient = recipientModels.get(0).getRecipient_type();

                            }


                        }
                    }
                    Log.e("dkj", no_recipient.toString());
                    if (no_recipient.equals("Recipient")) {
                        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
                        binding.recipietnRecyclerview.setLayoutManager(layoutManager);
                        binding.recipietnRecyclerview.setHasFixedSize(true);
                        RecipientAdapter adapter = new RecipientAdapter(getApplicationContext(), recipientModels);
                        binding.recipietnRecyclerview.setAdapter(adapter);

                    } else {

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("recipient_ex", e.getMessage());

                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("recipient_error", t.getMessage());

            }
        });
    }


    public void noOne(String token) {
        noOneModels.clear();
        GetDataService getDataService = RetrofitClintanse.getClient(token).create(GetDataService.class);
        Call<JsonObject> call = getDataService.recipient();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                try {
                    Log.e("recipient_res", response.body().toString());
                    JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                    String res = jsonObject.getString("message");
                    if (res.equals("Successfull")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            NoOneModel noOneModel = new Gson().fromJson(jsonArray.getString(i).toString(), NoOneModel.class);
                            recipient_type = noOneModel.getRecipient_type();
                            noOneModels.add(noOneModel);
                        }
                    }
                    if (recipient_type.equals("No One")) {
                        RecyclerView.LayoutManager layoutManager1 = new GridLayoutManager(getApplicationContext(), 3);
                        binding.ononeRecyclerview.setLayoutManager(layoutManager1);
                        binding.ononeRecyclerview.setHasFixedSize(true);
                        NoOneAdapter adapter1 = new NoOneAdapter(getApplicationContext(), noOneModels);
                        binding.ononeRecyclerview.setAdapter(adapter1);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("recipient_ex", e.getMessage());

                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("recipient_error", t.getMessage());

            }
        });
    }
}

