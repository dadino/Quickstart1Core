package com.dadino.quickstart.core.interfaces;


import com.dadino.quickstart.core.mvp.components.presenter.MvpView;

public interface IPresenter<T> {

	void load();
	void loadIfNeeded();
	boolean needsLoad();
	void onDestroy();
	void addView(MvpView<T> view);
	void removeView(MvpView<T> view);
}
