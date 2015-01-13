package com.stanzione.dailyselfie;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.widget.ImageView;

public class PictureActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picture);
		
		String path = getIntent().getStringExtra("path");
		
		Bitmap bitmap = BitmapFactory.decodeFile(path);
		
		ImageView pictureView = (ImageView) findViewById(R.id.picture_show);
		pictureView.setImageBitmap(bitmap);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.picture, menu);
		return true;
	}

}
