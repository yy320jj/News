package com.example.ccbb.myapplication.p;
//使用PSeeknews将VPI，PMI的接口与MseekNews中
import android.content.Context;

import com.example.ccbb.myapplication.I.PMIListener;
import com.example.ccbb.myapplication.I.VPIListener;
import com.example.ccbb.myapplication.m.MSeekNews;

public class PSeekNews {
    public static void pSeekNews(String type, final VPIListener vpiListener) {
        MSeekNews.mSeekNews(type, new PMIListener() {
            @Override
            public void success(Object o) {
                vpiListener.success(o);
            }

            @Override
            public void failure() {
                vpiListener.failure();
            }

            @Override
            public void showProgress() {
                vpiListener.showProgress();
            }

            @Override
            public void hideProgress() {
                vpiListener.hideProgress();
            }
        });
    }
}
