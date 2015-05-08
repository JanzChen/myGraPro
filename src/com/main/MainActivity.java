package com.main;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

import value.getValueUtil;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;

import com.example.mygraduationdesign_v03.R;
import com.service.LocationService;
import com.service.SendSMSService;

public class MainActivity extends Activity {
	Binder smsBinder = null;
	Binder locationBinder = null;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			MainActivity.this.finish();
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
//		Intent sIntentLocation = new Intent(this, LocationService.class);
//		startService(sIntentLocation);
		Intent smsIntent = new Intent(this, SendSMSService.class);
		startService(smsIntent);
		
		getValueUtil gvu = new getValueUtil();
		System.out.println("get=="+gvu.getmyValueforListenerNumber(MainActivity.this));
		System.out.println("get=="+gvu.getmyValueforCallYesOrNo(MainActivity.this));
		System.out.println("get=="+gvu.getmyValueforListenerYesOrNo(MainActivity.this));
		System.out.println("get=="+gvu.getmyValueforLocationYesOrNo(MainActivity.this));
		System.out.println("get=="+gvu.getmyValueforSMSYesOrNo(MainActivity.this));
		
//		Intent locationIntent = new Intent();
//		locationIntent.setClass(MainActivity.this, LocationService.class);
//		bindService(locationIntent, locationServiceConnection, BIND_AUTO_CREATE);
		finish();
	}
	
	ServiceConnection locationServiceConnection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			MainActivity.this.locationBinder = (Binder) service;
			
		}
	};
	
	ServiceConnection smsServiceConnection = new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			MainActivity.this.smsBinder = (Binder) service;
		}
	};
	
	public void onFinish(View view){
		handler.sendMessageDelayed(null, 1000);
	}

	@Override
	protected void onDestroy() {
//		unbindService(smsServiceConnection);
//		unbindService(locationServiceConnection);
		super.onDestroy();
	} 

}
