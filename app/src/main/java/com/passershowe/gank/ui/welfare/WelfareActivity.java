package com.passershowe.gank.ui.welfare;

import android.view.View;

import com.passershowe.gank.R;
import com.passershowe.gank.presenter.WelfarePresenter;
import com.passershowe.gank.ui.base.BaseDrawLayoutActivity;
import com.passershowe.gank.utils.ActivityUtils;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by PassersHowe on 2016/9/1.
 */

public class WelfareActivity extends BaseDrawLayoutActivity {

    private WelfareContract.Presenter mPresenter;



    @Override
    public void initAllView() {

        WelfareFragment welfareFragment = (WelfareFragment) getSupportFragmentManager().findFragmentById(R.id.base_frame);
        if (null == welfareFragment) {
            welfareFragment = WelfareFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), welfareFragment, R.id.base_frame);
        }
        mPresenter = new WelfarePresenter(welfareFragment);
        getFloatingActionButton().setVisibility(View.INVISIBLE);
    }

    @Override
    public void obtainIntentData() {

    }

    @Override
    protected void onResume() {
        super.onResume();

        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
