package com.app.gearapp.Fragment;

import static android.content.Context.CLIPBOARD_SERVICE;

import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import com.app.gearapp.Activity.ScannerActivity;
import com.app.gearapp.Activity.SelectRecipienttypeActivity;
import com.app.gearapp.Adapter.Capture;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.gearapp.Adapter.ToDoAdapter;
import com.app.gearapp.R;
import com.app.gearapp.databinding.FragmentToDoBinding;

public class To_Do_Fragment extends Fragment {

    FragmentToDoBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentToDoBinding.inflate(inflater, container, false);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.TodoRecyclerView.setLayoutManager(layoutManager);
        binding.TodoRecyclerView.setHasFixedSize(true);
        ToDoAdapter adapter = new ToDoAdapter(getContext());
        binding.TodoRecyclerView.setAdapter(adapter);

        binding.scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent=new Intent(getContext(), ScannerActivity.class);
                startActivity(intent);
//                IntentIntegrator intentIntegrator = new IntentIntegrator(getActivity());
//                intentIntegrator.setPrompt("For flash use volume up key");
//                intentIntegrator.setBeepEnabled(true);
//                intentIntegrator.setOrientationLocked(true);
//                intentIntegrator.setCaptureActivity(Capture.class);
//                intentIntegrator.initiateScan();


            }
        });

        return binding.getRoot();


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);




    }
}