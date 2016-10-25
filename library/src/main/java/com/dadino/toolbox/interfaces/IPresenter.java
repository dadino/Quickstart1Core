package com.dadino.toolbox.interfaces;


import com.dadino.toolbox.mvp.components.presenter.MvpView;

public interface IPresenter<T> {

	void load();
	void loadIfNeeded();
	void onDestroy();
	void addView(MvpView<T> view);
	void removeView(MvpView<T> view);
}
