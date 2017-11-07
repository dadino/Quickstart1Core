package com.dadino.quickstart.core.mvp.components.presenter;

import android.content.Context;
import android.support.v4.content.Loader;

import com.dadino.quickstart.core.interfaces.IPresenter;

public class PresenterLoader<T extends IPresenter> extends Loader<T> {

	private final PresenterFactory<T> factory;
	private final String              tag;
	private       T                   presenter;

	public PresenterLoader(Context context, PresenterFactory<T> factory, String tag) {
		super(context);
		this.factory = factory;
		this.tag = tag;
	}


	@Override
	protected void onStartLoading() {
		if (presenter != null) {
			deliverResult(presenter);
			return;
		}

		forceLoad();
	}

	@Override
	protected void onForceLoad() {
		presenter = factory.create(getContext());
		deliverResult(presenter);
	}

	@Override
	protected void onReset() {
		if (presenter != null) presenter.onDestroy();
		presenter = null;
	}

	public String getTag() {
		return tag;
	}
}
