package eventorganiser.dir.API;

import eventorganiser.dir.APIRequest.AttendanceFunctions;
import eventorganiser.dir.DBMethods.*;
import eventorganiser.dir.DBMethods.DBClasses.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
public class AttendanceAPI {

    private AttendanceQueries attdQrys;
    private UserQueries UserQrys;

    public AttendanceAPI() { }

    @Autowired
    public AttendanceAPI(AttendanceQueries a, UserQueries u) {
        UserQrys = u;
        attdQrys = a;
    }

    //get User Attendance
    @GetMapping("api/get/user/attendance")
    public Object APIgetUserAttendance(@RequestParam(name="userId", required=true) String userId) {
        return UserQrys.getUserAttendanceRecordsFromUserId(userId);
    }

    //Get Events from user attendance
    @GetMapping("api/get/user/attending")
    public Object apiGetUserEventsAttending(@RequestParam(name="userId") int userId){
        return attdQrys.getEventsUserIsAttendingByUserId(userId);
    }

    //get Event Attendance
    @GetMapping("/api/get/event/attendance")
    public Object APIgetEventAttendanceById(@RequestParam(name="eventId", required=true) String eventId) {
        int eventIdInt =  Integer.parseInt(eventId);
        return attdQrys.getEventAttendanceRecordsByEventId(eventIdInt);
    }


    //get All attendance
    @GetMapping("/api/get/attendance/all")
    public Object APIgetAllAttendanceRecords() {
        return attdQrys.getAllAttendanceRecords();
    }

    //get  random Attendance
    @GetMapping("/api/get/attendance/random")
    public Object APIgetRandomAttendanceRecord() {
        return attdQrys.getRandomAttendanceRecord();
    }

    //get Attendance Record Id
    @GetMapping("/api/get/attendance")
    public Object APIgetAttendanceRecordIdFromUserIdAndEventId(@RequestParam(name="userId", required=true) String userId, @RequestParam(name="eventId", required=true) String eventId) {
        int userIdInt =  Integer.parseInt(userId);
        int eventIdInt =  Integer.parseInt(eventId);
        return attdQrys.getAttendanceRecordIdFromUserIdAndEventId(userIdInt, eventIdInt);
    }

    //get Attendance Record
    @GetMapping("/api/get/attendance/record")
    public Object APIgetAttendanceRecordFromUserIdAndEventId(@RequestParam(name="userId", required=true) String userId, @RequestParam(name="eventId", required=true) String eventId) {
        int userIdInt =  Integer.parseInt(userId);
        int eventIdInt =  Integer.parseInt(eventId);
        return attdQrys.getAttendanceRecordFromUserIdAndEventId(userIdInt, eventIdInt);
    }

    @RequestMapping(value="/api/post/attendance/update", method=RequestMethod.GET)
    public boolean updateAttendance(HttpServletRequest req,
                                    @RequestParam(name="userId") String userId,
                                    @RequestParam(name="eventId") String eventId,
                                    @RequestParam(name="response") String response,
                                    @RequestParam(name="attendanceId") String attendanceId){
        System.out.println(response +" e attendanceId:" + attendanceId);
        return 1 == attdQrys.editAttendanceRecordUserResponseToEvent(Integer.parseInt(attendanceId),
                                                                     Integer.parseInt(response) ); // returns true if DB post was successful
    }

    @RequestMapping(value="/api/post/attendance/new", method=RequestMethod.GET)
    public boolean postAttendance(HttpServletRequest req,
                                  @RequestParam(name="userId") String userId,
                                  @RequestParam(name="eventId") String eventId,
                                  @RequestParam(name="response") String response) {
        return 1 == attdQrys.postNewAttendanceRecord(Integer.parseInt(userId), Integer.parseInt(eventId),
                                                     Integer.parseInt(response)); // returns true if DB post was successful
    }

}
