package com.example.dao;

import java.util.ArrayList;
import java.util.List;

import com.example.dbHelper.DataBaseHelper_1;
import com.example.entity.City;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class SelectedCityDAO {
	Context context;
	String DBName = "selected_city.db";
	String TableName = "citylist";
	String CITY_NUM = "city_num";
	String NAME = "name";
	String NOW = "now";
	String TODAY = "today";
	String FUTURE = "future";

	/**
	 * 程序第一次运行创创建数据库
	 * 
	 * @param context
	 */
	public SelectedCityDAO(Context context) {
		super();
		this.context = context;

	}

	/**
	 * 插入
	 * 
	 * @param city
	 */

	public void insert(City city, String now, String today, String future) {
		DataBaseHelper_1 dataBaseHelper_1 = new DataBaseHelper_1(context,
				DBName, null, 1);
		SQLiteDatabase db = dataBaseHelper_1.getReadableDatabase();
		ContentValues values = new ContentValues();
		values.put("city_num", city.getCity_num());
		values.put("name", city.getName());
		values.put("now", now);
		values.put("today", today);
		values.put("future", future);
		db.insert(TableName, null, values);
		db.close();
	}

	/**
	 * 天气更新
	 * 
	 * @param city
	 * @param now
	 * @param today
	 * @param future
	 */
	public void updata(City city, String now, String today, String future) {
		DataBaseHelper_1 dataBaseHelper_1 = new DataBaseHelper_1(context,
				DBName, null, 1);
		SQLiteDatabase db = dataBaseHelper_1.getReadableDatabase();
		ContentValues values = new ContentValues();
		values.put("city_num", city.getCity_num());
		values.put("name", city.getName());
		values.put("now", now);
		values.put("today", today);
		values.put("future", future);
		db.update(TableName, values, CITY_NUM + "=" + city.city_num, null);
		db.close();
	}

	/**
	 * 刪除数据
	 * 
	 * @param city
	 *            ; city.getCity_num()
	 */
	public void delete(City city) {
		DataBaseHelper_1 dataBaseHelper_1 = new DataBaseHelper_1(context,
				DBName, null, 1);
		SQLiteDatabase db = dataBaseHelper_1.getReadableDatabase();
		db.delete(TableName, CITY_NUM + "=" + city.getCity_num(), null);
		db.close();
	}

	/**
	 * 查询已經選著的城市
	 * 
	 * @return List<City>
	 */
	public List<City> query() {
		DataBaseHelper_1 dataBaseHelper_1 = new DataBaseHelper_1(context,
				DBName, null, 1);
		SQLiteDatabase db = dataBaseHelper_1.getReadableDatabase();
		Cursor cursor;
		int num;
		cursor = db.query(TableName, new String[] { "city_num", "name" }, null,
				null, null, null, null, null);
		num = cursor.getCount();
		City[] city = new City[num];
		List<City> listCity = new ArrayList<City>();
		for (int i = 0; i < num; i++) {
			cursor.moveToNext();
			city[i] = new City();
			city[i].city_num = cursor.getString(cursor
					.getColumnIndex("city_num"));
			city[i].name = cursor.getString(cursor.getColumnIndex("name"));
			listCity.add(city[i]);
		}
		cursor.close();
		db.close();
		return listCity;
	}

	/**
	 * 查询已经存在的天气数据
	 * 
	 * @param city_num
	 * @return
	 */
	public List<City> query(String city_num) {
		DataBaseHelper_1 dataBaseHelper_1 = new DataBaseHelper_1(context,
				DBName, null, 1);
		SQLiteDatabase db = dataBaseHelper_1.getReadableDatabase();
		Cursor cursor;
		int num;
		cursor = db.query(TableName, new String[] { "city_num", "name", "now",
				"today", "future" }, CITY_NUM + "=" + city_num, null, null,
				null, null, null);
		num = cursor.getCount();
		City[] city = new City[num];
		List<City> listCity = new ArrayList<City>();
		for (int i = 0; i < num; i++) {
			cursor.moveToNext();
			city[i] = new City();
			city[i].city_num = cursor.getString(cursor
					.getColumnIndex("city_num"));
			city[i].name = cursor.getString(cursor.getColumnIndex("name"));
			city[i].now = cursor.getString(cursor.getColumnIndex("now"));
			city[i].today = cursor.getString(cursor.getColumnIndex("today"));
			city[i].future = cursor.getString(cursor.getColumnIndex("future"));
			listCity.add(city[i]);
		}
		cursor.close();
		db.close();
		return listCity;
	}

}
