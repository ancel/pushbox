package com.ancelly.mariopushbox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;

public class BoxPushActivity extends Activity {

	private GameView gameView;
	private MyHandler myHandler;

	private TextView bestGradeView;
	private TextView nowGradeView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// 全屏显示窗口
		Setting.fullScreen(this);

		int level = this.getIntent().getExtras().getInt("level");

		setContentView(R.layout.game);

		bestGradeView = (TextView) findViewById(R.id.bestgrade);
		bestGradeView.setText(Setting.getBestGrade(this, String.valueOf(level)));
		
		nowGradeView = (TextView) findViewById(R.id.nowgrade);
		gameView = (GameView) findViewById(R.id.view3d);
		gameView.init(this, level,nowGradeView);
		gameView.startGame();
		myHandler = new MyHandler();

	}

	/**
	 * 
	 * 接受消息,处理消息 ,此Handler会与当前主线程一块运行
	 * 
	 * */

	class MyHandler extends Handler {

		public MyHandler() {

		}

		public MyHandler(Looper L) {

			super(L);

		}

		// 子类必须重写此方法,接受数据

		@Override
		public void handleMessage(Message msg) {

			// TODO Auto-generated method stub

			Log.d("MyHandler", "handleMessage......");

			super.handleMessage(msg);

			// 此处可以更新UI
			Bundle b = msg.getData();
			String command = b.getString("command");
			if (command.equalsIgnoreCase("next")) {
				if (Setting.getLevel(BoxPushActivity.this) < MapList.pushbox_map.length) {
					bestGradeView.setText(Setting.getBestGrade(BoxPushActivity.this, String.valueOf(gameView.getLevel() + 1)));
					gameView.setLevel(gameView.getLevel() + 1);
					gameView.createMap();
					gameView.startGame();
				} else {
					Intent it = new Intent(BoxPushActivity.this,
							LevelActivity.class);
					startActivity(it);
				}

			} else if (command.equalsIgnoreCase("back")) {
				closeMusic();
				finish();
				overridePendingTransition(R.anim.slide_up_in,
						R.anim.slide_down_out);
				// System.exit(0);
			}
		}

	}

	public MyHandler getMyHandler() {
		return myHandler;
	}

	public void setMyHandler(MyHandler myHandler) {
		this.myHandler = myHandler;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			// Intent it = new Intent(BoxPushActivity.this,
			// LevelActivity.class);
			// startActivity(it);
			// finish();
			closeMusic();
			gameView.getGameDrawThread().setFlag(false);
			finish();
			overridePendingTransition(R.anim.slide_up_in, R.anim.slide_down_out);
		}
		return true;
	}


	private void closeMusic() {
		if (gameView.getHitPlayer() != null) {
			gameView.getHitPlayer().stop();
		}
	}

}