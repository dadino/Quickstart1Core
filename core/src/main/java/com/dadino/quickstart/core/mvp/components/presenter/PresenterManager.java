package com.dadino.quickstart.core.mvp.components.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.dadino.quickstart.core.BaseActivity;
import com.dadino.quickstart.core.fragments.BaseDialogFragment;
import com.dadino.quickstart.core.fragments.BaseFragment;
import com.dadino.quickstart.core.interfaces.IPresenter;
import com.dadino.quickstart.core.interfaces.SimpleActivityLifecycleListener;
import com.dadino.quickstart.core.interfaces.SimpleFragmentLifecycleListener;
import com.dadino.quickstart.core.utils.Logs;


public class PresenterManager<ITEM, PRESENTER extends IPresenter<ITEM>> implements LoaderManager
		.LoaderCallbacks<PRESENTER> {

	private final PresenterCallback<PRESENTER> callback;
	private final PresenterFactory<PRESENTER>  factory;
	private final Context                      context;
	private       PRESENTER                    presenter;
	private       MvpView<ITEM>                mvpView;

	public PresenterManager(Context context, PresenterFactory<PRESENTER> factory,
	                        PresenterCallback<PRESENTER> callback, MvpView<ITEM> view) {
		this.context = context.getApplicationContext();
		this.callback = callback;
		this.factory = factory;
		this.mvpView = view;
	}

	public PresenterManager(Fragment fragment, PresenterFactory<PRESENTER> factory,
	                        PresenterCallback<PRESENTER> callback, MvpView<ITEM> view) {
		this.context = fragment.getContext()
		                       .getApplicationContext();
		this.callback = callback;
		this.factory = factory;
		this.mvpView = view;
	}

	public PresenterManager<ITEM, PRESENTER> bindTo(BaseActivity activity) {
		activity.getSupportLoaderManager()
		        .initLoader(factory.id(), null, this);
		activity.addActivityLifecycleListener(new SimpleActivityLifecycleListener() {
			@Override
			public void onStart() {
				super.onStart();
				if (presenter != null) presenter.addView(mvpView);
			}

			@Override
			public void onStop() {
				super.onStop();
				if (presenter != null) presenter.removeView(mvpView);
			}
		});
		return this;
	}

	public PresenterManager<ITEM, PRESENTER> bindTo(BaseFragment fragment) {
		fragment.getLoaderManager()
		        .initLoader(factory.id(), null, this);
		fragment.addLifecycleListener(new SimpleFragmentLifecycleListener() {
			@Override
			public void onResume() {
				super.onResume();
				if (presenter != null) presenter.addView(mvpView);
			}

			@Override
			public void onPause() {
				super.onPause();
				if (presenter != null) presenter.removeView(mvpView);
			}
		});
		return this;
	}

	public PresenterManager<ITEM, PRESENTER> bindTo(BaseDialogFragment fragment, MvpView view) {
		this.mvpView = view;
		fragment.getLoaderManager()
		        .initLoader(factory.id(), null, this);
		fragment.addLifecycleListener(new SimpleFragmentLifecycleListener() {
			@Override
			public void onResume() {
				super.onResume();
				if (presenter != null) presenter.addView(mvpView);
			}

			@Override
			public void onPause() {
				super.onPause();
				if (presenter != null) presenter.removeView(mvpView);
			}
		});
		return this;
	}

	@Override
	public PresenterLoader onCreateLoader(int id, Bundle arg) {
		Logs.presenter("Creating new Presenter " + factory.tag() + " with ID:" + id);
		return new PresenterLoader<>(context, factory, factory.tag());
	}

	@Override
	public void onLoadFinished(Loader<PRESENTER> loader, PRESENTER presenter) {
		this.presenter = presenter;
		if (callback != null) callback.onPresenterLoaded(presenter);
	}

	@Override
	public void onLoaderReset(Loader loader) {
		presenter = null;
	}

	public PRESENTER get() {
		return presenter;
	}

	public interface PresenterCallback<P extends IPresenter> {

		void onPresenterLoaded(P presenter);
	}
}
