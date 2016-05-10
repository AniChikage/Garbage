package com.thars.question;

import com.thars.question.User;
import com.thars.question.Question;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class QuesOperate {
	 private QuesSqliteHelper helper;
	 //写入 ，不然会是出错，是空指针
	 public QuesOperate(Context context){
		 helper=new QuesSqliteHelper(context);}
		 

	 /**
	  * 添加用户信息
	  */
	 
	 public void add(User user){
		 SQLiteDatabase db=helper.getWritableDatabase(); 
		 String sql="Insert into u_user(uname,upassword) values(?,?)";
		 db.execSQL(sql, new Object[]
		                            {
				 user.getUname(),user.getUpassword()
		                            }                           
		 );
		 db.close();
	 }
	 
	 public void add(Learn learn){
		 SQLiteDatabase db=helper.getWritableDatabase(); 
		 String sql="Insert into user_learn_table(_id,learn_user_name,l_anime,l_cold,l_computer,"+
		 "l_culture,l_literature,l_other,l_quzz,l_sports,l_video,l_world) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		 db.execSQL(sql, new Object[]
		                            {
				 learn.luid(),learn.user_name(),learn.anime(),learn.cold(),learn.computer(),
				 learn.culture(),learn.literature(),learn.other(),learn.quzz(),learn.sports(),
				 learn.video(),learn.world()
		                            }                           
		 );
		 db.close();
	 }
	 
	 public void add_point(Pointrank point){
		 SQLiteDatabase db=helper.getWritableDatabase(); 
		 String sql="Insert into user_point(point) values(?)";
		 db.execSQL(sql, new Object[]
		                            {
				             point.getPoint()
		                            }                           
		 );
		 db.close();
	 }
	 public void add_ques(Question ques){
		 SQLiteDatabase db=helper.getWritableDatabase(); 
		 String sql="Insert into q_ques(_id,question_show,a_ans,b_ans,c_ans,d_ans,right_ans) values(?,?,?,?,?,?,?)";
		 db.execSQL(sql, new Object[]
		                            {
				 ques.getQuid(),ques.getQuestion_show(),ques.getA_ans(),ques.getB_ans(),ques.getC_ans(),ques.getD_ans(),ques.getRight_ans()
		                            }                           
		 );
		 db.close();
	 }
	 public void add_ques_cold(Question ques){
		 SQLiteDatabase db=helper.getWritableDatabase(); 
		 String sql="Insert into q_ques_cold(_id,question_show,a_ans,b_ans,c_ans,d_ans,right_ans) values(?,?,?,?,?,?,?)";
		 db.execSQL(sql, new Object[]
		                            {
				 ques.getQuid(),ques.getQuestion_show(),ques.getA_ans(),ques.getB_ans(),ques.getC_ans(),ques.getD_ans(),ques.getRight_ans()
		                            }                           
		 );
		 db.close();
	 }
	 public void add_ques_computer(Question ques){
		 SQLiteDatabase db=helper.getWritableDatabase(); 
		 String sql="Insert into q_ques_computer(_id,question_show,a_ans,b_ans,c_ans,d_ans,right_ans) values(?,?,?,?,?,?,?)";
		 db.execSQL(sql, new Object[]
		                            {
				 ques.getQuid(),ques.getQuestion_show(),ques.getA_ans(),ques.getB_ans(),ques.getC_ans(),ques.getD_ans(),ques.getRight_ans()
		                            }                           
		 );
		 db.close();
	 }
	 public void add_ques_culture(Question ques){
		 SQLiteDatabase db=helper.getWritableDatabase(); 
		 String sql="Insert into q_ques_culture(_id,question_show,a_ans,b_ans,c_ans,d_ans,right_ans) values(?,?,?,?,?,?,?)";
		 db.execSQL(sql, new Object[]
		                            {
				 ques.getQuid(),ques.getQuestion_show(),ques.getA_ans(),ques.getB_ans(),ques.getC_ans(),ques.getD_ans(),ques.getRight_ans()
		                            }                           
		 );
		 db.close();
	 }
	 public void add_ques_literature(Question ques){
		 SQLiteDatabase db=helper.getWritableDatabase(); 
		 String sql="Insert into q_ques_literature(_id,question_show,a_ans,b_ans,c_ans,d_ans,right_ans) values(?,?,?,?,?,?,?)";
		 db.execSQL(sql, new Object[]
		                            {
				 ques.getQuid(),ques.getQuestion_show(),ques.getA_ans(),ques.getB_ans(),ques.getC_ans(),ques.getD_ans(),ques.getRight_ans()
		                            }                           
		 );
		 db.close();
	 }
	 public void add_ques_other(Question ques){
		 SQLiteDatabase db=helper.getWritableDatabase(); 
		 String sql="Insert into q_ques_other(_id,question_show,a_ans,b_ans,c_ans,d_ans,right_ans) values(?,?,?,?,?,?,?)";
		 db.execSQL(sql, new Object[]
		                            {
				 ques.getQuid(),ques.getQuestion_show(),ques.getA_ans(),ques.getB_ans(),ques.getC_ans(),ques.getD_ans(),ques.getRight_ans()
		                            }                           
		 );
		 db.close();
	 }
	 public void add_ques_quz(Question ques){
		 SQLiteDatabase db=helper.getWritableDatabase(); 
		 String sql="Insert into q_ques_quz(_id,question_show,a_ans,b_ans,c_ans,d_ans,right_ans) values(?,?,?,?,?,?,?)";
		 db.execSQL(sql, new Object[]
		                            {
				 ques.getQuid(),ques.getQuestion_show(),ques.getA_ans(),ques.getB_ans(),ques.getC_ans(),ques.getD_ans(),ques.getRight_ans()
		                            }                           
		 );
		 db.close();
	 }
	 public void add_ques_quzz(Question ques){
		 SQLiteDatabase db=helper.getWritableDatabase(); 
		 String sql="Insert into q_ques_quzz(_id,question_show,a_ans,b_ans,c_ans,d_ans,right_ans) values(?,?,?,?,?,?,?)";
		 db.execSQL(sql, new Object[]
		                            {
				 ques.getQuid(),ques.getQuestion_show(),ques.getA_ans(),ques.getB_ans(),ques.getC_ans(),ques.getD_ans(),ques.getRight_ans()
		                            }                           
		 );
		 db.close();
	 }
	 public void add_ques_sports(Question ques){
		 SQLiteDatabase db=helper.getWritableDatabase(); 
		 String sql="Insert into q_ques_sports(_id,question_show,a_ans,b_ans,c_ans,d_ans,right_ans) values(?,?,?,?,?,?,?)";
		 db.execSQL(sql, new Object[]
		                            {
				 ques.getQuid(),ques.getQuestion_show(),ques.getA_ans(),ques.getB_ans(),ques.getC_ans(),ques.getD_ans(),ques.getRight_ans()
		                            }                           
		 );
		 db.close();
	 }
	 public void add_ques_video(Question ques){
		 SQLiteDatabase db=helper.getWritableDatabase(); 
		 String sql="Insert into q_ques_video(_id,question_show,a_ans,b_ans,c_ans,d_ans,right_ans) values(?,?,?,?,?,?,?)";
		 db.execSQL(sql, new Object[]
		                            {
				 ques.getQuid(),ques.getQuestion_show(),ques.getA_ans(),ques.getB_ans(),ques.getC_ans(),ques.getD_ans(),ques.getRight_ans()
		                            }                           
		 );
		 db.close();
	 }
	 public void add_ques_world(Question ques){
		 SQLiteDatabase db=helper.getWritableDatabase(); 
		 String sql="Insert into q_ques_world(_id,question_show,a_ans,b_ans,c_ans,d_ans,right_ans) values(?,?,?,?,?,?,?)";
		 db.execSQL(sql, new Object[]
		                            {
				 ques.getQuid(),ques.getQuestion_show(),ques.getA_ans(),ques.getB_ans(),ques.getC_ans(),ques.getD_ans(),ques.getRight_ans()
		                            }                           
		 );
		 db.close();
	 }
	 /**
	  * 删除用户信息
	  */
	  public void delete(String...user_name){
			 SQLiteDatabase database=helper.getWritableDatabase();
		//	 String sql="delete from u_user where uname=?";
			 database.delete("u_user","uname=?",user_name);
	  }
	  public void delete_ques(Integer...uid){
		  if(uid.length>0){
			 StringBuffer sb=new StringBuffer();
			 for(int i=0;i<uid.length;i++){
				 sb.append("?").append(",");
			 }
			 sb.deleteCharAt(sb.length()-1);
			 SQLiteDatabase database=helper.getWritableDatabase();
			 String sql="delete from q_ques where _id in ("+sb+")";
			 database.execSQL(sql, (Object[])uid);
		  }
	  }


	  /**
	   * 用户修改
	   */
	  public void update(User user){
		  SQLiteDatabase db=helper.getWritableDatabase();//写入数据库中注意！！！！不能放在外面
	 	 String sql="update u_user set upassword=? where uname=?";
	 	 db.execSQL(sql, new Object[]{
	 			user.getUpassword(),user.getUname()
	 	 });
	  }
	  
	  public void update_learn(String user_name, String cate, int point){
		  SQLiteDatabase db=helper.getWritableDatabase();//写入数据库中注意！！！！不能放在外面
	 	 String sql="update user_learn_table set " + cate + " =? where user_name=?";
	 	 db.execSQL(sql, new Object[]{
	 			point,user_name
	 	 });
	  }
	  

	  public void update_ques(Question ques){
		  SQLiteDatabase db=helper.getWritableDatabase();//写入数据库中注意！！！！不能放在外面
	 	 String sql="update q_ques set question_show=?,a_ans=?,b_ans=?,c_ans=?,d_ans=?,right_ans=? where _id=?";
	 	 db.execSQL(sql, new Object[]{
	 			 ques.getQuestion_show(),ques.getA_ans(),ques.getB_ans(),ques.getC_ans(),ques.getD_ans(),ques.getRight_ans(),ques.getQuid()
	 	 });
	  }
	  /**
	   * 查找用户信息
	   */
	  public User find(String user_name){
		  SQLiteDatabase db=helper.getWritableDatabase();//写入数据库中注意！！！！不能放在外面
	 	 String sql="select uname,upassword from u_user where uname=?";
	 	 Cursor cursor=db.rawQuery(sql, new String[]{
	 			user_name
	 	 });
	 	 if(cursor.moveToNext()){
	 		 return new User(
	 				 cursor.getString(cursor.getColumnIndex("uname")), 
	 		         cursor.getString(cursor.getColumnIndex("upassword"))
	 				);
	 	 }
	 		 return null;
	 }
	  public SQLiteDatabase find_account(){
		  SQLiteDatabase db=helper.getWritableDatabase();//写入数据库中注意！！！！不能放在外面
	   
	 	 return db;
	// 	 if(cursor.getCount()<=0){
	// 		return false;
	// 	 }
	 //	 else
	 //		 return true;
	 }
	  public Question find_ques(int uid){
		  SQLiteDatabase db=helper.getWritableDatabase();//写入数据库中注意！！！！不能放在外面
	 	 String sql="select _id,question_show,a_ans,b_ans,c_ans,d_ans,right_ans from q_ques where _id=?";
	 	 Cursor cursor=db.rawQuery(sql, new String[]{
	 			 String.valueOf(uid)
	 	 });
	 	 if(cursor.moveToNext()){
	 		 return new Question(
	 				 cursor.getInt(cursor.getColumnIndex("_id")),
	 				 cursor.getString(cursor.getColumnIndex("question_show")), 
	 		         cursor.getString(cursor.getColumnIndex("a_ans")),
	 		         cursor.getString(cursor.getColumnIndex("b_ans")),
	 		         cursor.getString(cursor.getColumnIndex("c_ans")),
	 		         cursor.getString(cursor.getColumnIndex("d_ans")),
	 		         cursor.getString(cursor.getColumnIndex("right_ans"))
	 				);
	 	 }
	 		 return null;
	 }
	  
	/**
	 * 显示用户
	 */
		public Cursor select() {
			SQLiteDatabase db = helper.getReadableDatabase();
			Cursor cursor = db.query("u_user",
					null, null, null, null,
					null, "_id desc");
			return cursor;
		}
		
		public Cursor select_ques() {
			SQLiteDatabase db = helper.getReadableDatabase();
			Cursor cursor = db.query("q_ques",
					null, null, null, null,
					null, "_id desc");
			return cursor;
		}

		public Cursor select_learn_user() {
			SQLiteDatabase db = helper.getReadableDatabase();
			Cursor cursor = db.query("user_learn_table",
					null, null, null, null,
					null, "_id desc");
			return cursor;
		}
}
