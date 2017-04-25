package com.example.Set;

import java.util.List;

import com.example.dao.SelectedCityDAO;
import com.example.entity.City;

import android.content.Context;
import android.content.SharedPreferences;

public class MyConfiguration {
	List<City> listCities;
	SelectedCityDAO selectedCityDAO;
	public Context context;
	public static String MyConfiguration = "MyConfiguration";
	public static SharedPreferences.Editor editor;// 系统配置文件
	String configurFileDir="/data/data/com.example.acivity/shared_prefs/pref_key.xml";

	public MyConfiguration(Context context) {
		super();
		this.context = context;

	}
    public boolean isfileExist(){
    	selectedCityDAO=new SelectedCityDAO(context);
    	listCities= selectedCityDAO.query();
		return false;}

	// 创建配置文件
	public void newPreference(String name,String city_num) {
		editor = context.getSharedPreferences(MyConfiguration, 0).edit();
		editor.putString("city_num", city_num);
		editor.putString("name", name);// 设置更新的城市
		//editor.putBoolean("isChecked", false);// 是否啟動服務
		//editor.putInt("freshTime",-1);//设置更新时间
		editor.commit();

	}

	// 读取配置文件
	public SharedPreferences readPreference() {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				MyConfiguration, 0);
		return sharedPreferences;

	}

	// 获取SharedPreferences.Editor
	public SharedPreferences.Editor editPreference() {
		SharedPreferences.Editor editor = context.getSharedPreferences(
				MyConfiguration, 0).edit();
		return editor;
	}

}
