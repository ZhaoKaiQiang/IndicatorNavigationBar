package com.example.indicatornavigationbar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TestFragment extends Fragment {

	private String textString;

	public static TestFragment newInstance(String textString) {
		TestFragment newFragment = new TestFragment(textString);
		return newFragment;
	}

	public TestFragment(String textString) {
		this.textString = textString;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment, container, false);
		TextView viewhello = (TextView) view.findViewById(R.id.tv_hello);
		viewhello.setText(textString);
		return view;
	}

}
