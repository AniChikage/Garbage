package com.thars.chuangguan;

import com.thars.R;
import com.thars.chuangguan.Information;
import com.thars.question.User;
import com.thars.question.QuesOperate;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

 public class Second extends Activity{
 
	private Button sel_btn1;
	private Button sel_btn2;
	private ImageView img1; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cgsecond);

		img1 = (ImageView)findViewById(R.id.cgimg2);
		img1.setOnClickListener(new cg1());

	//	btn_ques_anime.setOnClickListener(new user_OnClick());
	}
	class cg1 implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
	        intent.setClass(Second.this, CGSecond.class);
	        startActivity(intent);
			
		}
	}
	
}
 
