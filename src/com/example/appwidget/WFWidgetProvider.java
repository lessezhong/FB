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
	String action;// ���ܵ��Ĺ㲥
	String xingqi;
	RemoteViews remoteViews;
	PendingIntent pendingIntent;// ���������������Ժ��ٽ���
	ComponentName componentname;
	SharedPreferences preferences;
	String name;// ������
	String city_num;// ������
	String WD;// �����¶�
	JSONObject jsonObject;
	int pitrueId;
	String date;// ����

	@Override
	public void onEnabled(Context context) {

		Log.d("���", "WFWidgetProvider onEnabled-------------onEnabled");

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
		// ����һ��remoteViews��
		remoteViews = new RemoteViews(context.getPackageName(),
				R.layout.wfwidgetprovider);
		remoteViews.setTextViewText(R.id.showxingqi, xingqi); // ��������
		remoteViews.setTextViewText(R.id.w_date, date); // ��������
		remoteViews.setTextViewText(R.id.w_city_name, name); // ��ʾ ����
		// ����һ��pendingIntent
		pendingIntent = PendingIntent.getActivity(context, 0, intent1,
				PendingIntent.FLAG_UPDATE_CURRENT);
		// �󶨴���������ʾ�ؼ������󣬻�����pendingIntent��
		remoteViews.setOnClickPendingIntent(R.id.w_picture, pendingIntent);
		// �������档
		// ���componentName��
		appwidgetManager = AppWidgetManager.getInstance(context);
		componentname = new ComponentName(context, WFWidgetProvider.class);
		// ���£�
		appwidgetManager.updateAppWidget(componentname, remoteViews);
		Log.d("���", "WFWidgetProvider -------------onReceive");

		SelectedCityDAO selectedCityDAO = new SelectedCityDAO(context);
		City city = selectedCityDAO.query(city_num).get(0);
		String now = city.getNow();
		try {
			jsonObject = new JSONObject(now).getJSONObject("l");
			JSONObject forecast3d = new JSONObject(city.today)
					.getJSONObject("f"); // ���Ԥ��������JSon����
			JSONArray forecast = forecast3d.getJSONArray("f1");
			JSONObject forecast_0 = (JSONObject) forecast.opt(0);
			WD = jsonObject.optString("l1");
			pitrueId = Imageset.getimage(forecast_0.optString("fb"));
		} catch (JSONException e) {
			Log.d("���", e.toString());
		}
		remoteViews.setTextViewText(R.id.w_wd, WD + "��");
		remoteViews.setImageViewResource(R.id.w_picture, pitrueId);
		appwidgetManager = AppWidgetManager.getInstance(context);
		componentname = new ComponentName(context, WFWidgetProvider.class);
		appwidgetManager.updateAppWidget(componentname, remoteViews);
		Log.d("���", "WFWidgetProvider -------------onReceive���ܵ��˹㲥");

		super.onEnabled(context);
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		Log.d("���", "WFWidgetProvider -------------onDeleted");
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
		// ����һ��remoteViews��
		remoteViews = new RemoteViews(context.getPackageName(),
				R.layout.wfwidgetprovider);
		remoteViews.setTextViewText(R.id.showxingqi, xingqi); // ��������
		remoteViews.setTextViewText(R.id.w_date, date); // ��������
		remoteViews.setTextViewText(R.id.w_city_name, name); // ��ʾ ����
/*		// ����һ��pendingIntent ���������acivity
		pendingIntent = PendingIntent.getActivity(context, 0, intent1,
				PendingIntent.FLAG_UPDATE_CURRENT);
		// �󶨴���������ʾ�ؼ������󣬻�����pendingIntent��
		remoteViews.setOnClickPendingIntent(R.id.w_picture, pendingIntent);*/
//		TaskStackBuilder taskStackBuilder=TaskStackBuilder.create(context);
//		taskStackBuilder.addParentStack(MainActivity.class);
//		taskStackBuilder.addNextIntent(new Intent(context, MainActivity.class));
		//remoteViews.setOnClickFillInIntent(R.id.w_picture, );
		// �������档
		// ���componentName��
		appwidgetManager = AppWidgetManager.getInstance(context);
		componentname = new ComponentName(context, WFWidgetProvider.class);
		// ���£�
		appwidgetManager.updateAppWidget(componentname, remoteViews);
		Log.d("���", "WFWidgetProvider -------------onReceive");
		action = intent.getAction();
		if (action.equals("com.example.appwidget.WFWidgetProvider")) {
			SelectedCityDAO selectedCityDAO = new SelectedCityDAO(context);
			City city = selectedCityDAO.query(city_num).get(0);
			String now = city.getNow();
			try {
				jsonObject = new JSONObject(now).getJSONObject("l");
				JSONObject forecast3d = new JSONObject(city.today)
						.getJSONObject("f"); // ���Ԥ��������JSon����
				JSONArray forecast = forecast3d.getJSONArray("f1");
				JSONObject forecast_0 = (JSONObject) forecast.opt(0);
				WD = jsonObject.optString("l1");
				pitrueId = Imageset.getimage(forecast_0.optString("fb"));
			} catch (JSONException e) {
				Log.d("���", e.toString());
			}
			remoteViews.setTextViewText(R.id.w_wd, WD + "��");
			remoteViews.setImageViewResource(R.id.w_picture, pitrueId);
			appwidgetManager = AppWidgetManager.getInstance(context);
			componentname = new ComponentName(context, WFWidgetProvider.class);
			appwidgetManager.updateAppWidget(componentname, remoteViews);
			Log.d("���", "WFWidgetProvider -------------onReceive���ܵ��˹㲥");

		} else {
			super.onReceive(context, intent);
		}
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		Log.d("���", "WFWidgetProvider -------------onUpdate");
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}

}
