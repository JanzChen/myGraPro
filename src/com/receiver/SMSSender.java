package com.receiver;

import value.myValue;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;

public class SMSSender extends BroadcastReceiver{
	private String sendMobile =  myValue.sendMsgPhone;
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		System.out.println("SMSSender.onReceive()");
//		SmsManager smsManager = SmsManager.getDefault();
//		Intent sentIntent = new Intent("SENT_SMS_ACTION");
//		PendingIntent mPI = PendingIntent.getBroadcast(null, 0,sentIntent, 0);
//		smsManager.sendTextMessage(sendMobile, null, "hello,I am Janz", null, null);
	}

}
