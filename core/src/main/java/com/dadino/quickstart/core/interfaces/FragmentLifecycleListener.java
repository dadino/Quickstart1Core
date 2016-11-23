package com.dadino.quickstart.core.interfaces;


public interface FragmentLifecycleListener {

	void onStart();
	void onStop();
	void onCreate();
	void onDestroy();
	void onResume();
}
