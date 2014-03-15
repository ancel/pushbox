package com.ancelly.util;

import android.app.Activity;
import android.util.DisplayMetrics;



/**
 * 
 * 
 * 
 * @author ancel
 * 
 * @version 2012-5-8
 */

public class Util{
	public static DisplayMetrics dm = new DisplayMetrics();
	public static int getScreenWidth(Activity context){
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.widthPixels; // ��ǰ�ֱ��� ���
	}
	public static int getScreenHeight(Activity context){
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels; // ��ǰ�ֱ��ʸ߶�
	}
}
