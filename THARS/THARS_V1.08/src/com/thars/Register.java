package com.thars;

import com.thars.R;
import com.thars.question.User;
import com.thars.question.QuesOperate;

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
/**
 * 实现SQLite数据库增加、删除、修改、查询操作
 * 
 */
 public class Register extends Activity implements OnClickListener{

 private EditText edname,edpass;
 private Button btn_regi,btn_return;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		/*
		 * 引入组件
		 */
		edname = (EditText)findViewById(R.id.regi_name);
		edpass = (EditText)findViewById(R.id.regi_pwd);
		btn_regi = (Button)findViewById(R.id.regi_regi);
		btn_return = (Button)findViewById(R.id.regi_return);
		
		btn_regi.setOnClickListener(this);
		btn_return.setOnClickListener(this);
		
	}
   public void empty(){
       edname.setText("");
       edpass.setText("");
   }
	@Override
	public void onClick(View v) {
		QuesOperate userDAO = new QuesOperate(this);
		switch (v.getId()) {
		//添加
		case R.id.regi_regi:
			if(!(edname.getText().toString().equals("")|| edpass.getText().toString().equals(""))){
			QuesOperate ani = new QuesOperate(Register.this);
			SQLiteDatabase userregis = ani.find_account();
			String sqll="select _id,uname,upassword from u_user where uname=?";
		    Cursor cursor=userregis.rawQuery(sqll, new String[]{
		    		edname.getText().toString()
		 	 });
		    if(cursor.getCount()<=0){
			try{
			User user = new User(edname.getText().toString(), edpass.getText().toString());
			userDAO.add(user);
			     new AlertDialog.Builder(Register.this)
                 .setMessage("注册成功！")
                 .setPositiveButton("确定", null)
                 .show();
             empty();
			}catch (Exception e) {
			}}
		    else
		    {
		    	 new AlertDialog.Builder(Register.this)
                 .setMessage("已存在用户名！")
                 .setPositiveButton("确定", null)
                 .show();
		    }}
			else{
				new AlertDialog.Builder(Register.this)
                .setMessage("名户名或密码不能为空!！")
                .setPositiveButton("确定", null)
                .show();
			}
			break;
			
		case R.id.regi_return:
			Intent intent = new Intent();
	        intent.setClass(Register.this, MainActivity.class);
	        startActivity(intent);
			break;

		default:
			break;
		}
		
	}
 
}

