package com.dadino.quickstart.core.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.dadino.quickstart.core.App;
import com.dadino.quickstart.core.interfaces.ActivityLifecycleListener;
import com.dadino.quickstart.core.interfaces.ISub;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


public abstract class BaseFragment extends Fragment implements ISub {

	private CompositeSubscription mSubscriptions;
	private boolean                         mShouldWatchForLeaks = true;
	private List<ActivityLifecycleListener> lifecycleListeners   = new ArrayList<>();


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		for (ActivityLifecycleListener listener : lifecycleListeners) {
			listener.onCreate();
		}
		mSubscriptions = new CompositeSubscription();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onStart() {
		super.onStart();
		for (ActivityLifecycleListener listener : lifecycleListeners) {
			listener.onStart();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		for (ActivityLifecycleListener listener : lifecycleListeners) {
			listener.onResume();
		}
	}

	@Override
	public void onStop() {
		super.onStop();
		for (ActivityLifecycleListener listener : lifecycleListeners) {
			listener.onStop();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		for (ActivityLifecycleListener listener : lifecycleListeners) {
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

	public void addLifecycleListener(ActivityLifecycleListener activityLifecycleListener) {
		lifecycleListeners.add(activityLifecycleListener);
	}
}

