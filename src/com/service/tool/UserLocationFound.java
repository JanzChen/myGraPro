package com.service.tool;  
  
import android.content.Context;  
import android.location.Criteria;  
import android.location.Location;  
import android.location.LocationListener;  
import android.location.LocationManager;  
import android.os.Bundle;  
import android.widget.Toast;  
  
public class UserLocationFound{  
      
    private double latitude = 0;  
    private double longitude = 0;  
    private Context context;  
      
    private LocationManager locationManager;  
    private String provider;  
    private Location location;  
      
    public UserLocationFound(Context context){  
        this.context= context;  
        setLatitudeAndLongitude();    
    }  
      
    public void setLatitude(int latitude) {  
        this.latitude = latitude;  
    }  
  
    public double getLatitude() {  
        return latitude;  
    }  
  
    public void setLongitude(int longitude) {  
        this.longitude = longitude;  
    }  
  
    public double getLongitude() {  
        return longitude;  
    }  
  
    public void setLatitudeAndLongitude() {  
        // ��ȡ LocationManager ����  
        locationManager =  (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);  
//      locationManager.setTestProviderEnabled("gps", true);  
        // ��ȡ Location Provider  
        getProvider();  
        // ���δ����λ��Դ���� GPS ���ý���  
        openGPS();  
        // ��ȡλ��  
        location = locationManager.getLastKnownLocation(provider);  
        // ��ʾλ����Ϣ�����ֱ�ǩ  
        updateWithNewLocation(location);  
        // ע������� locationListener ���� 2 �� 3 ���������Կ��ƽ��� gps ��Ϣ��Ƶ���Խ�ʡ�������� 2 ������Ϊ���룬  
        // ��ʾ���� listener �����ڣ��� 3 ������Ϊ�� , ��ʾλ���ƶ�ָ�������͵��� listener  
          
    }  
      
    // Gps ��Ϣ������  
    private final LocationListener locationListener = new LocationListener() {  
          
        // λ�÷����ı�����  
        public void onLocationChanged(Location location) {  
            updateWithNewLocation(location);  
        }  
        // provider ���û��رպ����  
        public void onProviderDisabled(String provider) {  
            updateWithNewLocation(null);  
        }  
  
        // provider ���û����������  
        public void onProviderEnabled(String provider) {          
              
        }  
  
        // provider ״̬�仯ʱ����  
        public void onStatusChanged(String provider, int status,Bundle extras) {  
        }  
    };  
  
    private void updateWithNewLocation(Location location2) {  
        while(location == null){  
            locationManager.requestLocationUpdates(provider, 2000, (float) 0.1, locationListener);  
        }  
        if (location != null) {  
            latitude = ((double)(location.getLatitude()*100000));  
            longitude = ((double)(location.getLongitude()*100000));  
        } else {  
            latitude = 37.422006;  
            longitude = -122.084095;  
        }  
    }  
  
    private void openGPS() {  
          
        if (locationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER) || 
        	locationManager.isProviderEnabled(android.location.LocationManager.NETWORK_PROVIDER)){  
            Toast.makeText(context, " λ��Դ�����ã� ", Toast.LENGTH_SHORT).show();  
            return;  
        }   
        Toast.makeText(context, " λ��Դδ���ã�", Toast.LENGTH_SHORT).show();  
    }  
  
    private void getProvider() {  
        // ����λ�ò�ѯ����  
        Criteria criteria = new Criteria();  
        // ��ѯ���ȣ���  
        criteria.setAccuracy(Criteria.ACCURACY_FINE);  
        // �Ƿ��ѯ��������  
        criteria.setAltitudeRequired(false);  
        // �Ƿ��ѯ��λ�� : ��  
        criteria.setBearingRequired(false);  
        // �Ƿ������ѣ���  
        criteria.setCostAllowed(true);  
        // ����Ҫ�󣺵�  
        criteria.setPowerRequirement(Criteria.POWER_LOW);  
        // ��������ʵķ��������� provider ���� 2 ������Ϊ true ˵�� , ���ֻ��һ�� provider ����Ч�� , �򷵻ص�ǰ  
        // provider  
        provider = locationManager.getBestProvider(criteria, true);  
    }  
} 