package com.example.receiver;

import com.example.Set.MyConfiguration;
import com.example.dao.SelectedCityDAO;
import com.example.dbHelper.DataBaseHelper_1;
import com.example.entity.All;
import com.example.entity.City;
import com.example.tools.GetNetWorkState;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class WeatherUpdataReceiver extends BroadcastReceiver {

	private Context context;
	All all;
	City city;
	String DB_NAME = "selected_city.db";// 已经选择城市的数据库
	DataBaseHelper_1 baseHelper_1;
	SQLiteDatabase db;
	SelectedCityDAO selectedCityDAO;
	boolean b;// 网络状态
	ProgressDialog progressDialog;

	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;
		GetNetWorkState getNetWorkState = new GetNetWorkState(context);
		b = getNetWorkState.getNetWorkState();
		if (b == true) {
			// 建立 DB_NAME 数据库
			baseHelper_1 = new DataBaseHelper_1(context, DB_NAME, null, 1);
			db = baseHelper_1.getReadableDatabase();
			selectedCityDAO = new SelectedCityDAO(context);
			city = new City();
			all = new All();
			// 获取showcity传回来的值
			city.city_num = intent.getStringExtra("city_num");
			city.name = intent.getStringExtra("name");
			// Toast.makeText(context, intent.getStringExtra("name") + "天气更新中",
			// Toast.LENGTH_SHORT).show();
			// 显示正在更细提示框
			progressDialog = new ProgressDialog(context);
			progressDialog.setMessage("天气正在更新中");
			progressDialog.show();
			Thread thread = new Thread(r);
			thread.start();
			Log.d("输出", "WeatherUpdataReceiver——————>onReceive"
					+ Thread.currentThread().getId());

		} else {
			Toast.makeText(context, "你的网络开了点小差咯O(∩_∩)O~", Toast.LENGTH_SHORT)
					.show();
		}

	}

	Runnable r = new Runnable() {

		@Override
		public void run() {
			Log.d("输出", "当前r进程ID----->" + Thread.currentThread().getId());
			Message msg = new Message();
			GetWeather getWeather = new GetWeather();
			try {
				// 获取天气字符串
				all = getWeather.GetALLWeaterContent(city.city_num);
				String info = all.observe;
				String info1 = all.focast3d;
				String info2 = all.index;
				Log.d("输出", info);
				Log.d("输出", info1);
				Log.d("输出", info2);
				if (info != null && info1 != null && info2 != null) {
					if (selectedCityDAO.query().size() < 1) {
						MyConfiguration configuration = new MyConfiguration(
								context);// 配置提醒的城市创建配置文件
						configuration.newPreference(city.name, city.city_num);
					}
					selectedCityDAO.updata(city, info, info1, info2);
					msg.what = 1;
				} else {
					msg.what = 2;
				}

				handler.sendMessage(msg);
			} catch (Exception e) {
				Log.d("输出", e.toString());
			}
		}
	};
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				progressDialog.hide();
				Toast.makeText(context, "城市天气更新成功", Toast.LENGTH_SHORT).show();
				break;

			case 2:
				progressDialog.hide();
				Toast.makeText(context, "您的网络似乎有点问题哦O(∩_∩)O",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

}
