package com.dadino.quickstart.core.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.dadino.quickstart.core.App;
import com.dadino.quickstart.core.interfaces.FragmentLifecycleListener;
import com.dadino.quickstart.core.interfaces.ISub;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseDialogFragment extends AppCompatDialogFragment implements ISub {

	private CompositeSubscription mSubscriptions;
	private boolean                         mShouldWatchForLeaks = true;
	private List<FragmentLifecycleListener> lifecycleListeners   = new ArrayList<>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		for (FragmentLifecycleListener listener : lifecycleListeners) {
			listener.onCreate();
		}
		mSubscriptions = new CompositeSubscription();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initPresenters();
	}

	@Override
	public void onStart() {
		super.onStart();
		for (FragmentLifecycleListener listener : lifecycleListeners) {
			listener.onStart();
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		for (FragmentLifecycleListener listener : lifecycleListeners) {
			listener.onStop();
		}
	}

	protected abstract void initPresenters();

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onResume() {
		super.onResume();
		for (FragmentLifecycleListener listener : lifecycleListeners) {
			listener.onResume();
		}
	}

	@Override
	public void onPause() {
		super.onPause();
		for (FragmentLifecycleListener listener : lifecycleListeners) {
			listener.onPause();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		for (FragmentLifecycleListener listener : lifecycleListeners) {
			listener.onDestroy();
		}
		mSubscriptions.unsubscribe();
		if (mShouldWatchForLeaks) {
			App.getRefWatcher(getActivity())
			   .watch(this);
		}
	}

	public void shouldWatchForLeaks(boolean watchForLeaks) {
		this.mShouldWatchForLeaks = watchForLeaks;
	}

	public void addSubscription(Subscription subscription) {
		mSubscriptions.add(subscription);
	}

	@Override
	public void onNewSubscription(Subscription subscription) {
		addSubscription(subscription);
	}

	public void addLifecycleListener(FragmentLifecycleListener fragmentLifecycleListener) {
		lifecycleListeners.add(fragmentLifecycleListener);
	}
}

