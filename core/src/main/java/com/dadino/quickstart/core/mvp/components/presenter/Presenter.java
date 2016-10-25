package com.dadino.quickstart.core.mvp.components.presenter;

import com.dadino.quickstart.core.interfaces.IModel;
import com.dadino.quickstart.core.interfaces.IPresenter;
import com.dadino.quickstart.core.utils.Logs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import rx.Observable;
import rx.Observer;
import rx.Single;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

public abstract class Presenter<E, M extends IModel> implements Observer<E>, IPresenter<E> {

	private final M mModel;
	protected     E mItem;
	private Set<MvpView<E>> mMvpViews = new HashSet<>();
	private Throwable mError;
	private List<Subscription> mSubscriptions = new ArrayList<>();
	private boolean mLoading;
	private boolean mCompleted;
	private boolean mAllowMultipleSubscription = false;
	private boolean mPublishLoadFinishedOnNext;
	private boolean mResetItemAfterPublish;

	public Presenter(M model) {
		this.mModel = model;
	}

	public void load() {
		load(false);
	}

	public void loadIfNeeded() {
		if (mItem == null && !mLoading) load();
	}

	public void onDestroy() {
		mMvpViews.clear();
		reset();
		if (mModel != null) mModel.onDestroy();
	}

	public void addView(MvpView<E> view) {
		mMvpViews.add(view);
		publishState(view);
	}

	public void removeView(MvpView<E> view) {
		mMvpViews.remove(view);
	}

	public void load(boolean userInitiatedLoad) {
		loadItems(userInitiatedLoad);
	}

	public void beginCustomLoading(Observable<E> observable) {
		mCompleted = false;
		publishLoading(true);
		onNewSubscription(observable.observeOn(AndroidSchedulers.mainThread())
		                            .subscribe(this));
	}

	public void beginCustomLoading(Single<E> single) {
		mCompleted = false;
		publishLoading(true);
		onNewSubscription(single.observeOn(AndroidSchedulers.mainThread())
		                        .subscribe(this));
	}

	public void reset() {
		mItem = null;
		mError = null;
		for (Subscription subscription : mSubscriptions) {
			if (subscription != null && !subscription.isUnsubscribed()) subscription.unsubscribe();
		}
		mSubscriptions.clear();
	}

	private void loadItems(boolean userInitiatedLoad) {
		beginCustomLoading(loadItemsInternal(userInitiatedLoad));
	}

	protected abstract Observable<E> loadItemsInternal(boolean userInitiatedLoad);

	private String getItemDescription(E item) {
		return item instanceof List ? (((List) item).size() + " items") : item.toString();
	}

	protected abstract String tag();

	public void onCompleted() {
		Logs.presenter(tag() + " - Observable onCompleted");
		this.mCompleted = true;
		publishCompleted();
		publishLoading(false);
	}

	public void onError(Throwable e) {
		Logs.presenter(tag() + " - Observable onError: " + e.getMessage());
		mError = e;
		publishError();
		publishLoading(false);
	}

	public void onNext(E item) {
		Logs.presenter(tag() + " - Observable onNext: " +
		               (item != null ? getItemDescription(item) : "null"));
		this.mItem = item;
		publishNext();
		if (mPublishLoadFinishedOnNext) publishLoading(false);
	}

	private void onNewSubscription(Subscription subscription) {
		Logs.presenter(tag() + " - New subscription");
		if (!mAllowMultipleSubscription && mSubscriptions.size() > 0) {
			final Subscription currentSubscription = mSubscriptions.get(0);
			if (currentSubscription != null && !currentSubscription.isUnsubscribed()) {
				Logs.presenter(tag() + " - Unsubscribing from current subscription");
				currentSubscription.unsubscribe();
			}
			mSubscriptions.remove(0);
		}
		mSubscriptions.add(subscription);

		if (mSubscriptions != null) for (MvpView<E> view : mMvpViews) {
			if (view != null && view.sub != null) view.sub.onNewSubscription(subscription);
		}
	}

	private void publishState(MvpView<E> view) {
		publishError(view);
		publishNext(view);
		publishCompleted(view);
		publishLoading(view);
	}

	private void publishLoading(boolean loading) {
		this.mLoading = loading;
		for (MvpView<E> view : mMvpViews) {
			publishLoading(view);
		}
	}

	private void publishLoading(MvpView<E> view) {
		if (view != null && view.load != null) view.load.onItemLoad(mLoading);
	}


	private void publishNext() {
		for (MvpView<E> view : mMvpViews) {
			publishNext(view);
		}

		if (mResetItemAfterPublish) mItem = null;
	}

	private void publishNext(MvpView<E> view) {
		if (view != null && view.next != null && mItem != null) view.next.onItemNext(mItem);
	}


	private void publishError() {
		for (MvpView<E> view : mMvpViews) {
			publishError(view);
		}
	}

	private void publishError(MvpView<E> view) {
		if (view != null && view.error != null && mError != null) view.error.onItemError(mError);
	}


	private void publishCompleted() {
		for (MvpView<E> view : mMvpViews) {
			publishCompleted(view);
		}
	}

	private void publishCompleted(MvpView<E> view) {
		if (view != null && view.completed != null && mCompleted) {
			view.completed.onItemCompleted();
		}
	}

	public void setAllowMultipleSubscription(boolean allowMultipleSubscription) {
		this.mAllowMultipleSubscription = allowMultipleSubscription;
	}

	protected M model() {
		return mModel;
	}

	public void setPublishLoadFinishedOnNext(boolean mPublishLoadFinishedOnNext) {
		this.mPublishLoadFinishedOnNext = mPublishLoadFinishedOnNext;
	}

	public void setResetItemAfterPublish(boolean resetItemAfterPublish) {
		this.mResetItemAfterPublish = resetItemAfterPublish;
	}
}
