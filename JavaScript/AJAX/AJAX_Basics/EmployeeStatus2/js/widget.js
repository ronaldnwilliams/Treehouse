$(document).ready(function () {
    var url = "../data/employees.json";
    $.getJSON(url, function (response) {
        var statusHTML = '<ul class="bulleted">';
        $.each(response, function (index, employee) {
            if (employee.inoffice === true) {
                statusHTML += '<li class ="in">';
            } else {
                statusHTML += '<li class ="out">';
            }
            statusHTML += employee.name + '</li>';
        });
        statusHTML += '</ul>';
        $('#employeeList').html(statusHTML);
    });
    var urlRoom = "../data/rooms.json";
    $.getJSON(urlRoom, function (response) {
        var roomHTML = '<ul class="rooms">';
        $.each(response, function (index, room) {
            if (room.available === true) {
                roomHTML += '<li class="empty">';
            } else {
                roomHTML += '<li class="full">';
            }
            roomHTML += room.room + '</li>';
        });
        roomHTML += '</ul>';
        $('#roomList').html(roomHTML);
    });
});