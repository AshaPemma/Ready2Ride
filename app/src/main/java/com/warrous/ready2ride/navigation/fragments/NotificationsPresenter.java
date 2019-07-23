package com.warrous.ready2ride.navigation.fragments;

import com.warrous.ready2ride.base.BasePresenter;
import com.warrous.ready2ride.navigation.model.MessagesResponse;
import com.warrous.ready2ride.navigation.model.NotificationsResponse;

import java.util.ArrayList;

public class NotificationsPresenter extends BasePresenter implements NotificationsContract.Presenter {
    NotificationsContract.View view;

    public NotificationsPresenter(NotificationsContract.View view) {
        super(view);
        this.view = view;
    }

    @Override
    public void getNotifications(int dealerId, int ownerId) {
execute(getApiInterface().getNotifications(ownerId));
    }

    @Override
    public void getMessages(int dealerId, int ownerId) {
        execute(getApiInterface().getMessages(dealerId,ownerId));
    }

    @Override
    public void onResponse(String method, String message, Object result) {
        view.dismissLoader();
        if(method.equalsIgnoreCase("r2r.ms.mobile/r2r.ms.mobile.api/api/Notification/GetNotifications")){

            ArrayList<NotificationsResponse> list = (ArrayList<NotificationsResponse>) result;
            view.onGetNotificationSucess(list);
        }else{
            ArrayList<MessagesResponse> list = (ArrayList<MessagesResponse>) result;
            view.onGetMessagesSucess(list);

        }



    }
}
