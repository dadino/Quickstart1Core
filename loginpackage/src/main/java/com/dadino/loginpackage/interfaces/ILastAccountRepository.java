package com.dadino.loginpackage.interfaces;

import com.dadino.toolbox.interfaces.IRepository;

import rx.Observable;
import rx.Single;

public interface ILastAccountRepository extends IRepository {

	Observable<String> retrieve();
	Single<Boolean> create(String account);
	Single<Boolean> delete();
	Single<Boolean> update(String account);
}
