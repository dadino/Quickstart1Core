package com.dadino.quickstart.core.utils;

import android.text.TextUtils;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class Logs {

	private static final Logger logger = new Logger();

	private static final String T_UI         = "UI_CALL";
	private static final String T_RETROFIT   = "RETROFIT";
	private static final String T_RX         = "RX";
	private static final String T_PRESENTER  = "PRESENTER";
	private static final String T_TOKEN      = "AUTH";
	private static final String T_FCM        = "FCM";
	private static final String T_ERROR      = "ERROR";
	private static final String T_MODEL      = "MODEL_CALL";
	private static final String T_LOGIN      = "LOGIN_CALL";
	private static final String T_REPOSITORY = "REPOSITORY_CALL";

	public static void setEnableLogging(boolean enable) {
		logger.setEnableLogging(enable);
	}

	public static void setEnabledTag(String tag, boolean enabled) {
		logger.setEnabledTag(tag, enabled);
	}

	public static void ui(String message) {
		log(T_UI, message);
	}

	public static void retrofit(String message) {
		log(T_RETROFIT, message);
	}


	public static void rx(String message) {
		log(T_RX, message);
	}

	public static void presenter(String message) {
		log(T_PRESENTER, message);
	}


	public static void token(String message) {
		log(T_TOKEN, message);
	}

	public static void fcm(String message) {
		log(T_FCM, message);
	}

	public static void error(String message) {
		log(T_ERROR, message);
	}


	public static void model(String message) {
		log(T_MODEL, message);
	}

	public static void login(String message) {
		log(T_LOGIN, message);
	}

	public static void repository(String message) {
		log(T_REPOSITORY, message);
	}


	public static void log(String tag, String message) {
		if (logger.canLog(tag) && !TextUtils.isEmpty(message)) {
			Log.d(tag, message);
		}
	}


	private static class Logger {

		private boolean              enableLogging;
		private Map<String, Boolean> enabledLogs;

		Logger() {
			enabledLogs = new HashMap<>();
			setEnableLogging(true);
			setEnabledTag(T_UI, true);
			setEnabledTag(T_RETROFIT, true);
			setEnabledTag(T_RX, true);
			setEnabledTag(T_PRESENTER, true);
			setEnabledTag(T_TOKEN, true);
			setEnabledTag(T_FCM, true);
			setEnabledTag(T_ERROR, true);
			setEnabledTag(T_MODEL, true);
			setEnabledTag(T_LOGIN, true);
			setEnabledTag(T_REPOSITORY, true);
		}

		boolean canLog(String tag) {
			return enabledLogs.containsKey(tag) && enabledLogs.get(tag) && isEnableLogging();
		}

		boolean isEnableLogging() {
			return enableLogging;
		}

		void setEnableLogging(boolean enableLogging) {
			this.enableLogging = enableLogging;
		}

		void setEnabledTag(String tag, boolean enabled) {
			enabledLogs.put(tag, enabled);
		}
	}
}
