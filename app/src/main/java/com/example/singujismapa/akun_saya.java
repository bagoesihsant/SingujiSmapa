package com.example.singujismapa;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.singujismapa.Helper.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link akun_saya#newInstance} factory method to
 * create an instance of this fragment.
 */
public class akun_saya extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button edit;
    private EditText Ejurusan, Ekelas, Esemester, Ejenis_kelamin, Eusername, Epassword;
    private TextView Tnama;
    private Bitmap bitmap;
    private SessionManager sessionManager;
    private ProgressBar progressBar;
    private String NIS, BaseUrl;
    private ProgressDialog progressDialog;
    CircleImageView foto_profil;
    private Boolean issetImportantValue;
    private String tmpJurusan, tmpKelas, tmpSemester, tmpJenis_kelamin, tmpUsername, tmpPassword;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Spinner spinnerJK;
    private Spinner spinnerSM;

    public akun_saya() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment akun_saya.
     */
    // TODO: Rename and change types and number of parameters
    public static akun_saya newInstance(String param1, String param2) {
        akun_saya fragment = new akun_saya();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_akun_saya, container, false);
        edit = root.findViewById(R.id.edit);
        Ejurusan = root.findViewById(R.id.jurusan);
        Ekelas = root.findViewById(R.id.kelas);
        //Esemester = root.findViewById(R.id.semester);
        spinnerJK = root.findViewById(R.id.jenis_kelamin);
        spinnerSM = root.findViewById(R.id.semester);
        Eusername = root.findViewById(R.id.username);
        Epassword = root.findViewById(R.id.password);
        Tnama = root.findViewById(R.id.nama);
        foto_profil = root.findViewById(R.id.foto_profil);

        BaseUrl = SessionManager.BASE_URL;
        sessionManager = new SessionManager(getContext());
        progressDialog = new ProgressDialog(getContext());

        HashMap<String, String> user = sessionManager.getUserDetail();
        NIS = user.get(SessionManager.ID);

        foto_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
                }
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEmptyValue();
                if (issetImportantValue) {
                    UpdateUser();
                } else {
                    Toast.makeText(getContext(), "Mohon lengkapi semua data diatas!", Toast.LENGTH_LONG).show();
                }
            }
        });

        spinnerJK.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0){
                    tmpJenis_kelamin = "Laki laki";
                }else {
                    tmpJenis_kelamin = "Perempuan";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerSM.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==0){
                    tmpSemester = "Genap";
                }else {
                    tmpSemester = "Ganjil";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        GetUser();
        return root;
    }
    private void GetUser(){
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        String URL_CHECK_EMAIL = BaseUrl + "api/profil/getuser";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_CHECK_EMAIL,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String message = jsonObject.getString("message");

                            if (message.equals("success")) {
                                JSONArray jsonArray = jsonObject.getJSONArray("data");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String nama = object.getString("nama_siswa").trim();
                                    String jenis_kelamin = object.getString("jenis_kelamin").trim();
                                    String kelas = object.getString("kelas").trim();
                                    String jurusan = object.getString("id_jurusan").trim();
                                    String semester = object.getString("semester").trim();
                                    String username = object.getString("username_siswa").trim();
                                    String password = object.getString("password_siswa").trim();
                                    String photo = object.getString("foto_siswa").trim();

                                    Tnama.setText(nama);
                                    Eusername.setText(username);
                                  //  Esemester.setText(semester);
                                    Epassword.setText(password);

                                    if (jurusan.equals("null")){
                                        Ejurusan.setText("");
                                    }else {
                                        Ejurusan.setText(jurusan);
                                    }

                                    if (kelas.equals("null")){
                                        Ekelas.setText("");
                                    }else{
                                        Ekelas.setText(kelas);
                                    }

                                    if (username.equals("null")){
                                        Eusername.setText("");
                                    }else {
                                        Eusername.setText(username);
                                    }

                                    if (jenis_kelamin.equals("Laki laki")){
                                        spinnerJK.setSelection(0);
                                        tmpJenis_kelamin = "Laki laki";
                                    }else {
                                        spinnerJK.setSelection(1);
                                        tmpJenis_kelamin = "Perempuan";
                                    }

                                    if (semester.equals("Genap")){
                                        spinnerSM.setSelection(0);
                                        tmpSemester = "Genap";
                                    }else {
                                        spinnerSM.setSelection(1);
                                        tmpSemester = "Ganjil";
                                    }

                                   if (photo.equals("null")){
                                        foto_profil.setImageResource(R.drawable.ic_baseline_account_circle_24);
                                   }else{
                                      Picasso.with(getContext()).load(BaseUrl + "assets/foto_siswa/" + photo).into(foto_profil);
                                   }
                                }
                            } else {
                                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Error" + e.toString(), Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "Error Response" + error.toString(), Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("NIS", NIS);
                params.put("Content-Type", "application/x-www-form-urlencoded");

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

        private void UpdateUser(){
            progressDialog.setMessage("Loading...");
            progressDialog.show();

            String URL_CHECK_EMAIL = BaseUrl + "api/profil/updateprofil";
            StringRequest stringRequest = new StringRequest(Request.Method.PUT, URL_CHECK_EMAIL,
                    new Response.Listener<String>() {
                        @RequiresApi(api = Build.VERSION_CODES.N)
                        @Override
                        public void onResponse(String response) {
                            progressDialog.dismiss();

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String message = jsonObject.getString("message");

                                if (message.equals("success")) {
                                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);

                                        Intent intent = new Intent(getContext(), MainActivity.class);
                                        intent.putExtra("NAVIGATION", "AKUN");
                                        startActivity(intent);
                                    }
                                } else {
                                    Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "Error" + e.toString(), Toast.LENGTH_LONG).show();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Error Response" + error.toString(), Toast.LENGTH_LONG).show();
                        }
                    })
            {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("NIS", NIS);
                    params.put("id_jurusan", tmpJurusan);
                    params.put("jenis_kelamin", tmpJenis_kelamin);
                    params.put("kelas", tmpKelas);
                    params.put("semester", tmpSemester);
                    params.put("username_siswa", tmpUsername);
                    params.put("password", tmpPassword);
                    params.put("Content-Type", "application/x-www-form-urlencoded");

                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(stringRequest);
        }

        private void checkEmptyValue(){
            tmpJurusan = Ejurusan.getText().toString().trim();
            tmpKelas = Ekelas.getText().toString().trim();
            tmpSemester = Esemester.getText().toString().trim();
            //tmpJenis_kelamin = spinnerJK.getText().toString().trim();
            tmpUsername = Eusername.getText().toString().trim();
            tmpPassword = Epassword.getText().toString().trim();

            if (TextUtils.isEmpty(tmpJurusan) || TextUtils.isEmpty(tmpKelas) ||
                    TextUtils.isEmpty(tmpSemester) || TextUtils.isEmpty(tmpUsername) || TextUtils.isEmpty(tmpPassword)){
                issetImportantValue = false;
            }else {
                issetImportantValue = true;
            }
        }


    }
