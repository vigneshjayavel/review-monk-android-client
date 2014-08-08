package com.reviewmonk.activity;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.reviewmonk.R;
import com.reviewmonk.adapter.ServiceHandler;
import com.reviewmonk.models.Constants;

public class UserProfile extends Activity implements OnClickListener{
	protected String val;
	TextView id;
	TextView bio;
	EditText invite;
	String email_value;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_profile);
		Button b=(Button)findViewById(R.id.user_profile_send_button);
		b.setOnClickListener(this);
		
		id=(TextView)findViewById(R.id.user_profile_id);
		bio=(TextView)findViewById(R.id.user_profile_bio);
	invite=(EditText)findViewById(R.id.user_profile_invite_message);
		
		
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... args) {
				// TODO Auto-generated method stub
				
	            ServiceHandler s=new ServiceHandler();
	            int pos=getIntent().getIntExtra("pos", 0);
	            email_value=getIntent().getStringArrayListExtra("email_list").get(pos);
	            List<NameValuePair> params = new ArrayList<NameValuePair>();
	    		params.add(new BasicNameValuePair("email",email_value));
	     val= s.makeServiceCall(Constants.HOST+"/users/owner_details", ServiceHandler.POST, params);
	            
				return null;
			}
        	
			
			protected void onPostExecute(Void result) {
	            super.onPostExecute(result);
	            JSONObject js;
	            try {
					 js=new JSONObject(val);
					 JSONObject user=js.getJSONObject("user");
					 
					 id.setText(user.getString("name"));
					 bio.setText(user.getString("description"));
					 
					 
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            
	        
	    		
			
			
			
			}
			
		}.execute();

		
	}

	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.user_profile_send_button:
			Intent gadgetCreate = new Intent(this,Push.class);
			new AsyncTask<Void, Void, Void>() {

				@Override
				protected Void doInBackground(Void... args) {
					// TODO Auto-generated method stub
					
		            ServiceHandler s=new ServiceHandler();
		            int pos=getIntent().getIntExtra("pos", 0);
		            List<NameValuePair> params = new ArrayList<NameValuePair>();
		            
		            JSONObject j=new JSONObject();
		            try {
		            	
		            	j.put(		"sender_email",
							getSharedPreferences(
										Constants.PREFERENCE,
										Context.MODE_PRIVATE)
										.getString("user", ""));
		            
						j.put("receiver_email", email_value);
						j.put("product_name", getIntent().getStringExtra("product_name"));
						j.put("message", invite.getText().toString());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		            
		    		params.add(new BasicNameValuePair("review_request",j.toString()));
		     val= s.makeServiceCall(Constants.HOST+"/users/send_review_request", ServiceHandler.POST, params);
		            
					return null;
				}
			}.execute();
			
			
			startActivity(gadgetCreate);
						
			break;
		}
	}
}
