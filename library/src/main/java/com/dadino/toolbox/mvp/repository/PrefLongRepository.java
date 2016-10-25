package com.dadino.toolbox.mvp.repository;

import android.content.Context;

import com.dadino.toolbox.interfaces.ILongRepository;
import com.dadino.toolbox.utils.Logs;

import rx.Observable;
import rx.Single;
import rx.subjects.BehaviorSubject;

public abstract class PrefLongRepository extends PrefRepository implements ILongRepository {

	private BehaviorSubject<Long> subject;

	public PrefLongRepository(Context context) {
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
	public Observable<Long> retrieve() {
		Logs.model("Retrieving pref: " + getKey());
		if (subject == null) {
			subject = BehaviorSubject.create(getPref());
			subject.onNext(getPref());
		}
		return subject;
	}

	@Override
	public Single<Boolean> create(Long account) {
		return Single.just(editor().putLong(getKey(), account)
		                           .commit());
	}

	@Override
	public Single<Boolean> delete() {
		return Single.just(editor().remove(getKey())
		                           .commit());
	}

	@Override
	public Single<Boolean> update(Long account) {
		return create(account);
	}

	private Long getPref() {return pref().getLong(getKey(), getDefault());}

	protected abstract long getDefault();
}
