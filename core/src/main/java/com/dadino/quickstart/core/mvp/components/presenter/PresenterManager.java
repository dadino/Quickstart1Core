package com.dadino.quickstart.core.mvp.components.presenter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.dadino.quickstart.core.interfaces.IPresenter;
import com.dadino.quickstart.core.utils.Logs;


public class PresenterManager<T extends IPresenter> implements LoaderManager.LoaderCallbacks<T> {

	private final PresenterCallback<T> callback;
	private final PresenterFactory<T>  factory;
	private final Context              context;
	private       T                    presenter;

	public PresenterManager(Context context, PresenterFactory<T> factory,
	                        PresenterCallback<T> callback) {
		this.context = context.getApplicationContext();
		this.callback = callback;
		this.factory = factory;
	}

	public PresenterManager(Fragment fragment, PresenterFactory<T> factory,
	                        PresenterCallback<T> callback) {
		this.context = fragment.getContext()
		                       .getApplicationContext();
		this.callback = callback;
		this.factory = factory;
	}

	public PresenterManager<T> bindTo(FragmentActivity activity) {
		activity.getSupportLoaderManager()
		        .initLoader(factory.id(), null, this);
		return this;
	}

	public PresenterManager<T> bindTo(Fragment activity) {
		activity.getLoaderManager()
		        .initLoader(factory.id(), null, this);
		return this;
	}

	@Override
	public PresenterLoader onCreateLoader(int id, Bundle arg) {
		Logs.presenter("Creating new Presenter " + factory.tag() + " with ID:" + id);
		return new PresenterLoader<>(context, factory, factory.tag());
	}

	@Override
	public void onLoadFinished(Loader<T> loader, T presenter) {
		this.presenter = presenter;
		if (callback != null) callback.onPresenterLoaded(presenter);
	}

	@Override
	public void onLoaderReset(Loader loader) {
		presenter = null;
	}

	public T get() {
		return presenter;
	}

	public interface PresenterCallback<P extends IPresenter> {

		void onPresenterLoaded(P presenter);
	}
}
