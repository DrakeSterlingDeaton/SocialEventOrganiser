package eventorganiser.dir.DBMethods;

import eventorganiser.dir.DBMethods.DBClasses.Group;
import eventorganiser.dir.DBMethods.DBClasses.GroupMembership;
import eventorganiser.dir.DBMethods.DBClasses.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GroupQueries extends DBQueries {

    @Autowired
    public GroupQueries(JdbcTemplate jdbctemplate) {
        super(jdbctemplate);
    }

    /////////////////////////////////////// GET ALL METHODS ///////////////////////////////////////

    public List<Group> getAllGroups() {
        return jdbcTemplate().query("SELECT * FROM groups", new Object[]{},
                (rs, i) -> new Group(rs.getInt("groupId"), rs.getString("groupName") )
        );
    }

    public List<GroupMembership> getAllGroupMemberships() {
        return jdbcTemplate().query("select * from group_members", new Object[]{},
                (rs, i) -> new GroupMembership(rs.getInt("membershipId"), rs.getInt("groupId"), rs.getInt("userId") )
        );
    }

    /////////////////////////////////////// GET ID METHODS ///////////////////////////////////////

    public Integer getGroupIdFromGroupName(String groupName)throws DataAccessException {
        String str = String.format("SELECT groupId FROM groups WHERE groupName = \"%s\" LIMIT 1", groupName);
        List<Integer> groupId = jdbcTemplate().query(str, new Object[]{},
            (rs, i) -> rs.getInt("groupId") );
        return groupId.get(0);
    }

    /////////////////////////////////////// GET GROUP DETAILS METHODS ///////////////////////////////////////

    public List<User> getGroupMembers(int groupId) throws DataAccessException{
        String str = String.format("SELECT userId FROM group_members WHERE groupId = \"%s\"", groupId);
        List<String> groupMembersIds = jdbcTemplate().query(str, new Object[]{},
            (rs, i) -> ( (Integer) rs.getInt("userId")).toString() );
        String insertStr = String.join(", ", groupMembersIds);
        String str2 = String.format("SELECT * FROM users WHERE userId IN (\"%s\")", insertStr);
        return jdbcTemplate().query(str2, new Object[]{},
            (rs, i) -> new User(rs.getInt("userId"), rs.getString("username"),
                                rs.getString("password"), rs.getString("firstName"),
                                rs.getString("lastName")) );
    }

    /////////////////////////////////////// POST GROUP METHODS ///////////////////////////////////////

    public int postNewGroup(String groupName)throws DataAccessException {
        String str = "INSERT INTO groups(groupName) VALUES(?)";
        return jdbcTemplate().update(str, groupName);
    }

    /////////////////////////////////////// DELETE GROUP METHODS ///////////////////////////////////////

    public int deleteGroupById(int groupId){
        String str = "DELETE FROM groups WHERE groupId = ?";
        return jdbcTemplate().update(str, groupId);
    }


    /////////////////////////////////////// POST GROUP MEMBERSHIP METHODS ///////////////////////////////////////

    public int postNewGroupMember(int groupId, int userId){
        String str = "INSERT INTO group_members(groupId, userId) VALUES(?,?)";
        return jdbcTemplate().update(str, groupId, userId);
    }

    /////////////////////////////////////// DELETE GROUP MEMBERSHIP METHODS ///////////////////////////////////////

    public int deleteGroupMember(int groupId, int userId){
        String str = "DELETE FROM group_members WHERE groupId = ? AND userId = ?";
        return jdbcTemplate().update(str, groupId, userId);
    }

    /////////////////////////////////////// GET RANDOM GROUP METHODS ///////////////////////////////////////

    public Group getRandomGroup() throws DataAccessException {
        String str = "SELECT * FROM groups ORDER BY RAND() LIMIT 1";
        List<Group> group = jdbcTemplate().query(str, new Object[]{},
                (rs, i) -> new Group(rs.getInt("groupId"), rs.getString("groupName")) );
        return group.get(0);
    }

    /////////////////////////////////////// GET RANDOM GROUP MEMBERSHIP METHODS ///////////////////////////////////////

    public GroupMembership getRandomGroupMember() throws DataAccessException {
        String str = "SELECT * FROM group_members ORDER BY RAND() LIMIT 1";
        List<GroupMembership> membership = jdbcTemplate().query(str, new Object[]{},
                (rs, i) -> new GroupMembership(rs.getInt("membershipId"), rs.getInt("groupId"), rs.getInt("userId")));
        return membership.get(0);
    }
}
