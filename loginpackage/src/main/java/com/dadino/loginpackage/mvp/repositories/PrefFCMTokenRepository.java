package com.dadino.loginpackage.mvp.repositories;

import android.content.Context;

import com.dadino.loginpackage.interfaces.IFCMTokenRepository;
import com.dadino.toolbox.mvp.repository.PrefStringRepository;


public class PrefFCMTokenRepository extends PrefStringRepository implements IFCMTokenRepository {


	public PrefFCMTokenRepository(Context context) {
		super(context);
	}

	@Override
	protected String getKey() {
		return "fcm_token";
	}

	@Override
	protected String getDefault() {
		return null;
	}
}
