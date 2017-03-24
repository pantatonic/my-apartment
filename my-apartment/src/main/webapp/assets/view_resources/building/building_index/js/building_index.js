/* global modalBuildingDetail, app, _CONTEXT_PATH_ */

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
            
            jQuery('#box-building-container').on('click', '.box-building', function() {
                alert('test');
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
            var parent_box = jQuery('#parent-box-building-container');
            app.loadingInElement('show', parent_box);
            
            setTimeout(function() {
                var _renderBoxBuilding = function(response) {
                    var buildingData = response.data;
                    var html = '';
                    var __getTemplate = function() {
                        return jQuery('#box-building-template').val();
                    };
                    var boxBuildingContainer = jQuery('#box-building-container');

                    boxBuildingContainer.html(html);

                    for(var index in buildingData) {
                        var currentData = buildingData[index];
                        html = __getTemplate();

                        boxBuildingContainer.append(html);
                        var currentBoxBuilding = boxBuildingContainer.find('.box-building').last();

                        currentBoxBuilding.find('.box-building-name').html(
                            app.translate('apartment.building') 
                            + ' : '
                            + currentData.name
                        );
                    }
                    
                    app.loadingInElement('remove', parent_box);
                };

                jQuery.ajax({
                    type: 'get',
                    url: _CONTEXT_PATH_ + '/building_get.html',
                    cache: false,
                    success: function(response) {
                        response = app.convertToJsonObject(response);

                        _renderBoxBuilding(response);
                    }
                });
            }, 500);
        }
    };
})();
