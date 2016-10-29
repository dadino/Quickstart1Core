package com.dadino.quickstart.core.interfaces;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;

public interface DrawerToggleServer {

	DrawerLayout getDrawer();
	void setDrawerToggle(ActionBarDrawerToggle toggle);
	void removeDrawerToggle();
}
