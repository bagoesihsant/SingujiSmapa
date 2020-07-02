package com.example.singujismapa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.singujismapa.Helper.Soal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityUjianGanda extends AppCompatActivity {

    TextView textSubject, textQuizCount, textSoal, prevButton, nextButton;
    RadioGroup radioGroup;
    RadioButton rbA, rbB, rbC, rbD, rbE;
    Button submitJawaban, selesaiUjian;

    Intent intentSoal;

    private static String URL_SOAL = "http://192.168.43.132/SiNgujiSmapa-Web/api/exam/questions";

    List<Soal> soalList;
    List<Soal> kunciJawaban;
    List<List<Soal>> opsi;

    List<Soal> subOpsi;

    List<String> jawaban;

    int counter = 0;
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ujian_ganda);

        initComponent();

        intentSoal = getIntent();

        String jenisSoal = intentSoal.getStringExtra("id_jenis_soal");
        String mapel = intentSoal.getStringExtra("subject_quiz");

        soalList = new ArrayList<>();
        kunciJawaban = new ArrayList<>();
        opsi = new ArrayList<List<Soal>>();
        subOpsi = new ArrayList<>();
        jawaban = new ArrayList<>();

        getSoal(jenisSoal, mapel);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextSoal();
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prevSoal();
            }
        });

        submitJawaban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanJawaban();
            }
        });

        selesaiUjian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for (int i = 0; i < jawaban.size(); i++)
                {
                    Toast.makeText(ActivityUjianGanda.this, "Jawaban ke " + i + " adalah : " + jawaban.get(i).toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void initComponent()
    {
        textSubject = findViewById(R.id.textQuizSubject);
        textQuizCount = findViewById(R.id.textQuizCounter);
        textSoal = findViewById(R.id.textQuizQuestion);

        radioGroup = findViewById(R.id.radioGroup);
        rbA = findViewById(R.id.radioButtonA);
        rbB = findViewById(R.id.radioButtonB);
        rbC = findViewById(R.id.radioButtonC);
        rbD = findViewById(R.id.radioButtonD);
        rbE = findViewById(R.id.radioButtonE);

        submitJawaban = findViewById(R.id.submitAnswerButton);
        selesaiUjian = findViewById(R.id.finishAnswerButton);

        prevButton = findViewById(R.id.prevButton);
        nextButton = findViewById(R.id.nextButton);

    }

    private void startExam(String subjectQuiz)
    {
        textSubject.setText(subjectQuiz);
        textQuizCount.setText(String.valueOf(counter + 1));
        textSoal.setText(soalList.get(counter).getPertanyaan());

        List<Soal> subOpsi = opsi.get(counter);

        rbA.setText(subOpsi.get(counter).getOpsi_a());
        rbB.setText(subOpsi.get(counter).getOpsi_b());
        rbC.setText(subOpsi.get(counter).getOpsi_c());
        rbD.setText(subOpsi.get(counter).getOpsi_d());
        rbE.setText(subOpsi.get(counter).getOpsi_e());

    }

    private void nextSoal()
    {

        if(counter >= (soalList.size() - 1))
        {
            Toast.makeText(this, "Anda mencapai soal terakhir", Toast.LENGTH_SHORT).show();
        }else
        {
            counter++;

            textQuizCount.setText(String.valueOf(counter + 1));
            textSoal.setText(soalList.get(counter).getPertanyaan());

            List<Soal> subOpsi = opsi.get(counter);

            radioGroup.clearCheck();

           if(jawaban.get(counter).equals("A"))
           {
               rbA.setText(subOpsi.get(counter).getOpsi_a());
               rbA.setChecked(true);

               rbB.setText(subOpsi.get(counter).getOpsi_b());
               rbC.setText(subOpsi.get(counter).getOpsi_c());
               rbD.setText(subOpsi.get(counter).getOpsi_d());
               rbE.setText(subOpsi.get(counter).getOpsi_e());
           }else if(jawaban.get(counter).equals("B"))
           {
               rbB.setText(subOpsi.get(counter).getOpsi_b());
               rbB.setChecked(true);

               rbA.setText(subOpsi.get(counter).getOpsi_a());
               rbC.setText(subOpsi.get(counter).getOpsi_c());
               rbD.setText(subOpsi.get(counter).getOpsi_d());
               rbE.setText(subOpsi.get(counter).getOpsi_e());
           }else if(jawaban.get(counter).equals("C"))
           {
               rbC.setText(subOpsi.get(counter).getOpsi_c());
               rbC.setChecked(true);

               rbA.setText(subOpsi.get(counter).getOpsi_a());
               rbB.setText(subOpsi.get(counter).getOpsi_b());
               rbD.setText(subOpsi.get(counter).getOpsi_d());
               rbE.setText(subOpsi.get(counter).getOpsi_e());
           }else if(jawaban.get(counter).equals("D"))
           {
               rbD.setText(subOpsi.get(counter).getOpsi_d());
               rbD.setChecked(true);

               rbA.setText(subOpsi.get(counter).getOpsi_a());
               rbB.setText(subOpsi.get(counter).getOpsi_b());
               rbC.setText(subOpsi.get(counter).getOpsi_c());
               rbE.setText(subOpsi.get(counter).getOpsi_e());
           }else if(jawaban.get(counter).equals("E"))
           {
               rbE.setText(subOpsi.get(counter).getOpsi_e());
               rbE.setChecked(true);

               rbA.setText(subOpsi.get(counter).getOpsi_a());
               rbB.setText(subOpsi.get(counter).getOpsi_b());
               rbC.setText(subOpsi.get(counter).getOpsi_c());
               rbD.setText(subOpsi.get(counter).getOpsi_d());
           }else
           {
               rbA.setText(subOpsi.get(counter).getOpsi_a());
               rbB.setText(subOpsi.get(counter).getOpsi_b());
               rbC.setText(subOpsi.get(counter).getOpsi_c());
               rbD.setText(subOpsi.get(counter).getOpsi_d());
               rbE.setText(subOpsi.get(counter).getOpsi_e());
           }

        }

    }

    private void prevSoal()
    {

        if(counter <= 0)
        {
            Toast.makeText(this, "Anda sudah mencapai soal pertama", Toast.LENGTH_SHORT).show();
        }else
        {

            counter--;

            textQuizCount.setText(String.valueOf(counter + 1));
            textSoal.setText(soalList.get(counter).getPertanyaan());

            List<Soal> subOpsi = opsi.get(counter);

            radioGroup.clearCheck();

            if(jawaban.get(counter).equals("A"))
            {
                rbA.setText(subOpsi.get(counter).getOpsi_a());
                rbA.setChecked(true);

                rbB.setText(subOpsi.get(counter).getOpsi_b());
                rbC.setText(subOpsi.get(counter).getOpsi_c());
                rbD.setText(subOpsi.get(counter).getOpsi_d());
                rbE.setText(subOpsi.get(counter).getOpsi_e());
            }else if(jawaban.get(counter).equals("B"))
            {
                rbB.setText(subOpsi.get(counter).getOpsi_b());
                rbB.setChecked(true);

                rbA.setText(subOpsi.get(counter).getOpsi_a());
                rbC.setText(subOpsi.get(counter).getOpsi_c());
                rbD.setText(subOpsi.get(counter).getOpsi_d());
                rbE.setText(subOpsi.get(counter).getOpsi_e());
            }else if(jawaban.get(counter).equals("C"))
            {
                rbC.setText(subOpsi.get(counter).getOpsi_c());
                rbC.setChecked(true);

                rbA.setText(subOpsi.get(counter).getOpsi_a());
                rbB.setText(subOpsi.get(counter).getOpsi_b());
                rbD.setText(subOpsi.get(counter).getOpsi_d());
                rbE.setText(subOpsi.get(counter).getOpsi_e());
            }else if(jawaban.get(counter).equals("D"))
            {
                rbD.setText(subOpsi.get(counter).getOpsi_d());
                rbD.setChecked(true);

                rbA.setText(subOpsi.get(counter).getOpsi_a());
                rbB.setText(subOpsi.get(counter).getOpsi_b());
                rbC.setText(subOpsi.get(counter).getOpsi_c());
                rbE.setText(subOpsi.get(counter).getOpsi_e());
            }else if(jawaban.get(counter).equals("E"))
            {
                rbE.setText(subOpsi.get(counter).getOpsi_e());
                rbE.setChecked(true);

                rbA.setText(subOpsi.get(counter).getOpsi_a());
                rbB.setText(subOpsi.get(counter).getOpsi_b());
                rbC.setText(subOpsi.get(counter).getOpsi_c());
                rbD.setText(subOpsi.get(counter).getOpsi_d());
            }else
            {
                rbA.setText(subOpsi.get(counter).getOpsi_a());
                rbB.setText(subOpsi.get(counter).getOpsi_b());
                rbC.setText(subOpsi.get(counter).getOpsi_c());
                rbD.setText(subOpsi.get(counter).getOpsi_d());
                rbE.setText(subOpsi.get(counter).getOpsi_e());
            }

        }

    }

    private void simpanJawaban()
    {
        if(rbA.isChecked())
        {
            String answer = "A";
            jawaban.set(counter, answer);
        }else if(rbB.isChecked())
        {
            String answer = "B";
            jawaban.set(counter, answer);
        }else if(rbC.isChecked())
        {
            String answer = "C";
            jawaban.set(counter, answer);
        }else if(rbD.isChecked())
        {
            String answer = "D";
            jawaban.set(counter, answer);
        }else if(rbE.isChecked())
        {
            String answer = "E";
            jawaban.set(counter, answer);
        }
    }

    private void setJawabanLength(int jumlahSoal)
    {
        for(int i = 0; i < jumlahSoal; i++)
        {
            jawaban.add(i, "");
        }
    }

    private void getSoal(final String id_jenis_soal, final String paramMapel)
    {

        RequestQueue requestQueue = Volley.newRequestQueue(ActivityUjianGanda.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SOAL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("question");

                            for(int i = 0; i < jsonArray.length(); i++)
                            {
                                JSONObject questionObject = jsonArray.getJSONObject(i);

                                Soal listSoal = new Soal();
                                listSoal.setId_soal(questionObject.getString("id_soal"));
                                listSoal.setPertanyaan(htmlToText(questionObject.getString("pertanyaan")));

                                soalList.add(listSoal);

                                Soal listJawaban = new Soal();
                                listJawaban.setKunci_jawaban(htmlToText(questionObject.getString("kunci_jawaban")));

                                kunciJawaban.add(listJawaban);

                                Soal listOpsi = new Soal();
                                listOpsi.setOpsi_a(htmlToText(questionObject.getString("opsi_a")));
                                listOpsi.setOpsi_b(htmlToText(questionObject.getString("opsi_b")));
                                listOpsi.setOpsi_c(htmlToText(questionObject.getString("opsi_c")));
                                listOpsi.setOpsi_d(htmlToText(questionObject.getString("opsi_d")));
                                listOpsi.setOpsi_e(htmlToText(questionObject.getString("opsi_e")));

                                subOpsi.add(listOpsi);
                                opsi.add(subOpsi);

//                                soal.setId_paket(questionObject.getString("id_paket"));
//                                soal.setId_jenis_soal(questionObject.getString("id_jenis_soal"));
//                                soal.setPertanyaan(questionObject.getString("pertanyaan"));
//                                soal.setOpsi_a(questionObject.getString("opsi_a"));
//                                soal.setOpsi_b(questionObject.getString("opsi_b"));
//                                soal.setOpsi_c(questionObject.getString("opsi_c"));
//                                soal.setOpsi_d(questionObject.getString("opsi_d"));
//                                soal.setOpsi_e(questionObject.getString("opsi_e"));
//                                soal.setKunci_jawaban(questionObject.getString("kunci_jawaban"));

                                startExam(paramMapel);

                            }


                            setJawabanLength(jsonObject.getInt("question_count"));

                        } catch (JSONException e) {
                            Log.e("JSON Error", e.toString());
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Error", error.toString());
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<>();
                params.put("jenisSoal",id_jenis_soal);
                return params;
            }
        };

        requestQueue.add(stringRequest);

    }

    private static String htmlToText(String html)
    {
        return android.text.Html.fromHtml(html).toString();
    }

}