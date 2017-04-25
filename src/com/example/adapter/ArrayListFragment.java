package com.example.adapter;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.Set.Imageset;
import com.example.activity.R;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.ArrayListFragment;
import com.example.dao.SelectedCityDAO;
import com.example.entity.All;
import com.example.entity.City;
import com.example.entity.Day;
import com.example.receiver.GetWeather;
import com.example.trans.TransDate;
import com.example.trans.WeatherInfoTransfer;

public class ArrayListFragment extends Fragment {

	private String hello;
	private String defaultHello = "default value";
	String info;// ���ӷ���ʵʱ����
	String info1;// �A����������
	String info2;// ����ָ��
	String i4;// ָ������
	String i5;// ����ָ�� i5
	Resources resources;
	TextView tempText;
	ImageView imageView;
	JSONObject json;// ��ȡʵʱ�������ݵ�����
	int imgeId;// ����ͼƬID
	View v;// ȫ��view
	TextView tv;// ��ʾ��������view
	TextView viewIndex;// ����ָ����view
	Context context;
	City city;
	String city_num;
	String name;
	SelectedCityDAO selectedCityDAO;
	List<City> listCities;
	All all;
	JSONObject forecast3d;// ���Ԥ��������JSon����
	JSONArray forecast;// �����
	JSONObject forecast_index_0;// index �����
	JSONObject forecast_0;// ��������Ԥ��
	JSONObject forecast_1;// �ڶ���
	JSONObject forecast_2;// ������
	JSONObject index_0;// ��һ������ָ��
	String TODAY;
	String TOMORROW;
	String HUOTIAN;
	Day day;
	long id;
	LayoutInflater inflater;// fragment�Ĳ����ļ�
	ViewGroup container;// ����
	ProgressDialog progressDialog;

