package com.dadino.loginpackage.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.dadino.loginpackage.mvp.components.Authenticator;

public class AuthenticatorService extends Service {

	public AuthenticatorService() {
	}

	@Override
	public IBinder onBind(Intent intent) {
		Authenticator authenticator = new Authenticator(this);
		return authenticator.getIBinder();
	}
}
