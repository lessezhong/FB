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
	public static SharedPreferences.Editor editor;// ϵͳ�����ļ�
	String configurFileDir="/data/data/com.example.acivity/shared_prefs/pref_key.xml";

	public MyConfiguration(Context context) {
		super();
		this.context = context;

	}
    public boolean isfileExist(){
    	selectedCityDAO=new SelectedCityDAO(context);
    	listCities= selectedCityDAO.query();
		return false;}

	// ���������ļ�
	public void newPreference(String name,String city_num) {
		editor = context.getSharedPreferences(MyConfiguration, 0).edit();
		editor.putString("city_num", city_num);
		editor.putString("name", name);// ���ø��µĳ���
		//editor.putBoolean("isChecked", false);// �Ƿ񆢄ӷ���
		//editor.putInt("freshTime",-1);//���ø���ʱ��
		editor.commit();

	}

	// ��ȡ�����ļ�
	public SharedPreferences readPreference() {
		SharedPreferences sharedPreferences = context.getSharedPreferences(
				MyConfiguration, 0);
		return sharedPreferences;

	}

	// ��ȡSharedPreferences.Editor
	public SharedPreferences.Editor editPreference() {
		SharedPreferences.Editor editor = context.getSharedPreferences(
				MyConfiguration, 0).edit();
		return editor;
	}

}
