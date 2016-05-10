package com.thars;

import com.thars.R;
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
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 实现SQLite数据库增加、删除、修改、查询操作
 * 
 */
 public class Administrator extends Activity{
 
	 private Button btn_user = null;
	 private Button btn_ques_anime = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.administrator);
		
		btn_user = (Button)findViewById(R.id.adminis_user);
		btn_ques_anime = (Button)findViewById(R.id.adminis_ques_anime);

		btn_user.setOnClickListener(new user_OnClick());
		btn_ques_anime.setOnClickListener(new anime_Onclick());
	}
	class user_OnClick implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
	        intent.setClass(Administrator.this, Adminis_user.class);
	        startActivity(intent);
			
		}
	}
	class anime_Onclick implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
	        intent.setClass(Administrator.this, Adminis_anime.class);
	        startActivity(intent);
			
		}
	}

		
	}
 


		
	//	btn_user = (Button)findViewById(R.id.adminis_user);
	//	btn_ques_anime = (Button)findViewById(R.id.adminis_ques_anime);
		
	//	btn_user.setOnClickListener(new user_OnClick());
	//	btn_ques_anime.setOnClickListener(new anime_Onclick());
		
	
	


