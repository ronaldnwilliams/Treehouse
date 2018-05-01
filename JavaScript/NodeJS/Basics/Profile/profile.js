// Require https module
const https = require('https');
// Require http module for status codes
const http = require('http');

// Print Error Messages
function printError(error) {
  console.error(error.message);
}

// Function to print message to console
function printMessage(username, babdgeCount, points) {
  const message = `${username} has ${babdgeCount} total babge(s) and ${points} JavaScript points.`;
  console.log(message);
}

function get(username) {
  try {
    // Connect to API url ("https://teamtreehouse.com/username.json")
    const request = https.get(`https://teamtreehouse.com/${username}.json`, response => {
      if (response.statusCode === 200) {
        // console.dir(response.statusCode);
        let body = '';
        // Read the data
        response.on('data', data => {
          body += data.toString();
        });

        response.on('end', () => {
          try {
            // Parse data
            const profile = JSON.parse(body);
            // Print data
            printMessage(username, profile.badges.length, profile.points.JavaScript);
          } catch (error) {
            printError(error);
          }
        });
      } else {
        const message = `There was an error getting the profile for ${username} (${http.STATUS_CODES[response.statusCode]})`;
        const statusCodeError = new Error(message);
        printError(statusCodeError);
      }
    });
    request.on('error', printError);
  } catch (error) {
    printError(error);
  }
}

module.exports.get = get;
