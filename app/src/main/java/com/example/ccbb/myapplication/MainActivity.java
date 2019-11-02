package com.example.ccbb.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.ccbb.myapplication.I.VPIListener;
import com.example.ccbb.myapplication.adapter.NewsLiviewAdapter;
import com.example.ccbb.myapplication.entity.News;
import com.example.ccbb.myapplication.p.PSeekNews;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private List<News> newsList = new ArrayList<>();
    private NewsLiviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        loadDate();

        onclick();

    }

    private void onclick() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,LookNewsActivity.class);
                intent.putExtra("URL",newsList.get(position).getUrl());
                startActivity(intent);
            }
        });
    }//onclick打开新闻页面

    private void loadDate() {
        PSeekNews.pSeekNews("shehui", new VPIListener() {
            @Override
            public void success(Object o) {
                newsList.clear();
                newsList.addAll((Collection<? extends News>) o);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void failure() {

            }

            @Override
            public void showProgress() {

            }

            @Override
            public void hideProgress() {

            }
        });
    }

    private void initViews() {
        listView = findViewById(R.id.activity_listview);

        adapter = new NewsLiviewAdapter(newsList,MainActivity.this);

        listView.setAdapter(adapter);
    }
}
