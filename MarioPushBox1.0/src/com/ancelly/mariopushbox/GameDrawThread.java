package com.ancelly.mariopushbox;

import android.graphics.Canvas;
import android.os.Looper;
import android.view.SurfaceHolder;

/**
 * 
 * 
 * 
 * @author ancel
 * 
 * @version 2012-5-8
 */

public class GameDrawThread extends Thread {

	private int sleepSpan = 200;// 睡眠的毫秒数
	private boolean flag = false;// 循环标记位
	GameView gameView;// 游戏界面的引用
	SurfaceHolder surfaceHolder = null;
	BoxPushActivity bpa;

	public GameDrawThread(GameView gameView, SurfaceHolder surfaceHolder) {
		super();
		this.gameView = gameView;
		this.surfaceHolder = surfaceHolder;
		bpa = (BoxPushActivity)gameView.getContext();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		Looper.prepare();
		Canvas c;// 画布
		while (flag) {
			c = null;
			try {
				// 锁定整个画布，在内存要求比较高的情况下，建议参数不要为null
				c = surfaceHolder.lockCanvas(null);
				synchronized (this.surfaceHolder) {
					try {
						gameView.onDraw(c);
					} catch (Exception e) {
					}
				}
			} finally {
				if (c != null) {
					// 更新屏幕显示内容
					surfaceHolder.unlockCanvasAndPost(c);
				}
			}
			try {
				Thread.sleep(sleepSpan);// 睡眠sleepSpan毫秒
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		Looper.loop();
		
		
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

}
