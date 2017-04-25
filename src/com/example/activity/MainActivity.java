package com.example.activity;

import java.util.ArrayList;
import java.util.List;

import others.BDLocationTool;
import android.app.AlertDialog;
import android.app.LauncherActivity;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.Set.MyConfiguration;
import com.example.adapter.ArrayListFragment;
import com.example.adapter.MyFragmentPagerAdapter;
import com.example.appwidgetservice.AppWidgetService;
import com.example.dao.SelectedCityDAO;
import com.example.entity.City;
import com.example.receiver.GetCityNumReceiver;
import com.example.receiver.WeatherUpdataReceiver;
import com.example.tools.CapturePicture;

public class MainActivity extends FragmentActivity {
	private static final int SetActivity = 1;
	Context context;
	MyFragmentPagerAdapter mAdapter;
	ViewPager mPager;
	List<City> listCities;
	public ArrayList<Fragment> listFragments;
	GetCityNumReceiver getCityNumReceiver;
	LauncherActivity launcherActivity;
	SelectedCityDAO selectedCityDAO;
	int NUM_ITEMS;
	long exitTime = 0;
	int flag = 0;
	public static ArrayListFragment arrayListFragment;// ��ǰactivity��fragment
	public static View v;// ����fragment��view
	Intent backGroundUpdateintent;// ��̨��λservice intent
	public LinearLayout rootView;
	String locatInfon;// ��λ��Ϣ
	WeatherUpdataReceiver weatherUpdataReceiver;
	MyConfiguration myConfiguration;
	SharedPreferences sharedPreferences;
	SharedPreferences.Editor editor;
	ProgressDialog progressDialog;
	String simpleName;
	IntentFilter intentFilter ;//�������غ�̨service�����ĸ��¹㲥

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("���", "dangqianid  oncreate" + Thread.currentThread().getId()
				+ "");
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		// ActionBar actionBar = getActionBar();
		// actionBar.hide();
		setContentView(R.layout.activity_main);
		rootView = (LinearLayout) findViewById(R.id.rootView);
		context = MainActivity.this;
		mPager = (ViewPager) findViewById(R.id.pager);
		Log.d("���", Thread.currentThread().getId() + "");
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
		// ����ϵ�y�������ļ�
		// MyConfiguration myConfiguration = new MyConfiguration(context);
		// myConfiguration.newPreference("����", "101010100");
		// ���ü��ȡ�������ݵ�receiver
		getCityNumReceiver = new GetCityNumReceiver();
		intentFilter = new IntentFilter();
		intentFilter.addAction(Intent.ACTION_EDIT);
		MainActivity.this.registerReceiver(getCityNumReceiver, intentFilter);
		weatherUpdataReceiver = new WeatherUpdataReceiver();
		IntentFilter intentFilter2 = new IntentFilter();
		intentFilter2.addAction("weatherUpdataReceiver");
		MainActivity.this
				.registerReceiver(weatherUpdataReceiver, intentFilter2);
		myConfiguration = new MyConfiguration(context);
		sharedPreferences = myConfiguration.readPreference();
		if (sharedPreferences.getInt("freshTime", -1) > 0) {
			// ���� AppService
			backGroundUpdateintent = new Intent(context, AppWidgetService.class);
			getApplicationContext().startService(backGroundUpdateintent);
			
		}

