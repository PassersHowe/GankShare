package com.passershowe.gank.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.passershowe.gank.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by PassersHowe on 2016/8/27.
 */

public abstract class BaseFragment extends Fragment {
    @BindView(R.id.base_not_data_text)
    TextView noDataTextView;
    @BindView(R.id.base_recycler)
    RecyclerView recyclerView;
    @BindView(R.id.base_swipe_refresh)
    SwipeRefreshLayout baseSwipeRefresh;

    private Unbinder unbinder;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.base_content_main, container, false);
        unbinder = ButterKnife.bind(this, view);
        //设置刷新滚动条的颜色
        baseSwipeRefresh.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);
        initAllView(view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public TextView getNoDataTextView() {
        return noDataTextView;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public SwipeRefreshLayout getBaseSwipeRefresh() {
        return baseSwipeRefresh;
    }

    public abstract void initAllView(View view);



}
