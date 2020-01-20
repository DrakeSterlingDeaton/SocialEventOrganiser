package eventorganiser.dir.base;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import eventorganiser.dir.APIRequest.*;
import eventorganiser.dir.DBMethods.DBClasses.Event;
import eventorganiser.dir.DBMethods.DBClasses.Team;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.beans.factory.annotation.Autowired;

import eventorganiser.dir.DBMethods.DBClasses.newEvent;

import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;


@Controller
public class BaseController {
    private UserFunctions cUserFunctions = new UserFunctions();
    private TeamFunctions cTeamsFunctions = new TeamFunctions();
    private GroupFunctions cGroupFunctions = new GroupFunctions();
    private AttendanceFunctions cAttendanceFunction = new AttendanceFunctions();
    private EventFunctions cEventFunctions = new EventFunctions();
    private SlackFunctions cSLackFunctions = new SlackFunctions();
    private OutlookFunctions cOutlookFunctions = new OutlookFunctions();
    private AttendanceFunctions cAttendanceFunctions = new AttendanceFunctions();

    @Autowired
    private  HttpServletRequest context; // this will provide the current instance of HttpServletRequest

    /*@GetMapping("/")
    public ModelAndView home(HttpSession session) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("feedPage");
        return mv;
    }

     */

    @GetMapping("/")
    public RedirectView redirectWithUsingRedirectView() {

        return new RedirectView("/feedPage");
    }

    @GetMapping("/Calendar")
    public ModelAndView Calendar(HttpSession session) {
        ModelAndView mv = new ModelAndView();
        ObjectMapper objectMapper = new ObjectMapper();
        session = context.getSession();
        int  userId =  Integer.parseInt((String)session.getAttribute("SESSION_USERID"));
        mv.addObject("userId",userId);
        mv.setViewName("calendar");
        return mv;
    }

    @GetMapping("/addEvent")
    public ModelAndView addEvent(@RequestParam(name="code", defaultValue = "") String code, Event event, HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("/addEvent");

        request.getSession().setAttribute("SESSION_TOKEN", code);
        mv.addObject(event);
        return mv;
    }

    @GetMapping("/eventPage")
    @ResponseBody
    public ModelAndView eventPage(@RequestParam(name="eventId") int eventId, HttpSession session) throws IOException {
        ModelAndView mv = new ModelAndView("eventPage");
        String userId = session.getAttribute("SESSION_USERID").toString();
        JsonNode evntJson = cEventFunctions.getEventByIdFromDatabase(eventId);
        mv = cEventFunctions.addEventDataToEventPageModelView(mv, evntJson);
        mv = cTeamsFunctions.addTeamEventDataToEventPageModelView(mv, evntJson, userId);
        mv = cAttendanceFunctions.addAttendanceStatusToEventPageModelView(mv, evntJson, context);
        mv = cEventFunctions.addMapDatatoEventPageModelView(mv, evntJson);
        mv.addObject("userId", userId);
        mv.addObject("eventId", ((Integer) evntJson.path("id").intValue()).toString());
        return mv;
    }

    @GetMapping("/eventPagePopUp")
    @ResponseBody
    public ModelAndView eventPagePopUp(HttpSession session) throws IOException {
//        Object teams = cEventFunctions.getEventTeamsByIdFromDatabase(eventId);
        ModelAndView mv = new ModelAndView("eventPagePopUp");
//        mv.addObject(teams);

        return mv;
    }

    @PostMapping("/post/attendance")
    @ResponseBody
    public Object processPostAttendance(HttpServletRequest req,
                                        @RequestParam(name="userId") String userId,
                                        @RequestParam(name="eventId") String eventId,
                                        @RequestParam(name="response") String response,
                                        @RequestParam(name="attendanceId", required=false) String attendanceId){
        if ( req.getParameterMap().containsKey("attendanceId") ) {
            return cAttendanceFunctions.updateAttdRecordOnDB(userId, eventId, response, attendanceId); }
        else {
            return cAttendanceFunctions.postAttdRecordToDB(userId, eventId, response);
        }
    }

    @GetMapping("/mapPage")
    @ResponseBody
    public ModelAndView returnMap(HttpSession session){
        return new ModelAndView("mapPage");
    }

    @GetMapping("/feedPage")
    public ModelAndView feedPage(HttpSession s) throws IOException, ParseException {

        int  userId =  Integer.parseInt((String)s.getAttribute("SESSION_USERID"));

        ModelAndView mv = new ModelAndView();
        JsonNode liOfEvntUserAttandance = cAttendanceFunctions.getUserAttendanceRecordsFromUserIdFromTheDatabase(userId);
        JsonNode liOfEvnts = cEventFunctions.getEventsInChronologicalOrderFromDatabase();

        // All the events chronologically
        mv = cEventFunctions.addEventDataToFeedPageModelView(mv,liOfEvnts,liOfEvntUserAttandance);

        //Events from the login user chronologically
        mv = cEventFunctions.addEventDataToFeedForUserPageModelView(mv, liOfEvnts, liOfEvntUserAttandance);

        mv.setViewName("feedPage");
        return mv;
    }



    @GetMapping("/deleteEvent")
    public ModelAndView deletePage(HttpSession session) throws IOException, ParseException {

        HttpSession s = context.getSession();
        int  userId =  Integer.parseInt((String)s.getAttribute("SESSION_USERID"));

        ModelAndView mv = new ModelAndView();
        //Get all the events created by the User logged in
        mv = cEventFunctions.getEventsCreatedbyUserId(mv,userId);

        mv.setViewName("deletePage");
        return mv;
    }


    @PostMapping("/post/event")
    @ResponseBody
    public ModelAndView postEvent(@ModelAttribute newEvent event, HttpServletRequest req, HttpSession session) throws ParseException, IOException {
        HttpSession s = context.getSession();
        String token =  (String)s.getAttribute("SESSION_TOKEN");
        ModelAndView mv = new ModelAndView();

        String startDate = event.getEventStartDate();
        String endDate = event.getEventEndDate();
        String eventTitle = event.getTitle();
        String eventDescription = event.getDescription();
        String startTime = event.getEventStartTime();
        String endTime = event.getEventEndTime();
        String address1 = event.getEventLocSt1();
        String address2 = event.getEventLocSt2();
        String city = event.getEventLocCity();
        String postCode = event.getEventLocPost();
        int poster = Integer.parseInt(UserFunctions.getUserIdFromTheDatabase(req));
        String name = UserFunctions.getUserFullNamesFromTheDatabase(req);
        int maxTeamSize = event.getMaxTeamSize();
        String eventColour = event.getBackgroundColor();

        if(cEventFunctions.postNewEventToDB(eventTitle,eventDescription,address1,
                address2,city, postCode,startDate,
                endDate,startTime,endTime,poster,maxTeamSize,
                eventColour)){
            int id = cEventFunctions.getEventIdByNameAndDateFromDatabase(eventTitle,startDate).asInt();

            SlackFunctions.postToSlack(eventTitle,eventDescription,address1,
                    address2,city, postCode,startDate,
                    endDate,startTime,endTime,name,maxTeamSize,
                    id);

            OutlookFunctions.sendEmail(eventTitle,eventDescription,address1,
                    address2,city, postCode,startDate,
                    endDate,startTime,endTime,name,maxTeamSize,
                    id, token);

            mv.setViewName("redirect:/eventPage?eventId=" + id);
            return mv;
        }
        else{
            mv.setViewName("redirect:/addEvent");
            return mv;
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        new SecurityContextLogoutHandler().logout(request, null, null);
        return "redirect:/feedPage";
    }
}
