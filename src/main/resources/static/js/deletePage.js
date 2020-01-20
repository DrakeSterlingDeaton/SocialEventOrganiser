
    function searchFunction() {
        var searchValue = $("input[name='search_value']").val();
        var deleteTitles = [];
        var increment = 0;
        $(".list-group-item").each(function () {
            $(this).removeClass("post-none-search post-block-search");
            deleteTitles.push($(this).children("a").children("h5").text());
         });

        // Warning text
        $(".eventNoMatches").addClass("post-none-search")
        $(".eventNoMatchesText").addClass("post-none-search")


        //search for variable
        for(var ii = 0 ; ii < deleteTitles.length; ii++) {
            if (deleteTitles[ii].toLowerCase().includes(searchValue.toLowerCase())) {
                console.log("enter");
                $(".card-title").each(function() {
                    if($(this).text() == deleteTitles[ii]){
                        $(this).closest(".list-group-item").addClass("post-block-search")
                    }
                });

            }
        }

        $(".list-group-item").each(function() {
            if(!$(this).hasClass("post-block-search")){
                $(this).addClass("post-none-search")
                increment++
            }
            if($(".eventNoMatches").hasClass("post-none-search")) {
                if ($(".list-group-item").length == increment) {
                    $(".eventNoMatches").removeClass("post-none-search")
                    $(".eventNoMatchesText").removeClass("post-none-search")
                }
            }
        });
    }


    //HTTP REQUEST METHODS
    function deleteEvents(eventId) {
    var baseUri = "/api/delete/event";
    var eventId_url = "eventId=" + eventId;
    var fullUri = baseUri + "?" + eventId_url;

    var token = $("meta[name='_csrf']").attr("content");    // Used to bypass SPring Boot's CSRF protocol     -- SOlution taken from 'https://stackoverflow.com/questions/34747437/use-of-spring-csrf-with-ajax-rest-call-and-html-page-with-thymeleaf' on Nov 26th 2019
    var header = $("meta[name='_csrf_header']").attr("content");    // Used to bypass SPring Boot's CSRF protocol


    $.ajax({ type: "DELETE",url: fullUri ,
        beforeSend: function(xhr) { xhr.setRequestHeader(header, token); },
        success: function(data){
            if ( data === true ) {
               location.reload() // refresh the page
            } else {
                alert("There was an error, please try again.")
                alert(data.responseText)
                alert(data)
            }
        },
        error: function(data) { alert("FAIL"); alert(data.responseText); }

    });
}