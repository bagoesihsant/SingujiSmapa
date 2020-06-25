package com.example.singujismapa;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.singujismapa.Helper.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.HashMap;


public class HomeSinguji extends AppCompatActivity {
    private static String TAG = HomeSinguji.class.getSimpleName();
    SessionManager sessionManager;
    String getId;
    private String nav;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_singuji);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_jadwal, R.id.navigation_laporan ,R.id.navigation_profil)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);



        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(SessionManager.ID);

        nav = getIntent().getStringExtra("NAVIGATION");

        if (nav != null){
            switch (nav){
                case "HOME" :
                    navView.setSelectedItemId(R.id.navigation_home);
                    break;

                case "JADWAL" :
                    navView.setSelectedItemId(R.id.navigation_jadwal);
                    break;

                case "LAPORAN" :
                    navView.setSelectedItemId(R.id.navigation_laporan);
                    break;

                case "AKUN" :
                    navView.setSelectedItemId(R.id.navigation_profil);
                    break;
            }
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
