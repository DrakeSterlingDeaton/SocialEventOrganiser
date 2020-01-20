var eventList = [
  // const marketing = "red";
  // const it = "blue";
  // const sales = "yellow";
  // const all = "#223b47";
  {
    id: 1,
    title: 'Pub Golf',
    start: '2019-11-12T22:30:00',
    end: '2019-11-13T04:30:00',
    attendance: 1,
    department: 'Marketing',
    location: "National Software Academy, Newport",
    eventType: "Team",
    backgroundColor: "red",
    description: 'Friendly get together. Drink responsibly.',
    url: "eventPage"
  },
  {
    id: 2,
    title: 'Pub Quiz',
    start: '2019-11-20T19:30:00',
    end: '2019-11-20T22:30:00',
    attendance: 2,
    department: 'all',
    location: "National Software Academy, Newport",
    eventType: "Individual",
    backgroundColor: "blue",
    description: 'Pub quiz for all departments',
    url: "eventPage"
  },
  {
    id: 3,
    title: 'Social Meal',
    start: '2019-11-26T19:30:00',
    end: '2019-11-26T22:30:00',
    attendance: 0,
    department: 'All',
    location: "National Software Academy, Newport",
    eventType: "Individual",
    backgroundColor: "#223b47",
    description: 'AYCE',
    url: "eventPage"
  },
  {
    id: 4,
    title: 'Karaoke',
    start: '2019-10-31T19:30:00',
    end: '2019-10-31T22:30:00',
    attendance: 3,
    department: 'All',
    location: "National Software Academy, Newport",
    eventType: "Individual",
    backgroundColor: "#223b47",
    description: 'Some description',
    url: "eventPage"
  },
  {
    id: 5,
    title: '5 a Side',
    start: '2019-11-29T19:30:00',
    end: '2019-11-29T22:30:00',
    attendance: 1,
    department: 'All',
    location: "National Software Academy, Newport",
    eventType: "Team",
    backgroundColor: "#223b47",
    description: '1 hour session at the sports centre',
    url: "eventPage"
  }

];
var token = $("meta[name='_csrf']").attr("content");    // Used to bypass SPring Boot's CSRF protocol     -- SOlution taken from 'https://stackoverflow.com/questions/34747437/use-of-spring-csrf-with-ajax-rest-call-and-html-page-with-thymeleaf' on Nov 26th 2019
var header = $("meta[name='_csrf_header']").attr("content");    // Used to bypass SPring Boot's CSRF protocol

var attendance=[];
$.ajax({
  type:"GET",
  url: "/api/get/user/attendance?userId=" + localStorage.getItem("userId"),
  dataType:"json",
  async: false,   //Only works when set to false.
  // beforeSend: function (xhr) {
  //   xhr.setRequestHeader(header, token);
  // },
  success: function (data) {
    attendance = data;
  }
});


function changeToGoing(eventId){

  var statusText = document.getElementById("aStatus" + eventId);
  statusText.innerText = "Going";
  statusText.classList.remove("maybe","cantgo");
  statusText.classList.add("going");

  var buttonGoing = document.getElementById("going" + eventId);
  var buttonMaybe = document.getElementById("maybe" + eventId);
  var buttonCantGo = document.getElementById("cantgo" + eventId);

  buttonGoing.classList.remove("washed");
  buttonCantGo.classList.add("washed");
  buttonMaybe.classList.add("washed");

  var attdId = -1;
  var attdResponse = -1;

  $.ajax({
    type:"GET",
    url: "http://localhost:8080/api/get/attendance/record?userId=" + userId +"&eventId=" + eventId,
    dataType:"json",
    async: false,   //Only works when set to false.
    beforeSend: function (xhr) {
      xhr.setRequestHeader(header, token);
    },
    success: function (data) {
      attdId = data.attendanceId;
      attdResponse = data.response;
      // console.log(data);
      // console.log("SUCCESS");
    },
  });

  console.log("ID: " +attdId);
  console.log("Response: " +attdResponse);


  if(attdResponse === 1){
    return;
  }


  if(attdId === null || attdId === -1){
    var res = $.ajax({
      type: "get",
      url: "http://localhost:8080/api/post/attendance/new" +
          "?userId=" + userId +"&eventId=" + eventId + "&response=" + 1,
      dataType: "text",
      beforeSend: function (xhr) {
        xhr.setRequestHeader(header, token);
      },
      success: function (data) {
        // alert(data.responseText);
        // rerenderEvents("http://localhost:8080/api/get/events");
        location.reload();
      },
      error: function (data) {
        // alert(data.responseText);
      }
    });
  }
  else {
    var res = $.ajax({
      type: "get",
      url: "http://localhost:8080/api/post/attendance/update" + "?userId=" + userId +
          "&eventId=" + eventId + "&response=" + 1 + "&attendanceId=" + attdId,
      dataType: "text",
      beforeSend: function (xhr) {
        xhr.setRequestHeader(header, token);
      },
      success: function (data) {
        // alert(data.responseText);
        // rerenderEvents("http://localhost:8080/api/get/events");
        location.reload();
      },
      error: function (data) {
        // alert(data.responseText);
      }
    });
  }
}

