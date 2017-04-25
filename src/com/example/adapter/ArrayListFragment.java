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
	String info;// 链接返回实时数据
	String info1;// 預報天气数据
	String info2;// 穿衣指數
	String i4;// 指数级别
	String i5;// 穿衣指数 i5
	Resources resources;
	TextView tempText;
	ImageView imageView;
	JSONObject json;// 获取实时天气数据的链接
	int imgeId;// 天气图片ID
	View v;// 全局view
	TextView tv;// 显示城市名的view
	TextView viewIndex;// 穿衣指数的view
	Context context;
	City city;
	String city_num;
	String name;
	SelectedCityDAO selectedCityDAO;
	List<City> listCities;
	All all;
	JSONObject forecast3d;// 获得预报天气里JSon对象
	JSONArray forecast;// 组对象
	JSONObject forecast_index_0;// index 组对象
	JSONObject forecast_0;// 当天天气预报
	JSONObject forecast_1;// 第二天
	JSONObject forecast_2;// 第三天
	JSONObject index_0;// 第一个穿衣指数
	String TODAY;
	String TOMORROW;
	String HUOTIAN;
	Day day;
	long id;
	LayoutInflater inflater;// fragment的布局文件
	ViewGroup container;// 容器
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
		// 查询数据库获取天气数据
		selectedCityDAO = new SelectedCityDAO(context);
		listCities = selectedCityDAO.query(city_num);
		// Log.d("输出", name + "ArrayListFragmentonCreate" + listCities.size());
		// 从数据库中获取String型的天气数据
		info = listCities.get(0).now;
		info1 = listCities.get(0).today;
		info2 = listCities.get(0).future;
		// 将string型的数据装换成json格式的对象
		try {
			if (info != null && info1 != null && info2 != null) {
				json = new JSONObject(info).getJSONObject("l");
				forecast3d = new JSONObject(info1).getJSONObject("f"); // 获得预报天气里JSon对象
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
		tv.setText(hello); // 显示城市名称 注册更新的监听器
		initView();
		progressDialog=new ProgressDialog(getActivity());

		tv.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Thread thread = new Thread(runnable);
				thread.start(); // 启动更新线程
				progressDialog.setMessage("天气正在更新中");
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
				Toast.makeText(context, "天气更新成功", Toast.LENGTH_SHORT).show();
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
				// 获取天气字符串
				all = getWeather.GetALLWeaterContent(city.city_num);
				info = all.observe;
				info1 = all.focast3d;
				info2 = all.index;
				json = new JSONObject(info).getJSONObject("l");
				JSONObject forecast3d = new JSONObject(info1)
						.getJSONObject("f");// 获得预报天气里JSon对象
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
					Log.d("输出", info + info1 + info2);
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

	public void initView() {
		try {
			// 得到实时温度
			tempText = (TextView) v.findViewById(R.id.currentTemp);
			tempText.setText(json.optString("l1") + "°");
			// weather天气状况
			tempText = (TextView) v.findViewById(R.id.weather);
			String a = forecast_0.optString("fa");
			if (forecast_0.optString("fa").equalsIgnoreCase("")) {
				tempText.setText("晚上:"
						+ WeatherInfoTransfer.getWeatherPhenomenon(forecast_0
								.optString("fb")));// "fb"晚上
			} else {
				tempText.setText("白天:"
						+ WeatherInfoTransfer.getWeatherPhenomenon(forecast_0
								.optString("fa"))// "fa"白天
						+ "晚上:"
						+ WeatherInfoTransfer.getWeatherPhenomenon(forecast_0
								.optString("fb")));// "fb"晚上
			}
			// 风向风力
			tempText = (TextView) v.findViewById(R.id.WD);
			tempText.setText(WeatherInfoTransfer.getWindDirection(json
					.optString("l4")) + json.optString("l3") + "级");
			// 湿度
			tempText = (TextView) v.findViewById(R.id.SD);
			tempText.setText("湿度:" + json.optString("l2") + "%");

			// 发布时间
			tempText = (TextView) v.findViewById(R.id.time);
			tempText.setText("更新时间:" + json.optString("l7"));
			// /////////预报部分
			// 今天
			tempText = (TextView) v.findViewById(R.id.first_day);// 星期
			tempText.setText(day.day_0);
			tempText = (TextView) v.findViewById(R.id.text_fc_00_d);// 白天温度
			tempText.setText(forecast_0.optString("fc") + "°");
			imageView = (ImageView) v.findViewById(R.id.imageView_n_00);// 晚上气象图
			// resources=getResources();
			imageView.setImageResource(Imageset.getimage(forecast_0
					.optString("fb")));
			tempText = (TextView) v.findViewById(R.id.text_fc_00_n);// 晚上天气温度
			tempText.setText(forecast_0.optString("fd") + "°");

			// 明天
			tempText = (TextView) v.findViewById(R.id.second_day);// 星期
			tempText.setText(day.day_1);
			tempText = (TextView) v.findViewById(R.id.text_fc_01_d);// 白天温度
			tempText.setText(forecast_1.optString("fc") + "°");
			imageView = (ImageView) v.findViewById(R.id.imageView_n_01);// 晚上气象图片
			// resources=getResources();
			imageView.setImageResource(Imageset.getimage(forecast_1
					.optString("fb")));
			tempText = (TextView) v.findViewById(R.id.text_fc_01_n);// 晚上温度
			tempText.setText(forecast_1.optString("fd") + "°");

			// 后天
			tempText = (TextView) v.findViewById(R.id.third_day);// 星期
			tempText.setText(day.day_2);
			tempText = (TextView) v.findViewById(R.id.text_fc_02_d);// 白天温度
			tempText.setText(forecast_2.optString("fc") + "°");
			imageView = (ImageView) v.findViewById(R.id.imageView_n_02);// 晚上气象图片
			// resources=getResources();
			imageView.setImageResource(Imageset.getimage(forecast_2
					.optString("fb")));
			tempText = (TextView) v.findViewById(R.id.text_fc_02_n);// 晚上温度
			tempText.setText(forecast_2.optString("fd") + "°");
			viewIndex.setText(i5);
			tempText = (TextView) v.findViewById(R.id.Info_index_level);
			tempText.setText("天气指数:" + i4);
		} catch (Exception e) {
			Log.d("输出", e.toString());
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
