package com.app.gearapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.app.gearapp.R;
import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;

public class ScannerActivity extends AppCompatActivity {

    private CodeScanner mCodeScanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);

        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        AlertDialog.Builder builder=new AlertDialog.Builder( ScannerActivity.this );
                        builder.setTitle( "Result" );
                        builder.setMessage( result.getText() );
                        builder.setPositiveButton( "Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Intent intent=new Intent(getApplicationContext(),SelectRecipienttypeActivity.class);
                                intent.putExtra( "intentid",result.getText() );

                                Log.i( "intentid",result.getText() );
                                startActivity( intent );
                                dialogInterface.dismiss();
                            }
                        } );
                        builder.show();

                       // Toast.makeText(ScannerActivity.this, result.getText(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });



    }
}