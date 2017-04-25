package com.example.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import com.example.dao.ChinaDAO;
import com.example.entity.City;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ShowCityActivity extends ListActivity {
	SimpleAdapter adapter;
	TextView textView;
	

	/**
	 * @author hai
	 * @param String
	 * @return String city_null
	 * 
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_city_activity);
	
		textView = (TextView) findViewById(R.id.text1);
		Intent intent = getIntent();
		String province_id = intent.getStringExtra("_id");// 获取传进来的province_id
		ChinaDAO myHelper = new ChinaDAO(this);
		List<City> listCites = myHelper.queryCity(province_id);
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		for (Iterator<City> iterator = listCites.iterator(); iterator.hasNext();) {
			HashMap<String, String> hashMap = new HashMap<String, String>();
			City city = (City) iterator.next();
			hashMap.put("name", city.getName());
			hashMap.put("city_num", city.getCity_num());
			list.add(hashMap);
		}
		adapter = new SimpleAdapter(this, list, R.layout.province,
				new String[] { "name", "_id" },
				new int[] { R.id.name, R.id._id });
		setListAdapter(adapter);
	}

	/**
	 * @param
	 * @return city_num
	 */

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		HashMap<String, String> hashMap = (HashMap<String, String>) adapter
				.getItem(position);
		String text = hashMap.get("name") + hashMap.get("city_num");
		textView.setText(text);
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_EDIT);
		intent.putExtra("name", hashMap.get("name"));
		intent.putExtra("city_num", hashMap.get("city_num"));
		sendBroadcast(intent);
		ShowCityActivity.this.finish();

	}
}
