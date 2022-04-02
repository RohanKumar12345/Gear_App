package com.app.gearapp.Helpr;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefrenceManager {
    SharedPreferences sharedPreferences;
    Context context;
    SharedPreferences.Editor editor;

    public PrefrenceManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences("Apna jini",Context.MODE_PRIVATE);
        this.context = context;
        this.editor = sharedPreferences.edit();
    }

    public String getUserId(){
        return sharedPreferences.getString( "userid","0" );
    }

    public void setUserId(String userId){
        editor.putString( "userid",userId );
        editor.commit();
    }

    public String getQrId(){
        return sharedPreferences.getString( "qrid","0" );
    }

  public void setQrId(String qrId){
        editor.putString( "qrid",qrId );
        editor.commit();
    }

    public String getRecipientName(){
        return sharedPreferences.getString( "recipientName","0" );
    }

    public void setRecipientName(String recipientName){
        editor.putString( "recipientName",recipientName );
        editor.commit();
    }

    public void setBookingMobile(String bookingMobilemobiles){
        editor.putString( "bookingmobiles",bookingMobilemobiles );
        editor.commit();
    }

    public String getBookingmobile(){
        return sharedPreferences.getString( "bookingmobiles","0" );
    }

    public String getShopkeeprnumber(){
        return sharedPreferences.getString( "shopkeeprnumber","0" );
    }

    public void setShopkeeprnumber(String shopkeeprnumber){
        editor.putString( "shopkeeprnumber",shopkeeprnumber );
        editor.commit();
    }

    public String getUsername(){
        return sharedPreferences.getString( "username","0" );
    }

    public void setUsername(String username){
        editor.putString( "username",username );
        editor.commit();
    }

    public String getUsermobile(){
        return sharedPreferences.getString( "rejmobile","0" );
    }

    public void setUsermobile(String usermobile){
        editor.putString( "rejmobile",usermobile );
        editor.commit();
    }

    public String getTime(){
        return sharedPreferences.getString( "time","0" );
    }

    public void setTime(String time){
        editor.putString( "time",time );
        editor.commit();
    }

    public String getUseraddress(){
        return sharedPreferences.getString( "useraddress","0" );
    }

    public void setUseraddress(String useraddress){
        editor.putString( "useraddress",useraddress );
        editor.commit();
    }

    public void setUseremail(String useremail){
        editor.putString( "useremail",useremail );
        editor.commit();
    }

    public String getuseremail(){
        return sharedPreferences.getString( "useremail","0" );
    }

    public String getLoginUserid(){
        return sharedPreferences.getString( "loginUserid","0" );
    }

    public void setLoginUserid(String loginUserid){
        editor.putString( "loginUserid",loginUserid );
        editor.commit();
    }

    public String getLoginEmail(){
        return sharedPreferences.getString( "loginEmail","0" );
    }

    public void setLoginEmail(String loginEmail){
        editor.putString( "loginEmail",loginEmail );
        editor.commit();
    }

    public String getToken(){
        return sharedPreferences.getString( "token","0" );
    }

    public void setToken(String token){
        editor.putString( "token",token );
        editor.commit();
    }

    public String getLoginusername(){
        return sharedPreferences.getString( "loginusername","0" );
    }

    public void setLoginusername(String loginusername){
        editor.putString( "loginusername",loginusername );
        editor.commit();
    }

    public String getLoginuseraddress(){
        return sharedPreferences.getString( "loginuseraddress","0" );
    }

    public void setLoginuseraddress(String loginuseraddress){
        editor.putString( "loginuseraddress",loginuseraddress );
        editor.commit();
    }

    public void logout(){
        editor.clear();
        editor.apply();
        editor.commit();
    }
}
