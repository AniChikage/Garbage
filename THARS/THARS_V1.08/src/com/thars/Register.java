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
 * ʵ��SQLite���ݿ����ӡ�ɾ�����޸ġ���ѯ����
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
		 * �������
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
		//���
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
                 .setMessage("ע��ɹ���")
                 .setPositiveButton("ȷ��", null)
                 .show();
             empty();
			}catch (Exception e) {
			}}
		    else
		    {
		    	 new AlertDialog.Builder(Register.this)
                 .setMessage("�Ѵ����û�����")
                 .setPositiveButton("ȷ��", null)
                 .show();
		    }}
			else{
				new AlertDialog.Builder(Register.this)
                .setMessage("�����������벻��Ϊ��!��")
                .setPositiveButton("ȷ��", null)
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

