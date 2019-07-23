package com.warrous.ready2ride.constants;


import java.util.Arrays;
import java.util.List;
import com.android.volley.Request;


public class Constants {

    public static final String CONNECTION_ERROR = "Network Error";

    public static final String PLEASE_CHECK_INTERNET = "Please Check Internet";

    public static final String PREFS = "com.maya.vgarage";



    public static final String SOMETHING_WENT_WRONG = "Something Went Wrong";

    public static final String ERROR = "Error";

    public static final String REJECTED = "Rejected";

    public static final String ACCEPTED = "Accepted";

    public static final String INVALID_OPERATION = "Invalid Operation";

    public static final int SECOND_MILLIS = 1000;

    public static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;

    public static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;

    public static final int DAY_MILLIS = 24 * HOUR_MILLIS;

    public static final int POST_REQUEST = Request.Method.POST;

    public static final int GET_REQUEST = Request.Method.GET;

    public static final String FRAGMENT_KEY = "fragment_key";

    public static final int LOCATION_CODE = 10101;

    public static final String COLOR_CODES[] = {"#BA4141", "#E07C5C", "#00B3FF", "#0069C9"};

    public static final String TAG_COLOR_CODES[] = {"#000000", "#8B4513", "#990000", "#0069C9"};

    public static final String COLOR_START_CODES[] = {"#6ACAD3", "#816CD4", "#BEE05C", "#f07C5C", "#E8DF16"};

    public static final String COLOR_END_CODES[] = {"#4791BE", "#BA4198", "#41BA63", "#BA4141", "#F9D006"};

    public static final String PARKING_COLOR_START_CODES[] = {"#00B2FF", "#816CD4", "#BEE05C", "#f07C5C", "#E8DF16"};

    public static final String PARKING_COLOR_END_CODES[] = {"#0069C9", "#BA4198", "#41BA63", "#BA4141", "#F9D006"};

    public static final List<String> FB_PERMISSIONS = Arrays.asList("public_profile", "email");

    public static final String USER_ID = "user_id";

    public static final int SAMPLE_USER_ID = 3;

    public static final String ACCESS_TOKEN = "access_token";

    public static final String EXPIRES_IN = "expires_in";

    public static final String TOKEN_TYPE = "token_type";

    public static final String LAST_NAME = "last_name";

    public static final String FIRST_NAME = "first_name";

    public static final String USER_EMAIL = "user_email";

    public static final String USER_NAME = "user_name";

    public static final String USER_PHONE_NUMBER = "user_phone_number";

    public static final String USER_PHOTO_URL = "user_photo_url";

    public static final String USER_ADDRESS = "user_address";

    public static final String USER_COMPLETE_ADDRESS = "user_complete_address";

    public static final String USER_LOCALITY_ADDRESS = "user_locality_address";

    public static final String USER_ADMIN_AREA = "user_admin_area";

    public static final String USER_PIN_CODE = "user_pin_code";

    public static final String USER_ADDRESS1 = "user_address1";

    public static final String USER_ADDRESS2 = "user_address2";

    public static final String LOGIN = "login";

    public static final String CART_DATA = "cart_data";

    public static final String DEFAULT_CAR_DATA = "DEFAULT_CAR_DATA";

    public static final String VGARAGE_SERVICES = "vgarage_services";

    public static final String USER_FCM_TOKEN = "user_fcm_token";

    public static final String AUTH_TOKEN = "auth_token";

    public static final String CURRENT_USER_FCM_TOKEN = "current_user_fcm_token";

   // public static final int IMAGE_PLACE_HOLDER = R.drawable.place_holder;

    public static final String AVATAR_IMAGE = "https://ssl.gstatic.com/images/branding/product/1x/avatar_circle_blue_512dp.png";

    public static final String SAMPLE_ERROR_IMAGE = "https://i.pinimg.com/originals/3e/3e/14/3e3e14d932098c3c3140c419daaa61c6.jpg";

    public static final String SAMPLE_CUSTOMER_PHONE_NUMBER = "1234567890";

    public static final String SERIAL_PROJECT_ID = "509471007783";

    public static final int SUCCESS_RESULT = 0;

    public static final int FAILURE_RESULT = 1;

    public static final String PACKAGE_NAME = "com.maya.vgarages";

    public static final String RECEIVER = PACKAGE_NAME + ".AddressReceiver";

    public static final String RESULT_DATA_KEY = PACKAGE_NAME + ".RESULT_DATA_KEY";

    public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_DATA_EXTRA";

    public static final int RUPEE_PAISA = 100;

    public static final int OTP_COUNT = 9;

    public static final String URL_APP_LOGO = "http://devassets.warrous.com/Mobile%20Assets/png/app_logo.png";

    public static final String URL = "https://api.warrous.com:8790/api/";
    //main url
    public static final String URL_OTP = " https://api.warrous.com:19081/warrous.ms.auth/warrous.ms.auth.api/api/";
    public static final String AUTH="warrous.ms.auth/";
    public static final String AUTH_API="warrous.ms.auth.api/api/";

    //main url
    public static final String URL_VGARAGE = "https://api.warrous.com:8506/api/";
    //main url



    public static final String URL_LOGIN = "https://portal.ready2ridemobile.com:19081/r2r.ms.auth/r2r.ms.auth.api/connect/token";

    public static final String URL_UPLOAD="https://api.warrous.com//api/assets/MobileImageAssetsUpload";
    //username & password post




}
