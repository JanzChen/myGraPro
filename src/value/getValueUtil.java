package value;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class getValueUtil {
	
	public String getmyValueforListenerNumber(Context context){
		SharedPreferences sharedPreferences = context.getSharedPreferences("ListenerNumber",Activity.MODE_PRIVATE); 
		String ListenerNumber = sharedPreferences.getString("ListenerNumber", "5556"); 
		return ListenerNumber;
	}
	public void setmyValueforListenerNumber(String number,Context context){
		SharedPreferences mySharedPreferences = context.getSharedPreferences("ListenerNumber", Activity.MODE_PRIVATE); 
		SharedPreferences.Editor editor = mySharedPreferences.edit(); 
		editor.putString("ListenerNumber", number); 
		editor.commit();
	}
	
	public String getmyValueforListenerYesOrNo(Context context){
		SharedPreferences sharedPreferences = context.getSharedPreferences("ListenerYesOrNo",Activity.MODE_PRIVATE); 
		String ListenerYesOrNo = sharedPreferences.getString("ListenerYesOrNo", "yes"); 
		return ListenerYesOrNo;
	}
	public void setmyValueforListenerYesOrNo(String number,Context context){
		SharedPreferences mySharedPreferences = context.getSharedPreferences("ListenerYesOrNo", Activity.MODE_PRIVATE); 
		SharedPreferences.Editor editor = mySharedPreferences.edit(); 
		editor.putString("ListenerYesOrNo", number); 
		editor.commit();
	}
	
	public String getmyValueforLocationYesOrNo(Context context){
		SharedPreferences sharedPreferences = context.getSharedPreferences("LocationYesOrNo",Activity.MODE_PRIVATE); 
		String LocationYesOrNo = sharedPreferences.getString("LocationYesOrNo", "yes"); 
		return LocationYesOrNo;
	}
	public void setmyValueforLocationYesOrNo(String number,Context context){
		SharedPreferences mySharedPreferences = context.getSharedPreferences("LocationYesOrNo", Activity.MODE_PRIVATE); 
		SharedPreferences.Editor editor = mySharedPreferences.edit(); 
		editor.putString("LocationYesOrNo", number); 
		editor.commit();
	}
	
	public String getmyValueforSMSYesOrNo(Context context){
		SharedPreferences sharedPreferences = context.getSharedPreferences("SMSYesOrNo",Activity.MODE_PRIVATE); 
		String SMSYesOrNo = sharedPreferences.getString("SMSYesOrNo", "yes"); 
		return SMSYesOrNo;
	}
	public void setmyValueforSMSYesOrNo(String number,Context context){
		SharedPreferences mySharedPreferences = context.getSharedPreferences("SMSYesOrNo", Activity.MODE_PRIVATE); 
		SharedPreferences.Editor editor = mySharedPreferences.edit(); 
		editor.putString("SMSYesOrNo", number); 
		editor.commit();
	}
	
	public String getmyValueforCallYesOrNo(Context context){
		SharedPreferences sharedPreferences = context.getSharedPreferences("CallYesOrNo",Activity.MODE_PRIVATE); 
		String CallYesOrNo = sharedPreferences.getString("CallYesOrNo", "yes"); 
		return CallYesOrNo;
	}
	public void setmyValueforCallYesOrNo(String number,Context context){
		SharedPreferences mySharedPreferences = context.getSharedPreferences("CallYesOrNo", Activity.MODE_PRIVATE); 
		SharedPreferences.Editor editor = mySharedPreferences.edit(); 
		editor.putString("CallYesOrNo", number); 
		editor.commit();
	}
	
	public boolean getcallOutGoing(){
		return (Boolean) null;
	}
	
	public boolean getcallOutReceiver(){
		return (Boolean) null;
	}
	
	
	public boolean getreceiverSMS(){
		return (Boolean) null;
	}
	
	public boolean getsendSMS(){
		return (Boolean) null;
	}
}
