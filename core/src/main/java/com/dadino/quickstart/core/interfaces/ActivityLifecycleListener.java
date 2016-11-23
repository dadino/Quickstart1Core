package com.dadino.quickstart.core.interfaces;


public interface ActivityLifecycleListener {

	void onStart();
	void onStop();
	void onCreate();
	void onDestroy();
	void onResume();
}
