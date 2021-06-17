package com.example;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;

import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.security.auth.login.LoginException;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "";

    private TextView frag_tv_temp;
    private TextView frag_tv_weather;
    private TextView frag_tv_date;
    private TextView frag_tv_city;
    private Button frag_tv_search;
    private SearchView frag_tv_query;

    private TextView frag_tv_day2;
    private TextView frag_tv_day3;
    private TextView frag_tv_day4;
    private TextView frag_tv_day5;
    private TextView frag_tv_day6;
    private TextView frag_tv_day7;

    //暂存7天天气数据
    private String city;

    private String w1;
    private String w2;
    private String w3;
    private String w4;
    private String w5;
    private String w6;
    private String w7;

    private String t1;
    private String t2;
    private String t3;
    private String t4;
    private String t5;
    private String t6;
    private String t7;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg){
            if(msg.what == 0){

                //获取系统时间
                Calendar cal = Calendar.getInstance();
                String time1 = cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH) + 1)+"-"+cal.get(Calendar.DAY_OF_MONTH);
                String time2 = cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH) + 1)+"-"+(cal.get(Calendar.DAY_OF_MONTH)+1);
                String time3 = cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH) + 1)+"-"+(cal.get(Calendar.DAY_OF_MONTH)+2);
                String time4 = cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH) + 1)+"-"+(cal.get(Calendar.DAY_OF_MONTH)+3);
                String time5 = cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH) + 1)+"-"+(cal.get(Calendar.DAY_OF_MONTH)+4);
                String time6 = cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH) + 1)+"-"+(cal.get(Calendar.DAY_OF_MONTH)+5);
                String time7 = cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH) + 1)+"-"+(cal.get(Calendar.DAY_OF_MONTH)+6);

                frag_tv_temp.setText(t1);
                frag_tv_weather.setText(w1);
                frag_tv_date.setText(time1);
                frag_tv_city.setText(city);

                frag_tv_day2.setText(time2+"\t\t\t"+w2+"\t\t\t"+t2);
                frag_tv_day3.setText(time3+"\t\t\t"+w3+"\t\t\t"+t3);
                frag_tv_day4.setText(time4+"\t\t\t"+w4+"\t\t\t"+t4);
                frag_tv_day5.setText(time5+"\t\t\t"+w5+"\t\t\t"+t5);
                frag_tv_day6.setText(time6+"\t\t\t"+w6+"\t\t\t"+t6);
                frag_tv_day7.setText(time7+"\t\t\t"+w7+"\t\t\t"+t7);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //测试sdk

        //初始化
        getStart();
        //检测查询按钮
    }
    public void getStart(){
        //SDK获取城市名
        //初始化
        MapUtil.getInstance().initLocation(this,this);
        //设置定位模式
        MapUtil.getInstance().setLocationMode();
        //开启定位服务
        MapUtil.getInstance().startLocation();
        //暂停定位服务
        //MapUtil.getInstance().stopLocation();
        MapUtil.getInstance().setListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation var1){
                city = var1.getCity().substring(0,var1.getCity().length()-1);
                frag_tv_city.setText(city);
                doQuery(city);
            }
        });
        // 销毁定位服务
        // MapUtil.getInstance().destoryService();

        //
        //初始化
        frag_tv_search = findViewById(R.id.frag_tv_search);
        frag_tv_temp = findViewById(R.id.frag_tv_temp);
        frag_tv_weather = findViewById(R.id.frag_tv_weather);
        frag_tv_date = findViewById(R.id.frag_tv_date);
        frag_tv_city = findViewById(R.id.frag_tv_city);

        frag_tv_day2 = findViewById(R.id.frag_tv_day2);
        frag_tv_day3 = findViewById(R.id.frag_tv_day3);
        frag_tv_day4 = findViewById(R.id.frag_tv_day4);
        frag_tv_day5 = findViewById(R.id.frag_tv_day5);
        frag_tv_day6 = findViewById(R.id.frag_tv_day6);
        frag_tv_day7 = findViewById(R.id.frag_tv_day7);

        Calendar cal = Calendar.getInstance();
        String time1 = cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH) + 1)+"-"+cal.get(Calendar.DAY_OF_MONTH);
        String time2 = cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH) + 1)+"-"+(cal.get(Calendar.DAY_OF_MONTH)+1);
        String time3 = cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH) + 1)+"-"+(cal.get(Calendar.DAY_OF_MONTH)+2);
        String time4 = cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH) + 1)+"-"+(cal.get(Calendar.DAY_OF_MONTH)+3);
        String time5 = cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH) + 1)+"-"+(cal.get(Calendar.DAY_OF_MONTH)+4);
        String time6 = cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH) + 1)+"-"+(cal.get(Calendar.DAY_OF_MONTH)+5);
        String time7 = cal.get(Calendar.YEAR)+"-"+(cal.get(Calendar.MONTH) + 1)+"-"+(cal.get(Calendar.DAY_OF_MONTH)+6);

        frag_tv_temp.setText("温度");
        frag_tv_weather.setText("天气");
        frag_tv_date.setText(time1);
        //frag_tv_city.setText(city);

        frag_tv_day2.setText(time2+"\t\t\t"+"天气"+"\t\t\t"+"温度");
        frag_tv_day3.setText(time3+"\t\t\t"+"天气"+"\t\t\t"+"温度");
        frag_tv_day4.setText(time4+"\t\t\t"+"天气"+"\t\t\t"+"温度");
        frag_tv_day5.setText(time5+"\t\t\t"+"天气"+"\t\t\t"+"温度");
        frag_tv_day6.setText(time6+"\t\t\t"+"天气"+"\t\t\t"+"温度");
        frag_tv_day7.setText(time7+"\t\t\t"+"天气"+"\t\t\t"+"温度");

        //启动搜索框
        frag_tv_query = findViewById(R.id.frag_tv_query);
        frag_tv_query.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                city = query;
                doQuery(city);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                if(!TextUtils.isEmpty(newText)){
                    city = newText;;
                }
                return false;
            }
        });
        //启动按钮
        frag_tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.frag_tv_search:
                        //测试多线程
                        doQuery(city);
                        break;
                }
            }
        });
    }

    private void doQuery(String c){
        new Thread(new Runnable() {
            @Override
            public void run() {
                MysqlUtil.setCityName(c);
                MysqlUtil.creatnet();

                t1 = MysqlUtil.gett1();
                t2 = MysqlUtil.gett2();
                t3 = MysqlUtil.gett3();
                t4 = MysqlUtil.gett4();
                t5 = MysqlUtil.gett5();
                t6 = MysqlUtil.gett6();
                t7 = MysqlUtil.gett7();

                w1 = MysqlUtil.getw1();
                w2 = MysqlUtil.getw2();
                w3 = MysqlUtil.getw3();
                w4 = MysqlUtil.getw4();
                w5 = MysqlUtil.getw5();
                w6 = MysqlUtil.getw6();
                w7 = MysqlUtil.getw7();

                Message msg = Message.obtain();
                msg.what = 0;
                msg.obj = 0;
                handler.sendMessage(msg);
            }
        }).start();
    }
}
