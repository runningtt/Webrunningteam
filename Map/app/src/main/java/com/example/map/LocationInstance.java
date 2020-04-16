package com.example.map;
import android.content.Context;
import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
public class LocationInstance {
    public LocationClient mLocationClient;
    private MyLocationListener myListener;
    public LocationInstance(Context context, MyLocationListener myListener) {
        mLocationClient = new LocationClient(context);
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        // /**
        // * 第二步：配置定位SDK参数
        // */
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        option.setCoorType("bd09ll");
        option.setScanSpan(1000);
        option.setOpenGps(true);
        option.setLocationNotify(true);
        option.setIgnoreKillProcess(false);
        option.SetIgnoreCacheException(false);
        option.setWifiCacheTimeOut(5 * 60 * 1000);
        option.setEnableSimulateGps(false);
        option.setIsNeedAddress(true);
        mLocationClient.setLocOption(option);
    }
    /**     * 第三步.实现BDAbstractLocationListener接口     */
    public static class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            //此处的BDLocation为定位结果信息类，通过它的各种get方法可获取定位相关的全部结果
            // 以下只列举部分获取经纬度相关（常用）的结果信息
            // 更多结果信息获取说明，请参照类参考中BDLocation类中的说明
            double latitude = location.getLatitude();    //获取纬度信息
            double longitude = location.getLongitude();    //获取经度信息
            float radius = location.getRadius();    //获取定位精度，默认值为0.0f
            String coorType = location.getCoorType();            //获取经纬度坐标类型，以LocationClientOption中设置过的坐标类型为准
            int errorCode = location.getLocType();            //获取定位类型、定位错误返回码，具体信息可参照类参考中BDLocation类中的说明        }    }     /**     * 第四步：设置定位的开始和结束的方法     * 1s定位一次非常耗电的操作     */    public void start() {        mLocationClient.start();    }     public void stop() {        mLocationClient.stop();    }  }
        }
        }public void start() {
        mLocationClient.start();
    }
    public void stop() {
        mLocationClient.stop();
    }
}

