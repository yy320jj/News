package com.example.ccbb.myapplication.I;

public interface PMIListener {
    void success(Object o);
    void failure();
    void showProgress();
    void hideProgress();
}
//定义的interface是一个接口