package com.dadino.quickstart.core.interfaces;


import io.reactivex.Observable;
import io.reactivex.Single;

public interface ILongRepository extends IRepository {

	Observable<Long> retrieve();

	Single<Boolean> create(Long aLong);

	Single<Boolean> delete();

	Single<Boolean> update(Long aLong);
}
