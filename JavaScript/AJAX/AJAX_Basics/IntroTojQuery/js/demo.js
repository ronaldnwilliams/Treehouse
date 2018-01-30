var url = '/employees.php';
var data = {
    firstName : "Ron",
    lastName : "Williams"
};
var callback = function (response) {
    //do something with the response
};
$.get(url, data, callback);


//some prefer this way:

$.get('/employees.php', {
    firstName : "Ron",
    lastName : "Williams"
}, function (response) {
       //do something with the response
});