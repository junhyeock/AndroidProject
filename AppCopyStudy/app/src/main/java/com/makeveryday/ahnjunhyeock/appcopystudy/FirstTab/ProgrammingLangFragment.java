package com.makeveryday.ahnjunhyeock.appcopystudy.FirstTab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.makeveryday.ahnjunhyeock.appcopystudy.R;

/**
 * Created by ahnjunhyeock on 2017. 12. 6..
 */

public class ProgrammingLangFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_programming_lang, container, false);
        return v;
    }
}
