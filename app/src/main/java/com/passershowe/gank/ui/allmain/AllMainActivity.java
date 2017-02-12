package com.passershowe.gank.ui.allmain;

import android.util.Log;
import android.view.View;

import com.passershowe.gank.R;
import com.passershowe.gank.presenter.AllPresenter;
import com.passershowe.gank.ui.base.BaseDrawLayoutActivity;
import com.passershowe.gank.utils.ActivityUtils;
import com.umeng.analytics.MobclickAgent;

import static com.passershowe.gank.constant.Constant.KEY_TYPE;
import static com.passershowe.gank.constant.Constant.KEY_TYPE.KEY_TYPE_ALL;
import static com.passershowe.gank.constant.Constant.KEY_TYPE.KEY_TYPE_ANDROID;
import static com.passershowe.gank.constant.Constant.KEY_TYPE.KEY_TYPE_APP;
import static com.passershowe.gank.constant.Constant.KEY_TYPE.KEY_TYPE_EXPAND;
import static com.passershowe.gank.constant.Constant.KEY_TYPE.KEY_TYPE_FOREEND;
import static com.passershowe.gank.constant.Constant.KEY_TYPE.KEY_TYPE_IOS;
import static com.passershowe.gank.constant.Constant.KEY_TYPE.KEY_TYPE_RECOMMEND;
import static com.passershowe.gank.constant.Constant.KEY_TYPE.KEY_TYPE_REST;
import static com.passershowe.gank.constant.Constant.TYPE_ALL;

public class AllMainActivity extends BaseDrawLayoutActivity {
    private static final String TAG ="AllMainActivity";
    private AllContract.Presenter mPresenter;
    private String gank_type;


    @Override
    public void initAllView() {

        String type = gank_type == null ? TYPE_ALL : gank_type;
        AllMainFragment allMainFragment = (AllMainFragment) getSupportFragmentManager().findFragmentById(R.id.base_frame);

        if (null == allMainFragment) {
            allMainFragment = AllMainFragment.newInstance(type);
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), allMainFragment, R.id.base_frame);
        }

        mPresenter = new AllPresenter(getApplicationContext(),allMainFragment,type);
        getFloatingActionButton().setVisibility(View.INVISIBLE);
    }

    @Override
    public void obtainIntentData() {
       String keyType =  getIntent().getStringExtra(KEY_TYPE);
        Log.e(TAG,"keyType:"+keyType);
        if (keyType != null) {
            switch (keyType) {
                case KEY_TYPE_ALL:
                    gank_type = getIntent().getStringExtra(KEY_TYPE_ALL);
                    break;
                case KEY_TYPE_ANDROID:
                    gank_type = getIntent().getStringExtra(KEY_TYPE_ANDROID);
                    break;
                case KEY_TYPE_APP:
                    gank_type = getIntent().getStringExtra(KEY_TYPE_APP);
                    break;
                case KEY_TYPE_EXPAND:
                    gank_type = getIntent().getStringExtra(KEY_TYPE_EXPAND);
                    break;
                case KEY_TYPE_FOREEND:
                    gank_type = getIntent().getStringExtra(KEY_TYPE_FOREEND);
                    break;
                case KEY_TYPE_IOS:
                    gank_type = getIntent().getStringExtra(KEY_TYPE_IOS);
                    break;
                case KEY_TYPE_REST:
                    gank_type = getIntent().getStringExtra(KEY_TYPE_REST);
                    break;
                case KEY_TYPE_RECOMMEND:
                    gank_type = getIntent().getStringExtra(KEY_TYPE_RECOMMEND);
                    break;
            }
        }


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