	public ArrayListFragment newInstance(City city) {
		ArrayListFragment f = new ArrayListFragment();
		Bundle bundle = new Bundle();
		id = f.getId();
		bundle.putString("name", city.name);
		bundle.putString("city_num", city.city_num);
		bundle.putString("id", id + "");
		f.setArguments(bundle);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity().getApplicationContext();
		resources = getResources();
		Bundle args = getArguments();
		city_num = args.getString("city_num");
		name = args.getString("name");
		city = new City();
		city.city_num = city_num;
		city.name = name;
		hello = args != null ? args.getString("name") : defaultHello;
		// ��ѯ���ݿ��ȡ��������
		selectedCityDAO = new SelectedCityDAO(context);
		listCities = selectedCityDAO.query(city_num);
		// Log.d("���", name + "ArrayListFragmentonCreate" + listCities.size());
		// �����ݿ��л�ȡString�͵���������
		info = listCities.get(0).now;
		info1 = listCities.get(0).today;
		info2 = listCities.get(0).future;
		// ��string�͵�����װ����json��ʽ�Ķ���
		try {
			if (info != null && info1 != null && info2 != null) {
				json = new JSONObject(info).getJSONObject("l");
				forecast3d = new JSONObject(info1).getJSONObject("f"); // ���Ԥ��������JSon����
				forecast = forecast3d.getJSONArray("f1");
				forecast_0 = (JSONObject) forecast.opt(0);
				forecast_1 = (JSONObject) forecast.opt(1);
				forecast_2 = (JSONObject) forecast.opt(2);
				forecast_index_0 = new JSONObject(info2).getJSONArray("i")
						.getJSONObject(0);
				i4 = forecast_index_0.getString("i4");
				i5 = forecast_index_0.getString("i5");
			} else {

			}
		} catch (JSONException e) {

			e.printStackTrace();
		}
		day = new TransDate(context).yunsuan();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.container = container;
		this.inflater = inflater;
		v = inflater.inflate(R.layout.fragment_pager_list, container, false);
		viewIndex = (TextView) v.findViewById(R.id.Info_index);
		tv = (TextView) v.findViewById(R.id.text);
		/* Button button = (Button) v.findViewById(R.id.share_weather); */
		tv.setText(hello); // ��ʾ�������� ע����µļ�����
		initView();
		progressDialog=new ProgressDialog(getActivity());

		tv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Thread thread = new Thread(runnable);
				thread.start(); // ���������߳�
				progressDialog.setMessage("�������ڸ�����");
				progressDialog.show();
			}
		});
		return v;

	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				initView();
				progressDialog.hide();
				Toast.makeText(context, "�������³ɹ�", Toast.LENGTH_SHORT).show();
			}
			super.handleMessage(msg);
		}

	};
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			Message msg = new Message();
			GetWeather getWeather = new GetWeather();
			all = new All();
			try {
				// ��ȡ�����ַ���
				all = getWeather.GetALLWeaterContent(city.city_num);
				info = all.observe;
				info1 = all.focast3d;
				info2 = all.index;
				json = new JSONObject(info).getJSONObject("l");
				JSONObject forecast3d = new JSONObject(info1)
						.getJSONObject("f");// ���Ԥ��������JSon����
				forecast = forecast3d.getJSONArray("f1");
				forecast_0 = (JSONObject) forecast.opt(0);
				forecast_1 = (JSONObject) forecast.opt(1);
				forecast_2 = (JSONObject) forecast.opt(2);
				forecast_index_0 = new JSONObject(info2).getJSONArray("i")
						.getJSONObject(0);
				i4 = forecast_index_0.getString("i4");
				i5 = forecast_index_0.getString("i5");
				if (info != null && info1 != null & info2 != null) {
					selectedCityDAO.updata(city, info, info1, info2);
					Log.d("���", info + info1 + info2);
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

	public void initView() {
		try {
			// �õ�ʵʱ�¶�
			tempText = (TextView) v.findViewById(R.id.currentTemp);
			tempText.setText(json.optString("l1") + "��");
			// weather����״��
			tempText = (TextView) v.findViewById(R.id.weather);
			String a = forecast_0.optString("fa");
			if (forecast_0.optString("fa").equalsIgnoreCase("")) {
				tempText.setText("����:"
						+ WeatherInfoTransfer.getWeatherPhenomenon(forecast_0
								.optString("fb")));// "fb"����
			} else {
				tempText.setText("����:"
						+ WeatherInfoTransfer.getWeatherPhenomenon(forecast_0
								.optString("fa"))// "fa"����
						+ "����:"
						+ WeatherInfoTransfer.getWeatherPhenomenon(forecast_0
								.optString("fb")));// "fb"����
			}
			// �������
			tempText = (TextView) v.findViewById(R.id.WD);
			tempText.setText(WeatherInfoTransfer.getWindDirection(json
					.optString("l4")) + json.optString("l3") + "��");
			// ʪ��
			tempText = (TextView) v.findViewById(R.id.SD);
			tempText.setText("ʪ��:" + json.optString("l2") + "%");

			// ����ʱ��
			tempText = (TextView) v.findViewById(R.id.time);
			tempText.setText("����ʱ��:" + json.optString("l7"));
			// /////////Ԥ������
			// ����
			tempText = (TextView) v.findViewById(R.id.first_day);// ����
			tempText.setText(day.day_0);
			tempText = (TextView) v.findViewById(R.id.text_fc_00_d);// �����¶�
			tempText.setText(forecast_0.optString("fc") + "��");
			imageView = (ImageView) v.findViewById(R.id.imageView_n_00);// ��������ͼ
			// resources=getResources();
			imageView.setImageResource(Imageset.getimage(forecast_0
					.optString("fb")));
			tempText = (TextView) v.findViewById(R.id.text_fc_00_n);// ���������¶�
			tempText.setText(forecast_0.optString("fd") + "��");

			// ����
			tempText = (TextView) v.findViewById(R.id.second_day);// ����
			tempText.setText(day.day_1);
			tempText = (TextView) v.findViewById(R.id.text_fc_01_d);// �����¶�
			tempText.setText(forecast_1.optString("fc") + "��");
			imageView = (ImageView) v.findViewById(R.id.imageView_n_01);// ��������ͼƬ
			// resources=getResources();
			imageView.setImageResource(Imageset.getimage(forecast_1
					.optString("fb")));
			tempText = (TextView) v.findViewById(R.id.text_fc_01_n);// �����¶�
			tempText.setText(forecast_1.optString("fd") + "��");

			// ����
			tempText = (TextView) v.findViewById(R.id.third_day);// ����
			tempText.setText(day.day_2);
			tempText = (TextView) v.findViewById(R.id.text_fc_02_d);// �����¶�
			tempText.setText(forecast_2.optString("fc") + "��");
			imageView = (ImageView) v.findViewById(R.id.imageView_n_02);// ��������ͼƬ
			// resources=getResources();
			imageView.setImageResource(Imageset.getimage(forecast_2
					.optString("fb")));
			tempText = (TextView) v.findViewById(R.id.text_fc_02_n);// �����¶�
			tempText.setText(forecast_2.optString("fd") + "��");
			viewIndex.setText(i5);
			tempText = (TextView) v.findViewById(R.id.Info_index_level);
			tempText.setText("����ָ��:" + i4);
		} catch (Exception e) {
			Log.d("���", e.toString());
		}
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
		initView();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

	}
}
