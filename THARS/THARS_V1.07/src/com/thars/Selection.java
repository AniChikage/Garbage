package com.thars;

import com.thars.R;
import com.thars.chuangguan.CGintro;
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
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 实现SQLite数据库增加、删除、修改、查询操作
 * 
 */
 public class Selection extends Activity{
 
	private Button sel_btn1;
	private Button sel_btn2;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select);

		sel_btn1 = (Button)findViewById(R.id.sel_btn1);
		sel_btn2 = (Button)findViewById(R.id.sel_btn2);
		
		sel_btn1.setOnClickListener(new dati());
		sel_btn2.setOnClickListener(new chuangguan());
		
	//	btn_ques_anime.setOnClickListener(new user_OnClick());
	}
	class dati implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
	        intent.setClass(Selection.this, Category.class);
	        startActivity(intent);
			
		}
	}
	class chuangguan implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
	        intent.setClass(Selection.this, CGintro.class);
	        startActivity(intent);
			
		}
	}
	
}
 
