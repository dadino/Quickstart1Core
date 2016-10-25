package com.dadino.loginpackage.mvp.components;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.dadino.loginpackage.LoginActivity;
import com.dadino.loginpackage.R;
import com.dadino.loginpackage.mvp.http.request.Credentials;
import com.dadino.loginpackage.mvp.usecases.login.LoginMVP;
import com.dadino.loginpackage.mvp.usecases.login.LoginModel;


public class Authenticator extends AbstractAccountAuthenticator {

	public static final String IT_LAMINOX_AUTH_EMAIL = "it.laminox.auth.email";
	private final Context mContext;

	public Authenticator(Context context) {
		super(context);
		this.mContext = context.getApplicationContext();
	}

	@Override
	public Bundle editProperties(AccountAuthenticatorResponse accountAuthenticatorResponse,
	                             String s) {
		return null;
	}

	@Override
	public Bundle addAccount(AccountAuthenticatorResponse response, String accountType,
	                         String authTokenType, String[] requiredFeatures, Bundle options)
			throws
			NetworkErrorException {
		final Intent intent = new Intent(mContext, LoginActivity.class);
		intent.putExtra(LoginActivity.ARG_ACCOUNT_TYPE, accountType);
		intent.putExtra(LoginActivity.ARG_AUTH_TYPE, authTokenType);
		intent.putExtra(LoginActivity.ARG_IS_ADDING_NEW_ACCOUNT, true);
		intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
		final Bundle bundle = new Bundle();
		bundle.putParcelable(AccountManager.KEY_INTENT, intent);
		return bundle;
	}

	@Override
	public Bundle confirmCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse,
	                                 Account account, Bundle bundle) throws
			NetworkErrorException {
		return null;
	}

	@Override
	public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account,
	                           String authTokenType, Bundle options) throws
			NetworkErrorException {
		// Extract the username and password from the Account Manager, and ask
		// the server for an appropriate AuthToken.
		final AccountManager am = AccountManager.get(mContext);

		String authToken = am.peekAuthToken(account, authTokenType);

		// Lets give another try to authenticate the user
		if (TextUtils.isEmpty(authToken)) {
			final String password = am.getPassword(account);
			if (password != null) {
				LoginMVP.Model model = new LoginModel(mContext);
				Credentials credentials = new Credentials(account.name, password, "");
				authToken = model.login(credentials)
				                 .toBlocking()
				                 .value();
			}
		}

		// If we get an authToken - we return it
		if (!TextUtils.isEmpty(authToken)) {
			final Bundle result = new Bundle();
			result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
			result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
			result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
			return result;
		}

		// If we get here, then we couldn't access the user's password - so we
		// need to re-prompt them for their credentials. We do that by creating
		// an intent to display our AuthenticatorActivity.
		final Intent intent = new Intent(mContext, LoginActivity.class);
		intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
		intent.putExtra(LoginActivity.ARG_ACCOUNT_TYPE, account.type);
		intent.putExtra(LoginActivity.ARG_AUTH_TYPE, authTokenType);
		final Bundle bundle = new Bundle();
		bundle.putParcelable(AccountManager.KEY_INTENT, intent);
		return bundle;
	}

	@Override
	public String getAuthTokenLabel(String s) {
		return mContext.getString(R.string.login_auth_token_label);
	}

	@Override
	public Bundle updateCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse,
	                                Account account, String s, Bundle bundle) throws
			NetworkErrorException {
		return null;
	}

	@Override
	public Bundle hasFeatures(AccountAuthenticatorResponse accountAuthenticatorResponse,
	                          Account account, String[] strings) throws
			NetworkErrorException {
		return null;
	}
}
