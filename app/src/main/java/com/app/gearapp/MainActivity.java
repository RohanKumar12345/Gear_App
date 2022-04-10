package com.app.gearapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.app.gearapp.Activity.DasbordActivity;
import com.app.gearapp.Activity.DilverconfirmActivity;
import com.app.gearapp.Activity.LoginActivity;
import com.app.gearapp.Activity.SelectRecipienttypeActivity;
import com.app.gearapp.Helpr.PrefrenceManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("use_id",new PrefrenceManager(getApplicationContext()).getUserId());
        Log.e("token",new PrefrenceManager(getApplicationContext()).getToken());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (new PrefrenceManager(getApplicationContext()).getUserId().equalsIgnoreCase("0")){
                    Intent intent=new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent=new Intent(getApplicationContext(), DasbordActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        },3000);
    }
}