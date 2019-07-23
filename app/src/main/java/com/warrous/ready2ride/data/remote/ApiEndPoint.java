package com.warrous.ready2ride.data.remote;


import android.os.StatFs;

public interface ApiEndPoint {


    String AUTH="r2r.ms.mobile/r2r.ms.mobile.api/";
    String GET_DEALERSHIP_DETAILS=AUTH+"api/Dealer/GetDealerDetails";
    String GET_SERVICE_CATEGORIES=AUTH+"api/Cycle/GetServiceCategories";
    String GET_BIKE_DETAILS=AUTH+"api/Cycle/GetCycleDetails";
    String GET_ALERTS=AUTH+"api/Owner/GetAlerts";
    String GET_EVENTS=AUTH+"api/Events/GetAllEvents";
    String GET_FEATURED_GROUPS=AUTH+"api/Buddy/GetGroupByOwnerId";
    String GET_FEATURED_RIDERS=AUTH+"api/Owner/GetRiders";
    String REGISTER_USER="r2r.ms.auth/r2r.ms.auth.api/api/User/RegisterMobileUser";
    String CREATE_ALERT=AUTH+"api/Owner/SaveAlert";
    String GET_OWNER_ID="r2r.ms.auth/r2r.ms.auth.api/api/User/GetOwnerId";
    String GET_BRANDS=AUTH+"api/Cycle/GetBrands";
    String GET_YEARS=AUTH+"api/Cycle/GetYearsData";
    String GET_MODELS=AUTH+"api/Cycle/GetModelsData";
    String CREATE_BIKE=AUTH+"api/Cycle/SaveCycleDetails";
    String CREATE_GROUP=AUTH+"api/Buddy/SaveGroup";
   String GET_NOTIFICATIONS=AUTH+"api/Notification/GetNotifications";
   String CREATE_APPOINTMENT=AUTH+"api/Cycle/BookAppoinment";
   String SEND_FORGOT_EMAIL="r2r.ms.auth/r2r.ms.auth.api/api/Mobile/SendForgotPasswordEmail";
   String UPDATE_PASSWORD="r2r.ms.auth/r2r.ms.auth.api/api/Mobile/UpdatePassword";
   String GET_GROUP_BY_OWNERID=AUTH+"api/Buddy/GetGroupByOwnerId";
   String LEAVE_GROUP=AUTH+"api/Buddy/LeaveGroup";
   String GET_PROFILE_DETAILS=AUTH+"api/Owner/OwnerProfileDetails";
   String CREATE_USER_PROFILE=AUTH+"api/Owner/UpdateOwnerData";

   String SET_AS_DEFAULT_DEALER=AUTH+"api/Dealer/SetDefaultDealer";
   String GET_SELECTED_DEALERS=AUTH+"api/Owner/GetSelectedDealers";
   String REMOVE_AS_SELECTED_DEALER=AUTH+"api/Owner/DeleteSelectedDealer";
   String SET_AS_SELECTED_DEALER=AUTH+"api/Owner/SaveSelectedDealers";
   String SAVE_RIDE_REQUST=AUTH+"api/Ride/InsertUpdateRideDetails";
   String GET_RIDE_LOG=AUTH+"api/Cycle/GetCycleRides";
   String GET_MESSAGES=AUTH+"api/Owner/GetMessage";
   String INVITE_BUDDIES=AUTH+"api/Buddy/InviteBuddies";
   String FCM_TOkEN=AUTH+"api/Notification/InsertUpdateFCMbyUser";
   String START_NEW_DISCUSSION=AUTH+"api/Owner/SaveGroupDiscussion";
   String GET_DISCUSSIONS=AUTH+"api/Owner/GetGroupDiscussion";
   String GET_DISCUSSION_COMMENTS=AUTH+"api/Owner/GetDiscussionComments";
   String SAVE_DISCUSSION_COMMENT=AUTH+"api/Owner/SaveDiscussionComments";
   String GET_SAVED_PROMOTIONS=AUTH+"api/Events/GetSavedPromotions";
   String GET_ATTENDING_EVENTS=AUTH+"api/Events/GetAttendingEvents";
   String SAVE_PROMOTION=AUTH+"api/Notification/PerformNotificationAction";
   String GET_OWNER_EVENTS=AUTH+"api/Events/GetOwnerEvents";
   String GET_OWNERPROMOTIONS=AUTH+"api/Events/GetOwnerPromotions";
   String GET_RIDE_LOG_PATH=AUTH+"api/Ride/GetRideLog";
   String GET_RIDE_PATH=AUTH+"api/Ride/GetRideLatLngLog";
   String GET_BUDDIES=AUTH+"api/Buddy/GetBuddiesByOwnerId";
   String GET_APP_USERS=AUTH+"api/User/GetAppUsers";
   String GET_BUDDIES_LIST=AUTH+"api/Buddy/GetBuddiesYouMayKnow";
   String SEND_BUDDIE_REQUEST=AUTH+"api/Buddy/SendBuddyRequestToBuddy";
   String GET_PARKING_DETAILS=AUTH+"api/Parking/GetParkingDetails";












}
