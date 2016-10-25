package com.dadino.quickstart.core.rx;


import rx.Observable;
import rx.Single;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class TransfSchedulers {

	@SuppressWarnings("unchecked")
	public static <T> Observable.Transformer<T, T> apply() {
		return observable -> observable.subscribeOn(Schedulers.io())
		                               .observeOn(AndroidSchedulers.mainThread());
	}

	@SuppressWarnings("unchecked")
	public static <T> Single.Transformer<T, T> applySingle() {
		return single -> single.subscribeOn(Schedulers.io())
		                       .observeOn(AndroidSchedulers.mainThread());
	}
}
