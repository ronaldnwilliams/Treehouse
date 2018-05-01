const weather = require('./weather');
// Join multiple values passed as arguments and replace all spaces with underscores
const query = process.argv.slice(2).join('_').replace(' ', '_');
// query: 78757
// query: Austin_TX
// query: London_England
weather.get(query);
