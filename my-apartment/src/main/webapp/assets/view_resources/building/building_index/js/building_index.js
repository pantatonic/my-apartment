/* global modalBuildingDetail, app */

jQuery(document).ready(function() {
    page.initialProcess();
    page.addEvent();
});

var page = (function() {
    
    return {
        initialProcess: function() {
            page.getBuilding();
        },
        addEvent: function() {
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
                
                app.clearFormData(modalBuildingDetail.getForm());
                modalBuildingDetail.clearInputId();
            }
            
            modalBuildingDetail.checkUseElectricityWater();
            
            modalBuildingDetail.getModal().modal('show');            
        },
        getBuilding: function() {
            jQuery.ajax({
                type: 'get',
                url: _CONTEXT_PATH_ + '/building_get.html',
                cache: false,
                success: function(response) {
                    //response = app.convertToJsonObject(response);
                }
            });
        }
    };
})();
