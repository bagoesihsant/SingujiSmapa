package com.example.singujismapa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActivityNilai extends AppCompatActivity {

    TextView textScore;
    Button backButton;
    String URL_NILAI = "http://192.168.43.132/SiNgujiSmapa-Web/api/exam/grade";
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nilai);

        initComponent();

        intent = getIntent();

        String nis = intent.getStringExtra("NIS");
        String idUjian = intent.getStringExtra("id_ujian");

        getNilai(nis, idUjian);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ActivityNilai.this, MainActivity.class));
            }
        });

    }

    private void initComponent()
    {
        textScore = findViewById(R.id.textGrade);
        backButton = findViewById(R.id.btnKembali);
    }

    private void getNilai(final String nis, final String idUjian)
    {
        RequestQueue requestQueue = Volley.newRequestQueue(ActivityNilai.this);
        StringRequest request = new StringRequest(Request.Method.POST, URL_NILAI,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean status = jsonObject.getBoolean("error");

                            if(!status)
                            {
                                textScore.setText(String.valueOf(jsonObject.getInt("user_score")));
                            }else
                            {
                                Toast.makeText(ActivityNilai.this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("nis", nis);
                params.put("idUjian", idUjian);
                return params;
            }
        };
        requestQueue.add(request);
    }

}