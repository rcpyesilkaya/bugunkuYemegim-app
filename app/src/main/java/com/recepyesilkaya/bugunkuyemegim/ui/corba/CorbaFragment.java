package com.recepyesilkaya.bugunkuyemegim.ui.corba;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.recepyesilkaya.bugunkuyemegim.R;
import com.recepyesilkaya.bugunkuyemegim.adapter.RecyclerViewAdapter;
import com.recepyesilkaya.bugunkuyemegim.model.yemekModel;
import com.recepyesilkaya.bugunkuyemegim.service.YemekAPI;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CorbaFragment extends Fragment {

    RecyclerView recyclerView;

    RecyclerViewAdapter recyclerViewAdapter;

    CompositeDisposable compositeDisposable;

    ArrayList<yemekModel> yemekModels;
    private String baseURL = "https://ibrahimekinci.com/";
    Retrofit retrofit;

    private ToolsViewModel toolsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        toolsViewModel =
                ViewModelProviders.of(this).get(ToolsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_corba, container, false);

        recyclerView = root.findViewById(R.id.rcy_Corba);

        toolsViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                //Retrofit && JSON

                Gson gson = new GsonBuilder().setLenient().create();
                retrofit = new Retrofit.Builder()
                        .baseUrl(baseURL)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create(gson))
                        .build();

                //JSON verilerini çekeceğimiz metodu çağırıyoruz
                loadData();

            }
        });
        return root;
    }

    private void loadData() {

        YemekAPI yemekAPI = retrofit.create(YemekAPI.class);

        compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(yemekAPI.getData()
                //Observable sonucu yayınlanacak olacak işlemin hangi threadde çalışması gerektiğini belirtiyoruz
                .subscribeOn(Schedulers.io())
                //Subsriber hangi thread’de dinlemesi gerektiğini belirtiyoruz
                .observeOn(AndroidSchedulers.mainThread())
                //subscribe, Observable’a bir abone, abone olduğunda gerçekleştirilecek eylemi tanımlayan bir arabirimdir.
                //Abone olma yöntemi yalnızca bir Observer Observable’e abone olduğunda çalışır.
                .subscribe(this::handleResponse));
    }

    private void handleResponse(List<yemekModel> yemekList) {

        yemekModels = new ArrayList<>(yemekList);//cryptoModels ArrayList'imize responList deki değerleri kaydediyoruz.


        //kategori kontrolü yapıyoruz  ve ilgili bilgileri yemekDizi de tutuyoruz
        int urunSayisi = 0;
        String kategori = "corba";

        for (yemekModel s : yemekList) {
            if (kategori.equals(s.yemek_tur)) {
                urunSayisi++;
            }
        }

        String[][] yemekDizi = new String[urunSayisi--][9];

        int sira = 0;
        for (yemekModel s : yemekList) {
            if (kategori.equals(s.yemek_tur)) {
                yemekDizi[sira][0] = s.getYemek_id();
                yemekDizi[sira][1] = s.getYemek_adi();
                yemekDizi[sira][2] = s.getYemek_aciklama();
                yemekDizi[sira][3] = s.getYemek_tur();
                yemekDizi[sira][4] = s.getYemek_pisirme_suresi();
                yemekDizi[sira][5] = s.getYemek_kisi_sayisi();
                yemekDizi[sira][6] = s.getYemek_video();
                yemekDizi[sira][7] = s.getYemek_resim();
                yemekDizi[sira][8] = s.getYemek_malzeme();
                sira++;
            }
        }


        //RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewAdapter = new RecyclerViewAdapter(yemekModels, yemekDizi);
        recyclerView.setAdapter(recyclerViewAdapter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}