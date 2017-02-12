package com.passershowe.gank.ui.base;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.passershowe.gank.R;
import com.passershowe.gank.ui.about.AboutActivity;
import com.passershowe.gank.ui.allmain.AllMainActivity;

import com.passershowe.gank.ui.welfare.WelfareActivity;
import com.passershowe.gank.utils.ActivityUtils;
import butterknife.BindView;

import butterknife.ButterKnife;

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
import static com.passershowe.gank.constant.Constant.TYPE_ANDROID;
import static com.passershowe.gank.constant.Constant.TYPE_APP;
import static com.passershowe.gank.constant.Constant.TYPE_EXPAND;
import static com.passershowe.gank.constant.Constant.TYPE_FOREEND;
import static com.passershowe.gank.constant.Constant.TYPE_IOS;
import static com.passershowe.gank.constant.Constant.TYPE_RECOMMEND;
import static com.passershowe.gank.constant.Constant.TYPE_REST;

/**
 * Created by PassersHowe on 2016/8/27.
 *
 *AS自动生成的 DrawLayouლ(╹◡╹ლ)
 */

public abstract class BaseDrawLayoutActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.base_toolbar)
    Toolbar toolbar;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_activity_main);
        ButterKnife.bind(this);
        obtainIntentData();
        setSupportActionBar(toolbar);
        initAllView();


        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);


        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);


    }

    public FloatingActionButton getFloatingActionButton() {
        return fab;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }





    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        //item的id监听
        selectedMenuItemId(id);


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 每个id项看起来都差不多╮(╯▽╰)╭   具体怎么实现更好，小伙伴们发挥想象力吧ლ(╹◡╹ლ)
     * menu中的item监听
     *
     * @param id
     */
    private void selectedMenuItemId(int id) {
        Bundle bundle = new Bundle();
        if (id == R.id.nav_all) {
            bundle.putString(KEY_TYPE, KEY_TYPE_ALL);
            bundle.putString(KEY_TYPE_ALL, TYPE_ALL);
            ActivityUtils.openToActivity(getApplicationContext(), AllMainActivity.class, bundle);
            finish();
        } else if (id == R.id.nav_android) {
            bundle.putString(KEY_TYPE, KEY_TYPE_ANDROID);
            bundle.putString(KEY_TYPE_ANDROID, TYPE_ANDROID);
            ActivityUtils.openToActivity(getApplicationContext(), AllMainActivity.class, bundle);
            finish();
        } else if (id == R.id.nav_ios) {
            bundle.putString(KEY_TYPE, KEY_TYPE_IOS);
            bundle.putString(KEY_TYPE_IOS, TYPE_IOS);
            ActivityUtils.openToActivity(getApplicationContext(), AllMainActivity.class, bundle);
            finish();
        } else if (id == R.id.nav_expand) {
            bundle.putString(KEY_TYPE, KEY_TYPE_EXPAND);
            bundle.putString(KEY_TYPE_EXPAND, TYPE_EXPAND);
            ActivityUtils.openToActivity(getApplicationContext(), AllMainActivity.class, bundle);
            finish();
        } else if (id == R.id.nav_foreend) {
            bundle.putString(KEY_TYPE, KEY_TYPE_FOREEND);
            bundle.putString(KEY_TYPE_FOREEND, TYPE_FOREEND);
            ActivityUtils.openToActivity(getApplicationContext(), AllMainActivity.class, bundle);
            finish();
        } else if (id == R.id.nav_rest) {
            bundle.putString(KEY_TYPE, KEY_TYPE_REST);
            bundle.putString(KEY_TYPE_REST, TYPE_REST);
            ActivityUtils.openToActivity(getApplicationContext(), AllMainActivity.class, bundle);
            finish();
        } else if (id == R.id.nav_recommed) {
            bundle.putString(KEY_TYPE, KEY_TYPE_RECOMMEND);
            bundle.putString(KEY_TYPE_RECOMMEND, TYPE_RECOMMEND);
            ActivityUtils.openToActivity(getApplicationContext(), AllMainActivity.class, bundle);
            finish();
        } else if (id == R.id.nav_app) {
            bundle.putString(KEY_TYPE, KEY_TYPE_APP);
            bundle.putString(KEY_TYPE_APP, TYPE_APP);
            ActivityUtils.openToActivity(getApplicationContext(), AllMainActivity.class, bundle);
            finish();
        } else if (id == R.id.nav_welfare) {

            ActivityUtils.openToActivity(getApplicationContext(), WelfareActivity.class);
            finish();
        } else if (id == R.id.nav_about) {

            ActivityUtils.openToActivity(getApplicationContext(), AboutActivity.class);
            finish();
        }


    }


    //用于初始化视图
    public abstract void initAllView();

    //用于获取Intent中携带的数据
    public abstract void obtainIntentData();
}
