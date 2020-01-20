$(document).ready(function () {

    // gives the group/individual event icon
    $(".team-icon").each(function() {
        if($(this).text() == 0){
            $(this).addClass("fa-user");
        }else{
            $(this).addClass("fa-users")
        }
        $(this).empty();
    });

    $('span[name="startTimeEvent"]').each(function() {

        var StartTime = $(this).text();
        StartTime =  StartTime.substring(0, 5);
       $(this).empty;
       $(this).text(StartTime);

    });

    // saves the preference of the toggle button
    $("#toggle-state").change(function(){
        if($("#toggle-state").is(":checked")){
            $(".postEventsAll").removeClass("post-none-toggle");
            $(".postEventsUsers").addClass("post-none-toggle");
            // Store
            localStorage.setItem("toggle", $("#toggle-state").is(":checked"));
        }else{
            $(".postEventsAll").addClass("post-none-toggle");
            $(".postEventsUsers").removeClass("post-none-toggle");
            // Store
            localStorage.setItem("toggle", $("#toggle-state").is(":checked"));
        }
    });

     // Check browser support
    if (typeof(Storage) !== "undefined") {

        // gets the value of the toggle preference from the Local Storage and applies it to the toggle button
        if(localStorage.getItem("toggle")== "true"){
            $("#toggle-state").bootstrapToggle('on')
            $(".postEventsAll").removeClass("post-none-toggle")
            $(".postEventsUsers").addClass("post-none-toggle")

        }else{
            $("#toggle-state").bootstrapToggle('off')
            $(".postEventsAll").addClass("post-none-toggle")
            $(".postEventsUsers").removeClass("post-none-toggle")
        }

    } else {
        $("#toggle-state").innerHTML = "Sorry, your browser does not support Web Storage...";
    }

});



    function searchFunction(){
        var searchValue = $("input[name='search_value']").val();
        var feedTitles = [];
        var increment = 0;
        $(".card").each(function() {
            $(this).removeClass("post-none-search post-block-search");
        });
        $(".eventNoMatches").addClass("post-none-search")
        $(".eventNoMatchesText").addClass("post-none-search")


        $.ajax({ type: "GET",url: "http://localhost:8080/api/get/events?sortBy=chronological", success: function(data){

                for(var i = 0; i < data.length; i++){
                    feedTitles.push(data[i].title);
                }

                //search for variable
                for(var ii = 0 ; ii < feedTitles.length; ii++) {
                    if (feedTitles[ii].toLowerCase().includes(searchValue.toLowerCase())) {
                        $(".card-title").each(function() {
                            if($(this).text() == feedTitles[ii]){
                                $(this).closest(".card").addClass("post-block-search")
                            }
                        });

                    }
                }

                $(".card").each(function() {
                    if(!$(this).hasClass("post-block-search")){
                        $(this).addClass("post-none-search")
                        increment++
                    }
                    if($(".eventNoMatches").hasClass("post-none-search")) {
                        if ($(".card").length == increment) {
                            $(".eventNoMatches").removeClass("post-none-search")
                            $(".eventNoMatchesText").removeClass("post-none-search")
                        }
                    }
                });

            }});


        $.ajax({ type: "GET",url: "http://localhost:8080/post/attendance", success: function(data){

                for(var i = 0; i < data.length; i++){
                    feedTitles.push(data[i].title);
                }

                //search for variable
                for(var ii = 0 ; ii < feedTitles.length; ii++) {
                    if (feedTitles[ii].toLowerCase().includes(searchValue.toLowerCase())) {
                        $(".card-title").each(function() {
                            if($(this).text() == feedTitles[ii]){
                                $(this).closest(".card").addClass("post-block-search")
                            }
                        });

                    }
                }

                $(".card").each(function() {
                    if(!$(this).hasClass("post-block-search")){
                        $(this).addClass("post-none-search")
                        increment++
                    }
                    if($(".eventNoMatches").hasClass("post-none-search")) {
                        if ($(".card").length == increment) {
                            $(".eventNoMatches").removeClass("post-none-search")
                            $(".eventNoMatchesText").removeClass("post-none-search")
                        }
                    }
                });

            }});
    };


//HTTP REQUEST METHODS
function postAttendanceUpdateRecord( userId, eventId, response, attendanceId, el) {

    var baseUri = "/post/attendance";
    var userId_url = "userId=" + userId;
    var eventId_url = "&eventId=" + eventId;
    var response_url = "&response=" + response;
    var attendance_url = "&attendanceId=" + attendanceId;
    var fullUri = baseUri + "?" + userId_url + eventId_url + response_url + attendance_url;

    var token = $("meta[name='_csrf']").attr("content");    // Used to bypass SPring Boot's CSRF protocol     -- SOlution taken from 'https://stackoverflow.com/questions/34747437/use-of-spring-csrf-with-ajax-rest-call-and-html-page-with-thymeleaf' on Nov 26th 2019
    var header = $("meta[name='_csrf_header']").attr("content");    // Used to bypass SPring Boot's CSRF protocol


    $.ajax({ type: "POST",url: fullUri , beforeSend: function(xhr) { xhr.setRequestHeader(header, token); }, success: function(data){
            console.log(data)
            if ( data === true ) {
                //Change the buttons to active
                $("form[name='"+eventId+"']").children().removeClass("attendanceButtons-active")
                $("form[name='"+eventId+"']").children().addClass("attendanceButtons")
                $(el).addClass("attendanceButtons-active").removeClass("attendanceButtons");

            } else { alert("There was an error, please try again.") }
        },
        error: function(data) { alert("FAIL"); alert(data.responseText); }

    });
}



//HTTP REQUEST METHODS
function postAttendanceAddRecord( userId, eventId, response, el) {

    var baseUri = "/post/attendance";
    var userId_url = "userId=" + userId;
    var eventId_url = "&eventId=" + eventId;
    var response_url = "&response=" + response;
    var fullUri = baseUri + "?" + userId_url + eventId_url + response_url;

    var token = $("meta[name='_csrf']").attr("content");    // Used to bypass SPring Boot's CSRF protocol     -- SOlution taken from 'https://stackoverflow.com/questions/34747437/use-of-spring-csrf-with-ajax-rest-call-and-html-page-with-thymeleaf' on Nov 26th 2019
    var header = $("meta[name='_csrf_header']").attr("content");    // Used to bypass SPring Boot's CSRF protocol


    $.ajax({ type: "POST",url: fullUri , beforeSend: function(xhr) { xhr.setRequestHeader(header, token); }, success: function(data){
            console.log(data)
            if ( data === true ) {
                //Change the buttons to active
                $("form[name='"+eventId+"']").children().removeClass("attendanceButtons-active")
                $("form[name='"+eventId+"']").children().addClass("attendanceButtons")
                $(el).addClass("attendanceButtons-active").removeClass("attendanceButtons");
                location.reload();
            } else { alert("There was an error, please try again.") }
        },
        error: function(data) { alert("FAIL"); alert(data.responseText); }

    });

}


