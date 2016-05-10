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

public class User_cold extends Activity implements OnClickListener{
	
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
		/*
		 * 引入组件
		 */
		Total_Time = data.getA();
		
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
		
		QuesOperate user_name_qo = new QuesOperate(User_cold.this);
		SQLiteDatabase user_anime_login = user_name_qo.find_account();
		String sql="select _id,question_show,a_ans,b_ans,c_ans,d_ans,right_ans from q_ques_cold where _id=?";
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
				{Toast.makeText(User_cold.this, "duang~,答对了~", Toast.LENGTH_LONG).show();
				point = point+1;}
				else
				Toast.makeText(User_cold.this, "好遗憾->_->", Toast.LENGTH_LONG).show();
	//			anime_delay(delay_time);
				set_show();
			}
			else{
				if(Str_A.equals(Str_RightAnswer)){
					Toast.makeText(User_cold.this, "duang~,答对了~", Toast.LENGTH_LONG).show();
					point = point+1;}
					else
					Toast.makeText(User_cold.this, "好遗憾->_->", Toast.LENGTH_LONG).show();
				Intent intent = new Intent();
		        intent.setClass(User_cold.this, Result.class);
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
					Toast.makeText(User_cold.this, "duang~,答对了~", Toast.LENGTH_LONG).show();
					point = point+1;}
					else
					Toast.makeText(User_cold.this, "好遗憾->_->", Toast.LENGTH_LONG).show();
		//		anime_delay(delay_time);
				set_show();
			}
			else{
				if(Str_B.equals(Str_RightAnswer))
				{
					Toast.makeText(User_cold.this, "duang~,答对了~", Toast.LENGTH_LONG).show();
					point = point+1;}
					else
					Toast.makeText(User_cold.this, "好遗憾->_->", Toast.LENGTH_LONG).show();
				Intent intent = new Intent();
		        intent.setClass(User_cold.this, Result.class);
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
    					Toast.makeText(User_cold.this, "duang~,答对了~", Toast.LENGTH_LONG).show();
    					point = point+1;}
    					else
    					Toast.makeText(User_cold.this, "好遗憾->_->", Toast.LENGTH_LONG).show();
    	//			anime_delay(delay_time);
    				set_show();
    			}
    			else{
    				if(Str_C.equals(Str_RightAnswer))
    				{
    					Toast.makeText(User_cold.this, "duang~,答对了~", Toast.LENGTH_LONG).show();
    					point = point+1;}
    					else
    					Toast.makeText(User_cold.this, "好遗憾->_->", Toast.LENGTH_LONG).show();
    				Intent intent = new Intent();
    		        intent.setClass(User_cold.this, Result.class);
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
    					Toast.makeText(User_cold.this, "duang~,答对了~", Toast.LENGTH_LONG).show();
    					point = point+1;}
    					else
    					Toast.makeText(User_cold.this, "好遗憾->_->", Toast.LENGTH_LONG).show();
    	//			anime_delay(delay_time);
    				set_show();
    			}
    			else{
    				if(Str_D.equals(Str_RightAnswer))
    				{
    					Toast.makeText(User_cold.this, "duang~,答对了~", Toast.LENGTH_LONG).show();
    					point = point+1;}
    					else
    					Toast.makeText(User_cold.this, "好遗憾->_->", Toast.LENGTH_LONG).show();
    				Intent intent = new Intent();
    		        intent.setClass(User_cold.this, Result.class);
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
		db.execSQL("DROP TABLE IF EXISTS q_ques_cold");  
		db.execSQL("create table if not exists q_ques_cold(_id integer primary key,question_show varchar(50),a_ans varchar(50),b_ans varchar(50),c_ans varchar(50),d_ans varchar(50),right_ans varchar(50))");
		Question[] Ques_Text_enum = new Question[50];

		Ques_Text_enum[0] = new Question(1,"“给力”这个词出自以下那本名著？","《不给力啊，老师》","《这货不是宿敌》","《西游记：旅程的终点》","不知道","《西游记：旅程的终点》");
		Ques_Text_enum[1] = new Question(2,"“坑爹”是什么意思","小坑他爹","他爹姓坑","骗人、不太给力","不知道","骗人、不太给力");
		Ques_Text_enum[2] = new Question(3,"开车撞到人，只要说什么就没事了？","我爸是李刚","我女是彭帅","对不起","不知道","我爸是李刚");
		Ques_Text_enum[3] = new Question(4,"中国最神秘的部门是神马部门？","有关部门 ","无关部门","艳照门","不知道","有关部门");
		Ques_Text_enum[4] = new Question(5,"什么是“3Q大战”","三个阿Q玩3P","腾讯公司强迫4亿网民卸载360事件 ","赌王和赌圣决战时双方的底牌分别是红桃3和黑桃Q","不知道","腾讯公司强迫4亿网民卸载360事件");
		Ques_Text_enum[5] = new Question(6,"从前有个女孩叫小文，后来她有了男朋友，她就改名叫神马了？","凤姐","小月月","小六","不知道","小六");
		Ques_Text_enum[6] = new Question(7,"选出你认为无毒的东西？","三鹿奶粉","一滴香","老鼠药","不知道","老鼠药");
		Ques_Text_enum[7] = new Question(8,"如果湖南卫视叫“芒果台”，江苏卫视叫？","大蒜台","鸭梨台","番茄台","不知道","大蒜台");
		Ques_Text_enum[8] = new Question(9,"淘宝卖家喜欢怎么称呼买家？","你妹","亲","大叔","不知道","亲");
		Ques_Text_enum[9] = new Question(10,"“不给力”的英文怎么表达？","bugeili","ungeili","ungelivable","不知道","ungelivable");
		Ques_Text_enum[10] = new Question(11,"以下谁最需要领取住房补贴？","马化腾","方舟子","唐骏","不知道","马化腾");
		Ques_Text_enum[11] = new Question(12,"如果你是兰州，你最讨厌别人回神马？","兰州烧饼","沙发","围观","不知道","兰州烧饼");
		Ques_Text_enum[12] = new Question(13,"当你过关斩将赢得世界冠军后，应该先谢谁？","CCTV","所有TV","父母","国家","国家");
		Ques_Text_enum[13] = new Question(14,"说一个人很倒霉，下面那个不合适","苦逼","悲催","杯具","沙发","沙发");
		Ques_Text_enum[14] = new Question(15,"以下哪个油可以食用？","中石油","中石化","中海油","中地沟与","中地沟油");
		Ques_Text_enum[15] = new Question(16,"如果我们视金钱、权利、美女为粪土，通常会说","你知道的太多了","神马都是浮云","XX神马的最讨厌了","不知道","神马都是浮云");
		Ques_Text_enum[16] = new Question(17,"以下哪种需求一生难求？","求合体","求加薪","求公平","不知道","求公平");
		
		for(int i = 0; i < max_num; i++){
		    db_qo.add_ques_cold(Ques_Text_enum[i]);	
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
		        intent.setClass(User_cold.this, Result.class);
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

