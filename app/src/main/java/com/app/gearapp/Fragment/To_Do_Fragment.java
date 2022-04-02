package com.app.gearapp.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.gearapp.Activity.ScannerActivity;
import com.app.gearapp.Helpr.GetDataService;
import com.app.gearapp.Helpr.PrefrenceManager;
import com.app.gearapp.Helpr.RetrofitClintanse;
import com.app.gearapp.Model.TodoModel;
import com.google.gson.Gson;
import com.google.gson.JsonObject;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.gearapp.Adapter.ToDoAdapter;
import com.app.gearapp.R;
import com.app.gearapp.databinding.FragmentToDoBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.AbstractList;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class To_Do_Fragment extends Fragment {

    FragmentToDoBinding binding;
    ToDoAdapter adapter;
    int page_no = 1;
    ArrayList<TodoModel> todoModels = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentToDoBinding.inflate(inflater, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.TodoRecyclerView.setLayoutManager(layoutManager);
        binding.TodoRecyclerView.setHasFixedSize(true);

        ToDo(new PrefrenceManager(getContext()).getToken());

        binding.scrollview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    page_no++;
                    binding.spinKits.setVisibility(View.VISIBLE);
                    ToDo(new PrefrenceManager(getContext()).getToken());


                }
            }
        });

        binding.scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(), ScannerActivity.class);
                startActivity(intent);
            }
        });

        return binding.getRoot();


    }

    public void ToDo(String token) {
        binding.spinKit.setVisibility(View.VISIBLE);
        GetDataService getDataService = RetrofitClintanse.getClient(token).create(GetDataService.class);
        Call<JsonObject> call = getDataService.token(String.valueOf(page_no));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.e("todo_response", new Gson().toJson(response.body()));
                Log.e("todos_response", "" + page_no);
                binding.spinKit.setVisibility(View.GONE);
                binding.spinKits.setVisibility(View.GONE);
                try {

                    JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    JSONObject jsonObject2 = jsonObject1.getJSONObject("todo");
                    //  next_url = jsonObject2.getString("next_page_url");
                    binding.itmeCount.setText(jsonObject2.getString("total") + " Entries");
                    JSONArray jsonArray = jsonObject2.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        TodoModel todoModel = new Gson().fromJson(jsonArray.getString(i).toString(), TodoModel.class);
                        todoModels.add(todoModel);

                    }


                    if (todoModels.size() < 0) {
                        binding.emty.setVisibility(View.VISIBLE);
                        binding.TodoRecyclerView.setVisibility(View.GONE);
                    } else {
                        binding.emty.setVisibility(View.GONE);
                        binding.TodoRecyclerView.setVisibility(View.VISIBLE);
                    }
                    adapter = new ToDoAdapter(getContext(), todoModels);
                    binding.TodoRecyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("todo_ex", e.getMessage());
                    binding.spinKit.setVisibility(View.GONE);
                    binding.emty.setVisibility(View.VISIBLE);
                    binding.TodoRecyclerView.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("todo_response", t.getMessage());
                binding.spinKit.setVisibility(View.GONE);
                binding.emty.setVisibility(View.VISIBLE);
                binding.TodoRecyclerView.setVisibility(View.GONE);
            }
        });
    }
}