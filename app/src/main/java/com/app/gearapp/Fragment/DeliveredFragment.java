package com.app.gearapp.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.gearapp.Adapter.ToDoAdapter;
import com.app.gearapp.R;
import com.app.gearapp.databinding.FragmentDeliveredBinding;
import com.app.gearapp.databinding.FragmentToDoBinding;

public class DeliveredFragment extends Fragment {

    FragmentDeliveredBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= FragmentDeliveredBinding.inflate(inflater, container, false);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.DeliveredRecyclerView.setLayoutManager(layoutManager);
        binding.DeliveredRecyclerView.setHasFixedSize(true);
        ToDoAdapter adapter=new ToDoAdapter(getContext());
        binding.DeliveredRecyclerView.setAdapter(adapter);




        return binding.getRoot();


    }
}