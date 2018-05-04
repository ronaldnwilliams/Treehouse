var router = require('./router.js');
// Problem: Need a simple way to get users badge count and JS point from a web browser
// Solution: Use Node.js to perform the profile lookup and serve our template via HTTP

// Create a web server
const http = require('http');

const hostname = 'localhost';
const port = 8080;

const server = http.createServer((req, res) => {
  router.home(req, res);
  router.user(req, res);

});

server.listen(port, hostname, () => {
  console.log(`Server running at http://${hostname}:${port}/`);
});
