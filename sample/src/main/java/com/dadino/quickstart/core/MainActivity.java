package com.dadino.quickstart.core;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.dadino.quickstart.R;
import com.dadino.quickstart.core.widgets.RecyclerLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action",
				Snackbar.LENGTH_LONG)
				.setAction("Action", null)
				.show());

		final RecyclerLayout rl = (RecyclerLayout) findViewById(R.id.sample_list);
		rl.setEmptyText("Empty Text");
	}
}
