package com.dadino.loginpackage.utils;

import android.content.Context;
import android.support.v4.content.Loader;

public class SmartLockLoader extends Loader<SmartLockLogin> {

	private SmartLockLogin smartLockLogin;

	public SmartLockLoader(Context context) {
		super(context);
	}


	@Override
	protected void onStartLoading() {
		if (smartLockLogin != null) {
			deliverResult(smartLockLogin);
			return;
		}

		forceLoad();
	}

	@Override
	protected void onForceLoad() {
		smartLockLogin = new SmartLockLogin();
		deliverResult(smartLockLogin);
	}

	@Override
	protected void onReset() {
		smartLockLogin.onDestroy();
		smartLockLogin = null;
	}
}
