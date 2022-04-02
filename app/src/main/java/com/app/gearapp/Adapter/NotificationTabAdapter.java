package com.app.gearapp.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.app.gearapp.Fragment.AllFragment;
import com.app.gearapp.Fragment.AnnouncementFragment;
import com.app.gearapp.Fragment.CompesationFragment;
import com.app.gearapp.Fragment.DeliveredFragment;
import com.app.gearapp.Fragment.On_Hold_Fragment;
import com.app.gearapp.Fragment.To_Do_Fragment;
import com.app.gearapp.Fragment.WorkFragment;

public class NotificationTabAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;
    public NotificationTabAdapter(Context c, FragmentManager fm, int totalTabs) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                AllFragment allFragment = new AllFragment();
                return allFragment;
            case 1:
                WorkFragment workFragment = new WorkFragment();
                return workFragment;
            case 2:
                CompesationFragment compesationFragment = new CompesationFragment();
                return compesationFragment;

                case 3:
                AnnouncementFragment announcementFragment = new AnnouncementFragment();
                return announcementFragment;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return totalTabs;
    }
}
