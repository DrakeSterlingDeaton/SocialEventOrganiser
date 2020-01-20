INSERT INTO `hodgedb`.`authorities` (username, authority)
VALUES  ('testUser101@gmail.com', "ROLE_USER"),
        ('testUser202@yahoo.com', "ROLE_USER"),
        ('testUser303@outlook.com', "ROLE_USER"),
        ('testUser404@gmail.com', "ROLE_USER"),
        ('testUser505@gmail.com', "ROLE_USER"),
        ('testUser606@gmail.com', "ROLE_USER"),
        ('testUser707@gmail.com', "ROLE_USER"),
        ('testUser808@gmail.com', "ROLE_USER"),
        ('testUser909@gmail.com', "ROLE_USER"),
        ('drake.s.deaton@gmail.com', "ROLE_USER");

INSERT INTO `hodgedb`.`users` (username, password, firstName, lastName, enabled)
VALUES  ('testUser101@gmail.com', "$2y$12$gX0VY71YUACdq1LmLr3e7egs4kA1yzbsqOPm8xPLGA2qgXuQ1bTKW", 'David', 'Taylor', 1),
        ('testUser202@yahoo.com', "$2y$12$gX0VY71YUACdq1LmLr3e7egs4kA1yzbsqOPm8xPLGA2qgXuQ1bTKW", 'John', 'Wilson', 1),
        ('testUser303@outlook.com', "$2y$12$gX0VY71YUACdq1LmLr3e7egs4kA1yzbsqOPm8xPLGA2qgXuQ1bTKW", 'Lisa', 'Fray', 1),
        ('testUser404@gmail.com', "$2y$12$gX0VY71YUACdq1LmLr3e7egs4kA1yzbsqOPm8xPLGA2qgXuQ1bTKW", 'Ashley', 'Johnston', 1),
        -- New data
        ('testUser505@gmail.com', "$2y$12$gX0VY71YUACdq1LmLr3e7egs4kA1yzbsqOPm8xPLGA2qgXuQ1bTKW", 'Josh', 'Milton', 1),
        ('testUser606@gmail.com', "$2y$12$gX0VY71YUACdq1LmLr3e7egs4kA1yzbsqOPm8xPLGA2qgXuQ1bTKW", 'Bill', 'Grey', 1),
        ('testUser707@gmail.com', "$2y$12$gX0VY71YUACdq1LmLr3e7egs4kA1yzbsqOPm8xPLGA2qgXuQ1bTKW", 'Peter', 'Banner', 1),
        ('testUser808@gmail.com', "$2y$12$gX0VY71YUACdq1LmLr3e7egs4kA1yzbsqOPm8xPLGA2qgXuQ1bTKW", 'Clark', 'Louis', 1),
        ('testUser909@gmail.com', "$2y$12$gX0VY71YUACdq1LmLr3e7egs4kA1yzbsqOPm8xPLGA2qgXuQ1bTKW", 'Rebecca', 'Carahar', 1),
        ('drake.s.deaton@gmail.com', "$2y$12$gX0VY71YUACdq1LmLr3e7egs4kA1yzbsqOPm8xPLGA2qgXuQ1bTKW", 'Drake', 'Deaton', 1);
        -- End of new data

INSERT INTO `hodgedb`.`events` (eventName, eventDesc, eventLocSt1, eventLocSt2, eventLocCity, eventLocPost,
                                eventStartDate, eventEndDate, eventStartTime, eventEndTime, eventOrganiser, dateCreated, maxTeamSize, eventColour)
