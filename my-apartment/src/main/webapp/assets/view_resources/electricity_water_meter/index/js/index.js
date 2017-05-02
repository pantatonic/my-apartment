/* global app, _DELAY_PROCESS_, SESSION_EXPIRE_STRING, _CONTEXT_PATH_ */

jQuery(document).ready(function() {
    page.initialProcess();
    page.addEvent();
    
});

var page = (function() {
    
    return {
        initialProcess: function() {
            
        },
        addEvent: function() {
            page.getElement.getBuildingList().change(function() {
                var thisElement = jQuery(this);
                
                if(app.valueUtils.isEmptyValue(thisElement.val())) {
                    page.boxRoomContainer.empty();
                }
                else {
                    page.getRoom();
                }
            });
        },
        getElement: {
            getBuildingList: function() {
                return jQuery('#building-list');
            },
            getBoxRoomContainer: function() {
                return jQuery('#box-room-container');
            }
        },
        boxRoomContainer: {
            noDataFound: function() {
                var html = '<div id="box-room-container-no-data">'
                    + '-- ' +app.translate('common.data_not_found') + ' --'
                    + '</div>';

                page.getElement.getBoxRoomContainer().html(html);
            },
            empty: function() {
                var html = '<div id="box-room-container-select-data">'
                    + '-- ' +app.translate('common.please_select_data') + ' --'
                    + '</div>';
            
                page.getElement.getBoxRoomContainer().html(html);
            }
        },
        getRoom: function() {
            var buildingList = page.getElement.getBuildingList();
            var buildingId = buildingList.val();
            var contentBox = buildingList.closest('.box-primary');
            
            if(!app.valueUtils.isEmptyValue(buildingId)) {
                app.loadingInElement('show', contentBox);
                
                setTimeout(function() {
                    var _renderBoxRoom = function(response) {
                        var roomData = response.data;
                        
                        alert('To do : List of room for enter meter');
                        
                        app.loadingInElement('remove', contentBox);
                    };
                    
                    jQuery.ajax({
                        type: 'get',
                        url: _CONTEXT_PATH_ + '/room_get_by_building_id.html',
                        data: {
                            building_id: buildingId
                        },
                        cache: false,
                        success: function(response) {
                            response = app.convertToJsonObject(response);
                            
                            if(response.message == SESSION_EXPIRE_STRING) {
                                app.alertSessionExpired();
                                app.loadingInElement('remove', contentBox);
                            }
                            else {
                                _renderBoxRoom(response);
                            }
                        },
                        error: function() {
                            app.loadingInElement('remove', contentBox);

                            app.alertSomethingError();
                        }
                    });
                }, _DELAY_PROCESS_);
            }
            
            
            
            
        }
    };
})();