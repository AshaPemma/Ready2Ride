package com.warrous.ready2ride.auth.login;

public class GoogleConstants {


    // Use your own client id
    public static String CLIENT_ID = "85862604426-788s4ptbo71tdsh2r7q84lb4mogkkih1.apps.googleusercontent.com";

    // Use your own client secret
    public static String CLIENT_SECRET = "L6EQkhvPQCZrJ6YSwUxD5uvD";

    public static String REDIRECT_URI = "https://console.firebase.google.com";
    public static String GRANT_TYPE = "authorization_code";
    public static String TOKEN_URL = "https://accounts.google.com/o/oauth2/token";
    public static String OAUTH_URL = "https://accounts.google.com/o/oauth2/auth";
    public static String OAUTH_SCOPE = "https://www.googleapis.com/auth/contacts.readonly";

    public static final String CONTACTS_URL = "https://www.google.com/m8/feeds/contacts/ashuasha51@gmail.com/full";
    public static final int MAX_NB_CONTACTS = 50;
    public static final String APP = "Ready2Ride";

}
