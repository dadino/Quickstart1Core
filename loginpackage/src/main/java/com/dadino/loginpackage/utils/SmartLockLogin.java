package com.dadino.loginpackage.utils;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dadino.toolbox.BaseActivity;
import com.dadino.toolbox.utils.Logs;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.CredentialPickerConfig;
import com.google.android.gms.auth.api.credentials.CredentialRequest;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.auth.api.credentials.IdentityProviders;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;

import static android.app.Activity.RESULT_OK;


public class SmartLockLogin implements GoogleApiClient.ConnectionCallbacks,
		GoogleApiClient.OnConnectionFailedListener {

	private static final int RC_READ = 1;
	private static final int RC_HINT = 2;
	private static final int RC_SAVE = 3;

	private BaseActivity    activity;
	private Callbacks       callbacks;
	private GoogleApiClient mCredentialsClient;
	private boolean         mIsReading;
	private boolean         mIsHinting;
	private boolean         mIsSaving;
	private boolean         clientConnected;
	private Status          mMultipleAccountsStatus;
	private Credential      mExistingAccount;

	/* TODO check out "login with ID Token" at
	 https://developers.google.com/identity/smartlock-passwords/android/idtoken-auth
	*/
	public SmartLockLogin() {

	}

	public void bind(BaseActivity activity, Callbacks callbacks) {
		setActivity(activity);
		setCallbacks(callbacks);
		if (mCredentialsClient == null) {
			mCredentialsClient = new GoogleApiClient.Builder(
					activity.getApplicationContext()).addConnectionCallbacks(this)
			                                         //.enableAutoManage(activity, this)
			                                         .addApi(Auth.CREDENTIALS_API)
			                                         .build();
			mCredentialsClient.connect();
		}
	}

	public void unbind() {
		setActivity(null);
		setCallbacks(null);
	}

	private void setActivity(BaseActivity activity) {
		this.activity = activity;
	}

	private void setCallbacks(Callbacks callbacks) {
		this.callbacks = callbacks;
	}

	@Override
	public void onConnected(@Nullable Bundle bundle) {
		Logs.login("OnConnection established");
		clientConnected = true;
		CredentialRequest mCredentialRequest =
				new CredentialRequest.Builder().setPasswordLoginSupported(true)
				                               .setAccountTypes(IdentityProviders.GOOGLE,
						                               IdentityProviders.FACEBOOK,
						                               IdentityProviders.LINKEDIN,
						                               IdentityProviders.MICROSOFT,
						                               IdentityProviders.PAYPAL,
						                               IdentityProviders.YAHOO,
						                               IdentityProviders.TWITTER)
				                               .build();

		Auth.CredentialsApi.request(mCredentialsClient, mCredentialRequest)
		                   .setResultCallback(credentialRequestResult -> {
			                   if (credentialRequestResult.getStatus()
			                                              .isSuccess()) {
				                   onCredentialRetrieved(credentialRequestResult.getCredential());
			                   } else {
				                   resolveResult(credentialRequestResult.getStatus());
			                   }
		                   });
	}

	@Override
	public void onConnectionSuspended(int i) {
		Logs.login("OnConnection suspended");
		clientConnected = false;
	}

	@Override
	public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
		Logs.login("OnConnection failed: " + connectionResult.getErrorMessage());
		clientConnected = false;
	}

	private void onCredentialRetrieved(Credential credential) {
		mExistingAccount = credential;
		if (callbacks != null) callbacks.onExistingAccountFound(credential);
	}

	private void resolveResult(Status status) {
		if (mIsReading) return;
		if (status.getStatusCode() == CommonStatusCodes.RESOLUTION_REQUIRED) {
			mMultipleAccountsStatus = status;
			if (callbacks != null) callbacks.onMultipleExistingAccounts();
		} else {
			// The user must create an account or sign in manually.
			Logs.login("No saved credentials");
			showEmailHints();
		}
	}

	public void showMultipleExistingAccounts() {
		try {
			mMultipleAccountsStatus.startResolutionForResult(activity, RC_READ);
			mIsReading = true;
		} catch (IntentSender.SendIntentException e) {
			e.printStackTrace();
		}
	}

	public void showEmailHints() {
		if (!clientConnected) return;
		if (mIsHinting) return;
		HintRequest hintRequest = new HintRequest.Builder().setHintPickerConfig(
				new CredentialPickerConfig.Builder().setShowCancelButton(true)
				                                    .build())
		                                                   .setEmailAddressIdentifierSupported
				                                                   (true)
		                                                   .setAccountTypes(
				                                                   IdentityProviders.GOOGLE,
				                                                   IdentityProviders.FACEBOOK,
				                                                   IdentityProviders.LINKEDIN,
				                                                   IdentityProviders.MICROSOFT,
				                                                   IdentityProviders.PAYPAL,
				                                                   IdentityProviders.YAHOO,
				                                                   IdentityProviders.TWITTER)
		                                                   .build();

		PendingIntent intent = Auth.CredentialsApi.getHintPickerIntent(mCredentialsClient,
				hintRequest);
		try {
			activity.startIntentSenderForResult(intent.getIntentSender(), RC_HINT, null, 0, 0, 0);
			mIsHinting = true;
		} catch (IntentSender.SendIntentException e) {
			Logs.login("Could not start hint picker Intent");
			e.printStackTrace();
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == RC_READ) {
			mIsReading = false;
			if (resultCode == RESULT_OK) {
				mExistingAccount = data.getParcelableExtra(Credential.EXTRA_KEY);
				loginWithExisting();
			} else {
				Logs.login("Credentials reading failed");
			}
		}

		if (requestCode == RC_HINT) {
			mIsHinting = false;
			if (resultCode == RESULT_OK) {
				Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
				onHintChoosen(credential);
			} else {
				Logs.login("Hint reading failed");
			}
		}

		if (requestCode == RC_SAVE) {
			mIsSaving = false;
			if (callbacks != null) callbacks.onSaveToSmartLockFinished();
			if (resultCode == RESULT_OK) {
				Logs.login("Credentials saved");
			} else {
				Logs.login("Credentials save canceled by user");
			}
		}
	}

	public void onSuccessfulLogin(String email, String password) {
		if (!clientConnected) return;
		Credential credential = new Credential.Builder(email).setPassword(password)
		                                                     .build();
		Logs.login("Saving credentials to SmartLock");
		Auth.CredentialsApi.save(mCredentialsClient, credential)
		                   .setResultCallback(result -> {
			                   Status status = result.getStatus();
			                   if (status.isSuccess()) {
				                   Logs.login("Credentials saved");
				                   if (callbacks != null) callbacks.onSaveToSmartLockFinished();
			                   } else {
				                   if (status.hasResolution()) {
					                   if (mIsSaving) {
						                   if (callbacks != null)
							                   callbacks.onSaveToSmartLockFinished();
						                   return;
					                   }
					                   try {
						                   status.startResolutionForResult(activity, RC_SAVE);
						                   mIsSaving = true;
					                   } catch (IntentSender.SendIntentException e) {
						                   Logs.login("Credentials save failed");
						                   if (callbacks != null)
							                   callbacks.onSaveToSmartLockFinished();
					                   }
				                   } else {
					                   Logs.login("Credentials save failed");
					                   if (callbacks != null) callbacks
							                   .onSaveToSmartLockFinished();
				                   }
			                   }
		                   });
	}

	public void onUnsuccessfulLogin(String email, String password) {
		if (!clientConnected) return;
		Credential credential = new Credential.Builder(email).setPassword(password)
		                                                     .build();

		Auth.CredentialsApi.delete(mCredentialsClient, credential)
		                   .setResultCallback(result -> {
			                   Status status = result.getStatus();
			                   if (status.isSuccess()) {
				                   Logs.login("Credentials deleted");
			                   }
		                   });
	}

	private void onHintChoosen(Credential credential) {
		if (callbacks != null) callbacks.onHintChoosen(credential.getId(),
				credential.getPassword());
	}

	public Credential getExistingAccount() {
		return mExistingAccount;
	}

	public Status getMultipleAccountsStatus() {
		return mMultipleAccountsStatus;
	}

	public void loginWithExisting() {
		if (callbacks != null) callbacks.onLoginRequestedWith(getExistingAccount());
	}

	public void onDestroy() {

		if (mCredentialsClient != null &&
		    (mCredentialsClient.isConnected() || mCredentialsClient.isConnecting()))
			mCredentialsClient.disconnect();
	}

	public interface Callbacks {

		void onLoginRequestedWith(Credential credential);
		void onExistingAccountFound(Credential credential);
		void onMultipleExistingAccounts();
		void onHintChoosen(String email, String password);
		void onSaveToSmartLockFinished();
	}
}
