package com.example.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.activity.R;

public class Fragment3 extends Fragment {
	String hai = "hai";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.e(hai, "Fragment3 ----oncreat");
		super.onCreate(savedInstanceState);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment3, null);
	}
}