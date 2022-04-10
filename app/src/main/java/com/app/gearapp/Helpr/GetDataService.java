package com.app.gearapp.Helpr;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetDataService {

    @POST("login")
    @FormUrlEncoded
    Call<JsonObject> login(
            @Field("email") String email,
            @Field("password") String password,
            @Field("device_token") String device_token,
            @Field("firebase_key") String firebase_key

    );
    @Headers("multipart: true")
    @POST("deliver/{id}")
    @Multipart
    Call<JsonObject> RemarkSelected(
            @Path("id") String id,
            @Part(  "remarks") RequestBody remarks
    );


    @GET("jobList")
    Call<JsonObject> token(
            @Query("page") String page
    );

    @GET("jobList")
    Call<JsonObject> search(
            @Query("search") String search
    );

    @GET("recipient")
    Call<JsonObject> recipient(
    );

    @GET("failed/{id}")
    Call<JsonObject> failed(
            @Path("id") String id);


    @GET("remarks")
    Call<JsonObject> Remarks(
    );


    @GET("qr/{id}")
    Call<JsonObject> qr_edit(
            @Path("id") String id
    );

    @GET("jobDetail/{id}")
    Call<JsonObject> jobDetail(
            @Path("id") String id
    );

    @GET("summarry")
    Call<JsonObject> summarry(

    );


    @Headers("multipart: true")
    @POST("deliver/{id}")
    @Multipart
    Call<JsonObject> dilvery(
            @Part List<MultipartBody.Part> image,
            @Part MultipartBody.Part signature,
            @Part("recipient_type") RequestBody recipient_type,
            @Part("recipient_name") RequestBody recipient_name,
            @Path("id") String id

    );


}
