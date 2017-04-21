package com.anew.note.activity;

import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.anew.note.R;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.List;

import static com.anew.note.utils.AppUtils.isGrantExternalRW;

public class MapActivity extends AppCompatActivity {
    private MapView mapView;
    private BaiduMap mBaiduMap;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener;
    //定位图层显示方式
    private MyLocationConfiguration.LocationMode locationMode;
    private BitmapDescriptor mIconLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化Sdk
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_map);

        isGrantExternalRW(this);

        mapView = (MapView) findViewById(R.id.bmapView);

        mBaiduMap = mapView.getMap();
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

        //定位服务的客户端，目前只支持在主线程中启动
        mLocationClient = new LocationClient(getApplicationContext());

        //注册监听函数
        myListener = new MyLocationListener();
        mLocationClient.registerLocationListener( myListener );

        //配置定位SDK各配置参数，比如定位模式、定位时间间隔、坐标系类型，图标，标记点位等
        mBaiduMap.setMyLocationEnabled(true);
        initLocation();



        //开启定位
        mLocationClient.start();


    }

    //配置
    private void initLocation(){

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll") ;//可选，默认gcj02，设置返回的定位结果坐标系
        int span=1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要//初始化图标,BitmapDescriptorFactory是bitmap 描述信息工厂类.

        //修改图标
        mIconLocation= BitmapDescriptorFactory
                .fromResource(R.drawable.location_marker);
        mLocationClient.setLocOption(option);

        //显示指南针
        mBaiduMap.getUiSettings().setCompassEnabled(true);


        //描述地图将要发生的变化，使用工厂类MapStatusUpdateFactory创建，设置级别， 默认是12
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.zoomTo(18);
        mBaiduMap.setMapStatus(mapStatusUpdate);

        // 绘制mark覆盖物
        drawMark();

    }
    public class MyLocationListener implements BDLocationListener {

        private boolean isFirst = true;

        @Override
        public void onReceiveLocation(BDLocation location) {

            //获取定位结果
            StringBuffer sb = new StringBuffer(256);

            sb.append("time : ");
            sb.append(location.getTime());    //获取定位时间

            sb.append("\nerror code : ");
            sb.append(location.getLocType());    //获取类型类型

            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());    //获取纬度信息

            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());    //获取经度信息

            sb.append("\nradius : ");
            sb.append(location.getRadius());    //获取定位精准度

            if (location.getLocType() == BDLocation.TypeGpsLocation){

                // GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());    // 单位：公里每小时

                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());    //获取卫星数

                sb.append("\nheight : ");
                sb.append(location.getAltitude());    //获取海拔高度信息，单位米

                sb.append("\ndirection : ");
                sb.append(location.getDirection());    //获取方向信息，单位度

                sb.append("\naddr : ");
                sb.append(location.getAddrStr());    //获取地址信息

                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){

                // 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());    //获取地址信息

                sb.append("\noperationers : ");
                sb.append(location.getOperators());    //获取运营商信息

                sb.append("\ndescribe : ");
                sb.append("网络定位成功");

            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {

                // 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");

            } else if (location.getLocType() == BDLocation.TypeServerError) {

                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");

            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {

                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");

            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {

                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");

            }

            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());    //位置语义化信息

            List<Poi> list = location.getPoiList();    // POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }

            Log.i("BaiduLocationApiDem", sb.toString());
            String addr = location.getAddrStr();
            String city = location.getCity();
            Log.e("++++++++",city);

            double mLat = location.getLatitude();
            double  mLon = location.getLongitude();
            Log.e("============",mLat+","+mLon);
            locationMode= MyLocationConfiguration.LocationMode.NORMAL;

            MyLocationData data= new MyLocationData.Builder()
                    .accuracy(0.0f)//getRadius 获取定位精度,默认值0.0f
                    .latitude(mLat)//百度纬度坐标
                    .longitude(mLon)//百度经度坐标
                    .build();
            mBaiduMap.setMyLocationData(data);
            MyLocationConfiguration configuration
                    =new MyLocationConfiguration(locationMode,true,mIconLocation);
            mBaiduMap.setMyLocationConfigeration(configuration);

            if(isFirst)
            {
                //地理坐标基本数据结构
                LatLng latLng=new LatLng(mLat,mLon);

                //描述地图状态将要发生的变化,通过当前经纬度来使地图显示到该位置
                MapStatusUpdate mapStatusUpdate_circle = MapStatusUpdateFactory.newLatLng(latLng);
                mBaiduMap.setMapStatus(mapStatusUpdate_circle);

                isFirst=false;

            }




        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }
    // 绘制mark覆盖物，标记点位

    private void drawMark() {

        LatLng latLng1 = new LatLng(30.293721,120.388353);
        String text1 = "我家附近的地铁";
        setMaker(latLng1,text1);

        LatLng latLng2 = new LatLng(30.304199,120.388592);
        String text2 = "我家附近的物美超市";
        setMaker(latLng2, text2);

        LatLng latLng3 = new LatLng(30.310029,120.37501);
        String text3 = "我工作过的地方";
        setMaker(latLng3, text3);

    }

    /**
     * 标记
     *
     * @param point 点位置
     * @param text
     */
    private void setMaker(LatLng point, String text) {
        //构建Maker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory.fromResource(R.drawable.icon_mark);
        //构建option
        OverlayOptions overlayOptions = new MarkerOptions()
                .position(point)
                .icon(bitmap);
        //在地图上添加Marker
        mBaiduMap.addOverlay(overlayOptions);

        //添加自定义文字
        OverlayOptions overlayOptions1 = new TextOptions()
                .position(point)
                .bgColor(0xAAFFFF00)
                .fontSize(35)
                .fontColor(0xFFFF00FF)
                .rotate(-30)
                .text(text);
        mBaiduMap.addOverlay(overlayOptions1);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
            mBaiduMap.clear();
            mLocationClient.stop();

        mapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mapView.onPause();
    }
}
