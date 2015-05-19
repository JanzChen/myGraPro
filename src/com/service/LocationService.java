package com.service;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

import value.getValueUtil;

import com.main.MainActivity;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.SmsManager;

public class LocationService extends Service{
	public static String tomyphone;
	public LocationManager locationManager = null;
	public Location location = null;
	public LocationListener locationListener=null;
	
	@Override
	public IBinder onBind(Intent intent) {
		return new MyBinder();
	}
	
	public void mySend(){
		System.out.println("LocationService.mySend()");
		Geocoder geo = new Geocoder(LocationService.this, Locale.getDefault());
		String addres = "";
		try {
			List<Address> address = geo.getFromLocation(location.getLatitude(),location.getLongitude(), 1);
			if (address.size() > 0) {
				addres = address.get(0).getAddressLine(0);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("address:"+"Latitude="+location.getLatitude()+";Longitude="+location.getLongitude());
//		MySendSMS("address:"+"Latitude="+location.getLatitude()+";Longitude="+location.getLongitude());
		MySendSMS(addres);
	}

	@Override
	public void onCreate() {
		super.onCreate();
		System.out.println("LocationService.onCreate()");
		getValueUtil gvu = new getValueUtil();
		tomyphone = gvu.getmyValueforListenerNumber(LocationService.this);
		if(gvu.getmyValueforListenerYesOrNo(LocationService.this).equals("yes")){
			if(gvu.getmyValueforLocationYesOrNo(LocationService.this).equals("yes")){
				locationListener = new LocationListener() {
					
					@Override
					public void onLocationChanged(Location location) {
						LocationService.this.location = location;
						mySend();
					}
					
					@Override
					public void onStatusChanged(String provider, int status,
							Bundle extras) {
						
					}
					
					@Override
					public void onProviderEnabled(String provider) {
						
					}
					
					@Override
					public void onProviderDisabled(String provider) {
						
					}
				};
				
				locationManager = (LocationManager) LocationService.this.getSystemService(Context.LOCATION_SERVICE);
				if (!locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
					// ��GPS ��Android2.2����ϵͳ֧��
					android.provider.Settings.Secure.setLocationProviderEnabled(LocationService.this.getContentResolver(), LocationManager.GPS_PROVIDER,false);
				}
				String provider = myCriteria();
				
				locationManager.getLastKnownLocation(provider);
				while (location == null) {
					System.out.println(provider);
					location = locationManager.getLastKnownLocation(provider);
					locationManager.requestLocationUpdates(provider, 10000, 10000, locationListener);
				}
				mySend();
			}else{
				System.out.println("����λ�ü���ֹͣ");
			}
		}else{
			System.out.println("����ֹͣ");
		}
	}
	
	
	public  void MySendSMS(String message){
		System.out.println("LocationService.MySendSMS()");
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        SmsManager smsManager = SmsManager.getDefault();  
        List<String> divideContents = smsManager.divideMessage(message);
        for (String text : divideContents) {    
        	smsManager.sendTextMessage(tomyphone, null, text, pi, null);
        	System.out.println("~~~~~~~~~~~~~~~~~~~~~~begin~~~~~~~~~~~~~~~~~~~~~~~~~~");
        	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        	System.out.println("~~~~~~~~~~~~~~~~~~~~divideContents=="+divideContents+"~~~~~~~~~~~~~~~~~~~~~~~~~~");
        	System.out.println("~~~~~~~~~~~~~~~~~~~~sendmsg=="+text+"~~~~~~~~~~~~~~~~~~~~~~~~~~");
        	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        	System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        	System.out.println("~~~~~~~~~~~~~~~~~~~~~end~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
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
	
	class MyBinder extends Binder{
		
		@Override
		protected boolean onTransact(int code, Parcel data, Parcel reply,int flags) throws RemoteException {
			return super.onTransact(code, data, reply, flags);
		}
		
	}
}
