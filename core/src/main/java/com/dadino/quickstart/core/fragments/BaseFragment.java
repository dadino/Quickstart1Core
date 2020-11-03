package com.dadino.quickstart.core.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dadino.quickstart.core.interfaces.FragmentLifecycleListener;
import com.dadino.quickstart.core.interfaces.IBackPressedClient;
import com.dadino.quickstart.core.interfaces.IBackPressedServer;
import com.dadino.quickstart.core.interfaces.ISub;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public abstract class BaseFragment extends Fragment implements ISub, IBackPressedClient {

    private CompositeDisposable mSubscriptions;
    private List<FragmentLifecycleListener> lifecycleListeners = new ArrayList<>();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IBackPressedServer)
            ((IBackPressedServer) context).addBackPressedClient(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (FragmentLifecycleListener listener : lifecycleListeners) {
            listener.onCreate();
        }
        mSubscriptions = new CompositeDisposable();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
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
    public void onStop() {
        super.onStop();
        for (FragmentLifecycleListener listener : lifecycleListeners) {
            listener.onStop();
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

    @Override
    public void onDetach() {
        if (getContext() instanceof IBackPressedServer)
            ((IBackPressedServer) getContext()).removeBackPressedClient(this);
        super.onDetach();
    }

    protected abstract void initPresenters();

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

    @Override
    public boolean onBackPressed() {
        return false;
    }
}

