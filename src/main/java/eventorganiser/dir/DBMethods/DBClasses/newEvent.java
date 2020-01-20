package eventorganiser.dir.DBMethods.DBClasses;

import java.sql.Time;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class newEvent {
    private String title;
    private String description;
    private String eventLocSt1;
    private String eventLocSt2;
    private String eventLocCity;
    private String eventLocPost;
    private String eventStartDate;
    private String eventEndDate;
    private String eventStartTime;
    private String eventEndTime;
    private String start;
    private String end;
    private int eventOrganiser;
    private Date dateCreated;
    private int maxTeamSize;
    private String backgroundColor;
    private String url;
    private String token;


    public newEvent() {
    }

    public newEvent(String eventName, String eventDesc, String eventLocSt1, String eventLocSt2, String eventLocCity, String eventLocPost,
                    String eventStartDate, String eventEndDate, String eventStartTime, String eventEndTime, int eventOrganiser, Date dateCreated, int maxTeamSize, String eventColour, String token) {

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
        this.token = token;

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

    public String getEventStartDate() {
        return eventStartDate;
    }

    public String getEventEndDate() { return eventEndDate; }

    public String getEventStartTime() {
        return eventStartTime;
    }

    public String getEventEndTime() {
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

    public String getToken() { return token; }

    public List<Object> getListOfAllInfo() { // Returns in order: [eventId, eventName, eventDesc, eventLocSt1,
        List<Object> li = new ArrayList<>();  //                    eventLocSt2, eventLocCity, eventLocPost,
                      //                    eventDate, eventStart Time, eventEndTime,
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

    public void setEventStartDate(String eventStartDate) {
        this.eventStartDate = eventStartDate;
    }

    public void setEventEndDate(String eventEndDate) { this.eventEndDate = eventEndDate; }

    public void setEventStartTime(String eventStartTime) {
        this.eventStartTime = eventStartTime;
    }

    public void setEventEndTime(String eventEndTime) {
        this.eventEndTime = eventEndTime;
    }

    public void setStart(String eventStartDate, String eventStartTime) {
        this.start = eventStartDate.toString() + "T" + eventStartTime.toString(); }

    public void setEnd(String eventEndDate, String eventEndTime) {
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
                             String eventStartDate, String eventEndDate, String eventStartTime, String eventEndTime, int eventOrganiser, Date dateCreated, int maxTeamSize, String eventColour) {
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