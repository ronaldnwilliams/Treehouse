$(document).ready(function () {
    $('form').click(function () {
        $('#photos').html('<ul></ul>');
    });
    $('form').submit(function (evt) {
        evt.preventDefault();
        var flickrAPI = "https://api.flickr.com/services/feeds/photos_public.gne?jsoncallback=?";
        var $searchTerm = $('#search').val();
        var $submitButton = $('#submit');
        var $searchField = $('#search');
        $searchField.prop("disabled",true);
        $submitButton.attr("disabled",true).val("searching...");
        var flickrOptions = {
            tags: $searchTerm,
            format: "json"
        };
        function displayPhotos(data) {
            var photoHTML = '';
            if (data.items.length === 0) {
                photoHTML += '<li> Sorry! Could not find any photos for "' + $('#search').val() + '"</li>';
            } else {
                photoHTML = '<ul>';
                $.each( data.items, function (i, photo) {
                    photoHTML += '<li class="grid-25 tablet-grid-50">';
                    photoHTML += '<a href="' + photo.link + '" class="image">';
                    photoHTML += '<img src="' + photo.media.m + '"></a></li>';
                });
                photoHTML += '</ul>';
            }
            $('#photos').html(photoHTML);
            $searchField.prop("disabled",false);
            $submitButton.attr("disabled", false).val("Search");
        }
        $.getJSON(flickrAPI, flickrOptions, displayPhotos);
    });
});