package com.dadino.quickstart.core.mvp.components;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.Log;

import com.dadino.quickstart.core.utils.Logs;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;

public class ErrorHandler {

	public static final  int ERROR_NO_CREDENTIAL     = -6;
	public static final  int ERROR_HTTP_UNAUTHORIZED = 401;
	private static final int ERROR_UNKNOWN           = 0;
	private static final int ERROR_HTTP_CLIENT_ERROR = -4;
	private static final int ERROR_HTTP_SERVER_ERROR = -5;
	private static final int ERROR_CONNECTION        = 1;

	public static int analyzeError(Throwable e) {return analyzeError(e, true);}

	public static int analyzeError(Throwable e, boolean print) {
		if (print) e.printStackTrace();

		if (e instanceof HttpException) {
			HttpException http = (HttpException) e;
			int code = http.code();
			try {
				Log.d("Error Handler", code + " - " + http.message() + " for: " + http.response()
				                                                                      .raw()
				                                                                      .request()
				                                                                      .url()
				                                                                      .toString());
			} catch (Exception ignored) {
			}

			if (code >= 500 && code < 600) return ERROR_HTTP_SERVER_ERROR;
			if (code == 400 || code >= 402 && code < 500) return ERROR_HTTP_CLIENT_ERROR;
			if (code == 401) return ERROR_HTTP_UNAUTHORIZED;
			return code;
		}

		if (e instanceof ConnectException || e instanceof SocketTimeoutException ||
		    e instanceof UnknownHostException) {
			return ERROR_CONNECTION;
		}

		return ERROR_UNKNOWN;
	}

	public static boolean shouldRefreshToken(Throwable throwable) {
		Logs.error("Checking if a token refresh is needed");
		return analyzeError(throwable, false) == ERROR_HTTP_UNAUTHORIZED;
	}

	public static boolean needsCredential(Throwable throwable) {
		return analyzeError(throwable, false) == ERROR_NO_CREDENTIAL;
	}

	public static boolean shouldSkipAndRetryLater(Throwable throwable) {
		final int error = analyzeError(throwable, false);
		return error == ERROR_HTTP_SERVER_ERROR;
	}

	public static boolean isRecoverable(Integer tries, Throwable throwable) {
		int code = analyzeError(throwable, false);

		switch (code) {
			case ERROR_HTTP_SERVER_ERROR:
			case ERROR_CONNECTION:
				return tries < 3;
			default:
				return false;
		}
	}

	public static boolean shouldReschedule(Throwable t) {
		return isRecoverable(0, t);
	}

	public static boolean isValid(Response response) {
		return response.code() >= 200 && response.code() < 300;
	}

	@NonNull
	public static String getErrorMessageForUser(Context context, Throwable throwable) {
		return getErrorMessageForUser(context.getResources(), throwable);
	}

	@NonNull
	public static String getErrorMessageForUser(Resources res, Throwable throwable) {
		//TODO write an understandable message for the user
		return "C'è stato un errore";
	}

	public static class NoCredentialError extends RuntimeException {

		public NoCredentialError() {
			super("No credential saved to attempt login");
		}
	}

	public static class LoginError extends RuntimeException {

		private final Throwable mOriginalError;

		public LoginError(Throwable originalError) {
			super("Error while logging in");
			this.mOriginalError = originalError;
		}

		public Throwable getOriginalError() {
			return mOriginalError;
		}

		@Override
		public void printStackTrace() {
			mOriginalError.printStackTrace();
		}
	}
}
