package com.dadino.toolbox.mvp.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.dadino.toolbox.interfaces.IRepository;


public abstract class PrefRepository implements IRepository,
		SharedPreferences.OnSharedPreferenceChangeListener {

	private final SharedPreferences prefs;

	public PrefRepository(Context context) {
		prefs = PreferenceManager.getDefaultSharedPreferences(context);
		prefs.registerOnSharedPreferenceChangeListener(this);
	}

	@Override
	public void onDestroy() {
		prefs.unregisterOnSharedPreferenceChangeListener(this);
	}

	protected abstract String listenOn();

	protected SharedPreferences pref() {
		return prefs;
	}

	protected SharedPreferences.Editor editor() {
		return prefs.edit();
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
		if (listenOn().equals(s)) onPrefChanged();
	}

	protected abstract void onPrefChanged();

	protected abstract String getKey();
}
