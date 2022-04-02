package com.app.gearapp.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.gearapp.R;
import com.app.gearapp.databinding.ActivitySignatureBinding;
import com.github.gcacace.signaturepad.views.SignaturePad;

public class SignatureActivity extends AppCompatActivity {
    ActivitySignatureBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySignatureBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
                Intent intent = new Intent(getApplicationContext(), DilverconfirmActivity.class);
                startActivity(intent);
            }
        });
    }
}