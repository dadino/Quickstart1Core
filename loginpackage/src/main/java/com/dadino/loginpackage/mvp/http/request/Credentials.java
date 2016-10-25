package com.dadino.loginpackage.mvp.http.request;

import com.dadino.toolbox.mvp.components.SquareUtils;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Credentials {

	@Expose
	@SerializedName("Email")
	private String email;
	@Expose
	@SerializedName("Password")
	private String password;
	@Expose
	@SerializedName("FcmToken")
	private String fcmToken;
	@Expose
	@SerializedName("DeviceName")
	private String deviceName;

	public Credentials() {}

	public Credentials(String email, String password) {
		this.email = email;
		this.password = password;
		this.fcmToken = null;
		this.deviceName = SquareUtils.DEVICE_NAME;
	}

	public Credentials(String email, String password, String fcmToken) {
		this.email = email;
		this.password = password;
		this.fcmToken = fcmToken;
		this.deviceName = SquareUtils.DEVICE_NAME;
	}

	public Credentials(Credentials credentials, String fcmToken) {
		this.email = credentials.getEmail();
		this.password = credentials.getPassword();
		this.fcmToken = fcmToken;
		this.deviceName = SquareUtils.DEVICE_NAME;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFcmToken() {
		return fcmToken;
	}

	public void setFcmToken(String fcmToken) {
		this.fcmToken = fcmToken;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
}
