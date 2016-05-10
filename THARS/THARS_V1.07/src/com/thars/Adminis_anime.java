package com.thars;

import com.thars.R;
import com.thars.question.Question;
import com.thars.question.QuesOperate;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;
/**
 * ʵ��SQLite���ݿ����ӡ�ɾ�����޸ġ���ѯ����
 * 
 */
public class Adminis_anime extends Activity implements OnClickListener{
 private static final String TAG = "qwe";
private EditText ques,anime_a,anime_b,anime_c,anime_d,anime_right_ans,anime_id;
 private ListView listview;
 private TextView datashow;
 private Button buadd,buupdate,budelete,bushow;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.anime);
		/*
		 * �������
		 */
		ques = (EditText)findViewById(R.id.anime_ques);
		anime_a = (EditText)findViewById(R.id.anime_A);
		anime_b = (EditText)findViewById(R.id.anime_B);
		anime_c = (EditText)findViewById(R.id.anime_C);
		anime_d = (EditText)findViewById(R.id.anime_D);
		anime_right_ans = (EditText)findViewById(R.id.anime_right_ans);
		anime_id = (EditText)findViewById(R.id.anime_id);
		
		listview = (ListView)findViewById(R.id.listview);
		
		datashow = (TextView)findViewById(R.id.datashow);
		
		buadd = (Button)findViewById(R.id.buadd);
		buupdate = (Button)findViewById(R.id.buupdate);
		budelete = (Button)findViewById(R.id.budelete);
		bushow = (Button)findViewById(R.id.bushow);
		
		/*
		 * ������
		 */
		buadd.setOnClickListener(this);
		buupdate.setOnClickListener(this);
		budelete.setOnClickListener(this);
		bushow.setOnClickListener(this);
	}
   public void empty(){
       ques.setText("");
       anime_a.setText("");
       anime_b.setText("");
       anime_c.setText("");
       anime_d.setText("");
       anime_right_ans.setText("");
       anime_id.setText("");
   }
	@Override
	public void onClick(View v) {
		QuesOperate quesDAO = new QuesOperate(this);
		switch (v.getId()) {
		//���
		case R.id.buadd:
			try{
			
				Question anime_ques = new Question(Integer.valueOf(anime_id.getText().toString()), 
						ques.getText().toString(), anime_a.getText().toString(), anime_b.getText().toString(), 
						anime_c.getText().toString(), anime_d.getText().toString(), anime_right_ans.getText().toString());
				quesDAO.add_ques(anime_ques);
            Toast.makeText(Adminis_anime.this, "�ɹ���ӣ�", Toast.LENGTH_LONG)  
            .show(); 
            datashow.setText("��������Ϊ��"+"\n"+"ID:"+Integer.valueOf(anime_id.getText().toString())+","+"ques:"+ques.getText().toString()
            		+","+"A:"+anime_a.getText().toString()+","+"B:"+anime_b.getText().toString()+","+"C:"+anime_c.getText().toString()
            		+","+"D:"+anime_d.getText().toString()+","+"right:"+anime_right_ans.getText().toString()
            		);
             empty();
			}catch (Exception e) {
				Log.i(TAG, e.getMessage());
			}
			break;
		//ɾ��
        case R.id.budelete:
        	try{
        		if(!anime_id.getText().toString().equals("")){
        			quesDAO.delete_ques(Integer.valueOf(anime_id.getText().toString()));
        		 Toast.makeText(Adminis_anime.this, "���ɹ�ɾ����"+Integer.valueOf(anime_id.getText().toString()), Toast.LENGTH_LONG).show();
        		 empty();
        		}else {
        			 Toast.makeText(Adminis_anime.this, "����������Ҫɾ����ID", Toast.LENGTH_LONG).show();
        		}
        		}catch (Exception e) {
        		Log.i(TAG, e.getMessage());
			}
        	break;
        //�޸�
        	case R.id.buupdate:
        		try{
        			Question anime_ques = quesDAO.find_ques(Integer.valueOf(anime_id.getText().toString()));
        			anime_ques.setQuestion_show(ques.getText().toString());
        			anime_ques.setA_ans(anime_a.getText().toString());
        			anime_ques.setB_ans(anime_b.getText().toString());
        			anime_ques.setC_ans(anime_c.getText().toString());
        			anime_ques.setD_ans(anime_d.getText().toString());
        			anime_ques.setRight_ans(anime_right_ans.getText().toString());
        			quesDAO.update_ques(anime_ques);
        			Toast.makeText(Adminis_anime.this, "�޸ĳɹ�", Toast.LENGTH_LONG).show();
                    datashow.setText("�޸ĺ�����Ϊ��"+"\n"+"ID:"+Integer.valueOf(anime_id.getText().toString())+","+"ques:"+ques.getText().toString()
                    		+","+"A:"+anime_a.getText().toString()+","+"B:"+anime_b.getText().toString()+","+"C:"+anime_c.getText().toString()
                    		+","+"D:"+anime_d.getText().toString()+","+"right:"+anime_right_ans.getText().toString()
                    		);
        			 empty();
        		}catch (Exception e) {
        			Log.i(TAG, e.getMessage());
        			Toast.makeText(Adminis_anime.this, "������", Toast.LENGTH_LONG).show();
				}
        		break;
        //��ѯ
        	case R.id.bushow:
        		try{
                   Cursor cursor = quesDAO.select_ques();
                   SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.queslistview, cursor, 
                		  new String[]{"_id","question_show","a_ans","b_ans","c_ans","d_ans","right_ans"},
                		  new int[]{R.id.ques_id,R.id.ques_ques,R.id.ques_aa,R.id.ques_bb,R.id.ques_cc,R.id.ques_dd,R.id.ques_right});
                 listview.setAdapter(adapter);
        			datashow.setText("");
        		}catch (Exception e) {
        			Log.i(TAG, e.getMessage());
        			Toast.makeText(Adminis_anime.this, "��ʾ����"+e.getMessage(), Toast.LENGTH_LONG).show();
				}
        		break;
		default:
			break;
		}
		
	}
 
}

