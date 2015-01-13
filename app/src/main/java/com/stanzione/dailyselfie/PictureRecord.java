package com.stanzione.dailyselfie;

import android.graphics.Bitmap;

public class PictureRecord {
	
	private Bitmap data;
	private String timestamp;
	private String path;
	
	public PictureRecord(Bitmap data, String timestamp, String path){
		this.data = data;
		this.timestamp = timestamp;
		this.path = path;
	}
	
	public PictureRecord(){}

	public Bitmap getData() {
		return data;
	}

	public void setData(Bitmap data) {
		this.data = data;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
