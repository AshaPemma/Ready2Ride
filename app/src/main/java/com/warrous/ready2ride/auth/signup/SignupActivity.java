package com.warrous.ready2ride.auth.signup;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.imagepipeline.decoder.DecodeException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gdata.client.Query;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.Link;
import com.google.gdata.data.contacts.ContactEntry;
import com.google.gdata.data.contacts.ContactFeed;
import com.google.gdata.data.contacts.GroupMembershipInfo;
import com.google.gdata.data.extensions.Email;
import com.google.gdata.data.extensions.ExtendedProperty;
import com.google.gdata.data.extensions.Im;
import com.google.gdata.data.extensions.Name;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.warrous.ready2ride.R;
import com.warrous.ready2ride.Util.FaceBookLoginMngr;
import com.warrous.ready2ride.Util.JWTUtils;
import com.warrous.ready2ride.Util.ValidationUtil;
import com.warrous.ready2ride.auth.FcmRequest;
import com.warrous.ready2ride.auth.login.GoogleConstants;
import com.warrous.ready2ride.auth.login.LoginActivity;
import com.warrous.ready2ride.auth.profile.DefaultProfileActivity;
import com.warrous.ready2ride.base.BaseActivity;
import com.warrous.ready2ride.constants.Constants;
import com.warrous.ready2ride.data.volley.volley.VolleyHelperLayer;
import com.warrous.ready2ride.dealership.popup.DataManager;
import com.warrous.ready2ride.framework.ActivityUtils;
import com.warrous.ready2ride.framework.AndroidUtil;
import com.warrous.ready2ride.framework.PreferenceManager;
import com.warrous.ready2ride.framework.Ready2RideLog;
import com.warrous.ready2ride.framework.Utility;
import com.warrous.ready2ride.invitebuddies.model.ContactModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class SignupActivity extends BaseActivity implements SignUpContract.View ,GoogleApiClient.OnConnectionFailedListener {

    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_password)
    EditText etPassWord;
    SignUpContract.Presenter mPrsesenter;
    GoogleApiClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 234;
    FaceBookLoginMngr faceBookLoginMngr;
    FirebaseAuth mAuth;
    String googleToken;
    String googleUserName;
    Ready2RideLog ready2RideLog;
    CallbackManager callbackManager;
    ArrayList<ContactModel> contactModels;
    private int pagingIndex = 1;
    ContactsService myService;
    String tokenAuth;
    String fcmToken;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        injectButterKnife(this);
        mPrsesenter=new SignupPresenter(this);
        mAuth = FirebaseAuth.getInstance();
        ready2RideLog=Ready2RideLog.getInstance();
        faceBookLoginMngr = new FaceBookLoginMngr(this);
        generateHashkey();
        fcmToken=PreferenceManager.getStringValue(this,PreferenceManager.KEY_FCM_TOKEN);
       // Toast.makeText(this,fcmToken,Toast.LENGTH_LONG).show();


        //  generateHashkey();

        //Then we need a GoogleSignInOptions object
        //And we need to build it as below
        String serverClientId = getString(R.string.server_client_id);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestScopes(new Scope(Scopes.DRIVE_APPFOLDER), new Scope(Scopes.PROFILE), new Scope(Scopes.EMAIL), new Scope("https://www.googleapis.com/auth/contacts"))
                .requestServerAuthCode(serverClientId)
                .requestEmail()
                .requestIdToken(serverClientId)
                .build();
        // [END configure_signin]

        // Build GoogleAPIClient with the Google Sign-In API and the above options.
        mGoogleSignInClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();



    }

    public void generateHashkey(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.warrous.ready2ride",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());

                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("Has", e.getMessage(), e);
        } catch (NoSuchAlgorithmException e) {
            Log.d("Has", e.getMessage(), e);
        }
    }
    @OnClick(R.id.btn_facebook)
    public void onClickFacebook(){
        fbLogin();
    }

    public void fbLogin() {

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList(
                "public_profile", "email"));

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Profile profile = Profile.getCurrentProfile();

                        final String accessToken = loginResult.getAccessToken().getToken();
                        String username=loginResult.getAccessToken().getUserId();
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        Log.v("LoginActivity", response.toString());

                                        // Application code
                                        try {
                                            String email = object.getString("email");
                                            doLoginApiFaceBook(accessToken,email);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        //String birthday = object.getString("birthday"); // 01/31/1980 format
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender,birthday");
                        request.setParameters(parameters);
                        request.executeAsync();

                        // presenter.registerFacebookUserWitAvail(accessToken);


                    }


                    @Override
                    public void onCancel() {
                        ready2RideLog.info("Login attempt cancelled.");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        error.printStackTrace();
                        ready2RideLog.info("Login attempt failed.");
                        //  Toast.makeText(LoginActivity.this, "Something went wrong with facebook login, please try later", Toast.LENGTH_SHORT).show();
//                        deleteAccessToken();
                        AndroidUtil.showSnackBarSafe(SignupActivity.this,"Something went wrong with facebook login, please try later");
                    }


                }
        );

    }

    private void doLoginApiFaceBook(String token,String name)
    {

        final ProgressDialog progressDialog = Utility.generateProgressDialog(this);
        String URL = Constants.URL_LOGIN;


        Map<String,String> input = new HashMap<String,String>();
        input.put("username",name);
        input.put("password","nopassword");
        input.put("auth_type","facebook");
        input.put("social_token",token);
        //&grant_type=password&scope=api1+offline_access&client_id=ro.client&client_secret=secret&org_name=Kia&user_type_name=Dealer
        input.put("grant_type","password");
        input.put("scope","api1 offline_access");
        input.put("client_id","ro.client");
        input.put("client_secret","secret");
        input.put("org_name","generic");
        input.put("user_type_name","Owner");



        VolleyHelperLayer volleyHelperLayer = new VolleyHelperLayer();
        Response.Listener<String> listener = new Response.Listener<String>() {


            @Override
            public void onResponse(String response)
            {
                Log.d("[response]",response);

                try
                {

                    JSONObject jsonObject = new JSONObject(response);

                    if(jsonObject.has(Constants.ACCESS_TOKEN))
                    {

//                        Utility.setString(Utility.getSharedPreferences(),Constants.ACCESS_TOKEN,jsonObject.getString(Constants.ACCESS_TOKEN));
//                        Utility.setString(Utility.getSharedPreferences(),Constants.EXPIRES_IN,jsonObject.getString(Constants.EXPIRES_IN));
//                        Utility.setString(Utility.getSharedPreferences(),Constants.TOKEN_TYPE,jsonObject.getString(Constants.TOKEN_TYPE));

                        try
                        {
//                            JWT jwt = new JWT(jsonObject.getString(Constants.ACCESS_TOKEN));
//                            firstName =  jwt.getClaim("FirstName").asString();
//                            lastName  =  jwt.getClaim("LastName").asString();
//                            userName  =  jwt.getClaim("UserName").asString();
//                            userId  =  jwt.getClaim("UserId").asString();
//                            email  =  jwt.getClaim("Email").asString();
//                            phone  =  jwt.getClaim("PhoneNumber").asString();
//
//
//                            Utility.setString(Utility.getSharedPreferences(),Constants.FIRST_NAME,firstName);
//                            Utility.setString(Utility.getSharedPreferences(),Constants.LAST_NAME,lastName);
//                            Utility.setString(Utility.getSharedPreferences(),Constants.USER_NAME,firstName + " " + lastName);
//                            Utility.setString(Utility.getSharedPreferences(),Constants.USER_EMAIL,email);
//                            Utility.setString(Utility.getSharedPreferences(),Constants.USER_ID,userId);
//                            Utility.setString(Utility.getSharedPreferences(),Constants.USER_PHONE_NUMBER,phone);
//                            Utility.setString(Utility.getSharedPreferences(),Constants.USER_PHOTO_URL,Constants.AVATAR_IMAGE);
//                            Utility.setBoolen(Utility.getSharedPreferences(),Constants.LOGIN,true);


                            Utility.closeProgressDialog(progressDialog);
                            PreferenceManager.storeValue(SignupActivity.this, PreferenceManager.KEY_TOKEN, jsonObject.getString(Constants.ACCESS_TOKEN));
                            String userData= JWTUtils.decoded(jsonObject.getString(Constants.ACCESS_TOKEN));
                            JSONObject jObject  = new JSONObject(userData); // json

//                            String dealerID = jObject.getString("dealerid");
//                            // get the name from data.
                            String userName=jObject.getString("UserName");
//                            String fName=jObject.getString("FirstName");
//                            String lName=jObject.getString("LastName");
//                            String orgId=jObject.getString("orgid");
                            String userId=jObject.getString("userId");
//                            Log.e("DEALERID",dealerID);
//                           PreferenceManager.storeValue(LoginActivity.this, PreferenceManager.KEY_DEALER_ID, dealerID);
                            PreferenceManager.storeValue(SignupActivity.this, PreferenceManager.KEY_USERNAME, userName);
//                            PreferenceManager.storeValue(LoginActivity.this, PreferenceManager.KEY_FNAME, fName);
//                            PreferenceManager.storeValue(LoginActivity.this, PreferenceManager.KEY_LNAME, lName);
//                            PreferenceManager.storeValue(LoginActivity.this, PreferenceManager.KEY_ORG_ID, orgId);
                            PreferenceManager.storeValue(SignupActivity.this,PreferenceManager.KEY_USER_ID,userId);
                            PreferenceManager.storeValue(SignupActivity.this,PreferenceManager.KEY_DEALER_ID,Integer.parseInt(userId));

                            mPrsesenter.getOwnerId(Integer.parseInt(userId));
                            // ActivityUtils.startActivity(LoginActivity.this,HomeActivity.class,null);
                            // ((SplashActivity) activity()).attachNextFragment();
                        }
                        catch (DecodeException exception)
                        {
                            //Invalid token

                        }


                    }
                    else
                    {
                        AndroidUtil.showSnackBarSafe(SignupActivity.this,"Invalid username or password");
                        // Toast.makeText(LoginActivity.this,"Invalid username or password",Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception e)
                {
                    //etUserName.setText("");
                    //etPassword.setText("");
                    AndroidUtil.showSnackBarSafe(SignupActivity.this,"Invalid username or password");
                    // Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }


                Utility.closeProgressDialog(progressDialog);


            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("[response]",Constants.CONNECTION_ERROR);
                Utility.closeProgressDialog(progressDialog);
                //  Toast.makeText(LoginActivity.this, Constants.CONNECTION_ERROR, Toast.LENGTH_SHORT).show();
                AndroidUtil.showSnackBarSafe(SignupActivity.this,Constants.CONNECTION_ERROR);
            }
        };
        volleyHelperLayer.startHandlerVolley(URL,input,listener,errorListener, Request.Priority.NORMAL,Constants.POST_REQUEST);

    }
    private void signInWithGoogle() {
        //getting the google signin intent
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleSignInClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @OnClick(R.id.btn_google)
    public void onCLickGoogleLogin(){
        signInWithGoogle();
    }

    @OnClick(R.id.tv_login)
    public void onClickLogin(){
        ActivityUtils.startActivity(this,LoginActivity.class,null);
    }

    @OnClick(R.id.btn_sign_up)
    public void onClickSoioignup(){

        String email=etEmail.getText().toString();
        String pasword=etPassWord.getText().toString();

        if(TextUtils.isEmpty(email)){
            AndroidUtil.showSnackBarSafe(etEmail,"Please fill all fields");
            return;
        }
        if(!ValidationUtil.isValidEmail(email)){
            AndroidUtil.showSnackBarSafe(etEmail,"Please provide valid email address");
            return;
        }
        if(TextUtils.isEmpty(pasword)){
            AndroidUtil.showSnackBarSafe(etEmail,"Please fill all fields");
            return;
        }

        SignUpRequest signUpRequest=new SignUpRequest();
        signUpRequest.setEmail(etEmail.getText().toString());
        signUpRequest.setPassword(etPassWord.getText().toString());
        signUpRequest.setFirstName("");
        signUpRequest.setLastName("");
        signUpRequest.setPhoneNumber("");
        signUpRequest.setUserName(etEmail.getText().toString());
      mPrsesenter.onSaveClick(signUpRequest);


    }

    private void doLoginApiVerify(String userName,String password)
    {
        try{



        final ProgressDialog progressDialog = Utility.generateProgressDialog(this);
        String URL = Constants.URL_LOGIN;


        Map<String,String> input = new HashMap<String,String>();
        input.put("username",userName);
        input.put("password",password);
        input.put("auth_type","auth");
        input.put("social_token","social_token");
        //&grant_type=password&scope=api1+offline_access&client_id=ro.client&client_secret=secret&org_name=Kia&user_type_name=Dealer
        input.put("grant_type","password");
        input.put("scope","api1 offline_access");
        input.put("client_id","ro.client");
        input.put("client_secret","secret");
        input.put("org_name","powersports");
        input.put("user_type_name","Owner");



        VolleyHelperLayer volleyHelperLayer = new VolleyHelperLayer();
        Response.Listener<String> listener = new Response.Listener<String>() {


            @Override
            public void onResponse(String response)
            {
                Log.d("[response]",response);

                try
                {

                    JSONObject jsonObject = new JSONObject(response);

                    if(jsonObject.has(Constants.ACCESS_TOKEN))
                    {

//                        Utility.setString(Utility.getSharedPreferences(),Constants.ACCESS_TOKEN,jsonObject.getString(Constants.ACCESS_TOKEN));
//                        Utility.setString(Utility.getSharedPreferences(),Constants.EXPIRES_IN,jsonObject.getString(Constants.EXPIRES_IN));
//                        Utility.setString(Utility.getSharedPreferences(),Constants.TOKEN_TYPE,jsonObject.getString(Constants.TOKEN_TYPE));

                        try
                        {
//                            JWT jwt = new JWT(jsonObject.getString(Constants.ACCESS_TOKEN));
//                            firstName =  jwt.getClaim("FirstName").asString();
//                            lastName  =  jwt.getClaim("LastName").asString();
//                            userName  =  jwt.getClaim("UserName").asString();
//                            userId  =  jwt.getClaim("UserId").asString();
//                            email  =  jwt.getClaim("Email").asString();
//                            phone  =  jwt.getClaim("PhoneNumber").asString();
//
//
//                            Utility.setString(Utility.getSharedPreferences(),Constants.FIRST_NAME,firstName);
//                            Utility.setString(Utility.getSharedPreferences(),Constants.LAST_NAME,lastName);
//                            Utility.setString(Utility.getSharedPreferences(),Constants.USER_NAME,firstName + " " + lastName);
//                            Utility.setString(Utility.getSharedPreferences(),Constants.USER_EMAIL,email);
//                            Utility.setString(Utility.getSharedPreferences(),Constants.USER_ID,userId);
//                            Utility.setString(Utility.getSharedPreferences(),Constants.USER_PHONE_NUMBER,phone);
//                            Utility.setString(Utility.getSharedPreferences(),Constants.USER_PHOTO_URL,Constants.AVATAR_IMAGE);
//                            Utility.setBoolen(Utility.getSharedPreferences(),Constants.LOGIN,true);


                            Utility.closeProgressDialog(progressDialog);

                            PreferenceManager.storeValue(SignupActivity.this, PreferenceManager.KEY_TOKEN, jsonObject.getString(Constants.ACCESS_TOKEN));
                            String userData= JWTUtils.decoded(jsonObject.getString(Constants.ACCESS_TOKEN));
                            JSONObject jObject  = new JSONObject(userData); // json

                            String dealerID = jObject.getString("dealerid");
//                            // get the name from data.
                            String userName=jObject.getString("UserName");
//                            String fName=jObject.getString("FirstName");
//                           String lName=jObject.getString("LastName");
////                            String orgId=jObject.getString("orgid");
                            String userId=jObject.getString("userId");
//                            Log.e("DEALERID",dealerID);
//                            PreferenceManager.storeValue(LoginActivity.this, PreferenceManager.KEY_DEALER_ID, dealerID);
                            PreferenceManager.storeValue(SignupActivity.this, PreferenceManager.KEY_USERNAME, userName);
//                            PreferenceManager.storeValue(LoginActivity.this, PreferenceManager.KEY_FNAME, fName);
//                            PreferenceManager.storeValue(LoginActivity.this, PreferenceManager.KEY_LNAME, lName);
//                            PreferenceManager.storeValue(LoginActivity.this, PreferenceManager.KEY_ORG_ID, orgId);
                            PreferenceManager.storeValue(SignupActivity.this, PreferenceManager.KEY_USER_ID, userId);
                            PreferenceManager.storeValue(SignupActivity.this,PreferenceManager.KEY_DEALER_ID,Integer.parseInt(userId));



                            mPrsesenter.getOwnerId(Integer.parseInt(userId));
                            // ActivityUtils.startActivity(LoginActivity.this,HomeActivity.class,null);
                            // ((SplashActivity) activity()).attachNextFragment();
                        }
                        catch (DecodeException exception)
                        {
                            //Invalid token

                        }


                    }
                    else
                    {
                        AndroidUtil.showSnackBarSafe(SignupActivity.this,"Invalid username or password");
                        //Toast.makeText(SignupActivity.this,"Invalid username or password",Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception e)
                {
                    //etUserName.setText("");
                    //etPassword.setText("");
                    AndroidUtil.showSnackBarSafe(SignupActivity.this,"Invalid username or password");
                   // Toast.makeText(SignupActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }


                Utility.closeProgressDialog(progressDialog);


            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

                Log.d("[response]",Constants.CONNECTION_ERROR);
                Utility.closeProgressDialog(progressDialog);
                AndroidUtil.showSnackBarSafe(SignupActivity.this,"Invalid username or password");
              //  Toast.makeText(SignupActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
        };
        volleyHelperLayer.startHandlerVolley(URL,input,listener,errorListener, Request.Priority.NORMAL,Constants.POST_REQUEST);
        }catch (Exception e){

        }
    }
    @Override
    public void onSaveSuccess(String userName, String password) {
        doLoginApiVerify(userName,password);
       // ActivityUtils.startActivity(this,DefaultProfileActivity.class,null);

    }

    @Override
    public void onSucess(int ownerId) {
        PreferenceManager.storeValue(this,PreferenceManager.KEY_OWNER_ID,ownerId);

        FcmRequest fcmRequest=new FcmRequest();
        fcmRequest.setUserId(PreferenceManager.getIntegerValue(this,PreferenceManager.KEY_OWNER_ID));
        fcmRequest.setFcmToken(fcmToken);
        fcmRequest.setId(0);
        fcmRequest.setPlatform("A");

        mPrsesenter.updateFcm(fcmRequest);
        ActivityUtils.startActivity(this,DefaultProfileActivity.class,null);
    }

    @Override
    public void userAlreadyExists() {
       // Toast.makeText(this,"User already exists",Toast.LENGTH_LONG).show();
        AndroidUtil.showSnackBarSafe(SignupActivity.this,"User already exists");

    }

    @Override
    public void onWrongPwd() {
        AndroidUtil.showSnackBarSafe(SignupActivity.this,"Passwords must have at least one non alphanumeric character, one lowercase ('a'-'z'),one uppercase ('A'-'Z'), one special character, min 8 characters");
    }

    @Override
    public void onFcmUpdateSucess(ArrayList<SignUpResponse> signUpResponses) {
        AndroidUtil.showSnackBarSafe(this,"Fcm Token updated");
    }

    private void doLoginApiGoogle(String token,String name)
    {

        final ProgressDialog progressDialog = Utility.generateProgressDialog(this);
        String URL = Constants.URL_LOGIN;


        Map<String,String> input = new HashMap<String,String>();
        input.put("username",name);
        input.put("password","nopassword");
        input.put("auth_type","Google");
        input.put("social_token",token);
        //&grant_type=password&scope=api1+offline_access&client_id=ro.client&client_secret=secret&org_name=Kia&user_type_name=Dealer
        input.put("grant_type","password");
        input.put("scope","api1 offline_access");
        input.put("client_id","ro.client");
        input.put("client_secret","secret");
        input.put("org_name","generic");
        input.put("user_type_name","Owner");



        VolleyHelperLayer volleyHelperLayer = new VolleyHelperLayer();
        Response.Listener<String> listener = new Response.Listener<String>() {


            @Override
            public void onResponse(String response)
            {
                Log.d("[response]",response);

                try
                {

                    JSONObject jsonObject = new JSONObject(response);

                    if(jsonObject.has(Constants.ACCESS_TOKEN))
                    {

//                        Utility.setString(Utility.getSharedPreferences(),Constants.ACCESS_TOKEN,jsonObject.getString(Constants.ACCESS_TOKEN));
//                        Utility.setString(Utility.getSharedPreferences(),Constants.EXPIRES_IN,jsonObject.getString(Constants.EXPIRES_IN));
//                        Utility.setString(Utility.getSharedPreferences(),Constants.TOKEN_TYPE,jsonObject.getString(Constants.TOKEN_TYPE));

                        try
                        {
//                            JWT jwt = new JWT(jsonObject.getString(Constants.ACCESS_TOKEN));
//                            firstName =  jwt.getClaim("FirstName").asString();
//                            lastName  =  jwt.getClaim("LastName").asString();
//                            userName  =  jwt.getClaim("UserName").asString();
//                            userId  =  jwt.getClaim("UserId").asString();
//                            email  =  jwt.getClaim("Email").asString();
//                            phone  =  jwt.getClaim("PhoneNumber").asString();
//
//
//                            Utility.setString(Utility.getSharedPreferences(),Constants.FIRST_NAME,firstName);
//                            Utility.setString(Utility.getSharedPreferences(),Constants.LAST_NAME,lastName);
//                            Utility.setString(Utility.getSharedPreferences(),Constants.USER_NAME,firstName + " " + lastName);
//                            Utility.setString(Utility.getSharedPreferences(),Constants.USER_EMAIL,email);
//                            Utility.setString(Utility.getSharedPreferences(),Constants.USER_ID,userId);
//                            Utility.setString(Utility.getSharedPreferences(),Constants.USER_PHONE_NUMBER,phone);
//                            Utility.setString(Utility.getSharedPreferences(),Constants.USER_PHOTO_URL,Constants.AVATAR_IMAGE);
//                            Utility.setBoolen(Utility.getSharedPreferences(),Constants.LOGIN,true);


                            Utility.closeProgressDialog(progressDialog);
                            PreferenceManager.storeValue(SignupActivity.this, PreferenceManager.KEY_TOKEN, jsonObject.getString(Constants.ACCESS_TOKEN));
                            String userData= JWTUtils.decoded(jsonObject.getString(Constants.ACCESS_TOKEN));
                            JSONObject jObject  = new JSONObject(userData); // json

//                            String dealerID = jObject.getString("dealerid");
//                            // get the name from data.
                            String userName=jObject.getString("UserName");
//                            String fName=jObject.getString("FirstName");
//                            String lName=jObject.getString("LastName");
//                            String orgId=jObject.getString("orgid");
                            String userId=jObject.getString("userId");
                            PreferenceManager.storeValue(SignupActivity.this,PreferenceManager.KEY_DEALER_ID,Integer.parseInt(userId));
//                            Log.e("DEALERID",dealerID);
//                            PreferenceManager.storeValue(LoginActivity.this, PreferenceManager.KEY_DEALER_ID, dealerID);
                            PreferenceManager.storeValue(SignupActivity.this, PreferenceManager.KEY_USERNAME, userName);
//                            PreferenceManager.storeValue(LoginActivity.this, PreferenceManager.KEY_FNAME, fName);
//                            PreferenceManager.storeValue(LoginActivity.this, PreferenceManager.KEY_LNAME, lName);
//                            PreferenceManager.storeValue(LoginActivity.this, PreferenceManager.KEY_ORG_ID, orgId);
                            PreferenceManager.storeValue(SignupActivity.this,PreferenceManager.KEY_USER_ID,userId);
                            mPrsesenter.getOwnerId(Integer.parseInt(userId));
                            //  ActivityUtils.startActivity(LoginActivity.this,HomeActivity.class,null);
                            // ((SplashActivity) activity()).attachNextFragment();
                        }
                        catch (DecodeException exception)
                        {
                            //Invalid token

                        }


                    }
                    else
                    {
                        AndroidUtil.showSnackBarSafe(SignupActivity.this,"Invalid username or password");
                        //  Toast.makeText(LoginActivity.this,"Invalid username or password",Toast.LENGTH_LONG).show();
                    }
                }
                catch (Exception e)
                {
                    //etUserName.setText("");
                    //etPassword.setText("");
                    AndroidUtil.showSnackBarSafe(SignupActivity.this,"Invalid username or password");
                    // Toast.makeText(LoginActivity.this, "Invalid username or password", Toast.LENGTH_SHORT).show();
                }


                Utility.closeProgressDialog(progressDialog);


            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("[response]",Constants.CONNECTION_ERROR);
                Utility.closeProgressDialog(progressDialog);
                Toast.makeText(SignupActivity.this, Constants.CONNECTION_ERROR, Toast.LENGTH_SHORT).show();
                AndroidUtil.showSnackBarSafe(SignupActivity.this,Constants.CONNECTION_ERROR);
            }
        };
        volleyHelperLayer.startHandlerVolley(URL,input,listener,errorListener, Request.Priority.NORMAL,Constants.POST_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Ready2RideLog.getInstance().info(requestCode + data.toString());
        if (requestCode == RC_SIGN_IN) {

            //Getting the GoogleSignIn Task
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
//            faceBookLoginMngr.onActivityResult(requestCode, resultCode, data);
            // LISessionManager.getInstance(getApplicationContext()).onActivityResult(this, requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            //  googleToken=account.getIdToken();
            googleUserName=account.getEmail();
            // googleToken = account.getServerAuthCode();
            getAcessToken(account.getServerAuthCode());
            // doLoginApiGoogle(googleToken,googleUserName);
            // Signed in successfully, show authenticated UI.
            //  updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Gmail", "signInResult:failed code=" + e.getStatusCode());
            //  updateUI(null);
        }
    }

    public void getAcessToken(String code){
        OkHttpClient client = new OkHttpClient();
        com.squareup.okhttp.RequestBody requestBody = new FormEncodingBuilder()
                .add("grant_type", "authorization_code")
                .add("client_id", "821839882293-dfci0s7seta92nfdtelg2bd85ba8tt4k.apps.googleusercontent.com")
                .add("client_secret", "bc5aLSWMLAuqsAGfoHBCrK1c")
                .add("redirect_uri","")
                .add("code", code)
                .build();
        final com.squareup.okhttp.Request request = new com.squareup.okhttp.Request.Builder()
                .url("https://www.googleapis.com/oauth2/v4/token")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final com.squareup.okhttp.Request request, final IOException e) {
                Log.e("Fail", e.toString());
            }

            @Override
            public void onResponse(com.squareup.okhttp.Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    String message = jsonObject.getString("id_token");
                    String token=jsonObject.getString("access_token");
                    tokenAuth=token;
                    googleToken=message;
                    // printAllContacts(googleUserName,token);
                    new GetGoogleContacts(getContext()).execute(tokenAuth);

                    doLoginApiGoogle(googleToken,googleUserName);
                    //  Log.i("Token", message);
//                    Toast.makeText(LoginActivity.this,message,Toast.LENGTH_LONG).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
// catch (ServiceException e) {
//                    e.printStackTrace();
//                }
            }
        });
    }

    private class GetGoogleContacts extends AsyncTask<String, String, List<ContactEntry>> {
        private Context context;

        public GetGoogleContacts(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected List<ContactEntry> doInBackground(String... args) {
            String accessToken = args[0];
            ContactsService contactsService = new ContactsService(GoogleConstants.APP);
            contactsService.setHeader("Authorization", "Bearer " + accessToken);
            contactsService.setHeader("GData-Version", "3.0");
            List<ContactEntry> contactEntries = null;
            try {
                //  URL feedUrl = new URL(GoogleConstants.CONTACTS_URL);
                URL feedUrl = new URL("https://www.google.com/m8/feeds/contacts/"+googleUserName+"/full?access_token="+tokenAuth);
                Query myQuery = new Query(feedUrl);
                myQuery.setStartIndex(pagingIndex);
                myQuery.setMaxResults(GoogleConstants.MAX_NB_CONTACTS);

                ContactFeed resultFeed = contactsService.getFeed(myQuery, ContactFeed.class);
                contactEntries = resultFeed.getEntries();
            } catch (Exception e) {
                //  pDialog.dismiss();
                //Toast.makeText(context, "Failed to get Contacts", Toast.LENGTH_SHORT).show();
            }
            return contactEntries;
        }

        @Override
        protected void onPostExecute(List<ContactEntry> googleContacts) {
            if (null != googleContacts && googleContacts.size() > 0) {
                //List<Contact> contacts = new ArrayList<Contact>();
                Log.e("COntacts","googleContacts:"+googleContacts.size());

                contactModels=new ArrayList<>();


                for (ContactEntry entry : googleContacts) {
                    ContactModel contactModel=new ContactModel();

                    if (entry.hasName()) {

                        Name name = entry.getName();
                        if (name.hasFullName()) {
                            String fullNameToDisplay = name.getFullName().getValue();
                            contactModel.setName(fullNameToDisplay);
                            if (name.getFullName().hasYomi()) {
                                fullNameToDisplay += " (" + name.getFullName().getYomi() + ")";
                            }
                            System.out.println("\t\t" + fullNameToDisplay);
                        } else {
                            System.out.println("\t\t (no full name found)");
                        }
                        if (name.hasNamePrefix()) {
                            System.out.println("\t\t" + name.getNamePrefix().getValue());
                        } else {
                            System.out.println("\t\t (no name prefix found)");
                        }
                        if (name.hasGivenName()) {
                            String givenNameToDisplay = name.getGivenName().getValue();
                            if (name.getGivenName().hasYomi()) {
                                givenNameToDisplay += " (" + name.getGivenName().getYomi() + ")";
                            }
                            System.out.println("\t\t" + givenNameToDisplay);
                        } else {
                            System.out.println("\t\t (no given name found)");
                        }
//                        if (name.hasAdditionalName()) {
//                            String additionalNameToDisplay = name.getAdditionalName().getValue();
//                            if (name.getAdditionalName().hasYomi()) {
//                                additionalNameToDisplay += " (" + name.getAdditionalName().getYomi() + ")";
//                            }
//                            System.out.println("\t\t" + additionalNameToDisplay);
//                        } else {
//                            System.out.println("\t\t (no additional name found)");
//                        }
                        // if (name.hasFamilyName()) {
//                            String familyNameToDisplay = name.getFamilyName().getValue();
//                            if (name.getFamilyName().hasYomi()) {
//                                familyNameToDisplay += " (" + name.getFamilyName().getYomi() + ")";
//                            }
//                            System.out.println("\t\t" + familyNameToDisplay);
//                        } else {
//                            System.out.println("\t\t (no family name found)");
//                        }
//                        if (name.hasNameSuffix()) {
////                            System.out.println("\t\t" + name.getNameSuffix().getValue());
////                        } else {
////                            System.out.println("\t\t (no name suffix found)");
////                        }
                    } else {
                        System.out.println("\t (no name found)");
                    }
                    System.out.println("Email addresses:");
                    for (Email email : entry.getEmailAddresses()) {
                        System.out.print(" " + email.getAddress());

                        if (email.getRel() != null) {
                            System.out.print(" rel:" + email.getRel());
                        }
                        if (email.getLabel() != null) {
                            System.out.print(" label:" + email.getLabel());
                        }
                        if (email.getPrimary()) {
                            System.out.print(" (primary) ");
                        }
                        System.out.print("\n");
                    }
                    System.out.println("IM addresses:");
                    for (Im im : entry.getImAddresses()) {
                        System.out.print(" " + im.getAddress());
                        if (im.getLabel() != null) {
                            System.out.print(" label:" + im.getLabel());
                        }
                        if (im.getRel() != null) {
                            System.out.print(" rel:" + im.getRel());
                        }
                        if (im.getProtocol() != null) {
                            System.out.print(" protocol:" + im.getProtocol());
                        }
                        if (im.getPrimary()) {
                            System.out.print(" (primary) ");
                        }
                        System.out.print("\n");
                    }
                    System.out.println("Groups:");
                    for (GroupMembershipInfo group : entry.getGroupMembershipInfos()) {
                        String groupHref = group.getHref();
                        System.out.println("  Id: " + groupHref);
                    }
                    System.out.println("Extended Properties:");
                    for (ExtendedProperty property : entry.getExtendedProperties()) {
                        if (property.getValue() != null) {
                            System.out.println("  " + property.getName() + "(value) = " +
                                    property.getValue());
                        } else if (property.getXmlBlob() != null) {
                            System.out.println("  " + property.getName() + "(xmlBlob)= " +
                                    property.getXmlBlob().getBlob());
                        }
                    }
                    Link photoLink = entry.getContactPhotoLink();
                    String photoLinkHref = photoLink.getHref();
                    System.out.println("Photo Link: " + photoLinkHref);
                    if (photoLink.getEtag() != null) {
                        System.out.println("Contact Photo's ETag: " + photoLink.getEtag());
                    }
                    System.out.println("Contact's ETag: " + entry.getEtag());
                    contactModels.add(contactModel);
                }


            } else {
                Log.e("NoCOntact", "No Contact Found.");
                Toast.makeText(context, "No Contact Found.", Toast.LENGTH_SHORT).show();
            }

            DataManager.getInstance().setContactModels(contactModels);
        }
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
