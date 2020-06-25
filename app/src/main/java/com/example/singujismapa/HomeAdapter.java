package com.example.singujismapa;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class HomeAdapter  extends RecyclerView.Adapter<HomeAdapter.HomeHolder>{
    LayoutInflater inflater;
    List<HomeItem> homeitem;

    public HomeAdapter(Context context, List<HomeItem> list)
    {
        this.inflater = LayoutInflater.from(context);
        this.homeitem = list;
    }

    @NonNull
    @Override
    public HomeAdapter.HomeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.home_list_layout, parent, false);
        return new HomeAdapter.HomeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.HomeHolder holder, int position) {
        holder.mata_pelajaran.setText(homeitem.get(position).getNamaData());
        holder.nama_guru.setText(homeitem.get(position).getNamaGuru());



    }

    @Override
    public int getItemCount() {
        return homeitem.size();
    }

    public class HomeHolder extends RecyclerView.ViewHolder
    {
        TextView mata_pelajaran, nama_guru;

        public HomeHolder(View itemView) {
            super(itemView);

            mata_pelajaran = (TextView) itemView.findViewById(R.id.nama_data_list);
            nama_guru = (TextView) itemView.findViewById(R.id.nama_guru);


        }
    }

}

