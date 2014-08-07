package com.reviewmonk.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.reviewmonk.R;
import com.reviewmonk.adapter.ServiceHandler;

public class UserProfile extends Activity implements OnClickListener{
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_profile);
		Button b=(Button)findViewById(R.id.user_profile_send_button);
		b.setOnClickListener(this);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.user_profile_send_button:
//			Intent gadgetCreate = new Intent(this,UserRegisterActivity.class);
//			startActivity(gadgetCreate);
		final JSONObject parent=new JSONObject();
			JSONObject userData = new JSONObject();
			JSONObject deviceData = new JSONObject();

			try {
				userData.put("name", "srini");
				userData.put("email", "karthickpdy@gmail.com");
				
				deviceData.put("id", "123");
				parent.put("user", userData);
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
//				Log.i("Review",s.makeServiceCall("http://hackday-mapi.flipkart.com/hackday/search", ServiceHandler.GET, params));
					Log.i("Review",s.makeServiceCall("http://192.168.255.100:3000/users/register", ServiceHandler.POST, params));
					return null;
				}
			}.execute();
			
			break;
		}
	}
}
