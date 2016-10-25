package com.dadino.loginpackage.interfaces;

import com.dadino.toolbox.interfaces.IRepository;

import rx.Single;

public interface ITokenRepository extends IRepository {

	Single<String> retrieve(String username);
	Single<Boolean> create(String username, String token);
	Single<Boolean> delete(String username);
	Single<Boolean> update(String username, String token);
}
