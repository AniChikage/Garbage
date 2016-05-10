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

public class User_culture extends Activity implements OnClickListener{
	
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
			
		QuesOperate user_name_qo = new QuesOperate(User_culture.this);
		SQLiteDatabase user_anime_login = user_name_qo.find_account();
		String sql="select _id,question_show,a_ans,b_ans,c_ans,d_ans,right_ans from q_ques_culture where _id=?";
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
				{Toast.makeText(User_culture.this, "duang~,答对了~", Toast.LENGTH_LONG).show();
				point = point+1;}
				else
				Toast.makeText(User_culture.this, "好遗憾->_->", Toast.LENGTH_LONG).show();
	//			anime_delay(delay_time);
				set_show();
			}
			else{
				if(Str_A.equals(Str_RightAnswer)){
					Toast.makeText(User_culture.this, "duang~,答对了~", Toast.LENGTH_LONG).show();
					point = point+1;}
					else
					Toast.makeText(User_culture.this, "好遗憾->_->", Toast.LENGTH_LONG).show();
				Intent intent = new Intent();
		        intent.setClass(User_culture.this, Result.class);
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
					Toast.makeText(User_culture.this, "duang~,答对了~", Toast.LENGTH_LONG).show();
					point = point+1;}
					else
					Toast.makeText(User_culture.this, "好遗憾->_->", Toast.LENGTH_LONG).show();
		//		anime_delay(delay_time);
				set_show();
			}
			else{
				if(Str_B.equals(Str_RightAnswer))
				{
					Toast.makeText(User_culture.this, "duang~,答对了~", Toast.LENGTH_LONG).show();
					point = point+1;}
					else
					Toast.makeText(User_culture.this, "好遗憾->_->", Toast.LENGTH_LONG).show();
				Intent intent = new Intent();
		        intent.setClass(User_culture.this, Result.class);
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
    					Toast.makeText(User_culture.this, "duang~,答对了~", Toast.LENGTH_LONG).show();
    					point = point+1;}
    					else
    					Toast.makeText(User_culture.this, "好遗憾->_->", Toast.LENGTH_LONG).show();
    	//			anime_delay(delay_time);
    				set_show();
    			}
    			else{
    				if(Str_C.equals(Str_RightAnswer))
    				{
    					Toast.makeText(User_culture.this, "duang~,答对了~", Toast.LENGTH_LONG).show();
    					point = point+1;}
    					else
    					Toast.makeText(User_culture.this, "好遗憾->_->", Toast.LENGTH_LONG).show();
    				Intent intent = new Intent();
    		        intent.setClass(User_culture.this, Result.class);
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
    					Toast.makeText(User_culture.this, "duang~,答对了~", Toast.LENGTH_LONG).show();
    					point = point+1;}
    					else
    					Toast.makeText(User_culture.this, "好遗憾->_->", Toast.LENGTH_LONG).show();
    	//			anime_delay(delay_time);
    				set_show();
    			}
    			else{
    				if(Str_D.equals(Str_RightAnswer))
    				{
    					Toast.makeText(User_culture.this, "duang~,答对了~", Toast.LENGTH_LONG).show();
    					point = point+1;}
    					else
    					Toast.makeText(User_culture.this, "好遗憾->_->", Toast.LENGTH_LONG).show();
    				Intent intent = new Intent();
    		        intent.setClass(User_culture.this, Result.class);
    		        Bundle bundle = new Bundle();
    		        bundle.putString("result", String.valueOf(point));
    		        intent.putExtras(bundle);
    		        startActivity(intent);
    		      //  this.startActivityForResult(intent, 0);
    			}
        		break;
        	case R.id.returncategory:
        		Intent intent = new Intent();
		        intent.setClass(User_culture.this, Category.class);
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
	}                                                                       //文学
	public void Anime_Ques_Init(){
		QuesOperate db_qo = new QuesOperate(this);
		SQLiteDatabase db = db_qo.find_account();
		db = openOrCreateDatabase("test006.db", Context.MODE_PRIVATE, null);  
		db.execSQL("DROP TABLE IF EXISTS q_ques_culture");  
		db.execSQL("create table if not exists q_ques_culture(_id integer primary key,question_show varchar(50),a_ans varchar(50),b_ans varchar(50),c_ans varchar(50),d_ans varchar(50),right_ans varchar(50))");
		Question[] Ques_Text_enum = new Question[50];

		Ques_Text_enum[0] = new Question(1,"鲁迅先生称（ ）为“史家之绝唱，无韵之离骚”","三国志","史记","汉书","资治通鉴","史记");
		Ques_Text_enum[1] = new Question(2,"下面哪个选项是”四书”的构成","大学.中庸.论语.尔雅","大学.中庸.论语.孟子","大学.孝经.论语.尔雅","大学.孝经.论语.孟子","大学.中庸.论语.孟子");
		Ques_Text_enum[2] = new Question(3,"三纲五常即：君为臣纲，父为子纲，夫为妻纲，那么它是由谁提出的","孔子","孟子","朱熹","董仲舒","董仲舒");
		Ques_Text_enum[3] = new Question(4,"下面哪个文学家被称为“太康之英”","左思","潘岳","阮籍","陆机","陆机");
		Ques_Text_enum[4] = new Question(5,"我们通常所说的“韩孟诗派”中的孟是指","孟郊","孟浩然","孟光","孟云卿","孟郊");
		Ques_Text_enum[5] = new Question(6,"中国古代哲学中，有“阴阳八卦”的理论，这一理论出自（ ）一书。","《尚书》","《论语》","《周易》","《孟子》","《周易》");
		Ques_Text_enum[6] = new Question(7,"“生旦净末丑”是京剧的行当，其中“净”是：","男角","女角","儿童","男女均可","男角");
		Ques_Text_enum[7] = new Question(8,"下列文学家中，不属于“唐宋八大家”的是","欧阳修","王勃","柳宗元","王安石","王勃");
		Ques_Text_enum[8] = new Question(9,"下面哪个字常用作表示顺序的第五位？","戊","戍","戌","术","戊");
		Ques_Text_enum[9] = new Question(10,"梨园用来指代戏曲界，那么“杏林”指代的是","教育界","医学界","文艺界","桃园","医学界");
		Ques_Text_enum[10] = new Question(11,"“不以物喜，不以己悲”出自哪篇目哪篇古文？","《醉翁亭记》","《岳阳楼记》","《黄鹤楼送孟浩然之广陵》","《望洞庭》","《岳阳楼记》");
		Ques_Text_enum[11] = new Question(12,"“问世间情为何物，直教生死相许”这句诗是谁写的","元好问","柳永","晏殊","李清照","元好问");
		Ques_Text_enum[12] = new Question(13,"《阿Q正传》中阿Q的最后结局是","被当成罪羊抓往城里，关进大牢","糊里糊涂送掉性命","成了富人","平庸过生活","糊里糊涂送掉性命");
		Ques_Text_enum[13] = new Question(14,"下列对作品、作家的表述，错误的一项是","汤显祖，明代杂剧家，代表作为《牡丹亭》","曹禺、夏衍是我国现代著名的剧作家","塞万提斯，西班牙作家，著有《鲁滨逊瓢流记》","《老人和海》的作者是美国作家海明威","塞万提斯，西班牙作家，著有《鲁滨逊瓢流记》");
		Ques_Text_enum[14] = new Question(15,"我国古代诗歌按产生的时代排列，正确的一项是","诗经——楚辞——乐府——曲——词","诗经——乐府——楚辞——曲——词","诗经——楚辞——乐府——词——曲","诗经——乐府——楚辞——词——曲","诗经——楚辞——乐府——词——曲");
		Ques_Text_enum[15] = new Question(16,"选出正确的一项","《雨霖铃》――柳永――南宋","《鲁滨逊漂流记》――笛福――法国","《基督山伯爵》――小仲马――法国","《扬州慢》――杜牧――唐代","《鲁滨逊漂流记》――笛福――法国");
		Ques_Text_enum[16] = new Question(17,"《汉书》所属的历史编纂体例是","编年史","纪传体通史","断代史","国别史","断代史");
		
		for(int i = 0; i < max_num; i++){
		    db_qo.add_ques_culture(Ques_Text_enum[i]);	
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
		        intent.setClass(User_culture.this, Result.class);
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

