package com.dadino.loginpackage.mvp.repositories;

import android.accounts.AccountManager;
import android.content.Context;

import com.dadino.toolbox.interfaces.IRepository;


public class AuthenticatorRepository implements IRepository {

	private final AccountManager mAccountManager;

	public AuthenticatorRepository(Context context) {
		mAccountManager = AccountManager.get(context);
	}

	@Override
	public void onDestroy() {

	}

	public AccountManager accountManager() {
		return mAccountManager;
	}
}
