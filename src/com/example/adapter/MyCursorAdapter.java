package com.example.adapter;

import com.example.activity.R;
import com.example.dao.ChinaDAO;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyCursorAdapter extends CursorAdapter {

	private LayoutInflater layoutInflater;
	ChinaDAO myHelper;
	Context context;
	public MyCursorAdapter(Context context, Cursor c, boolean autoRequery) {
		super(context, c, autoRequery);
		this.context=context;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	

	private void setView(View view, Cursor cursor) {
		TextView textView = (TextView) view;
		textView.setText(cursor.getString(cursor.getColumnIndex("name")));
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		setView(view, cursor);
	}

	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		layoutInflater = LayoutInflater.from(context);
		TextView view = (TextView) layoutInflater.inflate(
				R.layout.list_view_item, parent, false);
		setView(view, cursor);
		return view;

	}

	@Override
	public CharSequence convertToString(Cursor cursor) {
		return cursor == null ? "" : cursor.getString(cursor
				.getColumnIndex("name"));// ÁÐÃûÎª name
	}

	@Override
	public Object getItem(int position) {

		return super.getItem(position);
	}

}
