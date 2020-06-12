package com.recepyesilkaya.bugunkuyemegim.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.recepyesilkaya.bugunkuyemegim.R;
import com.recepyesilkaya.bugunkuyemegim.model.yemekModel;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RowHolder> {

    private ArrayList<yemekModel> yemekList;
    private String[][] yemekDizi;
    int diziSira = 0;


    public RecyclerViewAdapter(ArrayList<yemekModel> yemekList, String[][] yemekDizi) {
        this.yemekList = yemekList;
        this.yemekDizi = yemekDizi;
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
        return yemekDizi.length;
    }

    public class RowHolder extends RecyclerView.ViewHolder {

        TextView txt_yemekad, txt_sure, txt_kisiSayisi;
        ImageView img_yemekResim;

        public RowHolder(@NonNull View itemView) {
            super(itemView);

        }



        public void bind(yemekModel yemek_Model, Integer position) {


            System.out.println(yemek_Model.getYemek_resim());
            //cryptoModel daki değerleri gösteriyoruz


            //Text leri Layout daki id ler ile eşleştiriyoruz
            txt_yemekad = itemView.findViewById(R.id.txt_yemekAdi);
            txt_sure = itemView.findViewById(R.id.txt_sure);
            txt_kisiSayisi = itemView.findViewById(R.id.txt_kisiSayisi);
            img_yemekResim = itemView.findViewById(R.id.img_yemekResim);


            //gezinme sırasında sürekli aktif olduğundan modunu aldık
            txt_yemekad.setText(yemekDizi[diziSira%yemekDizi.length][1]);
            txt_sure.setText(yemekDizi[diziSira%yemekDizi.length][4]);
            txt_kisiSayisi.setText(yemekDizi[diziSira%yemekDizi.length][5]);


            diziSira++;

            //img_yemekResim.setText(yemek_Model.getYemek_adi());


        }


    }


}
