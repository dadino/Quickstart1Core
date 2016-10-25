package com.dadino.loginpackage;

import com.dadino.loginpackage.interfaces.IWebApiRepository;

public class LoginPackage {

	public static IWebApiRepository loginRepository;

	public static IWebApiRepository getLoginRepository() {
		if (loginRepository == null) throw new RuntimeException(
				"You didn't set a login repository, use LoginPackage.setLoginRepository to set " +
				"it" +
				".");
		return loginRepository;
	}

	public static void setLoginRepository(IWebApiRepository repository) {
		loginRepository = repository;
	}
}
