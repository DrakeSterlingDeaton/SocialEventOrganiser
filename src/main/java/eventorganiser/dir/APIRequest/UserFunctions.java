package eventorganiser.dir.APIRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UserFunctions {
    public static String getUsersDetailsFromTheDatabase(){

        String uri = "http://localhost:8080/api/get/users";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        return result;
    }


    public static String getUserIdFromTheDatabase( HttpServletRequest request){
        // Getting the username from the session
        HttpSession s = request.getSession();
        String  username =  (String)s.getAttribute("SESSION_USERNAME");

        String uri = "http://localhost:8080/api/get/user?username=" + username;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        return result;
    }

    public static String getUserDetailsFromTheDatabase(HttpServletRequest request){
        // Getting the username from the session
        HttpSession s = request.getSession();
        String  userId =  (String)s.getAttribute("SESSION_USERID");

        String uri = "http://localhost:8080/api/get/user/details?userId=" + userId;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        return result;
    }

    public static String getUserFullNamesFromTheDatabase( HttpServletRequest request){
        // Getting the username from the session
        HttpSession s = request.getSession();
        String  username =  (String)s.getAttribute("SESSION_USERNAME");

        String uri = "http://localhost:8080/api/get/userfullname?username=" + username;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        return result;
    }

    public static String getRandomUserFromTheDatabase(){

        String uri = "http://localhost:8080/api/get/user/random";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        return result;
    }


    public static String getUserTeamsFromUserIdFromTheDatabase( HttpServletRequest request){
        // Getting the userId from the session
        HttpSession s = request.getSession();
        String  userId =  (String)s.getAttribute("SESSION_USERID");

        String uri = "http://localhost:8080/api/get/user/teams?userId=" + userId;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        return result;
    }


    public static String getUserGroupsFromUserIdFromTheDatabase( HttpServletRequest request){
        // Getting the userId from the session
        HttpSession s = request.getSession();
        String  userId =  (String)s.getAttribute("SESSION_USERID");

        String uri = "http://localhost:8080/api/get/user/group?userId=" + userId;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        return result;
    }


    public static String getUserGroupsFromUserIdFromTheDatabase( HttpServletRequest request,String eventId){
        // Getting the username from the session
        HttpSession s = request.getSession();
        String  userId =  (String)s.getAttribute("SESSION_USERID");

        String uri = "http://localhost:8080/api/get/userteams/events?userId=" + userId +"&eventId=" +eventId;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        return result;
    }
}