VALUES ("David's Birthday Bash", "David's turning 40 - let's celebrate!", "162 Richmond Road", "", "Cardiff",
        "CF24 3BX", "2019-12-10", "2019-12-10", "17:00:00", "17:00:00",  5, curdate(), null, "red"),
        ("Pub Quiz", "The monthly pub quiz is back! Sign up your teams and come on down to Tiny Rebel.", "25 Westgate St", "", "Cardiff",
        "CF10 1DD", "2019-11-29", "2019-11-29", "17:00:00", "17:00:00", 4, curdate(), 8, "navy"),
        ("Ping Pong Competition", "For all table tennis lovers - another tournment is begins!", "Unit 4, Dominion Way", "Newport Rd", "Cardiff",
        "CF24 1RF", "2019-12-10", "2019-12-10", "17:00:00", "17:00:00",  2, curdate(), null, "red"),
        ("Team Frisbee Golf", "Just because it's winter, doesn't mean we have to stop playing Frisbee Golf!", "4 Blackberry Hill", "", "Bristol",
        "BS16 1DB", "2019-12-02", "2019-12-02", "17:00:00", "17:00:00",  4, curdate(), 4, "navy"),
        ("Christmas Party", "The Christmas Party is upon us once again! Santa outfits permitted.", "4-5 Mount Stuart Square", "", "Cardiff",
        "CF10 5FQ", "2019-12-23", "2019-12-23", "17:00:00", "17:00:00",  4, curdate(), null, "red"),
        -- New data
        ("Office Pantomine", "Bring your kids if you've got them, hillarity will undoubtedly be ensuring", "4-5 Mount Stuart Square", "", "Cardiff",
        "CF10 5FQ", "2019-12-20", "2019-12-20", "19:00:00", "22:00:00",  2, curdate(), null, "red"),
        ("Hodge Bank Book Club", "This month we'll be discussing Pride and Prejudice. Coffee and cake is provided.", "100 Richmond Road", "", "Cardiff",
        "CF24 3BW", "2019-12-9", "2019-12-9", "19:00:00", "22:00:00",  5, curdate(), null, "red"),
        ("Hodge Yoga Retreat", "For all abilities.", "Woodland Retreat Yoga Centre", "148 Fidlas Rd", "Cardiff",
        "CF14 0NE", "2019-12-6", "2019-12-8", "19:00:00", "12:00:00",  4, curdate(), null, "red"),
        ("Hodge Hackathon", "Teams have 48 hours to hack our system. Prizes for the top teams included.", "One Central Square", "", "Cardiff",
        "CF10 1FS", "2019-11-29", "2019-12-1", "19:00:00", "19:00:00",  4, curdate(), 4, "navy"),
        ("Take Your Dog To Work Day", "Bring your fury friends to work! Dog food and drink will be provided.", "One Central Square", "", "Cardiff",
        "CF10 1FS", "2019-12-12", "2019-12-12", "9:00:00", "17:00:00",  5, curdate(), null, "red"),
        ("Office Paintball", "Two teams. One office. And one whole day of paintballing", "One Central Square", "", "Cardiff",
        "CF10 1FS", "2019-12-13", "2019-12-13", "9:00:00", "17:00:00",  5, curdate(), 125, "navy"),
        ("Department Dodgeball", "Each department will compete for the grand prize. Don't forget the 5 D's of dodgeball", "One Central Square", "", "Cardiff",
        "CF10 1FS", "2019-12-14", "2019-12-14", "9:00:00", "17:00:00",  5, curdate(), 25, "navy");
        -- End new data

INSERT INTO `hodgedb`.`teams` (eventId, teamName)
VALUES  (2, "The Quizinators"), /* Pub Quiz*/
        (2, "Questionable Tatics"),
        (2, "The Gentle Gentlemen"),
        (4, "David & Co."), /* Team Frisbee Golf */
        (4, "Team Win");

INSERT INTO `hodgedb`.`attendance` (userId, eventId, response, teamId)
VALUES  (1, 1, 1, null),  /* David's Birthday Bash */
        (2, 1, 1, null),
        (3, 1, 2, null),
        (4, 1, 3, null),
        (5, 1, 1, null),
        (1, 2, 1, 1),         /* Pub Quiz */
        (2, 2, 1, 1),
        (3, 2, 1, 1),
        (4, 2, 3, null),
        (5, 2, 1, 3),
        (1, 3, 1, null),
        (2, 3, 3, null),     /* Ping Pong Competition */
        (3, 3, 3, null),
        (5, 3, 1, null),
        (1, 4, 1, 4),           /* Team Frisbee */
        (2, 4, 3, null),
        (3, 4, 1, null),
        (4, 4, 1, 5),
        (5, 4, 1, 5),
        (1, 5, 2, null),        /* Christmas Party */
        (2, 5, 1, null);

INSERT INTO `hodgedb`.`groups` (groupName)
VALUES  ("IT Department"),
        ("HR Department")

INSERT INTO `hodgedb`.`group_members` (groupId, userId)
VALUES  (1, 1),
        (1, 2),
        (2, 3),
        (2, 4),
        (2, 5);