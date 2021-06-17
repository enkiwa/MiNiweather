package com.example;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.*;
import com.amap.api.location.AMapLocationListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MapUtil {

    //声名单例
    private static MapUtil mapUtil = new MapUtil();

    private MapUtil() {
    }

    public static MapUtil getInstance() {
        return mapUtil;
    }

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new MyAMapLocationListener();
    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption = null;

    private static final int WRITE_COARSE_LOCATION_REQUEST_CODE = 1;

    //初始化定位信息
    public void initLocation(Context context, Activity activity) {

        checkPermission(context, activity);
        //初始化定位
        mLocationClient = new AMapLocationClient(context);
        //设置定位回调监听
       // mLocationClient.setLocationListener(mLocationListener);

        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();

        /**
         * 设置定位场景，目前支持三种场景（签到、出行、运动，默认无场景）
         */
        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.SignIn);


        setLocationMode();

        //获取一次定位结果：
        //该方法默认为false。
        mLocationOption.setOnceLocation(true);

        //获取最近3s内精度最高的一次定位结果：
        //设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);

        if (null != mLocationClient) {
            mLocationClient.setLocationOption(mLocationOption);
            //设置场景模式后最好调用一次stop，再调用start以保证场景模式生效
            mLocationClient.stopLocation();
            mLocationClient.startLocation();
        }

        Log.e("locationinfo", "init----");

    }

    //设置模式
    public void setLocationMode() {
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        //mLocationOption.setLocationMode(AMapLocationMode.Battery_Saving);
        //设置定位模式为AMapLocationMode.Device_Sensors，仅设备模式。
        //mLocationOption.setLocationMode(AMapLocationMode.Device_Sensors);
    }

    //开启一次定位
    public void startLocation() {
        if (mLocationClient != null) {
            mLocationClient.startLocation();
        }
    }

    //停止定位服务
    public void stopLocation() {
        //停止定位后，本地定位服务并不会被销毁
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
        }
    }

    public void setListener(AMapLocationListener aMapLocationListener){
        mLocationClient.setLocationListener(aMapLocationListener);
    }

    public class MyAMapLocationListener implements AMapLocationListener {


        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                Log.e("locationinfo", "receive msg : " + amapLocation.toString());
                if (amapLocation.getErrorCode() == 0) {
                    //可在其中解析amapLocation获取相应内容。
                    amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    amapLocation.getLatitude();//获取纬度
                    amapLocation.getLongitude();//获取经度
                    amapLocation.getAccuracy();//获取精度信息
                    amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    amapLocation.getCountry();//国家信息
                    amapLocation.getProvince();//省信息
                    amapLocation.getCity();//城市信息
                    amapLocation.getDistrict();//城区信息
                    amapLocation.getStreet();//街道信息
                    amapLocation.getStreetNum();//街道门牌号信息
                    amapLocation.getCityCode();//城市编码
                    amapLocation.getAdCode();//地区编码
                    amapLocation.getAoiName();//获取当前定位点的AOI信息
                    amapLocation.getBuildingId();//获取当前室内定位的建筑物Id
                    amapLocation.getFloor();//获取当前室内定位的楼层
                    amapLocation.getGpsAccuracyStatus();//获取GPS的当前状态
                    //获取定位时间
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(amapLocation.getTime());
                    df.format(date);

                    stopLocation();
                } else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    }

    //销毁定位客户端，同时销毁本地定位服务。
    public void destoryService() {
        if (mLocationClient != null) {
            mLocationClient.onDestroy();
        }
    }

    //检查是否缺少权限
    public static void checkPermission(Context context, Activity activity) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请ACCESS_COARSE_LOCATION权限
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    WRITE_COARSE_LOCATION_REQUEST_CODE);//自定义的code
        }

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请ACCESS_FINE_LOCATION权限
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    WRITE_COARSE_LOCATION_REQUEST_CODE);//自定义的code
        }

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_COARSE_LOCATION_REQUEST_CODE);//自定义的code
        }

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请READ_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    WRITE_COARSE_LOCATION_REQUEST_CODE);//自定义的code
        }
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请READ_PHONE_STATE权限
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE},
                    WRITE_COARSE_LOCATION_REQUEST_CODE);//自定义的code
        }

    }
}
