package com.service;


import java.util.List;
import value.myValue;

import com.main.MainActivity;
import com.service.SendSMSService.MyBinder;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.SmsManager;

public class LocationService extends Service{
	public static final String tomyphone = myValue.sendMsgPhone;
	
	@Override
	public IBinder onBind(Intent intent) {
//		updateBArHandler.post(updateThread);
		LocationTools lt = new LocationTools();
		MySendSMS(lt.getAddress(LocationService.this));
		System.out.println("send message~~~~~~~~~~~~~~~~~~~~~");
		return new MyBinder();
	}
	
	class MyBinder extends Binder{
		
		@Override
		protected boolean onTransact(int code, Parcel data, Parcel reply,int flags) throws RemoteException {
			System.out.println("LocationService.MyBinder.onTransact()");
			return super.onTransact(code, data, reply, flags);
		}
		
	}

	@Override
	public void onCreate() {
		super.onCreate();
	}
	
//	Handler updateBArHandler = new Handler(){
//
//		@Override
//		public void handleMessage(Message msg) {
//			// TODO Auto-generated method stub
//			super.handleMessage(msg);
//			System.out.println("≤‚ ‘333");
//			LocationTools lt = new LocationTools();
//			MySendSMS(lt.getAddress(LocationService.this));
//			System.out.println("send message~~~~~~~~~~~~~~~~~~~~~");
//			updateBArHandler.post(updateThread);
//		}
//		
//	};
	
//	Runnable updateThread = new Runnable() {
//		public void run() {
//			Message msg = updateBArHandler.obtainMessage();
//			try {
//				Thread.sleep(3600000);
//				System.out.println("LocationService.enclosing_method()");
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//			updateBArHandler.sendMessage(msg);
//		}
//	};
	
	public  void MySendSMS(String message){
		System.out.println("LocationService.MySendSMS()");
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        SmsManager smsManager = SmsManager.getDefault();  
        List<String> divideContents = smsManager.divideMessage(message);    
        for (String text : divideContents) {    
        	smsManager.sendTextMessage(tomyphone, null, text, pi, null);
        }
    }
	
}
