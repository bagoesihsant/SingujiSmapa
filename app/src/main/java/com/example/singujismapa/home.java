package com.example.singujismapa;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class home extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment home.
     */
    // TODO: Rename and change types and number of parameters
    public static home newInstance(String param1, String param2) {
        home fragment = new home();
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

    private RecyclerView recyclerView;
    private HomeAdapter adapter;
    private List<HomeItem> homeitem;
    private final String URL_TASK =  "http://192.168.43.219/SingujiSmapa/folder_php/home.php";

    public home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.list_data);
        homeitem = new ArrayList<>();
        extractHome();

        return view;
    }

    private void extractHome()
    {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, URL_TASK, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++)
                        {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                               HomeItem md = new HomeItem();
                                md.setNamaData(jsonObject.getString("mata_pelajaran"));
                                md.setNamaGuru(jsonObject.getString("nama_guru"));

                                homeitem.add(md);
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(getActivity().getApplicationContext(), "Error" + e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                        adapter = new HomeAdapter(getActivity().getApplicationContext(), homeitem);
                        recyclerView.setAdapter(adapter);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity().getApplicationContext(), "Error" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(jsonRequest);
    }

}