package eventorganiser.dir.DBMethods;

import eventorganiser.dir.DBMethods.DBClasses.AttendanceRecord;
import eventorganiser.dir.DBMethods.DBClasses.Event;
import eventorganiser.dir.DBMethods.DBClasses.Team;
import eventorganiser.dir.DBMethods.DBClasses.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TeamQueries extends DBQueries {

    @Autowired
    public TeamQueries(JdbcTemplate jdbctemplate) {
        super(jdbctemplate);
    }

    /////////////////////////////////////// GET ALL METHODS ///////////////////////////////////////

    public List<Team> getAllTeams() throws DataAccessException{
        return jdbcTemplate().query("SELECT * FROM teams", new Object[]{},
                (rs, i) -> new Team(rs.getInt("teamId"), rs.getInt("eventId"),
                        rs.getString("teamName"))
        );
    }

    public List<Team> getAllTeamsInEventByEventId(int eventId) throws DataAccessException{
        return jdbcTemplate().query("select * from teams inner join events where teams.eventId = " + eventId + " group by teamName", new Object[]{},
                (rs, i) -> new Team(rs.getInt("teamId"), rs.getInt("eventId"),
                        rs.getString("teamName"))

        );

    }

    /////////////////////////////////////// GET ID METHODS ///////////////////////////////////////

    public Integer getTeamIdFromTeamNameAndEventId(String teamName, int eventId) throws DataAccessException {
        try {
            String str = String.format("SELECT teamId FROM teams WHERE teamName = \"%s\" AND eventId = \"%s\" LIMIT 1", teamName, eventId);
            List<Integer> teamId = jdbcTemplate().query(str, new Object[]{},
                    (rs, i) -> rs.getInt("teamId"));
            return teamId.get(0);
        } catch (IndexOutOfBoundsException e) { // Exception returned if DB returns no matches
            return null;
        }
    }

    public Integer getTeamIdFromEventId(int eventId) throws DataAccessException {
        try {
            String str = String.format("SELECT teamId FROM teams WHERE eventId = \"%s\" LIMIT 1", eventId);
            List<Integer> teamId = jdbcTemplate().query(str, new Object[]{},
                    (rs, i) -> rs.getInt("teamId"));
            return teamId.get(0);
        } catch (IndexOutOfBoundsException e) {  // Exception returned if DB returns no matches
            System.out.println("No Team Found");
            return null;
        }
    }

    public Integer getTeamIdFromEventIdAndUserId(int eventId, int userId) throws DataAccessException {
        try {
            String str = String.format("SELECT teamId FROM attendance WHERE eventId = \"%s\" AND userId = \"%s\" LIMIT 1", eventId, userId);
            List<Integer> teamId = jdbcTemplate().query(str, new Object[]{},
                    (rs, i) -> rs.getInt("teamId"));
            return teamId.get(0);
        } catch (IndexOutOfBoundsException e) {  // Exception returned if DB returns no matches
            System.out.println("No Team Found");
            return null;
        }
    }

    /////////////////////////////////////// GET TEAM DETAILS METHODS ///////////////////////////////////////

    public List<User> getTeamMembers(int eventId, int teamId) throws DataAccessException {
        String str = String.format("SELECT * FROM users INNER JOIN attendance ON attendance.userId = users.userId WHERE eventId=%d AND teamId=%d",eventId,teamId);
        return jdbcTemplate().query(str,
                (rs, i) -> new User(rs.getInt("userId"), rs.getString("username"), (null),
                        rs.getString("firstName"), rs.getString("lastName")));
    }

    public List<Team> getTeamsForAnEventFromEventId(int eventId) throws DataAccessException {
        String str = String.format("SELECT * FROM teams WHERE eventId = \"%s\"", eventId);
        return jdbcTemplate().query(str,
                (rs, i) -> new Team(rs.getInt("teamId"), rs.getInt("eventId"),
                        rs.getString("teamName") )
        );
    }

    public Integer getEventIdThatTeamIsRelatedTo(int teamId) throws DataAccessException {
        String str = String.format("SELECT eventId from teams WHERE teamId = \"%s\" LIMIT 1", teamId);
        List<Integer> eventIdLi = jdbcTemplate().query(str, new Object[]{},
                (rs, i) -> rs.getInt("eventId") );
        return eventIdLi.get(0);
    }

    public Integer getTeamMaxSizeFromTeamId(int teamId) throws DataAccessException {
        String str = String.format("SELECT eventId FROM attendance WHERE teamId = \"%s\" LIMIT 1", teamId);
        List<Integer> eventIdLi = jdbcTemplate().query(str, new Object[]{},
                (rs, i) -> rs.getInt("eventId") );
        String str2 = String.format("SELECT maxTeamSize FROM events WHERE eventId = \"%s\" LIMIT 1",
                eventIdLi.get(0).toString());
        List<Integer> maxTeamSizeLi = jdbcTemplate().query(str2,new Object[]{},
                (rs, i) -> rs.getInt("maxTeamSize"));
        return maxTeamSizeLi.get(0);
    }

    public String getTeamNameFromTeamId(int teamId) throws DataAccessException { //SELECT teamId FROM teams WHERE eventId = \"%s\" LIMIT 1"
        try {
            String str = String.format("SELECT * FROM teams WHERE teamId = \"%s\" LIMIT 1", teamId);
            List<String> teamNameLi = jdbcTemplate().query(str, new Object[]{},
                    (rs, i) -> rs.getString("teamName"));
            return teamNameLi.get(0);
        } catch (IndexOutOfBoundsException e) {  // Exception returned if DB returns no matches
            return null;
        }
    }

    /////////////////////////////////////// POST TEAM METHODS ///////////////////////////////////////

    public Integer postNewTeam(int eventId, String teamName) throws DataAccessException{
        String str = "INSERT INTO teams(eventId, teamName) VALUES(?,?)";
        return jdbcTemplate().update(str, eventId, teamName);
    }

    /////////////////////////////////////// DELETE TEAM METHODS ///////////////////////////////////////

    public Integer deleteTeamById(int teamId) throws DataAccessException{
        String str = "DELETE FROM teams WHERE teamId = ?";
        return jdbcTemplate().update(str, teamId);
    }

    ////////////////////////////////////////// EDIT TEAM METHODS ///////////////////////////////////////

    public int editTeamName(int teamId, String newTeamName) throws DataAccessException {
        String str = "UPDATE teams SET teamName = ? WHERE teamId = ? ";
        return jdbcTemplate().update(str, newTeamName, teamId);
    }

    /////////////////////////////////////// GET RANDOM ROWS METHODS ///////////////////////////////////////

    public Team getRandomTeam()throws DataAccessException {
        String str = "SELECT * FROM teams ORDER BY RAND() LIMIT 1";
        List<Team> team = jdbcTemplate().query(str, new Object[]{},
                (rs, i) -> new Team(rs.getInt("teamId"), rs.getInt("eventId"),
                rs.getString("teamName")));
        return team.get(0);
    }

}
