package com.example.googlelearn;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

public class HideActivity extends Activity{

	ListView list ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.view_b);
		list = (ListView)findViewById(R.id.list);
		int a = 0;
	}
}
