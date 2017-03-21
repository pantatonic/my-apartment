/* global modalBuildingDetail */

jQuery(document).ready(function() {
    page.add_event();
});

var page = (function() {
    
    return {
        add_event: function() {
            jQuery('.add-button').click(function() {
                page.showBuildingDetail();
            });
            
            modalBuildingDetail.getModal().find('#use-min-electricity').click(function() {
                var thisElement = jQuery(this);
                
                if(thisElement.is(':checked')) {
                    modalBuildingDetail.minElectricityBlog.show();
                }
                else {
                    modalBuildingDetail.minElectricityBlog.hide();
                }
            });
            
            modalBuildingDetail.getModal().find('#use-min-water').click(function() {
                var thisElement = jQuery(this);
                
                if(thisElement.is(':checked')) {
                    modalBuildingDetail.minWaterBlog.show();
                }
                else {
                    modalBuildingDetail.minWaterBlog.hide();
                }
            });
            
            modalBuildingDetail.getForm().submit(function(e) {
                e.preventDefault();
                
                modalBuildingDetail.save();
            });
        },
        showBuildingDetail: function(type) {
            if(type == undefined) {
                type = 'add';
            }
            
            modalBuildingDetail.checkUseElectricityWater();
            
            modalBuildingDetail.getModal().modal('show');            
        }
    };
})();
