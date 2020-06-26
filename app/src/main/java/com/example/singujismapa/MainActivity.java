package com.example.singujismapa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.os.Bundle;
import android.view.MenuItem;

import com.example.singujismapa.Helper.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity  {
    BottomNavigationView btm_view;
    DrawerLayout dlay;
    NavigationView nav_view;
    ActionBarDrawerToggle toggle;
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawernav);
        getFragment(new home());
        nav_view = findViewById(R.id.drawerview);
        dlay = findViewById(R.id.drawernav);
        toggle =new ActionBarDrawerToggle(this, dlay, R.string.Open, R.string.Close);
        dlay.addDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.Akunsaya){
                    getSupportActionBar().setTitle("Akun saya");
                    getFragment(new akun_saya());
                }else if(item.getItemId() == R.id.Bantuan) {
                    getSupportActionBar().setTitle("Bantuan");
                    getFragment(new bantuan());
                }else if(item.getItemId() == R.id.Logout) {

                 }
                dlay.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        btm_view = findViewById(R.id.bottom_view);
        btm_view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home){
                    getSupportActionBar().setTitle("Home");
                    getFragment(new home());
                }else if(item.getItemId() == R.id.jadwal) {
                    getSupportActionBar().setTitle("Jadwal");
                    getFragment(new jadwal());

                }else if (item.getItemId() == R.id.laporan){
                    getSupportActionBar().setTitle("Laporan");
                    getFragment(new laporan());
                }
                else if (item.getItemId() == R.id.akun){
                    getSupportActionBar().setTitle("Akun Saya");
                    getFragment(new akun_saya());
                }
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(toggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    private void getFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.isi,fragment);
        fragmentTransaction.commit();
    }


}