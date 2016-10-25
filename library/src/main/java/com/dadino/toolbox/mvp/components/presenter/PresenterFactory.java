package com.dadino.toolbox.mvp.components.presenter;

import android.content.Context;

import com.dadino.toolbox.interfaces.IPresenter;


public interface PresenterFactory<T extends IPresenter> {

	T create(Context context);
	String tag();
	int id();
}