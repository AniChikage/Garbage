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
 * 实现SQLite数据库增加、删除、修改、查询操作
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
		 * 引入组件
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
		 * 监听器
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
		//添加
		case R.id.buadd:
			try{
			
			User user = new User(edname.getText().toString(), edpass.getText().toString());
			userDAO.add(user);
            Toast.makeText(Adminis_user.this, "成功添加！", Toast.LENGTH_LONG)  
            .show(); 
            datashow.setText("新添数据为："+"\n"+"姓名:"+edname.getText().toString()
            		+","+"密码:"+edpass.getText().toString()
            		);
             empty();
			}catch (Exception e) {
				Log.i(TAG, e.getMessage());
			}
			break;
		//删除
        case R.id.budelete:
        	try{
        		if(!edname.getText().toString().equals("")){
        		userDAO.delete(edname.getText().toString());
        		 Toast.makeText(Adminis_user.this, "您成功删除了"+edname.getText().toString(), Toast.LENGTH_LONG).show();
        		 empty();
        		}else {
        			 Toast.makeText(Adminis_user.this, "请输入你想要删除的name", Toast.LENGTH_LONG).show();
        		}
        		}catch (Exception e) {
        		Log.i(TAG, e.getMessage());
			}
        	break;
        //修改
        	case R.id.buupdate:
        		try{
        			User user = userDAO.find(edname.getText().toString());
        			user.setUname(edname.getText().toString());
        			user.setUpassword(edpass.getText().toString());
        			userDAO.update(user);
        			Toast.makeText(Adminis_user.this, "修改成功", Toast.LENGTH_LONG).show();
                    datashow.setText("修改后数据为："+"\n"+"姓名:"+edname.getText().toString()
                    		+","+"密码:"+edpass.getText().toString()
                    		);
        			 empty();
        		}catch (Exception e) {
        			Log.i(TAG, e.getMessage());
        			Toast.makeText(Adminis_user.this, "出错了", Toast.LENGTH_LONG).show();
				}
        		break;
        //查询
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
        			Toast.makeText(Adminis_user.this, "显示不了"+e.getMessage(), Toast.LENGTH_LONG).show();
				}
        		break;
		default:
			break;
		}
		
	}
 
}

