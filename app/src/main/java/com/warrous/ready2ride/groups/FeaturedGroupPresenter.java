package com.warrous.ready2ride.groups;


import com.warrous.ready2ride.base.BasePresenter;
import com.warrous.ready2ride.invitebuddies.model.FeaturedGroupsResponse;

import java.util.ArrayList;

public class FeaturedGroupPresenter extends BasePresenter implements FeaturedContract.Presenter {

    FeaturedContract.View view;
        boolean groups,featuredgroups;

        public FeaturedGroupPresenter(FeaturedContract.View view) {
            super(view);
            this.view = view;
        }

        @Override
        public void getFeaturedGroups(int ownerId,boolean group) {
            execute(getApiInterface().getFeaturedGroups(ownerId));
            featuredgroups=group;
        }

        @Override
        public void getGroups(int ownerId,boolean group) {
            groups=group;
            execute(getApiInterface().getGroups(ownerId));

        }

        @Override
        public void onResponse(String method, String message, Object result) {
            view.dismissLoader();
//            if(groups){
//                groups=false;
//                ArrayList<FeaturedGroupsResponse> list = (ArrayList<FeaturedGroupsResponse>) result;
//                view.onGetGroupSucess(list);
//            }
//            if(featuredgroups){
                featuredgroups=false;
                ArrayList<FeaturedGroupsResponse> list = (ArrayList<FeaturedGroupsResponse>) result;
                view.onFeaturedGroupSucessF(list);

           // }




        }
    }


