package com.passershowe.gank.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;
import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by PassersHowe on 2016/6/14.
 */
public class ActivityUtils {


    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     */
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment);
        transaction.commit();
    }

    /**
     * 跳转到指定的Activity
     *
     * @param context 上下文
     * @param claxx   目标Avtivity Class
     * @param bundle  携带的数据
     */
    public static void openToActivity(Context context, Class claxx, Bundle bundle) {
        Intent intent = new Intent(context, claxx);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void openToActivity(Context context, Class claxx) {
        Intent intent = new Intent(context, claxx);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void openToActivity(Context context, Class claxx, String key, String value) {
        Intent intent = new Intent(context, claxx);
        intent.putExtra(key, value);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void openToActivity(Context context, Class claxx, String key, Integer value) {
        Intent intent = new Intent(context, claxx);
        intent.putExtra(key, value);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

    public static void openToActivity(Context context, Class claxx, String key, boolean value) {
        Intent intent = new Intent(context, claxx);
        intent.putExtra(key, value);
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
