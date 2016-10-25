package com.dadino.loginpackage.mvp.usecases.login;

import android.content.Context;
import android.support.annotation.NonNull;

import com.dadino.loginpackage.LoginPackage;
import com.dadino.loginpackage.interfaces.ICredentialRepository;
import com.dadino.loginpackage.interfaces.IFCMTokenRepository;
import com.dadino.loginpackage.interfaces.ITokenRepository;
import com.dadino.loginpackage.interfaces.IWebApiRepository;
import com.dadino.loginpackage.mvp.http.request.Credentials;
import com.dadino.loginpackage.mvp.http.response.BaseBody;
import com.dadino.loginpackage.mvp.repositories.AuthenticatorCredentialRepository;
import com.dadino.loginpackage.mvp.repositories.AuthenticatorTokenRepository;
import com.dadino.loginpackage.mvp.repositories.PrefFCMTokenRepository;

import java.util.List;

import rx.Single;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class LoginModel implements LoginMVP.Model {

	private final IWebApiRepository     api;
	private final ICredentialRepository credRepository;
	private final IFCMTokenRepository   fcmRepository;
	private final ITokenRepository      tokenRepository;

	public LoginModel(Context context) {
		Context mAppContext = context.getApplicationContext();
		api = LoginPackage.getLoginRepository();
		credRepository = new AuthenticatorCredentialRepository(mAppContext);
		tokenRepository = new AuthenticatorTokenRepository(mAppContext);
		fcmRepository = new PrefFCMTokenRepository(mAppContext);
	}


	@Override
	public Single<String> token(String username) {
		return tokenRepository.retrieve(username)
		                      .subscribeOn(Schedulers.io());
	}

	@Override
	public Single<List<Credentials>> credentials() {
		return credRepository.retrieve()
		                     .subscribeOn(Schedulers.io());
	}

	@Override
	public Single<Credentials> credentials(String username) {
		return Single.zip(credRepository.retrieve(username), fcmRepository.retrieve()
		                                                                  .first(s -> s != null)
		                                                                  .toSingle(),
				Credentials::new)
		             .subscribeOn(Schedulers.io());
	}

	@Override
	public Single<String> login(String username) {
		return credentials(username).flatMap(cred -> api.login(cred)
		                                                .subscribeOn(Schedulers.io())
		                                                .map(BaseBody::data)
		                                                .doOnSuccess(saveUser(cred)));
	}

	@Override
	public Single<String> login(Credentials credentials) {
		return fcmRepository.retrieve()
		                    .first(s -> s != null)
		                    .toSingle()
		                    .map(fcmtoken -> new Credentials(credentials, fcmtoken))
		                    .flatMap(cred -> api.login(cred)
		                                        .subscribeOn(Schedulers.io())
		                                        .map(BaseBody::data)
		                                        .doOnSuccess(saveUser(cred)));
	}


	@Override
	public Single<String> register(Credentials credentials) {
		return fcmRepository.retrieve()
		                    .first(s -> s != null)
		                    .toSingle()
		                    .map(fcmtoken -> new Credentials(credentials, fcmtoken))
		                    .flatMap(cred -> api.register(credentials)
		                                        .subscribeOn(Schedulers.io())
		                                        .map(BaseBody::data)
		                                        .doOnSuccess(saveUser(cred)));
	}

	@NonNull
	private Action1<String> saveUser(Credentials cred) {
		return token -> credRepository.create(cred.getEmail(), cred.getPassword())
		                              .concatWith(tokenRepository.create(cred.getEmail(), token))
		                              .subscribeOn(Schedulers.io())
		                              .subscribe();
	}

	@Override
	public void onDestroy() {
		credRepository.onDestroy();
		fcmRepository.onDestroy();
		tokenRepository.onDestroy();
	}
}
