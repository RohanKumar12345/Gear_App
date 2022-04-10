package com.app.gearapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.gearapp.Helpr.GetDataService;
import com.app.gearapp.Helpr.PrefrenceManager;
import com.app.gearapp.Helpr.RetrofitClintanse;
import com.app.gearapp.MainActivity;
import com.app.gearapp.R;

import com.app.gearapp.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;
    PrefrenceManager prefrenceManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prefrenceManager = new PrefrenceManager(getApplicationContext());


        binding.buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean connected = false;
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                    if (validation()) {
                        sendFirebaseNotification();
                    }

                    connected = true;
                } else

                    alirtDilog();
                connected = false;
//
//
            }
        });

    }

    private void sendFirebaseNotification() {
        FirebaseApp.initializeApp( LoginActivity.this );
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener( new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w( "FCM", "getInstanceId failed", task.getException() );
                            return;
                        }
                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        // Log and toast
                        String msg = getString( R.string.msg_token_fmt, token );
                        Log.e( "MSG", msg + " | " + token );
                        Log.e("token",token);

                        login(token,msg);

                        //  Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                } );
    }



    public void alirtDilog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
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

    public boolean validation() {
        if (binding.loginId.getText().toString().trim().isEmpty()) {
            binding.loginId.setError("Enter The Login ID ");
            binding.loginId.requestFocus();
        } else if (binding.password.getText().toString().trim().isEmpty()) {
            binding.password.setError("Enter The Password");
            binding.password.requestFocus();
        } else if (binding.password.getText().toString().trim().length() < 10) {
            binding.password.setError("Password The 10 DigitPassword");
            binding.password.requestFocus();
        } else {
            return true;
        }
        return false;
    }

    public void login(String firbase_tonek,String firbase_key) {
        binding.spinKit.setVisibility(View.VISIBLE);
        GetDataService getDataService = RetrofitClintanse.getRetrofit().create(GetDataService.class);
        Call<JsonObject> call = getDataService.login(binding.loginId.getText().toString(), binding.password.getText().toString(),firbase_tonek,firbase_key);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.e("login_response", response.body().toString());
                binding.spinKit.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                    prefrenceManager.setToken(jsonObject.getString("access_token"));
                    JSONObject jsonObject2 = jsonObject.getJSONObject("data");
                    prefrenceManager.setUserId(jsonObject2.getString("id"));

                    Toast.makeText(LoginActivity.this, "Login  Successfully ", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), DasbordActivity.class);
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("login_ex", e.getMessage());
                    binding.spinKit.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Login  Not  Successfully ", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("login_response", t.getMessage());
                binding.spinKit.setVisibility(View.GONE);
                Toast.makeText(LoginActivity.this, "Login Not  Successfully ", Toast.LENGTH_SHORT).show();

            }
        });
    }
}