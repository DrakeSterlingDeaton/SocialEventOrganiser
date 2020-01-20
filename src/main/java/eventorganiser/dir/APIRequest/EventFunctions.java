package eventorganiser.dir.APIRequest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import eventorganiser.dir.API.EventsAPI;
import eventorganiser.dir.DBMethods.DBClasses.Event;

import eventorganiser.dir.DBMethods.EventQueries;
import eventorganiser.dir.DBMethods.TeamQueries;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.thymeleaf.util.ArrayUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EventFunctions {

    //**************************************
    // Functions to communicate with the API
    //**************************************

    public JsonNode getEventsFromDatabase() throws IOException {

        String uri = "http://localhost:8080/api/get/events";
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        ResponseEntity<String> response = restTemplate.getForEntity(uri,String.class);
        return mapper.readTree(response.getBody());

    }

    public JsonNode getEventAttendeesFromEventId(int eventId) throws IOException{
        String uri = "http://localhost:8080/api/get/event/attendees?eventId=" + eventId;
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        return mapper.readTree(response.getBody());
    }

    public JsonNode getEventsInChronologicalOrderFromDatabase() throws IOException {

        String uri = "http://localhost:8080/api/get/events?sortBy=chronological";
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        ResponseEntity<String> response = restTemplate.getForEntity(uri,String.class);
        return mapper.readTree(response.getBody());
    }

    public JsonNode getEventsInChronologicalOrderAndUserIdFromDatabase(Integer userId) throws IOException {

        String uri = "http://localhost:8080/api/get/events?sortBy=chronological&userId=" + userId;
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        ResponseEntity<String> response = restTemplate.getForEntity(uri,String.class);
        return mapper.readTree(response.getBody());
    }

    public ModelAndView getEventsCreatedbyUserId(ModelAndView mv, Integer userId) throws IOException, ParseException{
        String uri = "http://localhost:8080/api/get/events/created?userId=" + userId;
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        //Makes the request to the API
        ResponseEntity<String> response = restTemplate.getForEntity(uri,String.class);

        return addEventDataToDeletePageModelView(mv, mapper.readTree(response.getBody()));

    }



    public JsonNode getEventByIdFromDatabase(int id) throws IOException {

        String uri = "http://localhost:8080/api/get/event?id=" + id;
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        return mapper.readTree(response.getBody());
    }

    public JsonNode getEventIdByNameAndDateFromDatabase(String name, String date) throws IOException {

        String uri = "http://localhost:8080/api/get/eventId?name=" + name + "&date=" + date;
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        ResponseEntity<String> response = restTemplate.getForEntity(uri,String.class);
        return mapper.readTree(response.getBody());

    }

    public JsonNode getEventAttendanceByIdFromDatabase(int id) throws IOException {

        String uri = "http://localhost:8080/api/get/eventAttendance?id=" + id;
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        ResponseEntity<String> response = restTemplate.getForEntity(uri,String.class);
        return mapper.readTree(response.getBody());
    }

    public JsonNode getEventTeamsByIdFromDatabase(int id) throws IOException {

        String uri = "http://localhost:8080/api/get/eventTeams?id=" + id;
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        ResponseEntity<String> response = restTemplate.getForEntity(uri,String.class);
        return mapper.readTree(response.getBody());
    }


    public static String makeEventLocGoogleMapsApiQryStr(JsonNode evntJson) {

        // Base URI
        String uriBase = "https://maps.googleapis.com/maps/api/geocode/json?address=";

        // Convert event loc details in 'address' query parameter. Example of final format needed for 'address' param for API is: address=4+Blackberry+Hill+null+Bristol+BS16+1DB&
        ArrayList<String> uriQryLi = new ArrayList<>();
        uriQryLi.add(evntJson.path("eventLocSt1").textValue() + "+");
        if ( evntJson.path("eventLocSt2").textValue().isBlank() ) {
            uriQryLi.add(evntJson.path("evntLocSt2").textValue() + "+"); }
        uriQryLi.add(evntJson.path("eventLocCity").textValue() + "+");
        uriQryLi.add(evntJson.path("eventLocPost").textValue());
        String address = String.join("", uriQryLi);
        String addressStr = address.replace(" ", "+");

        // Concatenating query string
        String uriQry = "?address=" + addressStr + "&key=AIzaSyDa2fKq4AtIZchKPBrETMr7MZF-2CvyA44";

        // returning full API req in the following format: https://maps.googleapis.com/maps/api/geocode/json?address=<address>&key=<key>
        return uriBase + uriQry;
    }

    public static String getEventLocAndLngFromGoogleMapsApi(String uri) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);
        JsonNode locJson = mapper.readTree(response.getBody());
        String lat = locJson.findPath("lat").toString();
        String lng= locJson.findPath("lng").toString();
        return "?lat=" + lat + "&lng=" + lng;
    }

    public ModelAndView addMapDatatoEventPageModelView(ModelAndView mv, JsonNode evntJson) {
        // Establishing connection to google maps API
        System.out.println(evntJson);
        try {
            String uri = EventFunctions.makeEventLocGoogleMapsApiQryStr(evntJson);
            String evntLocLatAndLng = EventFunctions.getEventLocAndLngFromGoogleMapsApi(uri);
            mv.addObject("mapQueryStr", evntLocLatAndLng);
        } catch (Exception e) {
            System.out.println(e);
        }
        return mv;
    }

    //***********************************
    // Functions to add data to the view
    //************************************




    //Delete Page
    public ModelAndView addEventDataToDeletePageModelView(ModelAndView mv, JsonNode listOfEventsCreated) throws ParseException{
        ArrayList<Integer> eventId = new ArrayList<Integer>();
        ArrayList<String> eventTitles = new ArrayList<String>();
        ArrayList<Integer> eventDateCreated = new ArrayList<Integer>();

        for (JsonNode listOfEventsCreatedJson : listOfEventsCreated ) {
            eventId.add(listOfEventsCreatedJson.path("id").intValue());
            eventTitles.add(listOfEventsCreatedJson.path("title").textValue());
            eventDateCreated.add(listOfEventsCreatedJson.path("dateCreated").intValue());
        }

        // Adding event data to view
        mv.addObject("eventTitles", eventTitles);
        mv.addObject("eventId", eventId);
        mv.addObject("eventDateCreated", eventDateCreated);

        return mv;
    }


    // Feed Page
    public ModelAndView addEventDataToFeedPageModelView(ModelAndView mv, JsonNode liOfEvnts, JsonNode liOfEvntUserAttandance) throws ParseException {

        //Create variables to retrieve data
        ArrayList<String> eventTitles = new ArrayList<String>();
        ArrayList<String> eventDescriptions = new ArrayList<String>();
        ArrayList<String> eventStartDate = new ArrayList<String>();
        ArrayList<String> eventEndDate = new ArrayList<String>();
        ArrayList<String> eventStartTime = new ArrayList<String>();
        ArrayList<String> eventEndTime = new ArrayList<String>();
        ArrayList<String> eventStart = new ArrayList<String>();
        ArrayList<String> eventEnd = new ArrayList<String>();
        ArrayList<String> eventLocSt1 = new ArrayList<String>();
        ArrayList<String> eventLocSt2 = new ArrayList<String>();
        ArrayList<String> eventLocCity = new ArrayList<String>();
        ArrayList<String> eventLocPost = new ArrayList<String>();
        ArrayList<Integer> eventMaxTeamSize = new ArrayList<Integer>();
        ArrayList<Integer> eventId = new ArrayList<Integer>();
        ArrayList<Integer> eventId_buffer = new ArrayList<Integer>();
        ArrayList<Integer> eventId_buffers = new ArrayList<Integer>();
        ArrayList<Integer> AttendanceUserEventId = new ArrayList<Integer>();
        ArrayList<Integer> allAttendanceId = new ArrayList<Integer>();
        ArrayList<Integer> allAttendanceId_buffer = new ArrayList<Integer>();
        ArrayList<Integer> allAttendanceUserResponse_buffer = new ArrayList<Integer>();
        ArrayList<Integer> allAttendanceUserResponse = new ArrayList<Integer>();
        int ii = 0;
        String dateFormater; // to format the time from yyyy-mm-dd to dd-mm-yyyy

        // Populate variables from attendance table
        for (JsonNode evntAttendanceJson : liOfEvntUserAttandance ) {
            AttendanceUserEventId.add(evntAttendanceJson.path("eventId").intValue());
            allAttendanceUserResponse_buffer.add(evntAttendanceJson.path("response").intValue());
            allAttendanceId_buffer.add(evntAttendanceJson.path("attendanceId").intValue());
        }

        // Populate eventId with all events that exits
        for (JsonNode evntJson : liOfEvnts ) {
            eventId_buffers.add(evntJson.path("id").intValue());
        }

        //attribute data value to specific variables
        for (JsonNode evntJson : liOfEvnts ) {
            eventId_buffer.add(evntJson.path("id").intValue());

            // If there are events with responses
            if((AttendanceUserEventId.contains(eventId_buffer.get(eventId_buffer.size()-1)))){
                ii = eventId_buffer.get(eventId_buffer.size()-1); // shows the eventId that has a response
            }

            // Populates variables if they don't have a response yet or they have a response equals to "not going" (Vlaue: 2)
            if ( (!(AttendanceUserEventId.contains(eventId_buffer.get(eventId_buffer.size()-1)))) || (AttendanceUserEventId.contains(eventId_buffer.get(eventId_buffer.size()-1)) && (allAttendanceUserResponse_buffer.get(AttendanceUserEventId.indexOf(ii)) == 2)) ){

                // Convert the date format from  yyyy-MM-dd to dd-MM-yyyy
                dateFormater = evntJson.path("eventStartDate").textValue();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date d = sdf.parse(dateFormater);
                sdf.applyPattern("dd-MM-yyyy");
                dateFormater = sdf.format(d);


                eventTitles.add(evntJson.path("title").textValue());
                eventDescriptions.add(evntJson.path("description").textValue());
                eventLocSt1.add(evntJson.path("eventLocSt1").textValue());
                eventLocSt2.add(evntJson.path("eventLocSt2").textValue());
                eventLocCity.add(evntJson.path("eventLocCity").textValue());
                eventLocPost.add(evntJson.path("eventLocPost").textValue());
                eventStartDate.add(dateFormater);
                eventEndDate.add(evntJson.path("eventEndDate").textValue());
                eventStartTime.add(evntJson.path("eventStartTime").textValue());
                eventEndTime.add(evntJson.path("eventEndTime").textValue());
                eventStart.add(evntJson.path("start").textValue());
                eventEnd.add(evntJson.path("end").textValue());
                eventMaxTeamSize.add(evntJson.path("maxTeamSize").intValue());
                eventId.add(evntJson.path("id").intValue());

                //If there is an event it is a event with a response
                if(AttendanceUserEventId.contains(eventId_buffer.get(eventId_buffer.size()-1))){
                    allAttendanceUserResponse.add(allAttendanceUserResponse_buffer.get(AttendanceUserEventId.indexOf(ii)));
                    allAttendanceId.add( allAttendanceId_buffer.get(AttendanceUserEventId.indexOf(ii)));
                }else{
                    allAttendanceUserResponse.add(0);
                    allAttendanceId.add(0);
                }

            }

        }

        // Adding event data to view
        mv.addObject("eventTitles", eventTitles);
        mv.addObject("eventDescriptions", eventDescriptions);
        mv.addObject("eventLocSt1", eventLocSt1);
        mv.addObject("eventLocSt2", eventLocSt2);
        mv.addObject("eventLocPost", eventLocPost);
        mv.addObject("eventLocCity", eventLocCity);
        mv.addObject("eventStartDate", eventStartDate);
        mv.addObject("eventEndDate", eventEndDate);
        mv.addObject("eventStartTime", eventStartTime);
        mv.addObject("eventEndTime", eventEndTime);
        mv.addObject("eventStart", eventStart);
        mv.addObject("eventEnd", eventEnd);
        mv.addObject("eventMaxTeamSize", eventMaxTeamSize);
        mv.addObject("eventId", eventId);
        mv.addObject("allAttendanceUserResponse",  allAttendanceUserResponse);
        mv.addObject("allAttendanceId", allAttendanceId);


        return mv;
    }

    //Feed Page for specific Users
    public ModelAndView addEventDataToFeedForUserPageModelView(ModelAndView mv, JsonNode liOfEvnts, JsonNode liOfEvntUserAttandance) throws ParseException {
        //Create variables to retrieve data
        ArrayList<String> UserEventTitles = new ArrayList<String>();
        ArrayList<String> UserEventDescriptions = new ArrayList<String>();
        ArrayList<String> UserEventStartDate = new ArrayList<String>();
        ArrayList<String> UserEventEndDate = new ArrayList<String>();
        ArrayList<String> UserEventStartTime = new ArrayList<String>();
        ArrayList<String> UserEventEndTime = new ArrayList<String>();
        ArrayList<String> UserEventStart = new ArrayList<String>();
        ArrayList<String> UserEventEnd = new ArrayList<String>();
        ArrayList<String> UserEventLocSt1 = new ArrayList<String>();
        ArrayList<String> UserEventLocSt2 = new ArrayList<String>();
        ArrayList<String> UserEventLocCity = new ArrayList<String>();
        ArrayList<String> UserEventLocPost = new ArrayList<String>();
        ArrayList<Integer> UserEventMaxTeamSize = new ArrayList<Integer>();
        ArrayList<Integer> UserEventId = new ArrayList<Integer>();
        ArrayList<Integer> EventId = new ArrayList<Integer>();
        ArrayList<Integer> AttendanceUserEventId = new ArrayList<Integer>();
        ArrayList<Integer> AttendanceUserResponse = new ArrayList<Integer>();
        ArrayList<Integer> AttendanceId = new ArrayList<Integer>();
        ArrayList<Integer> UserAttendanceId = new ArrayList<Integer>();
        ArrayList<Integer> UserAttendanceUserResponse = new ArrayList<Integer>();
        String dateFormater;


        // Populate variables from attendance table
        for (JsonNode evntAttendanceJson : liOfEvntUserAttandance ) {
            AttendanceUserEventId.add(evntAttendanceJson.path("eventId").intValue());
            AttendanceUserResponse.add(evntAttendanceJson.path("response").intValue());
            AttendanceId.add(evntAttendanceJson.path("attendanceId").intValue());
        }

        for (JsonNode evntJson : liOfEvnts ) {

            // Convert the date format from  yyyy-MM-dd to dd-MM-yyyy
            dateFormater = evntJson.path("eventStartDate").textValue();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = sdf.parse(dateFormater);
            sdf.applyPattern("dd-MM-yyyy");
            dateFormater = sdf.format(d);

            EventId.add(evntJson.path("id").intValue());

            for(int i = 0; i < AttendanceUserEventId.size(); i++) {
                // if the events has a response or the response is different from "not going" (value: 2)
                if ((AttendanceUserEventId.get(i) == EventId.get(EventId.size()-1)) && (AttendanceUserResponse.get(AttendanceUserEventId.indexOf(AttendanceUserEventId.get(i)))!=2)) {

                    UserEventTitles.add(evntJson.path("title").textValue());
                    UserEventDescriptions.add(evntJson.path("description").textValue());
                    UserEventLocSt1.add(evntJson.path("eventLocSt1").textValue());
                    UserEventLocSt2.add(evntJson.path("eventLocSt2").textValue());
                    UserEventLocCity.add(evntJson.path("eventLocCity").textValue());
                    UserEventLocPost.add(evntJson.path("eventLocPost").textValue());
                    UserEventStartDate.add(dateFormater);
                    UserEventEndDate.add(evntJson.path("eventEndDate").textValue());
                    UserEventStartTime.add(evntJson.path("eventStartTime").textValue());
                    UserEventEndTime.add(evntJson.path("eventEndTime").textValue());
                    UserEventStart.add(evntJson.path("start").textValue());
                    UserEventEnd.add(evntJson.path("end").textValue());
                    UserEventMaxTeamSize.add(evntJson.path("maxTeamSize").intValue());
                    UserEventId.add(evntJson.path("id").intValue());
                    UserAttendanceUserResponse.add(AttendanceUserResponse.get(i));
                    UserAttendanceId.add(AttendanceId.get(i));
                }
            }

        }

        // Adding event data to view
        mv.addObject("UserEventTitles", UserEventTitles);
        mv.addObject("UserEventDescriptions", UserEventDescriptions);
        mv.addObject("UserEventLocSt1", UserEventLocSt1);
        mv.addObject("UserEventLocSt2", UserEventLocSt2);
        mv.addObject("UserEventLocPost", UserEventLocPost);
        mv.addObject("UserEventLocCity", UserEventLocCity);
        mv.addObject("UserEventStartDate", UserEventStartDate);
        mv.addObject("UserEventEndDate", UserEventEndDate);
        mv.addObject("UserEventStartTime", UserEventStartTime);
        mv.addObject("UserEventEndTime", UserEventEndTime);
        mv.addObject("UserEventStart", UserEventStart);
        mv.addObject("UserEventEnd", UserEventEnd);
        mv.addObject("UserEventMaxTeamSize", UserEventMaxTeamSize);
        mv.addObject("UserEventId", UserEventId);
        mv.addObject("UserAttendanceUserResponse", UserAttendanceUserResponse);
        mv.addObject("UserAttendanceId", UserAttendanceId);

        return mv;
    }




    //Event Page
    public ModelAndView addEventDataToEventPageModelView(ModelAndView mv, JsonNode evntJson) {

        // Creating event Date & Time Strings
        String evntStrt = evntJson.path("eventStartDate").textValue() + " @ " + evntJson.path("eventStartTime").textValue();
        String evntEnd = evntJson.path("eventEndDate").textValue() + " @ " + evntJson.path("eventEndTime").textValue();

        // Adding event data to view
        mv.addObject("title", evntJson.path("title").textValue());
        mv.addObject("start", evntStrt);
        mv.addObject("end", evntEnd);
        mv.addObject("description", evntJson.path("description").textValue());
        mv.addObject("eventLocSt1", evntJson.path("eventLocSt1").textValue());
        mv.addObject("eventLocSt2", evntJson.path("eventLocSt2").textValue());
        mv.addObject("eventLocCity", evntJson.path("eventLocCity").textValue());
        mv.addObject("eventLocPost", evntJson.path("eventLocPost").textValue());

        return mv;
    }

    public boolean postNewEventToDB(String eventTitle, String eventDescription, String address1, String address2, String city,
                                    String postcode, String startDate, String endDate, String startTime, String endTime, int poster, int maxTeamSize, String eventColour) throws ParseException {

        RestTemplate restTemplate = new RestTemplate();


        Date sd = new SimpleDateFormat("yyyy-MM-dd").parse(startDate);
        Date ed = new SimpleDateFormat("yyyy-MM-dd").parse(endDate);

        Date st = new SimpleDateFormat("HH:mm:ss").parse(startTime.substring(0, startTime.length() - 3) + ":00"); //Fix for time format being sent from the input field as 10:00 +AM
        Date et = new SimpleDateFormat("HH:mm:ss").parse(endTime.substring(0, endTime.length() - 3) + ":00"); //Fix for time format being sent from the input field as 10:00 +AM
        Time startT = new Time(st.getTime());
        Time endT = new Time(et.getTime());

        if(ed.before(sd) || (et.before(st) && sd.equals(ed))){
            return false;
        }

        Date dCreated = Date.from(Instant.now());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String stringDCreated = formatter.format(dCreated);

        eventColour = eventColour.substring(1);

        String uri = "http://localhost:8080/api/post/event/new" + "?eventName=" + eventTitle + "&eventDesc=" + eventDescription +
                "&eventStartDate=" + startDate + "&eventEndDate=" + endDate + "&eventStartTime=" + startT + "&eventEndTime=" +
                endT + "&eventLocSt1=" + address1 + "&eventLocSt2=" + address2 + "&eventLocCity=" + city + "&eventLocPost=" + postcode +
                "&eventOrganiser=" + poster + "&dateCreated=" + stringDCreated + "&maxTeamSize=" + maxTeamSize + "&eventColour=" + eventColour;

        String res = restTemplate.getForObject(uri,String.class);
        return Boolean.parseBoolean(res);
    }
}
