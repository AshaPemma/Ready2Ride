package com.warrous.ready2ride.facebookfriendslist.data.service;

import com.warrous.ready2ride.facebookfriendslist.data.model.FriendsListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Sally on 08-Jan-17.
 */

public interface FacebookFriendList {
    @GET("v2.4/{user_id}/taggable_friends")
    Call<FriendsListResponse> getFriendsList(@Path("user_id") String userId,
                                             @Query("access_token") String accessToken,
                                             @Query("limit") int limit,
                                             @Query("after") String afterPage);
}
