var xhr = new XMLHttpRequest();
xhr.onreadystatechange = function () {
    if(xhr.readyState === 4) {
        var employees = JSON.parse(xhr.responseText);
        var statusHTML = '<ul class="bulleted">';
        for (var i=0; i<employees.length; i += 1) {
            if (employees[i].inoffice === true) {
                statusHTML += '<li class="in">';
            } else {
                statusHTML += '<li class="out">';
            }
            statusHTML += employees[i].name;
            statusHTML += '</li>';
        }
        statusHTML += '</ul>';
        document.getElementById('employeeList').innerHTML = statusHTML;
    }
};
xhr.open('GET', '../data/employees.json');
xhr.send();

var xhrRoom = new XMLHttpRequest();
xhrRoom.onreadystatechange = function () {
    if(xhrRoom.readyState === 4) {
        var rooms = JSON.parse(xhrRoom.responseText);
        var availableHTML = '<ul class="rooms">';
        for (var i = 0; i<rooms.length; i += 1) {
            if (rooms[i].available === true) {
                availableHTML += '<li class="empty">';
            } else {
                availableHTML += '<li class="full">';
            }
            availableHTML += rooms[i].room;
            availableHTML += '</li>';
        }
        availableHTML += '</ul>';
        document.getElementById('roomList').innerHTML = availableHTML;
    }
};
xhrRoom.open('GET', '../data/rooms.json');
xhrRoom.send();