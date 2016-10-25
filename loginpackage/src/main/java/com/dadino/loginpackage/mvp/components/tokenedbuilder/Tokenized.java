package com.dadino.loginpackage.mvp.components.tokenedbuilder;

import com.dadino.loginpackage.interfaces.IWebApiRepository;

import retrofit2.Response;
import rx.Single;

public abstract class Tokenized<T extends Response> {

	protected final IWebApiRepository service;
	private         String            mToken;

	public Tokenized(IWebApiRepository service) {
		this.service = service;
	}


	public Tokenized<T> setToken(String token) {
		this.mToken = token;
		return this;
	}

	protected String auth() {
		return "Token " + mToken;
	}

	public Single<T> get() {
		return Single.defer(this::build);
	}

	protected abstract Single<T> build();
}
