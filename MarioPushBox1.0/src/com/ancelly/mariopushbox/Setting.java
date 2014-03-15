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
	public static final CharSequence[] items = { "��������", "����"};


	/*
	 * ��ȡ��Ϸ����
	 */
	public static boolean getSetting(Context con,String key){
		SharedPreferences sharedPreferences = con.getSharedPreferences("conf",
				Context.MODE_PRIVATE);
		// getInt()�ڶ�������Ϊȱʡֵ�����preference�в����ڸ�key��������ȱʡֵ
		return sharedPreferences.getBoolean(key, true);
	}
	
	/*
	 * ������Ϸ����
	 */
	public static void saveSetting(Context con,String key,boolean value){
		SharedPreferences sharedPreferences = con.getSharedPreferences("conf", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();// ��ȡ�༭��
		editor.putBoolean(key, value);
		editor.commit();// �ύ�޸�

	}
	
	/*
	 * �����ѹ��ؿ�
	 */
	public static void saveLevel(Context con,int level){
		SharedPreferences sharedPreferences = con.getSharedPreferences("conf", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();// ��ȡ�༭��
		editor.putInt("level",level);
		editor.commit();// �ύ�޸�
	}
	
	/*
	 * ��ȡ�ѹ��ؿ�
	 */
	public static int getLevel(Context con){
		SharedPreferences sharedPreferences = con.getSharedPreferences("conf",
				Context.MODE_PRIVATE);
		// getInt()�ڶ�������Ϊȱʡֵ�����preference�в����ڸ�key��������ȱʡֵ
		return sharedPreferences.getInt("level", 0);
		
	}
	
	/*
	 * ��ȡ��óɼ�
	 */
	public static String getBestGrade(Context con,String level){
		SharedPreferences sharedPreferences = con.getSharedPreferences("grade",
				Context.MODE_PRIVATE);
		// getInt()�ڶ�������Ϊȱʡֵ�����preference�в����ڸ�key��������ȱʡֵ
		return sharedPreferences.getString(level, "0");
		
	}
	
	/*
	 * ������óɼ�
	 */
	public static void setBestGrade(Context con,String level,String bestGrade){
		SharedPreferences sharedPreferences = con.getSharedPreferences("grade", Context.MODE_PRIVATE);
		Editor editor = sharedPreferences.edit();// ��ȡ�༭��
		editor.putString(level, bestGrade);
		editor.commit();// �ύ�޸�

	}
	
	/*
	 * ȫ��
	 */
	public static void fullScreen(Activity context){
		context.requestWindowFeature(Window.FEATURE_NO_TITLE);

		context.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}
	
}
