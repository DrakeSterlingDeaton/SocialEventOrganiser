package eventorganiser.dir.DBMethods;

import eventorganiser.dir.DBMethods.DBClasses.AttendanceRecord;
import eventorganiser.dir.DBMethods.DBClasses.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class AttendanceQueries extends DBQueries {

    @Autowired
    public AttendanceQueries(JdbcTemplate jdbctemplate) {
        super(jdbctemplate);
    }

    /////////////////////////////////////// GET ALL METHODS ///////////////////////////////////////

    public List<AttendanceRecord> getAllAttendanceRecords() throws DataAccessException {
        return jdbcTemplate().query("SELECT * FROM attendance", new Object[]{},
                (rs, i) -> new AttendanceRecord(rs.getInt("attendanceId"), rs.getInt("userId"),
                        rs.getInt("response"), rs.getInt("eventId"),
                        rs.getInt("teamId")) );
    }

    /////////////////////////////////////// GET ID METHODS ///////////////////////////////////////

    public Integer getAttendanceRecordIdFromUserIdAndEventId(int userId, int eventId) throws DataAccessException {
        try {
            String str = String.format("SELECT attendanceId FROM attendance WHERE userId = \"%s\" AND eventId = \"%s\" LIMIT 1", userId, eventId);
            List<Integer> attendanceId = jdbcTemplate().query(str, new Object[]{}, (rs, i) -> rs.getInt("attendanceId"));
            return attendanceId.get(0);
        } catch (IndexOutOfBoundsException e) { // encountered if no row is found in DB
            return null;
        }
    }

    /////////////////////////////////////// GET EVENT DETAILS METHODS ///////////////////////////////////////

    public List<AttendanceRecord> getEventAttendanceRecordsByEventId(int eventId) throws DataAccessException {
        String str = String.format("SELECT * FROM attendance WHERE eventId = \"%s\"", eventId);
        List<AttendanceRecord> attendanceLi = jdbcTemplate().query(str, new Object[]{},
                (rs, i) -> new AttendanceRecord(rs.getInt("attendanceId"), rs.getInt("userId"),
                        rs.getInt("response"), rs.getInt("eventId"),
                        rs.getInt("teamId")) );
        if ( attendanceLi.isEmpty() ) { return null; }
        else { return attendanceLi; }
    }

    public AttendanceRecord getAttendanceRecordFromUserIdAndEventId(int userId, int eventId) throws DataAccessException {
        try {
            String str = String.format("SELECT * FROM attendance WHERE userId = \"%s\" AND eventId = \"%s\" LIMIT 1", userId, eventId);
            List<AttendanceRecord> attendanceRecordLi = jdbcTemplate().query(str, new Object[]{},
                    (rs, i) -> new AttendanceRecord(
                            rs.getInt("attendanceId"), rs.getInt("userId"),
                            rs.getInt("eventId"), rs.getInt("response"),
                            rs.getInt("teamId"))
            );
            return attendanceRecordLi.get(0);
        } catch (IndexOutOfBoundsException e) { // encountered if no row is found in DB
            return null;
        }
    }

    public List<Event> getEventsUserIsAttendingByUserId(int userId){
        try{
            String str = "select * from events inner join attendance on attendance.eventId = events.eventId and (attendance.response = 1 or attendance.response = 3) and attendance.userId = " + userId + " group by events.eventId";
            List<Event> eventsLi = jdbcTemplate().query(str, new Object[]{},
                    (rs, i) -> new Event(rs.getInt("eventId"), rs.getString("eventName"),
                            rs.getString("eventDesc"), rs.getString("eventLocSt1"),
                            rs.getString("eventLocSt2"), rs.getString("eventLocCity"),
                            rs.getString("eventLocPost"), rs.getDate("eventStartDate"),
                            rs.getDate("eventEndDate"),rs.getTime("eventStartTime"),
                            rs.getTime("eventEndTime"), rs.getInt("eventOrganiser"),
                            rs.getDate("dateCreated"), rs.getInt("maxTeamSize"),
                            rs.getString("eventColour") )
            );
            return eventsLi;
        }catch(IndexOutOfBoundsException e){
            return null;
        }
    }

    /////////////////////////////////////// POST EVENT METHODS ///////////////////////////////////////

    public int postNewAttendanceRecord(int userId, int eventId, int response) throws DataAccessException {
        String str = "INSERT INTO attendance(userId, eventId, response)" +
                "VALUES(?,?,?)";
        return jdbcTemplate().update(str, userId, eventId, response);
    }

    /////////////////////////////////////// DELETE EVENT METHODS ///////////////////////////////////////

    public int deleteAttendanceRecordById(int attendanceId){
        String str = "DELETE FROM attendance WHERE attendanceId = ?";
        return jdbcTemplate().update(str, attendanceId);
    }

    /////////////////////////////////////// EDITING ATTENDANCE METHODS ///////////////////////////////////////

    public Integer editAttendanceRecordUserResponseToEvent(int attendanceId, int response) throws DataAccessException {
        String str = "UPDATE attendance SET response = ? WHERE attendanceId = ? ";
        return jdbcTemplate().update(str, response, attendanceId);
    }

    public Integer editAttendanceRecordUserTeam(int attendanceId, int newTeamId) throws DataAccessException {
        String str = "UPDATE attendance SET teamId = ? WHERE attendanceId = ? ";
        return jdbcTemplate().update(str, newTeamId, attendanceId);
    }

    /////////////////////////////////////// GET RANDOM ROWS METHODS ///////////////////////////////////////

    public AttendanceRecord getRandomAttendanceRecord() throws DataAccessException {
        String str = "SELECT * FROM attendance ORDER BY RAND() LIMIT 1";
        List<AttendanceRecord> record = jdbcTemplate().query(str, new Object[]{},
                (rs, i) ->
                        new AttendanceRecord(rs.getInt("attendanceId"), rs.getInt("userId"),
                                rs.getInt("response"), rs.getInt("eventId"),
                                rs.getInt("teamId")) );
        return record.get(0);
    }

}
