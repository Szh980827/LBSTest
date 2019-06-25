package com.example.song1.lbstest;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
	public LocationClient mLocationClient;
	private TextView textView;
	private Button button;
	private MapView mapView;
	private BaiduMap baiduMap;
	private boolean isFirstLocate = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);
		mLocationClient = new LocationClient(getApplicationContext());
		mLocationClient.registerLocationListener(new Main2Activity.MyLocationListener());
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_main2);
		mapView= (MapView) findViewById(R.id.baidumap);
		//baiduMap = mapView.getMap();
		textView = (TextView) findViewById(R.id.textView1);
		button = (Button) findViewById(R.id.button1);
		List<String> permissionList = new ArrayList<>();
		if (ContextCompat.checkSelfPermission(Main2Activity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
		}
		if (ContextCompat.checkSelfPermission(Main2Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
			permissionList.add(Manifest.permission.READ_PHONE_STATE);
		}
		if (ContextCompat.checkSelfPermission(Main2Activity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
			permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
		}
		if (!permissionList.isEmpty()) {
			String[] permissions = permissionList.toArray(new String[permissionList.size()]);
			ActivityCompat.requestPermissions(Main2Activity.this, permissions, 1);
		} else {
			requestLocation();
		}

	}
	private void initLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setScanSpan(500);
		option.setIsNeedAddress(true);
		mLocationClient.setLocOption(option);
	}

	private void requestLocation() {
		initLocation();
		mLocationClient.start();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mapView.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mLocationClient.stop();
		mapView.onDestroy();
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		switch (requestCode) {
			case 1:
				if (grantResults.length > 0) {
					for (int result : grantResults) {
						if (result != PackageManager.PERMISSION_GRANTED) {
							Toast.makeText(this, "必须同意全部权限才能使用本程序", Toast.LENGTH_SHORT).show();
							finish();
							return;
						}
					}
					requestLocation();
				} else {
					Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
					finish();
				}
				break;
			default:
		}
	}

//	private void navigateTo(BDLocation location) {
//		if (isFirstLocate) {
//			LatLng ll = new LatLng(location.getLatitude(), location.getLongitude());
//			MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
//			baiduMap.animateMapStatus(update);
//			update = MapStatusUpdateFactory.zoomTo(16f);
//			baiduMap.animateMapStatus(update);
//			isFirstLocate = false;
//		}
//	}

	public class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
//			if (location.getLocType() == BDLocation.TypeGpsLocation || location.getLocType() == BDLocation.TypeNetWorkLocation) {
//				navigateTo(location);
//			}
		}
	}
}
