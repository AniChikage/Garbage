package com.thars.chuangguan;

import java.util.Timer;
import java.util.TimerTask;

import com.thars.R;
import com.thars.question.Music;
import com.thars.question.QuesOperate;
import com.thars.question.Question;
import com.thars.question.User_Data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
/**
 * 实现SQLite数据库增加、删除、修改、查询操作
 * 
 */
//public int show_time;

public class CGFirst extends Activity implements OnClickListener{
	
        public int loop,loop_end=10,max_num=12;//loop_end：选几个题，max_num：题库中的总数
        public int delay_time = 500;
        public int point = 0;
        public Integer[] temp = new Integer[loop_end];
        private Button btn_A,btn_B,btn_C,btn_D;
        private TextView Ques_Text,Remain_ques,point_show;
        public String Str_Ques;
        public String Str_A;
        public String Str_B;
        public String Str_C;
        public String Str_D;
        public String Str_RightAnswer;
        /*
		 设置始终参数
		 */
        private static String  TAG = "TimerDemo";
		private TextView Remain_time = null;
		private Button mButton_start = null;
		private Button mButton_pause = null;
		private Timer mTimer = null;
		private TimerTask mTimerTask = null;
		private Handler mHandler = null;	
		private Handler timeHandler = null;
		private static int count = 0;
		private boolean isPause = false;
		private boolean isStop = true;
		private static int delay = 1000;  //1s
		private static int period = 1000;  //1s
		private static final int UPDATE_TEXTVIEW = 0;
		private int count1 = 0;
		private int Total_Time = 5;
		private MediaPlayer mp;                         //MediaPlayer引用  
		private User_Data data;
		private Music msc;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_category);
		
		/*
		 * 引入组件
		 */
		Ques_Text = (TextView)findViewById(R.id.category_ques);
		btn_A = (Button)findViewById(R.id.category_ans_a);
		btn_B = (Button)findViewById(R.id.category_ans_b);
		btn_C = (Button)findViewById(R.id.category_ans_c);
		btn_D = (Button)findViewById(R.id.category_ans_d);
		Remain_ques = (TextView)findViewById(R.id.category_remain);
		point_show = (TextView)findViewById(R.id.category_point);
	//	Remain_time = (TextView)findViewById(R.id.remain_time);
		
		/*
		初始化数据库
	    */
		Cold_Ques_Init();
		/*
		初始化界面 
	    */
		loop = 0;
        temp = Random_id(loop_end);
        set_show();

	}
    
	public void set_show(){

		QuesOperate user_name_qo = new QuesOperate(CGFirst.this);
		SQLiteDatabase user_anime_login = user_name_qo.find_account();
		String sql="select _id,question_show,a_ans,b_ans,c_ans,d_ans,right_ans from q_ques where _id=?";
	    Cursor user_anime_cur=user_anime_login.rawQuery(sql, new String[]{
			 String.valueOf(temp[loop])
	 	 });
	    user_anime_cur.moveToFirst();
	    
	    //答案随机产生
	    Integer[] random_answer = new Integer[4];
	    random_answer = Random_ans(4);
	    
        Str_Ques = user_anime_cur.getString(1);
        
        Str_A = user_anime_cur.getString(random_answer[0]);
        Str_B = user_anime_cur.getString(random_answer[1]);
        Str_C = user_anime_cur.getString(random_answer[2]);
        Str_D = user_anime_cur.getString(random_answer[3]);
        Str_RightAnswer = user_anime_cur.getString(6);
        
        Ques_Text.setText(Str_Ques);
		btn_A.setText(Str_A);
		btn_B.setText(Str_B);
		btn_C.setText(Str_C);
		btn_D.setText(Str_D);
		Remain_ques.setText("剩余："+String.valueOf(10-loop));
		point_show.setText("gold："+String.valueOf(point*10));
		btn_OnClick();
		
	}

	public void btn_OnClick()
	{
		btn_A.setOnClickListener(this);
		  btn_B.setOnClickListener(this);
		  btn_C.setOnClickListener(this);
		  btn_D.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		//添加
		case R.id.category_ans_a:
			loop = loop+1;
			if(loop<loop_end){
				if(Str_A.equals(Str_RightAnswer))
				{Toast.makeText(CGFirst.this, "duang~,答对了~", Toast.LENGTH_LONG).show();
				point = point+1;}
				else
				Toast.makeText(CGFirst.this, "好遗憾->_->", Toast.LENGTH_LONG).show();
	//			anime_delay(delay_time);
				set_show();

			}
			else{
				if(Str_A.equals(Str_RightAnswer)){
					Toast.makeText(CGFirst.this, "duang~,答对了~", Toast.LENGTH_LONG).show();
					point = point+1;}
					else
					Toast.makeText(CGFirst.this, "好遗憾->_->", Toast.LENGTH_LONG).show();
				Intent intent = new Intent();
				if(point > 5)
		        intent.setClass(CGFirst.this, CGSuccess1.class);
				else
					intent.setClass(CGFirst.this, CGFailure1.class);
		        Bundle bundle = new Bundle();
		        bundle.putString("result", String.valueOf(point));
		        intent.putExtras(bundle); 
		        startActivity(intent);
			}
			break;
		//删除
        case R.id.category_ans_b:
        	loop = loop+1;
			if(loop<loop_end){
				if(Str_B.equals(Str_RightAnswer))
				{
					Toast.makeText(CGFirst.this, "duang~,答对了~", Toast.LENGTH_LONG).show();
					point = point+1;}
					else
					Toast.makeText(CGFirst.this, "好遗憾->_->", Toast.LENGTH_LONG).show();
		//		anime_delay(delay_time);
				set_show();
			}
			else{
				if(Str_B.equals(Str_RightAnswer))
				{
					Toast.makeText(CGFirst.this, "duang~,答对了~", Toast.LENGTH_LONG).show();
					point = point+1;}
					else
					Toast.makeText(CGFirst.this, "好遗憾->_->", Toast.LENGTH_LONG).show();
				Intent intent = new Intent();
				if(point > 5)
			        intent.setClass(CGFirst.this, CGSuccess1.class);
					else
						intent.setClass(CGFirst.this, CGFailure1.class);
		        Bundle bundle = new Bundle();
		        bundle.putString("result", String.valueOf(point));
		        intent.putExtras(bundle);
		       startActivity(intent);
		        // this.startActivityForResult(intent, 0);
			}
        	break;
        //修改
        	case R.id.category_ans_c:
        		loop = loop+1;
    			if(loop<loop_end){
    				if(Str_C.equals(Str_RightAnswer))
    				{
    					Toast.makeText(CGFirst.this, "duang~,答对了~", Toast.LENGTH_LONG).show();
    					point = point+1;}
    					else
    					Toast.makeText(CGFirst.this, "好遗憾->_->", Toast.LENGTH_LONG).show();
    	//			anime_delay(delay_time);
    				set_show();
    			}
    			else{
    				if(Str_C.equals(Str_RightAnswer))
    				{
    					Toast.makeText(CGFirst.this, "duang~,答对了~", Toast.LENGTH_LONG).show();
    					point = point+1;}
    					else
    					Toast.makeText(CGFirst.this, "好遗憾->_->", Toast.LENGTH_LONG).show();
    				Intent intent = new Intent();
    				if(point > 5)
    			        intent.setClass(CGFirst.this, CGSuccess1.class);
    					else
    						intent.setClass(CGFirst.this, CGFailure1.class);
    		        Bundle bundle = new Bundle();
    		        bundle.putString("result", String.valueOf(point));
    		        intent.putExtras(bundle);
    		        startActivity(intent);
    		      //  this.startActivityForResult(intent, 0);
    			}
        		break;
        //查询
        	case R.id.category_ans_d:
        		loop = loop+1;
    			if(loop<loop_end){
    				if(Str_D.equals(Str_RightAnswer))
    				{
    					Toast.makeText(CGFirst.this, "duang~,答对了~", Toast.LENGTH_LONG).show();
    					point = point+1;}
    					else
    					Toast.makeText(CGFirst.this, "好遗憾->_->", Toast.LENGTH_LONG).show();
    	//			anime_delay(delay_time);
    				set_show();
    			}
    			else{
    				if(Str_D.equals(Str_RightAnswer))
    				{
    					Toast.makeText(CGFirst.this, "duang~,答对了~", Toast.LENGTH_LONG).show();
    					point = point+1;}
    					else
    					Toast.makeText(CGFirst.this, "好遗憾->_->", Toast.LENGTH_LONG).show();
    		
    				Intent intent = new Intent();
    				if(point > 5)
    			        intent.setClass(CGFirst.this, CGSuccess1.class);
    					else
    						intent.setClass(CGFirst.this, CGFailure1.class);
    		        Bundle bundle = new Bundle();
    		        bundle.putString("result", String.valueOf(point));
    		        intent.putExtras(bundle);
    		        startActivity(intent);
    		      //  this.startActivityForResult(intent, 0);
    			}
        		break;
		default:
			break;
		}
		
	}
	public Integer[] Random_id(int num)
	{
		Integer[] enum_num = new Integer[num];
		enum_num[0] = (int)(Math.random()*max_num)+1;
		for(int i = 1; i < num; i++ ){
			enum_num[i] = (int)(Math.random()*max_num)+1;
			for(int j = 0; j < i; j++){
				if(enum_num[i] ==enum_num[j] )
					i = i-1;
			}
		}
		
		return enum_num;
	}
	public Integer[] Random_ans(int num)
	{
		Integer[] enum_num = new Integer[num];
		enum_num[0] = (int)(Math.random()*4)+2;
		for(int i = 1; i < num; i++ ){
			enum_num[i] = (int)(Math.random()*4)+2;
			for(int j = 0; j < i; j++){
				if(enum_num[i] ==enum_num[j] )
					i = i-1;
			}
		}
		
		return enum_num;
	}                                                                                //那些你不知道的事
	public void Cold_Ques_Init(){
		QuesOperate db_qo = new QuesOperate(this);
		SQLiteDatabase db = db_qo.find_account();
		db = openOrCreateDatabase("test006.db", Context.MODE_PRIVATE, null);  
		db.execSQL("DROP TABLE IF EXISTS q_ques");  
		db.execSQL("create table if not exists q_ques(_id integer primary key,question_show varchar(50),a_ans varchar(50),b_ans varchar(50),c_ans varchar(50),d_ans varchar(50),right_ans varchar(50))");
		Question[] Ques_Text_enum = new Question[50];

		Ques_Text_enum[0] = new Question(1,"anime柯南中目暮警官中的死神是？","灰原哀","圆谷光彦","江户川新一","毛利大侦探","毛利大侦探");
		Ques_Text_enum[1] = new Question(2,"鸣人的“恋人”是？","小樱","佐助","雏田","井野","佐助");
		Ques_Text_enum[2] = new Question(3,"鲁鲁修为啥叛逆？","为了正义","处于叛逆期","重度妹控","中二了","重度妹控");
		Ques_Text_enum[3] = new Question(4,"柯南中的赤井秀一被暗杀后的名字？","本堂瑛祐","冲矢昴","工藤优作","羽田秀吉","冲矢昴");
		Ques_Text_enum[4] = new Question(5,"“这也太扯了吧”是谁的口头禅？","海神航","樱井智树","桂木桂马","鹿丸","海神航");
		Ques_Text_enum[5] = new Question(6,"第六代火影是？","旗木卡卡西","漩涡鸣人","千手纲手","千手柱间","旗木卡卡西");
		Ques_Text_enum[6] = new Question(7,"柯南剧场版《贝克街的亡灵》时代背景是？","巴黎","伦敦","柏林","荷兰","伦敦");
		Ques_Text_enum[7] = new Question(8,"“不是假发，是桂”出自？","《gintama》","《another》","《to love》","《naruto》","《gintama》");
		Ques_Text_enum[8] = new Question(9,"钉宫四萌不包括？","露易丝","逢坂大河","夏娜","哈比","哈比");
		Ques_Text_enum[9] = new Question(10,"漫画中路飞在战？","香克斯","四皇","多弗朗明哥","鹰眼","多弗朗明哥");
		Ques_Text_enum[10] = new Question(11,"《犬夜叉》几部剧场版？","2","3","4","5","4");
		Ques_Text_enum[11] = new Question(12,"2015年《龙珠》最新代号为？","龙珠Z","龙珠GT","龙珠超","龙珠AF","龙珠超");
		Ques_Text_enum[12] = new Question(13,"柯南中的赤井秀一被暗杀后的名字？","本堂瑛祐","冲矢昴","工藤优作","羽田秀吉","冲矢昴");
		Ques_Text_enum[13] = new Question(14,"柯南中的赤井秀一被暗杀后的名字？","本堂瑛祐","冲矢昴","工藤优作","羽田秀吉","冲矢昴");
		Ques_Text_enum[14] = new Question(15,"柯南中的赤井秀一被暗杀后的名字？","本堂瑛祐","冲矢昴","工藤优作","羽田秀吉","冲矢昴");
		Ques_Text_enum[15] = new Question(16,"柯南中的赤井秀一被暗杀后的名字？","本堂瑛祐","冲矢昴","工藤优作","羽田秀吉","冲矢昴");
		Ques_Text_enum[16] = new Question(17,"柯南中的赤井秀一被暗杀后的名字？","本堂瑛祐","冲矢昴","工藤优作","羽田秀吉","冲矢昴");
		
		
		for(int i = 0; i < max_num; i++){
		    db_qo.add_ques(Ques_Text_enum[i]);	
		}
		
	}
	private void updateTextView(){
		if(count != 0)
		{
		   Remain_time.setText(String.valueOf(Total_Time-count+1));
		}
		else
		{
			Remain_time.setText(String.valueOf(Total_Time-count));
		}
		if(count == Total_Time)
		{
			loop = loop+1;
			if(loop<loop_end)
			{
			    set_show();
			}
			else
			{
				Intent intent = new Intent();
		        intent.setClass(CGFirst.this, CGSuccess1.class);
		        Bundle bundle = new Bundle();
		        bundle.putString("result", String.valueOf(point));
		        intent.putExtras(bundle);
		        startActivity(intent);
			}
		}
	}

	private void startTimer(){
		if (mTimer == null) {
			mTimer = new Timer();
		}

		if (mTimerTask == null) {
			mTimerTask = new TimerTask() {
				@Override
				public void run() {
					Log.i(TAG, "count: "+String.valueOf(count));
					sendMessage(UPDATE_TEXTVIEW);
					
					do {
						try {
							Log.i(TAG, "sleep(1000)...");
							Thread.sleep(1000);
						} catch (InterruptedException e) {
						}	
					} while (isPause);
					
					count ++;  
				}
			};
		}

		if(mTimer != null && mTimerTask != null )
			mTimer.schedule(mTimerTask, delay, period);

	}

	private void stopTimer(){
		
		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}

		if (mTimerTask != null) {
			mTimerTask.cancel();
			mTimerTask = null;
		}	

		count = 0;

	}
	
	public void sendMessage(int id){
		if (mHandler != null) {
			Message message = Message.obtain(mHandler, id);   
			mHandler.sendMessage(message); 
		}
	}
}

