package com.example.dao;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dbHelper.DataBaseHelper;
import com.example.entity.City;
import com.example.entity.Province;

/**
 * 对china 数据库进行操作的DAO
 * 
 * @author hai
 * 
 * 
 */
public class ChinaDAO {
	Context context;
	String NAME = "name";

	/*
	 * @para context
	 */
	public ChinaDAO(Context context) {
		this.context = context;
		DataBaseHelper dbhelper = new DataBaseHelper(context);
		dbhelper = new DataBaseHelper(context);
		try {
			dbhelper.createDataBase();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	/**
	 * 
	 * @return provinceList
	 */
	public List<Province> queryProvince() {

		DataBaseHelper dbhelper = new DataBaseHelper(context);
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		Cursor cursor;
		cursor = db.query("provinces", new String[] { "_id", "name" }, null,
				null, null, null, null);
		int count = cursor.getCount();
		Province[] provinces = new Province[count];
		List<Province> provinceList = new ArrayList<Province>();
		for (int i = 0; i < count; i++) {
			cursor.moveToNext();
			provinces[i] = new Province();
			provinces[i]._id = cursor.getString(cursor.getColumnIndex("_id"));
			provinces[i].name = cursor.getString(cursor.getColumnIndex("name"));
			provinceList.add(provinces[i]);
		}
		cursor.close();
		db.close();
		return provinceList;
	}

	/**
	 * 显示城市
	 * 
	 * @param province_id
	 * @return citieList
	 */
	public List<City> queryCity(String province_id) {

		DataBaseHelper dbhelper = new DataBaseHelper(context);
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		Cursor cursor;
		cursor = db.query("citys", new String[] { "province_id", "city_num",
				"name" }, "province_id=" + province_id, null, null, null, null);

		int count = cursor.getCount();
		City[] cities = new City[count];
		List<City> citieList = new ArrayList<City>();
		for (int i = 0; i < count; i++) {
			cursor.moveToNext();
			cities[i] = new City();
			cities[i].city_num = cursor.getString(cursor
					.getColumnIndex("city_num"));
			cities[i].name = cursor.getString(cursor.getColumnIndex("name"));
			citieList.add(cities[i]);
		}
		cursor.close();
		db.close();
		return citieList;
	}

	// 获取全部城市名字
	public Cursor queryCity() {

		DataBaseHelper dbhelper = new DataBaseHelper(context);
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		Cursor cursor;
		cursor = db.query("citys", new String[] { "province_id", "city_num",
				"name" }, "_id =" + 100, null, null, null, null);
		// cursor.close();
		// db.close();
		return cursor;
	}

	/**
	 * 模糊查詢
	 * 
	 * @param city_name
	 * @return
	 */
	public List<City> query_simple(String city_name) {
		DataBaseHelper dbhelper = new DataBaseHelper(context);
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		Cursor cursor;
		// cursor = db.query("citys", new String[] { "city_num", "name" },
		// "name like " + city_name, null, null, null, null);
		cursor = db.query("citys", new String[] { "city_num", "name" },
				"name like ?", new String[] { "%" + city_name + "%" }, null,
				null, null);
		int count = cursor.getCount();
		City[] cities = new City[count];
		List<City> citieList = new ArrayList<City>();
		for (int i = 0; i < count; i++) {
			cursor.moveToNext();
			cities[i] = new City();
			cities[i].city_num = cursor.getString(cursor
					.getColumnIndex("city_num"));
			cities[i].name = cursor.getString(cursor.getColumnIndex("name"));
			citieList.add(cities[i]);
		}
		cursor.close();
		db.close();
		return citieList;

	}

	/**
	 * 根据输入的城市名字模糊查詢
	 * 
	 * @param city_name
	 * @return
	 */
	public Cursor mohuquery_simple(CharSequence city_name) {
		DataBaseHelper dbhelper = new DataBaseHelper(context);
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		Cursor cursor;
		// cursor = db.query("citys", new String[] { "city_num", "name" },
		// "name like " + city_name, null, null, null, null);
		// return db.query("citys", new String[] { "_id","city_num", "name" },
		// "name like ?", new String[] { "%" + city_name.toString()+ "%" },
		// null,
		// null, null);

		cursor = db.query("citys", new String[] { "_id", "city_num", "name" },
				"name like ?",
				new String[] { "%" + city_name.toString() + "%" }, null, null,
				null);
		int num = cursor.getCount();
		// cursor.close();
		// db.close();
		return cursor;

	}

	/**
	 * 城市名精确查询
	 * 
	 * @param name
	 * @return
	 */
	public City query_jignque(String name) {
		DataBaseHelper dbhelper = new DataBaseHelper(context);
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		Cursor cursor;
		String selection = "name like ?";
		cursor = db.query("citys", new String[] { "_id", "city_num", "name" },
				selection, new String[] { "%" + name + "%" }, null, null, null);
		int count = cursor.getCount();
		City[] cities = new City[count];
		List<City> citieList = new ArrayList<City>();
		for (int i = 0; i < count; i++) {
			cursor.moveToNext();
			cities[i] = new City();
			cities[i].city_num = cursor.getString(cursor
					.getColumnIndex("city_num"));
			cities[i].name = cursor.getString(cursor.getColumnIndex("name"));
			citieList.add(cities[i]);
		}
		cursor.close();
		db.close();
		return citieList.get(0);

	}
}
