package eventorganiser.dir.DBMethods;

import eventorganiser.dir.DBMethods.DBClasses.Event;
import eventorganiser.dir.DBMethods.DBClasses.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EventQueries extends DBQueries {

    @Autowired
    public EventQueries(JdbcTemplate jdbctemplate) {
        super(jdbctemplate);
    }

    /////////////////////////////////////// GET ALL METHODS ///////////////////////////////////////

    public List<Event> getAllEvents() throws DataAccessException {
        return jdbcTemplate().query("select * from events", new Object[]{},
            (rs, i) -> new Event(rs.getInt("eventId"), rs.getString("eventName"),
                                 rs.getString("eventDesc"), rs.getString("eventLocSt1"),
                                 rs.getString("eventLocSt2"), rs.getString("eventLocCity"),
                                 rs.getString("eventLocPost"), rs.getDate("eventStartDate"),
                                 rs.getDate("eventEndDate"),rs.getTime("eventStartTime"),
                                 rs.getTime("eventEndTime"), rs.getInt("eventOrganiser"),
                                 rs.getDate("dateCreated"), rs.getInt("maxTeamSize"),
                                 rs.getString("eventColour") )
        );
    }

    public List<Event> getAllEventsInChronologicalOrder() throws DataAccessException {
        return jdbcTemplate().query("SELECT * FROM events WHERE eventStartDate >= CURRENT_DATE() ORDER BY eventStartDate", new Object[]{},
            (rs, i) -> new Event(rs.getInt("eventId"), rs.getString("eventName"),
                                 rs.getString("eventDesc"), rs.getString("eventLocSt1"),
                                 rs.getString("eventLocSt2"), rs.getString("eventLocCity"),
                                 rs.getString("eventLocPost"), rs.getDate("eventStartDate"),
                                 rs.getDate("eventEndDate"),rs.getTime("eventStartTime"),
                                 rs.getTime("eventEndTime"), rs.getInt("eventOrganiser"),
                                 rs.getDate("dateCreated"), rs.getInt("maxTeamSize"),
                                 rs.getString("eventColour") )
        );
    }

    /////////////////////////////////////// GET ID METHODS ///////////////////////////////////////

    public Integer getEventIdFromEventNameAndDate(String eventName, String eventStartDate){
        String str = String.format("SELECT eventId FROM events WHERE eventName = \"%s\" AND eventStartDate = \"%s\" LIMIT 1", eventName, eventStartDate);
        List<Integer> eventId = jdbcTemplate().query(str, new Object[]{}, (rs, i) -> rs.getInt("eventId") );
        return eventId.get(0);
    }

    /////////////////////////////////////// GET EVENTS FROM ORGANIZER METHODS ///////////////////////////////////////

   public List<Event> getEventsCreatedbyUserId(int eventOrganiser) throws DataAccessException{
       String getSql = String.format("SELECT  eventId, eventName, dateCreated FROM events WHERE eventOrganiser = '%s' ", eventOrganiser);
       List<Event> eventLi = jdbcTemplate().query(getSql, new Object[]{},
               (rs, i) -> new Event(rs.getInt("eventId"),
                                    rs.getString("eventName"),
                                    rs.getDate("dateCreated") )
       );
       return eventLi;
   }


    /////////////////////////////////////// GET EVENT DETAILS METHODS ///////////////////////////////////////

    public Event getEventDetailsByEventId(int eventId) throws DataAccessException {
        String str = String.format("SELECT * FROM events WHERE eventId = \"%s\" LIMIT 1", eventId);
        List<Event> eventLi = jdbcTemplate().query(str, new Object[]{},
            (rs, i) -> new Event(rs.getInt("eventId"), rs.getString("eventName"),
                                 rs.getString("eventDesc"), rs.getString("eventLocSt1"),
                                 rs.getString("eventLocSt2"), rs.getString("eventLocCity"),
                                 rs.getString("eventLocPost"), rs.getDate("eventStartDate"),
                                 rs.getDate("eventEndDate"),rs.getTime("eventStartTime"),
                                 rs.getTime("eventEndTime"), rs.getInt("eventOrganiser"),
                                 rs.getDate("dateCreated"), rs.getInt("maxTeamSize"),
                                 rs.getString("eventColour") )
        );
        return eventLi.get(0);
    }

    public List<User> getEventAttendeesByEventId(int eventId) throws DataAccessException{
        String str = "select users.userId, username, firstName, lastName, attendance.response, events.eventId from users inner join " +
                "attendance on attendance.userId = users.userId inner join " +
                "events on attendance.eventId = events.eventId where attendance.response = 1 and events.eventId = " + eventId;
        List<User> userLi = jdbcTemplate().query(str, new Object[]{},
                (rs, i) -> new User(rs.getInt("userId"), rs.getString("username"),
                        rs.getString("firstName"), rs.getString("lastName"))
        );

        return userLi;
    }

    public List<Integer> getEventTeamsByEventId(int eventId) {
        String str = String.format("SELECT teamId FROM attendance WHERE eventId = \"%s\" GROUP BY teamId", eventId);
        List<Integer> teamsLi = jdbcTemplate().query(str, new Object[]{},
                (rs, i) -> rs.getInt("teamId") );
        if ( teamsLi.isEmpty() ) { return null; }
        else { return teamsLi; }
    }

    /////////////////////////////////////// POST EVENT METHODS ///////////////////////////////////////

    public int postNewEvent(String eventName, String eventDesc, String eventLocSt1, String eventLocSt2, String eventLocCity,
                            String eventLocPost, String eventStartDate, String eventEndDate, String eventStartTime, String eventEndTime,
                            Integer eventOrganiser, Integer maxTeamSize, String dateCreated, String eventColour)
            throws DataAccessException {
        String str = "INSERT INTO events(eventName, eventDesc, eventLocSt1, eventLocSt2, eventLocCity, " +
                "eventLocPost, eventStartDate, eventEndDate, eventStartTime, eventEndTime, eventOrganiser, maxTeamSize, dateCreated, eventColour)" +
                "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        return jdbcTemplate().update(str, eventName, eventDesc, eventLocSt1, eventLocSt2, eventLocCity,
                eventLocPost, eventStartDate, eventEndDate, eventStartTime, eventEndTime,
                eventOrganiser, maxTeamSize, dateCreated, "#" + eventColour);
    }

    /////////////////////////////////////// DELETE EVENTS METHODS ///////////////////////////////////////

    public Integer deleteEvents(int eventId) throws DataAccessException{
        String deleteSql = String.format("DELETE FROM events WHERE eventId = '%s' ", eventId);
        return jdbcTemplate().update(deleteSql);
    }

    /////////////////////////////////////// EVENT EDITING METHODS ///////////////////////////////////////

    public Integer updateUserTeam(int userId, int eventId, int teamId){
        String str = "update attendance set teamId=" + teamId + " where eventId=" + eventId + " and userId=" + userId;
        return jdbcTemplate().update(str);
    }

    public Integer editEventName(int eventId, String newEventName) throws DataAccessException {
        String str = "UPDATE events SET eventName = ? WHERE eventId = ? ";
        return jdbcTemplate().update(str, newEventName, eventId);
    }

    public Integer editDesc(int eventId, String newEventDesc) throws DataAccessException {
        String str = "UPDATE events SET eventDesc = ? WHERE eventId = ? ";
        return jdbcTemplate().update(str, newEventDesc, eventId);
    }

    public Integer editEventLoc(int eventId, String newEventLocSt1, String newEventLocSt2, String newEventLocCity,
                                String newEventLocPost) throws DataAccessException {
        String str = "UPDATE events SET eventLocSt1 = ?, eventLocSt2 = ?, eventLocCity = ?, " +
                     "eventLocPost = ? WHERE eventId = ? ";
        return jdbcTemplate().update(str, newEventLocSt1, newEventLocSt2, newEventLocCity, newEventLocPost, eventId);
    }

    public Integer editEventStartDate(int eventId, String newEventStartDate) throws DataAccessException {
        String str = "UPDATE events SET eventStartDate = ? WHERE eventId = ? ";
        return jdbcTemplate().update(str, newEventStartDate, eventId);
    }

    public Integer editEventEndDate(int eventId, String newEventEndDate) throws DataAccessException {
        String str = "UPDATE events SET eventEndDate = ? WHERE eventId = ? ";
        return jdbcTemplate().update(str, newEventEndDate, eventId);
    }

    public Integer editEventStartTime(int eventId, String newEventStartTime) throws DataAccessException {
        String str = "UPDATE events SET eventStartTime = ? WHERE eventId = ? ";
        return jdbcTemplate().update(str, newEventStartTime, eventId);
    }

    public Integer editEventEndTime(int eventId, String newEventEndTime) throws DataAccessException {
        String str = "UPDATE events SET eventEndTime = ? WHERE eventId = ? ";
        return jdbcTemplate().update(str, newEventEndTime, eventId);
    }

    public Integer editEventMaxTeamSize(int eventId, int newMaxTeamSize) throws DataAccessException {
        String str = "UPDATE events SET maxTeamSize = ? WHERE eventId = ? ";
        return jdbcTemplate().update(str, newMaxTeamSize, eventId);
    }

    public Integer editEventColour(int eventId, int newEventColour) throws DataAccessException {
        String str = "UPDATE events SET eventColour = ? WHERE eventId = ? ";
        return jdbcTemplate().update(str, newEventColour, eventId);
    }


    /////////////////////////////////////// GET RANDOM ROWS METHODS ///////////////////////////////////////

    public Event getRandomEvent() throws DataAccessException {
        String str = "SELECT * FROM events ORDER BY RAND() LIMIT 1";
        List<Event> event = jdbcTemplate().query(str, new Object[]{},
                (rs, i) -> new Event(rs.getInt("eventId"), rs.getString("eventName"),
                                     rs.getString("eventDesc"), rs.getString("eventLocSt1"),
                                     rs.getString("eventLocSt2"), rs.getString("eventLocCity"),
                                     rs.getString("eventLocPost"), rs.getDate("eventStartDate"),
                                     rs.getDate("eventEndDate"),rs.getTime("eventStartTime"),
                                     rs.getTime("eventEndTime"), rs.getInt("eventOrganiser"),
                                     rs.getDate("dateCreated"), rs.getInt("maxTeamSize"),
                                     rs.getString("eventColour") )
        );
        return event.get(0);
    }

}
