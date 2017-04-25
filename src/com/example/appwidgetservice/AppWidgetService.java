package com.example.appwidgetservice;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class AppWidgetService extends Service {
	String MyConfiguration = "MyConfiguration";
	public SharedPreferences sharedPreferences;
	public Runnable runnable;
	Handler handler;
	int freshTime;
	Intent intent2;
	boolean isFreshAuto;
	TimerTask task;
	Timer timer;

	/*
	 * 应用程序服务 用于更新桌面的Widget和天气的后台服务
	 */
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("输出", "——————————————————AppWidgetService onStartCommand 已经启动");
		sharedPreferences = getSharedPreferences(MyConfiguration, 0);
		freshTime = sharedPreferences.getInt("freshTime", 30) * 60000;
		handler = new Handler();
		task = new TimerTask() {
			@Override
			public void run() {
				update();
			}
		};
		timer = new Timer();
		if (freshTime > 0) {
			timer.schedule(task, freshTime, freshTime);
		}
		/*
		 * runnable=new Runnable() {
		 * 
		 * @Override public void run() { update(); handler.postDelayed(this,
		 * freshTime); } };
		 */
		return super.onStartCommand(intent, flags, startId);

	}

	// 更新操作
	public void update() {

		Log.d("输出", "mmmmmmmmmm更新咯");
		sharedPreferences = getSharedPreferences(MyConfiguration, 0);
		freshTime = sharedPreferences.getInt("freshTime", 30) * 60000;// 获取更新时间分钟
		intent2 = new Intent();
		String name = sharedPreferences.getString("name", "北京");
		String city_num = sharedPreferences.getString("city_num", "101010100");
		intent2.putExtra("name", name);
		intent2.putExtra("city_num",
				sharedPreferences.getString("city_num", city_num));
		intent2.setAction("weatherUpdataReceiver");
		Intent intent3 = new Intent();
		intent3.setAction("com.example.appwidget.WFWidgetProvider");
		if (freshTime > 0) {
			isFreshAuto = true;
			sendBroadcast(intent2);
			sendBroadcast(intent3);
		} else {
			isFreshAuto = false;
			Log.d("输出", "freshTime--------<0");
		}

	}

	@Override
	public void onCreate() {
		// sharedPreferences = getSharedPreferences(MyConfiguration, 0);
		// freshTime = sharedPreferences.getInt("freshTime", 30) *60000;//
		// 获取更新时间分钟
		// intent2 = new Intent();
		// intent2.putExtra("name", sharedPreferences.getString("name", null));
		// intent2.putExtra("city_num",
		// sharedPreferences.getString("city_num", null));
		// intent2.setAction("weatherUpdataReceiver");
		// handler = new Handler();
		// runnable = new Runnable() {
		// @Override
		// public void run() {
		// sendBroadcast(intent2);
		// Log.d("输出", "mmmmmmmmmm");
		// Intent intent3=new Intent();
		// intent3.setAction("com.example.appwidget.WFWidgetProvider");
		// sendBroadcast(intent3);
		// handler.postDelayed(this, freshTime);
		// }
		// };

		super.onCreate();
	}

	@Override
	public void onDestroy() {
		timer.cancel();
		Log.d("输出", "——————————————————————AppWidgetService onDestroy 已经启动");
		super.onDestroy();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		newConfig = getResources().getConfiguration();
		Log.d("输出",
				"——————————————————————AppWidgetService onConfigurationChanged 已经启动");
		super.onConfigurationChanged(newConfig);
	}

}
