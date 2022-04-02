package com.app.gearapp.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.gearapp.Adapter.NotificationAdapter;
import com.app.gearapp.R;
import com.app.gearapp.databinding.FragmentWorkBinding;

public class WorkFragment extends Fragment {
    FragmentWorkBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentWorkBinding.inflate(inflater, container, false);
        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext());
        binding.workRecylview.setLayoutManager(layoutManager);
        binding.workRecylview.setHasFixedSize(true);
        NotificationAdapter adapter=new NotificationAdapter(getContext());
        binding.workRecylview.setAdapter(adapter);

        return  binding.getRoot();
    }
}