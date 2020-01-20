package eventorganiser.dir.DBMethods;

import eventorganiser.dir.DBMethods.DBClasses.AttendanceRecord;
import eventorganiser.dir.DBMethods.DBClasses.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TestQueries {

    private JdbcTemplate jdbcTemplate;
    private EventQueries EventQrys;
    private GroupQueries GroupQrys;
    private TeamQueries TeamQrys;
    private UserQueries UserQrys;
    private AttendanceQueries AttdQrys;

    public TestQueries() {
        jdbcTemplate = new JdbcTemplate();
        EventQrys = new EventQueries(jdbcTemplate);
        GroupQrys = new GroupQueries(jdbcTemplate);
        TeamQrys = new TeamQueries(jdbcTemplate);
        UserQrys = new UserQueries(jdbcTemplate);
        AttdQrys = new AttendanceQueries(jdbcTemplate);
    }

    /////////////////////////////////////// CONSTRAINTS TEST ///////////////////////////////////////
//
// // ASSERT THAT DATA SUBMITTED FOR eventDate ARE IN THE CORRECT FORMAT "YYYY/MM/DD"
//
//  // ASSERT THAT DATA SUBMITTED FOR eventStartTime & eventEndTime ARE IN THE CORRECT FORMAT
//
//  // ENSURE THAT ON ATTENDANCE RECORDS THAT THE ONLY VALUES SUBMITTED ARE 1,2 OR 3
//
//    public boolean checkGroupNameIsntTaken(String groupName) {  // Groups are NOT allowed to have the same name
//
//    }
//
//    public boolean checkTeamNameIsntTaken(String teamName, int eventId) {  // Multiple teams at the same event are NOT allows to have the same name
//
//    }
//
//    public boolean checkUsernameIsntTaken(String username) {    // Users are NOT allowed to have the same name
//
//    }
//
//    public boolean checkAttendanceStatusVsTeamMembershipForConflicts { // If 'attendance'.'response' = 2 ( meaning NO), 'attendance'.'teamId' must = null
//
//    }

    // ENSURES THAT ON ATTENDANCE RECORDS THAT THE ONLY VALUES SUBMITTED ARE 1,2 OR 3
    public boolean testAllAttdRcrdResonseVals() {
        List<AttendanceRecord> attdLi = AttdQrys.getAllAttendanceRecords();
        for (AttendanceRecord record : attdLi) {
            if (!(1 <= record.getResponse() && record.getResponse() <= 3)) {
                System.err.println("ERROR: Bad Response");
                System.err.println("AttendanceId: " + record.getAttendanceId());
                System.err.println("Response: " + record.getResponse());
                return false;
            }
        }
        return true;
    }

    public boolean checkTeamNameIsntTaken(String teamName, int eventId) {  // Multiple teams at the same event are NOT allows to have the same name
        List<Team> teamsLi = TeamQrys.getTeamsForAnEventFromEventId(eventId);
        for (Team team : teamsLi) {
            if (teamName.equals(team.getTeamName())) {
                System.err.println("ERROR: Team name is taken");
                System.err.println("TeamId: " + team.getTeamId());
                return false;
            }
        }
        return true;
    }

    public boolean checkUsernameIsntTaken(String username) {    // Users are NOT allowed to have the same name
        try {
            UserQrys.getUserIdFromUsername(username);   // Will throw an error if there is no one with that username on the DB
            return false;   // User name is taken, so return false
        } catch (Exception e) {
            return true; // Username is available
        }
    }
}

    /////////////////////////////////////////// GENERAL DB COMMUNICATION TESTS ///////////////////////////////////////////

//    public boolean checkThatAllUserQueriesMethodsReturnData() {
//        try {
//            Integer test = UserQrys.getUserIdFromUsername("testUser101@gmail.com"); // SHOULD RETURN 1
//            assert test instanceof Integer;
//        }
//        return
// Integer testAdd = UserQrys.postNewUser("TestingPostUser@gmail.com", "1234", 1, "Fake", "User"); // SHOULD RETURN 1 (meaning successfully added)
// Integer testDelete = UserQrys.deleteUser("TestingPostUser@gmail.com");   // SHOULD RETURN 1 (refers to num of rows effected)

