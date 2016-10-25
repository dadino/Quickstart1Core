package com.dadino.loginpackage.mvp.usecases.lastusedaccount;

import android.content.Context;

import com.dadino.loginpackage.mvp.repositories.PrefLastAccountRepository;
import com.dadino.toolbox.interfaces.ILastAccountRepository;

import rx.Observable;
import rx.Single;


public class LastAccountModel implements LastAccountMVP.Model {

	private final ILastAccountRepository lastAccountRepository;

	public LastAccountModel(Context context) {
		final Context applicationContext = context.getApplicationContext();
		lastAccountRepository = new PrefLastAccountRepository(applicationContext);
	}

	@Override
	public void onDestroy() {
		lastAccountRepository.onDestroy();
	}

	@Override
	public Observable<String> retrieve() {
		return lastAccountRepository.retrieve();
	}

	@Override
	public Single<String> save(String username) {
		return lastAccountRepository.create(username)
		                            .map(aBoolean -> username);
	}
}
