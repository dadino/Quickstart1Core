package com.dadino.loginpackage.mvp.repositories;

import android.content.Context;

import com.dadino.toolbox.interfaces.ILastAccountRepository;
import com.dadino.toolbox.mvp.repository.PrefStringRepository;

public class PrefLastAccountRepository extends PrefStringRepository implements
		ILastAccountRepository {

	public PrefLastAccountRepository(Context context) {
		super(context);
	}

	@Override
	protected String getDefault() {
		return "";
	}

	@Override
	protected String getKey() {
		return "last_account";
	}
}
