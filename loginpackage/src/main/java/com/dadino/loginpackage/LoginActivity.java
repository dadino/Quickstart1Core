package com.dadino.loginpackage;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dadino.loginpackage.mvp.components.Authenticator;
import com.dadino.loginpackage.mvp.http.request.Credentials;
import com.dadino.loginpackage.mvp.usecases.lastusedaccount.LastAccountMVP;
import com.dadino.loginpackage.mvp.usecases.login.LoginMVP;
import com.dadino.loginpackage.utils.Matcher;
import com.dadino.loginpackage.utils.SmartLockLoader;
import com.dadino.loginpackage.utils.SmartLockLogin;
import com.dadino.loginpackage.utils.UiUtils;
import com.dadino.toolbox.mvp.components.ErrorHandler;
import com.dadino.toolbox.mvp.components.presenter.MvpView;
import com.dadino.toolbox.mvp.components.presenter.PresenterManager;
import com.google.android.gms.auth.api.credentials.Credential;

import static butterknife.ButterKnife.findById;

public class LoginActivity extends AccountAuthenticatorActivity implements SmartLockLogin.Callbacks,
		LoaderManager.LoaderCallbacks<SmartLockLogin> {

	public final static  String ARG_ACCOUNT_TYPE            = "ACCOUNT_TYPE";
	public final static  String ARG_AUTH_TYPE               = "AUTH_TYPE";
	public final static  String ARG_ACCOUNT_NAME            = "ACCOUNT_NAME";
	public final static  String ARG_IS_ADDING_NEW_ACCOUNT   = "IS_ADDING_ACCOUNT";
	public static final  int    LOGIN_ACTIVITY_REQUEST_CODE = 432;
	private static final int    SMART_LOCK_LOGIN_LOADER_ID  = 451;
	private static final String PARAM_USER_PASS             = "PARAM_USER_PASS";

	TextView             emailField;
	TextView             passwordField;
	TextInputLayout      emailFieldContainer;
	TextInputLayout      passwordFieldContainer;
	Button               login;
	Button               register;
	CardView             panel;
	TextView             continueAs;
	View                 continueAsContainer;
	View                 loginContainer;
	View                 progress;
	View                 registerBackground;
	FloatingActionButton fab;
	CoordinatorLayout    coordinator;

	private String                               username;
	private String                               password;
	private SmartLockLogin                       smartLock;
	private PresenterManager<LoginMVP.Presenter> loginPresenterManager;

	private boolean isLogin = true;
	private PresenterManager<LastAccountMVP.Presenter> lastAccountPresenterManager;
	private MvpView<String> iLoginView = new MvpView<>(this::onEmailLoginSuccessful,
			this::onLoginError, this::onLoginLoading, this);

	public static void show(Context context) {
		context.startActivity(new Intent(context, LoginActivity.class));
	}

	public static void showForResult(Activity context) {
		context.startActivityForResult(new Intent(context, LoginActivity.class),
				LOGIN_ACTIVITY_REQUEST_CODE);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initViews();

		loginPresenterManager = new PresenterManager<>(this, new LoginMVP.Factory(), null).bindTo(
				this);
		lastAccountPresenterManager = new PresenterManager<>(this, new LastAccountMVP.Factory(),
				null).bindTo(this);
		getSupportLoaderManager().initLoader(SMART_LOCK_LOGIN_LOADER_ID, null, this);
	}

	private void initViews() {
		Toolbar toolbar = findById(this, R.id.activity_toolbar);
		setSupportActionBar(toolbar);
		emailField = (TextView) findViewById(R.id.login_email);
		passwordField = (TextView) findViewById(R.id.login_password);
		emailFieldContainer = (TextInputLayout) findViewById(R.id.login_email_container);
		passwordFieldContainer = (TextInputLayout) findViewById(R.id.login_password_container);
		login = (Button) findViewById(R.id.login_login_section);
		register = (Button) findViewById(R.id.login_register_section);
		panel = (CardView) findViewById(R.id.login_panel);
		continueAs = (TextView) findViewById(R.id.login_continue_as);
		continueAsContainer = findViewById(R.id.login_continue_as_container);
		loginContainer = findViewById(R.id.login_panel_container);
		progress = findViewById(R.id.login_progress);
		registerBackground = findViewById(R.id.login_colored_background);
		fab = (FloatingActionButton) findViewById(R.id.login_fab);
		coordinator = (CoordinatorLayout) findViewById(R.id.login_coordinator);

		findViewById(R.id.login_choose_email).setOnClickListener(view -> showAccountSelection());
		findViewById(R.id.login_register_section).setOnClickListener(view -> showRegisterSection
				());
		findViewById(R.id.login_login_section).setOnClickListener(view -> showLoginSection());
		findViewById(R.id.login_fab).setOnClickListener(view -> checkFieldAndLogin());
	}

	public void showAccountSelection() {
		smartLock.showEmailHints();
	}

	public void showRegisterSection() {
		isLogin = false;
		register.setEnabled(false);
		login.setEnabled(true);
		UiUtils.animateReveal(registerBackground, register, true);
		login.setTextColor(
				ContextCompat.getColor(this, R.color.login_disabled_section_header_color));
		register.setTextColor(
				ContextCompat.getColor(this, R.color.login_enabled_section_header_color));
	}

	public void showLoginSection() {
		isLogin = true;
		register.setEnabled(true);
		login.setEnabled(false);
		UiUtils.animateReveal(registerBackground, register, false);
		login.setTextColor(
				ContextCompat.getColor(this, R.color.login_enabled_section_header_color));
		register.setTextColor(
				ContextCompat.getColor(this, R.color.login_disabled_section_header_color));
	}

	private void fillFields(String email, String password, boolean autoSignIn) {
		emailField.setText(email);
		passwordField.setText(password);
		if (autoSignIn) checkFieldAndLogin();
	}

	@Override
	public void onLoginRequestedWith(Credential credential) {
		fillFields(credential.getId(), credential.getPassword(), true);
	}

	@Override
	public void onExistingAccountFound(Credential credential) {
		showContinueAs();
	}

	@Override
	public void onMultipleExistingAccounts() {
		showContinueAs();
	}

	@Override
	public void onHintChoosen(String email, String password) {
		fillFields(email, password, false);
	}

	@Override
	public void onSaveToSmartLockFinished() {
		finish();
	}

	private void showContinueAs() {
		if (smartLock.getExistingAccount() == null &&
		    smartLock.getMultipleAccountsStatus() == null) {
			continueAsContainer.setVisibility(View.GONE);
			return;
		}

		final Credential credential = smartLock.getExistingAccount();
		String mContinueAsText = credential != null ? getString(R.string.login_continue_as,
				credential.getId()) : getString(R.string.login_multiple_existing_account);
		continueAs.setText(mContinueAsText);
		continueAsContainer.setVisibility(View.VISIBLE);
		continueAsContainer.setOnClickListener(
				credential != null ? view -> smartLock.loginWithExisting() :
				view -> smartLock.showMultipleExistingAccounts());
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		smartLock.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected void onStart() {
		super.onStart();

		if (loginPresenter() != null) loginPresenter().addView(iLoginView);
		smartLock.bind(this, this);
		showContinueAs();
	}

	@Override
	protected void onStop() {
		if (loginPresenter() != null) loginPresenter().removeView(iLoginView);
		smartLock.unbind();
		super.onStop();
	}

	@Override
	public SmartLockLoader onCreateLoader(int id, Bundle args) {
		return new SmartLockLoader(this);
	}

	@Override
	public void onLoadFinished(Loader<SmartLockLogin> loader, SmartLockLogin data) {
		smartLock = data;
	}

	@Override
	public void onLoaderReset(Loader<SmartLockLogin> loader) {
		smartLock = null;
	}

	public void checkFieldAndLogin() {
		emailFieldContainer.setError(null);
		passwordFieldContainer.setError(null);

		username = emailField.getText()
		                     .toString();
		password = passwordField.getText()
		                        .toString();

		if (!Matcher.email(username)) {
			emailFieldContainer.setError(getString(R.string.login_invalid_email));
			return;
		}

		if (TextUtils.isEmpty(password)) {
			passwordFieldContainer.setError(getString(R.string.login_password_needed));
			return;
		}

		final int minChar = 6;
		if (password.length() < minChar) {
			passwordFieldContainer.setError(getString(R.string.login_password_too_short, minChar));
			return;
		}

		loginOrRegister();
	}

	private void loginOrRegister() {
		if (isLogin) login();
		else register();
	}

	private void register() {
		loginPresenter().onRegisterRequested(new Credentials(username, password));
	}

	private void login() {loginPresenter().onLoginRequested(new Credentials(username, password));}

	private void onEmailLoginSuccessful(String token) {
		if (lastAccountPresenter() != null) lastAccountPresenter().onAccountSelected(username);
		smartLock.onSuccessfulLogin(username, password);
		sendLoginIntent(token);
	}

	private LastAccountMVP.Presenter lastAccountPresenter() {
		return lastAccountPresenterManager.get();
	}

	private void onLoginLoading(boolean loading) {
		progress.setVisibility(loading ? View.VISIBLE : View.GONE);
		loginContainer.setVisibility(loading ? View.GONE : View.VISIBLE);
		if (loading) fab.hide();
		else fab.show();
	}

	private void onLoginError(Throwable error) {
		int errorCode = ErrorHandler.analyzeError(error);
		if (errorCode == ErrorHandler.ERROR_HTTP_UNAUTHORIZED) smartLock.onUnsuccessfulLogin(
				username, password);
		Snackbar.make(coordinator, ErrorHandler.getErrorMessageForUser(this, error),
				Snackbar.LENGTH_LONG)
		        .show();
	}

	private void sendLoginIntent(String token) {
		Intent loginIntent = getAccountIntent(username, password, token);
		setAccountAuthenticatorResult(loginIntent.getExtras());
		setResult(RESULT_OK, loginIntent);
	}

	private Intent getAccountIntent(String username, String password, String token) {
		Intent loginIntent = new Intent();
		loginIntent.putExtra(AccountManager.KEY_ACCOUNT_NAME, username);
		loginIntent.putExtra(AccountManager.KEY_ACCOUNT_TYPE, Authenticator.IT_LAMINOX_AUTH_EMAIL);
		loginIntent.putExtra(PARAM_USER_PASS, password);
		loginIntent.putExtra(AccountManager.KEY_AUTHTOKEN, token);
		return loginIntent;
	}

	private LoginMVP.Presenter loginPresenter() {
		return loginPresenterManager.get();
	}
}
