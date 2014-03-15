package com.ancelly.mariopushbox;

import android.graphics.Bitmap;

/** 
 
 * 
 
 * @author ancel
 
 * @version 2012-5-8
 
 */

public class Mario {
	//�˵Ŀ������
	private int lumpX;
	private int lumpY;
	
	//������Ƭ
	private Bitmap photo;
	public Mario(int lumpX, int lumpY, Bitmap photo) {
		super();
		this.lumpX = lumpX;
		this.lumpY = lumpY;
		this.photo = photo;
	}
	public Bitmap getPhoto() {
		return photo;
	}
	public void setPhoto(Bitmap photo) {
		this.photo = photo;
	}
	public int getLumpX() {
		return lumpX;
	}
	public void setLumpX(int lumpX) {
		this.lumpX = lumpX;
	}
	public int getLumpY() {
		return lumpY;
	}
	public void setLumpY(int lumpY) {
		this.lumpY = lumpY;
	}
	
	
}
