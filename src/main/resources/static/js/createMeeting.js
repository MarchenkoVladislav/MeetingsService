$('#create-meeting').on('click', function () {
    $('#hint').css("display", "none");
    $('#new-meet').css("display", "block");
});

function writeMeetInDB() {
    if ($('#jsMaster')[0].checkValidity()) {
        $.ajax({
            url: 'http://localhost:9000/meetings/create',
            type: 'POST',
            data: {
                startTime: $('#startDate').val(),
                endTime: $('#endDate').val(),
                description: $('#description').val(),
            },
            success: function(d) {
              if (!d) {
                  alert("Invalid data on backend!");
              }
              else {
                  alert("This meeting was added into DB");
              }
            },
            error: function () {
                alert("Invalid data!");
            }
        });
    }
    $('#jsMaster')[0].classList.add('was-validated');
}