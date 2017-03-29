/* global buildingIdString, app, INPUT_ERROR_CLASS, WARNING_STRING, _CONTEXT_PATH_, _DELAY_PROCESS_ */

jQuery(document).ready(function() {
    page.initialProcess();
    page.addEvent();
});

var page = (function() {
    var _getBuildingListElement = function() {
        return jQuery('#building-list');
    };
    
    return {
        initialProcess: function() {
            page.setBuildingList();
        },
        addEvent: function() {
            var buildingList = _getBuildingListElement();
            
            buildingList.change(function() {
                if(jQuery(this).val() == '') {
                    page.boxRoomContainer.empty();
                }
                else {
                    page.getRoom();
                }
            });
            
            page.getElement.getAddButton().click(function() {
                page.showRoomDetail();
            });
        },
        setBuildingList: function() {
            if(!app.valueUtils.isEmptyValue(buildingIdString)) {
                var buildingList = _getBuildingListElement();
                
                buildingList.val(buildingIdString);
                
                page.getRoom();
            }
            else {
                page.boxRoomContainer.empty();
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
            var buildingList = _getBuildingListElement();
            var buildingId = buildingList.val();
            var contentBox = page.getElement.getContentBox();
            
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
                            var __getRoomStatusColorClass = function(roomStatusId) {
                                var color = {
                                    '1': 'label-success',
                                    '2': 'label-danger',
                                    '3': 'label-warning'
                                };

                                return color[roomStatusId];
                            };
                            var boxRoomElement_ = boxRoomElement.closest('.box-room_');
                             
                            boxRoomElement.attr('data-id', currentData.id);
                             
                            boxRoomElement.find('.box-room-name').html(
                                app.translate('building.room')
                                + ' : '
                                + currentData.name  
                            );

                            boxRoomElement.find('.label-room-status')
                                    .addClass(__getRoomStatusColorClass(currentData.roomStatusId))
                                    .html(app.translate(currentData.roomStatusText));
                    
                            boxRoomElement_.find('.button-delete').attr('data-id', currentData.id);
                            
                            //boxRoomElement_.find('.button-room').attr('data-id', currentData.id);
                        };
                        var __setAnimateBoxRoom = function() {
                            
                        };
                        
                        boxRoomContainer.html(html);
                        
                        for(var index in roomData) {
                            var currentData = roomData[index];
                            html = __getTemplate();

                            boxRoomContainer.append(html);
                            var currentBoxRoom = boxRoomContainer.find('.box-room').last();
                            
                            __setData(currentBoxRoom, currentData);
                        }
                        
                        if(roomData.length == 0) {
                            page.boxRoomContainer.noDataFound();
                        }
                        
                        app.loadingInElement('remove', contentBox);
                        
                        __setAnimateBoxRoom();
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

                            _renderBoxRoom(response);
                        },
                        error: function() {
                            app.loadingInElement('remove', contentBox);

                            app.alertSomethingError();
                        }
                    });
                }, _DELAY_PROCESS_);
            }
        },
        getElement: {
            getAddButton: function() {
                return jQuery('.add-button');
            },
            getBuildingListElement: function() {
                return _getBuildingListElement();
            },
            getContentBox: function() {
                return jQuery('.box-primary');
            },
            getBoxRoomContainer: function() {
                return jQuery('#box-room-container');;
            }
        },
        showRoomDetail: function(type, roomId) {
            var buildingList = page.getElement.getBuildingListElement();
            
            if(type == undefined) {
                type = 'add';
            }
            
            buildingList.removeClass(INPUT_ERROR_CLASS);
            
            if(!app.valueUtils.isEmptyValue(buildingList.val())) {
                if(type == 'add') {

                    modalRoomDetail.getModal().modal('show');
                }
                else {

                }
            }
            else {
                if(!app.checkNoticeExist('notice-select-data')) {
                    app.showNotice({
                        type: WARNING_STRING,
                        message: app.translate('common.please_enter_data'),
                        addclass: 'notice-select-data'
                    });
                }
                
                buildingList.addClass(INPUT_ERROR_CLASS);
            }            
        }
    };
})();

var modalRoomDetail = (function() {
    var _getModal = function() {
        return jQuery('#modal-room-detail');
    };
    
    var _getForm = function() {
        return jQuery('[name="room_form"]');
    };
    
    return {
        getModal: function() {
            return _getModal();
        },
        getForm: function() {
            return _getForm();
        }
    };
})();