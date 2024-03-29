package com.warrous.ready2ride.facebookfriendslist.ui.main;

import com.facebook.AccessToken;

/**
 * Created by Sally on 01-Jan-17.
 */

public interface MainView {
    void initializeFBSdk();

    void initializeView();

    void showFriendsList();

    void showFBLoginResult(AccessToken fbAccessToken);

    void loginUsingFBManager();
}
