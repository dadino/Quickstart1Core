package com.dadino.loginpackage.interfaces;

import com.dadino.toolbox.interfaces.IRepository;

import rx.Observable;
import rx.Single;

public interface IFCMTokenRepository extends IRepository {

	Observable<String> retrieve();
	Single<Boolean> create(String token);
	Single<Boolean> delete();
	Single<Boolean> update(String token);
}
