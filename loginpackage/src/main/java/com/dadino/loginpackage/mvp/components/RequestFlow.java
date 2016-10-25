package com.dadino.loginpackage.mvp.components;

import android.content.Context;
import android.text.TextUtils;

import com.dadino.loginpackage.mvp.components.tokenedbuilder.Tokenized;
import com.dadino.loginpackage.mvp.usecases.login.LoginMVP;
import com.dadino.loginpackage.mvp.usecases.login.LoginModel;
import com.dadino.toolbox.mvp.components.ErrorHandler;
import com.dadino.toolbox.utils.Logs;

import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.Single;
import rx.schedulers.Schedulers;

public class RequestFlow<T extends Response<?>> {

	private final LoginMVP.Model mLoginService;
	private final Tokenized<T>   mTokenized;
	private final String         mUsername;
	private       boolean        alreadyTriedLogin;

	public RequestFlow(Context context, String username, Tokenized<T> observable) {
		this.mLoginService = new LoginModel(context);
		this.mTokenized = observable;
		this.mUsername = username;
	}

	public Single<T> requestWithoutToken() {
		return get();
	}

	public Single<T> requestWithOptionalToken() {
		return mLoginService.token(mUsername)
		                    .flatMap(token -> TextUtils.isEmpty(token) ? requestWithoutToken() :
		                                      requestWithToken());
	}

	public Single<T> requestWithToken() {
		return mLoginService.token(mUsername)
		                    .flatMap(token -> TextUtils.isEmpty(token) ? checkCredentialAndLogin
				                    () : get());
	}

	private Single<T> get() {
		return mLoginService.token(mUsername)
		                    .flatMap(token -> {
			                    Logs.token("Token: " + token);
			                    return mTokenized.setToken(token)
			                                     .get();
		                    })
		                    .flatMap(response -> {
			                    if (response.isSuccessful()) return Single.just(response);
			                    else return Single.error(new HttpException(response));
		                    })
		                    .retry(ErrorHandler::isRecoverable)
		                    .onErrorResumeNext(throwable -> {
			                    Logs.error("Error occurred in request flow");
			                    if (ErrorHandler.shouldRefreshToken(throwable) &&
			                        !alreadyTriedLogin) return checkCredentialAndLogin();
			                    return Single.error(throwable);
		                    });
	}


	private Single<T> checkCredentialAndLogin() {
		return mLoginService.credentials()
		                    .flatMap(cred -> cred != null ? loginAndRetry() : noCredentialError());
	}

	private Single<T> loginAndRetry() {
		return mLoginService.login(mUsername)
		                    .subscribeOn(Schedulers.io())
		                    .flatMap(response -> {
			                    Logs.login("Token refresh successful: " + response);
			                    alreadyTriedLogin = true;
			                    return get();
		                    })
		                    .onErrorResumeNext(this::loginError);
	}

	private Single<T> noCredentialError() {
		return Single.error(new ErrorHandler.NoCredentialError());
	}

	private Single<T> loginError(Throwable originalError) {
		return Single.error(new ErrorHandler.LoginError(originalError));
	}
}
