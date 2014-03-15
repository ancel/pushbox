package com.ancelly.mariopushbox;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import cn.domob.android.ads.DomobAdEventListener;
import cn.domob.android.ads.DomobAdView;

/**
 * 
 * 
 * 
 * @author ancel
 * 
 * @version 2012-9-25
 */

public class LevelActivity extends Activity {

	private RelativeLayout mAdContainer;
	private DomobAdView mAdView320x50;
	private GridView gridview;

	private boolean musicBool;

	private MediaPlayer backPlayer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		// 全屏显示窗口
		Setting.fullScreen(this);
		
		setContentView(R.layout.levels);

		musicBool = Setting.getSetting(this, "背景音乐");
		if (musicBool) {
			backPlayer = MediaPlayer.create(this, R.raw.wulunudoshaoci);
			backPlayer.setLooping(true);
			backPlayer.start();
		}

		mAdContainer = (RelativeLayout) findViewById(R.id.adcontainer);
		mAdView320x50 = new DomobAdView(this, MainActivity.PUBLISHER_ID,
				DomobAdView.INLINE_SIZE_320X50);
		// mAdView320x50.setKeyword("game");
		// mAdView320x50.setUserGender("male");
		// mAdView320x50.setUserBirthdayStr("1990-08-11");
		// mAdView320x50.setUserPostcode("123456");
		// 设置广告view的监听器。
		mAdView320x50.setAdEventListener(new DomobAdEventListener() {

			@Override
			public void onDomobAdReturned(DomobAdView adView) {
				// TODO Auto-generated method stub
				Log.i("DomobSDKDemo", "onReceivedFreshAd");

			}

			@Override
			public void onDomobAdFailed(DomobAdView adView) {
				// TODO Auto-generated method stub
				Log.i("DomobSDKDemo", "onReceivedFailed");

			}

			@Override
			public void onDomobAdOverlayPresented(DomobAdView adView) {
				// TODO Auto-generated method stub
				Log.i("DomobSDKDemo", "overlayPresented");
			}

			@Override
			public void onDomobAdOverlayDismissed(DomobAdView adView) {
				// TODO Auto-generated method stub
				Log.i("DomobSDKDemo", "Overrided be dismissed");

			}
		});
		// 将广告View增加到视图中。
		mAdContainer.addView(mAdView320x50);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		final int passLevel = Setting.getLevel(this);
		gridview = (GridView) findViewById(R.id.gridview);
		gridview.setNumColumns(GridView.AUTO_FIT);
		gridview.setAdapter(new LevelAdapter(this, passLevel));
		gridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				if (position - passLevel < 1) {
					Intent it = new Intent(LevelActivity.this,
							BoxPushActivity.class);
					Bundle bundle = new Bundle();
					bundle.putInt("level", position);
					it.putExtras(bundle);
					startActivity(it);
					if (5 <= MainActivity.SDK_VERSION) {
						overridePendingTransition(R.anim.push_up_in,
								R.anim.push_up_out);
					}
				} else {
					Toast.makeText(LevelActivity.this, "该关卡未解锁!",
							Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// Intent it = new Intent(BoxPushActivity.this,
			// LevelActivity.class);
			// startActivity(it);
			closeMusic();
			finish();
			overridePendingTransition(R.anim.slide_up_in, R.anim.slide_down_out);
		}
		return true;
	}

	private void closeMusic() {
		if (backPlayer != null) {
			backPlayer.stop();
		}
	}
}
