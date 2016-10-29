package com.dadino.quickstart.core.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.dadino.quickstart.core.R;
import com.dadino.quickstart.core.interfaces.DrawerToggleServer;
import com.dadino.quickstart.core.utils.Logs;


public abstract class DrawerToggleFragment extends BaseFragment {

	private boolean mShouldSetupDrawerToggle;

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		if (mShouldSetupDrawerToggle) setupDrawerToggle(getActivity());
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		setupDrawerToggle(context);
	}

	@Override
	public void onDetach() {
		resetDrawerToggle(getActivity());
		super.onDetach();
	}

	abstract Toolbar toolbar();

	private void setupDrawerToggle(Context context) {
		mShouldSetupDrawerToggle = true;
		if (context instanceof DrawerToggleServer && context instanceof AppCompatActivity &&
		    toolbar() != null) {
			Logs.ui("Setting drawer toggle to fragment's toolbar");

			final DrawerToggleServer server = (DrawerToggleServer) context;
			final AppCompatActivity activity = (AppCompatActivity) context;
			final ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(activity,
					server.getDrawer(), toolbar(), R.string.navigation_drawer_open,
					R.string.navigation_drawer_close);
			server.setDrawerToggle(mDrawerToggle);
			activity.setSupportActionBar(toolbar());
			if (activity.getSupportActionBar() != null) {
				activity.getSupportActionBar()
				        .setDisplayHomeAsUpEnabled(true);
				activity.getSupportActionBar()
				        .setDisplayShowHomeEnabled(true);
				activity.getSupportActionBar()
				        .setTitle(title());
			}

			mShouldSetupDrawerToggle = false;
		} else if (context instanceof AppCompatActivity && toolbar() != null) {
			final AppCompatActivity activity = (AppCompatActivity) context;
			activity.setSupportActionBar(toolbar());
			if (activity.getSupportActionBar() != null) {
				activity.getSupportActionBar()
				        .setDisplayHomeAsUpEnabled(true);
				activity.getSupportActionBar()
				        .setDisplayShowHomeEnabled(true);
				activity.getSupportActionBar()
				        .setTitle(title());
			}
		}
	}

	protected abstract int title();

	private void resetDrawerToggle(Context context) {
		mShouldSetupDrawerToggle = true;
		if (context instanceof DrawerToggleServer) {
			Logs.ui("Resetting drawer toggle");

			final DrawerToggleServer server = (DrawerToggleServer) context;

			server.removeDrawerToggle();
			mShouldSetupDrawerToggle = true;
		}
	}
}
