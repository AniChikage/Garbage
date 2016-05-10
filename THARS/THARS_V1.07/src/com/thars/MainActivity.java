package com.thars;


import com.thars.R;
import com.thars.MainActivity;
import com.thars.MainActivity;
import com.thars.question.LearnUser;
import com.thars.question.QuesOperate;

import android.R.string;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {

	private Button btn_login,btn_regis,btn_test_enter;
	
	private EditText account_name;
	private EditText account_pwd;
	//public string name;
	public LearnUser luser; 
	
	MediaPlayer mp;                         //MediaPlayer引用  
    AudioManager am;                        //AudioManager引用  
    private SoundPool sp;//声明一个SoundPool
    private int music;//定义一个整型用load（）；来设置suondID

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


	//	btn1 = (Button)findViewById(R.id.button1);
	//	btn2 = (Button)findViewById(R.id.button2);
		btn_login = (Button)findViewById(R.id.login);
		btn_regis = (Button)findViewById(R.id.go_register);
	//	btn_test_enter = (Button)findViewById(R.id.main_test_enter);
		
		account_name = (EditText)findViewById(R.id.login_name);
		account_pwd = (EditText)findViewById(R.id.login_pwd);
		
	//	btn1.setOnClickListener(new anime_OnClickListener());
	//	btn2.setOnClickListener(new regi_OnClickListener());
		btn_login.setOnClickListener(new login_OnClickListener());
		btn_regis.setOnClickListener(new regi_OnClickListener());
	//	btn_test_enter.setOnClickListener(new test_enter_OnClickListener());
		
		
		//播放音乐
		mp = MediaPlayer.create(this, R.raw.music2);
		mp.start();
	}
	
	class anime_OnClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
	        intent.setClass(MainActivity.this, Adminis_anime.class);
	        startActivity(intent);
		}
	}
	class test_enter_OnClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
	        intent.setClass(MainActivity.this, Category.class);
	        startActivity(intent);
	        mp.stop();
		}
	}
	class regi_OnClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			//
			Intent intent = new Intent();
	        intent.setClass(MainActivity.this, Register.class);
	        startActivity(intent);
			
		}
	}
	
	class login_OnClickListener implements OnClickListener{
		
		
		@Override
		public void onClick(View v) {
			if(account_name.getText().toString().equals("Administrator"))
			{
				Intent intent = new Intent();
		        intent.setClass(MainActivity.this, Administrator.class);
		        startActivity(intent);
			}
			else{
			QuesOperate han = new QuesOperate(MainActivity.this);
			SQLiteDatabase userlogin = han.find_account();
			String sql="select _id,uname,upassword from u_user where uname=? and upassword=?";
		    Cursor cursor=userlogin.rawQuery(sql, new String[]{
				 account_name.getText().toString(),account_pwd.getText().toString()
		 	 });
		    if(cursor.getCount() <=0)
			{
				new AlertDialog.Builder(MainActivity.this)
                .setTitle("错误")
                .setMessage("帐号或密码错误！")
                .setPositiveButton("确定", null)
                .show();
			}
			else
			{/*
				new AlertDialog.Builder(MainActivity.this)
                .setTitle("right")
                .setMessage("right！")
                .setPositiveButton("确定", null)
                .show();*/
			//	luser.setusername(String.valueOf(account_name.getText()));
				Intent intent = new Intent();
		        intent.setClass(MainActivity.this, Selection.class);
		        startActivity(intent);
				
			}
			}
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