// Integer test = EventQrys.getEventIdFromEventNameAndDate("David's Birthday Bash", "2019-12-10"); // SHOULD RETURN 1
// User test0 = UserQrys.getRandomUser();          // SHOULD RETURN A RANDOM INSTANCE OF USER CLASS
// List<AttendanceRecord> test1 = UserQrys.getUserAttendanceRecordsFromUserId(test0.getUserId());  // SHOULD RETURN A LI OF ATTENDANCE RECORDS
// Team test2 = UserQrys.getUserTeamForEventByUserIdAndEventId(1, 2);  // SHOULD RETURN INSTANCE OF TEAM CLASS
// // TESTS FOR NULLS in UserQrys.getUserTeamForEventByUserIdAndEventId
// Integer test3 = UserQrys.postNewUser("NewFakeUser", "1234", 1, "Fake", "Fake");
// Integer test4 = UserQrys.getUserIdFromUsername("NewFakeUser");
// Team test5 = UserQrys.getUserTeamForEventByUserIdAndEventId(test4, 1); // SHOULD RETURN NULL
// // TESTS FOR NULLS in UserQrys.getUserGroupsFromUserId
// List<Group> test6 = UserQrys.getUserGroupsFromUserId(test4); // SHOULD RETURN NULL
// List<Group> test7 = UserQrys.getUserGroupsFromUserId(1); // SHOULD RETURN LIST OF GROUPS
// Integer testEnd = UserQrys.deleteUserById(test4); // SHOULD RETURN 1
//  List<Event> test = UserQrys.getFutureUserEventsFromUserIdInChronologicalOrder(4); // LIST OF EVENTS DATE ORDERED
//




    //    public boolean checkThatAllEventQueriesMethodsReturnData() {
