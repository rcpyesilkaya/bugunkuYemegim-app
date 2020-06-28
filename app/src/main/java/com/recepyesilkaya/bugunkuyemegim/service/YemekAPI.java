package com.recepyesilkaya.bugunkuyemegim.service;

import com.recepyesilkaya.bugunkuyemegim.model.yemekModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface YemekAPI {
    //https://ibrahimekinci.com/yemekapp/yemekler.json
    //https://github.com/rcpyesilkaya/Yemekler/blob/master/yemekler.json

    /*@GET("yemekapp/yemekler.json")
    Call<List<yemekModel>> getData();*/

    //get ile json daki bilgleri çekiyoruz metoda getData adını veriyoruz
    @GET("yemekapp/yemekler.json")
    Observable<List<yemekModel>> getData();
}
