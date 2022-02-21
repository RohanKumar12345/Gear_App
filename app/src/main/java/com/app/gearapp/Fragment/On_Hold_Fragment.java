package com.app.gearapp.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.gearapp.Adapter.OnHoldAdapter;
import com.app.gearapp.Adapter.ToDoAdapter;
import com.app.gearapp.R;
import com.app.gearapp.databinding.FragmentOnHoldBinding;
import com.app.gearapp.databinding.FragmentToDoBinding;

public class On_Hold_Fragment extends Fragment {
FragmentOnHoldBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= FragmentOnHoldBinding.inflate(inflater, container, false);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.OnHoldeRecyclerview.setLayoutManager(layoutManager);
        binding.OnHoldeRecyclerview.setHasFixedSize(true);
        OnHoldAdapter adapter=new OnHoldAdapter(getContext());
        binding.OnHoldeRecyclerview.setAdapter(adapter);



        return binding.getRoot();
    }
}