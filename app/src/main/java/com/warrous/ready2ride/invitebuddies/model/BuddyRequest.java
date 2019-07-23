package com.warrous.ready2ride.invitebuddies.model;

public class BuddyRequest {

     int SenderId ;
     int ReceiverId ;
  boolean IsRequestSent;
    boolean  IsAccepted ;

    public int getSenderId() {
        return SenderId;
    }

    public void setSenderId(int senderId) {
        SenderId = senderId;
    }

    public int getReceiverId() {
        return ReceiverId;
    }

    public void setReceiverId(int receiverId) {
        ReceiverId = receiverId;
    }

    public boolean isRequestSent() {
        return IsRequestSent;
    }

    public void setRequestSent(boolean requestSent) {
        IsRequestSent = requestSent;
    }

    public boolean isAccepted() {
        return IsAccepted;
    }

    public void setAccepted(boolean accepted) {
        IsAccepted = accepted;
    }
}
