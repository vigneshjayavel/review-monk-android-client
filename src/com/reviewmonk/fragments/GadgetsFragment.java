package com.reviewmonk.fragments;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.reviewmonk.R;
import com.reviewmonk.activity.NewGadgetActivity;
import com.reviewmonk.adapter.GadgetsListAdapter;
import com.reviewmonk.adapter.ServiceHandler;
import com.reviewmonk.models.Constants;

public class GadgetsFragment extends Fragment implements OnClickListener {
	String val="";
	View rootView;
	String[] values=new String[100];
	ListView l ;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		 rootView = inflater.inflate(R.layout.gadgets_fragment, container,
				false);
		 l = (ListView) rootView.findViewById(R.id.gadgets_list);
		if(!getActivity().getSharedPreferences(Constants.PREFERENCE, Context.MODE_PRIVATE).getBoolean("my_first_time", true)){
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... arg0) {
				ServiceHandler s = new ServiceHandler();
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair(
						"email",
						getActivity().getSharedPreferences(
								Constants.PREFERENCE,
								Context.MODE_PRIVATE)
								.getString("user", "")));
			val=	s.makeServiceCall(Constants.HOST
						+ "/users/list_user_products",
						ServiceHandler.POST, params);

				return null;
			}
			
			protected void onPostExecute(Void arg) {
				super.onPostExecute(arg);
				
				JSONObject js;
	            try {
					 js=new JSONObject(val);
					 JSONArray products=js.getJSONArray("products");
					 for(int i=0;i<products.length();i++)
					 {
						 values[i]=products.getJSONObject(i).getString("name");
						 Log.i("product",values[i]);
					 }
					 
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            
	            GadgetsListAdapter adapter = new GadgetsListAdapter(getActivity(), values);
	    		
	    				l.setAdapter(adapter);
	    	
			
			}

		}.execute();
	
		}
		Button b = (Button) rootView.findViewById(R.id.new_gadget_button);
		b.setOnClickListener(this);
	
		return rootView;
	}

	public void onClick(View v) {
		switch (v.getId()) {
		
		case R.id.new_gadget_button:
			Intent gadgetCreate = new Intent(this.getActivity(),NewGadgetActivity.class);
			val=	((EditText) rootView.findViewById(R.id.gadgets_fragments_text)).getText().toString();
			Log.i("val","value of val"+val);
			gadgetCreate.putExtra("searchkey", val);
			startActivity(gadgetCreate);
			break;
		}
	}
}
