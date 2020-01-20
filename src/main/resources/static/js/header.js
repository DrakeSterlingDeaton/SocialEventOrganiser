$(document).ready(function () {

    var path = window.location.pathname;
    var page = path.split("/").pop().split(".")[0];
    //var height = ($("nav.navbar").height() / 2) - 10; // the value of navbar devided by two minus the font size
    $("#" + page).addClass("active-menu active");
   // $(".nav-link").css("padding-top", height);
    //$(".nav-link").css("padding-bottom", height);



    //Checks if a user has created events
        var baseUri = "api/get/events/created";
        var userId_url = "userId=" + localStorage.getItem("userId");
        var fullUri = baseUri + "?" + userId_url;
        var token = $("meta[name='_csrf']").attr("content");    // Used to bypass SPring Boot's CSRF protocol     -- SOlution taken from 'https://stackoverflow.com/questions/34747437/use-of-spring-csrf-with-ajax-rest-call-and-html-page-with-thymeleaf' on Nov 26th 2019
        var header = $("meta[name='_csrf_header']").attr("content");    // Used to bypass SPring Boot's CSRF protocol

        $.ajax({
            type: "GET", url: fullUri, beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
            }, success: function (data) {
                if (data.length != 0) {
                    //Change the buttons to active
                    $("#deleteEvent").css("display", "block")
                } else {
                    $("#deleteEvent").css("display", "none")
                }
            },
            error: function (data) {
                alert("FAIL");
                alert(data.responseText);
            }

        });



    });




