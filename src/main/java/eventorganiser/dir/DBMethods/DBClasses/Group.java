package eventorganiser.dir.DBMethods.DBClasses;

import java.util.ArrayList;
import java.util.List;

public class Group {

    private int groupId;
    private String groupName;

    public Group() {
    }

    public Group(int groupId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
    }

    public int getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public List<Object> getListOfAllInfo() {   // Returns in order: [groupId, groupName]
        List<Object> li = new ArrayList<>();
        li.add(groupId);
        li.add(groupName);
        return li;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public void setAllFields(int groupId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
    }

}
