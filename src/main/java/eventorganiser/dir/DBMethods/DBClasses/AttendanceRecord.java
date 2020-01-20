package eventorganiser.dir.DBMethods.DBClasses;

import java.util.ArrayList;
import java.util.List;

public class AttendanceRecord {

    private int attendanceId;
    private int userId;
    private int eventId;
    private int response;
    private int teamId;

    public AttendanceRecord() {
    }

    public AttendanceRecord(int attendanceId, int userId, int eventId, int response, int teamId) {
        this.attendanceId = attendanceId;
        this.userId = userId;
        this.eventId = eventId;
        this.response = response;
        this.teamId = teamId;
    }

    public int getAttendanceId() {
        return attendanceId;
    }

    public int getUserId() {
        return userId;
    }

    public int getEventId() {
        return eventId;
    }

    public int getResponse() {
        return response;
    }

    public int getTeamId() {
        return teamId;
    }

    public List<Object> getListOfAllInfo() { // Returns in order: [attendanceId, userId, eventId, response. teamId]
        List<Object> li = new ArrayList<>();
        li.add(attendanceId);
        li.add(userId);
        li.add(eventId);
        li.add(response);
        li.add(teamId);
        return li;
    }

    public void setAttendanceId(int attendanceId) {
        this.attendanceId = attendanceId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public void setResponse(int response) {
        this.response = response;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public void setAllFields( int attendanceId, int userId, int eventId, int response, int teamId ) {
        this.attendanceId = attendanceId;
        this.userId = userId;
        this.eventId = eventId;
        this.response = response;
        this.teamId = teamId;
    }

}