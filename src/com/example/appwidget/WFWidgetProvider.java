package com.example.appwidget;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Set.Imageset;
import com.example.activity.MainActivity;
import com.example.activity.R;
import com.example.dao.SelectedCityDAO;
import com.example.entity.City;
import com.example.entity.Day;
import com.example.trans.TransDate;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

public class WFWidgetProvider extends AppWidgetProvider {
	String wd;
	String MyConfiguration = "MyConfiguration";
	AppWidgetManager appwidgetManager;
	TransDate transDate;
	Day day;//
	String action;// 接受到的广播
	String xingqi;
	RemoteViews remoteViews;
	PendingIntent pendingIntent;// 。另外两个参数以后再讲。
	ComponentName componentname;
	SharedPreferences preferences;
	String name;// 城市名
	String city_num;// 城市码
	String WD;// 当期温度
	JSONObject jsonObject;
	int pitrueId;
	String date;// 日期

	@Override
	public void onEnabled(Context context) {

		Log.d("输出", "WFWidgetProvider onEnabled-------------onEnabled");

		transDate = new TransDate(context);
		day = transDate.yunsuan();
		xingqi = day.day_0;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
				"yyyy-MM-dd hh:mm");
		date = simpleDateFormat.format(new Date()).substring(14);
		Intent intent1 = new Intent(context, MainActivity.class);
		preferences = context.getSharedPreferences(MyConfiguration, 0);

		name = preferences.getString("name", "beijing");
		city_num = preferences.getString("city_num", "101010100");
		// 创建一个remoteViews。
		remoteViews = new RemoteViews(context.getPackageName(),
				R.layout.wfwidgetprovider);
		remoteViews.setTextViewText(R.id.showxingqi, xingqi); // 设置星期
		remoteViews.setTextViewText(R.id.w_date, date); // 设置日期
		remoteViews.setTextViewText(R.id.w_city_name, name); // 显示 城市
		// 创建一个pendingIntent
		pendingIntent = PendingIntent.getActivity(context, 0, intent1,
				PendingIntent.FLAG_UPDATE_CURRENT);
		// 绑定处理器，表示控件单击后，会启动pendingIntent。
		remoteViews.setOnClickPendingIntent(R.id.w_picture, pendingIntent);
		// 更新桌面。
		// 获得componentName。
		appwidgetManager = AppWidgetManager.getInstance(context);
		componentname = new ComponentName(context, WFWidgetProvider.class);
		// 更新：
		appwidgetManager.updateAppWidget(componentname, remoteViews);
		Log.d("输出", "WFWidgetProvider -------------onReceive");

		SelectedCityDAO selectedCityDAO = new SelectedCityDAO(context);
		City city = selectedCityDAO.query(city_num).get(0);
		String now = city.getNow();
		try {
			jsonObject = new JSONObject(now).getJSONObject("l");
			JSONObject forecast3d = new JSONObject(city.today)
					.getJSONObject("f"); // 获得预报天气里JSon对象
			JSONArray forecast = forecast3d.getJSONArray("f1");
			JSONObject forecast_0 = (JSONObject) forecast.opt(0);
			WD = jsonObject.optString("l1");
			pitrueId = Imageset.getimage(forecast_0.optString("fb"));
		} catch (JSONException e) {
			Log.d("输出", e.toString());
		}
		remoteViews.setTextViewText(R.id.w_wd, WD + "℃");
		remoteViews.setImageViewResource(R.id.w_picture, pitrueId);
		appwidgetManager = AppWidgetManager.getInstance(context);
		componentname = new ComponentName(context, WFWidgetProvider.class);
		appwidgetManager.updateAppWidget(componentname, remoteViews);
		Log.d("输出", "WFWidgetProvider -------------onReceive接受到了广播");

		super.onEnabled(context);
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		Log.d("输出", "WFWidgetProvider -------------onDeleted");
		Intent intent = new Intent();
		// context.stopService(intent.setClass(context,
		// AppWidgetService.class));
		super.onDeleted(context, appWidgetIds);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		transDate = new TransDate(context);
		day = transDate.yunsuan();
		xingqi = day.day_0;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
		// Calendar calendar = Calendar.getInstance();
		/*
		 * date = calendar.get(Calendar.HOUR_OF_DAY) + ":" +
		 * calendar.get(Calendar.MINUTE);
		 */
		date = simpleDateFormat.format(new Date());
		Intent intent1 = new Intent(context, MainActivity.class);
		preferences = context.getSharedPreferences(MyConfiguration, 0);
		name = preferences.getString("name", "beijing");
		city_num = preferences.getString("city_num", "101010100");
		// 创建一个remoteViews。
		remoteViews = new RemoteViews(context.getPackageName(),
				R.layout.wfwidgetprovider);
		remoteViews.setTextViewText(R.id.showxingqi, xingqi); // 设置星期
		remoteViews.setTextViewText(R.id.w_date, date); // 设置日期
		remoteViews.setTextViewText(R.id.w_city_name, name); // 显示 城市
/*		// 创建一个pendingIntent 点击唤醒主acivity
		pendingIntent = PendingIntent.getActivity(context, 0, intent1,
				PendingIntent.FLAG_UPDATE_CURRENT);
		// 绑定处理器，表示控件单击后，会启动pendingIntent。
		remoteViews.setOnClickPendingIntent(R.id.w_picture, pendingIntent);*/
//		TaskStackBuilder taskStackBuilder=TaskStackBuilder.create(context);
//		taskStackBuilder.addParentStack(MainActivity.class);
//		taskStackBuilder.addNextIntent(new Intent(context, MainActivity.class));
		//remoteViews.setOnClickFillInIntent(R.id.w_picture, );
		// 更新桌面。
		// 获得componentName。
		appwidgetManager = AppWidgetManager.getInstance(context);
		componentname = new ComponentName(context, WFWidgetProvider.class);
		// 更新：
		appwidgetManager.updateAppWidget(componentname, remoteViews);
		Log.d("输出", "WFWidgetProvider -------------onReceive");
		action = intent.getAction();
		if (action.equals("com.example.appwidget.WFWidgetProvider")) {
			SelectedCityDAO selectedCityDAO = new SelectedCityDAO(context);
			City city = selectedCityDAO.query(city_num).get(0);
			String now = city.getNow();
			try {
				jsonObject = new JSONObject(now).getJSONObject("l");
				JSONObject forecast3d = new JSONObject(city.today)
						.getJSONObject("f"); // 获得预报天气里JSon对象
				JSONArray forecast = forecast3d.getJSONArray("f1");
				JSONObject forecast_0 = (JSONObject) forecast.opt(0);
				WD = jsonObject.optString("l1");
				pitrueId = Imageset.getimage(forecast_0.optString("fb"));
			} catch (JSONException e) {
				Log.d("输出", e.toString());
			}
			remoteViews.setTextViewText(R.id.w_wd, WD + "℃");
			remoteViews.setImageViewResource(R.id.w_picture, pitrueId);
			appwidgetManager = AppWidgetManager.getInstance(context);
			componentname = new ComponentName(context, WFWidgetProvider.class);
			appwidgetManager.updateAppWidget(componentname, remoteViews);
			Log.d("输出", "WFWidgetProvider -------------onReceive接受到了广播");

		} else {
			super.onReceive(context, intent);
		}
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		Log.d("输出", "WFWidgetProvider -------------onUpdate");
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}

}
