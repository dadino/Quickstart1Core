package com.dadino.quickstart.core.utils;

import android.support.annotation.IntDef;
import android.text.TextUtils;
import android.util.Log;

import java.lang.annotation.Retention;
import java.util.HashMap;
import java.util.Map;

import static com.dadino.quickstart.core.utils.Logs.Logger.T_ERROR;
import static com.dadino.quickstart.core.utils.Logs.Logger.T_FCM;
import static com.dadino.quickstart.core.utils.Logs.Logger.T_LOGIN;
import static com.dadino.quickstart.core.utils.Logs.Logger.T_MODEL;
import static com.dadino.quickstart.core.utils.Logs.Logger.T_PRESENTER;
import static com.dadino.quickstart.core.utils.Logs.Logger.T_REPOSITORY;
import static com.dadino.quickstart.core.utils.Logs.Logger.T_RETROFIT;
import static com.dadino.quickstart.core.utils.Logs.Logger.T_RX;
import static com.dadino.quickstart.core.utils.Logs.Logger.T_TOKEN;
import static com.dadino.quickstart.core.utils.Logs.Logger.T_UI;
import static java.lang.annotation.RetentionPolicy.SOURCE;

public class Logs {

	public static final  int    DEBUG   = 0;
	public static final  int    INFO    = 1;
	public static final  int    WARNING = 2;
	public static final  int    WTF     = 3;
	public static final  int    ERROR   = 4;
	public static final  int    VERBOSE = 5;
	private static final Logger logger  = new Logger();

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

	public static void ui(String message, @LogLevel int logLevel) {
		log(T_UI, message, logLevel);
	}

	public static void retrofit(String message, @LogLevel int logLevel) {
		log(T_RETROFIT, message, logLevel);
	}

	public static void rx(String message, @LogLevel int logLevel) {
		log(T_RX, message, logLevel);
	}

	public static void presenter(String message, @LogLevel int logLevel) {
		log(T_PRESENTER, message, logLevel);
	}

	public static void token(String message, @LogLevel int logLevel) {
		log(T_TOKEN, message, logLevel);
	}

	public static void fcm(String message, @LogLevel int logLevel) {
		log(T_FCM, message, logLevel);
	}

	public static void error(String message, @LogLevel int logLevel) {
		log(T_ERROR, message, logLevel);
	}

	public static void model(String message, @LogLevel int logLevel) {
		log(T_MODEL, message, logLevel);
	}

	public static void login(String message, @LogLevel int logLevel) {
		log(T_LOGIN, message, logLevel);
	}

	public static void repository(String message, @LogLevel int logLevel) {
		log(T_REPOSITORY, message, logLevel);
	}

	public static void log(String tag, String message) {log(tag, message, DEBUG);}

	public static void log(String tag, String message, @LogLevel int logLevel) {
		if (logger.canLog(tag) && !TextUtils.isEmpty(message)) {
			switch (logLevel) {
				case DEBUG:
					Log.d(tag, message);
					break;
				case ERROR:
					Log.e(tag, message);
					break;
				case INFO:
					Log.i(tag, message);
					break;
				case VERBOSE:
					Log.v(tag, message);
					break;
				case WARNING:
					Log.w(tag, message);
					break;
				case WTF:
					Log.wtf(tag, message);
					break;
			}
		}
	}

	@Retention(SOURCE)
	@IntDef({DEBUG, INFO, WARNING, WTF, ERROR, VERBOSE})
	public @interface LogLevel {

	}

	protected static class Logger {

		protected static final String T_UI         = "UI_CALL";
		protected static final String T_RETROFIT   = "RETROFIT";
		protected static final String T_RX         = "RX";
		protected static final String T_PRESENTER  = "PRESENTER";
		protected static final String T_TOKEN      = "AUTH";
		protected static final String T_FCM        = "FCM";
		protected static final String T_ERROR      = "ERROR";
		protected static final String T_MODEL      = "MODEL_CALL";
		protected static final String T_LOGIN      = "LOGIN_CALL";
		protected static final String T_REPOSITORY = "REPOSITORY_CALL";

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
