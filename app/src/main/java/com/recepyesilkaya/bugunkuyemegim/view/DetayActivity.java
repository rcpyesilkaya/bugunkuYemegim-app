package com.recepyesilkaya.bugunkuyemegim.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.recepyesilkaya.bugunkuyemegim.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetayActivity extends AppCompatActivity {

    TextView txt_sure, txt_kisi, txt_malzeme, txt_yapilis, txt_video, txt_yemekAadi;
    ImageView fotograf, img_favori;

    String video, id;
    Boolean favori = false;

    public static List<String> items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detay);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        String yemekAd = (String) i.getSerializableExtra("yemekAd");
        String yemekAciklama = (String) i.getSerializableExtra("yemekAciklama");
        String yemekTur = (String) i.getSerializableExtra("yemekTur");
        String yemekSure = (String) i.getSerializableExtra("yemekSure");
        String kisiSayisi = (String) i.getSerializableExtra("kisiSayisi");
        video = (String) i.getSerializableExtra("video");
        String resim = (String) i.getSerializableExtra("resim");
        String malzeme = (String) i.getSerializableExtra("malzeme");
        id = (String) i.getSerializableExtra("id");


        txt_sure = findViewById(R.id.txt_sure);
        txt_kisi = findViewById(R.id.txt_kisiSayisi);
        txt_malzeme = findViewById(R.id.txt_malzeme);
        txt_yapilis = findViewById(R.id.txt_yapilis);
        txt_video = findViewById(R.id.txt_video);
        fotograf = findViewById(R.id.img_yemekResim);
        txt_yemekAadi = findViewById(R.id.txt_yemekAdi);
        img_favori = findViewById(R.id.img_favori);

        txt_yemekAadi.setText(yemekAd);
        txt_sure.setText(yemekSure);
        txt_kisi.setText(kisiSayisi);
        txt_malzeme.setText(malzeme);
        txt_yapilis.setText(yemekAciklama);
        txt_video.setText(video);
        Picasso.get().load(resim).into(fotograf);


        //sharedPreferences ile favorideki yemek id leri bulunuyor eğer yemek favorilerde ise img_favori set ediliyor
        SharedPreferences sharedPreferences = getSharedPreferences("pref", 0);
        String idsString = sharedPreferences.getString("id", "");
        String[] itemsWords = idsString.split(",");
        items = new ArrayList<>();

        for (int k = 0; k < itemsWords.length; k++) {
            items.add(itemsWords[k]);
        }
        for (int j = 0; j < items.size(); j++) {
            if (items.get(j).equals(id)) {
                img_favori.setImageResource(R.drawable.ic_favorite);
                favori = true;
            }
        }
        if (!favori) {
            img_favori.setImageResource(R.drawable.ic_no_favorite);
        }

    }

    public void video_link(View view) {
        Uri linkimiz = Uri.parse(video); //butona vermek istediğimiz linki buraya yazıyoruz.
        Intent intentimiz = new Intent(Intent.ACTION_VIEW, linkimiz);
        startActivity(intentimiz);

    }

    public void fovoriEkle(View view) {
        if (!favori) {
            items.add(id);
            StringBuilder stringBuilder = new StringBuilder();
            for (String k : items) {

                stringBuilder.append(k);
                stringBuilder.append(",");
            }
            SharedPreferences sharedPreferences = getSharedPreferences("pref", 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("id", stringBuilder.toString());
            editor.commit();

            favori=true;

            img_favori.setImageResource(R.drawable.ic_favorite);
        } else {
            items.remove(id);
            StringBuilder stringBuilder = new StringBuilder();
            for (String k : items) {

                stringBuilder.append(k);
                stringBuilder.append(",");
            }
            SharedPreferences sharedPreferences = getSharedPreferences("pref", 0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("id", stringBuilder.toString());
            editor.commit();

            img_favori.setImageResource(R.drawable.ic_no_favorite);
            favori=false;
        }
    }

}
