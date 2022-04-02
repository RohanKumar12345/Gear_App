package com.app.gearapp.Activity;

import static com.app.gearapp.R.drawable.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.app.gearapp.MainActivity;
import com.app.gearapp.R;
import com.app.gearapp.databinding.ActivityDasbordBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.leo.materialsearchview.MaterialSearchView;
import com.leo.materialsearchview.listeners.OnTextChangeListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.gearapp.Adapter.TabAdapter;
import com.google.android.material.tabs.TabLayout;

public class DasbordActivity extends AppCompatActivity {

    ActivityDasbordBinding binding;
    int i = arrow_back;

    String listfragemt="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDasbordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        final MaterialSearchView materialSearchView = new MaterialSearchView(this);



        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            binding.tabLayout.addTab(binding.tabLayout.newTab().setText("To-Do"));
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Delivered"));
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText("On-Hold"));
            binding.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            connected = true;
        } else

            alirtDilog();
        connected = false;
//



        binding.notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),NotificationActivity.class);
                startActivity(intent);
            }
        });
        final TabAdapter adapter = new TabAdapter(this, getSupportFragmentManager(),
                binding.tabLayout.getTabCount());
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout));
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


        listfragemt=getIntent().getStringExtra("listfragment");

//        if (listfragemt.equals("listfragment")){
//            Fragment fragment = new TestFragment();
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
//        }else {
//
//        }

        binding.fillter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomFillter();

            }
        });


        binding.search.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                //  Toast.makeText(DasbordActivity.this, "Showing Search from ImageView", Toast.LENGTH_LONG).show();

                //The method call where magic happens.
                materialSearchView.setOnTextChangeListener(new OnTextChangeListener() {
                    @Override
                    public void setOnTextChangeListener(String newText) {


                        if (binding.tabLayout.newTab().equals("To-Do")){
                            Toast.makeText(DasbordActivity.this, "To Do", Toast.LENGTH_SHORT).show();
                        }else if (binding.tabLayout.newTab().equals("Delivered")){
                            Toast.makeText(DasbordActivity.this, "Delivered", Toast.LENGTH_SHORT).show();
                        } else if (binding.tabLayout.newTab().equals("On-Hold")) {
                            Toast.makeText(DasbordActivity.this, "On-Hold", Toast.LENGTH_SHORT).show();
                        }
                     //   Log.e("new", newText);

                    }
                });

                materialSearchView.showKeyBoardDefault(true);
                //setBackButtonDrawable((R.drawable.arrow_back));
                materialSearchView.show(v);
            }
        });


    }


    public void alirtDilog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(DasbordActivity.this);
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


    public void bottomFillter(){
        BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(DasbordActivity.this);
        View sheetView = getLayoutInflater().inflate(R.layout.bottomfillter, null);
        bottomSheetDialog.setContentView(sheetView);


        bottomSheetDialog.show();

    }

}

