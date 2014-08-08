package com.reviewmonk.fragments;

import android.content.Intent;
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

public class GadgetsFragment extends Fragment implements OnClickListener {
	String val="";
	View rootView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		 rootView = inflater.inflate(R.layout.gadgets_fragment, container,
				false);
		String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
				"Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
				"Linux", "OS/2" };
		GadgetsListAdapter adapter = new GadgetsListAdapter(this.getActivity(), values);
		ListView l = (ListView) rootView.findViewById(R.id.gadgets_list);
				l.setAdapter(adapter);
		
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
