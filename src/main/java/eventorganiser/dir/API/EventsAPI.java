package eventorganiser.dir.API;

import eventorganiser.dir.DBMethods.*;
import eventorganiser.dir.DBMethods.DBClasses.Event;
import eventorganiser.dir.DBMethods.DBClasses.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class EventsAPI {

    private EventQueries EventQrys;
    private GroupQueries GroupQrys;
    private TeamQueries TeamQrys;
    private UserQueries UserQrys;
    private AttendanceQueries AttdQrys;

    @Autowired
    public EventsAPI(EventQueries e, GroupQueries g, TeamQueries t,
                             UserQueries u, AttendanceQueries a) {
        AttdQrys = a;
        EventQrys = e;
        GroupQrys = g;
        TeamQrys = t;
        UserQrys = u;
    }

    @GetMapping("/api/get/events")
    public Object getEvents(@RequestParam(value="sortBy", defaultValue = "") String sort, @RequestParam(value="userId", defaultValue = "") Integer userId){
        //TODO
        List<Event> allEvents = null;

        if(sort.isEmpty() || sort.isBlank()) {
            allEvents = EventQrys.getAllEvents();
        }
        else if(sort.equals("chronological") && userId == null){
            allEvents = EventQrys.getAllEventsInChronologicalOrder();
            System.out.println("");
        }else if (sort.equals("chronological") && userId != null){
            allEvents = UserQrys.getFutureUserEventsFromUserIdInChronologicalOrder(userId);
        }
        return allEvents;
    }

    @GetMapping("/api/get/event")
    public Object getEventById(@RequestParam(value="id", required = false) Integer id) {
        //TODO
        Object event = null;
        if(id == null)
            event = EventQrys.getRandomEvent();
        else
            event = EventQrys.getEventDetailsByEventId(id);
        return event;
    }

    @GetMapping("/api/get/eventId")
    public Object getEventIDByNameAndDate(@RequestParam(value="name") String name, @RequestParam(value="date") String date) {
        //TODO
        Object id = EventQrys.getEventIdFromEventNameAndDate(name,date);
        return id;
    }

    @GetMapping("/api/get/events/created")
    public Object getEventsCreatedbyUserId(@RequestParam(value="userId") Integer userId){
        Object allEventsCreatedbyUser = EventQrys.getEventsCreatedbyUserId(userId);
        return allEventsCreatedbyUser;
    }

    @GetMapping("/api/get/eventAttendance")
    @ResponseBody
    public Object getEventAttendanceById(@RequestParam(value="id") Integer id) {
        //TODO
        Object eventAttendance = AttdQrys.getEventAttendanceRecordsByEventId(id);
        return eventAttendance;
    }

    @GetMapping("api/get/event/attendees")
    public Object getEventAttendeesByEventId(@RequestParam(value="eventid") Integer id){
        return EventQrys.getEventAttendeesByEventId(id);
    }

    @GetMapping("/api/post/event/team/update")
    @ResponseBody
    public int updateTeam(@RequestParam(value="userId") Integer userId,
                              @RequestParam(value="eventId") Integer eventId,
                              @RequestParam(value="teamId") Integer teamId){
        return EventQrys.updateUserTeam(userId,eventId,teamId);
    }


    @GetMapping("/api/get/event/teams")
    public Object getEventTeams(@RequestParam(value="eventid") Integer id) {

        return TeamQrys.getAllTeamsInEventByEventId(id);
    }


    ///////////////////////    POST     ////////////////////////////////

    // Catches incorrect format when user inputs date and time
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        binder.registerCustomEditor(Time.class, new CustomDateEditor(timeFormat, false));
    }

    @RequestMapping(value="/api/post/event/new", method=RequestMethod.GET)
    public boolean submitEvent(HttpServletRequest req,
                               @RequestParam(name="eventName") String eventTitle,
                               @RequestParam(name="eventDesc") String eventDescription,
                               @RequestParam(name="eventLocSt1") String address1,
                               @RequestParam(name="eventLocSt2") String address2,
                               @RequestParam(name="eventLocCity") String city,
                               @RequestParam(name="eventLocPost") String postcode,
                               @RequestParam(name="eventStartDate") String startDate,
                               @RequestParam(name="eventEndDate") String endDate,
                               @RequestParam(name="eventStartTime") String startTime,
                               @RequestParam(name="eventEndTime") String endTime,
                               @RequestParam(name="eventOrganiser") int eventOrganiser,
                               @RequestParam(name="maxTeamSize") int maxTeamSize,
                               @RequestParam(name="dateCreated") String dateCreated,
                               @RequestParam(name="eventColour") String eventColour){

        return 1 == EventQrys.postNewEvent(eventTitle,eventDescription,address1,address2,city,postcode,startDate,
                endDate,startTime,endTime,eventOrganiser, maxTeamSize, dateCreated, eventColour);

    }

    @RequestMapping(value="/api/post/event/slack", method=RequestMethod.POST)
    public String submitEventFromSlack(@Valid @ModelAttribute Event event, BindingResult bindingResult){
        System.out.println("TEST");
        return null;
    }

    ///////////////////////    DELETE     ////////////////////////////////

    @DeleteMapping("api/delete/event")
    public boolean deleteEvents(@RequestParam(value="eventId") Integer eventId){
        if (EventQrys.deleteEvents(eventId) == 1) {
            return true;
        } else {
            return false;
        }
    }
}
