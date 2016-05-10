package com.thars.chuangguan;

import com.thars.R;
import com.thars.Selection;
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
 public class CGintro extends Activity{
 
	private Button sel_btn1;
	private Button sel_btn2;
	private Button return_selection;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cgintro);

		sel_btn1 = (Button)findViewById(R.id.intro_btn_infor);
		sel_btn2 = (Button)findViewById(R.id.intro_btn_start);
		return_selection = (Button)findViewById(R.id.intro_btn_return);

		sel_btn1.setOnClickListener(new intro());
		sel_btn2.setOnClickListener(new start());
		return_selection.setOnClickListener(new back());
	//	btn_ques_anime.setOnClickListener(new user_OnClick());
	}
	class intro implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
	        intent.setClass(CGintro.this, Information.class);
	        startActivity(intent);
			
		}
	}
	class back implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
	        intent.setClass(CGintro.this, Selection.class);
	        startActivity(intent);
			
		}
	}
	class start implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
	        intent.setClass(CGintro.this, First.class);
	        startActivity(intent);
			
		}
	}
	
}
 
