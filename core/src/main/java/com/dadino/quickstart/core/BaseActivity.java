package com.dadino.quickstart.core;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dadino.quickstart.core.interfaces.ActivityLifecycleListener;
import com.dadino.quickstart.core.interfaces.ISub;

import java.util.ArrayList;
import java.util.List;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseActivity extends AppCompatActivity implements ISub {

	private CompositeSubscription mSubscriptions;
	private List<ActivityLifecycleListener> lifecycleListeners = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		for (ActivityLifecycleListener listener : lifecycleListeners) {
			listener.onCreate();
		}
		mSubscriptions = new CompositeSubscription();
	}

	@Override
	protected void onStart() {
		super.onStart();
		for (ActivityLifecycleListener listener : lifecycleListeners) {
			listener.onStart();
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		for (ActivityLifecycleListener listener : lifecycleListeners) {
			listener.onStop();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		for (ActivityLifecycleListener listener : lifecycleListeners) {
			listener.onDestroy();
		}
		mSubscriptions.unsubscribe();

		App.getRefWatcher(this)
		   .watch(this);
	}

	public void addSubscription(Subscription subscription) {
		mSubscriptions.add(subscription);
	}

	@Override
	public void onNewSubscription(Subscription subscription) {
		addSubscription(subscription);
	}

	public abstract void initPresenters();

	@Override
	protected void onResume() {
		super.onResume();
		for (ActivityLifecycleListener listener : lifecycleListeners) {
			listener.onResume();
		}
	}

	public void addActivityLifecycleListener(ActivityLifecycleListener activityLifecycleListener) {
		lifecycleListeners.add(activityLifecycleListener);
	}
}