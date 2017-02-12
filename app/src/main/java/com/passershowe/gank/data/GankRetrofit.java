package com.passershowe.gank.data;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.passershowe.gank.api.GankAPI;
import com.passershowe.gank.data.entity.GankData;
import com.passershowe.gank.data.service.GankService;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by PassersHowe on 2016/8/27.
 */

public class GankRetrofit {
    private static final String TAG = "GankRetrofit";
    static GankRetrofit gankRetrofit;
    private static final int DEFAULT_TIMEOUT = 5;
    private GankService gankService;
    final static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .serializeNulls()
            .create();
    public static GankRetrofit getInstance() {
        if (null == gankRetrofit) {
            synchronized (GankRetrofit.class) {
                if (null == gankRetrofit) {
                    gankRetrofit = new GankRetrofit();
                }
            }
        }
        return gankRetrofit;
    }

    private GankRetrofit() {

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClientBuilder.build())
                .baseUrl(GankAPI.GANK_HOME)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        this.gankService = retrofit.create(GankService.class);


    }

    /**
     * 网络获取数据并进行调度
     * @param subscriber
     * @param type 数据类型
     * @param count 每次获取的条数
     * @param page 页数
     */
    public void getGankData(Subscriber<GankData> subscriber, String type, int count, int page) {


        gankService.getGankData(type, count,page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }

    /**
     * 网络获取数据(参数可以忽略，根据API结构来设定参数)
     * @param type 数据类型
     * @param count 每次获取的条数
     * @param page 页数
     * @return
     */
    public Observable<GankData> getGankData( String type, int count, int page) {

       return gankService.getGankData(type, count,page)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());


    }






//    public void getGankData(String type, int count,int page){
//
//        Call<List<Gank>> repos = gankService.getGankData(type,count,page);
//        // clone
//        Log.e(TAG, "response:" + repos.request().toString());
//     5. 请求网络，异步
//        repos.enqueue(new Callback<List<Gank>>() {
//            @Override
//            public void onResponse(Call<List<Gank>> call, Response<List<Gank>> response) {
//                Log.e(TAG, "response:" + response.body());
//                textView.setText(response.body().toString());
//            }
//
//            @Override
//            public void onFailure(Call<List<Gank>> call, Throwable t) {
//                Log.e(TAG, "response:" + call.request().toString());
//            }
//        });
//
//    }
}
