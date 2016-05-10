package com.thars;

import com.thars.R;
import com.thars.question.User;
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
 public class Adminis_user extends Activity implements OnClickListener{
 private static final String TAG = "err";
 private EditText edname,edpass;
 private ListView listview;
 private TextView datashow;
 private Button buadd,buupdate,budelete,bushow;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		/*
		 * �������
		 */
		edname = (EditText)findViewById(R.id.edname);
		edpass = (EditText)findViewById(R.id.edpass);
		
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
       edname.setText("");
       edpass.setText("");
   }
	@Override
	public void onClick(View v) {
		QuesOperate userDAO = new QuesOperate(this);
		switch (v.getId()) {
		//���
		case R.id.buadd:
			try{
			
			User user = new User(edname.getText().toString(), edpass.getText().toString());
			userDAO.add(user);
            Toast.makeText(Adminis_user.this, "�ɹ���ӣ�", Toast.LENGTH_LONG)  
            .show(); 
            datashow.setText("��������Ϊ��"+"\n"+"����:"+edname.getText().toString()
            		+","+"����:"+edpass.getText().toString()
            		);
             empty();
			}catch (Exception e) {
				Log.i(TAG, e.getMessage());
			}
			break;
		//ɾ��
        case R.id.budelete:
        	try{
        		if(!edname.getText().toString().equals("")){
        		userDAO.delete(edname.getText().toString());
        		 Toast.makeText(Adminis_user.this, "���ɹ�ɾ����"+edname.getText().toString(), Toast.LENGTH_LONG).show();
        		 empty();
        		}else {
        			 Toast.makeText(Adminis_user.this, "����������Ҫɾ����name", Toast.LENGTH_LONG).show();
        		}
        		}catch (Exception e) {
        		Log.i(TAG, e.getMessage());
			}
        	break;
        //�޸�
        	case R.id.buupdate:
        		try{
        			User user = userDAO.find(edname.getText().toString());
        			user.setUname(edname.getText().toString());
        			user.setUpassword(edpass.getText().toString());
        			userDAO.update(user);
        			Toast.makeText(Adminis_user.this, "�޸ĳɹ�", Toast.LENGTH_LONG).show();
                    datashow.setText("�޸ĺ�����Ϊ��"+"\n"+"����:"+edname.getText().toString()
                    		+","+"����:"+edpass.getText().toString()
                    		);
        			 empty();
        		}catch (Exception e) {
        			Log.i(TAG, e.getMessage());
        			Toast.makeText(Adminis_user.this, "������", Toast.LENGTH_LONG).show();
				}
        		break;
        //��ѯ
        	case R.id.bushow:
        		try{
                   Cursor cursor = userDAO.select();
                   SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.listview, cursor, 
                		  new String[]{"uname","upassword"},
                		  new int[]{R.id.tvuname,R.id.tvupass});
                 listview.setAdapter(adapter);
        			datashow.setText("");
        		}catch (Exception e) {
        			Log.i(TAG, e.getMessage());
        			Toast.makeText(Adminis_user.this, "��ʾ����"+e.getMessage(), Toast.LENGTH_LONG).show();
				}
        		break;
		default:
			break;
		}
		
	}
 
}

