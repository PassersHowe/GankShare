package com.passershowe.gank.ui.allmain;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.passershowe.gank.R;
import com.passershowe.gank.data.entity.Gank;
import com.passershowe.gank.presenter.AllPresenter;
import com.passershowe.gank.ui.adapter.AllMainAdapter;
import com.passershowe.gank.ui.adapter.OnItemClickListener;
import com.passershowe.gank.ui.base.BaseFragment;
import com.passershowe.gank.ui.detail.GankDetailActivity;
import com.passershowe.gank.utils.ActivityUtils;
import com.passershowe.gank.utils.RecyclerScrollUtil;
import com.passershowe.gank.widget.DividerDecoration;

import java.util.List;

import static com.passershowe.gank.constant.Constant.TYPE_ALL;

/**
 * Created by admin on 2016/8/29.
 */

public class AllMainFragment extends BaseFragment implements AllContract.View {
    private static final String TAG = "AllMainFragment";
    private AllContract.Presenter mPresenter;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private AllMainAdapter allMainAdapter;
    private DividerDecoration decoration;
    boolean onPause = false;
    private static String gankType;

    public static AllMainFragment newInstance(String type) {
        gankType = type;
        return new AllMainFragment();
    }

    LinearLayoutManager layoutManager;

    @Override
    public void initAllView(View view) {

        mPresenter = new AllPresenter(getActivity(),this, gankType);
        recyclerView = getRecyclerView();
        refreshLayout = getBaseSwipeRefresh();
        layoutManager = new LinearLayoutManager(getActivity());
        decoration = new DividerDecoration(getActivity());
        decoration.setSplitLine(false);


    }

    @Override
    public void setPresenter(AllContract.Presenter presenter) {
        mPresenter = presenter;

    }


    @Override
    public void onResume() {
        super.onResume();
        if (!onPause) {
            mPresenter.start();
            mPresenter.setGankType(gankType == null ? TYPE_ALL : gankType);
        }


    }

    @Override
    public void onPause() {
        super.onPause();
        onPause = true;

    }

    @Override
    public void showNotGankData() {
        getNoDataTextView().setVisibility(View.VISIBLE);
    }

    @Override
    public void showRecyclerGank(List<Gank> gankList) {
        Log.e(TAG, "showRecyclerGank");
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(decoration);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        allMainAdapter = new AllMainAdapter(getActivity(), gankList);
        recyclerView.setAdapter(allMainAdapter);

        RecyclerScrollUtil.getInstance().makeScrollListener(recyclerView, layoutManager,

                new RecyclerScrollUtil.ScrollCallBack() {
                    @Override
                    public void scrollInLast() {
                        loadMoreGankData();
                    }
                }
        );


        //刷新监听
        refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        mPresenter.refreshGankData(true);
                    }
                }
        );

        allMainAdapter.setOnItemClickListener(

                new OnItemClickListener() {
                    @Override
                    public void OnItemClick(View convertView, int position, Object o) {
                        Gank gank = (Gank) o;

                        Intent intent = new Intent(getActivity(), GankDetailActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("url", gank.getUrl());
                        intent.putExtra("title", gank.getDesc());
                        getActivity().startActivity(intent);
                    }
                }
        );
    }

    @Override
    public void showError(String error) {

            Snackbar.make(recyclerView, error, Snackbar.LENGTH_SHORT).show();

    }


    /**
     * 加载更多数据
     */
    private void loadMoreGankData() {
        Log.e(TAG, "loadMoreGankData");
        //请求的页数+1
        mPresenter.setPage(mPresenter.getPage() + 1);
        //是否刷新
        mPresenter.refreshGankData(false);
    }





    @Override
    public void hideNotDataText() {
        getNoDataTextView().setVisibility(View.INVISIBLE);
    }

    @Override
    public void showLoadMoreGankSuccess(List<Gank> gankList, boolean refresh) {
        if (refresh) {
            allMainAdapter.clean();
            allMainAdapter.setDataList(gankList);
        } else {
            allMainAdapter.addAll(gankList);
            allMainAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void showLoadMoreGankerror() {
        Snackbar.make(recyclerView, getResources().getText(R.string.load_error), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        if (refreshLayout == null) {
            return;
        }


        refreshLayout.post(new Runnable() {
            @Override
            public void run() {
               refreshLayout.setRefreshing(active);
            }
        });
    }


}

