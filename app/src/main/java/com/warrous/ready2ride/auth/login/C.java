package com.warrous.ready2ride.auth.login;


public class C {
	
	public static final String TAG = "OAuthExample";

	public static final String CONSUMER_KEY 	= "85862604426-788s4ptbo71tdsh2r7q84lb4mogkkih1.apps.googleusercontent.com";
	public static final String CONSUMER_SECRET 	= "L6EQkhvPQCZrJ6YSwUxD5uvD";

	public static final String SCOPE 			= "https://www.google.com/m8/feeds/";
	public static final String REQUEST_URL 		= "https://www.google.com/accounts/OAuthGetRequestToken";
	public static final String ACCESS_URL 		= "https://www.google.com/accounts/OAuthGetAccessToken";  
	public static final String AUTHORIZE_URL 	= "https://www.google.com/accounts/OAuthAuthorizeToken";
	
	public static final String GET_CONTACTS_FROM_GOOGLE_REQUEST 		= "https://www.google.com/m8/feeds/contacts/default/full?alt=json";
	
	public static final String ENCODING 		= "UTF-8";
	
	public static final String	OAUTH_CALLBACK_SCHEME	= "com.warrous.ready2ride";
	public static final String	OAUTH_CALLBACK_HOST		= "callback";
	public static final String	OAUTH_CALLBACK_URL		= OAUTH_CALLBACK_SCHEME + "://" + OAUTH_CALLBACK_HOST;
	public static final String	APP_NAME                = "Ready2RideLatest";

}
