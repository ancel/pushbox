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

	private int sleepSpan = 200;// ˯�ߵĺ�����
	private boolean flag = false;// ѭ�����λ
	GameView gameView;// ��Ϸ���������
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
		Canvas c;// ����
		while (flag) {
			c = null;
			try {
				// �����������������ڴ�Ҫ��Ƚϸߵ�����£����������ҪΪnull
				c = surfaceHolder.lockCanvas(null);
				synchronized (this.surfaceHolder) {
					try {
						gameView.onDraw(c);
					} catch (Exception e) {
					}
				}
			} finally {
				if (c != null) {
					// ������Ļ��ʾ����
					surfaceHolder.unlockCanvasAndPost(c);
				}
			}
			try {
				Thread.sleep(sleepSpan);// ˯��sleepSpan����
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
