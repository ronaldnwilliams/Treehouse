const $odd = $('a:odd');
const $secureLinks = $('a[href^="https://"]');
const $pdfs = $('a[href$=".pdf"]');
const $pdfCheckbox = $('<label><input type=\'checkbox\'> Allow PDF downloads</label>');

$('#links').append($pdfCheckbox);
$secureLinks.attr('target', '_blank');
$pdfs.attr('download', true);

$secureLinks.addClass('secure');
$pdfs.addClass('pdf');

$pdfs.on('click', function(e) {
    if ($(':checked').length === 0){
        e.preventDefault();
        alert('Please check the box to allow PDF downloads.');
    }
});

$('a').each(function(){
    const url = $(this).attr('href');
    $(this).parent().append(`(${url})`);
});