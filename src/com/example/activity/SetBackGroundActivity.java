package com.example.activity;

import com.example.fragment.Fragment1;
import com.example.fragment.Fragment2;
import com.example.fragment.Fragment3;
import com.loopj.android.http.AsyncHttpClient;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

public class SetBackGroundActivity extends FragmentActivity implements OnPageChangeListener {
	/**
	 * FragmentTabhost
	 */
	private FragmentTabHost mTabHost;

	/**
	 * ���������
	 * 
	 */
	private LayoutInflater mLayoutInflater;

	/**
	 * Fragment�������
	 * 
	 */
	private Class<?> mFragmentArray[] = { Fragment1.class, Fragment2.class,
			Fragment3.class, };
	/**
	 * ���ͼƬ����
	 * 
	 */
	private int mImageArray[] = { R.drawable.set,
			R.drawable.refresh, R.drawable.exit};

	/**
	 * ѡ�޿�����
	 * 
	 */
	private String mTextArray[] = { "����ͼƬ", "�Ѿ�����", "����"};
	ViewPager myViewPager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.setbackground);
		initView();
		AsyncHttpClient client =new AsyncHttpClient();    
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.show_province_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	

	/**
	 * ��ʼ�����
	 */
	private void initView() {
		mLayoutInflater = LayoutInflater.from(this);
		// �ҵ�TabHost
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

		// �õ�fragment�ĸ���
		int count = mFragmentArray.length;
		for (int i = 0; i < count; i++) {
			// ��ÿ��Tab��ť����ͼ�ꡢ���ֺ�����
			TabSpec tabSpec = mTabHost.newTabSpec(mTextArray[i]).setIndicator(
					getTabItemView(i));
			// ��Tab��ť��ӽ�Tabѡ���
			mTabHost.addTab(tabSpec, mFragmentArray[i], null);
			// ����Tab��ť�ı���
			// mTabHost.getTabWidget().getChildAt(i)
			// .setBackgroundResource(R.drawable.icon_home_sel);
		}
	}

	/**
	 * 
	 * ��ÿ��Tab��ť����ͼ�������
	 */
	private View getTabItemView(int index) {
		View view = mLayoutInflater.inflate(R.layout.tab_item_view, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
		imageView.setImageResource(mImageArray[index]);
		TextView textView = (TextView) view.findViewById(R.id.textview);
		textView.setText(mTextArray[index]);

		return view;
	}
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		mTabHost.setCurrentTab(arg0);

	}
	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		
	}

}
