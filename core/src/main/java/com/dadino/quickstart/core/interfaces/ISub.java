package com.dadino.quickstart.core.interfaces;


import io.reactivex.disposables.Disposable;

public interface ISub {

    void onNewSubscription(Disposable subscription);
}
