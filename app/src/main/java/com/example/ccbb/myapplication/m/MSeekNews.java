package com.example.ccbb.myapplication.m;
//定义类以及类的方法
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.example.ccbb.myapplication.I.PMIListener;
import com.example.ccbb.myapplication.entity.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;//导入Json包，用于解析json数据

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MSeekNews {

    private static Handler mHandler;
    private static String key = "34702718ec89ebdcf1024eccdb084305";
    private static String URL = "http://v.juhe.cn/toutiao/index";//聚合头条目录
    private static String[] IMGSTR = new String[]{
            "thumbnail_pic_s",
            "thumbnail_pic_s02",
            "thumbnail_pic_s03",
            "thumbnail_pic_s04"
    };
//定义图片名字
    public static void mSeekNews(String type, final PMIListener pmiListener) {

        mHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        pmiListener.success(getList(msg.obj.toString()));
                        break;
                    case 1:
                        pmiListener.failure();
                        break;
                }
                pmiListener.hideProgress();
            }
        };//定义得到消息的方法，用0,1来得到不同的消息

        pmiListener.showProgress();

        FormBody form = new FormBody.Builder().add("type", type)
                .add("key", key).build();

        Request request = new Request.Builder().url(URL).post(form).build();

        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {//打开网页？
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg = Message.obtain();
                msg.what = 1;
                mHandler.sendMessage(msg);
            }//用onfailure方法传消息就将消息参数设为1

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message msg = Message.obtain();
                msg.what = 0;
                msg.obj = response.body().string();
                mHandler.sendMessage(msg);
            }//用onResponse方法就将消息参数设为0
        });//在类中定义了一个内部类

    }

    private static Object getList(String string) {

//        String title;
//        String date;
//        List<String> imgList;
//        String url;
        List<News> newsList = new ArrayList<>();

        JSONObject jsonObject = null;//JSONObject？？？
        String temp = null;
        JSONArray jsonArray = null;
        try {
            jsonObject = new JSONObject(string);
            temp = jsonObject.getString("result");
            jsonObject = new JSONObject(temp);
            temp = jsonObject.getString("data");
            jsonArray = new JSONArray(temp);
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                String title = jsonObject.getString("title");
                String date = jsonObject.getString("date");
                String url = jsonObject.getString("url");
                String imgList = jsonObject.getString("thumbnail_pic_s");
                newsList.add(new News(title,date,imgList,url));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return newsList;
    }
}
