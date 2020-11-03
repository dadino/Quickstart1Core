package com.dadino.quickstart.core.mvp.repository;

import android.content.Context;

import com.dadino.quickstart.core.interfaces.IStringRepository;
import com.dadino.quickstart.core.utils.Logs;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.subjects.BehaviorSubject;


public abstract class PrefStringRepository extends PrefRepository implements IStringRepository {

	private BehaviorSubject<String> subject;

	public PrefStringRepository(Context context) {
		super(context);
	}

	@Override
	protected String listenOn() {
		return getKey();
	}

	@Override
	protected void onPrefChanged() {
		if (subject != null) subject.onNext(getPref());
	}

	@Override
	public Observable<String> retrieve() {
		Logs.model("Retrieving pref: " + getKey());
		if (subject == null) {
			subject = BehaviorSubject.create();
			subject.onNext(getPref());
		}
		return subject;
	}

	@Override
	public Single<Boolean> create(String string) {
		return Single.just(editor().putString(getKey(), string)
		                           .commit());
	}

	@Override
	public Single<Boolean> delete() {
		return Single.just(editor().remove(getKey())
		                           .commit());
	}

	@Override
	public Single<Boolean> update(String string) {
		return create(string);
	}

	private String getPref() {return pref().getString(getKey(), getDefault());}

	protected abstract String getDefault();
}
