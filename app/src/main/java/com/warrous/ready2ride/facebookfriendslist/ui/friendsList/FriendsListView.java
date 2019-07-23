package com.warrous.ready2ride.facebookfriendslist.ui.friendsList;

import com.warrous.ready2ride.facebookfriendslist.data.model.FriendItemData;

import java.util.ArrayList;


/**
 * Created by Sally on 01-Jan-17.
 */

public interface FriendsListView {
    void initializeView();

    void loadFriendsList(ArrayList<FriendItemData> friendsList);

    void showError();
}
