package com.app.gearapp.Fragment;

import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.gearapp.Adapter.DeliveredAdapter;
import com.app.gearapp.Adapter.ToDoAdapter;
import com.app.gearapp.Helpr.GetDataService;
import com.app.gearapp.Helpr.PrefrenceManager;
import com.app.gearapp.Helpr.RetrofitClintanse;
import com.app.gearapp.Model.TodoModel;
import com.app.gearapp.R;
import com.app.gearapp.databinding.FragmentDeliveredBinding;
import com.app.gearapp.databinding.FragmentToDoBinding;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveredFragment extends Fragment {

    FragmentDeliveredBinding binding;
    int page_no = 1;
    ArrayList<TodoModel> todoModels = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDeliveredBinding.inflate(inflater, container, false);

        Delivered(new PrefrenceManager(getContext()).getToken());

        binding.scrollview.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == v.getChildAt(0).getMeasuredHeight() - v.getMeasuredHeight()) {
                    page_no++;
                    binding.spinKits.setVisibility(View.VISIBLE);
                    Delivered(new PrefrenceManager(getContext()).getToken());


                }
            }
        });

        return binding.getRoot();
    }

    public void Delivered(String token) {
        binding.spinKit.setVisibility(View.VISIBLE);
        GetDataService getDataService = RetrofitClintanse.getClient(token).create(GetDataService.class);
        Call<JsonObject> call = getDataService.token(String.valueOf(page_no));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.e("delivered_response", new Gson().toJson(response.body()));
                binding.spinKit.setVisibility(View.GONE);
                binding.spinKits.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    JSONObject jsonObject2 = jsonObject1.getJSONObject("delivered");
                    binding.itmeCount.setText(jsonObject2.getString("total") + " Entries");
                    JSONArray jsonArray = jsonObject2.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        TodoModel todoModel = new Gson().fromJson(jsonArray.getString(i).toString(), TodoModel.class);
                        todoModels.add(todoModel);
                    }

                    if (todoModels.size() < 0) {
                        binding.emty.setVisibility(View.VISIBLE);
                        binding.DeliveredRecyclerView.setVisibility(View.GONE);
                    } else {
                        binding.emty.setVisibility(View.GONE);
                        binding.DeliveredRecyclerView.setVisibility(View.VISIBLE);
                    }

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                    binding.DeliveredRecyclerView.setLayoutManager(layoutManager);
                    binding.DeliveredRecyclerView.setHasFixedSize(true);
                    DeliveredAdapter adapter = new DeliveredAdapter(getContext(), todoModels);
                    binding.DeliveredRecyclerView.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                    binding.spinKit.setVisibility(View.GONE);
                    binding.emty.setVisibility(View.VISIBLE);
                    binding.DeliveredRecyclerView.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("delivered_response", t.getMessage());
                binding.spinKit.setVisibility(View.GONE);
                binding.emty.setVisibility(View.VISIBLE);
                binding.DeliveredRecyclerView.setVisibility(View.GONE);
            }
        });
    }
}