package com.thars.chuangguan;


import com.thars.R;
import com.thars.question.LearnCate;
import com.thars.question.LearnUser;
import com.thars.question.Pointrank;
import com.thars.question.QuesOperate;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class CGFailure2 extends Activity {

    private TextView anime_result,anime_result1,anime_result2,anime_result3;
    private Button cgbtn_next,cgbtn_return;
    public int result;
    public int temp1,temp2,temp3;
    public int ratio = 0;
    public long total = 0;
    public double right_rate = 0;
    public String ratio_str;
    public LearnCate learncate;
    public LearnUser luser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cgfailure);
        
		cgbtn_next = (Button)findViewById(R.id.cgfretry);
		cgbtn_return = (Button)findViewById(R.id.cgfreturn);

		Intent intent=getIntent();  
        String bundle=intent.getExtras().getString("result");
        result = Integer.valueOf(bundle).intValue()*10;
/*
    	anime_result.setText("您的正确率为"+String.valueOf(result)+"%");
    	
    	Update_point();
    	ratio_str = Rank(result);
    	total = Total();
    	
    	right_rate = Double.valueOf(ratio_str).doubleValue()/total;
    	temp1 = (int)(right_rate*10000);
    	temp2 = temp1/100;
    	temp3 = temp1%100;
    	
    	String search = Learn_user_search("anichikage");
    //	anime_result1.setText(ratio_str);
    //	anime_result2.setText(String.valueOf(total));
    //	anime_result2.setText(learncate.getCate());
    //	anime_result2.setText(search);
    //	anime_result2.setText(luser.getusername()+":经过您的NL奋战，推断您最擅长动漫！~");
    	anime_result3.setText("您击败了目前为止"+String.valueOf(temp2)+"."+String.valueOf(temp3)+"%"+"的用户！");
    //	anime_result3.setText(String.valueOf(temp2));
    	*/
     //   cgbtn_next.setOnClickListener(new anime_return_category_Click());
        cgbtn_next.setOnClickListener(new cgfretry());
        cgbtn_return.setOnClickListener(new cgfreturn());

	}
	class cgfreturn implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
	        intent.setClass(CGFailure2.this, CGintro.class);
	        startActivity(intent);
			
		}
	}
	class cgfretry implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
	        intent.setClass(CGFailure2.this, CGSecond.class);
	        startActivity(intent);
			
		}
	}
	
	class anime_clear_Click implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			QuesOperate computer_db_qo = new QuesOperate(CGFailure2.this);
			SQLiteDatabase computer_db = computer_db_qo.find_account();
			computer_db = openOrCreateDatabase("test006.db", Context.MODE_PRIVATE, null);  
			computer_db.execSQL("DROP TABLE IF EXISTS user_point");  
			new AlertDialog.Builder(CGFailure2.this)
            .setTitle("OK")
            .setMessage("clear complete")
            .setPositiveButton("确定", null)
            .show();
		}
	}
	
	public long Total(){
		String sql = "SELECT COUNT(*) FROM user_point";
		QuesOperate point_qo = new QuesOperate(this);
		SQLiteDatabase point_db = point_qo.find_account();
		point_db = openOrCreateDatabase("test006.db", Context.MODE_PRIVATE, null);  
		SQLiteStatement  statement = point_db.compileStatement(sql);
	    long count = statement.simpleQueryForLong();
	    return count;
	}
	
	public void Update_point(){
		QuesOperate point_qo = new QuesOperate(this);
		SQLiteDatabase point_db = point_qo.find_account();
		point_db = openOrCreateDatabase("test006.db", Context.MODE_PRIVATE, null);  
		point_db.execSQL("create table if not exists user_point(point varchar(50))");
		Pointrank point = new Pointrank(String.valueOf(result));
		point_qo.add_point(point);
	}
	
	public String Rank(int result){
		QuesOperate point_qo = new QuesOperate(this);
		SQLiteDatabase point_db = point_qo.find_account();
		point_db = openOrCreateDatabase("test006.db", Context.MODE_PRIVATE, null);  
		String sql="select point from user_point where point<=?";
		Cursor cursor = point_db.rawQuery(sql,new String[]{
	 			String.valueOf(result)
	 	 });
		cursor.moveToFirst();
		while(cursor.moveToNext()){
			ratio = ratio+1;
		}
		return String.valueOf(ratio);
	}
	
	public String Learn_user_search(String user_name){
		QuesOperate user_qo = new QuesOperate(this);
		SQLiteDatabase user_db = user_qo.find_account();
		user_db = openOrCreateDatabase("test006.db", Context.MODE_PRIVATE, null);  
	/*	user_db.execSQL("create table if not exists user_learn_table(_id integer primary key,"+
		"learn_user_name varchar(20)),l_anime varchar(10),l_cold varchar(10),l_computer varchar(10),"+
		"l_culture varchar(10),l_literature varchar(10),l_other varchar(10),l_quzz varchar(10),"+
		"l_sports varchar(10),l_video varchar(10),l_world varchar(10))");*/
		user_db.execSQL("create table if not exists temp(_id integer primary key,user_name varchar(20))");
		String sql="select * from temp where user_name=?";
		Cursor cursor = user_db.rawQuery(sql,new String[]{
				user_name.toString()
				
	 	 });
		if(cursor.getCount()<=0)
		return String.valueOf(0);
		else
			return String.valueOf(1);
	}
	

}
