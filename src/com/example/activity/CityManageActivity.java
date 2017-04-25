package com.example.activity;

import java.util.List;
import com.example.adapter.MyGridViewAdapter;
import com.example.dao.SelectedCityDAO;
import com.example.entity.City;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;

public class CityManageActivity extends Activity {
	SelectedCityDAO selectedCityDAO;
	City city;
	GridView gridview;
	MyGridViewAdapter myGridViewAdapter;
	List<City> listCities;
	/**
	 * 显示已经添加的城市
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		ActionBar bar=getActionBar();
		bar.hide();
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.city_manage);
		selectedCityDAO = new SelectedCityDAO(this);
		listCities = selectedCityDAO.query();
		gridview = (GridView) findViewById(R.id.gridview);
		myGridViewAdapter = new MyGridViewAdapter(this, listCities);
		gridview.setAdapter(myGridViewAdapter);
		gridview.setOnItemLongClickListener(new itemLongClick());
		gridview.setOnItemClickListener(new itemShortClick());
		ActivityManager manager = (ActivityManager) CityManageActivity.this
				.getSystemService(ACTIVITY_SERVICE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.city_manage_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		return super.onOptionsItemSelected(item);
	}

	/**
	 * 短按监听器
	 * 
	 * @author hai
	 * 
	 */
	public class itemShortClick implements OnItemClickListener {

		@Override
		//跳转到主
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			TaskStackBuilder taskStackBuilder=TaskStackBuilder.create(CityManageActivity.this);
			taskStackBuilder.addParentStack(MainActivity.class);
			Intent intent=new Intent();
			intent.putExtra("position", position);
			intent.setClass(CityManageActivity.this, MainActivity.class);
			taskStackBuilder.addNextIntent(intent);
			taskStackBuilder.startActivities();
			//finish();
		//	startActivity(intent);
		}

	}

	/**
	 * 长按监听器
	 * 
	 * @author hai
	 * 
	 */
	public class itemLongClick implements OnItemLongClickListener {

		@Override
		public boolean onItemLongClick(AdapterView<?> parent, View view,
				int position, long id) {
			city = (City) gridview.getItemAtPosition(position);
			selectedCityDAO = new SelectedCityDAO(CityManageActivity.this);
			if(selectedCityDAO.query().size()>1){
				selectedCityDAO.delete(city);
				// Toast.makeText(CityManageActivity.this, "长按" + city.name,
				// Toast.LENGTH_SHORT).show();
				// //myGridViewAdapter = (MyGridViewAdapter) gridview.getAdapter();
				listCities.remove(position);				
			}
			else{
				AlertDialog.Builder builder=new Builder(CityManageActivity.this);
				builder.setPositiveButton("確定", null);
				builder.setTitle("提醒");
				builder.setMessage("已經是最後一個城市不能刪除o(╯□╰)o");
				builder.show();
				builder.create();
			}
			myGridViewAdapter.notifyDataSetChanged();
			myGridViewAdapter = new MyGridViewAdapter(CityManageActivity.this,
					listCities);
			gridview.setAdapter(myGridViewAdapter);
			return true;
		}
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			finish();
			Intent intent=new Intent(CityManageActivity.this, SetActivity.class);
			startActivity(intent);
		}
		return super.onKeyDown(keyCode, event);
	}
}
