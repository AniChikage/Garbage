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

public class User_video extends Activity implements OnClickListener{
	
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
        private Button return_cate;
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
		return_cate = (Button)findViewById(R.id.returncategory);
		/*
		初始化数据库
	    */
		Anime_Ques_Init();
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
			
		QuesOperate user_name_qo = new QuesOperate(User_video.this);
		SQLiteDatabase user_anime_login = user_name_qo.find_account();
		String sql="select _id,question_show,a_ans,b_ans,c_ans,d_ans,right_ans from q_ques_video where _id=?";
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
		return_cate.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		//添加
		case R.id.category_ans_a:
			loop = loop+1;
			if(loop<loop_end){
				if(Str_A.equals(Str_RightAnswer))
				{Toast.makeText(User_video.this, "duang~,答对了~", Toast.LENGTH_LONG).show();
				point = point+1;}
				else
				Toast.makeText(User_video.this, "好遗憾->_->", Toast.LENGTH_LONG).show();
	//			anime_delay(delay_time);
				set_show();
			}
			else{
				if(Str_A.equals(Str_RightAnswer)){
					Toast.makeText(User_video.this, "duang~,答对了~", Toast.LENGTH_LONG).show();
					point = point+1;}
					else
					Toast.makeText(User_video.this, "好遗憾->_->", Toast.LENGTH_LONG).show();
				Intent intent = new Intent();
		        intent.setClass(User_video.this, Result.class);
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
					Toast.makeText(User_video.this, "duang~,答对了~", Toast.LENGTH_LONG).show();
					point = point+1;}
					else
					Toast.makeText(User_video.this, "好遗憾->_->", Toast.LENGTH_LONG).show();
		//		anime_delay(delay_time);
				set_show();
			}
			else{
				if(Str_B.equals(Str_RightAnswer))
				{
					Toast.makeText(User_video.this, "duang~,答对了~", Toast.LENGTH_LONG).show();
					point = point+1;}
					else
					Toast.makeText(User_video.this, "好遗憾->_->", Toast.LENGTH_LONG).show();
				Intent intent = new Intent();
		        intent.setClass(User_video.this, Result.class);
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
    					Toast.makeText(User_video.this, "duang~,答对了~", Toast.LENGTH_LONG).show();
    					point = point+1;}
    					else
    					Toast.makeText(User_video.this, "好遗憾->_->", Toast.LENGTH_LONG).show();
    	//			anime_delay(delay_time);
    				set_show();
    			}
    			else{
    				if(Str_C.equals(Str_RightAnswer))
    				{
    					Toast.makeText(User_video.this, "duang~,答对了~", Toast.LENGTH_LONG).show();
    					point = point+1;}
    					else
    					Toast.makeText(User_video.this, "好遗憾->_->", Toast.LENGTH_LONG).show();
    				Intent intent = new Intent();
    		        intent.setClass(User_video.this, Result.class);
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
    					Toast.makeText(User_video.this, "duang~,答对了~", Toast.LENGTH_LONG).show();
    					point = point+1;}
    					else
    					Toast.makeText(User_video.this, "好遗憾->_->", Toast.LENGTH_LONG).show();
    	//			anime_delay(delay_time);
    				set_show();
    			}
    			else{
    				if(Str_D.equals(Str_RightAnswer))
    				{
    					Toast.makeText(User_video.this, "duang~,答对了~", Toast.LENGTH_LONG).show();
    					point = point+1;}
    					else
    					Toast.makeText(User_video.this, "好遗憾->_->", Toast.LENGTH_LONG).show();
    				Intent intent = new Intent();
    		        intent.setClass(User_video.this, Result.class);
    		        Bundle bundle = new Bundle();
    		        bundle.putString("result", String.valueOf(point));
    		        intent.putExtras(bundle);
    		        startActivity(intent);
    		      //  this.startActivityForResult(intent, 0);
    			}
        		break;
        	case R.id.returncategory:
        		Intent intent = new Intent();
		        intent.setClass(User_video.this, Category.class);
		        startActivity(intent);
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
	}                                                            //娱乐圈
	public void Anime_Ques_Init(){
		QuesOperate db_qo = new QuesOperate(this);
		SQLiteDatabase db = db_qo.find_account();
		db = openOrCreateDatabase("test006.db", Context.MODE_PRIVATE, null);  
		db.execSQL("DROP TABLE IF EXISTS q_ques_video");  
		db.execSQL("create table if not exists q_ques_video(_id integer primary key,question_show varchar(50),a_ans varchar(50),b_ans varchar(50),c_ans varchar(50),d_ans varchar(50),right_ans varchar(50))");
		Question[] Ques_Text_enum = new Question[50];

		Ques_Text_enum[0] = new Question(1,"科幻电影《终结者》中是由哪位男演员主演了强悍的机器人形象","汤姆.克鲁斯","席维斯.史泰龙","阿诺德.施瓦辛格","尼古拉斯.凯奇","阿诺德.施瓦辛格");
		Ques_Text_enum[1] = new Question(2,"电影《金刚》中的金刚爬上了纽约市的哪座摩天大楼？","世界贸易中心一号大楼","帝国大厦","美国银行大厦","纽约时报大厦","帝国大厦");
		Ques_Text_enum[2] = new Question(3,"曾导演电影《东邪西毒》《花样年华》《2046》的香港著名电影导演是谁","徐克","处杜琪峰","陈可辛","王家卫","王家卫");
		Ques_Text_enum[3] = new Question(4,"电视剧《那扇门拯救你我的爱人》《永不瞩目》《玉观音》合称为哪位作家的三部曲","曹禺","宁财神","海岩","都梁","海岩");
		Ques_Text_enum[4] = new Question(5,"张纪中版的《天龙八部》里王语嫣的扮演者是哪位内的的演员？","朱茵","刘亦菲","李若彤","赵丽颖","刘亦菲");
		Ques_Text_enum[5] = new Question(6,"一代武术宗师、功夫影帝、截拳道武道哲学创立人、好莱坞首位华人演员是","成龙","李小龙","李连杰","赵文卓","李小龙");
		Ques_Text_enum[6] = new Question(7,"《如果爱》《赤壁》《投名状》的是哪位男星","梁朝伟","胡军","金城武","张学友","金城武");
		Ques_Text_enum[7] = new Question(8,"张艺谋总导演的《印象刘三姐》山水实景演出地是在哪？","桂林","张家界","九寨沟","洛阳","桂林");
		Ques_Text_enum[8] = new Question(9,"美国科幻片《骇客帝国》中是由哪位男演员主演了男主角救世尼奥","罗宾威廉姆斯","菲利普.塞默.霍夫曼","基努里维斯","哈里森福特","基努里维斯");
		Ques_Text_enum[9] = new Question(10,"美国奥斯卡奖历史上只有一人连续两届获得最佳男演员奖，这个人是谁","布拉德.皮特","威尔.史密斯","汤姆·汉克斯","约翰尼德普","汤姆·汉克斯");
		Ques_Text_enum[10] = new Question(11,"83 版的《射雕英雄传》中是哪位演员扮演了黄蓉？","翁美玲","朱茵","刘晓庆","张金玲","翁美玲");
		Ques_Text_enum[11] = new Question(12,"2015年《龙珠》最新代号为？","龙珠Z","龙珠GT","龙珠超","龙珠AF","龙珠超");
		Ques_Text_enum[12] = new Question(13,"2012年美国奥斯卡奖最佳影片奖是哪部影片？","《为奴十二年》","《国王的演讲》","《拆弹部队》","《艺术家》","《艺术家》");
		Ques_Text_enum[13] = new Question(14,"电影《泰坦尼克号》中，年老的 Rose 扔进海里的蓝色钻石叫什么名字","天空之眼","海洋之星","希望之星","轮船之眼","海洋之星");
		Ques_Text_enum[14] = new Question(15,"经典港剧《义不容情》的主题歌是已故歌手陈百强演唱的那首歌？","不再问究竟","一生何求","孤寂","偏偏喜欢你","冲矢昴");
		Ques_Text_enum[15] = new Question(16,"“天天兄弟”中因拍摄电视剧被火烧伤的是哪一位？","俞灏明","田源","欧弟","小五","俞灏明");
		Ques_Text_enum[16] = new Question(17,"醉驾入刑法例实施后，文化名人醉驾入狱的第一人是谁？","王志文","高晓松","林晓培","林惠","高晓松");
		
		for(int i = 0; i < max_num; i++){
		    db_qo.add_ques_video(Ques_Text_enum[i]);	
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
		        intent.setClass(User_video.this, Result.class);
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

