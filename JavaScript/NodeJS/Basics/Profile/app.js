/*
command line app that takes username argument and prints badge count and points
from treehouse profile
ex: node app.js someUser
*/
const profile = require('./profile.js');

const users = process.argv.slice(2);
users.forEach(profile.get);