function changeToMaybe(eventId){

  var statusText = document.getElementById("aStatus" + eventId);
  statusText.innerText = "Maybe";
  statusText.classList.remove("going","cantgo");
  statusText.classList.add("maybe");

  var buttonGoing = document.getElementById("going" + eventId);
  var buttonMaybe = document.getElementById("maybe" + eventId);
  var buttonCantGo = document.getElementById("cantgo" + eventId);

  buttonMaybe.classList.remove("washed");
  buttonCantGo.classList.add("washed");
  buttonGoing.classList.add("washed");

  var attdId = -1;
  var attdResponse = -1;

  $.ajax({
    type:"GET",
    url: "http://localhost:8080/api/get/attendance/record?userId=" + userId +"&eventId=" + eventId,
    dataType:"json",
    async: false,   //Only works when set to false.
    beforeSend: function (xhr) {
      xhr.setRequestHeader(header, token);
    },
    success: function (data) {
      attdId = data.attendanceId;
      attdResponse = data.response;
      // console.log(data);
      // console.log("SUCCESS");
    },
  });

  console.log("ID: " +attdId);

  if(attdResponse === 3){
    return;
  }

  if(attdId === null || attdId === -1){
    var res = $.ajax({
      type: "get",
      url: "http://localhost:8080/api/post/attendance/new" +
          "?userId=" + userId +"&eventId=" + eventId + "&response=" + 3,
      dataType: "text",
      beforeSend: function (xhr) {
        xhr.setRequestHeader(header, token);
      },
      success: function (data) {
        // alert(data.responseText);
        // rerenderEvents("http://localhost:8080/api/get/events");
        location.reload();
      },
      error: function (data) {
        // alert(data.responseText);
      }
    });
  }
  else {
    var res = $.ajax({
      type: "get",
      url: "http://localhost:8080/api/post/attendance/update" + "?userId=" + userId +
          "&eventId=" + eventId + "&response=" + 3 + "&attendanceId=" + attdId,
      dataType: "text",
      beforeSend: function (xhr) {
        xhr.setRequestHeader(header, token);
      },
      success: function (data) {
        // alert(data.responseText);
        // rerenderEvents("http://localhost:8080/api/get/events");
        location.reload();
      },
      error: function (data) {
        // alert(data.responseText);
      }
    });
  }

}

function changeToCantGo(eventId){

  var statusText = document.getElementById("aStatus" + eventId);
  statusText.innerText = "Can't Go";
  statusText.classList.remove("going","maybe");
  statusText.classList.add("cantgo");

  var buttonGoing = document.getElementById("going" + eventId);
  var buttonMaybe = document.getElementById("maybe" + eventId);
  var buttonCantGo = document.getElementById("cantgo" + eventId);

  buttonCantGo.classList.remove("washed");
  buttonMaybe.classList.add("washed");
  buttonGoing.classList.add("washed");

  var attdId = -1;
  var attdResponse = -1;

  $.ajax({
    type:"GET",
    url: "http://localhost:8080/api/get/attendance/record?userId=" + userId +"&eventId=" + eventId,
    dataType:"json",
    async: false,   //Only works when set to false.
    beforeSend: function (xhr) {
      xhr.setRequestHeader(header, token);
    },
    success: function (data) {
      attdId = data.attendanceId;
      attdResponse = data.response;
    },
  });

  console.log("ID: " +attdId);

  if(attdResponse === 2){
    return;
  }

  if(attdId === null || attdId === -1){
    var res = $.ajax({
      type: "get",
      url: "http://localhost:8080/api/post/attendance/new" +
          "?userId=" + userId +"&eventId=" + eventId + "&response=" + 2,
      dataType: "text",
      beforeSend: function (xhr) {
        xhr.setRequestHeader(header, token);
      },
      success: function (data) {
        // alert(data.responseText);
        // rerenderEvents("http://localhost:8080/api/get/events");
        location.reload();
      },
      error: function (data) {
        // alert(data.responseText);
      }
    });
  }
  else {
    var res = $.ajax({
      type: "get",
      url: "http://localhost:8080/api/post/attendance/update" + "?userId=" + userId +
          "&eventId=" + eventId + "&response=" + 2 + "&attendanceId=" + attdId,
      dataType: "text",
      beforeSend: function (xhr) {
        xhr.setRequestHeader(header, token);
      },
      success: function (data) {
        // alert(data.responseText);
        // rerenderEvents("http://localhost:8080/api/get/events");
        location.reload();
      },
      error: function (data) {
        // alert(data.responseText);
      }
    });
  }

}


