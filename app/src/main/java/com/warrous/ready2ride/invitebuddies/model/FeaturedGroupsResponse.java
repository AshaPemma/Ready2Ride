package com.warrous.ready2ride.invitebuddies.model;

import com.warrous.ready2ride.groups.model.GroupMembers;

import java.io.Serializable;
import java.util.ArrayList;

public class FeaturedGroupsResponse implements Serializable {

    int GroupId;
    String Name;
    String Mode;
    String Description;
    String Icon;
    int OwnersCount;
    ArrayList<GroupMembers> OwnerDetails;



    public int getGroupId() {
        return GroupId;
    }

    public void setGroupId(int groupId) {
        GroupId = groupId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMode() {
        return Mode;
    }

    public void setMode(String mode) {
        Mode = mode;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        Icon = icon;
    }

    public int getOwnersCount() {
        return OwnersCount;
    }

    public void setOwnersCount(int ownersCount) {
        OwnersCount = ownersCount;
    }

    public ArrayList<GroupMembers> getOwnerDetails() {
        return OwnerDetails;
    }

    public void setOwnerDetails(ArrayList<GroupMembers> ownerDetails) {
        OwnerDetails = ownerDetails;
    }
}