		/**
		 * ����fragment
		 */
		// initfragment();

	}

	@Override
	protected void onResume() {
		/* CityMangerActivity�Ķ�λ */
		Intent data = getIntent();
		initfragment();
		mPager.setCurrentItem(data.getIntExtra("position", 0));
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		return true;
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		Log.d("���", "��ǰonActivityResult����ID----->"
				+ Thread.currentThread().getId());
		initfragment();

	}

	@Override
	protected void onDestroy() {
		if (backGroundUpdateintent != null) {
			getApplication().getApplicationContext().stopService(backGroundUpdateintent);
		}
		super.onDestroy();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case R.id.main_menu_shezhi:
			Log.d("���", "��ǰ����onOptionsItemSelected----->"
					+ Thread.currentThread().getId());
			// finish();
			Intent intent = new Intent(MainActivity.this, SetActivity.class);
			// �����Ը�
			// startActivity(intent);
			startActivityForResult(intent, SetActivity);
			return true;
		case R.id.main_menu_exit:
			Dialog();
			return true;
			/* ����ť */
		case R.id.main_menu_share:
			String today;// ��������
			arrayListFragment = new ArrayListFragment();
			v = new View(context);
			arrayListFragment = (ArrayListFragment) listFragments.get(flag);
			// arrayListFragment = (ArrayListFragment) mAdapter.getItem(flag);
			v = arrayListFragment.getView();
			v.setDrawingCacheEnabled(true);
			Bitmap bitmap = v.getDrawingCache();
			String pictruePath = CapturePicture.Capture(bitmap);

			today = (String) ((TextView) v.findViewById(R.id.text)).getText()
					+ ","
					+ ((TextView) v.findViewById(R.id.currentTemp)).getText()
					+ "," + ((TextView) v.findViewById(R.id.weather)).getText()
					+ "," + ((TextView) v.findViewById(R.id.WD)).getText()
					+ ","
					+ ((TextView) v.findViewById(R.id.second_day)).getText()
					+ ","
					+ ((TextView) v.findViewById(R.id.text_fc_01_d)).getText()
					+ ","
					+ ((TextView) v.findViewById(R.id.text_fc_01_n)).getText()
					+ ","
					+ ((TextView) v.findViewById(R.id.third_day)).getText()
					+ ","
					+ ((TextView) v.findViewById(R.id.text_fc_02_d)).getText()
					+ ","
					+ ((TextView) v.findViewById(R.id.text_fc_02_n)).getText();
			Intent intent2 = new Intent();
			intent2.putExtra("today", today);
			intent2.putExtra(Intent.EXTRA_TEXT, "Shared from the ��������;" + today);
			intent2.putExtra("pictruePath", pictruePath);
			intent2.setClass(context, ShareActivity.class);
			startActivity(intent2);
			return true;
		case R.id.main_menu_shuaxin:
			Dialog_2();
			return true;
		default:
			return false;
		}
	}

	/**
	 * ʵ����fragment
	 */
	void initfragment() {
		selectedCityDAO = new SelectedCityDAO(context);
		listCities = selectedCityDAO.query();
		NUM_ITEMS = listCities.size();
		listFragments = new ArrayList<Fragment>();
		mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),
				listFragments);
		mPager.setAdapter(mAdapter);
		if (listCities.size() == 0) {
			BDLocationTool bdLocationTool = new BDLocationTool(context);
			if (sharedPreferences.getString("location", null) == null
					|| sharedPreferences.getString("location", null) == "") {
				bdLocationTool.StartLoact();
			}

		} else {
			for (int i = 0; i < NUM_ITEMS; i++) {
				City city = listCities.get(i);
				Log.d("���", city.name);
				Fragment fragment = new ArrayListFragment().newInstance(city);
				listFragments.add(fragment);
			}
		}

	}

	private void Dialog() {
		AlertDialog.Builder builder = new Builder(MainActivity.this);
		builder.setTitle("����");
		builder.setMessage("ȷ��Ҫ�˳���");
		builder.setPositiveButton("Yes", new exit());
		builder.setNegativeButton("No", null);
		builder.show();
		builder.create();
	}

	void Dialog_2() {
		AlertDialog.Builder builder = new Builder(MainActivity.this);
		builder.setTitle("˵��");
		builder.setMessage("������и�������");
		// builder.setPositiveButton("��", null);
		builder.show();
		builder.create();
	}

	/* ��ת����ʾʡ�ݵ�activity */
	class chosecity implements DialogInterface.OnClickListener {
		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			Intent intent = new Intent();
			intent.putExtra("name", "");
			intent.setClass(MainActivity.this, ShowProvinceActivity.class);
			startActivity(intent);
		}
	}

	/* ��ҳʱ�ļ����� */
	public class MyOnPageChangeListener implements OnPageChangeListener {
		// Resources resources = getResources();
		Message msg = new Message();
		Handler handler = new Handler() {

			@Override
			public void handleMessage(Message msg) {
				switch (msg.arg1) {
				case 0:
					rootView.setBackgroundResource(R.drawable.background_00);
					Log.e("���", "" + msg.arg1);
					Log.e("���", "dangqianid  :::"
							+ Thread.currentThread().getId() + "");
					break;
				case 1:
					rootView.setBackgroundResource(R.drawable.background_01);
					Log.e("���", "" + msg.arg1);
					Log.e("���", "dangqianid  :::"
							+ Thread.currentThread().getId() + "");
					break;
				case 2:
					rootView.setBackgroundResource(R.drawable.background_02);
					Log.e("���", "" + msg.arg1);
					break;
				case 3:
					Log.e("���", "dangqianid  :::"
							+ Thread.currentThread().getId() + "");
					rootView.setBackgroundResource(R.drawable.background_03);
					Log.e("���", "" + msg.arg1);
					break;
				case 4:
					rootView.setBackgroundResource(R.drawable.background_04);
					break;

				default:
					rootView.setBackgroundResource(R.drawable.background_01);
					break;
				}

				super.handleMessage(msg);
			}

		};
		Runnable thread = new Runnable() {

			@Override
			public void run() {
				switch (flag) {
				case 0:
					msg.arg1 = 0;
					break;
				case 1:
					msg.arg1 = 1;
					break;
				case 2:
					msg.arg1 = 2;
					break;

				case 3:
					msg.arg1 = 3;
					break;

				case 4:
					msg.arg1 = 4;
					break;
				default:

					break;
				}
				handler.sendMessage(msg);

			}
		};

		public void onPageScrollStateChanged(int arg0) {

		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageSelected(int arg0) {
			// flag = mPager.getCurrentItem();
			// msg = new Message();
			// Thread thread1 = new Thread(thread);
			// thread1.start();
			// handler.postDelayed(thread, 0);
			final String vo[] = { "ui" };
			AsyncTask<String, Void, Void> ui = new AsyncTask<String, Void, Void>() {
				@Override
				protected Void doInBackground(String... params) {
					flag = mPager.getCurrentItem();
					msg = new Message();
					Thread thread1 = new Thread(thread);
					thread1.start();
					return null;
				}

			};
			ui.execute(vo[0]);

		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
				return false;
			} else {
				finish();
				// System.exit(0);
			}

		}
		if (keyCode == KeyEvent.KEYCODE_HOME) {
			finish();
			Log.d("���", "home");
		}
		return super.onKeyDown(keyCode, event);
	}

	class exit implements DialogInterface.OnClickListener {
		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			MainActivity.this.unregisterReceiver(getCityNumReceiver);
			java.lang.System.exit(0);

		}
	}

}
