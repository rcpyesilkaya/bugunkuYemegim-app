package com.recepyesilkaya.bugunkuyemegim.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.recepyesilkaya.bugunkuyemegim.R;
import com.squareup.picasso.Picasso;

public class DetayActivity extends AppCompatActivity {

    TextView txt_sure, txt_kisi, txt_malzeme, txt_yapilis, txt_video, txt_yemekAadi;
    ImageView fotograf;

    String video;

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
        int position = (int) i.getSerializableExtra("id");


        txt_sure = findViewById(R.id.txt_sure);
        txt_kisi = findViewById(R.id.txt_kisiSayisi);
        txt_malzeme = findViewById(R.id.txt_malzeme);
        txt_yapilis = findViewById(R.id.txt_yapilis);
        txt_video = findViewById(R.id.txt_video);
        fotograf = findViewById(R.id.img_yemekResim);
        txt_yemekAadi = findViewById(R.id.txt_yemekAdi);

        txt_yemekAadi.setText(yemekAd);
        txt_sure.setText(yemekSure);
        txt_kisi.setText(kisiSayisi);
        txt_malzeme.setText(malzeme);
        txt_yapilis.setText(yemekAciklama);
        txt_video.setText(video);
        Picasso.get().load(resim).into(fotograf);


    }

    public void video_link(View view){
        Uri linkimiz=Uri.parse(video); //butona vermek istediğimiz linki buraya yazıyoruz.
        Intent intentimiz =new Intent(Intent.ACTION_VIEW,linkimiz);
        startActivity(intentimiz);

    }

}
