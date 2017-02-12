package com.passershowe.gank.ui.welfare;

import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.passershowe.gank.R;
import com.passershowe.gank.data.entity.Gank;
import com.passershowe.gank.presenter.WelfarePresenter;
import com.passershowe.gank.ui.adapter.WelfareAdapter;
import com.passershowe.gank.ui.base.BaseFragment;
import com.passershowe.gank.utils.RecyclerScrollUtil;
import com.passershowe.gank.widget.GridSpacingThemeDecoration;

import java.util.List;

/**
 * Created by PassersHowe on 2016/9/1.
 */

public class WelfareFragment extends BaseFragment implements WelfareContract.View {
    private static final String TAG = "WelfareFragment";
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;

    private WelfareContract.Presenter mPresenter;
    private boolean onPause = false;
    private WelfareAdapter welfareAdapter;
    private StaggeredGridLayoutManager layoutManager;

    public static WelfareFragment newInstance() {

        return new WelfareFragment();
    }

    @Override
    public void initAllView(View view) {
        mPresenter = new WelfarePresenter(this);
        recyclerView = getRecyclerView();
        refreshLayout = getBaseSwipeRefresh();


    }

    @Override
    public void showWelfareRecycler(List<Gank> gankList) {

        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new GridSpacingThemeDecoration(5, 5, true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        welfareAdapter = new WelfareAdapter(getActivity(), gankList);
        recyclerView.setAdapter(welfareAdapter);
        //lambda 表达式(๑•ᴗ•๑)
        //下滑刷新监听
        RecyclerScrollUtil.getInstance().makeScrollListener(recyclerView, layoutManager,

                new RecyclerScrollUtil.ScrollCallBack() {
                    @Override
                    public void scrollInLast() {
                        loadMoreWelfareGankData();
                    }
                }
        );

        //刷新监听
        refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        mPresenter.refreshWelfareGankData(true);
                    }
                }
        );
    }

    /**
     * 加载更多数据
     */
    private void loadMoreWelfareGankData() {
        Log.e(TAG, "loadMoreWelfareGankData");
        //请求的页数+1
        mPresenter.setPage(mPresenter.getPage() + 1);
        //是否刷新
        mPresenter.refreshWelfareGankData(false);
    }

    @Override
    public void showNoData() {

    }

    @Override
    public void hideNotDataText() {
        getNoDataTextView().setVisibility(View.INVISIBLE);
    }

    @Override
    public void showMoreWelfareGankSuccess(List<Gank> gankList, boolean refresh) {
        //控制刷新方式:true下拉刷新，false上拉加载更多
        if (refresh) {
            welfareAdapter.clean();
            welfareAdapter.setDataList(gankList);
        } else {
            welfareAdapter.addAll(gankList);
            welfareAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showMoreWelfareGankerror() {
        Snackbar.make(recyclerView, getResources().getText(R.string.load_error), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        if (refreshLayout == null) {
            return;
        }

        //刷新滚动条设置：true转呀转，false消失
        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(active);
            }
        });
    }

    @Override
    public void setPresenter(WelfareContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!onPause) mPresenter.start();

    }

    @Override
    public void onPause() {
        super.onPause();
        onPause = true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
