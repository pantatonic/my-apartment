$(document).ready(function() {
    page.add_event();
});

var page = (function() {
    
    return {
        add_event: function() {
            $('.add-button').click(function() {
                page.showBuildingDetail();
            });
        },
        showBuildingDetail: function(type) {
            if(type == undefined) {
                type = 'add';
            }
            
            $('#modal-building-detail').modal('show');            
        }
    };
})();