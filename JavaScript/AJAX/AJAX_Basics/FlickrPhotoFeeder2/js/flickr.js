$(document).ready(function () {
    $('form').submit(function (evt) {
        evt.preventDefault();
        var flickrAPI = "https://api.flickr.com/services/feeds/photos_public.gne?jsoncallback=?";
        var searchTerm = $('#search').val();
        var flickrOptions = {
            tags: searchTerm,
            format: "json"
        };
        function displayPhotos(data) {
            var photoHTML = '<ul>';
            $.each( data.items, function (i, photo) {
                photoHTML += '<li class="grid-25 tablet-grid-50">';
                photoHTML += '<a href="' + photo.link + '" class="image">';
                photoHTML += '<img src="' + photo.media.m + '"></a></li>';
            });
            photoHTML += '</ul>';
            $('#photos').html(photoHTML);
        }
        $.getJSON(flickrAPI, flickrOptions, displayPhotos);
    });
});