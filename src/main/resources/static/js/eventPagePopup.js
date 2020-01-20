var token = $("meta[name='_csrf']").attr("content");    // Used to bypass SPring Boot's CSRF protocol     -- SOlution taken from 'https://stackoverflow.com/questions/34747437/use-of-spring-csrf-with-ajax-rest-call-and-html-page-with-thymeleaf' on Nov 26th 2019
var header = $("meta[name='_csrf_header']").attr("content");    // Used to bypass SPring Boot's CSRF protocol

function changePopup() {
    var popupId = "#" + localStorage.getItem("popup");
    $(popupId).css("display", "block");
}

function addTeammatesToDisplay() {
    var teammatesLi = localStorage.getItem("eventPageTeamMates").split(",");   // DONT ALLOW TEAM NAMES TO HAVE COMMAS
    var teammatesUl = $("#teammatesLi");
    teammatesUl.empty();
    for (var i in teammatesLi) {
        if (teammatesLi[i] !== '') {
            teammatesUl.append("<li>" + teammatesLi[i] + "</li>");
        }
    }
}

function addTeamsForEventToDisplay(){
    var teamsLi = localStorage.getItem("eventPageTeams").split(",");
    var teamsUl = $("#teamsList");
    teamsUl.empty();
    for(var team in teamsLi){
        teamsUl.append("<option>" + teamsLi[team] + "</option>");
    }
}

function getTeamName(){
    var teamName = document.getElementById("createTeamTextBox").valueOf();
    return teamName;
}

function postNewTeam() {
    var eventId = "?eventId=" + localStorage.getItem("eventId");
    var teamName = getTeamName().value;
    var flag = false;
    var allTeams = localStorage.getItem("eventPageTeams").split(",");
    for(var i=0;i<allTeams.length;i++){
        allTeams[i] = allTeams[i].trim();
        if(allTeams[i].toLocaleLowerCase() === teamName.toLowerCase()){
            flag = true;
            break;
        }
    }

    if(flag === true){
        alert("A team already exists with that name, please choose another.");
        return;
    }


    teamName = teamName.replace(/ /g, "%20");   //Check for spaces and replace with %20
    var Uri = "http://localhost:8080/api/post/team/new" + eventId + "&teamName=" + teamName;
    console.log(teamName);
    console.log(Uri);
    var res = $.ajax({
        type: "get",
        url: Uri,
        dataType:"text",
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            alert(data.responseText);
            // location.reload();
        },
        error: function (data) {
            alert(data.responseText);
        }
    });
    console.log(res);
}

function changeTeam() {
    var list = document.getElementById("teamsList");
    var newTeamName = list.options[list.selectedIndex].text;
    newTeamName = newTeamName.replace(/ /g, "%20");   //Check for spaces and replace with %20
    var uri = "http://localhost:8080/api/get/event/team?teamName=" + newTeamName + "&eventId=" + localStorage.getItem("eventId");
    console.log("URI:" + uri);
    var newTeamId = -1;
    $.ajax({
        type:"GET",
        url: uri,
        dataType:"text",
        async: false,   //Only works when set to false.
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            newTeamId = data;
            console.log("SUCCESS");
            parent.location.reload();
        },
        error: function (data) {
            alert(data.responseText);
        }
    });

    console.log(newTeamId);
    var sendToDbUri = "/api/post/event/team/update?userId=" + localStorage.getItem("userId") + "&eventId=" + localStorage.getItem("eventId") + "&teamId=" + newTeamId;
    console.log(sendToDbUri);
    $.ajax({
        type:"GET",
        url: sendToDbUri,
        dataType:"text",
        async: false, //Only works when set to false.
        beforeSend: function (xhr) {
            xhr.setRequestHeader(header, token);
        },
        success: function (data) {
            console.log("SUCCESS");
        },
        error: function (data) {
            alert(data.responseText);
        }
    });
}

function addAttendeesToDisplay() {
    var attendeesLi = localStorage.getItem("eventPageEventAttendees").split(",");
    var attendeesUl = $("#attendeesLi");
    attendeesUl.empty();
    for (var i in attendeesLi) {
        if (attendeesLi[i] !== '') {
            attendeesUl.append("<li>" + attendeesLi[i] + "</li>");
        }
    }
}

$(document).ready(function() {
    $(".close").on("click", function() {
        var popupId = "#" + localStorage.getItem("popup");
        $(popupId).css("display", "none");
        parent.closeIFrame()
    });
});
