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

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SlackFunctions {

    public static final String uri = "https://hooks.slack.com/services/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";

    public static String postToSlack(String eventTitle, String eventDescription, String address1, String address2, String city,
                                     String postcode, String startDate, String endDate, String startTime, String endTime, String poster, int maxTeamSize, int eventId) throws ParseException {

        RestTemplate restTemplate = new RestTemplate();
        String eventUrl = "http://localhost:8080/eventPage?eventId=" + eventId;

        System.out.println(poster);

        String reqBody = "{\"text\": \"" + poster +" has just posted a new event\", \"attachments\": [ { \"text\": \"" + eventTitle +" for "+startDate+". Please <"+eventUrl+"|click here> to confirm attendance if you will be attending this event.\", \"fallback\": \"You are unable to confirm attendance\", \"author_name\": \"" + poster + "\", \"author_icon\": \"\", \"fields\": [ { \"title\": \"Event description\", \"value\": \""+ eventDescription +"\", \"short\": false }, { \"title\": \"Event start time\", \"value\": \"" + startTime + "\", \"short\": true }, { \"title\": \"Event end time\", \"value\": \"" + endTime + "\", \"short\": true }], \"footer\": \"Will you be attending this event?\", \"callback_id\": \"wopr_game\", \"color\": \"#3AA3E3\", \"attachment_type\": \"default\", \"actions\": [ { \"name\": \"event\", \"text\": \"Yes\", \"type\": \"button\", \"value\": \"chess\" }, { \"name\": \"event\", \"text\": \"Maybe\", \"type\": \"button\", \"value\": \"maze\" }, { \"name\": \"event\", \"text\": \"No\", \"style\": \"danger\", \"type\": \"button\", \"value\": \"war\", \"confirm\": { \"title\": \"Are you sure?\", \"text\": \"Would you not want to come to the Event\", \"ok_text\": \"Yes\", \"dismiss_text\": \"No\" } } ] } ] }";
        String result = restTemplate.postForObject(uri, reqBody, String.class); // Slack basically just returns ok

        return result;
    }
}
