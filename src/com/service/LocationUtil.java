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
		//���þ�γ�ȵľ�׼�� ��ѡ������ACCURACY_FINE ׼ȷ ACCURACY_COARSE ���� 
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		//���úĵ����ļ��� 
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		//�����Ƿ���Ҫ��ȡ�������� 
		criteria.setAltitudeRequired(false);
		//�����ٶȵľ�ȷ��
		criteria.setSpeedRequired(false);
		//�����Ƿ�����λ�����в����ʷѣ�����������
		criteria.setCostAllowed(false);
		// ��Ҫ��λ��Ϣ  
		criteria.setBearingRequired(false);
		//trueΪ���provider�Ѿ��رգ���������falseΪ�����provider�ر��ˣ��������Ա�
		String provider = locationManager.getBestProvider(criteria, true);
		return provider;
	}
	
	private class TestLocationListener implements LocationListener{

		@Override
		public void onLocationChanged(Location location) {
			System.out.println(location.getLongitude());
			System.out.println(location.getLatitude());
			String message = "������ģ�����="+location.getLongitude()+";γ��="+location.getLatitude();
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
