package eventorganiser.dir.APIRequest;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

public class TeamFunctions {

    public static String getTeamsDetailsFromTheDatabase(){

        String uri = "http://localhost:8080/api/get/teams";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        return result;
    }

    public JsonNode getTeamsRandomFromTheDatabase() throws IOException {

        String uri = "http://localhost:8080/api/get/team/random";
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper mapper = new ObjectMapper();
        ResponseEntity<String> response = restTemplate.getForEntity(uri,String.class);
        return mapper.readTree(response.getBody());

    }


    public static String getTeamMaxSizeFromTeamIdFromTheDatabase(String teamId){

        String uri = "http://localhost:8080/api/get/team/maxsize?teamId=" + teamId;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        return result;
    }

    public static String getTeamIdFromEventIdAndUserIdFromTheDatabase(String eventId, String userId){

        String uri = "http://localhost:8080/api/get/team?eventId=" + eventId + "&userId=" + userId;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        return result;
    }

    public static String getTeamNameFromTeamIdFromTheDatabase(String teamId){

        String uri = "http://localhost:8080/api/get/team/name?teamId=" + teamId;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        return result;
    }

    public ModelAndView addTeamEventDataToEventPageModelView(ModelAndView mv, JsonNode evntJson, String userId) {

        try {
            // Checking that event is team event by checking for null or 0 values on 'maxTeamSize' event info
            Integer maxTeamSize = evntJson.path("maxTeamSize").intValue();
            System.out.println("TeamFunctions: " + 1);
            if (maxTeamSize > 0) {
                System.out.println("TeamFunctions: " + 2);
                // Fetching team name
                Integer eventId = evntJson.path("id").intValue();
                System.out.println("TeamFunctions: " + 3);
                String teamId = TeamFunctions.getTeamIdFromEventIdAndUserIdFromTheDatabase(eventId.toString(), userId);
                System.out.println(1);
                System.out.println("TeamFunctions: " + 4);
                System.out.println("Team ID: " + teamId);
                if (teamId == null) { throw new NumberFormatException("No results from DB"); };
                String teamName = TeamFunctions.getTeamNameFromTeamIdFromTheDatabase(teamId);
                // Adding team data to view
                mv.addObject("isTeamEvent", true); // JS will use this bool to make HTML & CSS display changes
                mv.addObject("teamName", teamName);
                mv.addObject("teamId", teamId);

            } else {
                mv.addObject("isTeamEvent", false); // JS will use this bool to make HTML & CSS display changes
                return mv;
            }
        } catch (Exception e) { // Team event is most likely not a team event
            System.out.println("Not team event");
            mv.addObject("isTeamEvent", false); // JS will use this bool to make HTML & CSS display changes
        }

        return mv;
    }

    public boolean postNewTeamToDB(int eventId, String teamName){
        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://localhost:8080/api/post/team/new" + "?eventId=" + eventId + "&teamName=" + teamName;
        String res = restTemplate.getForObject(uri,String.class);
        return Boolean.parseBoolean(res);
    }

    public boolean updateTeamInDB(int teamId, String teamName){
        RestTemplate restTemplate = new RestTemplate();
        String uri = "http://localhost:8080/api/post/team/update" + "?teamId=" + teamId + "&teamName=" + teamName;
        String res = restTemplate.getForObject(uri,String.class);
        return Boolean.parseBoolean(res);
    }



}
