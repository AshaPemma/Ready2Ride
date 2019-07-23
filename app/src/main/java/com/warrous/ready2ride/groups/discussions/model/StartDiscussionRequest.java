package com.warrous.ready2ride.groups.discussions.model;

public class StartDiscussionRequest {

    int GroupId;
    int OwnerId;
    int DiscussionId;
    String DiscussionTittle;
    String DiscussionDescription;
    String Image;

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

    public int getDiscussionId() {
        return DiscussionId;
    }

    public void setDiscussionId(int discussionId) {
        DiscussionId = discussionId;
    }

    public String getDiscussionTittle() {
        return DiscussionTittle;
    }

    public void setDiscussionTittle(String discussionTittle) {
        DiscussionTittle = discussionTittle;
    }

    public String getDiscussionDescription() {
        return DiscussionDescription;
    }

    public void setDiscussionDescription(String discussionDescription) {
        DiscussionDescription = discussionDescription;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }
}
