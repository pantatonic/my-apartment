/* global modalBuildingDetail, app, _CONTEXT_PATH_ */

var latestBuildingIdProcess = null;

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
                page.showBuildingDetail('add');
            });
            
            jQuery('#box-building-container').on('click', '.box-building', function() {
                var thisElement = jQuery(this);
                
                page.showBuildingDetail('edit', thisElement.attr('data-id'));
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
        showBuildingDetail: function(type, buildingId) {
            if(type == undefined) {
                type = 'add';
            }

            if(type == 'add') {
                app.clearFormData(modalBuildingDetail.getForm());
                modalBuildingDetail.clearInputId();
                
                modalBuildingDetail.checkUseElectricityWater();
            
                modalBuildingDetail.getModal().modal('show');
            }
            else {
                modalBuildingDetail.getBuildingDetail(buildingId);
            }
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
                    var __setData = function(boxBuildingElement, currentData) {
                        boxBuildingElement.attr('data-id', currentData.id);
                        
                        boxBuildingElement.find('.box-building-name').html(
                            app.translate('apartment.building') 
                            + ' : '
                            + currentData.name
                        );
                    };
                    var __setAnimateBoxBuilding = function() {
                        if(latestBuildingIdProcess != null) {
                            var boxBuildingToAnimate = jQuery('.box-building').filter(function() {
                                if(latestBuildingIdProcess.toString() == jQuery(this).attr('data-id')) {
                                    return true;
                                }
                                else {
                                    return false;
                                }
                            });
                            
                            app.setAnimateCustom(boxBuildingToAnimate, 'jello');
                        }
                    };

                    boxBuildingContainer.html(html);

                    for(var index in buildingData) {
                        var currentData = buildingData[index];
                        html = __getTemplate();

                        boxBuildingContainer.append(html);
                        var currentBoxBuilding = boxBuildingContainer.find('.box-building').last();

                        __setData(currentBoxBuilding, currentData);
                    }
                    
                    app.loadingInElement('remove', parent_box);
                    
                    __setAnimateBoxBuilding();
                };

                jQuery.ajax({
                    type: 'get',
                    url: _CONTEXT_PATH_ + '/building_get.html',
                    cache: false,
                    success: function(response) {
                        response = app.convertToJsonObject(response);

                        _renderBoxBuilding(response);
                    },
                    error: function() {
                        app.alertSomethingError();
                        
                        app.loadingInElement('remove', parent_box);
                    }
                });
            }, 500);
        }
    };
})();
