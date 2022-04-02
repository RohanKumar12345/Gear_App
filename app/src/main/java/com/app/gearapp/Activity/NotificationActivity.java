package com.app.gearapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.app.gearapp.Adapter.NotificationTabAdapter;
import com.app.gearapp.Adapter.TabAdapter;
import com.app.gearapp.R;
import com.app.gearapp.databinding.ActivityNotificationBinding;
import com.google.android.material.tabs.TabLayout;

public class NotificationActivity extends AppCompatActivity {
    ActivityNotificationBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("All"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Work"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Compensation"));
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Announcement"));
        binding.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final NotificationTabAdapter adapter = new NotificationTabAdapter(this, getSupportFragmentManager(),
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
    }
}