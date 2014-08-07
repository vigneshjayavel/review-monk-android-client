package com.reviewmonk.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.reviewmonk.R;

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
			Intent gadgetCreate = new Intent(this,UserRegisterActivity.class);
			startActivity(gadgetCreate);
			break;
		}
	}
}
