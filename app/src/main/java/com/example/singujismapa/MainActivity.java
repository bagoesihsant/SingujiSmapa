package com.example.singujismapa;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.view.View;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.singujismapa.Helper.SessionManager;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity  {
    BottomNavigationView btm_view;
    DrawerLayout dlay;
    NavigationView nav_view;
    ActionBarDrawerToggle toggle;
    SessionManager sessionManager;
    private TextView Tusername;
    String NIS, BaseUrl;
    CircleImageView foto_profil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawernav);
        getFragment(new home());

        NavigationView navigationView = (NavigationView) findViewById(R.id.drawerview);
        View headerView = navigationView.getHeaderView(0);

        Tusername = headerView.findViewById(R.id.Tusername);
        foto_profil = headerView.findViewById(R.id.avatar);

        sessionManager = new SessionManager(this);
        BaseUrl = SessionManager.BASE_URL;

        GetUser();

        nav_view = findViewById(R.id.drawerview);
        sessionManager = new SessionManager(MainActivity.this);
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
                }else if(item.getItemId() == R.id.Logout) {

                    sessionManager.logout();
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


    public void GetUser(){

        String URL_CHECK_EMAIL = BaseUrl + "api/profil/getuser";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CHECK_EMAIL,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");

                            if (message.equals("success")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String nama = object.getString("nama_siswa").trim();
                                    String photo = object.getString("foto_siswa").trim();

                                    Tusername.setText(nama);


                                    if (photo.equals("null")) {
                                        foto_profil.setImageResource(R.drawable.ic_baseline_account_circle_24);
                                    } else {
                                        Picasso.with(MainActivity.this).load(BaseUrl + "assets/foto_siswa/" + photo).into(foto_profil);
                                    }
                                }
                            } else {
                                Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                            Toast.makeText(MainActivity.this, "Error" + e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(MainActivity.this, "Error Response" + error.toString(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("NIS", NIS);
                params.put("Content-Type", "application/x-www-form-urlencoded");

                return params;
            }
        };
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