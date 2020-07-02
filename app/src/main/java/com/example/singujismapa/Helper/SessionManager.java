package com.example.singujismapa.Helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.view.menu.ShowableListMenu;

import com.example.singujismapa.MainActivity;
import com.example.singujismapa.home;
import com.example.singujismapa.LoginActivity;
import com.example.singujismapa.home;

import java.util.HashMap;

public class SessionManager {
    public static final String PREF_NAME = "LOGIN";
    public static final String LOGIN = "US_LOGIN";
    public static final String ID = "ID";
    public static final String BASE_URL = "http://192.168.43.132/SiNgujiSmapa-Web/";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public Context context;


    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context context){
        this.context = context;
        int PRIVATE_MODE = 0;
        sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = sharedPreferences.edit();
    }

    public void createSession(String id){
        editor.putBoolean(LOGIN, true);
        editor.putString(ID, id);
        editor.apply();
    }

    private boolean isLogin() { return  sharedPreferences.getBoolean(LOGIN, false); }

    public void checkLogin(){
        if (!this.isLogin()){
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
            ((MainActivity) context).finish();
        }
    }

    public HashMap<String, String> getUserDetail(){
        HashMap<String, String> user = new HashMap<>();
        user.put(ID, sharedPreferences.getString(ID, null));
        return user;
    }

    public String getNIS()
    {
        return sharedPreferences.getString(ID, null);
    }

    public HashMap<String, String> getBaseUrl(){
        HashMap<String, String> url = new HashMap<>();
        url.put(BASE_URL, sharedPreferences.getString(BASE_URL, null));
        return url;
    }

    public void logout(){
        editor.clear();
        editor.commit();
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
        ((MainActivity) context).finish();
    }
}


