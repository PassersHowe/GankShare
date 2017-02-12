package com.passershowe.gank.ui.allmain;

import com.passershowe.gank.data.entity.Gank;
import com.passershowe.gank.ui.base.BasePresenter;
import com.passershowe.gank.ui.base.BaseView;

import java.util.List;

/**
 * Created by admin on 2016/8/27.
 */

public interface AllContract {
    interface View extends BaseView<Presenter> {
        void showNotGankData();

        void showRecyclerGank(List<Gank> gankList);

        void showError(String error);

        void hideNotDataText();

        void showLoadMoreGankSuccess(List<Gank> gankList, boolean refresh);

        void showLoadMoreGankerror();

        void setLoadingIndicator(boolean active);

    }

    interface Presenter extends BasePresenter {
        int getPage();

        void setPage(int page);
        void setGankType(String gankType);
        void loadGankData();

        void refreshGankData(boolean refresh);
    }
}
