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
	String DB_NAME = "selected_city.db";// �Ѿ�ѡ����е����ݿ�
	DataBaseHelper_1 baseHelper_1;
	SQLiteDatabase db;
	SelectedCityDAO selectedCityDAO;
	boolean b;// ����״̬
	ProgressDialog progressDialog;

	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;
		GetNetWorkState getNetWorkState = new GetNetWorkState(context);
		b = getNetWorkState.getNetWorkState();
		if (b == true) {
			// ���� DB_NAME ���ݿ�
			baseHelper_1 = new DataBaseHelper_1(context, DB_NAME, null, 1);
			db = baseHelper_1.getReadableDatabase();
			selectedCityDAO = new SelectedCityDAO(context);
			city = new City();
			all = new All();
			// ��ȡshowcity��������ֵ
			city.city_num = intent.getStringExtra("city_num");
			city.name = intent.getStringExtra("name");
			// Toast.makeText(context, intent.getStringExtra("name") + "����������",
			// Toast.LENGTH_SHORT).show();
			// ��ʾ���ڸ�ϸ��ʾ��
			progressDialog = new ProgressDialog(context);
			progressDialog.setMessage("�������ڸ�����");
			progressDialog.show();
			Thread thread = new Thread(r);
			thread.start();
			Log.d("���", "WeatherUpdataReceiver������������>onReceive"
					+ Thread.currentThread().getId());

		} else {
			Toast.makeText(context, "������翪�˵�С�O(��_��)O~", Toast.LENGTH_SHORT)
					.show();
		}

	}

	Runnable r = new Runnable() {

		@Override
		public void run() {
			Log.d("���", "��ǰr����ID----->" + Thread.currentThread().getId());
			Message msg = new Message();
			GetWeather getWeather = new GetWeather();
			try {
				// ��ȡ�����ַ���
				all = getWeather.GetALLWeaterContent(city.city_num);
				String info = all.observe;
				String info1 = all.focast3d;
				String info2 = all.index;
				Log.d("���", info);
				Log.d("���", info1);
				Log.d("���", info2);
				if (info != null && info1 != null && info2 != null) {
					if (selectedCityDAO.query().size() < 1) {
						MyConfiguration configuration = new MyConfiguration(
								context);// �������ѵĳ��д��������ļ�
						configuration.newPreference(city.name, city.city_num);
					}
					selectedCityDAO.updata(city, info, info1, info2);
					msg.what = 1;
				} else {
					msg.what = 2;
				}

				handler.sendMessage(msg);
			} catch (Exception e) {
				Log.d("���", e.toString());
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
				Toast.makeText(context, "�����������³ɹ�", Toast.LENGTH_SHORT).show();
				break;

			case 2:
				progressDialog.hide();
				Toast.makeText(context, "���������ƺ��е�����ŶO(��_��)O",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};

}
