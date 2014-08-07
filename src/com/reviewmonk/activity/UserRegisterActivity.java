package com.reviewmonk.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.reviewmonk.R;
import com.reviewmonk.adapter.ServiceHandler;
import com.reviewmonk.models.Constants;
import com.reviewmonk.models.UserModel;

public class UserRegisterActivity extends Activity implements OnClickListener {
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_register);
		Button b=(Button)findViewById(R.id.user_register_button);
		b.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
		
		final JSONObject parent=new JSONObject();
		JSONObject userData = new JSONObject();
		JSONObject deviceData = new JSONObject();
		
final UserModel u=new UserModel();

 String name="srini";
 String email="karthickpdy@gmail.com";
 String desc="its working";
 String work_place="freshdesk";
 String native_location="pdy";
 String language="tamil";
 Double gps_latitude=100.0;
 Double gps_longitude=2000.0;

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
					settings.edit().putString("user",u.getEmail());
					
					
				
				}
				return null;
			}
		}.execute();

		
	}
}
