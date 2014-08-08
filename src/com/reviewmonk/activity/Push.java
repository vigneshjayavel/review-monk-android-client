package com.reviewmonk.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.reviewmonk.R;
import com.reviewmonk.adapter.ServiceHandler;
import com.reviewmonk.models.Constants;

	public class Push extends Activity implements OnClickListener {
		 String status;
		 String buyer_email;
		 String buyer_name;
		 String buyer_bio;
		 String product;
		 TextView u;
		 TextView p;
		 TextView bio_field;
		 
		 
		
		 public static final String EXTRA_MESSAGE = "message";
		    public static  String PROPERTY_REG_ID = "registration_id";
		    private static final String PROPERTY_APP_VERSION = "appVersion";
		    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
		    String SENDER_ID = "556694333800";
		    
		    TextView mDisplay;
		    GoogleCloudMessaging gcm;
		    AtomicInteger msgId = new AtomicInteger();
		    SharedPreferences prefs;
		    Context context;  
		    static final String TAG = "GCMDemo";
		    String regid;

	   NotificationManager NM;
	   EditText one,two,three;
	   @SuppressLint("NewApi")
	@Override
	   protected void onCreate(Bundle savedInstanceState)
	   {
			super.onCreate(savedInstanceState);
    		setContentView(R.layout.reply_window);
    	context = getApplicationContext();
	            if (checkPlayServices()) 
	        {
	               gcm = GoogleCloudMessaging.getInstance(this);
	               regid = getRegistrationId(context);

	               if (regid.isEmpty())
	               {
	                   registerInBackground();
	               }
	               else
	               {
	               Log.i(TAG, "No valid Google Play Services APK found." + regid);
	              
	               }
	         }
//	            Intent intent=new Intent(this,ReplyWindow.class);
//	            startActivity(intent);
	            
//	            JSONObject buyer;
//	            JSONObject products;
//				try {
//					Log.i("intent", getIntent().toString());
//					buyer = new JSONObject(getIntent().getStringExtra("sender"));
//					buyer_email=buyer.getString("email");
//					buyer_name=buyer.getString("name");
//					buyer_bio=buyer.getString("description");
//					
//					products = new JSONObject(getIntent().getStringExtra("products"));
//					product=products.getString("name");
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//	            
	            
	        	Button b=(Button)findViewById(R.id.accept_button);
	    		b.setOnClickListener(this);
	    		Button b1=(Button)findViewById(R.id.reject_button);
	    		b1.setOnClickListener(this);
	    	u=(TextView)findViewById(R.id.reply_sent_user);
	    	p=(TextView)findViewById(R.id.reply_sent_user_product);
	    	bio_field=(TextView)findViewById(R.id.reply_sent_user_bio);
	    	u.setText(buyer_name);
	    	p.setText(product);
	    	bio_field.setText(buyer_bio);
	            
	    }
	   @Override
	   public boolean onCreateOptionsMenu(Menu menu) {
	      // Inflate the menu; this adds items to the action bar if it is present.
//	      getMenuInflater().inflate(R.menu.main, menu);
	      return true;
	   }

	   @Override protected void onResume()
	   {
	          super.onResume();       
	          checkPlayServices();
	   }
	   
	   private boolean checkPlayServices() {
	        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
	        if (resultCode != ConnectionResult.SUCCESS) {
	            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
	                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
	                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
	            } else {
	                Log.d(TAG, "This device is not supported - Google Play Services.");
	                finish();
	            }
	            return false;
	        }
	        return true;
	 }
	   
	   @SuppressLint("NewApi")
	private String getRegistrationId(Context context) 
	   {
	      final SharedPreferences prefs = getGCMPreferences(context);
	      String registrationId = prefs.getString(PROPERTY_REG_ID, "");
	      if (registrationId.isEmpty()) {
	          Log.d(TAG, "Registration ID not found.");
	          return "";
	      }
	      int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
	      int currentVersion = getAppVersion(context);
	      if (registeredVersion != currentVersion) {
	           Log.d(TAG, "App version changed.");
	           return "";
	       }
	       return registrationId;
	   }
	   
	   
	   private SharedPreferences getGCMPreferences(Context context) 
	   {
	       return getSharedPreferences(Push.class.getSimpleName(),
	                   Context.MODE_PRIVATE);
	   }

	   private static int getAppVersion(Context context) 
	   {
	        try 
	        {
	            PackageInfo packageInfo = context.getPackageManager()
	                       .getPackageInfo(context.getPackageName(), 0);
	               return packageInfo.versionCode;
	         } 
	         catch (NameNotFoundException e) 
	         {
	               throw new RuntimeException("Could not get package name: " + e);
	         }
	   }


	   private void registerInBackground() 
	   {     new AsyncTask() {	
	        @Override
	        protected Object doInBackground(Object... params) 
	        {
	             String msg = "";
	             try 
	             {
	                  if (gcm == null) 
	                  {
	                           gcm = GoogleCloudMessaging.getInstance(context);
	                  }
	                  regid = gcm.register(SENDER_ID);               
	                  Log.d(TAG, "########################################");
	                  Log.d(TAG, "Current Device's Registration ID is: "+regid); 
	                  PROPERTY_REG_ID=regid;
	                  storeRegistrationId(context, regid);
	             } 
	             catch (IOException ex) 
	             {
	                 msg = "Error :" + ex.getMessage();
	             }
	             return null;
	        }     
	        protected void onPostExecute(Object result) 
	        { //to do here 
	        };
	        
	        
	     }.execute(null, null, null);
	   }
	   
	   
	   public void suma(final View view) 
	   {
	        new AsyncTask() {     
	        	@Override
	        protected String doInBackground(Object... params) 
	        {
	             Log.i(TAG,"here");
	        		String msg = "";          
	        		try {
	                  Bundle data = new Bundle();
	                  data.putString("my_message", "Hello World");
	                  data.putString("my_action",
	                  "com.google.android.gcm.demo.app.ECHO_NOW");
	                  String id = Integer.toString(msgId.incrementAndGet());              
	                  gcm.send(SENDER_ID + "@gcm.googleapis.com", id, data);
	                  msg = "Sent message";
	              } catch (IOException ex) {
	                    msg = "Error :" + ex.getMessage();
	              }
	              return msg;
	        }     
	        	protected void onPostExecute(String msg) {
	             mDisplay.append(msg + "\n");
	        }
	     }.execute(null, null, null);
	   }

	   private void storeRegistrationId(Context context, String regId) {
		    final SharedPreferences prefs = getGCMPreferences(context);
		    int appVersion = getAppVersion(context);
		    Log.i(TAG, "Saving regId on app version " + appVersion);
		    SharedPreferences.Editor editor = prefs.edit();
		    editor.putString(PROPERTY_REG_ID, regId);
		    editor.putInt(PROPERTY_APP_VERSION, appVersion);
		    editor.commit();
		}
	   
	   
	   public void onClick(View v) {
			// TODO Auto-generated method stub
			
	switch (v.getId()) {
			
			case R.id.accept_button:
				status="accept";
				break;

			case R.id.reject_button:
				status="reject";
				break;
	}
	new AsyncTask<Void, Void, Void>() {

		@Override
		protected Void doInBackground(Void... args) {
			// TODO Auto-generated method stub
			
            ServiceHandler s=new ServiceHandler();
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            
            JSONObject j=new JSONObject();
            try {
            	
            	j.put(		"sender_email",
					getSharedPreferences(
								Constants.PREFERENCE,
								Context.MODE_PRIVATE)
								.getString("user", ""));
            
				j.put("receiver_email", buyer_email);
				j.put("status", status);
		
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
    		params.add(new BasicNameValuePair("review_response",j.toString()));
      s.makeServiceCall(Constants.HOST+"/users/send_review_request", ServiceHandler.POST, params);
            
			return null;
		}
		protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if(status.equals("accept")){
            Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("text/plain");
            startActivity(emailIntent);    }  
		}
		
	}.execute();			
		}

	}  