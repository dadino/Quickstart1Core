package com.dadino.quickstart.core.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.dadino.quickstart.core.R;
import com.dadino.quickstart.core.adapters.BaseSpinnerAdapter;


public class LoadingSpinner<T extends BaseSpinnerAdapter> extends FrameLayout {

	protected T           mAdapter;
	private   ProgressBar progress;
	private   Spinner     spinner;
	private   TextView    label;
	private   boolean     mLoading;
	private   String      mLabel;

	public LoadingSpinner(Context context) {
		super(context);
		init();
	}

	public LoadingSpinner(Context context, AttributeSet attrs) {
		super(context, attrs);
		setLabelFromAttributeSet(context, attrs);
		init();
	}

	public LoadingSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		setLabelFromAttributeSet(context, attrs);
		init();
	}

	private void init() {
		inflate(getContext(), R.layout.view_loading_spinner, this);
		progress = (ProgressBar) findViewById(R.id.loading_spinner_progress);
		spinner = (Spinner) findViewById(R.id.loading_spinner_spinner);
		label = (TextView) findViewById(R.id.loading_spinner_label);

		setLabel(mLabel);
		setOnClickListener(view -> spinner.performClick());
		initialize();
	}

	private void setLabelFromAttributeSet(Context context, AttributeSet attrs) {
		if (attrs != null) {
			TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoadingSpinner);
			mLabel = a.getString(R.styleable.LoadingSpinner_ls_label);
			a.recycle();
		}
	}

	protected void initialize() {}

	public void setListLoading(boolean loading) {
		this.mLoading = loading;

		progress.setVisibility(loading ? VISIBLE : INVISIBLE);
		spinner.setVisibility(loading ? INVISIBLE : VISIBLE);
	}

	public void setLabel(@StringRes int stringId) {
		label.setText(getContext().getString(stringId));
	}

	public void setLabel(String string) {
		label.setText(string);
		if (TextUtils.isEmpty(string)) label.setVisibility(GONE);
		else label.setVisibility(VISIBLE);
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

	public long getSelectedId() {
		return mAdapter.getItemId(spinner.getSelectedItemPosition());
	}

	public void setSelectedId(long id) {
		int wantedPosition = mAdapter.getPosition(id);
		if (wantedPosition < 0 || wantedPosition == spinner.getSelectedItemPosition()) return;
		spinner.setSelection(wantedPosition);
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		spinner.setEnabled(enabled);
		label.setEnabled(enabled);
	}
}
