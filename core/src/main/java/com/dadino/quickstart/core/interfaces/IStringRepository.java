package com.dadino.quickstart.core.interfaces;


import io.reactivex.Observable;
import io.reactivex.Single;

public interface IStringRepository extends IRepository {

	Observable<String> retrieve();

	Single<Boolean> create(String string);

	Single<Boolean> delete();

	Single<Boolean> update(String string);
}
