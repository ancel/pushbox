package com.ancelly.mariopushbox;

import com.ancelly.mariopushbox.R;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 
 * 
 * 
 * @author ancel
 * 
 * @version 2012-9-25
 */

public class LevelView extends LinearLayout {
	private int level;
	private ImageView imageView;
	private TextView levelView;

	public LevelView(final Context context, final int level, final int passLevel) {
		super(context);
		// TODO Auto-generated constructor stub
		setOrientation(LinearLayout.VERTICAL); // 垂直布局
		setGravity(Gravity.CENTER);

		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.WRAP_CONTENT,
				LinearLayout.LayoutParams.WRAP_CONTENT);
		this.level = level;
		imageView = new ImageView(context);
		if (level-1 <= passLevel) {
			imageView.setBackgroundResource(R.drawable.mariohead);
		} else {
			imageView.setBackgroundResource(R.drawable.mushroom);
		}

		imageView.setPadding(0, 0, 0, 0);
		imageView.setFocusable(false);
		
		
		levelView = new TextView(context);
		levelView.setText(level + "");
		levelView.setTextColor(Color.BLUE);
		levelView.setFocusable(false);
		// 水平居中
		levelView.setGravity(android.view.Gravity.CENTER_HORIZONTAL);
		levelView.setPadding(0, 0, 0, 0);

		addView(imageView, params);
		addView(levelView, params);
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public TextView getLevelView() {
		return levelView;
	}

	public void setLevelView(TextView levelView) {
		this.levelView = levelView;
	}

}
