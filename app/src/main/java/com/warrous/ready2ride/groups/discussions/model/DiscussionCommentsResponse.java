package com.warrous.ready2ride.groups.discussions.model;

public class DiscussionCommentsResponse {



    int OwnerId;
    int DiscussionCommentId;
    int DiscussionId;
    String DiscussionComment;
    String ImageUrl;
    String FirstName;
    String LastName;

    public int getOwnerId() {
        return OwnerId;
    }

    public void setOwnerId(int ownerId) {
        OwnerId = ownerId;
    }

    public int getDiscussionCommentId() {
        return DiscussionCommentId;
    }

    public void setDiscussionCommentId(int discussionCommentId) {
        DiscussionCommentId = discussionCommentId;
    }

    public int getDiscussionId() {
        return DiscussionId;
    }

    public void setDiscussionId(int discussionId) {
        DiscussionId = discussionId;
    }

    public String getDiscussionComment() {
        return DiscussionComment;
    }

    public void setDiscussionComment(String discussionComment) {
        DiscussionComment = discussionComment;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }
}
