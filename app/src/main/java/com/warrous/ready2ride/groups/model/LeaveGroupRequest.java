package com.warrous.ready2ride.groups.model;

public class LeaveGroupRequest {
    int GroupId;
    int OwnerId;

    public int getGroupId() {
        return GroupId;
    }

    public void setGroupId(int groupId) {
        GroupId = groupId;
    }

    public int getOwnerId() {
        return OwnerId;
    }

    public void setOwnerId(int ownerId) {
        OwnerId = ownerId;
    }
}
