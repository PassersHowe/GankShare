package com.passershowe.gank.ui.welfare;

import com.passershowe.gank.data.entity.Gank;
import com.passershowe.gank.ui.base.BasePresenter;
import com.passershowe.gank.ui.base.BaseView;

import java.util.List;

/**
 * Created by PassersHowe on 2016/9/1.
 */

public interface WelfareContract {
    interface View extends BaseView<Presenter> {
        //显示RecyclerView及加载数据
        void showWelfareRecycler(List<Gank> gankList);

        //没有数据时显示
        void showNoData();

        //当数据不为空时，隐藏Textview
        void hideNotDataText();

        //加载更多，成功时展示的视图
        void showMoreWelfareGankSuccess(List<Gank> gankList, boolean refresh);

        //加载更多，失败时展示的视图
        void showMoreWelfareGankerror();

        //设置加载指示器(刷新，加载更多)
        void setLoadingIndicator(boolean active);
    }

    interface Presenter extends BasePresenter {
        //获取当前页数
        int getPage();

        //设置页数
        void setPage(int page);

        //下载数据以及处理数据
        void loadWelfareGankData();

        //刷新数据
        void refreshWelfareGankData(boolean refresh);
    }
}
