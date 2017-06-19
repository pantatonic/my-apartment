/* global app, _DELAY_PROCESS_, _CONTEXT_PATH_, SESSION_EXPIRE_STRING, iconRoom */

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
            page.addEvent.getRoomByChangeBuildingList();
            page.addEvent.checkedRoomCheckbox();
            page.addEvent.createRoomInvoice();
        }
    };
})();



var page = (function() {
    
    return {
        initialProcess: {
            initDatePicker: function() {
                jQuery('#room-invoice-month-year').datepicker({
                    format:'yyyy-mm',
                    minViewMode: 'months',
                    autoclose: true
                }).on('changeDate',function(e) {
                    page.getRoom();
                });
            }
        },
        addEvent: {
            getRoomByChangeBuildingList: function() {
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
            checkedRoomCheckbox: function() {
                var boxRoomContainer = page.getElement.getBoxRoomContainer();
                
                boxRoomContainer.on('click', '.box-room', function() {
                    var thisElement = jQuery(this);
                    var checkbox = thisElement.closest('.box-room_').find('.room-checkbox');
                    
                    checkbox.prop("checked", !checkbox.prop("checked"));
                });
            },
            createRoomInvoice: function() {
                var buttonCreate = page.getElement.getCreateRoomInvoiceButton();
                
                buttonCreate.click(function() {
                    page.createRoomInvoiceProcess();
                });
            }
        },
        getElement: {
            getBuildingList: function() {
                return jQuery('#building-list');
            },
            getBoxRoomContainer: function() {
                return jQuery('#box-room-container');
            },
            getBoxRoom_: function() {
                return jQuery('.box-room_');
            },
            getCreateRoomInvoiceButton: function() {
                return jQuery('#create-room-invoice');
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
        createRoomInvoiceProcess: function() {
            alert('To create room invoice');
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
                            var __getRoomStatusColorClass = function(roomStatusId) {
                                return app.system.getRoomStatusColorClass(roomStatusId);
                            };
                            var boxRoomElement_ = boxRoomElement.closest('.box-room_');
                            
                            boxRoomElement_.attr('data-room-no', currentData.roomNo);
                            
                            boxRoomElement.attr('data-id', currentData.id);
                            
                            boxRoomElement.find('[name="id"]').val(currentData.id);
                            
                            boxRoomElement.find('.box-room-name').html(
                                app.translate('building.room')
                                + ' : '
                                + currentData.roomNo
                            );
                    
                            boxRoomElement.find('.label-room-status')
                                    .addClass(__getRoomStatusColorClass(currentData.roomStatusId))
                                    .html(app.translate(currentData.roomStatusText));
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
                        
                        boxRoomContainer.append('<div class="clearfix"></div>');

                        if(roomData.length == 0) {
                            page.boxRoomContainer.noDataFound();
                        }
                        
                        page.getRoomInvoiceRoomDetailList();
                        page.getRoomInvoice();
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
                            }
                            else {
                                _renderBoxRoom(response);
                            }
                            
                            app.loadingInElement('remove', contentBox);
                        },
                        error: function() {
                            app.loadingInElement('remove', contentBox);

                            app.alertSomethingError();
                        }
                    });
                }, _DELAY_PROCESS_);
            }
        },
        getRoomInvoiceRoomDetailList: function () {
            var _setCurrentCheckInLabel = function(response) {
                var currentCheckInData = response.data.currentCheckIn;
                var labelTemplate = '<span class="label label-info">' + app.translate('room.check_in') + '</span>';

                for(var index in currentCheckInData) {
                    var currentData =  currentCheckInData[index];
                    var boxRoom = jQuery('.box-room[data-id="' + currentData.roomId + '"]');
                    var currentLabelHtml =  boxRoom.find('.room-detail-label').html();

                    boxRoom.find('.room-detail-label').html(currentLabelHtml + labelTemplate);
                }
            };
            
            var _closeWithClearFix = function() {
                jQuery('.box-room_ .box-room').each(function() {
                    var thisElement = jQuery(this);
                    
                    thisElement.find('.room-detail-label').append('<div class="clearfix"></div>');
                });
            };
          
            jQuery.ajax({
                type: 'post',
                url: _CONTEXT_PATH_ + '/get_room_invoice_room_detail_list.html',
                cache: false,
                success: function(response) {
                    response = app.convertToJsonObject(response);
                    
                    //_setReserveLabel(response);
                    _setCurrentCheckInLabel(response);
                    //_setCurrentNoticeCheckOut(response);
                    _closeWithClearFix();
                },
                error: function() {
                    app.showNotice({
                        type: WARNING_STRING,
                        message: app.translate('room.cannot_get_room_manage_detail_list'),
                        addclass: 'notice-get-room-manage-detail'
                    });
                }
                
            });
        },
        getRoomInvoice: function() {
            var buildingList = page.getElement.getBuildingList();
            
            if(!app.valueUtils.isEmptyValue(buildingList.val())) {
                alert('To get Room Invoice of this month');
            }
        }
    };
})();