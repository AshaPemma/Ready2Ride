package com.warrous.ready2ride.Util;

import com.warrous.ready2ride.comman.Comman;

/**
 * Created by IPS on 26-Sep-17.
 */

public class EndPoints {
      public static String imageUpload                      = Comman.IMAGEURL + "cyclemate/image_uploading.php";
      public static String UPLOAD_URL                       = Comman.IMAGEURL + "ready2ride/image_uploading_android.php";

      public static String InsertUpdateOwner                = Comman.BASE_URL + "api/Owner/InsertUpdateOwner?";
      public static String InsertUpdateCycle                = Comman.BASE_URL + "api/Cycle/InsertUpdateCycle?";
      public static String InsertUpdateCyclePreference      = Comman.BASE_URL + "api/Cycle/InsertUpdateCyclePreference?";
      public static String getValidation_MOBILE_R2R_URL     = Comman.BASE_URL + "api/Owner/GetOwnerByMobile?Mobile=";
      public static String SendBuddyRequest                 = Comman.BASE_URL + "api/Buddy/SendBuddyRequest?";
      public static String MarkIsReadSpecial                = Comman.BASE_URL + "api/Coupon/MarkIsReadSpecial?";
      public static String DeletePromotion                  = Comman.BASE_URL + "api/Coupon/DeletePromotion?";
      public static String InsertMobileID_URL               = Comman.BASE_URL + "api/Owner/InsertMobile?";

      public static String DEVICE_REGISTER                  = Comman.BASE_URL + "api/Owner/FcmByDeviceUpdateInsert?";


      public static String GetVIN                           = Comman.BASE_URL + "api/Cycle/GetVIN?VINNumber=";
      public static String BRANDLIST_URL                    = Comman.BASE_URL + "api/Cycle/GetBrand?BrandId=";
      public static String MODELLIST_URL                    = Comman.BASE_URL + "api/Cycle/GetModelData?brandYearId=";
      public static String YEARLIST_URL                    = Comman.BASE_URL + "api/Cycle/GetYearData?brandId=";
      public static String CATEGORYLIST_URL                 = Comman.BASE_URL + "api/Cycle/GetCategory?CategoryId=";
      public static String RIDETYPELIST_URL                 = Comman.BASE_URL + "api/Cycle/GetRideType?RideTypeId=";
      public static String GetDealer                        = Comman.BASE_URL + "api/Dealer/GetDealer?DealerId=";
      public static String GET_LOGO=Comman.BASE_URL+"api/Dealer/GetDealerEnrollDataId?dealerId=";
    //  https://portal.ready2ridemobile.com:19081/r2r.ms.auth/r2r.ms.auth.api/api/user/GetDealerEnrollDataId?dealerId=4035

      /*for cycle*/
      public static String GetCycle                         = Comman.BASE_URL + "api/Cycle/GetCycle?CycleId=0&OwnerId=";
      public static String GetCycleDefault                  = Comman.BASE_URL + "api/Cycle/GetCycle?CycleId=";
      public static String GET_CYCLEPREFERENCEID_URL        = Comman.BASE_URL + "api/Cycle/GetCyclePreference?CycleId=";
      public static String GET_BANNERCOLOR_URL              = Comman.BASE_URL + "api/Cycle/GetCycleHealthColor?CycleId=";

      public static String GET_PRIMARYBIKEID_URL            = Comman.BASE_URL + "api/Cycle/GetPrimaryBikeByOwner?OwnerId=";
      public static String State_URL                        = Comman.BASE_URL + "api/Owner/GetState?StateId=0";
      public static String City_URL                         = Comman.BASE_URL + "api/Owner/GetCity?StateCode=";
      public static String Getpromotion                     = Comman.BASE_URL + "api/Coupon/Getpromotion?OwnerId=";

      public static String ALLNOTIFICATION_URL              = Comman.BASE_URL + "api/Notification/GetAllNotification?OwnerId=";
      public static String GetParkingList_URL               = Comman.BASE_URL + "api/Parking/GetParking?ParkingId=0&OwnerId=";

      public static String WEATHER_URL                      = "http://api.openweathermap.org/data/2.5/forecast/daily?";
      public static String GetpromotionSpecial              = "api/Coupon/Getpromotion?OwnerId=";
      public static String RedeemCoupon                     = "api/Coupon/RedeemCoupon?CouponId=";

      public static String mg_r2r_update                    = "http://7mg.biglistof.com/7mg_r2r_update.php";
      public static String mg_r2r                           = "http://7mg.biglistof.com/7mg_r2r.php?did=";

      public static String ReadNotification_URL             = Comman.BASE_URL + "api/Ride/MarkIsReadRide?";
      public static String DeleteRide_URL                   = Comman.BASE_URL + "api/Ride/DeleteRide";
      public static String RIDELIST_URL                     = Comman.BASE_URL + "api/Ride/GetRideList?OwnerId=";

      public static String LoadNotePhoto_URL                = Comman.BASE_URL + "api/Ride/GetRideNote?OwnerId=";
      public static String GetRideState_URL                 = Comman.BASE_URL + "api/Ride/GetRideState/OwnerId=";
      public static String CONTACTLIST_URL                  = Comman.BASE_URL + "api/Notification/GetContactListWithGroupName/OwnerId=";
      public static String InsertUpdateRideNotePhoto_URL    = Comman.BASE_URL + "api/Ride/InsertUpdateRideNote?";
      public static String Share_InsertUpdate_URL           = Comman.BASE_URL + "api/Ride/InsertUpdateShareRide?";
      public static String INSERTUPDATERIDE_URL             = Comman.BASE_URL + "api/Ride/InsertUpdateRide?";
      public static String InsertUpdateState_URL            = Comman.BASE_URL + "api/Ride/InsertUpdateRideState?";
      public static String GetActiveRide_URL                = Comman.BASE_URL + "api/Ride/GetActiveRide?OwnerId=";
      public static String Get_DEALER_LOGO                   = Comman.BASE_URL +"api/Dealer/GetDealerEnrollDataId?dealerId=";


}
