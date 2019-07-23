package com.warrous.ready2ride.navigation.fragments;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.base.BaseFragment;
import com.warrous.ready2ride.framework.PreferenceManager;
import com.warrous.ready2ride.navigation.adapter.MessagesAdapter;
import com.warrous.ready2ride.navigation.adapter.NotificationsAdapter;
import com.warrous.ready2ride.navigation.model.MessagesResponse;
import com.warrous.ready2ride.navigation.model.NotificationsResponse;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class InboxFragment extends BaseFragment implements NotificationsContract.View {

    @BindView(R.id.rv_items)
    RecyclerView rvInboxItems;
    @BindView(R.id.tv_notifications)
    TextView tvNotifications;
    @BindView(R.id.tv_messages)
            TextView tvMessages;
    @BindView(R.id.tv_no_notifications)
            TextView tvNoNotifications;

    NotificationsAdapter notificationsAdapter;
    MessagesAdapter messagesAdapter;
    NotificationsContract.Presenter mPresenter;

    public InboxFragment() {
    }
    public static InboxFragment getInstance() {
        InboxFragment fragment = new InboxFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);
        injectView(view);
        mPresenter=new NotificationsPresenter(this);
        tvMessages.setAlpha((float) 0.5);
        tvNotifications.setAlpha(1);
        rvInboxItems.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        notificationsAdapter=new NotificationsAdapter(getContext(),null,null);
        rvInboxItems.setAdapter(notificationsAdapter);
       mPresenter.getNotifications(PreferenceManager.getIntegerValue(getContext(),PreferenceManager.KEY_DEALER_ID),PreferenceManager.getIntegerValue(getContext(),PreferenceManager.KEY_OWNER_ID));
        return view;
    }

    public void setNotificationsAdapter(ArrayList<NotificationsResponse> notificationsList){

        notificationsAdapter=new NotificationsAdapter(getContext(),null,notificationsList);
        rvInboxItems.setAdapter(notificationsAdapter);

    }

    public void setMessagesAdapter(ArrayList<MessagesResponse> messagesList){

        messagesAdapter=new MessagesAdapter(getContext(),null,messagesList);
        rvInboxItems.setAdapter(messagesAdapter);

    }
    @OnClick(R.id.tv_notifications)
    public void onClickNotifications(){
        tvMessages.setAlpha((float) 0.5);
        tvNotifications.setAlpha(1);
        mPresenter.getNotifications(PreferenceManager.getIntegerValue(getContext(),PreferenceManager.KEY_DEALER_ID),PreferenceManager.getIntegerValue(getContext(),PreferenceManager.KEY_OWNER_ID));
        }

    @OnClick(R.id.tv_messages)
    public void onClickMessages(){
        tvNotifications.setAlpha((float) 0.5);
        tvMessages.setAlpha(1);
        tvNoNotifications.setVisibility(View.GONE);
        rvInboxItems.setVisibility(View.VISIBLE);
        mPresenter.getMessages(PreferenceManager.getIntegerValue(getContext(),PreferenceManager.KEY_DEALER_ID),PreferenceManager.getIntegerValue(getContext(),PreferenceManager.KEY_OWNER_ID));

    }

    @Override
    public void onGetNotificationSucess(ArrayList<NotificationsResponse> notificationsResponses) {
        if(notificationsResponses.size()>0){
            rvInboxItems.setVisibility(View.VISIBLE);
            tvNoNotifications.setVisibility(View.GONE);
            setNotificationsAdapter(notificationsResponses);
        }else{
            rvInboxItems.setVisibility(View.GONE);
            tvNoNotifications.setVisibility(View.VISIBLE);
        }



    }

    @Override
    public void onGetMessagesSucess(ArrayList<MessagesResponse> messagesResponses) {
        if(messagesResponses.size()>0){
            rvInboxItems.setVisibility(View.VISIBLE);
            tvNoNotifications.setVisibility(View.GONE);
            setMessagesAdapter(messagesResponses);
        }else{
            rvInboxItems.setVisibility(View.GONE);
            tvNoNotifications.setVisibility(View.VISIBLE);
            tvNoNotifications.setText("No Messages");
        }

    }
}
