package com.example.receiver;

import org.json.JSONArray;
import org.json.JSONObject;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GetCityNumReceiver extends BroadcastReceiver {
	/**
	 * 通过show city activity的触发事件获取天气数据并将其数据插入数据库
	 */
	String info;// 链接返回实时数据
	String info1;// 链接返回当天天气数据
	String info2;// 链接返回未来六天的数据
	TextView tempText;
	ImageView imageView;
	JSONObject json;// observer
	JSONObject json1;// 三天
	JSONObject json2;// index
	Context context = null;;
	DataBaseHelper_1 baseHelper_1 = null;
	SQLiteDatabase db = null;
	String DB_NAME = "selected_city.db";// 已经选择城市的数据库
	City city;
	All all;
	SelectedCityDAO selectedCityDAO;
	boolean b;
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
			progressDialog=new ProgressDialog(this.context);
			progressDialog.setMessage("天气正在更新中");
			progressDialog.show();
			Thread thread = new Thread(r);
			thread.start();
			Log.d("输出", "onReceive当前进程id——————>"
					+ Thread.currentThread().getId());
		} else {
			Toast.makeText(context, "你的网络开了点小差咯O(∩_∩)O~", Toast.LENGTH_SHORT)
					.show();
		}

	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case 1:
				progressDialog.hide();
				Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT).show();
				break;

			case 2:
				Toast.makeText(context, "您的网络似乎有点问题哦O(∩_∩)O",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};
	Runnable r = new Runnable() {
		
		@Override
		public void run() {
			Log.d("输出", "当前r进程ID----->" + Thread.currentThread().getId());
			Message msg = new Message();
			GetWeather getWeather = new GetWeather();
			try {
				// 获取天气字符串
				all = getWeather.GetALLWeaterContent(city.city_num);
				info = all.observe;
				info1 = all.focast3d;
				info2 = all.index;
				Log.d("输出", info);
				Log.d("输出", info1);
				Log.d("输出", info2);
				// =解析JSON得到天气===从info中获取JSONObject对象json
				json = new JSONObject(info).getJSONObject("l");
				json1 = new JSONObject(info1).getJSONObject("f");
				JSONArray jsonArray;
				jsonArray = new JSONObject(info2).getJSONArray("i");// 获取json数组
				json2 = jsonArray.getJSONObject(0);
				if (json != null && json1 != null && json2 != null) {
					if(selectedCityDAO.query().size()<1){
						MyConfiguration configuration=new MyConfiguration(context);//配置提醒的城市创建配置文件
						configuration.newPreference(city.name, city.city_num);
					}
					selectedCityDAO.insert(city, info, info1, info2);
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

}
