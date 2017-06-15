
jQuery(document).ready(function() {
    facade.initialProcess();
    facade.addEvent();
});

var facade = (function() {
    
    return {
        initialProcess: function() {
            page.initialProcess.initDatePicker();
        },
        addEvent: function() {
            
        }
    };
})();



var page = (function() {
    
    return {
        initialProcess: {
            initDatePicker: function() {
                jQuery('#electriccity-water-month-year').datepicker({
                    format:'yyyy-mm',
                    minViewMode: 'months',
                    autoclose: true
                }).on('changeDate',function(e) {
                    page.getRoom();
                });
            }
        },
        getElement: {
            
        }
    };
})();