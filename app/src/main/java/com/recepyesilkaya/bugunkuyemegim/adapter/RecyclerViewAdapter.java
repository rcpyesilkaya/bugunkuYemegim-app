package com.recepyesilkaya.bugunkuyemegim.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.recepyesilkaya.bugunkuyemegim.R;
import com.recepyesilkaya.bugunkuyemegim.model.yemekModel;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RowHolder> {

    private ArrayList<yemekModel> yemekList;

    public RecyclerViewAdapter(ArrayList<yemekModel> yemekList) {
        this.yemekList = yemekList;
    }

    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View view = layoutInflater.inflate(R.layout.row_layout, parent, false);
        return new RowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RowHolder holder, int position) {
        holder.bind(yemekList.get(position), position);

    }

    @Override
    public int getItemCount() {
        return yemekList.size();
    }

    public class RowHolder extends RecyclerView.ViewHolder {

        TextView txt_yemekad,txt_sure,txt_kisiSayisi;
        ImageView img_yemekResim;

        public RowHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(yemekModel cryptoModel, Integer position) {

            //Text leri Layout daki id ler ile eşleştiriyoruz
            txt_yemekad = itemView.findViewById(R.id.txt_yemekAdi);
            txt_sure = itemView.findViewById(R.id.txt_sure);
            txt_kisiSayisi = itemView.findViewById(R.id.txt_kisiSayisi);
            img_yemekResim = itemView.findViewById(R.id.img_yemekResim);

            //cryptoModel daki değerleri gösteriyoruz
            txt_yemekad.setText(cryptoModel.getYemek_adi());
            txt_sure.setText(cryptoModel.getYemek_pisirme_suresi());
            txt_kisiSayisi.setText(cryptoModel.getYemek_kisi_sayisi());
            //img_yemekResim.setText(cryptoModel.getYemek_adi());
        }
    }
}
