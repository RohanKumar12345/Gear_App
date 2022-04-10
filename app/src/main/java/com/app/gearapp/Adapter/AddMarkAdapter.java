package com.app.gearapp.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.gearapp.Activity.AddMarkActivity;
import com.app.gearapp.Activity.DilverconfirmActivity;
import com.app.gearapp.Activity.OnHoldActivity;
import com.app.gearapp.Activity.OrderInfoActivity;
import com.app.gearapp.Activity.ProofOfDeliveryActivity;
import com.app.gearapp.Activity.SignatureActivity;
import com.app.gearapp.Helpr.GetDataService;
import com.app.gearapp.Helpr.PrefrenceManager;
import com.app.gearapp.Helpr.RetrofitClintanse;
import com.app.gearapp.Model.RemarkModel;
import com.app.gearapp.R;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMarkAdapter extends RecyclerView.Adapter<AddMarkAdapter.ViewHolder> {

    Context context;
    ArrayList<RemarkModel> remarkModels;

    public AddMarkAdapter(Context context, ArrayList<RemarkModel> remarkModels) {
        this.context = context;
        this.remarkModels = remarkModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.addmark_recylview, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.remarkText.setText(remarkModels.get(position).getRemarks());

        holder.remarkText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RequestBody title = RequestBody.create(MediaType.parse("text/plain"), remarkModels.get(position).getRemarks());

                selectRemark(new PrefrenceManager(context).getToken(), title, position);

            }
        });

    }

    @Override
    public int getItemCount() {
        return remarkModels.size();
    }

    public void selectRemark(String token, RequestBody remarkid, int position) {
        GetDataService getDataService = RetrofitClintanse.getClient(token).create(GetDataService.class);
        Call<JsonObject> call = getDataService.RemarkSelected(new PrefrenceManager(context).getProductid(), remarkid);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.e("addremark_res", response.body().toString());
                try {
                    JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                    String res = jsonObject.getString("message");
                    if (res.equals("Delivered Successfully")) {
                        if (AddMarkActivity.addmark.equals("orderinfor")) {
                            Intent intent = new Intent(context, OrderInfoActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("orderInfor", "remark");
                            intent.putExtra("remarks", remarkModels.get(position).getRemarks());
                            context.startActivity(intent);
                            AddMarkActivity.activity.finish();
                            OrderInfoActivity.activity.finish();
                        } else if (AddMarkActivity.addmark.equals("onholder")) {
                            Intent intent = new Intent(context, OnHoldActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("onhold", "remarks");
                            intent.putExtra("remarks", remarkModels.get(position).getRemarks());
                            context.startActivity(intent);
                            AddMarkActivity.activity.finish();
                            OnHoldActivity.activity.finish();
                        } else if (AddMarkActivity.addmark.equals("dilverconfirm")) {
                            Intent intent = new Intent(context, DilverconfirmActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("remarks", remarkModels.get(position).getRemarks());
                            context.startActivity(intent);
                            DilverconfirmActivity.activity.finish();

                        }


                    } else {
                        Toast.makeText(context, "Remark Not selected", Toast.LENGTH_SHORT).show();


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("addremark_ex", e.getMessage());

                    //  binding.spinKit.setVisibility(View.GONE);

                }


            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.e("addremark_error", t.getMessage());
                // binding.spinKit.setVisibility(View.GONE);

            }
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MaterialButton remarkText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            remarkText = itemView.findViewById(R.id.remarkText);
        }

    }


}
