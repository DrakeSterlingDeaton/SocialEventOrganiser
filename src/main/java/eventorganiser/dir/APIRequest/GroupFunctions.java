package eventorganiser.dir.APIRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

public class GroupFunctions {

    public static String getAllGroupMembershipsFromTheDatabase(){

        String uri = "http://localhost:8080/api/get/group/membership";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        return result;
    }

    public static String getAllGroupsFromTheDatabase(){

        String uri = "http://localhost:8080/api/get/groups";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        return result;
    }


    public static String getGroupIdFromGroupNameFromTheDatabase(String groupName){

        String uri = "http://localhost:8080/api/get/group?groupName=" + groupName;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        return result;
    }

    public static String getGroupMembersFromTheDatabase(String groupId){

        String uri = "http://localhost:8080/api/get/group/member?groupId=" + groupId;
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        return result;
    }

    public static String getRandomGroupMemberFromTheDatabase(){

        String uri = "api/get/group/member/random";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        return result;
    }

    public static String getRandomGroupFromTheDatabase(){

        String uri = "api/get/group/random";
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);

        return result;
    }

    ///////////////////////////


}
