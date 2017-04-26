
/* global app, SESSION_EXPIRE_STRING, _CONTEXT_PATH_, SUCCESS_STRING, modalRoomManage, _DELAY_PROCESS_, page, alertUtil */

var roomManageCheckIn = (function() {
    var _getCheckInOutList = function(roomId) {
        jQuery('#check-in-out-list').dataTable({
            destroy: true,
            autoWidth: false,
            processing: true,
            serverSide: true,  
            bFilter: false,
            ajax: {
                url: _CONTEXT_PATH_ + '/get_check_in_out_list.html',
                type: 'get',
                data: {
                    room_id: roomId
                }
            },
            columns: [
                {data: 'checkInDate'},
                {data: 'checkOutDate'},
                {data: 'idCard'},
                {data: 'name'},
                {data: 'type'}
            ],
            drawCallback: function(settings) {
                //app.data_table.set_full_width();
            }
        });
    };
    
    return {
        checkOutRoomButton: {
            show: function() {
                page.getElement.getCheckOutRoomButton().closest('div.form-group').show();
            },
            hide: function() {
                page.getElement.getCheckOutRoomButton().closest('div.form-group').hide();
            }
        },
        saveCurrentCheckIn: function() {
            var form_ = modalRoomManage.getCurrentCheckInForm();
            var submitButton = form_.find('[type="submit"]');
            var _validate = function() {
                var validatePass = true;
                var __validateRequired = function() {
                    var _validatePass = true;
                    
                    form_.find('.' + REQUIRED_CLASS).each(function() {
                        var thisElement = jQuery(this);
                        
                        if(app.valueUtils.isEmptyValue(thisElement.val())) {
                            _validatePass = false;

                            thisElement.addClass(INPUT_ERROR_CLASS);

                            if(!app.checkNoticeExist('notice-enter-data')) {
                                app.showNotice({
                                    type: WARNING_STRING,
                                    message: app.translate('common.please_enter_data'),
                                    addclass: 'notice-enter-data'
                                });
                            }
                        }
                        else {
                            thisElement.removeClass(INPUT_ERROR_CLASS);
                        }
                    });
                    
                    return _validatePass;
                };
                
                if(!__validateRequired()) {
                    validatePass = false;
                }
                
                return validatePass;
            };
            
            
            /** begin main process */
            var resultValidate = _validate();
            
            if(!resultValidate) {
                return false;
            }
            
            submitButton.bootstrapBtn('loading');
            
            setTimeout(function() {
                jQuery.ajax({
                    type: 'post',
                    url: _CONTEXT_PATH_ + '/room_check_in_save.html',
                    data: form_.serialize(),
                    cache: false,
                    success: function(response) {
                        response = app.convertToJsonObject(response);

                        if(response.result === SUCCESS_STRING) {
                            app.showNotice({
                                message: app.translate('common.save_success'),
                                type: response.result
                            });
                            
                            if(response.numberCode != undefined) {
                                form_.find('[name="number_code"]').val(response.numberCode);
                                latestRoomIdProcess = response.roomId;
                            }

                            page.getRoom();
                            roomManageCheckIn.getCheckInOutList(response.roomId);
                            
                            roomManageCheckIn.checkOutRoomButton.show();
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

                        submitButton.bootstrapBtn('reset');
                    },
                    error: function() {
                        app.alertSomethingError();

                        submitButton.bootstrapBtn('reset');
                    }
                });
            }, _DELAY_PROCESS_);
        },
        getCheckInOutList: function(roomId) {
            return _getCheckInOutList(roomId);
        },
        checkOut: function() {
            var form_ = modalRoomManage.getCurrentCheckInForm();
            var roomId = form_.find('[name="room_id"]').val();
            var numberCode = form_.find('[name="number_code"]').val();

            
            alertUtil.confirmAlert(app.translate('common.please_confirm_to_process'), function() {
                jQuery.ajax({
                    type: 'post',
                    url: _CONTEXT_PATH_ + '/room_check_out.html',
                    data: {
                        room_id: roomId,
                        number_code: numberCode
                    },
                    cache: false,
                    success: function(response) {
                        response = app.convertToJsonObject(response);
                        
                        if(response.result == SUCCESS_STRING) {
                            app.showNotice({
                                message: app.translate('common.save_success'),
                                type: response.result
                            });

                            app.clearFormData(form_);
                            form_.find('[name="number_code"]').val('');
                            
                            modalRoomManage.preProcessForNewCurrentCheckIn(roomId);
                            roomManageCheckIn.getCheckInOutList(roomId);
                            
                            latestRoomIdProcess = roomId;
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
                    },
                    error: function() {
                        app.alertSomethingError();

                        //buttonDelete.bootstrapBtn('reset');
                    }
                });
            }, function() {
                
            },{
                animation: false,
                type: null
            });
        }
    };
})();