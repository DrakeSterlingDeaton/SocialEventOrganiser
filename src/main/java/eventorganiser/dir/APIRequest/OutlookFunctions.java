package eventorganiser.dir.APIRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import java.text.ParseException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class OutlookFunctions {

    private static final String tokenUrl = "https://login.microsoftonline.com/common/oauth2/v2.0/token";
    private static final String sendMailUrl = "https://graph.microsoft.com/v1.0/me/sendMail";
    private static final String clientId = "168224e1-0bd0-450a-8613-3f1d26bacbd1";
    private static final String clientSecret = "UWR]:U[k3l]XdgASxD5r8AYeKV9i0.BA";
    private static final String redirectUri = "http://localhost:8080/addEvent";

    private UserFunctions cUserFunctions = new UserFunctions();


    /*
     * Retrieve Access token from Microsoft Outlook
     */
    public static String authenticate(String code) {

        ObjectMapper objectMapper = new ObjectMapper();

        String token = "";
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<String, String>();
            requestBody.add("grant_type", "authorization_code");
            requestBody.add("code", code);
            requestBody.add("client_id", clientId);
            requestBody.add("client_secret", clientSecret);
            requestBody.add("redirect_uri", redirectUri);

            HttpEntity entity = new HttpEntity<MultiValueMap<String, String>>(requestBody, headers);
            RestTemplate restTemplate = new RestTemplate();

            ResponseEntity<String> response = restTemplate.exchange(tokenUrl, HttpMethod.POST, entity, String.class);

            System.out.println("Entity is "+entity);
            System.out.println(response.getBody());
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            token = jsonNode.get("access_token").asText();
            System.out.println("Token is " + token);
        } catch (IOException e) {
            System.out.println("** Exception: "+ e.getMessage());
        }
        return token;
    }

    private static HttpHeaders createHttpHeaders(String token) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization", "Bearer "+token);
        return headers;
    }

    public static void sendEmail(String eventTitle, String eventDescription, String address1, String address2, String city,
                                   String postcode, String startDate, String endDate, String startTime, String endTime, String poster, int maxTeamSize, int eventId, String code) throws ParseException {

        System.out.println("Grant code retrieved from API is: "+code);

        // Check if the grant code is empty and only proceed to the next step if it is not empty.
        if(!code.isEmpty()) {
            String token = authenticate(code);

            // Check if we were able to retireve token and only proceed to the next step if it is not empty.
            if (!token.isEmpty()) {

                RestTemplate restTemplate = new RestTemplate();
                System.out.println(token);
                String eventUrl = "http://localhost:8080/eventPage?eventId=" + eventId;
                try {
                    HttpHeaders headers = createHttpHeaders(token);
                    String reqBody = "{ \"message\": { \"subject\": \"" + poster + " has created a new event : " + eventTitle + "\", \"body\": { \"contentType\": \"Text\", \"content\": \"Hi, a new event has been created for you. Please see event and confirm availability " + eventUrl + "\" }, \"toRecipients\": [ { \"emailAddress\": { \"address\": \"scopes_oye@yahoo.com\" } } ], \"ccRecipients\": [ { \"emailAddress\": { \"address\": \"danas@contoso.onmicrosoft.com\" } } ] }, \"saveToSentItems\": \"true\" }";
                    HttpEntity<String> entity = new HttpEntity<String>(reqBody, headers);
                    ResponseEntity<String> response = restTemplate.exchange(sendMailUrl, HttpMethod.POST, entity, String.class);
                    System.out.println("Result - status (" + response.getStatusCode() + ") has body: " + response.hasBody());
                } catch (Exception eek) {
                    System.out.println("** Exception: " + eek.getMessage());
                }
            }
        }
    }
}