package com.thars.chuangguan;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.*;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.thars.flip.FlipViewGroup;
import com.thars.R;
import com.thars.Selection;

public class Information extends Activity {
	private FlipViewGroup contentView;

	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle(R.string.activity_title);

		contentView = new FlipViewGroup(this);

		contentView.addFlipView(View.inflate(this, R.layout.second_page, null));
		contentView.addFlipView(View.inflate(this, R.layout.first_page, null));

		setContentView(contentView);

		contentView.startFlipping(); //make the first_page view flipping
		
		
	}
	class inforimg implements OnClickListener{
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
	        intent.setClass(Information.this, First.class);
	        startActivity(intent);
			
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent = new Intent(
			Intent.ACTION_VIEW, 
			Uri.parse("http://openaphid.github.com/blog/2012/05/21/how-to-implement-flipboard-animation-on-android/")
		);
		startActivity(intent);

		return true;
	}

	@Override
	protected void onResume() {
		super.onResume();
		contentView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		contentView.onPause();
	}
}
