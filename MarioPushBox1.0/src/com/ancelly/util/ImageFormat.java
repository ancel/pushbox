package com.ancelly.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * 
 * 
 * 
 * @author ancel
 * 
 * @version 2012-5-8
 */

public class ImageFormat {
	
	private Bitmap srcBitmap;	
	private int imageWidth;
	private int imageHeight;
	private float scaleWidth;
	private float scaleHeight;
	// ��������ͼƬ�õ�matrix����
	private Matrix matrix = new Matrix();
	
	
	public ImageFormat(Bitmap srcBitmap, float newWidth , float newHeight ) {
		super();
		this.srcBitmap = srcBitmap;
		imageWidth = srcBitmap.getWidth();
		imageHeight = srcBitmap.getHeight();
		this.scaleWidth = ((float) newWidth) / imageWidth;
		this.scaleHeight = ((float) newHeight) / imageHeight; 
	}

	public Bitmap getDescBitmap() {
		// ����ͼƬ����
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap descBitmap = Bitmap.createBitmap(srcBitmap, 0, 0,   
				imageWidth, imageHeight, matrix, true);   

		return descBitmap;
	}

}
