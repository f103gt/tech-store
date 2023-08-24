$(document).ready(function() {
    // Like button click handler
    $('.like-btn').on('click', function() {
        $(this).toggleClass('is-active');
    });

    // Minus button click handler
    $('.minus-btn').on('click', function(e) {
        e.preventDefault();
        var $input = $(this).closest('div').find('input');
        var value = parseInt($input.val());

        if (value > 1) {
            value = value - 1;
        } else {
            value = 0;
        }

        $input.val(value);
    });

    // Plus button click handler
    $('.plus-btn').on('click', function(e) {
        e.preventDefault();
        var $input = $(this).closest('div').find('input');
        var value = parseInt($input.val());

        if (value < 100) {
            value = value + 1;
        } else {
            value = 100;
        }

        $input.val(value);
    });
});