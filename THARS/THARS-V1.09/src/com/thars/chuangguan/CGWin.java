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

 public class CGWin extends Activity{
	private Button win_btn1;
	private Button win_btn2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cgwin);

		win_btn1 = (Button)findViewById(R.id.cg_win_return_cg);
		win_btn2 = (Button)findViewById(R.id.cg_win_return_cate);

		win_btn1.setOnClickListener(new intro());
		win_btn2.setOnClickListener(new start());
	}
	class intro implements OnClickListener{
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
	        intent.setClass(CGWin.this, CGintro.class);
	        startActivity(intent);
			
		}
	}
	class start implements OnClickListener{
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
	        intent.setClass(CGWin.this, Selection.class);
	        startActivity(intent);
			
		}
	}
	
}
 
