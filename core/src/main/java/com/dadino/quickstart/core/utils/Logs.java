package com.dadino.quickstart.core.utils;

import android.text.TextUtils;
import android.util.Log;

import com.dadino.quickstart.core.BuildConfig;

public class Logs {

	private static final boolean DEBUG        = BuildConfig.DEBUG;
	private static final boolean UI           = true && DEBUG;
	private static final boolean RETROFIT     = true && DEBUG;
	private static final boolean RX           = true && DEBUG;
	private static final boolean PRESENTER    = true && DEBUG;
	private static final boolean TOKEN        = true && DEBUG;
	private static final boolean FCM          = true && DEBUG;
	private static final boolean ERROR        = true && DEBUG;
	private static final boolean MODEL        = true && DEBUG;
	private static final boolean LOGIN        = true && DEBUG;
	private static final boolean REPOSITORY   = true && DEBUG;
	private static final String  T_UI         = "UI_CALL";
	private static final String  T_RETROFIT   = "RETROFIT";
	private static final String  T_RX         = "RX";
	private static final String  T_PRESENTER  = "PRESENTER";
	private static final String  T_TOKEN      = "AUTH";
	private static final String  T_FCM        = "FCM";
	private static final String  T_ERROR      = "ERROR";
	private static final String  T_MODEL      = "MODEL_CALL";
	private static final String  T_LOGIN      = "LOGIN_CALL";
	private static final String  T_REPOSITORY = "REPOSITORY_CALL";


	public static void ui(String message) {
		if (UI && !TextUtils.isEmpty(message)) {
			Log.d(T_UI, message);
		}
	}

	public static void retrofit(String message) {
		if (RETROFIT && !TextUtils.isEmpty(message)) {
			Log.d(T_RETROFIT, message);
		}
	}


	public static void rx(String message) {
		if (RX && !TextUtils.isEmpty(message)) {
			Log.d(T_RX, message);
		}
	}

	public static void presenter(String message) {
		if (PRESENTER && !TextUtils.isEmpty(message)) {
			Log.d(T_PRESENTER, message);
		}
	}


	public static void token(String message) {
		if (TOKEN && !TextUtils.isEmpty(message)) {
			Log.d(T_TOKEN, message);
		}
	}

	public static void fcm(String message) {
		if (FCM && !TextUtils.isEmpty(message)) {
			Log.d(T_FCM, message);
		}
	}

	public static void error(String message) {
		if (ERROR && !TextUtils.isEmpty(message)) {
			Log.d(T_ERROR, message);
		}
	}


	public static void model(String message) {
		if (MODEL && !TextUtils.isEmpty(message)) {
			Log.d(T_MODEL, message);
		}
	}

	public static void login(String message) {
		if (LOGIN && !TextUtils.isEmpty(message)) {
			Log.d(T_LOGIN, message);
		}
	}

	public static void repository(String message) {
		if (REPOSITORY && !TextUtils.isEmpty(message)) {
			Log.d(T_REPOSITORY, message);
		}
	}
}
