/* global app, _DELAY_PROCESS_, _CONTEXT_PATH_, SESSION_EXPIRE_STRING, iconRoom, ERROR_STRING, WARNING_STRING, INPUT_ERROR_CLASS, SUCCESS_STRING, alertUtil */
var _ALREADY_INVOICED_ATTR_ = 'already-invoiced';
var _NOT_HAVE_METER_ATTR_ = 'not-have-meter';
var _CANCEL_INVOICE_ATTR_ = 'data-invoice-id';

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
            page.addEvent.cancelRoomInvoice();
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
                    
                    if(checkbox.attr(_ALREADY_INVOICED_ATTR_) == undefined 
                            && checkbox.attr(_NOT_HAVE_METER_ATTR_) == undefined) {
                        checkbox.prop("checked", !checkbox.prop("checked"));
                    }
                });
                
                jQuery('#cancel-invoice-process-button').click(function() {
                    page.cancelRoomInvoiceProcess();
                });
            },
            createRoomInvoice: function() {
                var buttonCreate = page.getElement.getCreateRoomInvoiceButton();
                
                buttonCreate.click(function() {
                    page.createRoomInvoiceProcess();
                });
            },
            cancelRoomInvoice: function() {
                var boxRoomContainer = page.getElement.getBoxRoomContainer();
                
                boxRoomContainer.on('click', '.cancel-invoice-button', function() {
                    var thisElement = jQuery(this);
                    
                    page.cancelRoomInvoice(thisElement);
                });
            }
        },
        roomInvoiceMonthYear: {
            getMonth: function() {
                var monthYear = page.getElement.getRoomInvoiceMonthYear().val();
                var splitText = monthYear.split('-');
                
                return splitText[1];
            },
            getYear: function() {
                var monthYear = page.getElement.getRoomInvoiceMonthYear().val();
                var splitText = monthYear.split('-');
                
                return splitText[0];
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
            },
            getRoomCheckbox: function() {
                return jQuery('.room-checkbox');
            },
            getRoomInvoiceMonthYear: function() {
                return jQuery('#room-invoice-month-year');
            },
            getModalCancelRoomInvoice: function() {
                return jQuery('#modal-cancel-room-invoice');
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
        cancelRoomInvoice: function(cancelButton) {
            var roomInvoiceId = cancelButton.attr(_CANCEL_INVOICE_ATTR_);
            var invoiceNo = cancelButton.closest('.already-invoiced').find('.invoice-no').text();
            var modal_ = page.getElement.getModalCancelRoomInvoice();
            
            modal_.find('#invoice-display').html(invoiceNo);
            modal_.find('[name="room_invoice_id"]').val(roomInvoiceId);
            
            /** clear old data */
            modal_.find('[name="cancel_description"]').val('');
            
            modal_.modal('show');
        },
        cancelRoomInvoiceProcess: function() {
            var modal_ = page.getElement.getModalCancelRoomInvoice();
            
            var roomInvoiceId = modal_.find('[name="room_invoice_id"]').val();
            var description = modal_.find('[name="cancel_description"]').val();
            
            var cancelButton = modal_.find('#cancel-invoice-process-button');
            
            cancelButton.bootstrapBtn('loading');
            
            setTimeout(function() {
                jQuery.ajax({
                    type: 'post',
                    url: _CONTEXT_PATH_ + '/cancel_room_invoice.html',
                    data: {
                        room_invoice_id: roomInvoiceId,
                        description: description
                    },
                    cache: false,
                    success: function(response) {
                        response = app.convertToJsonObject(response);
                        
                        if(response.result == SUCCESS_STRING) {
                            app.showNotice({
                                message: app.translate('common.save_success'),
                                type: response.result
                            });
                            
                            modal_.modal('hide');
                            
                            setTimeout(function() {
                                page.getRoom();
                            }, _DELAY_PROCESS_);
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
                        
                        cancelButton.bootstrapBtn('reset');
                    },
                    error: function() {
                        app.alertSomethingError();
                        
                        cancelButton.bootstrapBtn('reset');
                    }
                });
            }, _DELAY_PROCESS_);
            
        },
        createRoomInvoiceProcess: function() {
            var buttonCreate = page.getElement.getCreateRoomInvoiceButton();
            var checkboxs = page.getElement.getRoomCheckbox();
            var roomIdSet = [];
            var _getRoomNoToShow = function(roomIdSet) {
                var boxRoom_ = page.getElement.getBoxRoom_();
                var msg = '';
                
                for(var index in roomIdSet) {
                    /*msg  += boxRoom_.find('.box-room[data-id="' + roomIdSet[index] + '"]')
                            .find('.box-room-name').attr('data-room-name') + ', ';*/
                    
                    msg  += boxRoom_.find('.box-room[data-id="' + roomIdSet[index] + '"]')
                            .find('.box-room-name').attr('data-room-name') + ', ';
                }

                return msg.slice(0, -2);
            };
            var _createRoomInvoice = function() {
                buttonCreate.bootstrapBtn('loading');
                
                setTimeout(function() {
                    jQuery.ajax({
                        type: 'post',
                        url: _CONTEXT_PATH_ + '/create_room_invoice.html',
                        data: {
                            id: roomIdSet,
                            month: page.roomInvoiceMonthYear.getMonth(),
                            year: page.roomInvoiceMonthYear.getYear()
                        },
                        cache: false,
                        success: function(response) {
                            response = app.convertToJsonObject(response);
                            
                            if(response.result == SUCCESS_STRING) {
                                app.showNotice({
                                    message: app.translate('common.save_success'),
                                    type: response.result
                                });
                                
                                page.getRoom();
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
                            
                            buttonCreate.bootstrapBtn('reset');
                        },
                        error: function() {
                            app.alertSomethingError();
                            
                            buttonCreate.bootstrapBtn('reset');
                        }
                    });
                }, _DELAY_PROCESS_);
            };


            /** main process */
            
            
            if(checkboxs.length == 0) {
                if(!app.checkNoticeExist('notice-select-data')) {
                    app.showNotice({
                        type: WARNING_STRING,
                        message: app.translate('common.please_enter_data'),
                        addclass: 'notice-select-data'
                    });
                }
                
                page.getElement.getBuildingList().addClass(INPUT_ERROR_CLASS);
            }
            else {
                page.getElement.getBuildingList().removeClass(INPUT_ERROR_CLASS);
                
                var countCheckbox = 0;
                
                checkboxs.filter(function() {
                    return jQuery(this).prop('checked');
                }).each(function() {
                    var thisCheckbox = jQuery(this);
                    var id = thisCheckbox.closest('.box-room_').find('[name="id"]').val();
                    
                    countCheckbox = countCheckbox + 1;
                    
                    roomIdSet.push(id);
                });
                
                if(countCheckbox == 0) {
                    if(!app.checkNoticeExist('notice-checked-data')) {
                        app.showNotice({
                            type: WARNING_STRING,
                            message: app.translate('room.invoice.please_checked_room'),
                            addclass: 'notice-checked-data'
                        });
                    }
                }
                else {
                    var messageToShow = '<div style="text-align: center; font-size: 15px;">' + _getRoomNoToShow(roomIdSet) + '</div>';
                    
                    alertUtil.confirmAlert(app.translate('room.invoice.create_invoice_for_room')
                        + '<br><br>'
                        + messageToShow, function() {
                        _createRoomInvoice();
                    }, function() {

                    },{
                        animation: false,
                        type: null
                    });
                }
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
                    
                            boxRoomElement.find('.box-room-name')
                                    .attr('data-room-name', currentData.roomNo);
                    
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
                    };
                    
                    page.getElement.getBuildingList().removeClass(INPUT_ERROR_CLASS);
                    
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
            
            var _setAlreadyInvoiced = function(response) {
                var alreadyInvoicedData = response.data.roomInvoiceRoomDetailList;
                var labelAlreadyInvoicedTemplate = '<div class="already-invoiced">' 
                        + app.translate('room.invoice.already_invoice') 
                        + ' '
                        + '<button type="button" '
                            + 'class="btn btn-danger btn-flat btn-sm cancel-invoice-button">Cancel</button>'
                        + '</div>';
                
                for(var index in alreadyInvoicedData) {
                    var currentData = alreadyInvoicedData[index];
                    var boxRoom = jQuery('.box-room[data-id="' + currentData.roomId + '"]');
                    var boxRoom_ = boxRoom.closest('.box-room_');
                    
                    boxRoom_.append(labelAlreadyInvoicedTemplate);
                    boxRoom_.find('.already-invoiced').append('<br>' + '<span class="invoice-no">' + currentData.invoiceNo + '</span>');
                    boxRoom_.find('.room-checkbox')
                            .attr('disabled', 'disabled')
                            .attr(_ALREADY_INVOICED_ATTR_, 'true');
                    boxRoom_.find('.cancel-invoice-button').attr(_CANCEL_INVOICE_ATTR_, currentData.id);
                }
            };
            
            var _setNotHaveMeterData = function(response) {
                var electriccityMeterData = response.data.roomElectricityWaterMeter.electricityMeter;
                var boxRoom_ = page.getElement.getBoxRoom_();
                var tempArrayRoomId = [];
                var labelNotHaveMeter = '<div class="not-have-meter">' 
                        + app.translate('room.invoice.not_have_meter_of_this_month') + '</div>';
                
                
                for(var indexEW in electriccityMeterData) {
                    var currentData = electriccityMeterData[indexEW];
                    
                    tempArrayRoomId.push(currentData.roomId.toString());
                }

                boxRoom_.each(function() {
                    var thisBoxRoom_ = jQuery(this);
                    var boxRoomId = thisBoxRoom_.find('[name="id"]').val();

                    if(jQuery.inArray(boxRoomId, tempArrayRoomId) == -1) {
                        thisBoxRoom_.append(labelNotHaveMeter);
                        thisBoxRoom_.find('.room-checkbox')
                                .attr('disabled', 'disabled')
                                .attr(_NOT_HAVE_METER_ATTR_, 'true');
                    }
                });
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
                data: {
                    building_id: page.getElement.getBuildingList().val(),
                    month: page.roomInvoiceMonthYear.getMonth(),
                    year: page.roomInvoiceMonthYear.getYear()
                },
                cache: false,
                success: function(response) {
                    response = app.convertToJsonObject(response);
                    
                    if(response.message == SESSION_EXPIRE_STRING) {
                        app.alertSessionExpired();
                    }
                    else {
                        _setCurrentCheckInLabel(response);
                        _setAlreadyInvoiced(response);
                        _setNotHaveMeterData(response);
                        _closeWithClearFix();
                    }
                },
                error: function() {
                    app.showNotice({
                        type: WARNING_STRING,
                        message: app.translate('room.cannot_get_room_manage_detail_list'),
                        addclass: 'notice-get-room-manage-detail'
                    });
                }
                
            });
        }
    };
})();