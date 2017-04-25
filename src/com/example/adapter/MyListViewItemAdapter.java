package com.example.adapter;

import com.example.activity.R;
//import com.example.entity.City;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MyListViewItemAdapter<City> extends ArrayAdapter<City> {
	LayoutInflater inflater;
	Context context;
	City city;

	public MyListViewItemAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		convertView = inflater.inflate(R.layout.group, null);// 适配器inflate的布局文件
		TextView text = (TextView) convertView.findViewById(R.id.groupTo);// item
																			// 控件
		text.setText("");// 设置item控件的值
		return convertView;

	}

}
