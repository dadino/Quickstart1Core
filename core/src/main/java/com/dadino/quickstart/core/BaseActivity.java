package com.dadino.quickstart.core;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dadino.quickstart.core.interfaces.ISub;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class BaseActivity extends AppCompatActivity implements ISub {

	private CompositeSubscription mSubscriptions;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mSubscriptions = new CompositeSubscription();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
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
}