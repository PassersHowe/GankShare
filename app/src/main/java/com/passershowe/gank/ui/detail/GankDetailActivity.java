package com.passershowe.gank.ui.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.passershowe.gank.R;
import com.passershowe.gank.presenter.GankDetailPresenter;
import com.umeng.analytics.MobclickAgent;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by PassersHowe on 2016/8/31.
 *
 *
 */

public class GankDetailActivity extends AppCompatActivity implements GankDetailContract.View {
    private static final String TAG = "GankDetailActivity";
    @BindView(R.id.detail_web)
    WebView detailWeb;
    @BindView(R.id.detail_toolbar)
    Toolbar detailToolbar;
    @BindView(R.id.detail_progress)
    ProgressBar detailProgress;
    private GankDetailContract.Presenter mPresenter;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        ButterKnife.bind(this);
        url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");
        //设置标题
        detailToolbar.setTitle(title);
        setSupportActionBar(detailToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPresenter = new GankDetailPresenter(this, detailWeb);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setPresenter(GankDetailContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public String obtainUrl() {
        return url;
    }

    @Override
    public void showProgressGONE() {
        detailProgress.setVisibility(GONE);
    }

    @Override
    public void showProgressVISIBLE() {
        detailProgress.setVisibility(VISIBLE);
    }

    @Override
    public void showProgress(int progress) {

        detailProgress.setProgress(progress);
    }

    @Override
    public int getProgressVisibility() {

        return detailProgress.getVisibility();
    }





}
