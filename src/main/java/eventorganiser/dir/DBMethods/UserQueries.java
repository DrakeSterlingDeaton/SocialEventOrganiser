package eventorganiser.dir.DBMethods;

import eventorganiser.dir.DBMethods.DBClasses.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserQueries extends DBQueries {

    @Autowired
    public UserQueries(JdbcTemplate jdbctemplate) {
        super(jdbctemplate);
    }

    /////////////////////////////////////// GET ALL METHODS ///////////////////////////////////////

    public List<User> getAllUsers() throws DataAccessException {
        return jdbcTemplate().query("select * from users", new Object[]{},
            (rs, i) ->
                new User(rs.getInt("userId"), rs.getString("username"), rs.getString("password"),
                rs.getString("firstName"), rs.getString("lastName") )
        );
    }


    /////////////////////////////////////// GET ID METHODS ///////////////////////////////////////

    public Integer getUserIdFromUsername(String username) throws DataAccessException {
        String str = String.format("SELECT userId FROM users WHERE username = '%s' LIMIT 1;", username);
        List<Integer> usrId = jdbcTemplate().query(str, new Object[]{},
            (rs, i) -> rs.getInt("userId") );
        return usrId.get(0);
    }

    public String getUserFullNAmeFromUsername(String username) throws DataAccessException {

        String firstname = String.format("SELECT firstName FROM users WHERE username = '%s' LIMIT 1;", username);
        String lastname = String.format("SELECT lastName FROM users WHERE username = '%s' LIMIT 1;", username);
        List<String> fname = jdbcTemplate().query(firstname, new Object[]{},
                (rs, i) -> rs.getString("firstName") );
        List<String> lname = jdbcTemplate().query(lastname, new Object[]{},
                (rs, i) -> rs.getString("lastName") );
        String fullname = fname.get(0) + " " + lname.get(0);
        return fullname;
    }

    /////////////////////////////////////// GET USER DETAILS METHODS ///////////////////////////////////////

    public List<User> getUserDetailsFromId(String userId) throws DataAccessException {
        String str = String.format("SELECT * FROM users WHERE userId = '%s' LIMIT 1", userId);
        return jdbcTemplate().query(str, new Object[]{},
                (rs, i) -> new User(rs.getInt("userId"),
                        rs.getString("username"),rs.getString("password"), rs.getString("firstName"),
                        rs.getString("lastName"))
        );
    }
    public List<AttendanceRecord> getUserAttendanceRecordsFromUserId(String userId) throws DataAccessException {
        String str = String.format("SELECT * FROM attendance WHERE userId = '%s'", userId);
        return jdbcTemplate().query(str, new Object[]{},
            (rs, i) -> new AttendanceRecord(rs.getInt("attendanceId"), rs.getInt("userId"),
                                            rs.getInt("eventId"), rs.getInt("response"),
                                            rs.getInt("teamId") )
        );
    }

    public List<Event> getFutureUserEventsFromUserIdInChronologicalOrder(int userId) {
        String str = new StringBuilder()
        .append("SELECT * "                                                          )
        .append("FROM events "                                                       )
        .append("WHERE eventId IN (SELECT eventId "                                  )
        .append(                  "FROM attendance "                                 )
        .append(                  "WHERE userId = " + userId + " AND response != 2)" )
        .append("AND eventStartDate >= CURRENT_DATE() "                  )
        .append("ORDER BY eventStartDate "                               )
        .toString();
        List<Event> eventLi = jdbcTemplate().query(str, new Object[]{},
            (rs, i) ->
                new Event(rs.getInt("eventId"), rs.getString("eventName"),
                        rs.getString("eventDesc"), rs.getString("eventLocSt1"),
                        rs.getString("eventLocSt2"), rs.getString("eventLocCity"),
                        rs.getString("eventLocPost"), rs.getDate("eventStartDate"),
                        rs.getDate("eventEndDate"),rs.getTime("eventStartTime"),
                        rs.getTime("eventEndTime"), rs.getInt("eventOrganiser"),
                        rs.getDate("dateCreated"), rs.getInt("maxTeamSize"),
                        rs.getString("eventColour")) );
        if (eventLi.isEmpty()) {return null; }
        else { return eventLi; }
    }

    public List<Event> getPastUserEventsFromUserIdInReverseChronologicalOrder(int userId) {
        String str = new StringBuilder()
                .append("SELECT * "                                         )
                .append("FROM events "                                      )
                .append("WHERE eventId IN (SELECT eventId "                 )
                .append(                  "FROM attendance "                )
                .append(                  "WHERE userId = " + userId + ") " )
                .append("AND eventDate < CURRENT_DATE() "                   )
                .append("ORDER BY eventDate DESC "                          )
                .toString();
        List<Event> eventLi = jdbcTemplate().query(str, new Object[]{},
            (rs, i) ->
                new Event(rs.getInt("eventId"), rs.getString("eventName"),
                rs.getString("eventDesc"), rs.getString("eventLocSt1"),
                rs.getString("eventLocSt2"), rs.getString("eventLocCity"),
                rs.getString("eventLocPost"), rs.getDate("eventStartDate"),
                rs.getDate("eventEndDate"),rs.getTime("eventStartTime"),
                rs.getTime("eventEndTime"), rs.getInt("eventOrganiser"),
                rs.getDate("dateCreated"), rs.getInt("maxTeamSize"),
                rs.getString("eventColour")) );
        if (eventLi.isEmpty()) {return null; }
        else { return eventLi; }
    }

    public List<Team> getUserTeamsFromUserId(int userId) throws DataAccessException {
        String str = String.format("SELECT teamId FROM attendance WHERE userId = '%s'", userId);
        List<String> teamIdLi = jdbcTemplate().query(str, new Object[]{},
            (rs, i) -> ( (Integer) rs.getInt("teamId")).toString() );
        if (teamIdLi.isEmpty()){return null; }
        String insertStr = String.join(", ", teamIdLi);
        String str2 = String.format("SELECT * FROM teams WHERE teamId IN (%s)", String.join(", ", teamIdLi) );
        List<Team> teamLi = jdbcTemplate().query(str2, new Object[]{},
            (rs, i) -> new Team(rs.getInt("teamId"), rs.getInt("eventId"),
                                rs.getString("teamName"))
        );
        if (teamLi.isEmpty()){return null; }
        else {return teamLi;}
    }

    public Team getUserTeamForEventByUserIdAndEventId(int userId, int eventId) throws DataAccessException {
        String str = String.format("SELECT teamId FROM attendance WHERE userId = \"%s\" AND eventId = \"%s\" LIMIT 1", userId, eventId);
        List<Integer> teamIdLi = jdbcTemplate().query(str, new Object[]{},
            (rs, i) -> rs.getInt("teamId") );
        if ( teamIdLi.isEmpty()){ return null; }
        String str2 = String.format("SELECT * FROM teams WHERE teamId = \"%s\"", teamIdLi.get(0));
        List<Team> teamLi = jdbcTemplate().query(str2, new Object[]{},
                (rs, i) -> new Team(rs.getInt("teamId"), rs.getInt("eventId"),
                        rs.getString("teamName"))
        );
        if ( teamLi.isEmpty()){ return null; }
        else { return teamLi.get(0); }
    }

    public List<Group> getUserGroupsFromUserId(int userId) throws DataAccessException {
        String str = String.format("SELECT groupId FROM group_members WHERE userId = '%s'", userId);
        List<String> groupIdLi = jdbcTemplate().query(str, new Object[]{},
                (rs, i) -> ( (Integer) rs.getInt("groupId")).toString() );
        if ( groupIdLi.isEmpty() ) {return null; };
        String insertStr = String.join(", ", groupIdLi);
        String str2 = String.format("SELECT * FROM groups WHERE groupId IN (%s)", insertStr);
        List<Group> groupLi = jdbcTemplate().query(str2, new Object[]{},
            (rs, i) -> new Group(rs.getInt("groupId"), rs.getString("groupName") )
        );
        if ( groupLi.isEmpty() ){ return null; }
        else { return groupLi; }
    }

    /////////////////////////////////////// POST METHODS ///////////////////////////////////////

    public int postNewUser (String username, String password, int enabled, String firstName, String lastName) throws DataAccessException {
        String str = "INSERT INTO users(username, password, enabled, firstName, lastName) VALUES(?,?,?,?,?)";
        return jdbcTemplate().update(str, username, password, enabled, firstName, lastName);
    }

    /////////////////////////////////////// DELETE METHODS ///////////////////////////////////////

    public int deleteUserById (int userId) throws DataAccessException {
        String str = "DELETE FROM users WHERE username = ?";
        return jdbcTemplate().update(str, userId);
    }

    /////////////////////////////////////// GET RANDOM ROW METHODS ///////////////////////////////////////

    public User getRandomUser()throws DataAccessException {
        String str = "SELECT * FROM users ORDER BY RAND() LIMIT 1";
        List<User> usrLi = jdbcTemplate().query(str, new Object[]{},
            (rs, i) -> new User(rs.getInt("userId"), rs.getString("username"), rs.getString("password"),
                                rs.getString("firstName"), rs.getString("lastName") ) );
        return usrLi.get(0);
    }

}