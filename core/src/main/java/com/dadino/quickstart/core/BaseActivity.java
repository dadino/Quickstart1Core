package com.dadino.quickstart.core;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.dadino.quickstart.core.interfaces.ActivityLifecycleListener;
import com.dadino.quickstart.core.interfaces.ISub;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public abstract class BaseActivity extends AppCompatActivity implements ISub {

    private CompositeDisposable mSubscriptions;
    private List<ActivityLifecycleListener> lifecycleListeners = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubscriptions = new CompositeDisposable();
        initPresenters();
        for (ActivityLifecycleListener listener : lifecycleListeners) {
            listener.onCreate();
        }
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
        mSubscriptions.dispose();
    }

    public void addSubscription(Disposable subscription) {
        mSubscriptions.add(subscription);
    }

    @Override
    public void onNewSubscription(Disposable subscription) {
        addSubscription(subscription);
    }

    public abstract void initPresenters();

    public void addActivityLifecycleListener(ActivityLifecycleListener activityLifecycleListener) {
        lifecycleListeners.add(activityLifecycleListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        for (ActivityLifecycleListener listener : lifecycleListeners) {
            listener.onPause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        for (ActivityLifecycleListener listener : lifecycleListeners) {
            listener.onResume();
        }
    }
}