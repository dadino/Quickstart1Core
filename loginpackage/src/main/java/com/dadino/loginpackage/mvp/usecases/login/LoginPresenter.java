package com.dadino.loginpackage.mvp.usecases.login;

import android.content.Context;

import com.dadino.loginpackage.mvp.http.request.Credentials;
import com.dadino.toolbox.mvp.components.presenter.Presenter;

import rx.Observable;


public class LoginPresenter extends Presenter<String, LoginMVP.Model> implements LoginMVP
		.Presenter {


	public LoginPresenter(Context context) {
		super(new LoginModel(context.getApplicationContext()));
	}

	@Override
	protected Observable<String> loadItemsInternal(boolean userInitiatedLoad) {
		return Observable.error(new UnsupportedOperationException());
	}

	@Override
	protected String tag() {
		return "Login";
	}


	@Override
	public void onLoginRequested(Credentials credentials) {
		beginCustomLoading(model().login(credentials));
	}

	@Override
	public void onRegisterRequested(Credentials credentials) {
		beginCustomLoading(model().register(credentials));
	}
}
