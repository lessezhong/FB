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
	 * ͨ��show city activity�Ĵ����¼���ȡ�������ݲ��������ݲ������ݿ�
	 */
	String info;// ���ӷ���ʵʱ����
	String info1;// ���ӷ��ص�����������
	String info2;// ���ӷ���δ�����������
	TextView tempText;
	ImageView imageView;
	JSONObject json;// observer
	JSONObject json1;// ����
	JSONObject json2;// index
	Context context = null;;
	DataBaseHelper_1 baseHelper_1 = null;
	SQLiteDatabase db = null;
	String DB_NAME = "selected_city.db";// �Ѿ�ѡ����е����ݿ�
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
			// ���� DB_NAME ���ݿ�
			baseHelper_1 = new DataBaseHelper_1(context, DB_NAME, null, 1);
			db = baseHelper_1.getReadableDatabase();
			selectedCityDAO = new SelectedCityDAO(context);
			city = new City();
			all = new All();
			// ��ȡshowcity��������ֵ
			city.city_num = intent.getStringExtra("city_num");
			city.name = intent.getStringExtra("name");
			progressDialog=new ProgressDialog(this.context);
			progressDialog.setMessage("�������ڸ�����");
			progressDialog.show();
			Thread thread = new Thread(r);
			thread.start();
			Log.d("���", "onReceive��ǰ����id������������>"
					+ Thread.currentThread().getId());
		} else {
			Toast.makeText(context, "������翪�˵�С�O(��_��)O~", Toast.LENGTH_SHORT)
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
				Toast.makeText(context, "��ӳɹ�", Toast.LENGTH_SHORT).show();
				break;

			case 2:
				Toast.makeText(context, "���������ƺ��е�����ŶO(��_��)O",
						Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};
	Runnable r = new Runnable() {
		
		@Override
		public void run() {
			Log.d("���", "��ǰr����ID----->" + Thread.currentThread().getId());
			Message msg = new Message();
			GetWeather getWeather = new GetWeather();
			try {
				// ��ȡ�����ַ���
				all = getWeather.GetALLWeaterContent(city.city_num);
				info = all.observe;
				info1 = all.focast3d;
				info2 = all.index;
				Log.d("���", info);
				Log.d("���", info1);
				Log.d("���", info2);
				// =����JSON�õ�����===��info�л�ȡJSONObject����json
				json = new JSONObject(info).getJSONObject("l");
				json1 = new JSONObject(info1).getJSONObject("f");
				JSONArray jsonArray;
				jsonArray = new JSONObject(info2).getJSONArray("i");// ��ȡjson����
				json2 = jsonArray.getJSONObject(0);
				if (json != null && json1 != null && json2 != null) {
					if(selectedCityDAO.query().size()<1){
						MyConfiguration configuration=new MyConfiguration(context);//�������ѵĳ��д��������ļ�
						configuration.newPreference(city.name, city.city_num);
					}
					selectedCityDAO.insert(city, info, info1, info2);
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

}
