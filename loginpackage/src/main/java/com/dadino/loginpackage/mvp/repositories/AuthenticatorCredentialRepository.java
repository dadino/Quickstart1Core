package com.dadino.loginpackage.mvp.repositories;

import android.accounts.Account;
import android.content.Context;

import com.dadino.loginpackage.interfaces.ICredentialRepository;
import com.dadino.loginpackage.mvp.http.request.Credentials;

import java.util.ArrayList;
import java.util.List;

import rx.Single;
import rx.SingleSubscriber;

import static com.dadino.loginpackage.mvp.components.Authenticator.IT_LAMINOX_AUTH_EMAIL;

public class AuthenticatorCredentialRepository extends AuthenticatorRepository implements
		ICredentialRepository {


	public AuthenticatorCredentialRepository(Context context) {
		super(context);
	}

	@Override
	public Single<Credentials> retrieve(String username) {
		return Single.create(new Single.OnSubscribe<Credentials>() {
			@Override
			public void call(SingleSubscriber<? super Credentials> singleSubscriber) {
				final Account account = new Account(username, IT_LAMINOX_AUTH_EMAIL);
				String password = accountManager().getPassword(account);
				singleSubscriber.onSuccess(new Credentials(account.name, password, ""));
			}
		});
	}

	@Override
	public Single<List<Credentials>> retrieve() {
		return Single.create(new Single.OnSubscribe<List<Credentials>>() {
			@Override
			public void call(SingleSubscriber<? super List<Credentials>> singleSubscriber) {
				List<Credentials> credentials = new ArrayList<>();

				for (Account account : accountManager().getAccountsByType(IT_LAMINOX_AUTH_EMAIL)) {
					String password = accountManager().getPassword(account);
					credentials.add(new Credentials(account.name, password, ""));
				}
				singleSubscriber.onSuccess(credentials);
			}
		});
	}

	@Override
	public Single<Boolean> create(String username, String password) {
		return Single.create(new Single.OnSubscribe<Boolean>() {
			@Override
			public void call(SingleSubscriber<? super Boolean> singleSubscriber) {
				final Account account = new Account(username, IT_LAMINOX_AUTH_EMAIL);
				accountManager().addAccountExplicitly(account, password, null);
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
		return Single.create(new Single.OnSubscribe<Boolean>() {
			@Override
			public void call(SingleSubscriber<? super Boolean> singleSubscriber) {
				final Account account = new Account(username, IT_LAMINOX_AUTH_EMAIL);
				accountManager().setPassword(account, password);
				singleSubscriber.onSuccess(true);
			}
		});
	}
}
