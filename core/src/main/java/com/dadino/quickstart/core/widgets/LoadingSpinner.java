package com.dadino.quickstart.core.widgets;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.dadino.quickstart.core.R;


public class LoadingSpinner<T extends SpinnerAdapter> extends FrameLayout {

	protected T           mAdapter;
	private   ProgressBar progress;
	private   Spinner     spinner;
	private   boolean     mLoading;

	public LoadingSpinner(Context context) {
		super(context);
		init();
	}

	public LoadingSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public LoadingSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		inflate(getContext(), R.layout.view_loading_spinner, this);
		progress = (ProgressBar) findViewById(R.id.loading_spinner_progress);
		spinner = (Spinner) findViewById(R.id.loading_spinner_spinner);

		initialize();
	}

	protected void initialize() {}

	public void setListLoading(boolean loading) {
		this.mLoading = loading;

		progress.setVisibility(loading ? VISIBLE : INVISIBLE);
		spinner.setVisibility(loading ? INVISIBLE : VISIBLE);
	}

	public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener listener) {
		spinner.setOnItemSelectedListener(listener);
	}

	public void updateLoadingState() {
		setListLoading(mLoading);
	}

	public void setAdapter(T adapter) {
		this.mAdapter = adapter;
		mAdapter.registerDataSetObserver(new DataSetObserver() {
			@Override
			public void onChanged() {
				super.onChanged();
				updateLoadingState();
			}
		});
		spinner.setAdapter(mAdapter);
		updateLoadingState();
	}

	public int getSelection() {
		return spinner.getSelectedItemPosition();
	}

	public void setSelection(int position) {
		if (position == -1) return;
		if (position == spinner.getSelectedItemPosition()) return;
		spinner.setSelection(position);
	}
}
