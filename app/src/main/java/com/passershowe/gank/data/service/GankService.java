package com.passershowe.gank.data.service;


import com.passershowe.gank.data.entity.GankData;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by PassersHowe on 2016/8/27.
 */

public interface GankService {

    //http://gank.io/api/data/Android/10/1
    @GET("data/{type}/{count}/{page}")
    Observable<GankData> getGankData(@Path("type") String type, @Path("count") int count, @Path("page") int page);



    //http://gank.io/api/day/2015/08/06
    @GET("day/{year}/{month}/{day}")
    Observable<GankData> getGankDataByDate(@Path("year") String year, @Path("month") String month, @Path("day") String day);


}
