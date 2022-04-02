package com.app.gearapp.Helpr;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetDataService {

    @POST("login")
    @FormUrlEncoded
    Call<JsonObject> login(
            @Field("email") String email,
            @Field("password") String password

    );


    @GET("jobList")
    Call<JsonObject> token(
            @Query("page")String page
    );
 @GET("recipient")
    Call<JsonObject> recipient(

    );


    @GET("remarks")
    Call<JsonObject> Remarks(
    );


   @GET("qr/{id}")
    Call<JsonObject> qr_edit(
           @Path("id") String id
   );

}
