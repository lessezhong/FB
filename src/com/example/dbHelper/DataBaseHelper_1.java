package com.example.dbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/*
 *
 *已经选择的城市数据库
 */
public class DataBaseHelper_1 extends SQLiteOpenHelper {

	public DataBaseHelper_1(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String SQL = "create table citylist(city_num int primary key,name text not null,now text,today text,future text)";
		db.execSQL(SQL);
		// db.close();

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
