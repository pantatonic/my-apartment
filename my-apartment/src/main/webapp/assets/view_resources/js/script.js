/* global app, _CONTEXT_PATH_, _LOCALE_ */

var _DELAY_PROCESS_ = 500;

jQuery(document).ready(function() {
    jQuery('.modal-scroll .modal-body').css({
        'max-height': (jQuery(window).height() * 60) / 90, // /100
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
    
    jQuery('#project-simple-structure').click(function() {
        jQuery('#modal-project-simple-structure').modal('show');
    });
    

    if(typeof _LOCALE_ !== 'undefined') {
        jQuery.extend($.fn.dataTable.defaults, {
            /*lengthMenu: [[15, 25, 50, 100], [15, 25, 50, 100]],*/
            bLengthChange: false,
            displayLength: 10,
            language: {
                url: _CONTEXT_PATH_ + '/assets/data_table/languages/' + _LOCALE_ + '.json'
            },
            ordering: false,
            bFilter: false,
            pagingType: 'full_numbers'
        });
    }
    
});