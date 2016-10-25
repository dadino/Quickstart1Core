package com.dadino.loginpackage.mvp.usecases.lastusedaccount;

import android.content.Context;

import com.dadino.toolbox.mvp.components.presenter.Presenter;

import rx.Observable;

public class LastAccountPresenter extends Presenter<String, LastAccountMVP.Model> implements
		LastAccountMVP.Presenter {


	public LastAccountPresenter(Context context) {
		super(new LastAccountModel(context.getApplicationContext()));
	}

	@Override
	protected Observable<String> loadItemsInternal(boolean userInitiatedLoad) {
		throw new RuntimeException("Method not implemented");
	}

	@Override
	protected String tag() {
		return "LastAccount";
	}

	@Override
	public void onAccountRequested() {
		beginCustomLoading(model().retrieve());
	}

	@Override
	public void onAccountSelected(String username) {
		beginCustomLoading(model().save(username));
	}
}
