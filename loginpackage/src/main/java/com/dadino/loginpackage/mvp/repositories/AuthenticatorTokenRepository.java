package com.dadino.loginpackage.mvp.repositories;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.content.Context;
import android.os.Bundle;

import com.dadino.loginpackage.interfaces.ITokenRepository;

import rx.Single;
import rx.SingleSubscriber;

import static com.dadino.loginpackage.mvp.components.Authenticator.IT_LAMINOX_AUTH_EMAIL;

public class AuthenticatorTokenRepository extends AuthenticatorRepository implements
		ITokenRepository {


	private static final String TOKEN_TYPE = "all_access";

	public AuthenticatorTokenRepository(Context context) {
		super(context);
	}


	@Override
	public Single<String> retrieve(String username) {
		return Single.create(new Single.OnSubscribe<String>() {
			@Override
			public void call(SingleSubscriber<? super String> singleSubscriber) {
				final Account account = new Account(username, IT_LAMINOX_AUTH_EMAIL);
				AccountManagerFuture<Bundle> token = accountManager().getAuthToken(account,
						TOKEN_TYPE, null, true, null, null);
				final String string;
				try {
					string = token.getResult()
					              .getString(AccountManager.KEY_AUTHTOKEN);
					singleSubscriber.onSuccess(string);
				} catch (Exception e) {
					e.printStackTrace();
					singleSubscriber.onError(e);
				}
			}
		});
	}

	@Override
	public Single<Boolean> create(String username, String authtoken) {
		return Single.create(new Single.OnSubscribe<Boolean>() {
			@Override
			public void call(SingleSubscriber<? super Boolean> singleSubscriber) {
				final Account account = new Account(username, IT_LAMINOX_AUTH_EMAIL);

				accountManager().setAuthToken(account, TOKEN_TYPE, authtoken);
				singleSubscriber.onSuccess(true);
			}
		});
	}

	@Override
	public Single<Boolean> delete(String username) {
		return Single.create(new Single.OnSubscribe<Boolean>() {
			@Override
			public void call(SingleSubscriber<? super Boolean> singleSubscriber) {
				singleSubscriber.onSuccess(false);
			}
		});
	}


	@Override
	public Single<Boolean> update(String username, String password) {
		return create(username, password);
	}
}
