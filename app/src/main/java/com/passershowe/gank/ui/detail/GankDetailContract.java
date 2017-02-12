package com.passershowe.gank.ui.detail;

import com.passershowe.gank.ui.base.BasePresenter;
import com.passershowe.gank.ui.base.BaseView;

/**
 * Created by PassersHowe on 2016/8/31.
 */

public interface GankDetailContract {
    interface View extends BaseView<Presenter> {
        //获取url地址
        String obtainUrl();

        //隐藏进度条
        void showProgressGONE();

        //显示进度条
        void showProgressVISIBLE();

        //设置进度条进度
        void showProgress(int progress);

        //获取进度条当前的显示状态
        int getProgressVisibility();
    }

    interface Presenter extends BasePresenter {
        //配置及加载WebView
        void settingWebView();
    }
}
