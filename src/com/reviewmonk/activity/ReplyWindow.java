package com.reviewmonk.activity;

import com.reviewmonk.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ReplyWindow extends Activity implements OnClickListener{

	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reply_window);
		Button b=(Button)findViewById(R.id.accept_button);
		b.setOnClickListener(this);
		Button b1=(Button)findViewById(R.id.reject_button);
		b1.setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
switch (v.getId()) {
		
		case R.id.accept_button:
			break;

		case R.id.reject_button:
			break;
			
}
		
	}

}
