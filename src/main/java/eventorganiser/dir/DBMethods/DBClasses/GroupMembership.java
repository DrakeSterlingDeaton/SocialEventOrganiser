package eventorganiser.dir.DBMethods.DBClasses;

import java.util.ArrayList;
import java.util.List;

public class GroupMembership {

    private int membershipId;
    private int groupId;
    private int userId;

    public GroupMembership () { }

    public GroupMembership(int membershipId, int groupId, int userId) {
        this.membershipId = membershipId;
        this.groupId = groupId;
        this.userId = userId;
    }

    public int getMembershipId() {
        return membershipId;
    }

    public int getGroupId() {
        return groupId;
    }

    public int getUserId() {
        return userId;
    }

    public List<Object> getListOfAllInfo() {   // Returns in order: [membershipId, groupId, userId]
        List<Object> li = new ArrayList<>();
        li.add(membershipId);
        li.add(groupId);
        li.add(userId);
        return li;
    }

    public void setMembershipId(int membershipId) {
        this.membershipId = membershipId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setAllFields(int membershipId, int groupId, int userId) {
        this.membershipId = membershipId;
        this.groupId = groupId;
        this.userId = userId;
    }

}
