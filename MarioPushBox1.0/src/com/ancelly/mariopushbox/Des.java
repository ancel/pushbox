package com.ancelly.mariopushbox;

import android.graphics.Bitmap;

/** 
 
 * 
 
 * @author ancel
 
 * @version 2012-5-10
 
 */

public class Des {
	private int desX;
	private int desY;
	private Bitmap des;
	
	
	public Des(int desX, int desY, Bitmap des) {
		super();
		this.desX = desX;
		this.desY = desY;
		this.des = des;
	}
	public int getDesX() {
		return desX;
	}
	public void setDesX(int desX) {
		this.desX = desX;
	}
	public int getDesY() {
		return desY;
	}
	public void setDesY(int desY) {
		this.desY = desY;
	}
	public Bitmap getDes() {
		return des;
	}
	public void setDes(Bitmap des) {
		this.des = des;
	}
	
}
