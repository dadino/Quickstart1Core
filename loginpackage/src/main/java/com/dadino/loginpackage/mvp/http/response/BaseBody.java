package com.dadino.loginpackage.mvp.http.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.Response;

public class BaseBody<T> {

	@Expose
	@SerializedName("ErrorCode")
	private int errorCode;
	@Expose
	@SerializedName("Result")
	private T   result;

	public BaseBody() {}

	public static <D> D data(Response<BaseBody<D>> response) {
		if (!response.isSuccessful()) return null;

		final BaseBody<D> baseBody = response.body();
		if (baseBody == null || !baseBody.isSuccess()) return null;

		return baseBody.data();
	}

	public static <D> D dataResilient(Response<BaseBody<D>> response) {
		if (!response.isSuccessful()) return null;

		final BaseBody<D> baseBody = response.body();
		if (baseBody == null) return null;

		return baseBody.data();
	}

	public static boolean success(Response<BaseBody> response) {
		if (!response.isSuccessful()) return false;

		final BaseBody baseBody = response.body();
		return !(baseBody == null || !baseBody.isSuccess());
	}

	public int code() {
		return errorCode;
	}

	public T data() {
		return result;
	}

	public boolean isSuccess() {
		return code() == 0;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}
}
