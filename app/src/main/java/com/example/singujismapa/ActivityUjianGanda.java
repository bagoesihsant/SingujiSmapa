package com.example.singujismapa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ActivityUjianGanda extends AppCompatActivity {

    TextView textSubject, textQuizCount, textSoal;
    RadioGroup radioGroup;
    RadioButton rbA, rbB, rbC, rbD, rbE;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ujian_ganda);
    }
}