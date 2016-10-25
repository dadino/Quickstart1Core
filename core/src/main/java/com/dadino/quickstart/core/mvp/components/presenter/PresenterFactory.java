package com.dadino.quickstart.core.mvp.components.presenter;

import android.content.Context;

import com.dadino.quickstart.core.interfaces.IPresenter;


public interface PresenterFactory<T extends IPresenter> {

	T create(Context context);
	String tag();
	int id();
}