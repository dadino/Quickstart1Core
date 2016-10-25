package com.dadino.loginpackage.utils;

import android.text.TextUtils;

public class Matcher {

	public static boolean email(String email) {
		return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email)
		                                                                       .matches();
	}
}

