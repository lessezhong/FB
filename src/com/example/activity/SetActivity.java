package com.example.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.adapter.MyListViewAdapter;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

public class SetActivity extends ListActivity {

	EditText edText = null;
	ImageButton imageButton = null;
	String cityname = null;
	Uri uridata = null;

	// SimpleAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉titlebark
		// setContentView(R.layout.setactivity);
		String[] group = { "天气设置", "刷新设置", "壁纸设置", "城市管理" };
		List<String> lists = new ArrayList<String>();
		for (int i = 0; i < group.length; i++) {
			lists.add(group[i]);
		}
		MyListViewAdapter adapter = new MyListViewAdapter(this, lists);
		setListAdapter(adapter);

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		switch (position) {
		case 0:
			Intent intent = new Intent();
			intent.setClass(this, ShowProvinceActivity.class);
			startActivity(intent);
			break;
		case 1:
			Intent intent1 = new Intent();
			intent1.setClass(this, RefreshActivty.class);
			startActivity(intent1);
			break;
		case 3:
			Intent intent3 = new Intent();
			intent3.setClass(this, CityManageActivity.class);
			finish();
			startActivity(intent3);
			break;
		case 2:
			Intent intent2 = new Intent();
			intent2.setClass(this, SetBackGroundActivity.class);
			startActivity(intent2);
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode==KeyEvent.KEYCODE_BACK) {
			 /*finish();
	         Intent intent=new Intent(SetActivity.this,MainActivity.class);
	         startActivity(intent);*/
		}
		return super.onKeyDown(keyCode, event);
	}
}
