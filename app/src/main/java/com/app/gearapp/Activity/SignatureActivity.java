package com.app.gearapp.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.gearapp.Helpr.GetDataService;
import com.app.gearapp.Helpr.PrefrenceManager;
import com.app.gearapp.Helpr.RetrofitClintanse;
import com.app.gearapp.R;
import com.app.gearapp.databinding.ActivitySignatureBinding;
import com.github.gcacace.signaturepad.views.SignaturePad;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignatureActivity extends AppCompatActivity {
    ActivitySignatureBinding binding;
    File signacharfile;
    String recipient_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignatureBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        recipient_type = getIntent().getStringExtra("recpent_type");
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.resign.setEnabled(false);
        binding.confirm.setEnabled(false);


        binding.resign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.signaturePad.clear();
            }
        });


        binding.signaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {

            }

            @Override
            public void onSigned() {
                //Event triggered when the pad is signed
                binding.resign.setEnabled(true);
                binding.confirm.setEnabled(true);


            }

            @Override
            public void onClear() {
                //mSignaturePad.clear();
                //Event triggered when the pad is cleared
            }
        });


        binding.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = binding.signaturePad.getSignatureBitmap();
                persistImage(bitmap, "name");

                boolean connected = false;
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                        connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

                    dilvery(new PrefrenceManager(getApplicationContext()).getToken());

                    connected = true;
                } else

                    alirtDilog();
                connected = false;


            }
        });
    }


    private void persistImage(Bitmap bitmap, String name) {
        //File filesDir = getAppContext().getFilesDir();
        signacharfile = new File(getApplicationContext().getFilesDir() + name);
        //Log.e("iakg",file.toString());

        OutputStream os;
        try {
            os = new FileOutputStream(signacharfile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e("jf", "Error writing bitmap", e);
        }
    }


    public void dilvery(String token) {
        binding.spinKit.setVisibility(View.VISIBLE);
        RequestBody requestFile = RequestBody.create(MediaType.parse("*/*"), signacharfile);
        MultipartBody.Part signature_img = MultipartBody.Part.createFormData("signature", signacharfile.toString(), requestFile);
        GetDataService getDataService = RetrofitClintanse.getClient(token).create(GetDataService.class);
        Call<JsonObject> call = getDataService.dilvery(ProofOfDeliveryActivity.list, signature_img,
                RequestBody.create(MediaType.parse("multipart/form-data"), recipient_type),
                RequestBody.create(MediaType.parse("multipart/form-data"),
                        new PrefrenceManager(getApplicationContext()).getRecipientName()),
                new PrefrenceManager(getApplicationContext()).getQrId());
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                binding.spinKit.setVisibility(View.GONE);
                try {
                    Log.e("delivery_res", response.body().toString());
                    Log.e("delivery_res", ProofOfDeliveryActivity.list+"/ "+ signature_img+" /"+
                            RequestBody.create(MediaType.parse("multipart/form-data"), recipient_type)+"/"+
                            RequestBody.create(MediaType.parse("multipart/form-data"), new PrefrenceManager(getApplicationContext()).getRecipientName())+"/"+
                            new PrefrenceManager(getApplicationContext()).getQrId());
                    JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                    String res = jsonObject.getString("message");
                    if (res.equals("Delivered Successfully")) {
                        Toast.makeText(SignatureActivity.this, "Delivered Successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), DilverconfirmActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(SignatureActivity.this, "Delivered Not Successfully", Toast.LENGTH_SHORT).show();
                        binding.spinKit.setVisibility(View.GONE);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("delivery_ex", e.getMessage());

                    binding.spinKit.setVisibility(View.GONE);

                }


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("delivery_error", t.getMessage());
                binding.spinKit.setVisibility(View.GONE);

            }
        });
    }


    public void alirtDilog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(SignatureActivity.this);
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


}