var playlist = new Playlist();

var bellbottoms = new Song("Bellbottoms", "The Jon Spencer Blues Explosion", "5:18");
var harlemShuffle = new Song("Harlem Shuffle", "Bob & Earl", "2:48");

playlist.add(bellbottoms);
playlist.add(harlemShuffle);

var playlistElement = document.getElementById("playlist");

playlist.renderInElement(playlistElement);

var playButton = document.getElementById('play');
playButton.onclick = function () {
  playlist.play();
  playlist.renderInElement(playlistElement);
}
var nextButton = document.getElementById('next');
nextButton.onclick = function () {
  playlist.next();
  playlist.renderInElement(playlistElement);
}
var stopButton = document.getElementById('stop');
stopButton.onclick = function () {
  playlist.stop();
  playlist.renderInElement(playlistElement);
}
