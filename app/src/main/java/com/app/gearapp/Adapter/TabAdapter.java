package com.app.gearapp.Adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.app.gearapp.Fragment.DeliveredFragment;
import com.app.gearapp.Fragment.On_Hold_Fragment;
import com.app.gearapp.Fragment.To_Do_Fragment;

public class TabAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;
    public TabAdapter(Context c, FragmentManager fm, int totalTabs) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                To_Do_Fragment to_do_fragment = new To_Do_Fragment();
                return to_do_fragment;
            case 1:
                DeliveredFragment deliveredFragment = new DeliveredFragment();
                return deliveredFragment;
                case 2:
                On_Hold_Fragment on_hold_fragment = new On_Hold_Fragment();
                return on_hold_fragment;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return totalTabs;
    }
}