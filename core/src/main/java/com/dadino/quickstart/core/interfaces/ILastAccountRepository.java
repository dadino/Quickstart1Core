package com.dadino.quickstart.core.interfaces;


import io.reactivex.Observable;
import io.reactivex.Single;

public interface ILastAccountRepository extends IRepository {

	Observable<String> retrieve();

	Single<Boolean> create(String account);

	Single<Boolean> delete();

	Single<Boolean> update(String account);
}
