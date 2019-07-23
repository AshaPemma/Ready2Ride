package com.warrous.ready2ride.facebookfriendslist.usecase;


import com.warrous.ready2ride.facebookfriendslist.data.ApiService;
import com.warrous.ready2ride.facebookfriendslist.data.model.FriendsListResponse;
import com.warrous.ready2ride.facebookfriendslist.data.service.FacebookFriendList;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Sally on 08-Jan-17.
 */

public class GetFriendsList {

   /* public void getTaggableFriends(AccessToken fbToken, GraphRequest.Callback graphRequestCallback) {
        //fbToken return from login with facebook
        GraphRequestAsyncTask r = GraphRequest.newGraphPathRequest(fbToken,
                "/me/taggable_friends", graphRequestCallback
        ).executeAsync();
    }*/

    public void getFBFriendsList(String userId, String accessToken, int limit, String afterPage, Callback<FriendsListResponse> friendsListCallback) {
        FacebookFriendList facebookListService = ApiService.getService().create(FacebookFriendList.class);
        Call<FriendsListResponse> call = facebookListService.getFriendsList(userId, accessToken, limit, afterPage);
        call.enqueue(friendsListCallback);
    }
}
