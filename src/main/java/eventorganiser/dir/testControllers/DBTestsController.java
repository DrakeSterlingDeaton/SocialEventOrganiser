package eventorganiser.dir.testControllers;

import eventorganiser.dir.DBMethods.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;

@Controller
public class DBTestsController {

    private EventQueries EventQrys;
    private GroupQueries GroupQrys;
    private TeamQueries TeamQrys;
    private UserQueries UserQrys;
    private AttendanceQueries AttdQrys;

    public DBTestsController() { }

    @Autowired
    public DBTestsController(EventQueries e, GroupQueries g, TeamQueries t,
                             UserQueries u, AttendanceQueries a) {
        EventQrys = e;
        GroupQrys = g;
        TeamQrys = t;
        UserQrys = u;
        AttdQrys = a;
    }
//
//    public static void main(String[] args) {
//        SpringApplication.run(DBTestsController.class, args);
//    }

    //@EventListener(ApplicationReadyEvent.class)
    public void databaseFunctionsTest() {
        try {
            // do something with body


        } catch (Exception e) {
            e.printStackTrace();
        };
    }

//    @RestController("/api/get/events")
//    @ResponseBody
//    "www.OURWEBSITE.com/api/get/events?from=past"
//    public List<Event> getEvents(@RequestParam("from") String fromValue) {
//        if (fromValue == "upcoming") {
//            // DO THIS
//            return EventQrys.getAllEventsInChronologicalOrder();
//        }
//        if (fromValue == "past") {
//            // DO THIS
//            return EventQrys.getAllPastEventsInReverseChronologicalOrder();
//        }
//        else { return null; }
//    }

}

