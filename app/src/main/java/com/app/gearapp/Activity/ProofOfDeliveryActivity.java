package com.app.gearapp.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.app.gearapp.Helpr.PrefrenceManager;
import com.app.gearapp.databinding.ActivityProofOfDeliveryBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;

public class ProofOfDeliveryActivity extends AppCompatActivity {
    ActivityProofOfDeliveryBinding binding;
    int position1 = 0;
    Uri uri1 = Uri.parse("");
    Uri uri2 = Uri.parse("");
    Uri uri3 = Uri.parse("");
    Uri uri4 = Uri.parse("");
    Uri uri5 = Uri.parse("");
    String imagecount = "", recpent_type, recpent_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityProofOfDeliveryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.imgeClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchs(position1);
            }
        });

        recpent_type = getIntent().getStringExtra("recipient_type");
        recpent_name = new PrefrenceManager(getApplicationContext()).getRecipientName();

        binding.item.setText(recpent_type);
        binding.recipentNames.setText(recpent_name);

        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignatureActivity.class);
                startActivity(intent);
            }
        });
    }

    public void switchs(int position) {

        switch (position) {
            case 0:
                Toast.makeText(ProofOfDeliveryActivity.this, "0 =" + position, Toast.LENGTH_SHORT).show();
                ImagePicker.with(ProofOfDeliveryActivity.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start(101);

                position1 = 1;
                break;
            case 1:
                Toast.makeText(ProofOfDeliveryActivity.this, "1=" + position, Toast.LENGTH_SHORT).show();

                ImagePicker.with(ProofOfDeliveryActivity.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start(102);
                position1 = 2;
                break;
            case 2:
                Toast.makeText(ProofOfDeliveryActivity.this, "2=" + position, Toast.LENGTH_SHORT).show();

                ImagePicker.with(ProofOfDeliveryActivity.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start(103);
                position1 = 3;
                break;
            case 3:
                ImagePicker.with(ProofOfDeliveryActivity.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start(104);

                Toast.makeText(ProofOfDeliveryActivity.this, "3=" + position, Toast.LENGTH_SHORT).show();

                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case 101: {
                    uri1 = data.getData();
                    Log.e("image", uri1.toString());
                    imagecount = uri1.toString();
                    binding.image1.setImageURI(uri1);
                    if (uri1.equals("")) {

                    } else {
                        binding.imageCount.setText("1/3");
                        binding.close.setVisibility(View.VISIBLE);
                    }

                    break;
                }

                case 102: {
                    uri2 = data.getData();
                    Log.e("image", uri2.toString());
                    imagecount = uri1.toString();
                    binding.image2.setImageURI(uri2);

                    if (uri2.equals("")) {

                    } else {
                        binding.imageCount.setText("2/3");
                        binding.close.setVisibility(View.VISIBLE);
                    }
                    break;
                }
                case 103: {
                    uri3 = data.getData();
                    Log.e("image", uri3.toString());
                    binding.image3.setImageURI(uri3);
                    if (uri2.equals("")) {

                    } else {
                        binding.imageCount.setText("3/3");
                        binding.close.setVisibility(View.VISIBLE);
                    }
                    break;
                }

                case 104: {
                    uri4 = data.getData();
                    Log.e("image", uri4.toString());
                    binding.image4.setImageURI(uri4);
                    if (uri2.equals("")) {

                    } else {
                        binding.imageCount.setText("4/5");
                        binding.close.setVisibility(View.VISIBLE);
                    }
                    break;
                }

                case 105: {
                    uri5 = data.getData();
                    Log.e("image", uri5.toString());
                    binding.image5.setImageURI(uri5);
                    if (uri2.equals("")) {

                    } else {
                        binding.imageCount.setText("5/5");
                        binding.close.setVisibility(View.VISIBLE);
                    }

                    break;
                }
            }
        }
        Log.e("count", String.valueOf(imagecount.length()));

    }

    public void arrelis(){
        File[] images={new File(uri1.getPath(),uri2.getPath())};
    }

}