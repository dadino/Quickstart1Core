package com.dadino.quickstart.core.mvp.components.presenter;

import android.content.Context;

import com.dadino.quickstart.core.BaseActivity;
import com.dadino.quickstart.core.fragments.BaseFragment;
import com.dadino.quickstart.core.interfaces.ICompleted;
import com.dadino.quickstart.core.interfaces.IError;
import com.dadino.quickstart.core.interfaces.ILoad;
import com.dadino.quickstart.core.interfaces.INext;
import com.dadino.quickstart.core.interfaces.IPresenter;
import com.dadino.quickstart.core.interfaces.ISub;
import com.dadino.quickstart.core.mvp.exceptions.InvalidPresenterException;

public class PresenterBuilder<ITEM, PRESENTER extends IPresenter<ITEM>> {

	private BaseFragment fragment;
	private BaseActivity activity;
	private PresenterManager.PresenterCallback<PRESENTER> callback;
	private PresenterFactory<PRESENTER> factory;
	private MvpView<ITEM> mvpView;
	private INext<ITEM> onNext;
	private ICompleted onCompleted;
	private IError onError;
	private ILoad onLoad;
	private ISub onSub;

	public PresenterBuilder() {

	}

	public PresenterBuilder<ITEM, PRESENTER> with(BaseFragment fragment) {
		this.fragment = fragment;
		return this;
	}

	public PresenterBuilder<ITEM, PRESENTER> with(BaseActivity activity) {
		this.activity = activity;
		return this;
	}

	public PresenterBuilder<ITEM, PRESENTER> callback(
			PresenterManager.PresenterCallback<PRESENTER> callback) {
		this.callback = callback;
		return this;
	}

	public PresenterBuilder<ITEM, PRESENTER> factory(PresenterFactory<PRESENTER> factory) {
		this.factory = factory;
		return this;
	}

	public PresenterBuilder<ITEM, PRESENTER> view(MvpView<ITEM> mvpView) {
		this.mvpView = mvpView;
		return this;
	}

	public PresenterBuilder<ITEM, PRESENTER> onNext(INext<ITEM> onNext) {
		this.onNext = onNext;
		return this;
	}

	public PresenterBuilder<ITEM, PRESENTER> onCompleted(ICompleted onCompleted) {
		this.onCompleted = onCompleted;
		return this;
	}

	public PresenterBuilder<ITEM, PRESENTER> onError(IError onError) {
		this.onError = onError;
		return this;
	}

	public PresenterBuilder<ITEM, PRESENTER> onLoad(ILoad onLoad) {
		this.onLoad = onLoad;
		return this;
	}

	public PresenterBuilder<ITEM, PRESENTER> onSub(ISub onSub) {
		this.onSub = onSub;
		return this;
	}

	public PresenterManager<ITEM, PRESENTER> bind() {
		if (fragment == null && activity == null) throw new InvalidPresenterException(
				"Activity or Fragment not found");
		if (factory == null) throw new InvalidPresenterException("Factory not found");

		final MvpView<ITEM> buildMvpView = mvpView != null ? mvpView : mvpView();
		PresenterManager<ITEM, PRESENTER> m = new PresenterManager<>(context(), factory, callback, buildMvpView);
		if (activity != null) m.bindTo(activity);
		if (fragment != null) m.bindTo(fragment);
		return m;
	}

	private MvpView<ITEM> mvpView() {
		if (mvpView != null) return mvpView;
		if (onNext != null || onError != null || onSub != null || onCompleted != null ||
				onLoad != null) return new MvpView<>(onNext, onCompleted, onError, onLoad, onSub);
		return null;
	}

	private Context context() {
		if (activity != null) return activity.getApplicationContext();
		if (fragment.getActivity() != null) return fragment.getActivity()
				.getApplicationContext();
		throw new InvalidPresenterException("Context not found");
	}
}
