package com.passershowe.gank.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by admin on 2016/9/1.
 */

public class RecyclerScrollUtil {
    private static RecyclerScrollUtil scrollUtil;

    public static RecyclerScrollUtil getInstance() {
        if (null == scrollUtil) {
            synchronized (RecyclerScrollUtil.class) {
                if (null == scrollUtil) {
                    scrollUtil = new RecyclerScrollUtil();
                }
            }
        }
        return scrollUtil;
    }

    /**
     * 为RecyclerView设置滑动监听
     * @param recyclerView
     * @param layoutManager
     * @param scrollCallBack
     */
    public void makeScrollListener(RecyclerView recyclerView, final RecyclerView.LayoutManager layoutManager, final ScrollCallBack
            scrollCallBack) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private boolean dataLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //recyclerView是否静止状态
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;

                    if (newState == recyclerView.SCROLL_STATE_IDLE) {
                        //是否向下滑动，是否滑动到最后一个item
                        if (dataLast && (linearLayoutManager.findLastCompletelyVisibleItemPosition() == (layoutManager.getItemCount() - 1))) {
                            scrollCallBack.scrollInLast();

                        }
                    }
                    //判断是否是GridLayoutManager
                } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                    StaggeredGridLayoutManager gridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        //获取最后两个item
                        int[] bottom = gridLayoutManager.findLastCompletelyVisibleItemPositions(new int[2]);
                        int lastItemCount = gridLayoutManager.getItemCount() - 1;
                        //是否向下滑动，是否滑动到最后一个item
                        if (dataLast && (bottom[0] == lastItemCount || bottom[1] == lastItemCount)) {
                            scrollCallBack.scrollInLast();
                        }
                    }

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //dy > 0 ：向上滑动
                if (dy > 0) {
                    dataLast = true;
                } else {
                    dataLast = false;
                }
            }
        });

    }


    /**
     *  滑动回调(可根据需求添加回调方法)
     */
    public interface ScrollCallBack {
        //滑动到最后时调用
        void scrollInLast();
    }


}
