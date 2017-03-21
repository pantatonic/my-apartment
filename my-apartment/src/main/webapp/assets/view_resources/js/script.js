/* global app */

var _DELAY_PROCESS_ = 500;

jQuery(document).ready(function() {
    $('.modal-scroll .modal-body').css({
        'height': (jQuery(window).height() * 60) / 90, // /100
        'overflow-x': 'auto',
        'overflow-y': 'auto'
    });
    
    OK_STRING = app.translate('common.ok');
    CANCEL_STRING = app.translate('common.cancel');
});