package com.thars;

import java.util.Timer;
import java.util.TimerTask;

import com.thars.R;
import com.thars.question.QuesOperate;
import com.thars.question.Question;
import com.thars.question.User_Data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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

public class User_quzz_a extends Activity implements OnClickListener{
	
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
		
		private User_Data data;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_category);
		
		Total_Time = data.getA();
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
		Remain_time = (TextView)findViewById(R.id.remain_time);
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
		count1 = 0;
		isStop = !isStop;

		if (!isStop) {
			startTimer();
		}else {
			stopTimer();
		//	startTimer();
		}
	//	startTimer();
		mHandler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case UPDATE_TEXTVIEW:
					updateTextView();
					break;
				default:
					break;
				}
			//	count1++;
			}
		};
		if(isStop)set_show();
			
		QuesOperate user_name_qo = new QuesOperate(User_quzz_a.this);
		SQLiteDatabase user_anime_login = user_name_qo.find_account();
		String sql="select _id,question_show,a_ans,b_ans,c_ans,d_ans,right_ans from q_ques_quz where _id=?";
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
				{Toast.makeText(User_quzz_a.this, "duang~,答对了~", Toast.LENGTH_LONG).show();
				point = point+1;}
				else
				Toast.makeText(User_quzz_a.this, "好遗憾->_->", Toast.LENGTH_LONG).show();
	//			anime_delay(delay_time);
				set_show();
			}
			else{
				if(Str_A.equals(Str_RightAnswer)){
					Toast.makeText(User_quzz_a.this, "duang~,答对了~", Toast.LENGTH_LONG).show();
					point = point+1;}
					else
					Toast.makeText(User_quzz_a.this, "好遗憾->_->", Toast.LENGTH_LONG).show();
				Intent intent = new Intent();
		        intent.setClass(User_quzz_a.this, Result.class);
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
					Toast.makeText(User_quzz_a.this, "duang~,答对了~", Toast.LENGTH_LONG).show();
					point = point+1;}
					else
					Toast.makeText(User_quzz_a.this, "好遗憾->_->", Toast.LENGTH_LONG).show();
		//		anime_delay(delay_time);
				set_show();
			}
			else{
				if(Str_B.equals(Str_RightAnswer))
				{
					Toast.makeText(User_quzz_a.this, "duang~,答对了~", Toast.LENGTH_LONG).show();
					point = point+1;}
					else
					Toast.makeText(User_quzz_a.this, "好遗憾->_->", Toast.LENGTH_LONG).show();
				Intent intent = new Intent();
		        intent.setClass(User_quzz_a.this, Result.class);
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
    					Toast.makeText(User_quzz_a.this, "duang~,答对了~", Toast.LENGTH_LONG).show();
    					point = point+1;}
    					else
    					Toast.makeText(User_quzz_a.this, "好遗憾->_->", Toast.LENGTH_LONG).show();
    	//			anime_delay(delay_time);
    				set_show();
    			}
    			else{
    				if(Str_C.equals(Str_RightAnswer))
    				{
    					Toast.makeText(User_quzz_a.this, "duang~,答对了~", Toast.LENGTH_LONG).show();
    					point = point+1;}
    					else
    					Toast.makeText(User_quzz_a.this, "好遗憾->_->", Toast.LENGTH_LONG).show();
    				Intent intent = new Intent();
    		        intent.setClass(User_quzz_a.this, Result.class);
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
    					Toast.makeText(User_quzz_a.this, "duang~,答对了~", Toast.LENGTH_LONG).show();
    					point = point+1;}
    					else
    					Toast.makeText(User_quzz_a.this, "好遗憾->_->", Toast.LENGTH_LONG).show();
    	//			anime_delay(delay_time);
    				set_show();
    			}
    			else{
    				if(Str_D.equals(Str_RightAnswer))
    				{
    					Toast.makeText(User_quzz_a.this, "duang~,答对了~", Toast.LENGTH_LONG).show();
    					point = point+1;}
    					else
    					Toast.makeText(User_quzz_a.this, "好遗憾->_->", Toast.LENGTH_LONG).show();
    				Intent intent = new Intent();
    		        intent.setClass(User_quzz_a.this, Result.class);
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
		db.execSQL("DROP TABLE IF EXISTS q_ques_quz");  
		db.execSQL("create table if not exists q_ques_quz(_id integer primary key,question_show varchar(50),a_ans varchar(50),b_ans varchar(50),c_ans varchar(50),d_ans varchar(50),right_ans varchar(50))");
		Question[] Ques_Text_enum = new Question[50];

		Ques_Text_enum[0] = new Question(1,"林冲的绰号是？","黑旋风","黑三郎","及时雨","豹子头","豹子头");
		Ques_Text_enum[1] = new Question(2,"在评价《水浒传》时，谁被金圣叹评为“阔人？","武松","林冲","晁错","鲁智深","鲁智深");
		Ques_Text_enum[2] = new Question(3,"绛珠仙草在《红楼梦》中指代后来的","贾宝玉","林黛玉","薛宝钗","史湘云","林黛玉");
		Ques_Text_enum[3] = new Question(4,"下列哪个女子不喜欢杨过","程英","陆无双","耶律燕","小龙女","耶律燕");
		Ques_Text_enum[4] = new Question(5,"古龙作品中与楚留香齐名，被尊为“侠探”的陆小凤，他最厉害的武功是","天外飞仙","灵犀一指","凤舞九天","凌波微步","灵犀一指");
		Ques_Text_enum[5] = new Question(6,"“北乔峰，南慕容”中的慕容指的是谁","慕容复","慕容博","慕容儁","都不对","慕容复");
		Ques_Text_enum[7] = new Question(8,"《鹿鼎记》中，素有“满洲第一勇士”之称的是谁","苏克萨哈","索尼","鳌拜","都不对","鳌拜");
		Ques_Text_enum[8] = new Question(9,"《武林外史》中快活王手下财使是","金不换","熊猫儿","金无望","都不对","金无望");
		Ques_Text_enum[9] = new Question(10,"《倚天屠龙记》中，宋青书和陈友谅杀的武当七侠是","莫声谷","殷梨亭","张松溪","都不对","莫声谷");
		Ques_Text_enum[10] = new Question(11,"蜀汉的五虎上将不包括","关羽","张飞","吕布","马超","吕布");
		Ques_Text_enum[11] = new Question(12,"关于诸葛亮说法正确的是","山东人","被称为伏龙","三顾茅庐","都对","都对");
		Ques_Text_enum[12] = new Question(13,"中国小说史上，第一部长篇小说是（）","《西游记》","《三国演义》","《史记》","《水浒传》","《三国演义》");
		Ques_Text_enum[13] = new Question(14,"《大旗英雄传》中“日夜雷雨电风”六大高手名字存在哪篇文章中","《碧落赋》","《洛神赋》","《鹦鹉赋》","都不是","《碧落赋》");
		Ques_Text_enum[14] = new Question(15,"《碧血剑》中，“神行百变”是哪一门的武功","华山派","铁剑门","五毒教","都不是","铁剑门");
		Ques_Text_enum[15] = new Question(16,"“挟天子以令诸候”者是谁？","曹操","袁绍","刘备","孙坚","曹操");
		Ques_Text_enum[16] = new Question(17,"巧姐最后被（）从烟花巷中救出","贾宝玉","贾琏","刘姥姥","王熙凤","刘姥姥");
		
		for(int i = 0; i < max_num; i++){
		    db_qo.add_ques_quzz(Ques_Text_enum[i]);	
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
		        intent.setClass(User_quzz_a.this, Result.class);
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

