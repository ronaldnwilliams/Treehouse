// when the button is pressed show the spoiler and hide the button
$('.spoiler').on('click', 'button', function (e){
    $(this).prev().show();
    $(this).hide();
});

// Create the reveal spoiler button
const $button = $('<button>Reveal Spoiler</button>');

// append to web page
$('.spoiler').append($button);

// Hide the spoiler text
$('.spoiler span').hide();
