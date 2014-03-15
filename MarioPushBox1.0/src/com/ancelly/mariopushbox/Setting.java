package com.ancelly.mariopushbox;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Window;
import android.view.WindowManager;

/** 
 
 * 
 
 * @author ancel
 
 * @version 2012-10-5
 
 */

public class Setting {
	public static final CharSequence[] items = { "背景音乐", "声音"};


	/*
	 * 获取游戏设置
	 */
	public static boolean getSetting(Context con,String key){
		SharedPreferences sharedPreferences = con.getSharedPreferences("conf",
				Context.MODE_PRIVATE);
		// getInt()第二个参数为缺省值，如果preference中不存在该key，将返回缺省值
		return sharedPreferences.getBoolean(key, true);
	}
	
	/*
	 * 保存游戏设置
	 */
	public static void saveSetting(Context con,String key,boolean value){
		SharedPreferences sharedPreferences = con.getSharedPreferences("conf", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();// 获取编辑器
		editor.putBoolean(key, value);
		editor.commit();// 提交修改

	}
	
	/*
	 * 保存已过关卡
	 */
	public static void saveLevel(Context con,int level){
		SharedPreferences sharedPreferences = con.getSharedPreferences("conf", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();// 获取编辑器
		editor.putInt("level",level);
		editor.commit();// 提交修改
	}
	
	/*
	 * 获取已过关卡
	 */
	public static int getLevel(Context con){
		SharedPreferences sharedPreferences = con.getSharedPreferences("conf",
				Context.MODE_PRIVATE);
		// getInt()第二个参数为缺省值，如果preference中不存在该key，将返回缺省值
		return sharedPreferences.getInt("level", 0);
		
	}
	
	/*
	 * 获取最好成绩
	 */
	public static String getBestGrade(Context con,String level){
		SharedPreferences sharedPreferences = con.getSharedPreferences("grade",
				Context.MODE_PRIVATE);
		// getInt()第二个参数为缺省值，如果preference中不存在该key，将返回缺省值
		return sharedPreferences.getString(level, "0");
		
	}
	
	/*
	 * 保存最好成绩
	 */
	public static void setBestGrade(Context con,String level,String bestGrade){
		SharedPreferences sharedPreferences = con.getSharedPreferences("grade", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();// 获取编辑器
		editor.putString(level, bestGrade);
		editor.commit();// 提交修改

	}
	
	/*
	 * 全屏
	 */
	public static void fullScreen(Activity context){
		context.requestWindowFeature(Window.FEATURE_NO_TITLE);

		context.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	
}
