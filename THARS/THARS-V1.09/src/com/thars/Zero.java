package com.thars;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.thars.R;

public class Zero  extends Activity {
	
	public ProgressBar pb;
	private Button btn;
	private int flag;
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zero);
        
        pb = (ProgressBar)findViewById(R.id.scan);
      //  btn = (Button)findViewById(R.id.zero_btn);
        pb.setOnClickListener(new enter_OnClickListener());
        
    }
	
	class enter_OnClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			
			// TODO Auto-generated method stub
			ConnectivityManager cwjManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = cwjManager.getActiveNetworkInfo();
			if (info != null && info.isAvailable()) {
				// do nothing
				flag = 1;
				Toast.makeText(Zero.this, "数据更新成功！~！",Toast.LENGTH_SHORT).show();
				Intent intent = new Intent();
		        intent.setClass(Zero.this, MainActivity.class);
		        startActivity(intent);
			} else {
				flag = 0;
				Toast.makeText(Zero.this, "请检查您的网络连接！！",Toast.LENGTH_SHORT).show();
			}

		}
	}
}
