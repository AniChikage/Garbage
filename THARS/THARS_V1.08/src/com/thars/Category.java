package com.thars;


import com.thars.R;
import com.thars.question.LearnCate;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class Category extends Activity {

	private Button btn_anime,btn_computer,btn_cold,btn_culture,btn_literature;
	private Button btn_other,btn_quzz,btn_sports,btn_video,btn_world;
	private Button btn_setting;
	//private int time;
	public LearnCate learncate;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.category);
		
		btn_anime = (Button)findViewById(R.id.category_anime);
		btn_computer = (Button)findViewById(R.id.category_computer);
		btn_cold = (Button)findViewById(R.id.category_cold);
		btn_culture = (Button)findViewById(R.id.category_culture);
		btn_literature = (Button)findViewById(R.id.category_literature);
		btn_other = (Button)findViewById(R.id.category_other);
		btn_quzz = (Button)findViewById(R.id.category_quzz);
		btn_sports = (Button)findViewById(R.id.category_sports);
		btn_video = (Button)findViewById(R.id.category_video);
		btn_world = (Button)findViewById(R.id.category_world);
		btn_setting = (Button)findViewById(R.id.category_setting);
		
		btn_anime.setOnClickListener(new category_anime_OnClickListener());
		btn_computer.setOnClickListener(new category_computer_OnClickListener());
		btn_cold.setOnClickListener(new category_cold_OnClickListener());
		btn_culture.setOnClickListener(new category_culture_OnClickListener());
		btn_literature.setOnClickListener(new category_literature_OnClickListener());
		btn_other.setOnClickListener(new category_other_OnClickListener());
		btn_quzz.setOnClickListener(new category_quzz_OnClickListener());
		btn_sports.setOnClickListener(new category_sports_OnClickListener());
		btn_video.setOnClickListener(new category_video_OnClickListener());
		btn_world.setOnClickListener(new category_world_OnClickListener());
		btn_setting.setOnClickListener(new category_setting_OnClickListener());

	}
	
	//buttonÊÂ¼þ
	class category_setting_OnClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
	        intent.setClass(Category.this, Setting.class);
	        startActivity(intent);
			
		}
	}
	
	class category_anime_OnClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
	        intent.setClass(Category.this, User_anime.class);
	        startActivity(intent);
			learncate.setCate("anime");
		};
	}
	
	class category_computer_OnClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
	        intent.setClass(Category.this, User_computer.class);
	        startActivity(intent);
	        learncate.setCate("computer");
		}
	}
	class category_cold_OnClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
	        intent.setClass(Category.this, User_cold.class);
	        startActivity(intent);
	        learncate.setCate("cold");
		}
	}
	class category_culture_OnClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
	        intent.setClass(Category.this, User_culture.class);
	        startActivity(intent);
	        learncate.setCate("culture");
		}
	}
	class category_literature_OnClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
	        intent.setClass(Category.this, User_literature.class);
	        startActivity(intent);
	        learncate.setCate("literature");
		}
	}
	class category_other_OnClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
	        intent.setClass(Category.this, User_other.class);
	        startActivity(intent);
	        learncate.setCate("other");
		}
	}
	class category_quzz_OnClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
	        intent.setClass(Category.this, User_quzz.class);
	        startActivity(intent);
	        learncate.setCate("quzz");
		}
	}
	class category_sports_OnClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
	        intent.setClass(Category.this, User_sports.class);
	        startActivity(intent);
	        learncate.setCate("sports");
		}
	}class category_video_OnClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
	        intent.setClass(Category.this, User_video.class);
	        startActivity(intent);
	        learncate.setCate("video");
		}
	}class category_world_OnClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
	        intent.setClass(Category.this, User_world.class);
	        startActivity(intent);
	        learncate.setCate("world");
		}
	}
}
