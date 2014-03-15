package com.ancelly.mariopushbox;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.ancelly.util.ImageFormat;
import com.ancelly.util.Util;

/**
 * 
 * 
 * 
 * @author ancel
 * 
 * @version 2012-5-6
 */

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

	// View分辨率
	private int nowWidth;
	private int nowHeigth;

	// person
	private Mario person;

	// 块的边长
	private int LumpSideLength;
	// button边长
	private int buttonLength;

	private int initX = 0;
	private int initY = 0;
	private Bitmap floor;
	private Bitmap des;
	private Bitmap mario;
	private Bitmap redmushroom;
	private Bitmap bluemushroom;
	private Bitmap wall;

	// 上下左右图片按钮
	private Bitmap up;
	private Bitmap down;
	private Bitmap left;
	private Bitmap right;

	// 返回上一步
	private Bitmap back;
	// 刷新
	private Bitmap refresh;
	// 绘制地图线程
	private GameDrawThread gameDrawThread;
	// 关卡地图
	private int currentMap[][][];
	// 地图数组
	private int map0[][];
	private int map1[][];
	// 目的地数组
	private List<Des> desList;
	private List<int[][]> mapList;
	// 关卡
	private int level;
	private Paint paint;
	private SurfaceHolder holder;

	// 控制按钮的初始化像素坐标
	private float buttonX;
	private float buttonY;

	// 碰撞音乐播放器
	private MediaPlayer hitPlayer;
	//成功音乐播放器
	private MediaPlayer successPlayer;
	private boolean voiceBool;
	

	// 标志是否成功
	private boolean isSuccess;

	// 步数
	private int stepNum;
	// TextView 记录步数
	private TextView nowGradeView;

	public GameView(Activity context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public GameView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public void init(Activity context, int level,
			TextView textView) {
		// this.setcontext = context;
		nowGradeView = textView;
		voiceBool = Setting.getSetting(this.getContext(), "声音");
		if (voiceBool) {
			hitPlayer = MediaPlayer.create(this.getContext(), R.raw.hit);
			successPlayer = MediaPlayer.create(this.getContext(), R.raw.haliluya);
		}
		holder = this.getHolder();
		holder.addCallback(this);

		nowWidth = Util.getScreenWidth(context); // 当前分辨率 宽度
		nowHeigth = Util.getScreenHeight(context) - 30; // 当前分辨率高度,30为字体高度

		this.level = level;
		createMap();
	}

	/*
	 * 构造地图
	 */
	public void createMap() {
		currentMap = MapList.pushbox_map[level];
		calculateLength();

		desList = new ArrayList<Des>();
		mapList = new ArrayList<int[][]>();

		// 初始化游戏图片
		initBitMap();
		initMap();

	}

	// 计算图片边长和button边长
	private void calculateLength() {
		if (nowHeigth * 2 / 3 / currentMap[0].length < nowWidth
				/ currentMap[0][0].length) {
			LumpSideLength = nowHeigth * 2 / 3 / currentMap[0].length;
			initX = (nowWidth - LumpSideLength * currentMap[0][0].length) / 2;
			initY = 0;
		} else {
			LumpSideLength = nowWidth / currentMap[0][0].length;
			initY = (nowHeigth * 2 / 3 - LumpSideLength * currentMap[0].length) / 2;
			initX = 0;
		}
		buttonLength = nowHeigth / 3 / 3;
		buttonX = (nowWidth - buttonLength * 3) / 2;
		buttonY = nowHeigth * 2 / 3;
	}

	/*
	 * 启动游戏线程，相当于启动游戏
	 */
	public void startGame() {
		// 绘制地图线程
		gameDrawThread = new GameDrawThread(this, getHolder());
		gameDrawThread.setFlag(true);
		gameDrawThread.start();
	}

	/*
	 * 初始化地图数组
	 */
	public void initMap() {
		map0 = new int[currentMap[0].length][currentMap[0][0].length];
		System.out
				.println(currentMap[0][0].length + "," + currentMap[0].length);
		for (int i = 0; i < currentMap[0].length; i++) {
			for (int j = 0; j < currentMap[0][0].length; j++) {
				map0[i][j] = currentMap[0][i][j];
				// 判断是否是目的地
				if (map0[i][j] == 6) {
					Des dest = new Des(i, j, des);
					desList.add(dest);
				}
			}
		}
		map1 = new int[currentMap[1].length][currentMap[1][0].length];
		for (int i = 0; i < currentMap[1].length; i++) {
			for (int j = 0; j < currentMap[1][0].length; j++) {
				map1[i][j] = currentMap[1][i][j];
				if (map1[i][j] == 7) {
					person = new Mario(j, i, mario);
				}

			}
		}
		mapList.clear();

		stepNum = 0;
		nowGradeView.setText(String.valueOf(stepNum));
	}

	/*
	 * 初始化地图图片
	 */
	public void initBitMap() {
		floor = getBitmap(BitmapFactory.decodeResource(getResources(),
				R.drawable.floor));
		des = getBitmap(BitmapFactory.decodeResource(getResources(),
				R.drawable.des));
		mario = getBitmap(BitmapFactory.decodeResource(getResources(),
				R.drawable.mario));


		redmushroom = getBitmap(BitmapFactory.decodeResource(getResources(),
				R.drawable.redmushroom));
		bluemushroom = getBitmap(BitmapFactory.decodeResource(getResources(),
				R.drawable.bluemushroom));
		wall = getBitmap(BitmapFactory.decodeResource(getResources(),
				R.drawable.wall));

		up = getButtonBitmap(BitmapFactory.decodeResource(getResources(),
				R.drawable.up));
		down = getButtonBitmap(BitmapFactory.decodeResource(getResources(),
				R.drawable.down));
		right = getButtonBitmap(BitmapFactory.decodeResource(getResources(),
				R.drawable.right));
		left = getButtonBitmap(BitmapFactory.decodeResource(getResources(),
				R.drawable.left));

		back = getButtonBitmap(BitmapFactory.decodeResource(getResources(),
				R.drawable.back));
		refresh = getButtonBitmap(BitmapFactory.decodeResource(getResources(),
				R.drawable.refresh));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 * 
	 * 绘制该View
	 */

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub

		paint = new Paint();
		paint.setAntiAlias(true);// 抗锯齿
		canvas.drawColor(Color.BLACK);
		for (int i = 0; i < map0.length; i++) {
			for (int j = 0; j < map0[i].length; j++) {
				int x = initX + LumpSideLength * j;
				int y = initY + LumpSideLength * i;
				switch (map0[i][j]) {
				case 2:
					canvas.drawBitmap(floor, x, y, paint);
					break;
				case 6:
					canvas.drawBitmap(des, x, y, paint);
					break;
				default:
					break;
				}
			}
		}
		for (int i = 0; i < map1.length; i++) {
			for (int j = 0; j < map1[i].length; j++) {
				int x = initX + LumpSideLength * j;
				int y = initY + LumpSideLength * i;
				switch (map1[i][j]) {
				case 1:
					canvas.drawBitmap(wall, x, y, paint);
					break;
				case 4:
					canvas.drawBitmap(bluemushroom, x, y, paint);
					break;
				case 5:
					canvas.drawBitmap(redmushroom, x, y, paint);
					break;
				case 7:
					canvas.drawBitmap(mario, x, y, paint);
					break;
				default:
					break;
				}

			}
		}
		// 绘制上下左右按钮
		// 初始x,y
		canvas.drawBitmap(refresh, 0, nowHeigth - buttonLength, paint);
		canvas.drawBitmap(back, nowWidth - buttonLength, nowHeigth
				- buttonLength, paint);
		canvas.drawBitmap(up, buttonX + buttonLength, buttonY, paint);
		canvas.drawBitmap(down, buttonX + buttonLength, buttonY + 2
				* buttonLength, paint);
		canvas.drawBitmap(left, buttonX, buttonY + buttonLength, paint);
		canvas.drawBitmap(right, buttonX + 2 * buttonLength, buttonY
				+ buttonLength, paint);

		isSuccess = true;
		for (Des dest : desList) {
			if (map1[dest.getDesX()][dest.getDesY()] != 5) {
				isSuccess = false;
				break;
			}
		}
		if (isSuccess) {
			Log.e("list", String.valueOf(desList.size()));
			//判断保存记录，并提示进入下一关或返回关卡界面
			success();
		}
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.draw(canvas);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 * 重写屏幕触摸方法，判断点击事件
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		float x = event.getX();
		float y = event.getY();

		// person将要移动到的位置上的值
		int m;
		if (x > buttonX && x < buttonX + 3 * buttonLength && y > buttonY
				&& y < buttonY + 3 * buttonLength) {
			if (x < buttonX + buttonLength) {
				// 按左键
				if (y > buttonY + buttonLength
						&& y < buttonY + 2 * buttonLength) {
					m = map1[person.getLumpY()][person.getLumpX() - 1];
					if (m == 0) {
						map1[person.getLumpY()][person.getLumpX() - 1] = 7;
						map1[person.getLumpY()][person.getLumpX()] = 0;
						person.setLumpX(person.getLumpX() - 1);
						addMapRecord();
					} else if (m == 4 || m == 5) {
						if (map1[person.getLumpY()][person.getLumpX() - 2] == 0) {
							map1[person.getLumpY()][person.getLumpX() - 1] = 7;
							if (map0[person.getLumpY()][person.getLumpX() - 2] == 6) {
								map1[person.getLumpY()][person.getLumpX() - 2] = 5;
								hitPlay();
							} else {
								map1[person.getLumpY()][person.getLumpX() - 2] = 4;
							}
							map1[person.getLumpY()][person.getLumpX()] = 0;
							person.setLumpX(person.getLumpX() - 1);
							addMapRecord();
						}

					}

				}
			} else if (x > buttonX + 2 * buttonLength) {
				// 按右键
				if (y > buttonY + buttonLength
						&& y < buttonY + 2 * buttonLength) {
					m = map1[person.getLumpY()][person.getLumpX() + 1];
					if (m == 0) {
						map1[person.getLumpY()][person.getLumpX() + 1] = 7;
						map1[person.getLumpY()][person.getLumpX()] = 0;
						person.setLumpX(person.getLumpX() + 1);
						addMapRecord();
					} else if (m == 4 || m == 5) {
						if (map1[person.getLumpY()][person.getLumpX() + 2] == 0) {
							map1[person.getLumpY()][person.getLumpX() + 1] = 7;
							if (map0[person.getLumpY()][person.getLumpX() + 2] == 6) {
								map1[person.getLumpY()][person.getLumpX() + 2] = 5;
								hitPlay();
							} else {
								map1[person.getLumpY()][person.getLumpX() + 2] = 4;
							}

							map1[person.getLumpY()][person.getLumpX()] = 0;
							person.setLumpX(person.getLumpX() + 1);
							addMapRecord();

						}

					}
				}

			} else {
				// 按上键
				if (y < buttonY + buttonLength) {
					m = map1[person.getLumpY() - 1][person.getLumpX()];
					if (m == 0) {
						map1[person.getLumpY() - 1][person.getLumpX()] = 7;
						map1[person.getLumpY()][person.getLumpX()] = 0;
						person.setLumpY(person.getLumpY() - 1);
						addMapRecord();
					} else if (m == 4 || m == 5) {
						if (map1[person.getLumpY() - 2][person.getLumpX()] == 0) {
							map1[person.getLumpY() - 1][person.getLumpX()] = 7;
							if (map0[person.getLumpY() - 2][person.getLumpX()] == 6) {
								map1[person.getLumpY() - 2][person.getLumpX()] = 5;
								hitPlay();
							} else {
								map1[person.getLumpY() - 2][person.getLumpX()] = 4;
							}
							map1[person.getLumpY()][person.getLumpX()] = 0;
							person.setLumpY(person.getLumpY() - 1);
							addMapRecord();

						}
					}
				} else if (y > buttonY + 2 * buttonLength) { // 按下键
					m = map1[person.getLumpY() + 1][person.getLumpX()];
					if (m == 0) {
						map1[person.getLumpY() + 1][person.getLumpX()] = 7;
						map1[person.getLumpY()][person.getLumpX()] = 0;
						person.setLumpY(person.getLumpY() + 1);
						addMapRecord();
					} else if (m == 4 || m == 5) {
						if (map1[person.getLumpY() + 2][person.getLumpX()] == 0) {
							map1[person.getLumpY() + 1][person.getLumpX()] = 7;
							if (map0[person.getLumpY() + 2][person.getLumpX()] == 6) {
								map1[person.getLumpY() + 2][person.getLumpX()] = 5;
								hitPlay();
							} else {
								map1[person.getLumpY() + 2][person.getLumpX()] = 4;
							}
							map1[person.getLumpY()][person.getLumpX()] = 0;
							person.setLumpY(person.getLumpY() + 1);
							addMapRecord();
						}
					}
				}
			}
		}

		//重玩
		if (x > 0 && x < buttonLength && y > nowHeigth - buttonLength
				&& y < nowHeigth) {
			initMap();
		}
		
		//返回上一步
		if (x > nowWidth - buttonLength && x < nowWidth
				&& y > nowHeigth - buttonLength && y < nowHeigth) {
			retreat();
			if(stepNum>0){
				stepNum--;
				nowGradeView.setText(String.valueOf(stepNum));
			}
		}

		return super.onTouchEvent(event);
	}


	/*
	 * 添加步骤记录
	 */
	private void addMapRecord() {
		int[][] mapRecord = new int[map1.length][map1[0].length];
		for (int i = 0; i < map1.length; i++) {
			for (int j = 0; j < map1[0].length; j++) {
				mapRecord[i][j] = map1[i][j];
			}
		}
		mapList.add(mapRecord);
		stepNum++;
		nowGradeView.setText(String.valueOf(stepNum));

	}

	/*
	 * 返回上一步
	 */
	public void retreat() {

		if (mapList.size() > 0) {
			mapList.remove(mapList.size() - 1);
		}
		if (mapList.size() > 0) {
			int[][] lastMap = mapList.get(mapList.size() - 1);
			for (int i = 0; i < lastMap.length; i++) {
				for (int j = 0; j < lastMap[0].length; j++) {
					map1[i][j] = lastMap[i][j];
					if(map1[i][j]==7){
						person = new Mario(j, i, mario);
					}
				}
			}
		} else {
			initMap();
		}

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
	}

	/*
	 * 获取游戏图片
	 */
	private Bitmap getBitmap(Bitmap bitmap) {

		ImageFormat imageFormat = new ImageFormat(bitmap, LumpSideLength,
				LumpSideLength);
		return imageFormat.getDescBitmap();
	}

	/*
	 * 获取按钮图片
	 */
	private Bitmap getButtonBitmap(Bitmap bitmap) {
		ImageFormat imageFormat = new ImageFormat(bitmap, buttonLength,
				buttonLength);
		return imageFormat.getDescBitmap();
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public GameDrawThread getGameDrawThread() {
		return gameDrawThread;
	}

	public void setGameDrawThread(GameDrawThread gameDrawThread) {
		this.gameDrawThread = gameDrawThread;
	}

	/*
	 * 过关
	 */
	public void success() {
		gameDrawThread.setFlag(false);
		
		if(successPlayer!=null){
			successPlayer.start();
		}
	
		// 判断是否为最好成绩并存储
		String bestGradeStr = Setting.getBestGrade(getContext(),
				String.valueOf(level));
		int bestGrade = Integer.valueOf(bestGradeStr);
		System.out.println(stepNum+"dddd"+bestGrade);
		
		if (stepNum < bestGrade || bestGrade == 0) {
			Setting.setBestGrade(getContext(), String.valueOf(level),
					String.valueOf(stepNum));
		}
		

		int passLevel = Setting.getLevel(this.getContext());
		if (passLevel <= MapList.pushbox_map.length && level + 1 > passLevel) {
			Setting.saveLevel(getContext(), level + 1);
		}
		AlertDialog.Builder builder;
		if (level + 1 < MapList.pushbox_map.length) {
			builder = new Builder(getContext());
			builder.setMessage("o(≧v≦)o~~好棒！恭喜过关！");
			builder.setTitle("贺电：");
			builder.setIcon(R.drawable.mariohappy);
			builder.setPositiveButton("再来一关",
					new android.content.DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// TODO Auto-generated method stub
							Message msg = new Message();
							Bundle b = new Bundle();// 存放数据
							b.putString("command", "next");
							msg.setData(b);
							((BoxPushActivity) GameView.this.getContext())
									.getMyHandler().sendMessage(msg); // 向Handler发送消息,更新UI
						}
					});
			builder.setNegativeButton("先不了",
					new android.content.DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							Message msg = new Message();
							Bundle b = new Bundle();// 存放数据
							b.putString("command", "back");
							msg.setData(b);
							((BoxPushActivity) GameView.this.getContext())
									.getMyHandler().sendMessage(msg); // 向Handler发送消息,更新UI
						}
					});

		} else {
			builder = new Builder(getContext());
			builder.setMessage("恭喜通关！让我期待新的关卡哦！O(∩_∩)O");
			builder.setIcon(R.drawable.redmushroom);
			builder.setTitle("红蘑菇发来贺电：");
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.cancel();
							System.exit(0);
						}
					});

		}
		builder.create().show();

	}

	/*
	 * 蓝蘑菇碰到红金币是播放的声音
	 */
	private void hitPlay() {
		if (hitPlayer != null) {
			hitPlayer.start();
		}
	}

	public MediaPlayer getHitPlayer() {
		return hitPlayer;
	}

	public void setHitPlayer(MediaPlayer hitPlayer) {
		this.hitPlayer = hitPlayer;
	}

	public int getStepNum() {
		return stepNum;
	}

	public void setStepNum(int stepNum) {
		this.stepNum = stepNum;
	}

}
