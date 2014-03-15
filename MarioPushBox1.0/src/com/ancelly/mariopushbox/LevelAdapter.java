package com.ancelly.mariopushbox;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * 
 * 
 * 
 * @author ancel
 * 
 * @version 2012-9-25
 */

public class LevelAdapter extends BaseAdapter {

	private Context mContext;
	private int passLevel;

	public LevelAdapter(Context c, int passLevel) {
		mContext = c;
		this.passLevel = passLevel;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return MapList.pushbox_map.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		LevelView lv;
//		if (convertView == null) { // if it's not recycled, initialize some
									// attributes

			lv = new LevelView(mContext, position+1,passLevel);

//		} else {
//			lv = (LevelView) convertView;
//		}
		return lv;
	}

}
