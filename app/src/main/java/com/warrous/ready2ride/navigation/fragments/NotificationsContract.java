package com.warrous.ready2ride.navigation.fragments;

import com.warrous.ready2ride.base.BaseContract;
import com.warrous.ready2ride.navigation.model.MessagesResponse;
import com.warrous.ready2ride.navigation.model.NotificationsResponse;

import java.util.ArrayList;

public interface NotificationsContract {

    interface View extends BaseContract.View {


       void onGetNotificationSucess(ArrayList<NotificationsResponse> notificationsResponses);
       void onGetMessagesSucess(ArrayList<MessagesResponse> messagesResponses);




    }

    interface Presenter extends BaseContract.Presenter {


        //  void getFeaturedGroups(int ownerId);
        void getNotifications(int dealerId,int ownerId);
        void getMessages(int dealerId,int ownerId);





    }
}
