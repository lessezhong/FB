package com.example.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import com.example.Set.MyConfiguration;
import com.example.appwidgetservice.AppWidgetService;
import com.example.dao.SelectedCityDAO;
import com.example.entity.City;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class RefreshActivty extends Activity {
	CheckBox checkBoxRefresh;
	Intent intentService;
	Context context;
	Boolean isChecked;
	SharedPreferences sharedPreferences;
	SharedPreferences.Editor editor;
	EditText editText;// 输入时间的
	Button buttonOk;// 确认配置
	int freshTime;// 更新时间
	TextView timer;// 显示当前的更新时间
	TextView v;
	TextView v2;
	String ChosedCity;// 配置文件中的City
	SimpleAdapter simpleAdapter;
	List<HashMap<String, String>> listHashMaps;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.refreshactivty);
		context = RefreshActivty.this;
		// 读取配置文件
		SelectedCityDAO selectedCityDAO = new SelectedCityDAO(context);
		List<City> citiesList = selectedCityDAO.query();
		listHashMaps = new ArrayList<HashMap<String, String>>();
		for (Iterator<City> iterator = citiesList.iterator(); iterator
				.hasNext();) {
			HashMap<String, String> hashMap = new HashMap<String, String>();
			City city = (City) iterator.next();
			hashMap.put("city_num", city.getCity_num());
			hashMap.put("name", city.getName());
			listHashMaps.add(hashMap);
		}
		simpleAdapter = new SimpleAdapter(this, listHashMaps,
				R.layout.province, new String[] { "name" },
				new int[] { R.id.name });

		sharedPreferences = new MyConfiguration(context).readPreference();
		isChecked = sharedPreferences.getBoolean("ischecked", false);
		ChosedCity = sharedPreferences.getString("name", null);
		Thread thread = new Thread(runnable);
		thread.start();
		// 写配置文件
		editor = new MyConfiguration(context).editPreference();
		intentService = new Intent();
		intentService.setClass(context, AppWidgetService.class);
		checkBoxRefresh = (CheckBox) findViewById(R.id.checkBoxRefresh); // 选着按钮
		checkBoxRefresh.setOnCheckedChangeListener(new MyCheckBox());
		editText = (EditText) findViewById(R.id.fresh_time); // 编辑时间的控件
		buttonOk = (Button) findViewById(R.id.MyConfigurationConfirm);
		buttonOk.setOnClickListener(new MyConfigurationConfirm());
		v2 = (TextView) findViewById(R.id.updataCity_2);
		v2.setText(ChosedCity);
		v = (TextView) findViewById(R.id.updataCity_1);
		v.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				AlertDialog.Builder builder = new Builder(context);
				builder.setTitle("选择你的城市");
				builder.setAdapter(simpleAdapter,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface arg0, int arg1) {
								// context.stopService(intentService);

								MyConfiguration configuration = new MyConfiguration(
										context);
								String name = listHashMaps.get(arg1)
										.get("name");
								String city_num = listHashMaps.get(arg1).get(
										"city_num");
								configuration.newPreference(name, city_num);
								v2.setText(name);
								Toast.makeText(context, arg1 + "",
										Toast.LENGTH_SHORT).show();
								Intent intent = new Intent();
								intent.setAction("com.example.appwidget.WFWidgetProvider");
								sendBroadcast(intent);
								// context.startService(intentService);
							}
						});
				builder.show();

			}
		});
		timer = (TextView) findViewById(R.id.timer);// 显示当前更新的时间
		String time = sharedPreferences.getInt("freshTime", -1) + "";
		timer.setText(time);

	}

	// MyCheckBox监听器
	class MyCheckBox implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (checkBoxRefresh.isChecked()) {
				editText.setVisibility(View.VISIBLE);

			} else {
				editText.setVisibility(View.INVISIBLE);
			}

		}

	}

	// 提交的button 监听器
	class MyConfigurationConfirm implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (!(editText.getText().toString()).equals("")
					&& (editText.getText().toString()) != null
					&& checkBoxRefresh.isChecked() == true) {
				freshTime = Integer.parseInt(editText.getText().toString()
						.trim());// 更新时间
				if (freshTime > 0) {
					editor.putBoolean("ischecked", true);
					editor.putInt("freshTime", freshTime);
					editor.commit();
					//context.stopService(intentService);
					context.startService(intentService);

				} else {
					Toast.makeText(context, "请输入数字大于0", Toast.LENGTH_SHORT)
							.show();
				}

			}
			if (checkBoxRefresh.isChecked() == false) {
				context.stopService(intentService);
				editor.putBoolean("ischecked", false);
				editor.putInt("freshTime", -1);
				editor.commit();
				context.stopService(intentService);
			}

			Dialog();
		}

	}

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			if (msg.arg1 == 1) {
				checkBoxRefresh.setChecked(isChecked);
			}
			super.handleMessage(msg);
		}

	};
	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			Message msg = new Message();
			if (isChecked == true) {
				msg.arg1 = 1;
			}
			handler.sendMessage(msg);

		}
	};

	// 提示对话框
	void Dialog() {
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle("说明");
		builder.setMessage("配置保存成功");
		builder.setPositiveButton("OK", new finish());
		builder.show();
		builder.create();
	}

	/*
	 * // 选择城市列表 class chooseCity implements DialogInterface.OnClickListener {
	 * 
	 * @Override public void onClick(DialogInterface arg0, int arg1) {
	 * AlertDialog.Builder builder = new Builder(context);
	 * builder.setTitle("选择你的城市"); builder.setItems(list, null); } }
	 */

	class finish implements DialogInterface.OnClickListener {
		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			finish();
		}
	}

}
