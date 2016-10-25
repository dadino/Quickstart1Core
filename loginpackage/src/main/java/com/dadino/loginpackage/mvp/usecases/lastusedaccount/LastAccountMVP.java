package com.dadino.loginpackage.mvp.usecases.lastusedaccount;

import android.content.Context;

import com.dadino.toolbox.interfaces.IModel;
import com.dadino.toolbox.interfaces.INext;
import com.dadino.toolbox.interfaces.IPresenter;
import com.dadino.toolbox.mvp.components.presenter.PresenterFactory;

import rx.Observable;
import rx.Single;

public class LastAccountMVP {

	public interface Model extends IModel {

		Observable<String> retrieve();
		Single<String> save(String username);
	}

	public interface View extends INext<String> {

	}

	public interface Presenter extends IPresenter<String> {

		void onAccountRequested();
		void onAccountSelected(String username);
	}

	public static class Factory implements PresenterFactory<Presenter> {

		@Override
		public LastAccountPresenter create(Context context) {
			return new LastAccountPresenter(context);
		}

		@Override
		public String tag() {
			return "LastAccountPresenter";
		}

		@Override
		public int id() {
			return 5472;
		}
	}
}
