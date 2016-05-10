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
 * ʵ��SQLite���ݿ����ӡ�ɾ�����޸ġ���ѯ����
 * 
 */
//public int show_time;

public class User_quzz extends Activity implements OnClickListener{
	
        public int loop,loop_end=10,max_num=12;//loop_end��ѡ�����⣬max_num������е�����
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
		 ����ʼ�ղ���
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
		 * �������
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
		��ʼ�����ݿ�
	    */
		Cold_Ques_Init();
		/*
		��ʼ������ 
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
			
		QuesOperate user_name_qo = new QuesOperate(User_quzz.this);
		SQLiteDatabase user_anime_login = user_name_qo.find_account();
		String sql="select _id,question_show,a_ans,b_ans,c_ans,d_ans,right_ans from q_ques where _id=?";
	    Cursor user_anime_cur=user_anime_login.rawQuery(sql, new String[]{
			 String.valueOf(temp[loop])
	 	 });
	    user_anime_cur.moveToFirst();
	    
	    //���������
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
		Remain_ques.setText("ʣ�ࣺ"+String.valueOf(10-loop));
		point_show.setText("gold��"+String.valueOf(point*10));
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
		//���
		case R.id.category_ans_a:
			loop = loop+1;
			if(loop<loop_end){
				if(Str_A.equals(Str_RightAnswer))
				{Toast.makeText(User_quzz.this, "duang~,�����~", Toast.LENGTH_LONG).show();
				point = point+1;}
				else
				Toast.makeText(User_quzz.this, "���ź�->_->", Toast.LENGTH_LONG).show();
	//			anime_delay(delay_time);
				set_show();

			}
			else{
				if(Str_A.equals(Str_RightAnswer)){
					Toast.makeText(User_quzz.this, "duang~,�����~", Toast.LENGTH_LONG).show();
					point = point+1;}
					else
					Toast.makeText(User_quzz.this, "���ź�->_->", Toast.LENGTH_LONG).show();
				Intent intent = new Intent();
		        intent.setClass(User_quzz.this, Result.class);
		        Bundle bundle = new Bundle();
		        bundle.putString("result", String.valueOf(point));
		        intent.putExtras(bundle); 
		        startActivity(intent);
			}
			break;
		//ɾ��
        case R.id.category_ans_b:
        	loop = loop+1;
			if(loop<loop_end){
				if(Str_B.equals(Str_RightAnswer))
				{
					Toast.makeText(User_quzz.this, "duang~,�����~", Toast.LENGTH_LONG).show();
					point = point+1;}
					else
					Toast.makeText(User_quzz.this, "���ź�->_->", Toast.LENGTH_LONG).show();
		//		anime_delay(delay_time);
				set_show();
			}
			else{
				if(Str_B.equals(Str_RightAnswer))
				{
					Toast.makeText(User_quzz.this, "duang~,�����~", Toast.LENGTH_LONG).show();
					point = point+1;}
					else
					Toast.makeText(User_quzz.this, "���ź�->_->", Toast.LENGTH_LONG).show();
				Intent intent = new Intent();
		        intent.setClass(User_quzz.this, Result.class);
		        Bundle bundle = new Bundle();
		        bundle.putString("result", String.valueOf(point));
		        intent.putExtras(bundle);
		       startActivity(intent);
		        // this.startActivityForResult(intent, 0);
			}
        	break;
        //�޸�
        	case R.id.category_ans_c:
        		loop = loop+1;
    			if(loop<loop_end){
    				if(Str_C.equals(Str_RightAnswer))
    				{
    					Toast.makeText(User_quzz.this, "duang~,�����~", Toast.LENGTH_LONG).show();
    					point = point+1;}
    					else
    					Toast.makeText(User_quzz.this, "���ź�->_->", Toast.LENGTH_LONG).show();
    	//			anime_delay(delay_time);
    				set_show();
    			}
    			else{
    				if(Str_C.equals(Str_RightAnswer))
    				{
    					Toast.makeText(User_quzz.this, "duang~,�����~", Toast.LENGTH_LONG).show();
    					point = point+1;}
    					else
    					Toast.makeText(User_quzz.this, "���ź�->_->", Toast.LENGTH_LONG).show();
    				Intent intent = new Intent();
    		        intent.setClass(User_quzz.this, Result.class);
    		        Bundle bundle = new Bundle();
    		        bundle.putString("result", String.valueOf(point));
    		        intent.putExtras(bundle);
    		        startActivity(intent);
    		      //  this.startActivityForResult(intent, 0);
    			}
        		break;
        //��ѯ
        	case R.id.category_ans_d:
        		loop = loop+1;
    			if(loop<loop_end){
    				if(Str_D.equals(Str_RightAnswer))
    				{
    					Toast.makeText(User_quzz.this, "duang~,�����~", Toast.LENGTH_LONG).show();
    					point = point+1;}
    					else
    					Toast.makeText(User_quzz.this, "���ź�->_->", Toast.LENGTH_LONG).show();
    	//			anime_delay(delay_time);
    				set_show();
    			}
    			else{
    				if(Str_D.equals(Str_RightAnswer))
    				{
    					Toast.makeText(User_quzz.this, "duang~,�����~", Toast.LENGTH_LONG).show();
    					point = point+1;}
    					else
    					Toast.makeText(User_quzz.this, "���ź�->_->", Toast.LENGTH_LONG).show();
    				Intent intent = new Intent();
    		        intent.setClass(User_quzz.this, Result.class);
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
	}                                                                                //��Щ�㲻֪������
	public void Cold_Ques_Init(){
		QuesOperate db_qo = new QuesOperate(this);
		SQLiteDatabase db = db_qo.find_account();
		db = openOrCreateDatabase("test006.db", Context.MODE_PRIVATE, null);  
		db.execSQL("DROP TABLE IF EXISTS q_ques");  
		db.execSQL("create table if not exists q_ques(_id integer primary key,question_show varchar(50),a_ans varchar(50),b_ans varchar(50),c_ans varchar(50),d_ans varchar(50),right_ans varchar(50))");
		Question[] Ques_Text_enum = new Question[50];
		
		Ques_Text_enum[0] = new Question(1,"���и��������У���Ϊ�������һ����","��ơ��ɱ�","ʯ�͡�Һ��","Na2CO3 ��10H2O��Na2CO3","ʯ��ʯ����ˮ","Na2CO3 ��10H2O��Na2CO3");
		Ques_Text_enum[1] = new Question(2,"�ַ������ڷ��ƵĹ�ͬ�ص���ǿ��","�ȼ���ϵ","���ι�ϵ","������ϵ","ѪԵ��ϵ","ѪԵ��ϵ");
		Ques_Text_enum[2] = new Question(3,"��̼�������ԏ���Դ������ȾΪ�����ľ��á��������߽Ƕȿ�����չ��̼����Ӧ����","ת�侭�÷�չ��ʽ","������������ɫ����","�ƶ�����Э����չ","����Ϊ�����ʶ�����","������������ɫ����");
		Ques_Text_enum[3] = new Question(4,"�������ʲ����ý���������ֱ�ӷ�Ӧ��ȡ����","FeCl2","FeCl3","CuCl2","AlCl3 ","FeCl2");
		Ques_Text_enum[4] = new Question(5,"�ڹ��ʵ�λ���У��涨���ȡ�������ʱ��ĵ�λ��","m��kg��s","km��N��h","m��J��h ","W��kg��s","m��kg��s");
		Ques_Text_enum[5] = new Question(6,"���һ��������������������⣬���������ķ�����","һ���Ǽ�����","һ����������","��һ���Ǽ�����","��һ����������","һ����������");
		Ques_Text_enum[6] = new Question(7,"������ΪYyRr�ĸ��岻���ܲ�����������","YR","yR","Yr","Yy","Yy");
		Ques_Text_enum[7] = new Question(8,"��ֹ��б���ϵ����壬�ܵ�����������","������֧����","������ѹ�����»�����Ħ����","������֧������Ħ����","������֧�������»�����Ħ����","������֧������Ħ����");
		Ques_Text_enum[8] = new Question(9,"������⡰P��q�����������ҡ���P���Ǽ����⣬��ô","Pһ���Ǽ�����","qһ���Ǽ�����","qһ����������","P��������������","qһ����������");
		Ques_Text_enum[9] = new Question(10,"���к����У�������(0��0.5��)��Ϊ���������Ԧ�Ϊ���ڵ���","y=sin(0.5x)","y=sin(2x)","y=-tan(x)","y=-cos(2x)","-cos(2x)");
		Ques_Text_enum[10] = new Question(11,"���ھ����˶�ʱ��������ϸ�����������Ĳ�����","���� ","�ƾ���CO2","H2O��CO2 ","�ƾ������� ","����");
		Ques_Text_enum[11] = new Question(12,"���й���0.15mol /L��Na2SO4��Һ��˵���У���ȷ����","1L��Һ�к���Na����SO42������Ϊ0.3NA","1L��Һ�к���Na����Ŀ��0.15NA ","1 L��Һ��Na����Ũ����0.3 mol / L","2 L��Һ�к���SO42����Ũ����0.3mol / L","1 L��Һ��Na����Ũ����0.3 mol / L");
		Ques_Text_enum[12] = new Question(13,"�й���������ʮ�˽�����ίԱ����Ĵ�ȫ����������","2014��10��14�� ����","2014��11��1�ձ���","2014��10��20�� ����","2014��10��5��  ����","2014��10��20�� ����");
		Ques_Text_enum[13] = new Question(14,"һʯ���¥����������. ���ƿ���������ȡg=10m/s2. ��ʯ������Ĺ�����","��1sĩ���ٶ�Ϊ1m/s","��1sĩ���ٶ�Ϊ10m/s","��1s������ĸ߶�Ϊ1m ","��1s������ĸ߶�Ϊ10m","��1sĩ���ٶ�Ϊ1m/s");
		Ques_Text_enum[14] = new Question(15,"��a��b����(0��+��)����a2+b2<1���ǡ�ab+1>a+b��������","��Ҫ�ǳ������","��ַǱ�Ҫ����","��Ҫ����","�������Ҳ�Ǳ�Ҫ����","��ַǱ�Ҫ����");
		Ques_Text_enum[15] = new Question(16,"����̬ϵͳ����ɳɷ���������̬ϵͳ�Ļ�ʯ��","�����������������","������","������","�ֽ���","������");
		Ques_Text_enum[16] = new Question(17,"�ų���ĳ��Ÿ�Ӧǿ�ȵķ�����","������ڸõ����������","С����N���ڸõ����������","�˶�����ڸõ����������","һС��ͨ��ֱ�����ڸõ����������","С����N���ڸõ����������");

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
		        intent.setClass(User_quzz.this, Result.class);
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

