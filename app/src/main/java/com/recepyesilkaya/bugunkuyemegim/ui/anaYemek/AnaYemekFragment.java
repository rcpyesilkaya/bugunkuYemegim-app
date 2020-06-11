package com.recepyesilkaya.bugunkuyemegim.ui.anaYemek;

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
import com.recepyesilkaya.bugunkuyemegim.view.MainActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class AnaYemekFragment extends Fragment {

    RecyclerView recyclerView;

    RecyclerViewAdapter recyclerViewAdapter;

    CompositeDisposable compositeDisposable;

    ArrayList<yemekModel> yemekModels;
    private String baseURL = "https://ibrahimekinci.com/";
    Retrofit retrofit;

    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_anayemek, container, false);

        recyclerView = root.findViewById(R.id.recyclerView);

        galleryViewModel.getText().observe(this, new Observer<String>() {
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

        compositeDisposable=new CompositeDisposable();

       compositeDisposable.add(yemekAPI.getData()
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(this::handleResponse));
    }


    private void handleResponse(List<yemekModel> yemekList){

        yemekModels = new ArrayList<>(yemekList);//cryptoModels ArrayList'imize responList deki değerleri kaydediyoruz.

        //RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewAdapter = new RecyclerViewAdapter(yemekModels);
        recyclerView.setAdapter(recyclerViewAdapter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}