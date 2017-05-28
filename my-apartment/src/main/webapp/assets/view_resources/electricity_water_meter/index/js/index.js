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
                        var html = '';
                        var __getTemplate = function() {
                            return jQuery('#box-room-template').val();
                        };
                        var boxRoomContainer = page.getElement.getBoxRoomContainer();
                        var __setData = function(boxRoomElement, currentData) {
                            var boxRoomElement_ = boxRoomElement.closest('.box-room_');
                            
                            boxRoomElement.find('[name="id"]').val(currentData.id);
                            
                            boxRoomElement.find('.box-room-name').html(
                                app.translate('building.room')
                                + ' : '
                                + currentData.roomNo  
                            );
                        };
                        
                        boxRoomContainer.html(html);
                        
                        var latestFloorSeq = 0;
                        var htmlFloorSeq = '';
                        var htmlFloorSeqTemplate = function(floorSeq) {
                            return '<div style="clear: both;"></div>'
                                    + '<div class="floor-separate"> ' 
                                        + app.translate('room.floor') 
                                        + ' : ' + floorSeq +
                                    '</div>';
                        };
                        
                        for(var index in roomData) {
                            var currentData = roomData[index];
                            
                            if(latestFloorSeq != currentData.floorSeq) {
                                htmlFloorSeq = htmlFloorSeqTemplate(currentData.floorSeq);
                                latestFloorSeq = currentData.floorSeq;
                            }
                            else {
                                htmlFloorSeq = '';
                            }
                            
                            html = htmlFloorSeq + __getTemplate();
                            boxRoomContainer.append(html);
                            var currentBoxRoom = boxRoomContainer.find('.box-room').last();
                            
                            __setData(currentBoxRoom, currentData);
                        }

                        app.loadingInElement('remove', contentBox);
                        
                        setTimeout(function() {
                            alert('Get meter data coming soon');
                        }, 1000);
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