document.addEventListener('DOMContentLoaded', function() {

  $(function(){ $('#calToggle').bootstrapToggle() });

  var calendarEl = document.getElementById('calendar');

  var calendar = new FullCalendar.Calendar(calendarEl, {
    plugins: ['interaction', 'dayGrid', 'timeGrid', 'list', 'bootstrap'],
    defaultView: 'dayGridMonth',
    datesRender: function (info) {
      document.getElementById("calendarTitle").innerHTML = info.view.title;
    },
    events: "http://localhost:8080/api/get/events",
    displayEventTime: false,
    height: 'parent',
    themeSystem: 'bootstrap',
    buttonText: {
      month: 'Month',
      week: 'Week',
      day: 'Day',
      list: 'List',
      today: 'Now'
    },
    header: {
      left: 'dayGridMonth,timeGridDay,listWeek,today',
      right: 'prev,next'
    },
    navLinks: true, // can click day/week names to navigate views
    eventLimit: true, // allow "more" link when too many events
    eventRender: function (info) {
      var attendanceString;
      var attendanceClass;
      var teamString;
      var teamClass;

      function capString(newString, cap) {
        if (newString.length > cap) {
          return newString.substring(0, cap) + "...";
        }
        return newString;
      }

      function padTime(time) {
        if (time < 10) {
          return "0" + time;
        }

        return time;
      }

      if (info.event.extendedProps.maxTeamSize > 0) {
        teamClass = 'fas fa-users';
        teamString = 'Team Event'
      } else {
        teamClass = 'fas fa-user';
        teamString = 'Individual Event'
      }

      attendanceString=" ";
      attendanceClass = " ";
      var buttons="";

      for(var i=0;i<attendance.length;i++){
        if(attendance[i].eventId == info.event.id){
          if (attendance[i].response === 1) {
            attendanceString = "Going";
            attendanceClass = "going";
            buttons = "<span><button type='submit' value='going' id='going" + info.event.id + "' class='btn btn-success' onclick='changeToGoing(" + info.event.id + ")'>Going</button></span>" +
                "<span><button type='submit' value='maybe' id='maybe" + info.event.id + "' class='btn btn-warning text-white washed' onclick='changeToMaybe(" + info.event.id + ")'>Maybe</button></span>" +
                "<span><button type='submit' value='cantgo' id='cantgo" + info.event.id + "' class='btn btn-danger washed' onclick='changeToCantGo(" + info.event.id + ")'>Can't Go</button></span>";
          } else if (attendance[i].response === 2) {
            attendanceString = "Can't Go";
            attendanceClass = "cantgo";
            buttons = "<span><button type='submit' value='going' id='going" + info.event.id + "' class='btn btn-success washed' onclick='changeToGoing(" + info.event.id + ")'>Going</button></span>" +
                "<span><button type='submit' value='maybe' id='maybe" + info.event.id + "' class='btn btn-warning text-white washed' onclick='changeToMaybe(" + info.event.id + ")'>Maybe</button></span>" +
                "<span><button type='submit' value='cantgo' id='cantgo" + info.event.id + "' class='btn btn-danger' onclick='changeToCantGo(" + info.event.id + ")'>Can't Go</button></span>";
          } else if (attendance[i].response === 3) {
            attendanceString = "Maybe";
            attendanceClass = "maybe";
            buttons = "<span><button type='submit' value='going' id='going" + info.event.id + "' class='btn btn-success washed' onclick='changeToGoing(" + info.event.id + ")'>Going</button></span>" +
                "<span><button type='submit' value='maybe' id='maybe" + info.event.id + "' class='btn btn-warning text-white' onclick='changeToMaybe(" + info.event.id + ")'>Maybe</button></span>" +
                "<span><button type='submit' value='cantgo' id='cantgo" + info.event.id + "' class='btn btn-danger washed' onclick='changeToCantGo(" + info.event.id + ")'>Can't Go</button></span>";
          } else {
            attendanceString = " ";
            buttons = "<span><button type='submit' value='going' id='going" + info.event.id + "' class='btn btn-success' onclick='changeToGoing(" + info.event.id + ")'>Going</button></span>" +
                "<span><button type='submit' value='maybe' id='maybe" + info.event.id + "' class='btn btn-warning text-white' onclick='changeToMaybe(" + info.event.id + ")'>Maybe</button></span>" +
                "<span><button type='submit' value='cantgo' id='cantgo" + info.event.id + "' class='btn btn-danger' onclick='changeToCantGo(" + info.event.id + ")'>Can't Go</button></span>";
          }
          break;
        }
        else {
          attendanceString = " ";
          buttons = "<span><button type='submit' value='going' id='going" + info.event.id + "' class='btn btn-success' onclick='changeToGoing(" + info.event.id + ")'>Going</button></span>" +
              "<span><button type='submit' value='maybe' id='maybe" + info.event.id + "' class='btn btn-warning text-white' onclick='changeToMaybe(" + info.event.id + ")'>Maybe</button></span>" +
              "<span><button type='submit' value='cantgo' id='cantgo" + info.event.id + "' class='btn btn-danger' onclick='changeToCantGo(" + info.event.id + ")'>Can't Go</button></span>";
        }
      }

      $(info.el).popover({
        title: "<strong><a class='popover-title' href='" + info.event.url + "'>" + capString(info.event.title, 30) + "</a></strong>" + "<strong id='aStatus" + info.event.id + "' class='attendanceStatus " + attendanceClass + "'>" + attendanceString + "</strong>",
        placement: 'bottom',
        html: true,
        animation: false,
        sanitize: false,
        content:
            "<strong>Date:</strong> " + capString(info.event.start.toDateString(), 30) + "<span class='eventIcon " + teamClass + "' title=" + teamString + " aria-hidden=true></span><br>" +
            "<strong>Start Time:</strong> " + padTime(info.event.start.getHours()) + ":" +
            padTime(info.event.start.getMinutes()) + "<br>" +
            "<strong>End Time:</strong> " + padTime(info.event.end.getHours()) + ":" +
            padTime(info.event.end.getMinutes()) + "<br>" +
            "<strong>Description:</strong> " + capString(info.event.extendedProps.description, 100) + "<br>" +
            "<strong>Location:</strong> " + capString(info.event.extendedProps.eventLocCity, 20) + "<br><br>" +
            "<div class='popover-btn-container'>" + buttons + "</div>",
        trigger: "manual"
      }).on("mouseenter", function () {
        var _this = this; // _this points to the outer "this", which is the html element of the event in this specific case
        $(this).popover("show");
        $(".popover").on("mouseleave", function () {
          $(_this).popover('hide');
        });
      }).on("mouseleave", function () {
        var _this = this;
        setTimeout(function () {
          if (!$(".popover:hover").length || !$(".popover:hover").trigger) {
            $(_this).popover("hide");
          }
        }, 300);
      });
    }
  });
  calendar.render();
////////////////////////////////////////////////////////////////////

  if(localStorage.getItem("calEventView") === "1"){
    rerenderEvents("http://localhost:8080/api/get/user/attending?userId=" + userId);
    $("#calToggle").bootstrapToggle("off", true);
  }else{
    rerenderEvents("http://localhost:8080/api/get/events");
    $("#calToggle").bootstrapToggle("on", true);
  }

  $("#calToggle").change(function(){
    if($(this).prop("checked") === true){
      localStorage.setItem("calEventView", "0");
      rerenderEvents("http://localhost:8080/api/get/events");
    }else{
      localStorage.setItem("calEventView", "1");
      rerenderEvents("http://localhost:8080/api/get/user/attending?userId=" + userId);
    }
  });

  function rerenderEvents(source){
    calendar.removeAllEventSources();
    calendar.addEventSource(source);
    calendar.refetchEvents();
  }

});





