package com.reviewmonk.activity;

import java.util.ArrayList;
import java.util.Iterator;
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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.reviewmonk.R;
import com.reviewmonk.adapter.NewGadgetsListAdapter;
import com.reviewmonk.adapter.ServiceHandler;
import com.reviewmonk.models.Constants;

public class NewGadgetActivity extends Activity {
	String[] values= null;
	ListView l;
	String inName;
	String a;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.new_gadgets_activity);
		l = (ListView) findViewById(R.id.new_gadgets_activity_list);

		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... arg0) {
				// TODO Auto-generated method stub

				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("q", getIntent().getExtras()
						.getString("searchkey")));
				ServiceHandler s = new ServiceHandler();
				Log.i("A",
						"valeu of q" + getIntent().getStringExtra("searchkey"));
				a = s.makeServiceCall(
						"http://hackday-mapi.flipkart.com/hackday/search",
						ServiceHandler.GET, params).toString();
				Log.i("A", "valeu of a" + a);
				return null;
			}

			protected void onPostExecute(Void arg) {
				super.onPostExecute(arg);
				values = populate(a);
				Log.i("values outside", values[0]);
				if (values.length != 0) {
					NewGadgetsListAdapter adapter = new NewGadgetsListAdapter(
							getApplicationContext(), values);

					l.setAdapter(adapter);
					l.setOnItemClickListener(new OnItemClickListener() {

						public void onItemClick(AdapterView<?> parent,
								View view, int position, long id) {

							//

							inName = ((TextView) view
									.findViewById(R.id.gadget_name1)).getText()
									.toString();
							new AsyncTask<Void, Void, Void>() {

								@Override
								protected Void doInBackground(Void... arg0) {
									JSONObject j = new JSONObject();
									try {
										j.put("email",
												getSharedPreferences(
														Constants.PREFERENCE,
														Context.MODE_PRIVATE)
														.getString("user", ""));
										j.put("name", inName);
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									ServiceHandler s = new ServiceHandler();
									List<NameValuePair> params = new ArrayList<NameValuePair>();
									params.add(new BasicNameValuePair(
											"attach_product", j.toString()));
									s.makeServiceCall(Constants.HOST
											+ "/users/attach_product",
											ServiceHandler.POST, params);

									return null;
								}

							}.execute();

							Intent intent = new Intent(getBaseContext(),
									GadgetsActivity.class);
							intent.putExtra("staff_name", inName);
							// intent.putExtra("staff_desc", inText);
							startActivity(intent);
						}
					});
				}

			}
		}.execute();

	}

	public String[] populate(String str) {
		Log.i("value of str", str);

		try {
			JSONObject jsonObject = new JSONObject(str);
			jsonObject = ((JSONObject) ((JSONObject) (jsonObject
					.get("RESPONSE"))).get("product"));
			Iterator<String> keys = jsonObject.keys();
			String[] s = new String[10];
			int l = 0;
			String key = "";

			while (keys.hasNext()) {
				key = keys.next();
				s[l++] = ((JSONObject) jsonObject.get(key)).get("mainTitle")
						.toString();
			}
			Log.i("value of pop", s[0]);
			return s;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
