package com.dadino.loginpackage.mvp.usecases.login;

import android.content.Context;

import com.dadino.loginpackage.mvp.http.request.Credentials;
import com.dadino.toolbox.interfaces.IModel;
import com.dadino.toolbox.interfaces.INext;
import com.dadino.toolbox.interfaces.IPresenter;
import com.dadino.toolbox.mvp.components.presenter.PresenterFactory;

import java.util.List;

import rx.Single;

public class LoginMVP {

	public interface Model extends IModel {

		Single<String> token(String username);
		Single<List<Credentials>> credentials();
		Single<Credentials> credentials(String username);
		Single<String> login(String username);
		Single<String> login(Credentials credentials);
		Single<String> register(Credentials credentials);
	}

	public interface View extends INext<String> {

	}

	public interface Presenter extends IPresenter<String> {

		void onLoginRequested(Credentials credentials);
		void onRegisterRequested(Credentials credentials);
	}

	public static class Factory implements PresenterFactory<Presenter> {

		@Override
		public LoginPresenter create(Context context) {
			return new LoginPresenter(context);
		}

		@Override
		public String tag() {
			return "LoginPresenter";
		}

		@Override
		public int id() {
			return 491;
		}
	}
}
