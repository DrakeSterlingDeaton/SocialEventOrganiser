package eventorganiser.dir.DBMethods.DBClasses;

import java.util.ArrayList;
import java.util.List;

public class User {

    private int userId;
    private String username;
    private String password;
    private String firstName;
    private String lastName;

    public User(int userId, String username, String password, String firstName, String lastName) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(int userId, String username, String firstName, String lastName) {
        this.userId = userId;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User() {
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() { return password; }

    public String getFirstName() { return firstName; }

    public String getLastName() { return lastName; }

    public List<Object> getListOfAllInfo() {   // Returns in order: [userId, username, password, firstName, lastName]
        List<Object> li = new ArrayList<>();
        li.add(userId);
        li.add(username);
        li.add(password);
        li.add(firstName);
        li.add(lastName);
        return li;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAllFields(int userId, String username, String password, String firstName, String lastName) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
