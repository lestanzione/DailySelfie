package com.stanzione.dailyselfie;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PictureAdapter extends BaseAdapter{
	
	private ArrayList<PictureRecord> listPictures = new ArrayList<PictureRecord>();
	private static LayoutInflater inflater;
	private Context context;
	
	private static final String TAG = PictureAdapter.class.getSimpleName();
	
	static final String APP_PATH = "/DailySelfie";
	
	public PictureAdapter(Context context){
		this.context = context;
		inflater = LayoutInflater.from(this.context);
	}
	
	@Override
	public int getCount() {
		return listPictures.size();
	}

	@Override
	public Object getItem(int position) {
		return listPictures.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View newView = convertView;
		ViewHolder holder;

		PictureRecord curr = listPictures.get(position);

		if (null == convertView) {
			holder = new ViewHolder();
			newView = inflater.inflate(R.layout.picture_record_view, null);
			holder.picture = (ImageView) newView.findViewById(R.id.picture);
			holder.timestamp = (TextView) newView.findViewById(R.id.timestamp);
			newView.setTag(holder);
			
		} else {
			holder = (ViewHolder) newView.getTag();
		}

//		holder.picture.setImageBitmap(curr.getData());
		holder.picture.setImageBitmap(setPic(curr.getPath()));
		holder.timestamp.setText(curr.getTimestamp());

		return newView;
		
	}
	
	static class ViewHolder {
		ImageView picture;
		TextView timestamp;	
	}
	
	private Bitmap setPic(String path) {
	    // Get the dimensions of the View
	    int targetW = 100;
	    int targetH = 75;

	    // Get the dimensions of the bitmap
	    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
	    bmOptions.inJustDecodeBounds = true;
	    BitmapFactory.decodeFile(path, bmOptions);
	    int photoW = bmOptions.outWidth;
	    int photoH = bmOptions.outHeight;

	    // Determine how much to scale down the image
	    int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

	    // Decode the image file into a Bitmap sized to fill the View
	    bmOptions.inJustDecodeBounds = false;
	    bmOptions.inSampleSize = scaleFactor;
	    bmOptions.inPurgeable = true;

	    Bitmap bitmap = BitmapFactory.decodeFile(path, bmOptions);
	    return bitmap;
	}
	
	public void add(PictureRecord pictureRecord){
		listPictures.add(pictureRecord);
		this.notifyDataSetChanged();
	}
	
	public void addAll(){
		File storageDir = Environment.getExternalStoragePublicDirectory(
	            Environment.DIRECTORY_PICTURES + APP_PATH);
		
		File[] images = storageDir.listFiles();

        if(images != null) {
            Log.d(TAG, "images.length: " + images.length);

            for(File image : images){
                String path = image.getAbsolutePath();
                String timeStamp = image.getName().split("\\.")[0];
                PictureRecord pic = new PictureRecord(null, timeStamp, path);
                this.add(pic);
            }

        }
		
	}
	
	public void removeAll(){
		File storageDir = Environment.getExternalStoragePublicDirectory(
	            Environment.DIRECTORY_PICTURES + APP_PATH);
		
		File[] images = storageDir.listFiles();
		
		Log.d(TAG, "images.length: " + images.length);
		
		for(File image : images){
			image.delete();
		}
		
		this.listPictures.clear();
		this.notifyDataSetChanged();
		
	}

}
