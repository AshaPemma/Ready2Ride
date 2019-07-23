package com.warrous.ready2ride.dealership.popup;


import com.warrous.ready2ride.dealership.model.DealershipResponse;
import com.warrous.ready2ride.invitebuddies.model.ContactModel;

import java.util.ArrayList;

public class DataManager {
    static DataManager dataManager;

    ArrayList<DealershipResponse> dealerLists;
    ArrayList<ContactModel> contactModels;



    private DataManager() {

    }

    public static DataManager getInstance() {
        if (dataManager == null) {
            dataManager = new DataManager();
        }

        return dataManager;
    }

    public ArrayList<DealershipResponse> getDealerLists() {
        return dealerLists;
    }

    public void setDealerLists(ArrayList<DealershipResponse> dealerLists) {
        this.dealerLists = dealerLists;
    }

    public ArrayList<ContactModel> getContactModels() {
        return contactModels;
    }

    public void setContactModels(ArrayList<ContactModel> contactModels) {
        this.contactModels = contactModels;
    }
}
