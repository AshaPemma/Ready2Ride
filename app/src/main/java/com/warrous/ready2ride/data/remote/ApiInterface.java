package com.warrous.ready2ride.data.remote;


import com.warrous.ready2ride.alerts.createalerts.model.CreateAlertRequest;
import com.warrous.ready2ride.alerts.model.AlertListResponse;
import com.warrous.ready2ride.alerts.model.ServiceTypeResponse;
import com.warrous.ready2ride.auth.FcmRequest;
import com.warrous.ready2ride.auth.login.OwnerIdResponse;
import com.warrous.ready2ride.auth.profile.model.ProfileRequest;
import com.warrous.ready2ride.auth.profile.model.ProfileResponse;
import com.warrous.ready2ride.auth.signup.SignUpRequest;
import com.warrous.ready2ride.auth.signup.SignUpResponse;
import com.warrous.ready2ride.bike.model.DefaultBikeDetailsResponse;
import com.warrous.ready2ride.bike.model.RideList;
import com.warrous.ready2ride.createbike.model.Brand;
import com.warrous.ready2ride.createbike.model.CreateBikeRequest;
import com.warrous.ready2ride.createbike.model.Model;
import com.warrous.ready2ride.createbike.model.Year;
import com.warrous.ready2ride.dealership.model.DealershipResponse;
import com.warrous.ready2ride.dealership.model.EventsPromotionsResponse;
import com.warrous.ready2ride.dealership.model.RequestServiceRequest;
import com.warrous.ready2ride.dealership.model.SavePromotionRequest;
import com.warrous.ready2ride.forgotpassword.ForgotPasswordModel;
import com.warrous.ready2ride.forgotpassword.ForgotpasswordResponse;
import com.warrous.ready2ride.forgotpassword.PasswordModel;
import com.warrous.ready2ride.forgotpassword.UpdatePasswordResponse;
import com.warrous.ready2ride.groups.discussions.model.DiscussionCommentRequest;
import com.warrous.ready2ride.groups.discussions.model.DiscussionCommentsResponse;
import com.warrous.ready2ride.groups.discussions.model.DiscussionsResponse;
import com.warrous.ready2ride.groups.discussions.model.StartDiscussionRequest;
import com.warrous.ready2ride.groups.model.CreateGroupRequest;
import com.warrous.ready2ride.groups.model.LeaveGroupRequest;
import com.warrous.ready2ride.invitebuddies.model.AppUsers;
import com.warrous.ready2ride.invitebuddies.model.BuddiesKnown;
import com.warrous.ready2ride.invitebuddies.model.BuddyListResponse;
import com.warrous.ready2ride.invitebuddies.model.BuddyRequests;
import com.warrous.ready2ride.invitebuddies.model.FeaturedGroupsResponse;
import com.warrous.ready2ride.invitebuddies.model.FeaturedRidersResponse;
import com.warrous.ready2ride.invitebuddies.model.InviteBuddiesRequest;
import com.warrous.ready2ride.navigation.model.MessagesResponse;
import com.warrous.ready2ride.navigation.model.NotificationsResponse;
import com.warrous.ready2ride.network.ApiResponse;
import com.warrous.ready2ride.ridelog.RidePath;
import com.warrous.ready2ride.ridelog.ridedetails.RideLogDetail;
import com.warrous.ready2ride.rides.SaveRideRequest;
import com.warrous.ready2ride.rides.SaveRideresponse;
import com.warrous.ready2ride.setasdealership.SetDefaultDealerRequest;
import com.warrous.ready2ride.tracksearch.Model.ParkingResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET(ApiEndPoint.GET_DEALERSHIP_DETAILS)
    Call<ApiResponse<List<DealershipResponse>>> getLibraryImages(@Query("DealerId") int dealerId, @Query("latitude") double latitude, @Query("longitude") double longitude,@Query("OwnerId") int ownerId);

    @GET(ApiEndPoint.GET_SERVICE_CATEGORIES)
    Call<ApiResponse<List<ServiceTypeResponse>>> getServiceTypes(@Query("serviceCategoryId") int serviceCatId);

    @GET(ApiEndPoint.GET_BIKE_DETAILS)
    Call<ApiResponse<List<DefaultBikeDetailsResponse>>> getBikeDetails(@Query("CycleId") int cycleId,@Query("OwnerId") int ownerId);

    @GET(ApiEndPoint.GET_ALERTS)
    Call<ApiResponse<List<AlertListResponse>>> getAlertList(@Query("OwnerId") int ownerId);


    @GET(ApiEndPoint.GET_GROUP_BY_OWNERID)
    Call<ApiResponse<List<FeaturedGroupsResponse>>> getGroups(@Query("OwnerId") int ownerId);

    @GET(ApiEndPoint.GET_EVENTS)
    Call<ApiResponse<List<EventsPromotionsResponse>>> getEventList(@Query("DealerId") int dealerId);

    @GET(ApiEndPoint.GET_FEATURED_GROUPS)
    Call<ApiResponse<List<FeaturedGroupsResponse>>> getFeaturedGroups(@Query("OwnerId") int ownerId);

    @GET(ApiEndPoint.GET_FEATURED_RIDERS)
    Call<ApiResponse<List<FeaturedRidersResponse>>> getFeaturedRiders(@Query("DealerId") int dealerId);

    @POST(ApiEndPoint.REGISTER_USER)
    @Headers({"Accept: application/json"})
    Call<ApiResponse<List<SignUpResponse>>> signUp(@Body SignUpRequest signUpRequest);

    @POST(ApiEndPoint.CREATE_ALERT)
    @Headers({"Accept: application/json"})
    Call<ApiResponse<List<SignUpResponse>>> createAlert(@Body CreateAlertRequest createAlertRequest);

    @GET(ApiEndPoint.GET_OWNER_ID)
    Call<ApiResponse<List<OwnerIdResponse>>> getOwnerId(@Query("UserId") int userId);

    @GET(ApiEndPoint.GET_BRANDS)
    Call<ApiResponse<List<Brand>>> getBrands(@Query("brandId") int brandId);

    @GET(ApiEndPoint.GET_YEARS)
    Call<ApiResponse<List<Year>>> getYears(@Query("brandId") int brandId);

    @GET(ApiEndPoint.GET_MODELS)
    Call<ApiResponse<List<Model>>> getModels(@Query("brandYearId") int brandYearId);

    @GET(ApiEndPoint.GET_RIDE_LOG)
    Call<ApiResponse<List<RideList>>> getRideLog(@Query("CycleId") int cycleId, @Query("OwnerId") int ownerId);

    @POST(ApiEndPoint.CREATE_BIKE)
    @Headers({"Accept: application/json"})
    Call<ApiResponse<List<SignUpResponse>>> createBike(@Body CreateBikeRequest createBikeRequest);

    @POST(ApiEndPoint.CREATE_GROUP)
    @Headers({"Accept: application/json"})
    Call<ApiResponse<List<SignUpResponse>>> createGroup(@Body CreateGroupRequest createGroupRequest);


    @GET(ApiEndPoint.GET_NOTIFICATIONS)
    Call<ApiResponse<List<NotificationsResponse>>> getNotifications(@Query("OwnerId") int ownerId);

    @GET(ApiEndPoint.GET_MESSAGES)
    Call<ApiResponse<List<MessagesResponse>>> getMessages(@Query("DealerId") int dealerId, @Query("OwnerId") int ownerId);

    @POST(ApiEndPoint.CREATE_APPOINTMENT)
    @Headers({"Accept: application/json"})
    Call<ApiResponse<List<SignUpResponse>>> createAppointment(@Body RequestServiceRequest requestServiceRequest);

    @POST(ApiEndPoint.SEND_FORGOT_EMAIL)
    @Headers({"Accept: application/json"})
    Call<ApiResponse<List<ForgotpasswordResponse>>> getForgotpwdEmail(@Body ForgotPasswordModel forgotPasswordModel);

    @POST(ApiEndPoint.UPDATE_PASSWORD)
    @Headers({"Accept: application/json"})
    Call<ApiResponse<List<UpdatePasswordResponse>>> updatePassword(@Body PasswordModel passwordModel);

    @POST(ApiEndPoint.LEAVE_GROUP)
    @Headers({"Accept: application/json"})
    Call<ApiResponse<List<SignUpResponse>>> leaveGroup(@Body LeaveGroupRequest leaveGroupRequest);

    @GET(ApiEndPoint.GET_PROFILE_DETAILS)
    Call<ApiResponse<List<ProfileResponse>>> getProfileDetails(@Query("OwnerId") int ownerId);

    @POST(ApiEndPoint.CREATE_USER_PROFILE)
    @Headers({"Accept: application/json"})
    Call<ApiResponse<List<SignUpResponse>>> createUserProfile(@Body ProfileRequest profileRequest);

    @POST(ApiEndPoint.SET_AS_DEFAULT_DEALER)
    @Headers({"Accept: application/json"})
    Call<ApiResponse<List<SignUpResponse>>> setasDefaultDealer(@Body SetDefaultDealerRequest setDefaultDealerRequest);

    @GET(ApiEndPoint.GET_SELECTED_DEALERS)
    Call<ApiResponse<List<DealershipResponse>>> getselectedDealerships(@Query("OwnerId") int ownerId);

    @GET(ApiEndPoint.REMOVE_AS_SELECTED_DEALER)
    Call<ApiResponse<List<SignUpResponse>>> removeAsSelectedDealer(@Query("OwnerId") int ownerId,@Query("DealerId") int dealerId);

    @GET(ApiEndPoint.SET_AS_SELECTED_DEALER)
    Call<ApiResponse<List<SignUpResponse>>> setAsSelectedDealer(@Query("OwnerId") int ownerId,@Query("DealerIds") int dealerId);

    @POST(ApiEndPoint.SAVE_RIDE_REQUST)
    @Headers({"Accept: application/json"})
    Call<ApiResponse<List<SaveRideresponse>>> saveRideRequest(@Body SaveRideRequest saveRideRequest);

    @POST(ApiEndPoint.INVITE_BUDDIES)
    @Headers({"Accept: application/json"})
    Call<ApiResponse<List<SignUpResponse>>> inviteBuddies(@Body InviteBuddiesRequest inviteBuddiesRequest);

    @POST(ApiEndPoint.FCM_TOkEN)
    @Headers({"Accept: application/json"})
    Call<ApiResponse<List<SignUpResponse>>> updateFcmToken(@Body FcmRequest fcmRequest);

    @POST(ApiEndPoint.START_NEW_DISCUSSION)
    @Headers({"Accept: application/json"})
    Call<ApiResponse<List<SignUpResponse>>> startNewDiscussion(@Body StartDiscussionRequest startDiscussionRequest);

    @GET(ApiEndPoint.GET_DISCUSSIONS)
    Call<ApiResponse<List<DiscussionsResponse>>> getDiscussions(@Query("GroupId") int groupId);

    @GET(ApiEndPoint.GET_DISCUSSION_COMMENTS)
    Call<ApiResponse<List<DiscussionCommentsResponse>>> getDiscussionComments(@Query("DiscussionId") int discussionId);

    @POST(ApiEndPoint.SAVE_DISCUSSION_COMMENT)
    @Headers({"Accept: application/json"})
    Call<ApiResponse<List<SignUpResponse>>> saveDiscussionComment(@Body DiscussionCommentRequest startDiscussionRequest);

    @GET(ApiEndPoint.GET_SAVED_PROMOTIONS)
    Call<ApiResponse<List<EventsPromotionsResponse>>> getSavedPromotions(@Query("OwnerId") int ownerId);

    @GET(ApiEndPoint.GET_ATTENDING_EVENTS)
    Call<ApiResponse<List<EventsPromotionsResponse>>> getAttendingEvents(@Query("OwnerId") int ownerId);

    @POST(ApiEndPoint.SAVE_PROMOTION)
    @Headers({"Accept: application/json"})
    Call<ApiResponse<List<SignUpResponse>>> savePromotion(@Body SavePromotionRequest savePromotionRequest);

    @GET(ApiEndPoint.GET_OWNER_EVENTS)
    Call<ApiResponse<List<EventsPromotionsResponse>>> getOwnerEvents(@Query("OwnerId") int ownerId, @Query("DealerId") int dealerId);

    @GET(ApiEndPoint.GET_OWNERPROMOTIONS)
    Call<ApiResponse<List<EventsPromotionsResponse>>> getOwnerPromotions(@Query("OwnerId") int ownerId, @Query("DealerId") int dealerId);

    @GET(ApiEndPoint.GET_RIDE_LOG_PATH)
    Call<ApiResponse<List<RideLogDetail>>> getRideLogPath(@Query("RideId") int rideId);

    @GET(ApiEndPoint.GET_RIDE_PATH)
    Call<ApiResponse<List<RidePath>>> getRidePath(@Query("RideId") int rideId);

    @GET(ApiEndPoint.GET_BUDDIES)
    Call<ApiResponse<List<BuddyListResponse>>> getBuddies(@Query("OwnerId") int senderId);

    @GET(ApiEndPoint.GET_APP_USERS)
    Call<ApiResponse<List<AppUsers>>> getAppUsers();

    @GET(ApiEndPoint.GET_BUDDIES_LIST)
    Call<ApiResponse<List<BuddiesKnown>>> getBuddiesList(@Query("OwnerId") int ownerId);

    @POST(ApiEndPoint.SEND_BUDDIE_REQUEST)
    @Headers({"Accept: application/json"})
    Call<ApiResponse<List<SignUpResponse>>> sendBuddyRequest(@Body BuddyRequests buddyRequest);


    @GET(ApiEndPoint.GET_PARKING_DETAILS)
    Call<ApiResponse<List<ParkingResponse>>> getParkingDetails(@Query("Latitude") double latitude, @Query("Longitude") double longitude);



}
