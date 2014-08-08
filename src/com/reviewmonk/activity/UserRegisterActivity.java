package com.reviewmonk.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.reviewmonk.R;
import com.reviewmonk.adapter.ServiceHandler;
import com.reviewmonk.models.Constants;
import com.reviewmonk.models.UserModel;

public class UserRegisterActivity extends Activity implements OnClickListener {
	LocationManager locationMangaer=null;
	Location loc=null;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_register);
		Button b=(Button)findViewById(R.id.user_register_button);
		b.setOnClickListener(this);
		  locationMangaer = (LocationManager) 
				  getSystemService(Context.LOCATION_SERVICE);
		MyLocationListener  locationListener = new MyLocationListener();
		   locationMangaer.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1,locationListener);
		   
		   loc=locationMangaer.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		   Toast.makeText(getBaseContext(),"Location man : Lat: " +
				   loc.getLatitude()+ " Lng: " + loc.getLongitude(),
				   Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		
		final JSONObject parent=new JSONObject();
		JSONObject userData = new JSONObject();
		JSONObject deviceData = new JSONObject();
		
final UserModel u=new UserModel();

 String name=((EditText)findViewById(R.id.user_register_name)).getText().toString();
 String email=((EditText)findViewById(R.id.user_register_email)).getText().toString();
 String desc=((EditText)findViewById(R.id.user_register_display_msg)).getText().toString();
 String work_place=((EditText)findViewById(R.id.user_register_work_place)).getText().toString();
 String native_location=((EditText)findViewById(R.id.user_register_native_location)).getText().toString();
 String language=((EditText)findViewById(R.id.user_register_language)).getText().toString();
 Double gps_latitude=loc.getLatitude();
 Double gps_longitude=loc.getLongitude();

u.set(name, email, desc, work_place, native_location, language, gps_latitude, gps_longitude);
		try {
			deviceData.put("registration_id", "123");
			parent.put("user", u.to_json());
			parent.put("device", deviceData);
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... arg0) {
				// TODO Auto-generated method stub
				
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("registration", parent.toString()));
				ServiceHandler s=new ServiceHandler();
				String response=s.makeServiceCall(Constants.HOST+"/users/register", ServiceHandler.POST, params);
//			Log.i("Review",s.makeServiceCall("http://hackday-mapi.flipkart.com/hackday/search", ServiceHandler.GET, params));
				JSONObject responseJson;
				String status=null;
				try {
					responseJson = new JSONObject(response);
					status=responseJson.getString("status");
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				if(status.equals("success"))
				{
				
					SharedPreferences settings = getSharedPreferences(
						      Constants.PREFERENCE, Context.MODE_PRIVATE);
					settings.edit().putString("user",u.getEmail()).apply();
					Intent intent=new Intent(getApplicationContext(),GadgetsActivity.class);
					startActivity(intent);
					
					
				
				}
				return null;
			}
		}.execute();

		
	}

	 /*----------Listener class to get coordinates ------------- */
	 private class MyLocationListener implements LocationListener {
	        @Override
	        public void onLocationChanged(Location loc) {
	          
	            Toast.makeText(getBaseContext(),"Location changed : Lat: " +
	   loc.getLatitude()+ " Lng: " + loc.getLongitude(),
	   Toast.LENGTH_SHORT).show();
	            String longitude = "Longitude: " +loc.getLongitude();  
	      Log.i("lat", longitude);
	      String latitude = "Latitude: " +loc.getLatitude();
	      Log.i("lat", latitude);
	          
	    /*----------to get City-Name from coordinates ------------- */
	      String cityName=null;              
	      Geocoder gcd = new Geocoder(getBaseContext(), 
	   Locale.getDefault());             
	      List<Address>  addresses;  
	      try {  
	      addresses = gcd.getFromLocation(loc.getLatitude(), loc
	   .getLongitude(), 1);  
	      if (addresses.size() > 0)  
	         System.out.println(addresses.get(0).getLocality());  
	         cityName=addresses.get(0).getLocality();  
	        } catch (IOException e) {            
	        e.printStackTrace();  
	      } 
	          
	        }

	        @Override
	        public void onProviderDisabled(String provider) {
	            // TODO Auto-generated method stub         
	        }

	        @Override
	        public void onProviderEnabled(String provider) {
	            // TODO Auto-generated method stub         
	        }

	        @Override
	        public void onStatusChanged(String provider, 
	  int status, Bundle extras) {
	            // TODO Auto-generated method stub         
	        }
	    }

}
