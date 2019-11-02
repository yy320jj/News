package com.example.ccbb.myapplication;
//定义的方法用于看新闻
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebView;

public class LookNewsActivity extends Activity {

    private WebView webView;
    private String URL = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.look_news_main);

        initViews();

    }

    private void getURL() {
        Intent intent = getIntent();
        URL = intent.getStringExtra("URL");
    }

    private void initViews() {

        getURL();

        webView = findViewById(R.id.look_news_main_webview);

        webView.loadUrl(URL);
    }
}
