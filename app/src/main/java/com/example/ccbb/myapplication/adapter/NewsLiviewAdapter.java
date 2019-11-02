package com.example.ccbb.myapplication.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ccbb.myapplication.R;
import com.example.ccbb.myapplication.entity.News;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsLiviewAdapter extends BaseAdapter {

    private List<News> list;  //列表中的News对象是自己定义的，这里可以直接使用
    private Context context;
    private LayoutInflater inflater;//需要查询，加载界面？？setlist=    适配器中加入布局  一般是布局中加入适配器
    public NewsLiviewAdapter(List<News> list, Context context) {
        this.list = list;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.news_listview, null);
            holder = new viewHolder();//定义的一个对象
            holder.img = convertView.findViewById(R.id.news_listview_img);
            holder.tvDate = convertView.findViewById(R.id.news_listview_tv_date);
            holder.tvTitle = convertView.findViewById(R.id.news_listview_tv_title);
            convertView.setTag(holder);
        } else {
            holder = (viewHolder) convertView.getTag();
        }

        getBitMap(list.get(position).getImgUrl(), holder.img);//获取点击的链接
        holder.tvTitle.setText(list.get(position).getTitle());
        holder.tvDate.setText(list.get(position).getDate());//将列表信息传到视图

        return convertView;
    }

    private void getBitMap(String imgUrl, final ImageView img) {

        final Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 0:
                        img.setImageBitmap((Bitmap) msg.obj);
                        Log.e("====","OK");
                        break;
                    case 1:
                        break;
                }//如果参数为0，就得到图片
            }
        };

        Request request = new Request.Builder().url(imgUrl).build();
        OkHttpClient client = new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg = Message.obtain();
                msg.what = 1;
                handler.sendMessage(msg);
            }//定义消息参数为1，主线程为1 则接收到这条消息

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message msg = Message.obtain();
                msg.what = 0;
                InputStream inputStream = response.body().byteStream();//爬取的数据流
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);//网上搜索 装像素 长 宽
                msg.obj = bitmap;//图片装到msg
                handler.sendMessage(msg);//上传msg
            }
        });
    }

    class viewHolder {
        ImageView img;
        TextView tvDate;
        TextView tvTitle;
    }
}
