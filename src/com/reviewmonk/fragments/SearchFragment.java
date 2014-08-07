package com.reviewmonk.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.reviewmonk.R;
import com.reviewmonk.activity.UserProfile;
import com.reviewmonk.adapter.UserListAdapter;

public class SearchFragment extends Fragment {
	Context mContext=this.getActivity();
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.search_fragment, container, false);
        String[] values = new String[] { "Android", "iPhone", "WindowsMobile",
				"Blackberry", "WebOS", "Ubuntu", "Windows7", "Max OS X",
				"Linux", "OS/2" };
		
        UserListAdapter adapter = new UserListAdapter(this.getActivity(), values);
		ListView l = (ListView) rootView.findViewById(R.id.users_list);
				l.setAdapter(adapter);
				l.setOnItemClickListener(new OnItemClickListener(){

			        public void onItemClick(AdapterView<?> parent, View view, int position, long id){


			            String inName = ((TextView) view.findViewById(R.id.user_id)).getText().toString();
//			            String inText = ((TextView) view.findViewById(R.id.user_image)).getText().toString();

			            
			            Intent intent = new Intent(getActivity(), UserProfile.class);

			            intent.putExtra("staff_name", inName);
//			            intent.putExtra("staff_desc", inText);
			            startActivity(intent);
			       }
			});
        return rootView;
    }

}
