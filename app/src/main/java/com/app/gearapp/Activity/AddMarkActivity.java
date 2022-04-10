package com.app.gearapp.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.gearapp.Adapter.AddMarkAdapter;
import com.app.gearapp.Adapter.RecipientAdapter;
import com.app.gearapp.Helpr.GetDataService;
import com.app.gearapp.Helpr.PrefrenceManager;
import com.app.gearapp.Helpr.RetrofitClintanse;
import com.app.gearapp.Model.RemarkModel;
import com.app.gearapp.Model.TodoModel;
import com.app.gearapp.R;
import com.app.gearapp.databinding.ActivityAddMarkBinding;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMarkActivity extends AppCompatActivity {
    ActivityAddMarkBinding binding;
    public static Activity activity;
    public static String addmark = "";
    ArrayList<RemarkModel> remarkModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddMarkBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;
        addmark = getIntent().getStringExtra("addmarks");

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            Remark(new PrefrenceManager(getApplicationContext()).getToken());

            connected = true;
        } else

            alirtDilog();
        connected = false;


        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public void alirtDilog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(AddMarkActivity.this);
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


    public void Remark(String token) {
        binding.spinKit.setVisibility(View.VISIBLE);
        GetDataService getDataService = RetrofitClintanse.getClient(token).create(GetDataService.class);
        Call<JsonObject> call = getDataService.Remarks();
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                binding.spinKit.setVisibility(View.GONE);
                try {
                    Log.e("remark_response", response.body().toString());
                    JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                    String res = jsonObject.getString("message");
                    if (res.equals("Successfull")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            RemarkModel remarkModel = new Gson().fromJson(jsonArray.getString(i).toString(), RemarkModel.class);
                            remarkModels.add(remarkModel);
                        }
                        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getApplicationContext());
                        binding.addRecylview.setLayoutManager(layoutManager1);
                        binding.addRecylview.setHasFixedSize(true);
                        AddMarkAdapter adapter1 = new AddMarkAdapter(getApplicationContext(), remarkModels);
                        binding.addRecylview.setAdapter(adapter1);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("remark_ex", e.getMessage());
                    binding.spinKit.setVisibility(View.GONE);

                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("remark_error", t.getMessage());
                binding.spinKit.setVisibility(View.GONE);

            }
        });

    }
}