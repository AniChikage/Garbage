package com.thars;

import com.thars.R;
import com.thars.Category.category_video_OnClickListener;
import com.thars.question.Music;
import com.thars.question.User;
import com.thars.question.QuesOperate;
import com.thars.question.User_Data;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
/**
 * 实现SQLite数据库增加、删除、修改、查询操作
 * 
 */
 public class Setting extends Activity implements OnClickListener{
	public RadioButton rbtn1;
	public RadioButton rbtn2;
	public RadioButton rbtn3;
	public RadioButton mscon;
	public RadioButton mscoff;
	public Button btn_ok;
	public Button btn_back;
	public int time;
	public User_Data total_time;
	public Music msc;
	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);	
		
		rbtn1 = (RadioButton)findViewById(R.id.easy);
		rbtn2 = (RadioButton)findViewById(R.id.middle);
		rbtn3 = (RadioButton)findViewById(R.id.diff);
		mscon = (RadioButton)findViewById(R.id.MSCON);
		mscoff = (RadioButton)findViewById(R.id.MSCOFF);
		btn_ok = (Button)findViewById(R.id.setting_ok);
		btn_back = (Button)findViewById(R.id.setting_back);
		
		btn_ok.setOnClickListener(new setting_ok_OnClickListener());
		btn_back.setOnClickListener(new setting_back_OnClickListener());
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		
	}
	class setting_ok_OnClickListener implements OnClickListener
	{
		public void onClick(View v) {
			// TODO Auto-generated method stub
		if(rbtn1.isChecked())
			{
			total_time.setA(10);
			Toast.makeText(Setting.this, "您选择了10s钟~", Toast.LENGTH_LONG).show();
			}
		if(rbtn2.isChecked())
		{
			total_time.setA(8);
			Toast.makeText(Setting.this, "您选择了8s钟~", Toast.LENGTH_LONG).show();
			}
		if(rbtn3.isChecked())
		{
			total_time.setA(6);
			Toast.makeText(Setting.this, "您选择了6s钟~", Toast.LENGTH_LONG).show();
			}
		if(mscon.isChecked())
		{
			msc.setMscStatus(1);
			}
	   if(mscoff.isChecked())
	    {
		    msc.setMscStatus(0);
   		    }
     }
   }
	class setting_back_OnClickListener implements OnClickListener
	{
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
	        intent.setClass(Setting.this, Category.class);
	        startActivity(intent);
		}
	}
 
}

