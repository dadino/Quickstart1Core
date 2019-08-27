package com.dadino.quickstart.core.interfaces;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;

public interface DrawerToggleServer {

	DrawerLayout getDrawer();
	void setDrawerToggle(ActionBarDrawerToggle toggle);
	void removeDrawerToggle();
}
