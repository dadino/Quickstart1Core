package com.dadino.loginpackage.interfaces;


import com.dadino.loginpackage.mvp.http.request.Credentials;
import com.dadino.loginpackage.mvp.http.response.BaseBody;

import retrofit2.Response;
import rx.Single;

public interface IWebApiRepository {

	Single<Response<BaseBody<String>>> login(Credentials credentials);
	Single<Response<BaseBody<String>>> register(Credentials credentials);
}
