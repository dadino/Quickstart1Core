package com.dadino.quickstart.core.interfaces;

import com.dadino.quickstart.core.mvp.components.Optional;

import io.reactivex.Observable;
import io.reactivex.Single;

public interface IStringRepository extends IRepository {

	Observable<Optional<String>> retrieve();

	Single<Boolean> create(String string);

	Single<Boolean> delete();

	Single<Boolean> update(String string);
}
