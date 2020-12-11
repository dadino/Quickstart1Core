package com.dadino.quickstart.core.fragments;

import android.os.Bundle;
import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentActivity;

import com.dadino.quickstart.core.interfaces.FragmentLifecycleListener;
import com.dadino.quickstart.core.interfaces.ISub;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseDialogFragment extends AppCompatDialogFragment implements ISub {

	private CompositeDisposable mSubscriptions;
	private List<FragmentLifecycleListener> lifecycleListeners = new ArrayList<>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		for (FragmentLifecycleListener listener : lifecycleListeners) {
			listener.onCreate();
		}
		mSubscriptions = new CompositeDisposable();
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
		FragmentActivity activity = getActivity();
		if (activity != null) {
			for (OnBackPressedCallback callback : getOnBackPressCallbacks()) {
				activity.getOnBackPressedDispatcher().addCallback(this, callback);
			}
		}
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
		mSubscriptions.dispose();
	}

	public void addSubscription(Disposable subscription) {
		mSubscriptions.add(subscription);
	}

	@Override
	public void onNewSubscription(Disposable subscription) {
		addSubscription(subscription);
	}

	public void addLifecycleListener(FragmentLifecycleListener fragmentLifecycleListener) {
		lifecycleListeners.add(fragmentLifecycleListener);
	}

	protected List<OnBackPressedCallback> getOnBackPressCallbacks() {
		return new ArrayList<>();
	}
}

