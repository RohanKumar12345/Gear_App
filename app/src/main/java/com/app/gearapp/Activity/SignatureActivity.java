package com.app.gearapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app.gearapp.R;
import com.app.gearapp.databinding.ActivityIgnatureBinding;
import com.github.gcacace.signaturepad.views.SignaturePad;

public class SignatureActivity extends AppCompatActivity {
    ActivityIgnatureBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityIgnatureBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.resign.setEnabled(false);
     binding.confirm.setEnabled(false);

        binding.mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {

            @Override
            public void onStartSigning() {
                //Event triggered when the pad is touched
            }

            @Override
            public void onSigned() {
                //Event triggered when the pad is signed
                binding.resign.setEnabled(true);
                binding.confirm.setEnabled(true);

            }

            @Override
            public void onClear() {
                binding.resign.setEnabled(false);
                binding.confirm.setEnabled(false);

                //Event triggered when the pad is cleared
            }
        });

        binding.resign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.mSignaturePad.clear();
            }
        });


        binding.confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),DilverconfirmActivity.class);
                startActivity(intent);
              Toast.makeText(SignatureActivity.this, "Next Page", Toast.LENGTH_SHORT).show();
            }
        });
    }
}