package com.example.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.Set.MyConfiguration;
import com.example.activity.R;
import com.example.adapter.MyCursorAdapter;
import com.example.dao.ChinaDAO;
import com.example.entity.City;
import com.example.entity.Province;

import android.os.Bundle;
import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ShowProvinceActivity extends ListActivity {

	SimpleAdapter adapter;
	private LocationClient mLocClient;
	TextView mTv;
	// EditText chaxun;
	public static String TAG = "LocTestDemo";
	StringBuffer sb = new StringBuffer(256);
	BDLocation MyLocation;
	ChinaDAO myHelper;
	AutoCompleteTextView autoCompleteTextView;
	String[] trainColumns = new String[] { "city_name", "city_name as _id" };
	MyCursorAdapter myCursorAdapter;
	MyConfiguration configuration;
	SharedPreferences preferences;
	String simpleName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
//		ActionBar actionBar=getActionBar();
//		actionBar.hide();
		setContentView(R.layout.show_province_activity);
		Intent intent = getIntent();
		simpleName = intent.getStringExtra("name");
		MyLocation = new BDLocation();
		// chaxun = (EditText) findViewById(R.id.chaxun);
		configuration = new MyConfiguration(ShowProvinceActivity.this);
		preferences = configuration.readPreference();
		mTv = (TextView) findViewById(R.id.location_textView);

		mTv.setText(preferences.getString("location", "null"));
		myHelper = new ChinaDAO(this);
		List<Province> provincesList = myHelper.queryProvince();
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		for (Iterator<Province> iterator = provincesList.iterator(); iterator
				.hasNext();) {
			HashMap<String, String> hashMap = new HashMap<String, String>();
			Province province = (Province) iterator.next();
			hashMap.put("_id", province.get_id());
			hashMap.put("name", province.getName());
			list.add(hashMap);
		}
		/**
		 * 1 context 2 数据 3 布局文件 4 布局文件里的内容（字符串要与參數 一致） 5布局文件内容 对应的控件
		 */
		/*
		 * adapter = new SimpleAdapter(this, list, R.layout.province, new
		 * String[] { "name", "_id" }, new int[] { R.id.name, R.id._id });
		 */
		adapter = new SimpleAdapter(this, list, R.layout.province,
				new String[] { "name" }, new int[] { R.id.name });
		setListAdapter(adapter);

		// 百度地图定位相关

		ImageButton Locat_Button = (ImageButton) findViewById(R.id.my_serch);
		// mLocClient = ((Location) getApplication()).mLocationClient;//
		// 获取应用程中LocationClient的对象;

		mLocClient = new LocationClient(this);
		mLocClient.setAK("lohqPAWXx4UpDR90K6Ef7M6Y");

		mLocClient.registerLocationListener(new MyLocationListenner());
		Locat_Button.setOnClickListener(new MySerch());
		autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.chaxun);
		autoCompleteTextView.setText(simpleName);
		autoCompleteTextView.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				Cursor cursor = myHelper.mohuquery_simple(s.toString());
				myCursorAdapter = new MyCursorAdapter(
						ShowProvinceActivity.this, cursor, true);
				autoCompleteTextView.setAdapter(myCursorAdapter);
				// HashMap<String, String> map = (HashMap<String, String>)
				// myCursorAdapter
				// .getItem(count);
				// Intent intent = new Intent();
				// intent.setAction(Intent.ACTION_EDIT);
				// intent.putExtra("name", map.get("name"));
				// intent.putExtra("city_num", map.get("city_num"));
				// sendBroadcast(intent);

			}

		});

	}

	public class MyLocationListenner implements BDLocationListener {
		@Override
		/**
		 * 获取定位结果
		 */
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			sb.append("time : ");
			sb.append(location.getTime());
			sb.append("\nerror code : ");
			sb.append(location.getLocType());
			sb.append("\n latitude : ");
			sb.append(location.getLatitude());
			sb.append("\n lontitude : ");
			sb.append(location.getLongitude());
			sb.append("\n radius : ");
			sb.append(location.getRadius());
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sb.append("\nspeed : ");
				sb.append(location.getSpeed());
				sb.append("\nsatellite : ");
				sb.append(location.getSatelliteNumber());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 连接网络
				/**
				 * 格式化显示地址信息
				 */
				sb.append("\n省：");
				sb.append(location.getProvince());
				sb.append("\n市：");
				sb.append(location.getCity());
				sb.append("\n区/县：");
				sb.append(location.getDistrict());
				sb.append(location.getLocType());
			}
			sb.append("\nsdk version : ");
			sb.append(mLocClient.getVersion());
			sb.append("\nisCellChangeFlag : ");
			sb.append(location.isCellChangeFlag());
			Log.i(TAG, sb.toString());
			MyLocation = location;
			mTv.setText(location.getProvince() + location.getCity()
					+ location.getDistrict());
			SharedPreferences.Editor editor;
			MyConfiguration configuration = new MyConfiguration(ShowProvinceActivity.this);
			editor = configuration.editPreference();
			editor.putString("location",location.getProvince()+location.getCity()+location.getDistrict());
			editor.commit();
			String city_name = MyLocation.getCity().substring(0, 2);
			autoCompleteTextView.setText(city_name);

		}

		@Override
		public void onReceivePoi(BDLocation bd) {
			String a = bd.getAltitude() + "onReceivePoi纬度";
			Log.d(TAG, a);
		}

	}

	class MySerch implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// setLocationOption();
			// mLocClient.start();
			// mLocClient.requestLocation();
			// Log.d(TAG, "mLocClient不为空");
			String name = autoCompleteTextView.getText().toString();
			City city = myHelper.query_jignque(name);
			Intent intent = new Intent();
			intent.putExtra("name", city.name);
			intent.putExtra("city_num", city.city_num);
			intent.setAction(Intent.ACTION_EDIT);
			sendBroadcast(intent);

		}
	}

	/**
	 * 设置相关参数
	 */
	private void setLocationOption() {
		// LocationClientOption option = new LocationClientOption();
		// // option.setOpenGps(true); // 打开GPRS
		// option.setAddrType("all");// 返回的定位结果包含地址信息
		// option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
		// // option.setPriority(LocationClientOption.GpsFirst); // 设置GPS优先
		// option.setScanSpan(10000); // 设置发起定位请求的间隔时间为5000ms
		// option.disableCache(false);// 禁止启用缓存定位
		// // option.setOpenGps(true);
		// option.setServiceName("com.baidu.location.service_v2.9");
		// option.setAddrType("all");
		// option.setPoiNumber(10);
		// option.disableCache(true);
		// mLocClient.setLocOption(option);
		LocationClientOption option = new LocationClientOption();
		option.setServiceName("com.baidu.location.service_v2.9");
		option.setAddrType("all");
		option.setPoiNumber(10);
		option.disableCache(true);
		mLocClient.setLocOption(option);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		HashMap<String, String> map = (HashMap<String, String>) adapter
				.getItem(position);
		Intent intent = new Intent();
		intent.putExtra("_id", map.get("_id"));
		intent.setClass(this, ShowCityActivity.class);
		startActivity(intent);

	}

	@Override
	public void onDestroy() {
		mLocClient.stop();
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.show_province_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.my_loact:
			setLocationOption();
			mLocClient.start();
			mLocClient.requestLocation();
			break;

		default:
			break;
		}
		return false;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			Intent intent = new Intent(ShowProvinceActivity.this,
					MainActivity.class);
			//startActivity(intent);
		}
		return super.onKeyDown(keyCode, event);
	}

}
