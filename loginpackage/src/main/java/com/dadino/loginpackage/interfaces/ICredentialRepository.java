package com.dadino.loginpackage.interfaces;


import com.dadino.loginpackage.mvp.http.request.Credentials;
import com.dadino.toolbox.interfaces.IRepository;

import java.util.List;

import rx.Single;

public interface ICredentialRepository extends IRepository {

	Single<Credentials> retrieve(String username);
	Single<List<Credentials>> retrieve();
	Single<Boolean> create(String username, String password);
	Single<Boolean> delete(String username);
	Single<Boolean> update(String username, String password);
}
