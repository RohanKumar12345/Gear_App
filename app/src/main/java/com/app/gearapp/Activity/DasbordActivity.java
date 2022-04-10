package com.app.gearapp.Activity;

import static com.app.gearapp.R.drawable.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.app.gearapp.Adapter.DeliveredAdapter;
import com.app.gearapp.Adapter.OnHoldAdapter;
import com.app.gearapp.Adapter.ToDoAdapter;
import com.app.gearapp.Helpr.GetDataService;
import com.app.gearapp.Helpr.PrefrenceManager;
import com.app.gearapp.Helpr.RetrofitClintanse;
import com.app.gearapp.MainActivity;
import com.app.gearapp.Model.TodoModel;
import com.app.gearapp.R;
import com.app.gearapp.databinding.ActivityDasbordBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.leo.materialsearchview.MaterialSearchView;
import com.leo.materialsearchview.listeners.OnTextChangeListener;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.gearapp.Adapter.TabAdapter;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DasbordActivity extends AppCompatActivity {

    ActivityDasbordBinding binding;
    int i = arrow_back;
    int tabPosition;
    String listfragemt = "";

    int page_no = 1;
    ArrayList<TodoModel> todoModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDasbordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        final MaterialSearchView materialSearchView = new MaterialSearchView(this);

        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {

            binding.tabLayout.addTab(binding.tabLayout.newTab().setText("To-Do"));
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Delivered"));
            binding.tabLayout.addTab(binding.tabLayout.newTab().setText("On-Hold"));
            binding.tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            connected = true;
        } else

            alirtDilog();
        connected = false;
//


        binding.notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
                startActivity(intent);
            }
        });

        binding.summary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SummaryActivity.class);
                startActivity(intent);
            }
        });
        final TabAdapter adapter = new TabAdapter(this, getSupportFragmentManager(),
                binding.tabLayout.getTabCount());
        binding.viewPager.setAdapter(adapter);
        binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tabLayout));
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewPager.setCurrentItem(tab.getPosition());
                tabPosition = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });


        listfragemt = getIntent().getStringExtra("listfragment");

