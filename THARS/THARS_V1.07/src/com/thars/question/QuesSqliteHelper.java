package com.thars.question;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class QuesSqliteHelper extends SQLiteOpenHelper {

	public static final String DB_NAME = "test006.db";
	public static final String TB_NAME1 = "u_user";
	public static final String TB_NAME2 = "q_ques";
	public static final String TB_NAME3 = "q_ques_quzz";
	public static final String TB_NAME4 = "q_ques_computer";
	public static final String TB_NAME5 = "q_ques_literature";
	public static final String TB_NAME6 = "q_ques_video";
	public static final String TB_NAME7 = "q_ques_culture";
	public static final String TB_NAME8 = "q_ques_world";
	public static final String TB_NAME9 = "q_ques_sports";
	public static final String TB_NAME10 = "q_ques_cold";
	public static final String TB_NAME11 = "q_ques_other";
	public static final String TB_NAME12 = "user_point";
	public static final String TB_NAME13 = "user_learn_table";
	public static final String TB_NAME14 = "q_ques_quz";

	// 构造方法
	public QuesSqliteHelper(Context context) {

		super(context, DB_NAME, null, 2);
	}
	/**
	 * 创建新数据库表
	 */
	@Override
	/**
	 * 注意：似乎一定要用_id,而不能用如_usid
	 */
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table if not exists "+TB_NAME1+"(_id integer primary key,uname varchar(20),upassword varchar(20))");
		db.execSQL("create table if not exists "+TB_NAME2+"(_id integer primary key,question_show varchar(40),a_ans varchar(20),b_ans varchar(20),c_ans varchar(20),d_ans varchar(20),right_ans varchar(20))");
		db.execSQL("create table if not exists "+TB_NAME3+"(_id integer primary key,question_show varchar(40),a_ans varchar(20),b_ans varchar(20),c_ans varchar(20),d_ans varchar(20),right_ans varchar(20))");
		db.execSQL("create table if not exists "+TB_NAME4+"(_id integer primary key,question_show varchar(40),a_ans varchar(20),b_ans varchar(20),c_ans varchar(20),d_ans varchar(20),right_ans varchar(20))");
		db.execSQL("create table if not exists "+TB_NAME5+"(_id integer primary key,question_show varchar(40),a_ans varchar(20),b_ans varchar(20),c_ans varchar(20),d_ans varchar(20),right_ans varchar(20))");
		db.execSQL("create table if not exists "+TB_NAME6+"(_id integer primary key,question_show varchar(40),a_ans varchar(20),b_ans varchar(20),c_ans varchar(20),d_ans varchar(20),right_ans varchar(20))");
		db.execSQL("create table if not exists "+TB_NAME7+"(_id integer primary key,question_show varchar(40),a_ans varchar(20),b_ans varchar(20),c_ans varchar(20),d_ans varchar(20),right_ans varchar(20))");
		db.execSQL("create table if not exists "+TB_NAME8+"(_id integer primary key,question_show varchar(40),a_ans varchar(20),b_ans varchar(20),c_ans varchar(20),d_ans varchar(20),right_ans varchar(20))");
		db.execSQL("create table if not exists "+TB_NAME9+"(_id integer primary key,question_show varchar(40),a_ans varchar(20),b_ans varchar(20),c_ans varchar(20),d_ans varchar(20),right_ans varchar(20))");
		db.execSQL("create table if not exists "+TB_NAME10+"(_id integer primary key,question_show varchar(40),a_ans varchar(20),b_ans varchar(20),c_ans varchar(20),d_ans varchar(20),right_ans varchar(20))");
		db.execSQL("create table if not exists "+TB_NAME11+"(_id integer primary key,question_show varchar(40),a_ans varchar(20),b_ans varchar(20),c_ans varchar(20),d_ans varchar(20),right_ans varchar(20))");
		db.execSQL("create table if not exists "+TB_NAME12+"(_id integer primary key,point varchar(20))");
		db.execSQL("create table if not exists "+TB_NAME13+"(_id integer primary key,learn_user_name varchar(20)),l_anime varchar(10),l_cold varchar(10),l_computer varchar(10),l_culture varchar(10),l_literature varchar(10),l_other varchar(10),l_quzz varchar(10),l_sports varchar(10),l_video varchar(10),l_world varchar(10))");
		db.execSQL("create table if not exists temp(_id integer primary key,user_name varchar(20))");
		db.execSQL("create table if not exists "+TB_NAME14+"(_id integer primary key,question_show varchar(40),a_ans varchar(20),b_ans varchar(20),c_ans varchar(20),d_ans varchar(20),right_ans varchar(20))");
		
	}

	/**
	 * 当检测与前一次创建数据库版本不一样时，先删除表再创建新表
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS u_user");  
        db.execSQL("DROP TABLE IF EXISTS q_ques"); 
        db.execSQL("DROP TABLE IF EXISTS q_ques_quzz");
        db.execSQL("DROP TABLE IF EXISTS q_ques_quz");
        db.execSQL("DROP TABLE IF EXISTS q_ques_computer");
        db.execSQL("DROP TABLE IF EXISTS q_ques_literature");
        db.execSQL("DROP TABLE IF EXISTS q_ques_video");
        db.execSQL("DROP TABLE IF EXISTS q_ques_culture");
        db.execSQL("DROP TABLE IF EXISTS q_ques_world");
        db.execSQL("DROP TABLE IF EXISTS q_ques_sports");
        db.execSQL("DROP TABLE IF EXISTS q_ques_cold");
        db.execSQL("DROP TABLE IF EXISTS q_ques_other");
        db.execSQL("DROP TABLE IF EXISTS user_point");
        db.execSQL("DROP TABLE IF EXISTS user_learn_table");
        onCreate(db);
	}
	public void updateColumn(SQLiteDatabase db, String oldColumn,
			String newColumn, String typeColumn) {
		try {
			db.execSQL("ALTER TABLE " + TB_NAME1 + " CHANGE " + oldColumn + " "
					+ newColumn + " " + typeColumn);
			db.execSQL("ALTER TABLE " + TB_NAME2 + " CHANGE " + oldColumn + " "
					+ newColumn + " " + typeColumn);
			db.execSQL("ALTER TABLE " + TB_NAME3 + " CHANGE " + oldColumn + " "
					+ newColumn + " " + typeColumn);
			db.execSQL("ALTER TABLE " + TB_NAME4 + " CHANGE " + oldColumn + " "
					+ newColumn + " " + typeColumn);
			db.execSQL("ALTER TABLE " + TB_NAME5 + " CHANGE " + oldColumn + " "
					+ newColumn + " " + typeColumn);
			db.execSQL("ALTER TABLE " + TB_NAME6 + " CHANGE " + oldColumn + " "
					+ newColumn + " " + typeColumn);
			db.execSQL("ALTER TABLE " + TB_NAME7 + " CHANGE " + oldColumn + " "
					+ newColumn + " " + typeColumn);
			db.execSQL("ALTER TABLE " + TB_NAME8 + " CHANGE " + oldColumn + " "
					+ newColumn + " " + typeColumn);
			db.execSQL("ALTER TABLE " + TB_NAME9 + " CHANGE " + oldColumn + " "
					+ newColumn + " " + typeColumn);
			db.execSQL("ALTER TABLE " + TB_NAME10 + " CHANGE " + oldColumn + " "
					+ newColumn + " " + typeColumn);
			db.execSQL("ALTER TABLE " + TB_NAME11 + " CHANGE " + oldColumn + " "
					+ newColumn + " " + typeColumn);
			db.execSQL("ALTER TABLE " + TB_NAME12 + " CHANGE " + oldColumn + " "
					+ newColumn + " " + typeColumn);
			db.execSQL("ALTER TABLE " + TB_NAME13 + " CHANGE " + oldColumn + " "
					+ newColumn + " " + typeColumn);
			db.execSQL("ALTER TABLE " + TB_NAME14 + " CHANGE " + oldColumn + " "
					+ newColumn + " " + typeColumn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
