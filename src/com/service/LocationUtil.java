package com.service;

import java.util.List;

import com.receiver.CallReceiver;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class LocationUtil extends Activity{
	private LocationManager locationManager;
	private String tmessage;
	public void locationAction(String message){
		tmessage = message;
		LocationManager locationManager = (LocationManager)LocationUtil.this.getSystemService(Context.LOCATION_SERVICE);
		String provider = myCriteria();
		if(provider.equals("network")){
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3600000, 10000, new TestLocationListener());
		}else if(provider.equals("gps")){
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3600000, 10000, new TestLocationListener());
		}else if(provider.equals("passive")){
			locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 3600000, 10000, new TestLocationListener());
		}
		
	}
	
	public String myCriteria(){
		Criteria criteria = new Criteria();
		//设置经纬度的精准度 可选参数有ACCURACY_FINE 准确 ACCURACY_COARSE 粗略 
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		//设置耗电量的级别 
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		//设置是否需要获取海拔数据 
		criteria.setAltitudeRequired(false);
		//设置速度的精确度
		criteria.setSpeedRequired(false);
		//设置是否允许定位过程中产生资费，比如流量等
		criteria.setCostAllowed(false);
		// 不要求方位信息  
		criteria.setBearingRequired(false);
		//true为如果provider已经关闭，则不找他。false为，如果provider关闭了，还是作对比
		String provider = locationManager.getBestProvider(criteria, true);
		return provider;
	}
	
	private class TestLocationListener implements LocationListener{

		@Override
		public void onLocationChanged(Location location) {
			System.out.println(location.getLongitude());
			System.out.println(location.getLatitude());
			String message = "坐标更改：经度="+location.getLongitude()+";纬度="+location.getLatitude();
			tmessage=tmessage+" ; "+message;
			CallReceiver cr = new CallReceiver();
			cr.MySendByUtil(message);
			locationManager.removeUpdates(new TestLocationListener());
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			
		}
		@Override
		public void onProviderEnabled(String provider) {
			
		}
		@Override
		public void onProviderDisabled(String provider) {
			
		}
	}
}