//        if (listfragemt.equals("listfragment")){
//            Fragment fragment = new TestFragment();
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
//        }else {
//
//        }

        binding.fillter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomFillter();

            }
        });


        binding.search.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onClick(View v) {
                //  Toast.makeText(DasbordActivity.this, "Showing Search from ImageView", Toast.LENGTH_LONG).show();

                materialSearchView.showKeyBoardDefault(true);
                //The method call where magic happens.
                materialSearchView.setOnTextChangeListener(new OnTextChangeListener() {
                    @Override
                    public void setOnTextChangeListener(String newText) {
                        Log.e("tabpostion", "" + tabPosition);

                        if (tabPosition == 0) {
                            if (newText.length() < 8) {
                                binding.searchRecylerview.setVisibility(View.GONE);
                                binding.viewPager.setVisibility(View.VISIBLE);
                            } else {
                                searchToDo(new PrefrenceManager(getApplicationContext()).getToken(), newText);
                                binding.searchRecylerview.setVisibility(View.VISIBLE);
                                binding.viewPager.setVisibility(View.GONE);
                            }
                        } else if (tabPosition == 1) {
                            if (newText.length() < 8) {
                                binding.searchRecylerview.setVisibility(View.GONE);
                                binding.viewPager.setVisibility(View.VISIBLE);
                            } else {
                                searchDelivered(new PrefrenceManager(getApplicationContext()).getToken(), newText);
                                binding.searchRecylerview.setVisibility(View.VISIBLE);
                                binding.viewPager.setVisibility(View.GONE);
                            }

                        } else if (tabPosition == 2) {
                            if (newText.length() < 8) {
                                binding.searchRecylerview.setVisibility(View.GONE);
                                binding.viewPager.setVisibility(View.VISIBLE);
                            } else {
                                searchOnHold(new PrefrenceManager(getApplicationContext()).getToken(), newText);
                                binding.searchRecylerview.setVisibility(View.VISIBLE);
                                binding.viewPager.setVisibility(View.GONE);
                            }

                        }

                    }
                });

                materialSearchView.showKeyBoardDefault(true);
                materialSearchView.setBackButtonDrawable((R.drawable.arrow_back));
                materialSearchView.show(v);
            }
        });


    }


    public void alirtDilog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(DasbordActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.network_layout, viewGroup, false);
        builder.setView(dialogView);
        builder.setCancelable(false);
        AlertDialog alertDialog = builder.create();

        TextView ok = dialogView.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();

            }
        });

        alertDialog.show();
    }


    public void bottomFillter() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(DasbordActivity.this);
        View sheetView = getLayoutInflater().inflate(R.layout.bottomfillter, null);
        bottomSheetDialog.setContentView(sheetView);


        bottomSheetDialog.show();

    }


    public void searchToDo(String token, String searchKeyword) {
        todoModels.clear();
        binding.spinKits.setVisibility(View.VISIBLE);
        GetDataService getDataService = RetrofitClintanse.getClient(token).create(GetDataService.class);
        Call<JsonObject> call = getDataService.search(String.valueOf(searchKeyword));
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.e("searchtodo_response", new Gson().toJson(response.body()));
                Log.e("searchtodo_response", "" + searchKeyword);
                binding.spinKits.setVisibility(View.GONE);

                try {

                    JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    JSONObject jsonObject2 = jsonObject1.getJSONObject("todo");
                    JSONArray jsonArray = jsonObject2.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        TodoModel todoModel = new Gson().fromJson(jsonArray.getString(i).toString(), TodoModel.class);
                        todoModels.add(todoModel);
                    }

                    LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    binding.searchRecylerview.setLayoutManager(layoutManager);
                    binding.searchRecylerview.setHasFixedSize(true);
                    ToDoAdapter adapter = new ToDoAdapter(getApplicationContext(), todoModels);
                    binding.searchRecylerview.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("searchtodo_ex", e.getMessage());
                    binding.spinKits.setVisibility(View.GONE);

                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("searchtodo_response", t.getMessage());
                binding.spinKits.setVisibility(View.GONE);

            }
        });
    }

    public void searchDelivered(String token,String searchKeyword) {
        todoModels.clear();
        binding.spinKits.setVisibility(View.VISIBLE);
        GetDataService getDataService = RetrofitClintanse.getClient(token).create(GetDataService.class);
        Call<JsonObject> call = getDataService.search(searchKeyword);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.e("searcdelivered_response", new Gson().toJson(response.body()));
                binding.spinKits.setVisibility(View.GONE);

                try {
                    JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    JSONObject jsonObject2 = jsonObject1.getJSONObject("delivered");
                    JSONArray jsonArray = jsonObject2.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        TodoModel todoModel = new Gson().fromJson(jsonArray.getString(i).toString(), TodoModel.class);
                        todoModels.add(todoModel);
                    }


                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    binding.searchRecylerview.setLayoutManager(layoutManager);
                    binding.searchRecylerview.setHasFixedSize(true);
                    DeliveredAdapter adapter = new DeliveredAdapter(getApplicationContext(), todoModels);
                    binding.searchRecylerview.setAdapter(adapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                    binding.spinKits.setVisibility(View.GONE);

                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("searcdelivered_response", t.getMessage());
                binding.spinKits.setVisibility(View.GONE);

            }
        });
    }
    public void searchOnHold(String token,String searchKeyword) {
        todoModels.clear();
        binding.spinKits.setVisibility(View.VISIBLE);
        GetDataService getDataService = RetrofitClintanse.getClient(token).create(GetDataService.class);
        Call<JsonObject> call = getDataService.search(searchKeyword);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.e("searchonhold_response", new Gson().toJson(response.body()));
                binding.spinKits.setVisibility(View.GONE);
                try {
                    JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                    JSONObject jsonObject2 = jsonObject1.getJSONObject("onHold");
                    Log.e("jsonObject2",jsonObject2.toString());
                    JSONArray jsonArray = jsonObject2.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        TodoModel todoModel = new Gson().fromJson(jsonArray.getString(i).toString(), TodoModel.class);
                        todoModels.add(todoModel);

                    }

//                    if (todoModels.size() < 0) {
//                        binding.emty.setVisibility(View.VISIBLE);
//                        binding.OnHoldeRecyclerview.setVisibility(View.GONE);
//                    } else {
//                        binding.emty.setVisibility(View.GONE);
//                        binding.OnHoldeRecyclerview.setVisibility(View.VISIBLE);
//                    }
//

                    RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                    binding.searchRecylerview.setLayoutManager(layoutManager);
                    binding.searchRecylerview.setHasFixedSize(true);
                    OnHoldAdapter adapter = new OnHoldAdapter(getApplicationContext(),todoModels);
                    binding.searchRecylerview.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    binding.spinKits.setVisibility(View.GONE);
                 Log.e("searchonhold_ex",e.getMessage());
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("searchonhold_response", t.getMessage());
                binding.spinKits.setVisibility(View.GONE);

            }
        });
    }
}

