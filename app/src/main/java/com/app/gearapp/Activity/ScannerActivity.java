package com.app.gearapp.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.gearapp.Adapter.Capture;
import com.app.gearapp.R;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.zxing.Result;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ScannerActivity extends AppCompatActivity {

    private CodeScanner mCodeScanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);


       // CodeScannerView scannerView = findViewById(R.id.scanner_view);

       // mCodeScanner = new CodeScanner(this, scannerView);

//        mCodeScanner.setDecodeCallback(new DecodeCallback() {
//            @Override
//            public void onDecoded(@NonNull final Result result) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        AlertDialog.Builder builder = new AlertDialog.Builder(ScannerActivity.this);
//                        ViewGroup viewGroup = findViewById(android.R.id.content);
//                        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.customview, viewGroup, false);
//                        builder.setView(dialogView);
//                        AlertDialog alertDialog = builder.create();
//
//
//                        TextView ok= dialogView.findViewById(R.id.ok);
//                        TextView cancel= dialogView.findViewById(R.id.cancel);
//                        TextView text= dialogView.findViewById(R.id.text);
//                        text.setText(result.getText());
//
//                        ok.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                String resul=result.getText();
//                                bottomNav();
//                            }
//                        });
//
//                        cancel.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                alertDialog.dismiss();
//                            }
//                        });
//                        alertDialog.show();
//                       // Toast.makeText(ScannerActivity.this, result.getText(), Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
//        scannerView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mCodeScanner.startPreview();
//            }
//        });

        IntentIntegrator intentIntegrator=new IntentIntegrator( ScannerActivity.this );
        intentIntegrator.setPrompt( "For flash use volume up key" );
        intentIntegrator.setBeepEnabled(true );
        intentIntegrator.setOrientationLocked( true );
        intentIntegrator.setCaptureActivity( Capture.class );
        intentIntegrator.initiateScan();
       // finishAffinity();




    } @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult( requestCode, resultCode, data );

        IntentResult intentResult=IntentIntegrator.parseActivityResult(
                requestCode,resultCode,data
        );
        if (intentResult.getContents() !=null){


            AlertDialog.Builder builder = new AlertDialog.Builder(ScannerActivity.this);
                        ViewGroup viewGroup = findViewById(android.R.id.content);
                        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.customview, viewGroup, false);
                        builder.setView(dialogView);
                        AlertDialog alertDialog = builder.create();


                        TextView ok= dialogView.findViewById(R.id.ok);
                        TextView cancel= dialogView.findViewById(R.id.cancel);
                        TextView text= dialogView.findViewById(R.id.text);
                        text.setText(intentResult.getContents());

                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String resul=intentResult.getContents();
                                bottomNav();
                            }
                        });

                        cancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                            }
                        });
                        alertDialog.show();

        }else {
            Toast.makeText( this, "OOps.... You did not scan anything", Toast.LENGTH_SHORT ).show();
        }
    }






    public void bottomNav(){
        BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(ScannerActivity.this);
        View sheetView = getLayoutInflater().inflate(R.layout.bottom_nav, null);
        bottomSheetDialog.setContentView(sheetView);
        bottomSheetDialog.setCancelable(false);
         ImageView closeImage=sheetView.findViewById(R.id.close);
        MaterialButton deliverd=sheetView.findViewById(R.id.deliverd);

        deliverd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ScannerActivity.this,SelectRecipienttypeActivity.class);
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
}