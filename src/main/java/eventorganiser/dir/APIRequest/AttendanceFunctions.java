package eventorganiser.dir.APIRequest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eventorganiser.dir.DBMethods.DBClasses.AttendanceRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AttendanceFunctions {

    public JsonNode getUserAttendanceRecordsFromUserIdFromTheDatabase( Integer userId)throws IOException{

        String uri = "http://localhost:8080/api/get/user/attendance?userId=" + userId;
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        ResponseEntity<String> response = restTemplate.getForEntity(uri,String.class);
        return mapper.readTree(response.getBody());

    }


    public String getEventAttendanceRecordsByEventIdFromTheDatabase( HttpServletRequest request){
        // Getting the username from the session
        HttpSession s = request.getSession();
        String  userId =  (String)s.getAttribute("SESSION_USERID");

        String uri = "http://localhost:8080/api/get/event/attendance?userId=" + userId;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        return result;
    }


    public String getAllAttendanceRecordsFromTheDatabase(){
        String uri = "http://localhost:8080/api/get/attendance/all";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        return result;
    }

    public String getRandomAttendanceRecordFromTheDatabase(){
        String uri = "http://localhost:8080/api/get/attendance/random";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        return result;
    }


    public String getAttendanceRecordIdFromUserIdAndEventIdFromTheDatabase( HttpServletRequest request, String eventId){
        // Getting the username from the session

        HttpSession s = request.getSession();
        String  userId =  (String)s.getAttribute("SESSION_USERID");

        String uri = "http://localhost:8080/api/get/attendance?userId=" + userId +"&eventId=" + eventId;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        return result;
    }

    public static JsonNode getAttendanceRecordFromUserIdAndEventIdFromTheDatabase(
            HttpServletRequest request, String eventId) throws IOException {
        // Getting the username from the session
        HttpSession s = request.getSession();
        String  userId =  (String)s.getAttribute("SESSION_USERID");
        String uri = "http://localhost:8080/api/get/attendance/record?userId=" + userId +"&eventId=" + eventId;
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        return mapper.readTree(response.getBody());
    }

    public ModelAndView addAttendanceStatusToEventPageModelView(ModelAndView mv,
        JsonNode evntJson, HttpServletRequest request) throws IOException {
        try {   // Trying to retrieve usr attendance status
            //System.out.println("AttendanceFUnctions: " + 1);
            Integer eventId = evntJson.path("id").intValue();
            //System.out.println("AttendanceFUnctions: " + 2);
            JsonNode attdRcrd = AttendanceFunctions.getAttendanceRecordFromUserIdAndEventIdFromTheDatabase(request, eventId.toString());
            //System.out.println(attdRcrd);
            if (attdRcrd == null) { throw new NumberFormatException("No results from DB"); };
            //System.out.println("AttendanceFUnctions: " + 3);
            Integer attendance = attdRcrd.path("response").intValue();
            Integer attendanceId = attdRcrd.path("attendanceId").intValue();
            //System.out.println("AttendanceFUnctions: " + 4);
            mv.addObject("attendance", attendance.toString());
            mv.addObject("attendanceId", attendanceId.toString());
            //System.out.println("AttendanceFUnctions: " + 5);
            //System.out.println("ATTENDANCE ADDED");
        } catch (Exception e) {  // Assuming that the user probably doesn't have any attendnace status for this event
            //System.out.println("No attendance record available");
            mv.addObject("attendance", "0");
        }
        return mv;
    }

        public boolean updateAttdRecordOnDB(String userId, String eventId, String response, String attendanceId) {
            RestTemplate restTemplate = new RestTemplate();
            String uri = "http://localhost:8080/api/post/attendance/update" + "?userId=" + userId +
                    "&eventId=" + eventId + "&response=" + response + "&attendanceId=" + attendanceId;
            String res = restTemplate.getForObject(uri, String.class);
            return Boolean.parseBoolean(res);
        }

        public boolean postAttdRecordToDB(String userId, String eventId, String response) {
            RestTemplate restTemplate = new RestTemplate();
            String uri = "http://localhost:8080/api/post/attendance/new" +
                    "?userId=" + userId +"&eventId=" + eventId + "&response=" + response;
            String res = restTemplate.getForObject(uri, String.class);
            return Boolean.parseBoolean(res);
     }
}
