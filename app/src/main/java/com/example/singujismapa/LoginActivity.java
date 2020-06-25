package com.example.singujismapa;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.singujismapa.Helper.SessionManager;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {
    private EditText vUsername, vPassword;
    private Context context;
    RequestQueue requestQueue;
    TextView tvlupa_password;
    Button btnLogin;
    ProgressDialog progressDialog ;
    ProgressBar bar;
    Boolean CheckEditText;
    String EmailHolder, PasswordHolder, BaseUrl, tokenDevice;
    SessionManager sessionManager;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        bar = findViewById(R.id.load);
        vUsername = findViewById(R.id.username);
        sessionManager = new SessionManager(this);
        requestQueue = Volley.newRequestQueue(LoginActivity.this);
        progressDialog = new ProgressDialog(LoginActivity.this);
        vPassword = findViewById(R.id.password);
        tvlupa_password = findViewById(R.id.tvLupaPassword);
        btnLogin = findViewById(R.id.idButton);

        BaseUrl = SessionManager.BASE_URL;

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CheckEditTextIsEmptyOrNot();

                if (CheckEditText) {
                    UserLogin();
                } else {
                    Toast.makeText(LoginActivity.this, "Mohon isi semua data", Toast.LENGTH_LONG).show();

                }

            }
        });
    }

    public void CheckEditTextIsEmptyOrNot(){

        //Getting values from EditText.
        EmailHolder = vUsername.getText().toString().trim();
        PasswordHolder = vPassword.getText().toString().trim();

        //Checking wheter EditText value is empty or not.
        if (TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder))
        {

            //if any of EditText is empty then set variable value as False.
            CheckEditText = false;

        }else{
            //if any of EditText is empty then set variable value as false.
            CheckEditText = true;
        }
    }

    public void UserLogin(){

        progressDialog.setMessage("Sek Cuy");
        progressDialog.show();


        String HttpUrl = BaseUrl + "api/Auth/login";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, HttpUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");

                            if (message.equals("success")){
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String id = object.getString("NIS").trim();
                                    sessionManager.createSession(id);


                                    Intent intent = new Intent(LoginActivity.this, HomeSinguji.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);

                                    finish();
                                }
                            } else {

                                Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();

                            Toast.makeText(LoginActivity.this, "Error" + e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        //Hiding the progress dialog after all task complete.
                        progressDialog.dismiss();

                        //Showing error message if something goes wrong.
                        Toast.makeText(LoginActivity.this, "Error Response" + volleyError.toString(), Toast.LENGTH_LONG).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {

                //Creating map string params.
                Map<String, String> params = new HashMap<>();

                //Adding all values to params.
                //The first argument should be same your MySql database table columns.
                params.put("username_siswa", EmailHolder);
                params.put("password_siswa", PasswordHolder);
                params.put("Content-Type","application/x-www-form-urlencoded");

                return params;
            }

        };

        //Creating RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding the StringRequest object into requestQueue.
        requestQueue.add(stringRequest);

    }
    boolean doubleBackPressed = false;
    @Override
    public void onBackPressed(){
        if (doubleBackPressed){
            finish();
            System.exit(0);
        }else{
            final ConstraintLayout constraintLayout = findViewById(R.id.constrainlogin);
            Snackbar.make(constraintLayout, "Tekan tombol kembali sekali lagi", Snackbar.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackPressed=false;
                }
            }, 2000);
        }
    }


}






