package com.passershowe.gank.presenter;

import android.content.Context;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.passershowe.gank.R;
import com.passershowe.gank.constant.Constant;
import com.passershowe.gank.data.GankRetrofit;
import com.passershowe.gank.data.entity.Gank;
import com.passershowe.gank.data.entity.GankData;
import com.passershowe.gank.ui.allmain.AllContract;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by PassersHowe on 2016/8/27.
 */

public class AllPresenter implements AllContract.Presenter {
    private static final String TAG = "AllPresenter";
    private AllContract.View mAllView;

    private int mPage;
    private CompositeSubscription compositeSubscription;
    private static List<Gank> mGankList;
    private String mGankType;
    private Context mContext;
    public AllPresenter(Context context, AllContract.View allView, String gankType) {

        this.mAllView = allView;
        this.mContext = context;
        mAllView.setPresenter(this);
        compositeSubscription = new CompositeSubscription();
        mPage = 1;
        mGankType = gankType;
        mGankList = new ArrayList<>();
    }


    @Override
    public void start() {
        loadGankData();
    }

    @Override
    public int getPage() {
        return mPage;
    }

    @Override
    public void setPage(int page) {
        mPage = page;
    }

    @Override
    public void setGankType(String gankType) {
        mGankType = gankType;
    }

    @Override
    public void loadGankData() {
        mAllView.setLoadingIndicator(true);
        //已订阅的观察者中拿数据
        Subscriber<GankData> subscriber = new Subscriber<GankData>() {
            @Override
            public void onCompleted() {
                mAllView.setLoadingIndicator(false);

            }

            @Override
            public void onError(Throwable e) {
                mAllView.setLoadingIndicator(false);
                mAllView.showError(mContext.getResources().getString(R.string.failed_connect_server));
            }

            @Override
            public void onNext(GankData gankData) {

                if (gankData.isError()) {
                    mAllView.showNotGankData();

                } else {
                    mAllView.hideNotDataText();
                    mAllView.showRecyclerGank(gankData.getResults());
                    mGankList.addAll(gankData.getResults());
                }

            }
        };
        //网络获取数据及订阅观察者
        GankRetrofit.getInstance().getGankData(subscriber, mGankType, Constant.DATA_COUNT, mPage);

    }

    @Override
    public void refreshGankData(final boolean refresh) {
        mAllView.setLoadingIndicator(true);
        if (refresh) {
            //直接从list里拿数据，延迟1秒,用户体验(～￣▽￣)～
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mAllView.showLoadMoreGankSuccess(mGankList, true);
//                    mAllView.setLoadingIndicator(false);
//                }
//            }, 1000);

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mAllView.showLoadMoreGankSuccess(mGankList, true);
                    mAllView.setLoadingIndicator(false);
                }

            }, 1000);
        } else {
            //获取网络数据及订阅观察者
            compositeSubscription.add(GankRetrofit.getInstance().getGankData(mGankType, Constant.DATA_COUNT, mPage)
                    .subscribe(new Subscriber<GankData>() {
                        @Override
                        public void onCompleted() {
                            mAllView.setLoadingIndicator(false);
                        }

                        @Override
                        public void onError(Throwable e) {
                            mAllView.setLoadingIndicator(false);
                            mAllView.showLoadMoreGankerror();
                        }

                        @Override
                        public void onNext(GankData gankData) {
                            mAllView.showLoadMoreGankSuccess(gankData.getResults(), false);
                            mGankList.addAll(gankData.getResults());
                        }
                    }));
        }
    }

}
