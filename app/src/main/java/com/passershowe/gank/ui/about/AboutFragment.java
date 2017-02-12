package com.passershowe.gank.ui.about;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.passershowe.gank.R;

/**
 * Created by hao809395357 on 2016/9/27.
 */

public class AboutFragment extends Fragment {


    public static AboutFragment newInstance() {

        return new AboutFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_fragment, container, false);

        return view;
    }
}
