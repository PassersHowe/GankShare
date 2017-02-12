package com.passershowe.gank.ui.about;

import android.view.View;

import com.passershowe.gank.R;
import com.passershowe.gank.ui.base.BaseDrawLayoutActivity;
import com.passershowe.gank.utils.ActivityUtils;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by hao809395357 on 2016/9/27.
 */

public class AboutActivity extends BaseDrawLayoutActivity {
    @Override
    public void initAllView() {
        AboutFragment aboutFragment = (AboutFragment) getSupportFragmentManager().findFragmentById(R.id.base_frame);
        if (null == aboutFragment) {
            aboutFragment = AboutFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), aboutFragment, R.id.base_frame);
        }
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
