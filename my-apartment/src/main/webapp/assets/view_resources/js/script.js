/* global app */

var _DELAY_PROCESS_ = 500;

jQuery(document).ready(function() {
    jQuery('.modal-scroll .modal-body').css({
        'height': (jQuery(window).height() * 60) / 90, // /100
        'overflow-x': 'auto',
        'overflow-y': 'auto'
    });
    
    OK_STRING = app.translate('common.ok');
    CANCEL_STRING = app.translate('common.cancel');
    
    jQuery('.number-int').numberOnly({
        useDecimal: false,
        thousandSeparate: false,
        callBackFunction: function(el) {

        }
    });
    
    jQuery('.number-int-separate').numberOnly({
        useDecimal: false,
        thousandSeparate: true,
        callBackFunction: function(el) {

        }
    });
    
    jQuery('.number-decimal').numberOnly({
        useDecimal: true,
        thousandSeparate: false,
        callBackFunction: function(el) {

        }
    });
    
    jQuery('.number-decimal-separate').numberOnly({
        useDecimal: true,
        thousandSeparate: true,
        callBackFunction: function(el) {

        }
    });
});