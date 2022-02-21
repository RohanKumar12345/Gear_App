package com.app.gearapp.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.gearapp.Adapter.ToDoAdapter;
import com.app.gearapp.R;
import com.app.gearapp.databinding.FragmentToDoBinding;

public class To_Do_Fragment extends Fragment {

FragmentToDoBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentToDoBinding.inflate(inflater, container, false);



        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.TodoRecyclerView.setLayoutManager(layoutManager);
        binding.TodoRecyclerView.setHasFixedSize(true);
        ToDoAdapter adapter=new ToDoAdapter(getContext());
        binding.TodoRecyclerView.setAdapter(adapter);



        return binding.getRoot();


    }
}