//
//    }
//
//    public boolean checkThatAllGroupQueriesMethodsReturnData() {
//
//    }
//
//    public boolean checkThatAllTeamQueriesMethodsReturnData() {
//
//    }
//
// Integer test = GroupQrys.getGroupIdFromGroupName("IT Department"); // SHOULD RETURN 1
// Integer test = TeamQrys.getTeamIdFromTeamNameAndEventId("Team Win", 4); // SHOULD RETURN 5
//
// Integer test = EventQrys.postNewEvent();
//          METHOD FOR TESTING:
//            Integer test = EventQrys.postNewEvent("FakeTestEvent", "Fake Descption",
//                    "168 Richmond Road", "Flat 3", "Cardiff", "CF23 3BX",
//                    "2022/12/01", 1, null);
//            System.out.println(test);
//            Integer test2 = EventQrys.getEventIdFromEventNameAndDate("FakeTestEvent", "2022/12/01");
//            System.out.println("EventID is: " + test2.toString());
//            Integer test3 = EventQrys.deleteEventById(test2);
//            System.out.println(test3);
//
// Integer test = EventQrys.deleteEventById(test);
// Integer test = EventQrys.postNewAttendanceRecord(1, 1, 1, null);   // SHOULD RETURN 1 (meaning successfully added)
// Integer test2 = EventQrys.getAttendanceRecordIdFromUserIdAndEventId(1, 1); // SHOULD RETURN ONE ATTENDANCE RECORD
// Integer test3 = EventQrys.deleteAttendanceRecordById(?); SHOULD RETURN 1 (refering to num of rows effected)
// AttendanceRecord test = EventQrys.getRandomAttendanceRecord();  // SHOULD RETURN 1 RANDOM RECORD
// Event test2 = EventQrys.getRandomEvent();    // SHOULD RETURN 1 RANDOM EVENT
//
// int test1 = GroupQrys.postNewGroup("FakeGroupName"); // SHOULD RETURN 1 (meaning successfully added)
// int test2 = GroupQrys.getGroupIdFromGroupName("FakeGroupName"); // NOT IMPORTANT (already tested)
// int test3 = GroupQrys.postNewGroupMember(test2, 1); // SHOULD RETURN 1 (MEANING SUCCESFULL ADDED)
// int test4 = GroupQrys.deleteGroupMember(test2, 1);  // SHOULD RETURN NUM OF ROWS EFFECTED (should be 1)
// int testEnd = GroupQrys.deleteGroupById(test2); // SHOULD RETURN NUM OF ROWS EFFECTED (should be 1)
// Group test5 = GroupQrys.getRandomGroup(); // SHOULD RETURN ONE GROUP CLASS INSTANCE
// GroupMembership test6 = GroupQrys.getRandomGroupMember(); // SHOULD RETURN 1 GROUPMEMBERSHIP INSTANCE
// List<User> usrs = GroupQrys.getGroupMembers(1); // GROUP SHOULD HAVE ATLEAST 1 MEMBER
//
//
// Team test1 = TeamQrys.getRandomTeam();  // SHOULD RETURN 1 INSTANCE OF TEAM CLASS
// List<User> test2 = TeamQrys.getTeamMembers(test1.getEventId(), test1.getTeamId());  // SHOULD RETURN LI OF USERS
// Integer test3 = TeamQrys.getEventIdThatTeamIsRelatedTo(test1.getTeamId());  // SHOULD RETURN INT
// Integer test4 = TeamQrys.getTeamMaxSizeFromTeamId(test1.getTeamId());     // SHOULD RETURN INT
// Integer test5 = TeamQrys.postNewTeam(test1.getEventId(), "Test Team Name"); // SHOULD RETURN 1
// Integer test6 = TeamQrys.getTeamIdFromTeamNameAndEventId(test1.getTeamName(), test1.getEventId()); // SHOULD RETURN INT
// Integer test7 = TeamQrys.editTeamName(1, "Test Team Name UPDATE"); // SHOULD RETURN 1
// Integer test8 = TeamQrys.deleteTeamById(1);   // WILL FAIL DUE TO FK CONSTRAINT
//
//
// List<Event> test = EventQrys.getAllEventsInChronologicalOrder(); // LIST OF EVENT OBJECTS SHOULD BE DATE ORDERED
// List<Event> test = EventQrys.getPastUserEventsFromUserIdInReverseChronologicalOrder(1)   // LIST OF EVENTS DATE ORDERED (CLOSEST PAST DATES FIRST)
//                                                  // ^^^^^ ADD MORE DATA TO THIS TO MAKE SURE IT WORKS
// Event test = EventQrys.getEventDetailsByEventId(1);  // SHOULD EQUAL EVENT OBJ (davids birthday bash)
//
// List<AttendanceRecord> test3 = EventQrys.getEventAttendanceRecordsByEventId(2); // SHOULD RETURN LIST OF RECORDS
//    NULL TEST FOR ABOVE FUNCTION:
//             Integer test = EventQrys.postNewEvent("FakeTestEvent", "Fake Descption",
//                    "168 Richmond Road", "Flat 3", "Cardiff", "CF23 3BX",
//                    "2022/12/01", 1, null);
//            Integer test2 = EventQrys.getEventIdFromEventNameAndDate("FakeTestEvent", "2022/12/01");
//            List<AttendanceRecord> test3 = EventQrys.getEventAttendanceRecordsByEventId(test2);
//            System.out.println("ATTENDANCE RUNS ARE: " + test3);
//            Integer test4 = EventQrys.deleteEventById(test2);
//
//
// List<Integer> test = EventQrys.getEventTeamsByEventId(2)   // should return list of ints
//             Integer fakeEvent = EventQrys.postNewEvent("FakeTestEvent", "Fake Descption",
//                    "168 Richmond Road", "Flat 3", "Cardiff", "CF23 3BX",
//                    "2022/12/01", 1, null);
//            Integer fakeEventId = EventQrys.getEventIdFromEventNameAndDate("FakeTestEvent", "2022/12/01");
//            List<Integer> test3 = EventQrys.getEventTeamsByEventId(fakeEventId);
//            System.out.println("FAKEEVENT TEAMS ARE: " + test3);
//
// NULL TESTS for getPastUserEventsFromUserIdInReverseChronologicalOrder(usrId)
//        AND getFutureUserEventsFromUserIdInChronologicalOrder(usrId)
//             Integer testAdd = UserQrys.postNewUser("TestingPostUser@gmail.com", "1234", 1, "Fake", "User"); // SHOULD RETURN 1 (meaning successfully added)
//            Integer usrId = UserQrys.getUserIdFromUsername("TestingPostUser@gmail.com");
//            List<Event> test = UserQrys.getFutureUserEventsFromUserIdInChronologicalOrder(usrId); // TEST FOR NULL
//            System.out.println("FUTURE EVENTS ARE: " + test);
//            List<Event> test2 = UserQrys.getPastUserEventsFromUserIdInReverseChronologicalOrder(usrId);
//            System.out.println("PAST EVENTS ARE: " + test2);
//            Integer testDelete = UserQrys.deleteUserById(usrId);
//
//  //TESTING EDIT EVENT METHODS
//            Integer fakeEvent = EventQrys.postNewEvent("FakeTestEvent", "Fake Descption",
//                    "168 Richmond Road", "Flat 3", "Cardiff", "CF23 3BX",
//                    "2022/12/01", 1, null);
//            Integer fakeEventId = EventQrys.getEventIdFromEventNameAndDate("FakeTestEvent", "2022/12/01");
//            System.out.println("Beginning tests");
//            Integer test1 = EventQrys.editEventName(fakeEventId, "UPDATE" );    // SHOULD RETURN 1
//            System.out.println("PASSED TEST 1");
//            Integer test2 = EventQrys.editDesc(fakeEventId, "UPDATE");      // SHOULD RETURN 1
//            System.out.println("PASSED TEST 2");
//            Integer test3 = EventQrys.editEventLoc(fakeEventId,"UPDATE", "UPDATE", "UPDATE", "UPDATE"); // SHOULD RETURN 1
//            System.out.println("PASSED TEST 3");
//            Integer test4 = EventQrys.editEventDate(fakeEventId, "2020/10/01"); // SHOULD RETURN 1
//            System.out.println("PASSED TEST 4");
//            Integer test5 = EventQrys.editEventStartTime(fakeEventId, "20:00:00");     // SHOULD RETURN 1
//            System.out.println("PASSED TEST 5");
//            Integer test6 = EventQrys.editEventEndTime(fakeEventId, "21:00:00");     // SHOULD RETURN 1
//            System.out.println("PASSED TEST 6");
//            Integer test7 = EventQrys.editEventMaxTeamSize(fakeEventId, 10);     // SHOULD RETURN 1
//            System.out.println("PASSED TEST 7");
//            System.out.println("PASSED ALL EDIT EVENT TESTS");
//            Event ev = EventQrys.getEventDetailsByEventId(fakeEventId);
//            System.out.println("UPDATED DETAILS ARE....");
//            System.out.println(ev.toString());
//            System.out.println("-----------");
//
// // TESTS FOR EDITING ATTENDANCE RECORD METHODS
//
//                Integer id = EventQrys.getAttendanceRecordIdFromUserIdAndEventId(1,1);
//            Integer test8 = EventQrys.editAttendanceRecordUserResponseToEvent(id, 3);
//            System.out.println("PASSED EDIT ATTENDANCE TEST 1");    // SHOULD RETURN 1
//            Integer test9 = EventQrys.editAttendanceRecordUserTeam(1, id);
//            System.out.println("PASSED EDIT ATTENDANCE TEST 2");    // SHOULD RETURN 1
//
//
//
//   /////////////////////////////////////////// NON-JDBC DB COMMUNICATION TESTS ///////////////////////////////////////////
//
//    public void alternativeMethodForTestingDBConnectiongWithoutJdbcTemplateObject() {
//        List<String> all = new ArrayList<>();
//        try {
//            String DB_URL = "jdbc:mysql://hodgedb.cvs32jldpw8f.eu-west-2.rds.amazonaws.com:3306/hodgedb?user=HodgeGroupOne&password=FakePassword101%3F"; //TODO
//            String USER = "HodgeGroupOne";
//            String PASS = "FakePassword101?";
//
//            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);  // establishing connection
//
//            String query = "select * from users";           // Selecting all rows from a table
//            ResultSet rs = conn.createStatement().executeQuery(query);  // executing the statement
//
//            while (rs.next()) { all.add( rs.getString("email")); };
//
//        } catch (Exception e) { System.out.println("error in jdbc connection" + e); }
//
//        System.out.println(all.toString());
//    }
    //public boolean checkAttendanceStatusVsTeamMembershipForConflicts() {
    // if record.teamId !- null
    // pritn ERR
    // set it to null
    //return false;
    //return true;
    // If 'attendance'.'response' = 2 ( meaning NO), 'attendance'.'teamId' must = null
    //}

