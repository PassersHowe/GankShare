package com.passershowe.gank.ui.base;

/**
 * Created by PassersHowe on 2016/8/27.
 */

public interface BaseView<T> {
    /**
     * 初始化 Presenter
     * @param presenter
     */
    void setPresenter(T presenter);
}
