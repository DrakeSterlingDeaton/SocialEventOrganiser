package eventorganiser.dir.DBMethods.DBClasses;

import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class Event {

    private int id;
    private String title;
    private String description;
    private String eventLocSt1;
    private String eventLocSt2;
    private String eventLocCity;
    private String eventLocPost;
    private @DateTimeFormat(pattern = "yyyy-MM-dd") Date eventStartDate;
    private @DateTimeFormat(pattern = "yyyy-MM-dd") Date eventEndDate;
    private @DateTimeFormat(pattern = "HH:mm") Time eventStartTime;
    private @DateTimeFormat(pattern = "HH:mm") Time eventEndTime;
    private String start;
    private String end;
    private int eventOrganiser;
    private Date dateCreated;
    private int maxTeamSize;
    private String backgroundColor;
    private String url;

    public Event() {
    }

    public Event(int eventId, String eventName, String eventDesc, String eventLocSt1, String eventLocSt2, String eventLocCity, String eventLocPost,
                 Date eventStartDate, Date eventEndDate, Time eventStartTime, Time eventEndTime, int eventOrganiser, Date dateCreated, int maxTeamSize, String eventColour) {

        eventStartTime = Time.valueOf(eventStartTime.toLocalTime().minusHours(1));
        eventEndTime = Time.valueOf(eventEndTime.toLocalTime().minusHours(1));

        this.id = eventId;
        this.title = eventName;
        this.description = eventDesc;
        this.eventLocSt1 = eventLocSt1;
        this.eventLocSt2 = eventLocSt2;
        this.eventLocCity = eventLocCity;
        this.eventLocPost = eventLocPost;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.start = this.eventStartDate.toString() + "T" + this.eventStartTime.toString();
        this.end = this.eventEndDate.toString() + "T" + this.eventEndTime.toString();
        this.eventOrganiser = eventOrganiser;
        this.dateCreated = dateCreated;
        this.maxTeamSize = maxTeamSize;
        this.backgroundColor = eventColour;
        this.url = "./eventPage?eventId=" + eventId;
    }

    public Event(int id, String title,Date dateCreated) {
        this.id = id;
        this.title = title;
        this.dateCreated = dateCreated;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getEventLocSt1() {
        return eventLocSt1;
    }

    public String getEventLocSt2() {
        return eventLocSt2;
    }

    public String getEventLocCity() {
        return eventLocCity;
    }

    public String getEventLocPost() {
        return eventLocPost;
    }

    public Date getEventStartDate() {
        return eventStartDate;
    }

    public Date getEventEndDate() { return eventEndDate; }

    public Time getEventStartTime() {
        return eventStartTime;
    }

    public Time getEventEndTime() {
        return eventEndTime;
    }

    public String getStart() { return start; }

    public String getEnd() { return end; }

    public int getEventOrganiser() {
        return eventOrganiser;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public int getMaxTeamSize() {
        return maxTeamSize;
    }

    public String getBackgroundColor() { return backgroundColor; }

    public String getUrl() { return url; }

    public List<Object> getListOfAllInfo() { // Returns in order: [eventId, eventName, eventDesc, eventLocSt1,
        List<Object> li = new ArrayList<>();  //                    eventLocSt2, eventLocCity, eventLocPost,
        li.add("ID: " + id);                           //                    eventDate, eventStart Time, eventEndTime,
        li.add("TITLE: " + title);                    //                    eventOrganiser, dateCreated, maxTeamSize]
        li.add("DESCRIPTION: " + description);
        li.add("ADDRESS 1: " +eventLocSt1);
        li.add("ADDRESS 2: " + eventLocSt2);
        li.add("CITY: " + eventLocCity);
        li.add("POSTCODE: " + eventLocPost);
        li.add("START DATE: " + eventStartDate);
        li.add("END DATE: " + eventEndDate);
        li.add("START TIME: " + eventStartTime);
        li.add("END TIME: " + eventEndTime);
        li.add("START:" + start);
        li.add("END: " + end);
        li.add("ORGANISER: " + eventOrganiser);
        li.add("DATE CREATED: " + dateCreated);
        li.add("MAX TEAM SIZE: " + maxTeamSize);
        li.add("BG COLOUR: " + backgroundColor);
        li.add("URL: " + url);
        return li;
    }

    public void setId(int eventId) {
        this.id = eventId;
    }

    public void setTitle(String eventName) {
        this.title = eventName;
    }

    public void setDescription(String eventDesc) {
        this.description = eventDesc;
    }

    public void setEventLocSt1(String eventLocSt1) {
        this.eventLocSt1 = eventLocSt1;
    }

    public void setEventLocSt2(String eventLocSt2) {
        this.eventLocSt2 = eventLocSt2;
    }

    public void setEventLocCity(String eventLocCity) {
        this.eventLocCity = eventLocCity;
    }

    public void setEventLocPost(String eventLocPost) {
        this.eventLocPost = eventLocPost;
    }

    public void setEventStartDate(Date eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    public void setEventEndDate(Date eventEndDate) { this.eventEndDate = eventEndDate; }

    public void setEventStartTime(Time eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    public void setEventEndTime(Time eventEndTime) {
        this.eventEndTime = eventEndTime;
    }

    public void setStart(Date eventStartDate, Time eventStartTime) {
        this.start = eventStartDate.toString() + "T" + eventStartTime.toString(); }

    public void setEnd(Date eventEndDate, Time eventEndTime) {
        this.end = eventEndDate.toString() + "T" + eventEndTime.toString(); }

    public void setEventOrganiser(int eventOrganiser) {
        this.eventOrganiser = eventOrganiser;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setMaxTeamSize(int maxTeamSize) {
        this.maxTeamSize = maxTeamSize;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setAllFields(int eventId, String eventName, String eventDesc, String eventLocSt1, String eventLocSt2, String eventLocCity, String eventLocPost,
                             Date eventStartDate, Date eventEndDate, Time eventStartTime, Time eventEndTime, int eventOrganiser, Date dateCreated, int maxTeamSize, String eventColour) {
        this.id = eventId;
        this.title = eventName;
        this.description = eventDesc;
        this.eventLocSt1 = eventLocSt1;
        this.eventLocSt2 = eventLocSt2;
        this.eventLocCity = eventLocCity;
        this.eventLocPost = eventLocPost;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
        this.eventStartTime = eventStartTime;
        this.eventEndTime = eventEndTime;
        this.start = eventStartDate.toString() + "T" + eventStartTime.toString();
        this.end = eventEndDate.toString() + "T" + eventEndTime.toString();
        this.eventOrganiser = eventOrganiser;
        this.dateCreated = dateCreated;
        this.maxTeamSize = maxTeamSize;
        this.backgroundColor = eventColour;
        this.url = "./eventPage.html";
    }


//    public Date stringToDate(String date) throws ParseException {
//        Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(date);
//        return date1;
//    }
//
//    public Date stringToTime(String time) throws ParseException {
//        Date time1 = new SimpleDateFormat("hh:mm").parse(time);
//        return time1;
//    }

}