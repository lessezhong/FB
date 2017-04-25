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
	 * Ӧ�ó������ ���ڸ��������Widget�������ĺ�̨����
	 */
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("���", "������������������������������������AppWidgetService onStartCommand �Ѿ�����");
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

	// ���²���
	public void update() {

		Log.d("���", "mmmmmmmmmm���¿�");
		sharedPreferences = getSharedPreferences(MyConfiguration, 0);
		freshTime = sharedPreferences.getInt("freshTime", 30) * 60000;// ��ȡ����ʱ�����
		intent2 = new Intent();
		String name = sharedPreferences.getString("name", "����");
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
			Log.d("���", "freshTime--------<0");
		}

	}

	@Override
	public void onCreate() {
		// sharedPreferences = getSharedPreferences(MyConfiguration, 0);
		// freshTime = sharedPreferences.getInt("freshTime", 30) *60000;//
		// ��ȡ����ʱ�����
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
		// Log.d("���", "mmmmmmmmmm");
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
		Log.d("���", "��������������������������������������������AppWidgetService onDestroy �Ѿ�����");
		super.onDestroy();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		newConfig = getResources().getConfiguration();
		Log.d("���",
				"��������������������������������������������AppWidgetService onConfigurationChanged �Ѿ�����");
		super.onConfigurationChanged(newConfig);
	}

}
