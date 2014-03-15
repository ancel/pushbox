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
		return dm.widthPixels; // 当前分辨率 宽度
	}
	public static int getScreenHeight(Activity context){
		context.getWindowManager().getDefaultDisplay().getMetrics(dm);
		return dm.heightPixels; // 当前分辨率高度
	}
}
