package com.warrous.ready2ride.facebookfriendslist.ui.friendsList;

import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.warrous.ready2ride.R;
import com.warrous.ready2ride.facebookfriendslist.adapter.FriendsAdapter;
import com.warrous.ready2ride.facebookfriendslist.data.model.FriendItemData;

import java.util.ArrayList;



public class FriendsListActivity extends AppCompatActivity implements FriendsListView {
    private ArrayList<FriendItemData> friendsList = new ArrayList<FriendItemData>();
    private SwipeRefreshLayout swipeLayout;
    private RecyclerView lvFriendsList;
    private FriendsAdapter friendsAdapter;
    private FriendsListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fbfriends_list);

        presenter = new FriendsListPresenter(this);
        presenter.onGetFBFriendsList();
    }

    @Override
    public void initializeView() {
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        swipeLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_green_dark),
                getResources().getColor(android.R.color.holo_red_dark),
                getResources().getColor(android.R.color.holo_blue_dark),
                getResources().getColor(android.R.color.holo_orange_dark));
        swipeLayout.setRefreshing(true);

        lvFriendsList = (RecyclerView) findViewById(R.id.rv_friends_list);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        lvFriendsList.setLayoutManager(linearLayoutManager);

        friendsAdapter = new FriendsAdapter(friendsList);
        lvFriendsList.setAdapter(friendsAdapter);

        lvFriendsList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemsCount = linearLayoutManager.getItemCount();
                int visibleItemsCount = lvFriendsList.getChildCount();
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();

                presenter.onLoadMore(totalItemsCount, visibleItemsCount, firstVisibleItemPosition);
            }
        });
    }

    @Override
    public void loadFriendsList(ArrayList<FriendItemData> fLst) {
        friendsList.removeAll(friendsList);
        friendsList.addAll(fLst);
        swipeLayout.setRefreshing(false);

        if ((friendsList != null) && (friendsList.size() > 0)) {
            lvFriendsList.setVisibility(View.VISIBLE);
            friendsAdapter.notifyDataSetChanged();
        } else {
            lvFriendsList.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError() {
        Toast.makeText(this, "Login Fail", Toast.LENGTH_LONG).show();
    }
}
