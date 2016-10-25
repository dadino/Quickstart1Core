package com.dadino.quickstart.core.interfaces;

import rx.Observable;
import rx.Single;

public interface ILongRepository extends IRepository {

	Observable<Long> retrieve();
	Single<Boolean> create(Long aLong);
	Single<Boolean> delete();
	Single<Boolean> update(Long aLong);
}
