package com.dadino.toolbox.interfaces;

import rx.Observable;
import rx.Single;

public interface IStringRepository extends IRepository {

	Observable<String> retrieve();
	Single<Boolean> create(String string);
	Single<Boolean> delete();
	Single<Boolean> update(String string);
}