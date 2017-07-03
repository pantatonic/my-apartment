/* global app, _DELAY_PROCESS_, _CONTEXT_PATH_, SESSION_EXPIRE_STRING, iconRoom, ERROR_STRING, WARNING_STRING, INPUT_ERROR_CLASS, SUCCESS_STRING, alertUtil */
var _ALREADY_INVOICED_ATTR_ = 'already-invoiced';
var _NOT_HAVE_METER_ATTR_ = 'not-have-meter';
var _INVOICE_ID_ATTR_ = 'data-invoice-id';
var _RECEIPT_ID_ATTR_ = 'data-receipt-id';
var _RECEIPT_NO_ATTR_ = 'data-receipt-no';


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
            page.addEvent.showRoomInvoice();
            page.addEvent.pdfRoomInvoice();
            page.addEvent.payInvoice();
            page.addEvent.showRoomReceipt();
            page.addEvent.cancelRoomReceipt();
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
                
                jQuery('#cancel-receipt-process-button').click(function() {
                    page.cancelRoomReceiptProcess();
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
            },
            pdfRoomInvoice: function() {
                var buttonPdf = page.getElement.getPdfRoomInvoiceButton();
                
                buttonPdf.click(function() {
                    var modal_ = page.getElement.getModalPdfRoomInvoice();
                    
                    var checkboxs = page.getElement.getRoomCheckbox();
                    
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
                        var tableTemplate = jQuery('#table-pdf-invoice-template').val();
                        var boxRoom_ = page.getElement.getBoxRoom_();
                        var html_ = '';
                        
                        modal_.find('.modal-body').html(tableTemplate);
                        
                        boxRoom_.each(function() {
                            var thisBoxRoom_ = jQuery(this);
                            var alreadyInvoiced = thisBoxRoom_.find('.already-invoiced');
                            
                            if(alreadyInvoiced.length > 0) {
                                var currentInvoiceId = alreadyInvoiced.attr(_INVOICE_ID_ATTR_);
                                var labelRoomNo = thisBoxRoom_.attr('data-room-no');
                                
                                html_ += '<tr>'
                                    + '<td class="text-center"><input type="checkbox" value="' + currentInvoiceId + '"></td>'
                                    + '<td>' + labelRoomNo + '</td>'
                                    + '</tr>';
                            }
                        });
                        
                        html_ = html_.length == 0 ? '<tr><td colspan="2" class="text-center">_No Room To Export PDF Invoice_</td></tr>' : html_;
                        
                        var table_ = modal_.find('#table-pdf-invoice');
                        
                        table_.find('tbody').html(html_);
                        table_.find('input[type="checkbox"]').prop('checked', true);
                        
                        
                        
                        modal_.modal('show');
                    }
                });
                
                page.getElement.getModalPdfRoomInvoice().on('click', '#main-pdf-invoice-checkbox', function() {
                    var thisElement = jQuery(this);
                    var modal_ = page.getElement.getModalPdfRoomInvoice();
                    var checkboxs = modal_.find('#table-pdf-invoice tbody').find('input[type="checkbox"]');
                    
                    if(thisElement.is(':checked')) {
                        checkboxs.prop('checked', true);
                    }
                    else {
                        checkboxs.prop('checked', false);
                    }
                });
                
                page.getElement.getModalPdfRoomInvoice().find('#pdf-invoice-process-button').click(function() {
                    page.pdfRoomInvoiceProcess();
                });
            },
            showRoomInvoice: function() {
                var boxRoomContainer = page.getElement.getBoxRoomContainer();
                
                boxRoomContainer.on('click', '.invoice-detail', function() {
                    var thisElement = jQuery(this);
                    
                    page.showRoomInvoice(thisElement);
                });
            },
            payInvoice: function() {
                var boxRoomContainer = page.getElement.getBoxRoomContainer();
                
                boxRoomContainer.on('click', '.pay-invoice', function() {
                    var thisElement = jQuery(this);
                    var roomInvoiceId = thisElement.attr(_INVOICE_ID_ATTR_);
                    var jsonData = thisElement.closest('.already-invoiced').find('.invoice-json-data').text();
                    jsonData = JSON.parse(jsonData);
                    
                    var roomNo = thisElement.closest('.box-room_').attr('data-room-no');
                    var grandTotal = jsonData.electricityValue + jsonData.waterValue + jsonData.roomPricePerMonth;
                    var html_ = app.translate('building.room') + ' : ' + roomNo
                        + '<br><br>'
                        + '<table id="table-pay-invoice" class="table table-bordered">'
                            + '<thead><tr><th colspan="2" style="text-align: center;">' + app.translate('common.pay') + '</th></tr></thead>'
                            + '<tbody>'
                                + '<tr>'
                                    + '<td>' + app.translate('electricity_water_meter.electricity_meter') + '</td>'
                                    + '<td>' + app.valueUtils.numberFormat(jsonData.electricityValue) + ' ' + app.translate('common.baht') + '</td>'
                                + '</tr>'
                                + '<tr>'
                                    + '<td>' + app.translate('electricity_water_meter.water_meter') + '</td>'
                                    + '<td>' + app.valueUtils.numberFormat(jsonData.waterValue) + ' ' + app.translate('common.baht') + '</td>'
                                + '</tr>'
                                + '<tr>'
                                    + '<td>' + app.translate('room.invoice.room_price') + '</td>'
                                + '<td>' + app.valueUtils.numberFormat(jsonData.roomPricePerMonth) + ' ' + app.translate('common.baht') + '</td>'
                                + '</tr>'
                                + '<tr>'
                                    + '<td>' + app.translate('common.grand_total') + '</td>'
                                    + '<td>' + app.valueUtils.numberFormat(grandTotal) + ' ' + app.translate('common.baht') + '</td>'
                                + '</tr>'
                            + '</tbody>'
                        + '</table>';
                        
                    
                    alertUtil.confirmAlert(html_, function() {
                        page.createRoomReceiptProcess(roomInvoiceId);
                    }, function() {

                    },{
                        animation: false,
                        type: null
                    });
                });
            },
            showRoomReceipt: function() {
                var boxRoomContainer = page.getElement.getBoxRoomContainer();
                
                boxRoomContainer.on('click', '.receipt-detail', function() {
                    var thisElement = jQuery(this);
                    
                    //page.showRoomReceipt(thisElement);
                    alert('Goto show room receipt');
                });
            },
            cancelRoomReceipt: function() {
                var boxRoomContainer = page.getElement.getBoxRoomContainer();
                
                boxRoomContainer.on('click', '.cancel-receipt-button', function() {
                    var thisElement = jQuery(this);
                    
                    page.cancelRoomReceipt(thisElement);
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
            getPdfRoomInvoiceButton: function() {
                return jQuery('#pdf-room-invoice');
            },
            getPdfRoomInvoiceProcessButtin: function() {
                return jQuery('#pdf-invoice-process-button');
            },
            getRoomCheckbox: function() {
                return jQuery('.room-checkbox');
            },
            getRoomInvoiceMonthYear: function() {
                return jQuery('#room-invoice-month-year');
            },
            getModalCancelRoomInvoice: function() {
                return jQuery('#modal-cancel-room-invoice');
            },
            getModalRoomInvoiceDetail: function() {
                return jQuery('#modal-room-invoice-detail');
            },
            getModalCancelRoomReceipt: function() {
                return jQuery('#modal-cancel-room-receipt');
            },
            getModalPdfRoomInvoice: function() {
                return jQuery('#modal-room-pdf-invoice');
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
        cancelRoomReceipt: function(cancelButton) {
            var receiptId = cancelButton.attr(_RECEIPT_ID_ATTR_);
            var receiptNo = cancelButton.attr(_RECEIPT_NO_ATTR_);
            var modal_ = page.getElement.getModalCancelRoomReceipt();
            
            modal_.find('#receipt-display').html(receiptNo);
            modal_.find('[name="room_receipt_id"]').val(receiptId);
            
            /** clear old data */
            modal_.find('[name="cancel_description"]').val('');
            
            modal_.modal('show');
        },
        cancelRoomReceiptProcess: function() {
            var modal_ = page.getElement.getModalCancelRoomReceipt();
            
            var roomReceiptId = modal_.find('[name="room_receipt_id"]').val();
            var description = modal_.find('[name="cancel_description"]').val();
            
            var cancelButton = modal_.find('#cancel-receipt-process-button');
            
            cancelButton.bootstrapBtn('loading');
            
            setTimeout(function() {
                jQuery.ajax({
                    type: 'post',
                    url: _CONTEXT_PATH_ + '/cancel_room_receipt.html',
                    data: {
                        room_receipt_id: roomReceiptId,
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
        createRoomReceiptProcess: function(roomInvoiceId) {
            var buildingList = page.getElement.getBuildingList();
            var contentBox = buildingList.closest('.box-primary');
            
            app.loadingInElement('show', contentBox);
            
            setTimeout(function() {
                jQuery.ajax({
                    type: 'post',
                    url: _CONTEXT_PATH_ + '/create_room_receipt.html',
                    data: {
                        room_invoice_id: roomInvoiceId
                    },
                    cache: false,
                    success: function(response) {
                        response = app.convertToJsonObject(response);
                        
                        if(response.result == SUCCESS_STRING) {
                            app.showNotice({
                                message: app.translate('common.save_success'),
                                type: response.result
                            });
                            
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
                            
                            app.loadingInElement('remove', contentBox);
                        }
                    },
                    error: function() {
                        app.alertSomethingError();
                        
                        app.loadingInElement('remove', contentBox);
                    }
                });
            }, _DELAY_PROCESS_);
        },
        showRoomInvoice: function(invoiceDetailButton) {
            var roomInvoiceId = invoiceDetailButton.attr(_INVOICE_ID_ATTR_);
            var roomNo = invoiceDetailButton.closest('.box-room_').attr('data-room-no');
            var buildingList = page.getElement.getBuildingList();
            var contentBox = buildingList.closest('.box-primary');
            var modal_ = page.getElement.getModalRoomInvoiceDetail();
            var jsonData = invoiceDetailButton.closest('.already-invoiced').find('.invoice-json-data').text();
            jsonData = JSON.parse(jsonData);
            var electricityMinCalMsgElement = jQuery('#electricity-min-calculate-message');
            var waterMinCalMsgElement = jQuery('#water-min-calculate-message');

            app.loadingInElement('show', contentBox);

            setTimeout(function() {
                
                app.modalUtils.bodyScrollTop(modal_);
                for(var index in jsonData) {
                    if(modal_.find('[data-key="' + index + '"]').length > 0) {
                        if(typeof jsonData[index] == 'number') {
                            modal_.find('[data-key="' + index + '"]').html(app.valueUtils.numberFormat(jsonData[index]));
                        }
                        else {
                            modal_.find('[data-key="' + index + '"]').html(jsonData[index]);
                        }
                    }
                    
                    if(index == 'electricityUseMinimunUnitCalculate') {
                        if(jsonData.electricityUseMinimunUnitCalculate) {
                            modal_.find('#min-electricity-unit-display')
                                    .html(app.valueUtils.undefinedToEmpty(jsonData.minElectricityUnit));
                            modal_.find('#min-electricity-charge-display')
                                    .html(app.valueUtils.undefinedToEmpty(jsonData.minElectricityCharge));
                            
                            modal_.find('#min-water-unit-display')
                                    .html(app.valueUtils.undefinedToEmpty(jsonData.minWaterUnit));
                            modal_.find('#min-water-charge-display')
                                    .html(app.valueUtils.undefinedToEmpty(jsonData.minWaterCharge));
                            
                            
                            electricityMinCalMsgElement.show();
                        }
                        else {
                            electricityMinCalMsgElement.hide();
                        }
                    }
                }

                var grandTotal = jsonData.electricityValue + jsonData.waterValue + jsonData.roomPricePerMonth;
                
                modal_.find('#grand-total-display').html(app.valueUtils.numberFormat(grandTotal));
                modal_.find('#room-no-display').html(roomNo);
                
                modal_.modal('show');

                app.loadingInElement('remove', contentBox);
            }, _DELAY_PROCESS_);
            
            
        },
        cancelRoomInvoice: function(cancelButton) {
            var roomInvoiceId = cancelButton.attr(_INVOICE_ID_ATTR_);
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
        pdfRoomInvoiceProcess: function() {
            var modal_ = page.getElement.getModalPdfRoomInvoice();
            var buttonPdfProcess = page.getElement.getPdfRoomInvoiceProcessButtin();
            var invoiceIdSet = [];
            var table_ = modal_.find('#table-pdf-invoice');
            var checkboxs = table_.find('tbody').find('input[type="checkbox"]');
            
            var _pdfInvoiceProcess = function() {
                buttonPdfProcess.bootstrapBtn('loading');

                var width_ = (screen.width / 1.3);
                var height_ = (screen.height - 110);

                var params = [
                    'width=' + width_,
                    'height=' + height_,
                    'left=' + ((screen.width / 2)-(width_ / 2)),
                    //'top=' + ((screen.height / 2)-(height_ / 2)),
                    'top=10',
                    'fullscreen=yes'
                ].join(',');
                
                var url_ = '';
                var windowName = 'room_invoice_pdf';


                setTimeout(function() {
                    var form_ = jQuery('#pdf-invoice-form');
                    
                    form_.html('');
                    
                    for(var index in invoiceIdSet) {
                        form_.append('<input type="hidden" name="pdf_room_invoice_id" value="' + invoiceIdSet[index] + '" >');
                    }

                    window.open(url_, windowName, params);
                    
                    setTimeout(function() {
                        jQuery('#pdf-invoice-form').submit();
                    }, _DELAY_PROCESS_);
                    
                    buttonPdfProcess.bootstrapBtn('reset');
                }, _DELAY_PROCESS_);
            };
            
            
            
            /** main process */
            
            var countCheckbox = 0;
            
            checkboxs.filter(function() {
                return jQuery(this).prop('checked');
            }).each(function() {
                var thisCheckbox = jQuery(this);
                var roomInvoiceId = thisCheckbox.val();
                
                countCheckbox = countCheckbox + 1;
                
                invoiceIdSet.push(roomInvoiceId);
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
                _pdfInvoiceProcess();
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
                            + 'class="btn btn-danger btn-flat btn-sm cancel-invoice-button">' + app.translate('common.cancel') + '</button>'
                        + '</div>';
                var labelAlreadyReceiptTemplate = '<div class="already-receipt">'
                    + app.translate('room.invoice.already_receipt')
                    + ' '
                    + '<button type="button" '
                        + 'class="btn btn-danger btn-flat btn-sm cancel-receipt-button">' + app.translate('common.cancel') + '</button>'
                    + '</div>';
                    
                
                for(var index in alreadyInvoicedData) {
                    var currentData = alreadyInvoicedData[index];
                    var boxRoom = jQuery('.box-room[data-id="' + currentData.roomId + '"]');
                    var boxRoom_ = boxRoom.closest('.box-room_');
                    var receiptNo = app.valueUtils.undefinedToEmpty(currentData.receiptNo);

                    boxRoom_.append(labelAlreadyInvoicedTemplate);
                    
                    var alreadyInvoiceElement = boxRoom_.find('.already-invoiced');
                    
                    boxRoom_.find('.room-icon').fadeTo('slow', 0.1);
                    
                    alreadyInvoiceElement.attr(_INVOICE_ID_ATTR_, currentData.id);
                    
                    alreadyInvoiceElement.append('<br>' 
                            + '<span class="invoice-no">' 
                            + currentData.invoiceNo + ' '
                            + '<i class="fa fa-search invoice-detail"></i>'
                            + '</span>');
                    boxRoom_.find('.room-checkbox')
                            .attr('disabled', 'disabled')
                            .attr(_ALREADY_INVOICED_ATTR_, 'true');
                    boxRoom_.find('.cancel-invoice-button').attr(_INVOICE_ID_ATTR_, currentData.id);
                    boxRoom_.find('.invoice-detail').attr(_INVOICE_ID_ATTR_, currentData.id);
                    
                    alreadyInvoiceElement.append('<span class="invoice-json-data">'
                            + JSON.stringify(currentData)
                            + '</span>');
                    
                    alreadyInvoiceElement.append('<br>'
                            + '<button type="button" ' 
                            + 'class="btn btn-warning btn-sm btn-flat pay-invoice" '
                            + _INVOICE_ID_ATTR_ + '="' + currentData.id + '">'
                            + '<i class="fa fa-money"></i> ' + app.translate('common.pay') + '</button>');
                    
                    if(!app.valueUtils.isEmptyValue(receiptNo)) {
                        boxRoom_.append(labelAlreadyReceiptTemplate);
                        
                        var alreadyReceiptElement = boxRoom_.find('.already-receipt');
                        
                        boxRoom_.find('.pay-invoice').remove();
                        boxRoom_.find('.cancel-invoice-button').remove();
                        
                        alreadyReceiptElement.append('<br>' 
                            + '<span class="receipt-no">' 
                            + receiptNo + ' '
                            + '<i class="fa fa-search receipt-detail"></i>'
                            + '</span>');
                    
                        boxRoom_.find('.cancel-receipt-button').attr(_INVOICE_ID_ATTR_, currentData.id);
                        boxRoom_.find('.cancel-receipt-button').attr(_RECEIPT_ID_ATTR_, currentData.receiptId);
                        boxRoom_.find('.cancel-receipt-button').attr(_RECEIPT_NO_ATTR_, currentData.receiptNo);
                    }
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