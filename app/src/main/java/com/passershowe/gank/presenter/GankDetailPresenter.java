package com.passershowe.gank.presenter;

import android.graphics.Bitmap;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.passershowe.gank.ui.detail.GankDetailContract;

import static android.view.View.GONE;

/**
 * Created by PassersHowe on 2016/9/2.
 */

public class GankDetailPresenter implements GankDetailContract.Presenter {
    private static final String TAG = "GankDetailPresenter";
    private GankDetailContract.View mGankDetailView;
    private WebView mWebView;

    public GankDetailPresenter(GankDetailContract.View gankDetailView, WebView webView) {
        mGankDetailView = gankDetailView;
        mWebView = webView;
    }


    @Override
    public void settingWebView() {
        WebSettings settings = mWebView.getSettings();
        //是否支持"视口"("ViewPort")
        settings.setUseWideViewPort(true);
        //设置是否在概述模式，即WebView加载页面，缩小内容以适合屏幕宽度
        settings.setLoadWithOverviewMode(true);
        //是否启用JavaScript的执行，默认false
        settings.setJavaScriptEnabled(true);
        //设置是否启用应用程序缓存的应用程序缓存,默认false
        settings.setAppCacheEnabled(true);
        //设置基本布局算法,默认NARROW_COLUMNS
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        //设置是否WebView应该支持缩放使用屏幕上的变焦控制和手势,默认true
        settings.setSupportZoom(true);
        //WebView加载给定的url
        mWebView.loadUrl(mGankDetailView.obtainUrl());

        //解析，渲染网页
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                mWebView.setWebChromeClient(new GankWebChromeClient());
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });
    }

    /**
     * 设置打开网页时的进度条显示
     */
    private class GankWebChromeClient extends WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //当进度newProgress == 100时,隐藏进度条
            if (newProgress == 100) {
                mGankDetailView.showProgressGONE();
            } else {
                //判断进度条的状态
                if (mGankDetailView.getProgressVisibility() == GONE) {
                    mGankDetailView.showProgressVISIBLE();
                }
                //设置进度条进度值
                mGankDetailView.showProgress(newProgress);
            }

        }

    }


    @Override
    public void start() {
        settingWebView();
    }
}
