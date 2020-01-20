package eventorganiser.dir.API;

import eventorganiser.dir.DBMethods.DBClasses.Group;
import eventorganiser.dir.DBMethods.GroupQueries;
import eventorganiser.dir.DBMethods.TeamQueries;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GroupsAPI {

    private GroupQueries GroupQrys;

    public GroupsAPI(GroupQueries g) {
        GroupQrys = g;
    }

    // get All Group Memberships
    @GetMapping("api/get/group/membership")
    public Object APIgetAllGroupMemberships() {
        return GroupQrys.getAllGroupMemberships();
    }

    //get all Groups
    @GetMapping("api/get/groups")
    public Object APIgetAllGroups() {
        return GroupQrys.getAllGroups();
    }

    //get Group Id
    @GetMapping("api/get/group")
    public Object APIgetGroupIdFromGroupName(@RequestParam(name="groupName", required=true) String groupName) {
        return GroupQrys.getGroupIdFromGroupName(groupName);
}

    //get Group Members
    @GetMapping("api/get/group/member")
    public Object APIgetGroupMember(@RequestParam(name="groupId", required=true) String groupId) {
        int groupIdInt = Integer.parseInt(groupId);
        return GroupQrys.getGroupMembers(groupIdInt);
    }

    //get Random Group Member
    @GetMapping("api/get/group/member/random")
    public Object APIgetRandomGroupMember() {
        return GroupQrys.getRandomGroupMember();
    }

    //get random Group
    @GetMapping("api/get/group/random")
    public Object APIgetRandomGroup() {
        return GroupQrys.getRandomGroup();
    }
}
