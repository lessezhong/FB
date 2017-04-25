package com.example.adapter;

import java.util.List;

import com.example.dbHelper.DataBaseHelper_1;
import com.example.entity.City;

import com.example.activity.*;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyGridViewAdapter extends BaseAdapter {

	private Context context;
	SQLiteDatabase db = null;
	DataBaseHelper_1 baseHelper_1 = null;
	String DB_NAME = "selected_city.db";// 已经选择城市的数据库
	City city;
	List<City> listCities;
	City[] cities;
	int carNum;
	private LayoutInflater inflater;

	public MyGridViewAdapter(Context context, List<City> listCities) {
		this.context = context;
		inflater = LayoutInflater.from(this.context);
		this.listCities = listCities;
		carNum = listCities.size();
	}

	@Override
	public int getCount() {

		return carNum;
	}

	@Override
	public Object getItem(int position) {
		return listCities.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView textView;
		ViewHolder viewHolder;
		
		if (convertView == null) {
			convertView=inflater.inflate(R.layout.my_gridview_item, null);
			viewHolder = new ViewHolder();
			viewHolder.title = (TextView) convertView.findViewById(R.id.title);
			viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
			convertView.setTag(viewHolder);
			//textView = new TextView(context);
			// textView.setGravity(Gravity.CENTER);
			// textView.setLayoutParams(new GridView.LayoutParams(100, 100));
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
			//textView = (TextView) convertView;
			// textView.setGravity(Gravity.CENTER);
			// textView = (TextView) convertView.findViewById(R.id.textView1);
		}
		
		String name = listCities.get(position).name;
		viewHolder.title.setText(name);
		viewHolder.image.setImageResource(R.drawable.background_03);
		//textView.setText(name);
		//textView.setTextSize(25);
		//textView.setGravity(Gravity.CENTER);
		//return textView;
		return convertView;
	}

	class ViewHolder {
		public TextView title;
		public ImageView image;
	}

}
