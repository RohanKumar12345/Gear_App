package com.app.gearapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.app.gearapp.Adapter.TabAdapter;
import com.app.gearapp.R;
import com.app.gearapp.databinding.ActivityDasbordBinding;
import com.google.android.material.tabs.TabLayout;

public class DasbordActivity extends AppCompatActivity {

    ActivityDasbordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDasbordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


       binding.tabLayout.addTab(  binding. tabLayout.newTab().setText("To-Do"));
        binding. tabLayout.addTab(  binding. tabLayout.newTab().setText("Delivered"));
        binding. tabLayout.addTab(  binding. tabLayout.newTab().setText("On-Hold"));
        binding.  tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final TabAdapter adapter = new TabAdapter(this,getSupportFragmentManager(),
                binding.   tabLayout.getTabCount());
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
    }
}