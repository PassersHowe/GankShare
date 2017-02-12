package com.passershowe.gank.presenter;

import android.os.Handler;
import android.util.Log;

import com.passershowe.gank.constant.Constant;
import com.passershowe.gank.data.GankRetrofit;
import com.passershowe.gank.data.entity.Gank;
import com.passershowe.gank.data.entity.GankData;
import com.passershowe.gank.ui.welfare.WelfareContract;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by PassersHowe on 2016/9/1.
 */

public class WelfarePresenter implements WelfareContract.Presenter {
    private static final String TAG = "WelfarePresenter";
    private WelfareContract.View mWelfareView;
    private int mPage;
    private CompositeSubscription compositeSubscription;
    private static List<Gank> mWelfareGankList;

    public WelfarePresenter(WelfareContract.View welfareView) {
        mWelfareView = welfareView;
        mWelfareView.setPresenter(this);
        compositeSubscription = new CompositeSubscription();
        mPage = 1;
        mWelfareGankList = new ArrayList<>();

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
    public void loadWelfareGankData() {
        mWelfareView.setLoadingIndicator(true);
        //已订阅的观察者中拿数据
        Subscriber<GankData> subscriber = new Subscriber<GankData>() {
            @Override
            public void onCompleted() {
                mWelfareView.setLoadingIndicator(false);
            }

            @Override
            public void onError(Throwable e) {
                mWelfareView.setLoadingIndicator(false);
            }

            @Override
            public void onNext(GankData gankData) {
                if (gankData.isError()) {
                    mWelfareView.showNoData();
                } else {
                    mWelfareView.hideNotDataText();
                    mWelfareView.showWelfareRecycler(gankData.getResults());
                    mWelfareGankList.addAll(gankData.getResults());
                }

            }
        };
        //网络获取数据及订阅观察者
        GankRetrofit.getInstance().getGankData(subscriber, Constant.TYPE_WELFARE, Constant.DATA_COUNT, mPage);
    }

    @Override
    public void refreshWelfareGankData(boolean refresh) {
        mWelfareView.setLoadingIndicator(true);
        if (refresh) {
            //直接从list里拿数据，延迟1秒,用户体验(～￣▽￣)～
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mWelfareView.showMoreWelfareGankSuccess(mWelfareGankList, true);
//                    mWelfareView.setLoadingIndicator(false);
//                }
//            }, 1000);
            new Handler().postDelayed(

                    new Runnable() {
                        @Override
                        public void run() {
                            mWelfareView.showMoreWelfareGankSuccess(mWelfareGankList, true);
                            mWelfareView.setLoadingIndicator(false);
                        }
                    }
                    , 1000);

        } else {
            //获取网络数据及订阅观察者
            compositeSubscription.add(GankRetrofit.getInstance().getGankData(Constant.TYPE_WELFARE, Constant.DATA_COUNT, mPage)
                    .subscribe(new Subscriber<GankData>() {
                        @Override
                        public void onCompleted() {
                            mWelfareView.setLoadingIndicator(false);
                        }

                        @Override
                        public void onError(Throwable e) {
                            mWelfareView.setLoadingIndicator(false);
                            mWelfareView.showMoreWelfareGankerror();
                        }

                        @Override
                        public void onNext(GankData gankData) {
                            mWelfareView.showMoreWelfareGankSuccess(gankData.getResults(), false);
                            mWelfareGankList.addAll(gankData.getResults());
                        }
                    }));
        }
    }

    @Override
    public void start() {
        loadWelfareGankData();
    }
}
