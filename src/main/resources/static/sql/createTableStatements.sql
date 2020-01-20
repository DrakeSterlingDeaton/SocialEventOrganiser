CREATE TABLE `hodgedb`.`users` (
    userId int NOT NULL AUTO_INCREMENT, /* PK */
    email varchar(50)   NOT NULL,
    password varchar(70)    NOT NULL,
    firstName varchar(30)   NOT NULL,
    lastName varchar(30)    NOT NULL,
    enabled tinyint     NOT NULL,
    PRIMARY KEY (userId)
);

CREATE TABLE `hodgedb`.`authorities` (
    username varchar(50)    NOT NULL,
    authority varchar(50)   NOT NULL,
    PRIMARY KEY (username, authority)
);

CREATE TABLE `hodgedb`.`events` (
    eventId int NOT NULL AUTO_INCREMENT, /* PK */
    eventName varchar(50)   NOT NULL,
    eventDesc varchar(500)  NOT NULL,
    eventLocSt1 varchar(100)    NOT NULL,
    eventLocSt2 varchar(100),
    eventLocCity varchar(50)    NOT NULL,
    eventLocPost varchar (10)   NOT NULL,
    eventStartDate date  NOT NULL,
    eventEndDate date  NOT NULL,
    eventStartTime time NOT NULL,
    eventEndTime time   NOT NULL,
    eventOrganiser int  NOT NULL, /* FK */
    dateCreated date    NOT NULL,
    maxTeamSize int,
    eventColour varchar(20) NOT NULL,
    PRIMARY KEY (eventId),
    FOREIGN KEY (eventOrganiser) REFERENCES users(userId)
);

CREATE TABLE `hodgedb`.`teams` (
    teamId int AUTO_INCREMENT, /* PK */
    eventId int, /* FK */
    teamName varchar(50)    NOT NULL,
    PRIMARY KEY (teamId),
    FOREIGN KEY (eventId) REFERENCES events(eventId)
);

CREATE TABLE `hodgedb`.`attendance` (
    attendanceId int NOT NULL AUTO_INCREMENT, /* PK */
    userId int, /* FK */
    eventId int, /* FK */
    response smallint NOT NULL,      /* 1 = going, 2 = not going, 3 = maybe */
    teamId int, /* References a table containing details of teams for specific events. Can be NULL too */
    PRIMARY KEY (attendanceID),
    FOREIGN KEY (userId) REFERENCES users(userId)
    FOREIGN KEY (eventId) REFERENCES events(eventId)
    FOREIGN KEY (teamId) REFERENCES teams(teamId)
    ON DELETE CASCADE;
);

CREATE TABLE `hodgedb`.`groups` (
    groupId int NOT NULL AUTO_INCREMENT, /* PK */
    groupName varchar(50)   NOT NULL,
    PRIMARY KEY (groupId)
);

CREATE TABLE `hodgedb`.`group_members` (
    membershipId int NOT NULL AUTO_INCREMENT, /* PK */
    groupId int, /* FK */
    userId int, /* FK */
    PRIMARY KEY (membershipId),
    FOREIGN KEY (groupId) REFERENCES groups(groupId)
    FOREIGN KEY (userId) REFERENCES users(userId)
);
