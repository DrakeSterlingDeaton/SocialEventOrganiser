package eventorganiser.dir.API;

import eventorganiser.dir.DBMethods.DBClasses.Team;
import eventorganiser.dir.DBMethods.TeamQueries;
import eventorganiser.dir.DBMethods.UserQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class TeamsAPI {

    private TeamQueries TeamQrys;

    public TeamsAPI(TeamQueries t) {
        TeamQrys = t;
    }

    // get all Teams
    @GetMapping("api/get/teams")
    public Object APIgetAllTeams() {
        return TeamQrys.getAllTeams();
    }

    // get random Team
    @GetMapping("api/get/team/random")
    public Object APIgetRandomTeam() {
        return TeamQrys.getRandomTeam();
    }

    // get max size of a Team
    @GetMapping("api/get/team/maxsize")
    public Object APIgetTeamMaxSizeFromTeamId(@RequestParam(name="teamId", required=true) String teamId) {
        int teamIdInt = Integer.parseInt(teamId);
        return TeamQrys.getTeamMaxSizeFromTeamId(teamIdInt);
    }

    // get Team Id
    @GetMapping("api/get/team")
    public Object APIgetTeamIdFromEventId(@RequestParam(name="eventId", required=true) String eventId,
                                          @RequestParam(name="userId", required=true) String userId) {
        System.out.println("Event ID is: " + eventId);
        System.out.println("User ID is: " + userId);
        int eventIdInt = Integer.parseInt(eventId);
        int userIdInt = Integer.parseInt(userId);
        return TeamQrys.getTeamIdFromEventIdAndUserId(eventIdInt, userIdInt);
    }

    @GetMapping("api/get/event/team")
    public int getTeamIdFromTeamNameAndEventId(@RequestParam(name="teamName") String teamName,
                                                       @RequestParam(name="eventId") int eventId){
        return TeamQrys.getTeamIdFromTeamNameAndEventId(teamName,eventId);
    }

    @GetMapping("api/get/event/teammates")
    public Object APIgetTeammatesFromUserIdFromEventId(@RequestParam(name="eventid", required=true) String eventId,
                                                       @RequestParam(name="teamid", required=true) String teamId) {
        System.out.println("Event ID is: " + eventId);
        System.out.println("Team ID is: " + teamId);
        int eventIdInt = Integer.parseInt(eventId);
        int teamIdInt = Integer.parseInt(teamId);
        return TeamQrys.getTeamMembers(eventIdInt, teamIdInt);
    }

    // get Team Name
    @GetMapping("api/get/team/name")
    public Object APIgetTeamNameFromTeamId(@RequestParam(name="teamId", required=true) String teamId) {
        int teamIdInt = Integer.parseInt(teamId);
        return TeamQrys.getTeamNameFromTeamId(teamIdInt);
    }

    @RequestMapping(value="/api/post/team/new", method=RequestMethod.GET)
    public boolean submitNewTeam(HttpServletRequest req,
                                 @RequestParam(name="eventId") int eventId,
                                 @RequestParam(name="teamName") String teamName){

                return 1 == TeamQrys.postNewTeam(eventId, teamName);
    }

    @RequestMapping(value="/api/post/team/update", method=RequestMethod.GET)
    public boolean updateTeam(HttpServletRequest req,
                                @RequestParam(name="teamId") int teamId,
                                @RequestParam(name="teamName") String teamName){
                return 1 == TeamQrys.editTeamName(teamId, teamName);
    }

//    @GetMapping("api/get/event/teams")
//    public Object apiGetAllTeamsForEventByEventId(@RequestParam(name="eventId") int eventId){
//        return TeamQrys.getAllTeamsInEventByEventId(eventId);
//    }

}
