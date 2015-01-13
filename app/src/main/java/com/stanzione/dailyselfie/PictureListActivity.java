package com.stanzione.dailyselfie;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.AlarmManager;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class PictureListActivity extends ListActivity {
	
	static final int REQUEST_TAKE_PHOTO = 1;
	static final String APP_PATH = "/DailySelfie";
	PictureAdapter adapter;
	
	String timeStamp;
	String path;
	
	private Intent mNotificationReceiverIntent;
	private PendingIntent mNotificationReceiverPendingIntent;
	private static int TWO_MIN = 1000 * 60 * 2;
	
	private AlarmManager alarmManager;
	
	private static final String TAG = PictureListActivity.class.getSimpleName();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		adapter = new PictureAdapter(getApplicationContext());
//		adapter.removeAll();
		
		setListAdapter(adapter);
		adapter.addAll();
		
		setAlarm();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		switch (item.getItemId()) {
		case R.id.take_photo:
			
			startPhotoApp();
			return true;
			
		case R.id.cancel_alarm:
			
			cancelAlarm();
			return true;

		default:

			return super.onOptionsItemSelected(item);
		}
		
	}
	
	public void startPhotoApp(){
		
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
	    // Ensure that there's a camera activity to handle the intent
	    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
	        // Create the File where the photo should go
	        File photoFile = null;
	        try {
	            photoFile = createImageFile();
	        } catch (IOException ex) {
	            // Error occurred while creating the File
	            System.out.println("Error while creating photo file");
	        }
	        // Continue only if the File was successfully created
	        if (photoFile != null) {
	            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
	                    Uri.fromFile(photoFile));
	            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
	        }
	        else{
	        	System.out.println("photoFile was not created properly");
	        }
	    }
		
	}
	
	private File createImageFile() throws IOException {
	    // Create an image file name
	    timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    File storageDir = Environment.getExternalStoragePublicDirectory(
	            Environment.DIRECTORY_PICTURES + APP_PATH);
	    
	    File image = new File(storageDir, timeStamp + ".jpg");
	    
	    Log.d(TAG, "storageDir: " + storageDir.getAbsolutePath());
	    Log.d(TAG, String.valueOf(storageDir.exists()));
	    
	    if(!storageDir.exists()){
	    	storageDir.mkdirs();
	    }
	    
	    Log.d(TAG, "image: " + image.getAbsolutePath());
	    Log.d(TAG, String.valueOf(image.exists()));
	    
	    path = image.getAbsolutePath();
	    
	    return image;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if(requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK){
			
			File f = new File(path);
			System.out.println(f.exists());
			System.out.println(f.length());
			
	        PictureRecord newPic = new PictureRecord(null, timeStamp, path);
	        adapter.add(newPic);
	        
		}
		
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		
		PictureRecord pictureSelected = (PictureRecord) l.getAdapter().getItem(position);
		System.out.println(pictureSelected.getPath());
		System.out.println(pictureSelected.getTimestamp());
		
		Intent intent = new Intent(getApplicationContext(), PictureActivity.class);
		intent.putExtra("path", pictureSelected.getPath());
		
		startActivity(intent);
		
	}
	
	private void setAlarm(){
		
		mNotificationReceiverIntent = new Intent(getApplicationContext(),
				DailySelfieNotificationReceiver.class);
		
		mNotificationReceiverPendingIntent = 
				PendingIntent.getBroadcast(getApplicationContext(), 0, mNotificationReceiverIntent, 0);
		
		alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
		
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + TWO_MIN,
				TWO_MIN, mNotificationReceiverPendingIntent);
		
	}
	
	private void cancelAlarm(){
		
		if(alarmManager != null){
			alarmManager.cancel(mNotificationReceiverPendingIntent);
		}
		
	}
	
}
