package com.warrous.ready2ride.facebookfriendslist.data.model;

import java.util.ArrayList;

/**
 * Created by Sally on 08-Jan-17.
 */

public class FriendsListResponse {
    private ArrayList<FriendItemData> friendsDataList;
    private String nextPageId;
    private String previousPageId;

    public ArrayList<FriendItemData> getFriendsDataList() {
        return friendsDataList;
    }

    public void setFriendsDataList(ArrayList<FriendItemData> friendsDataList) {
        this.friendsDataList = friendsDataList;
    }

    public String getNextPageId() {
        return nextPageId;
    }

    public void setNextPageId(String nextPageId) {
        this.nextPageId = nextPageId;
    }

    public String getPreviousPageId() {
        return previousPageId;
    }

    public void setPreviousPageId(String previousPageId) {
        this.previousPageId = previousPageId;
    }
}
