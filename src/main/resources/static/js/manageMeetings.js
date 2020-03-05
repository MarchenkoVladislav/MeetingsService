let meetings;

$('#meetings-management').on('click', function () {
    $('#hint').css("display", "none");
    $('#new-meet').css("display", "none");
    $('#all-meet').css("display", "block");
    $.getJSON('http://localhost:9000/meetings/allMeetingsByUser/' + signInData.user.userID, function (d) {
        if (d.length == 0) {
            $('#meets').text("You have not had meetings yet.");
        }
        else {
            for (let i = 0; i < d.length; i++) {
                $('#meets').append(i + ") date: "+ d[i].startTime + " description: " + d[i].description + " \n ");
            }
        }
    });
});