/* global modalBuildingDetail, app, _CONTEXT_PATH_, _DELAY_PROCESS_, alertUtil, BUILDING_HAS_ANY_DATA, EDIT_ANIMATED_CLASS, SESSION_EXPIRE_STRING, SUCCESS_STRING */

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
            jQuery('.refresh-building-list').click(function() {
                page.getBuilding();
            });
            
            jQuery('#box-building-container').on('click', '.button-room', function() {
                page.gotoRoomSetting(jQuery(this).attr('data-id'));
            });
            
            jQuery('.add-button').click(function() {
                page.showBuildingDetail('add');
            });
            
            jQuery('#box-building-container').on('click', '.box-building', function() {
                var thisElement = jQuery(this);
                
                page.showBuildingDetail('edit', thisElement.attr('data-id'));
            });
            
            boxBuilding.getBoxBuildingContainer().on('click', '.button-delete', function() {
                boxBuilding.deleteBuilding(jQuery(this));
            });
            
            modalBuildingDetail.getModal().find('#use-min-electricity').click(function() {
                var thisElement = jQuery(this);
                
                if(thisElement.is(':checked')) {
                    modalBuildingDetail.minElectricityBlog.show();
                }
                else {
                    modalBuildingDetail.minElectricityBlog.hide();
                    modalBuildingDetail.minElectricityBlog.clearInputValue();
                }
            });
            
            modalBuildingDetail.getModal().find('#use-min-water').click(function() {
                var thisElement = jQuery(this);
                
                if(thisElement.is(':checked')) {
                    modalBuildingDetail.minWaterBlog.show();
                }
                else {
                    modalBuildingDetail.minWaterBlog.hide();
                    modalBuildingDetail.minWaterBlog.clearInputValue();
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
                app.clearAllInputErrorClass(modalBuildingDetail.getModal().find('.modal-body'));
                modalBuildingDetail.clearInputId();
                
                modalBuildingDetail.checkUseElectricityWater();
            
                modalBuildingDetail.getModal().modal('show');
                
                app.modalUtils.bodyScrollTop(modalBuildingDetail.getModal());
            }
            else {
                modalBuildingDetail.getBuildingDetail(buildingId);
            }
        },
        getBuilding: function() {
            boxBuilding.getBuilding();
        },
        gotoRoomSetting: function(buildingId) {
            window.location.href = _CONTEXT_PATH_ + '/room.html?building_id=' + buildingId;
        }
    };
})();

var boxBuilding = (function() {
    var _deleteBuilding = function(buttonDelete) {
        var id = buttonDelete.attr('data-id');
            
        buttonDelete.bootstrapBtn('loading');

        setTimeout(function() {
            jQuery.ajax({
                type: 'post',
                url: _CONTEXT_PATH_ + '/building_delete_by_id.html',
                data: {
                    id: id
                },
                cache: false,
                success: function(response) {
                    response = app.convertToJsonObject(response);

                    if(response.result == SUCCESS_STRING) {
                        app.showNotice({
                            message: app.translate('common.delete_success'),
                            type: response.result
                        });
                    }
                    else {
                        if(response.message == BUILDING_HAS_ANY_DATA) {
                            app.showNotice({
                                message: app.translate('building.cannot_delete_building_has_any_data'),
                                type: response.result
                            });
                        }
                        else {
                            if(response.message == SESSION_EXPIRE_STRING) {
                                app.alertSessionExpired();
                            }
                            else {
                                app.showNotice({
                                    message: app.translate('common.processing_failed'),
                                    type: response.result
                                });
                            }
                        }
                    }

                    buttonDelete.bootstrapBtn('reset');

                    boxBuilding.getBuilding();
                },
                error: function() {
                    app.alertSomethingError();

                    buttonDelete.bootstrapBtn('reset');
                }
            });
        }, _DELAY_PROCESS_);
    };
    
    return {
        getBoxBuildingContainer: function() {
            return jQuery('#box-building-container');
        },
        deleteBuilding: function(buttonDelete) {
            alertUtil.confirmAlert(app.translate('common.please_confirm_to_process'), function() {
                _deleteBuilding(buttonDelete);
            }, function() {
                
            },{
                animation: false,
                type: null
            });
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
                        var boxBuildingElement_ = boxBuildingElement.closest('.box-building_');
                        
                        boxBuildingElement.attr('data-id', currentData.id);
                        
                        boxBuildingElement.find('.box-building-name').html(
                            app.translate('apartment.building') 
                            + ' : '
                            + currentData.name
                        );
                
                        boxBuildingElement_.find('.button-delete').attr('data-id', currentData.id);
                        
                        boxBuildingElement_.find('.button-room').attr('data-id', currentData.id);
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
                            
                            app.setAnimateCustom(boxBuildingToAnimate, EDIT_ANIMATED_CLASS);
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
                        
                        if(response.message == SESSION_EXPIRE_STRING) {
                            app.alertSessionExpired();
                        }

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
