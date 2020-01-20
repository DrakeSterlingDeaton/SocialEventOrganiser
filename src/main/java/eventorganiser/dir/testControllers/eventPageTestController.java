package eventorganiser.dir.testControllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import eventorganiser.dir.APIRequest.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

@Controller
public class eventPageTestController {
    UserFunctions cUserFunctions = new UserFunctions();
    EventFunctions cEventFunctions = new EventFunctions();
    TeamFunctions cTeamsFunctions = new TeamFunctions();
    GroupFunctions cGroupFunctions = new GroupFunctions();
    AttendanceFunctions cAttendanceFunction = new AttendanceFunctions();

    @Autowired
    private HttpServletRequest context; // this will provide the current instacne of HttpServletRequest


}
