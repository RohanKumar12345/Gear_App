package com.app.gearapp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.gearapp.Adapter.Capture;
import com.app.gearapp.Helpr.GetDataService;
import com.app.gearapp.Helpr.PrefrenceManager;
import com.app.gearapp.Helpr.RetrofitClintanse;
import com.app.gearapp.R;

import com.app.gearapp.databinding.ActivityScannerBinding;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScannerActivity extends AppCompatActivity {
    private static final int MY_CAMERA_REQUEST_CODE = 100;
    ActivityScannerBinding binding;
    private CodeScanner mCodeScanner;
    String no = "", contact_name = "", contact_address = "", contact_phone = "", id = "";
    PrefrenceManager prefrenceManager;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScannerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prefrenceManager = new PrefrenceManager(getApplicationContext());

        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);

        //id = getIntent().getStringExtra("id");


        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_REQUEST_CODE);

        } else {


            mCodeScanner.setDecodeCallback(new DecodeCallback() {
                @Override
                public void onDecoded(@NonNull final Result result) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            setQrCode(result.getText().toString(), new PrefrenceManager(getApplicationContext()).getToken());
                           // bottomNav();
                        }
                    });
                }
            });


            binding.manualInputBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final AlertDialog.Builder alert = new AlertDialog.Builder(ScannerActivity.this);
                    View mView = getLayoutInflater().inflate(R.layout.edittext, null);
                    alert.setView(mView);
                    EditText qrcode = (EditText) mView.findViewById(R.id.qrcode);
                    MaterialButton confirm = (MaterialButton) mView.findViewById(R.id.qr_confirm);
                    final AlertDialog alertDialog = alert.create();
                    alertDialog.setCanceledOnTouchOutside(false);


                    confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (qrcode.getText().toString().trim().isEmpty()) {
                                qrcode.setError("Enter The QR Code");
                                qrcode.requestFocus();
                            } else if (qrcode.getText().toString().trim().length() < 8) {
                                qrcode.setError("Enter The 8 Digit QR Code");
                                qrcode.requestFocus();
                            } else {
                                setQrCode(qrcode.getText().toString(), new PrefrenceManager(getApplicationContext()).getToken());

                            }

                        }
                    });

                    alertDialog.show();
                }
            });

        }


        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
    }


    public void setQrCode(String QrCode, String token) {
        GetDataService getDataService = RetrofitClintanse.getClient(token).create(GetDataService.class);
        Call<JsonObject> call = getDataService.qr_edit(QrCode);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.e("qr_response", response.body().toString());

                try {
                    JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                    String res = jsonObject.getString("message");
                    if (res.equalsIgnoreCase("Success")) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                        no = jsonObject1.getString("no");
                        id = jsonObject1.getString("id");
                        contact_name = jsonObject1.getString("contact_name");
                        contact_address = jsonObject1.getString("address");
                        contact_phone = jsonObject1.getString("contact_phone");
                        bottomNav();
                    } else {
                        Toast.makeText(ScannerActivity.this, "OR Code Wrong", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("qr_ex", e.getMessage());

                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("qr_error", t.getMessage());

            }
        });
    }

    public void bottomNav() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(ScannerActivity.this);
        View sheetView = getLayoutInflater().inflate(R.layout.bottom_nav, null);
        bottomSheetDialog.setContentView(sheetView);
        bottomSheetDialog.setCancelable(false);
        ImageView closeImage = sheetView.findViewById(R.id.close);
        MaterialButton deliverd = sheetView.findViewById(R.id.deliverd);
        MaterialButton noButton = sheetView.findViewById(R.id.noButton);
        TextView ids = sheetView.findViewById(R.id.id);
        TextView name = sheetView.findViewById(R.id.name);
        TextView address = sheetView.findViewById(R.id.address);
        CardView whatsapp = sheetView.findViewById(R.id.whatsapp);
        CardView phone = sheetView.findViewById(R.id.phone);
        CardView message = sheetView.findViewById(R.id.message);


        ids.setText(id);
        name.setText(contact_name);
        noButton.setText("No. " + no);
        address.setText(contact_address);

        whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String url = "https://api.whatsapp.com/send?phone=" + contact_phone;
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } catch (Exception e) {
                    Toast.makeText(ScannerActivity.this, "Whatsapp is not Installed", Toast.LENGTH_SHORT).show();
                }

            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri u = Uri.parse("tel:" + contact_phone);
                Intent i = new Intent(Intent.ACTION_DIAL, u);
                try {
                    startActivity(i);
                } catch (SecurityException s) {
                    Toast.makeText(ScannerActivity.this, "An error occurred", Toast.LENGTH_LONG).show();
                }
            }
        });


        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", contact_phone, null)));
            }
        });


        deliverd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prefrenceManager.setRecipientName(contact_name);
                prefrenceManager.setQrId(id);
                Intent intent = new Intent(ScannerActivity.this, SelectRecipienttypeActivity.class);
                startActivity(intent);
            }
        });

        closeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}