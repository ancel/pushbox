package com.ancelly.mariopushbox;

import com.ancelly.mariopushbox.R;

import cn.domob.android.ads.DomobUpdater;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

/**
 * 
 * 
 * 
 * @author ancel
 * 
 * @version 2012-9-25
 */

public class MainActivity extends Activity implements OnClickListener {
	public static final String PUBLISHER_ID = "56OJzilIuMhVSgWA1d";

	public static final int SDK_VERSION = Integer
			.valueOf(android.os.Build.VERSION.SDK);
	private ImageButton start;
	private ImageButton setting;
	private ImageButton exit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// 全屏显示窗口
		Setting.fullScreen(this);

		setContentView(R.layout.main);

		start = (ImageButton) findViewById(R.id.start);
		// start.setBackgroundResource(R.drawable.start);
		setting = (ImageButton) findViewById(R.id.setting);

		exit = (ImageButton) findViewById(R.id.exit);
		start.setOnClickListener(this);
		setting.setOnClickListener(this);
		exit.setOnClickListener(this);
		DomobUpdater.checkUpdate(this, PUBLISHER_ID);
	
		//Setting.saveLevel(this, 25);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.start:
			Intent it = new Intent(MainActivity.this, LevelActivity.class);
			startActivity(it);
			if (5 <= MainActivity.SDK_VERSION) {
				overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
			}

			break;
		case R.id.setting:
			boolean[] checkedItems = {
					Setting.getSetting(this, Setting.items[0].toString()),
					Setting.getSetting(this, Setting.items[1].toString()) };
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("设置");
			builder.setIcon(R.drawable.mariohead);
			builder.setMultiChoiceItems(Setting.items, checkedItems,
					new OnMultiChoiceClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which,
								boolean isChecked) {
							// TODO Auto-generated method stub
							Setting.saveSetting(MainActivity.this,
									Setting.items[which].toString(), isChecked);
						}

					});
			builder.create().show();
			break;
		case R.id.exit:
			exit();
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			exit();
		}
		return true;
	}

	private void exit() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage("您现在真的想要离开我吗？");
		builder.setTitle("退出");
		builder.setIcon(R.drawable.mushroomcry);
		builder.setPositiveButton("嗯",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						arg0.dismiss();
						android.os.Process.killProcess(android.os.Process
								.myPid());
						finish();

					}
				});
		builder.setNegativeButton("不嗯",
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();
	}

}
