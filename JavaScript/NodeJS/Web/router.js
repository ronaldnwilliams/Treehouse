var Profile = require('./profile.js');
var renderer = require('./renderer.js');

// Handle HTTP route GET / and POST / i.e. Home
function home(req, res) {
  // if url === "/" && GET
  if (req.url === '/') {
    // show search field
    res.statusCode = 200;
    res.setHeader('Content-Type', 'text/plain');
    renderer.view('header', {}, res);
    renderer.view('search', {}, res);
    renderer.view('footer', {}, res);
    res.end();
  }
  // if url === "/" && POST
    // redirect to username
}
// Handle HTTP route GET /:username i.e. /ronaldwilliams6
function user(req, res) {
  // if url === "/..."
  var username = req.url.replace('/', '');
  if (username.length > 0) {
    res.statusCode = 200;
    res.setHeader('Content-Type', 'text/plain');
    renderer.view('header', {}, res);

    // get json from treehouse
    var studentProfile = new Profile(username);
    //on "end"
    studentProfile.on('end', function(profileJSON) {
      // show profile
      // store the values needed
      var values = {
        avatarUrl: profileJSON.gravatar_url,
        username: profileJSON.profile_name,
        badges: profileJSON.badges.length,
        javaScriptPoints: profileJSON.points.JavaScript
      }
      // simple response
      renderer.view('profile', values, res);
      renderer.view('footer', {}, res);
      res.end();
    });
    // on "error"
    studentProfile.on("error", function(error) {
      // show error
      renderer.view('error', {errorMessage: error.message}, res);
      renderer.view('search', {}, res);
      renderer.view('footer', {}, res);
      res.end();
    });
  }
}

module.exports.home = home;
module.exports.user = user;
