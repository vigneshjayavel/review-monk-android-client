package com.reviewmonk.fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.Preference;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.reviewmonk.R;
import com.reviewmonk.activity.Push;
import com.reviewmonk.activity.UserProfile;
import com.reviewmonk.adapter.ServiceHandler;
import com.reviewmonk.adapter.UserListAdapter;
import com.reviewmonk.models.Constants;

public class SearchFragment extends Fragment implements OnClickListener {
	Context mContext=this.getActivity();
	String inName;
	String[] values=new String[100];
	ListView l;
	String val;
	View rootView ;
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        rootView = inflater.inflate(R.layout.search_fragment, container, false);
//        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
//				"Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
//				"Linux", "OS/2" };
        
        l = (ListView) rootView.findViewById(R.id.users_list);
//		
       
    	Button b=(Button)rootView.findViewById(R.id.user_search_button);
		b.setOnClickListener(this);
                return rootView;
    }
	
	
	
	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.user_search_button:
			 inName = ((TextView) rootView.findViewById(R.id.user_search)).getText().toString();
			 new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... args) {
						// TODO Auto-generated method stub
						JSONObject j=new JSONObject();
			            try {
							j.put("user_email",getActivity().getSharedPreferences(
									Constants.PREFERENCE,
									Context.MODE_PRIVATE)
									.getString("user", ""));
							j.put("product_name",inName);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			            ServiceHandler s=new ServiceHandler();
			            List<NameValuePair> params = new ArrayList<NameValuePair>();
			    		params.add(new BasicNameValuePair("search",j.toString()));
			     val= s.makeServiceCall(Constants.HOST+"/users/search_users", ServiceHandler.POST, params);
			            
						return null;
					}
		        	
					
					protected void onPostExecute(Void result) {
			            super.onPostExecute(result);
			            JSONObject js;
			            try {
							 js=new JSONObject(val);
							 JSONArray users=js.getJSONArray("users");
							 for(int i=0;i<users.length();i++)
							 {
								 values[i]=users.getJSONObject(i).getString("name");
							 }
							 
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			            
			            UserListAdapter adapter = new UserListAdapter(getActivity(), values);
			    		
						l.setAdapter(adapter);
						l.setOnItemClickListener(new OnItemClickListener(){

					        public void onItemClick(AdapterView<?> parent, View view, int position, long id){


					            String inName = ((TextView) view.findViewById(R.id.user_id)).getText().toString();
//					            String inText = ((TextView) view.findViewById(R.id.user_image)).getText().toString();

					            
					            Intent intent = new Intent(getActivity(), UserProfile.class);

					            intent.putExtra("staff_name", inName);
//					            intent.putExtra("staff_desc", inText);
					            startActivity(intent);
					       }
					});

					
					
					}
					
				}.execute();
		        
		        
			break;
		}
	}

}
