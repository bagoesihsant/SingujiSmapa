package com.example.singujismapa;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LupaPasswordActivity extends AppCompatActivity {
    //ProgressDialog pDialog;
    private EditText email;
    private TextView tvKembaliLogin;
    private Context context;
    Button btnKirim;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lupa_password);

        email = findViewById(R.id.email);
        tvKembaliLogin = findViewById(R.id.tvKembali);

        btnKirim = findViewById(R.id.btnKirim);

        tvKembaliLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LupaPasswordActivity.this, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

    }
}
