/* global _CONTEXT_PATH_, _DELAY_PROCESS_, app, SUCCESS_STRING, SESSION_EXPIRE_STRING, DATA_NOT_FOUND_STRING, REQUIRED_CLASS, page, roomManageCheckIn, INPUT_ERROR_CLASS, WARNING_STRING, noticeCheckOut */

var modalRoomManage = (function() {
    var _getModal = function() {
        return jQuery('#modal-room-manage');
    };
    
    var _getRoomReservationForm = function() {
        return jQuery('[name="room_reservation_form"]');
    };
    
    var _getCurrentCheckInForm = function() {
        return jQuery('[name="room_check_in_form"]');
    };
    
    var _getNoticeCheckOutForm = function() {
        return jQuery('[name="notice_check_out_form"]');
    };
    
    var _getReservationList = function(roomId) {
        jQuery('#reservation-list').dataTable({
            destroy: true,
            autoWidth: false,
            processing: true,
            serverSide: true,  
            bFilter: false,
            ajax: {
                url: _CONTEXT_PATH_ + '/get_reservation_list.html',
                type: 'get',
                data: {
                    room_id: roomId
                }
            },
            columns: [
                {data: 'reserveDate'},
                {data: 'reserveExpired'},
                {data: 'idCard'},
                {data: 'name'},
                {data: 'status'}
            ],
            drawCallback: function(settings) {
                //app.data_table.set_full_width();
            }
        });
    };
    
    return {
        getModal: function() {
            return _getModal();
        },
        getRoomReservationForm: function() {
            return _getRoomReservationForm();
        },
        getCurrentCheckInForm: function() {
            return _getCurrentCheckInForm();
        },
        getNoticeCheckOutForm: function() {
            return _getNoticeCheckOutForm();
        },
        preProcessForNewReservation: function(roomId) {
            var roomReservationForm = _getRoomReservationForm();
            var currentDateString = page.getCurrentDateString();
            var modal = _getModal();
            
            roomReservationForm.find('[name="room_id"]').val(roomId);
            roomReservationForm.find('[name="status"]').val('1');
            roomReservationForm.find('[name="reserve_date"]').datepicker('update', currentDateString);
            roomReservationForm.find('#reserve-status-form-group').hide();

            roomReservationForm.hide();

            modal.find('#new-room-reservation').show();
        },
        preProcessForNewCurrentCheckIn: function(roomId) {
            var currentCheckInForm = _getCurrentCheckInForm();
            var currentDateString = page.getCurrentDateString();
            var modal = _getModal();
            
            currentCheckInForm.find('[name="room_id"]').val(roomId);
            currentCheckInForm.find('[name="check_in_date"]').datepicker('update', currentDateString);
            
            currentCheckInForm.hide();
            
            modal.find('#new-current-check-in').show();
        },
        preProcessForNoticeCheckOut: function(roomId) {
            var noticeCheckOutForm = _getNoticeCheckOutForm();
            var currentDateString = page.getCurrentDateString();
            var modal = _getModal();
            
            noticeCheckOutForm.find('[name="room_id"]').val(roomId);
            noticeCheckOutForm.find('[name="notice_check_out_date"]').datepicker('update', currentDateString);
            
            noticeCheckOutForm.hide();
            
            modal.find('#new-notice-check-out').show();
        },
        getRoomManage: function(roomId, buttonRoomManage) {
            var _setData = function(response) {
                var roomNoLabel = buttonRoomManage.closest('.box-room_').attr('data-room-no');
                var modal = _getModal();
                var roomReservationForm = _getRoomReservationForm();
                var setCurrentReservation = function() {
                    var data = response.data.roomReservarion;
                    var data_ = data[0];
                    var currentDateString = page.getCurrentDateString();
                    
                    app.clearFormData(roomReservationForm);
                    roomReservationForm.find('[name="id"]').val('');

                    if(data.length == 0) {
                        modalRoomManage.preProcessForNewReservation(roomId);
                        /*roomReservationForm.find('[name="room_id"]').val(roomId);
                        roomReservationForm.find('[name="status"]').val('1');
                        roomReservationForm.find('[name="reserve_date"]').datepicker('update', currentDateString);
                        roomReservationForm.find('#reserve-status-form-group').hide();
                        
                        roomReservationForm.hide();
                        modal.find('#new-room-reservation').show();*/
                    }
                    else {
                        roomReservationForm.show();
                        modal.find('#new-room-reservation').hide();
                        roomReservationForm.find('#reserve-status-form-group').show();
                        
                        for(var key in data_) {
                            var key_ = app.camelToUnderScore(key);
                            roomReservationForm.find('[name="' + key_ + '"]').val(data_[key]);
                        }
                        
                        roomReservationForm.find('.input-datepicker').each(function() {
                            var thisEle = jQuery(this);
                            
                            if(thisEle.attr('name') == 'reserve_date') {
                                thisEle.datepicker('update', app.valueUtils.undefinedToEmpty(data_.reserveDate));
                            }
                            
                            if(thisEle.attr('name') == 'reserve_expired') {
                                thisEle.datepicker('update', app.valueUtils.undefinedToEmpty(data_.reserveExpired));
                            }
                        });
                    }
                };
                
                var setCurrentCheckIn = function() {
                    var data = response.data.currentCheckIn;
                    var data_ = data[0];
                    var currentCheckInForm = _getCurrentCheckInForm();
                    
                    app.clearFormData(currentCheckInForm);
                    currentCheckInForm.find('[name="number_code"]').val('');
                    
                    if(data.length == 0) {
                        modalRoomManage.preProcessForNewCurrentCheckIn(roomId);
                    }
                    else {
                        roomManageCheckIn.checkOutRoomButton.show();

                        currentCheckInForm.show();
                        modal.find('#new-current-check-in').hide();
                        
                        for(var key in data_) {
                            var key_ = app.camelToUnderScore(key);
                            currentCheckInForm.find('[name="' + key_ + '"]').val(data_[key]);
                        }
                        
                        currentCheckInForm.find('.input-datepicker').each(function() {
                            var thisEle = jQuery(this);
                            
                            if(thisEle.attr('name') == 'check_in_date') {
                                thisEle.datepicker('update', app.valueUtils.undefinedToEmpty(data_.checkInDate));
                            }
                        });
                    }
                };
                
                var setNoticeCheckOut = function() {
                    var data = response.data.noticeCheckOut;
                    var data_ = data[0];
                    var noticeCheckOutForm = _getNoticeCheckOutForm();
                    
                    app.clearFormData(noticeCheckOutForm);

                    if(data.length == 0) {
                        modalRoomManage.preProcessForNoticeCheckOut(roomId);
                    }
                    else {
                        noticeCheckOutForm.show();
                        modal.find('#new-notice-check-out').hide();

                        for(var key in data_) {
                            var key_ = app.camelToUnderScore(key);
                            noticeCheckOutForm.find('[name="' + key_ + '"]').val(data_[key]);
                        }
                        
                        noticeCheckOutForm.find('.input-datepicker').each(function() {
                            var thisEle = jQuery(this);
                            
                            if(thisEle.attr('name') == 'notice_check_out_date') {
                                thisEle.datepicker('update', app.valueUtils.undefinedToEmpty(data_.noticeCheckOutDate));
                            }
                        });
                    }
                };
                

                /** begin main process */
                page.setModalRoomNoLabel(roomNoLabel);


                setCurrentReservation();
                setCurrentCheckIn();
                setNoticeCheckOut();
                
                _getReservationList(roomId);
                roomManageCheckIn.getCheckInOutList(roomId);
                
                
                app.clearAllInputErrorClass(modalRoomManage.getRoomReservationForm());
                app.clearAllInputErrorClass(modalRoomManage.getCurrentCheckInForm());
                
                modal.modal('show');
                
                app.modalUtils.bodyScrollTop(modalRoomManage.getModal());
                
                app.loading('remove');
            };
            
            /** begin main process */
            app.loading('show');
            
            setTimeout(function() {
                jQuery.ajax({
                    type: 'get',
                    url: _CONTEXT_PATH_ + '/room/get_room_manage.html',
                    data: {
                        room_id: roomId
                    },
                    cache: false,
                    success: function(response) {
                        response = app.convertToJsonObject(response);

                        if(response.result == SUCCESS_STRING) {
                            _setData(response);
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
                        
                        app.loading('remove');
                    }
                }); 
            }, _DELAY_PROCESS_);
        },
        getReservationList: function(roomId) {
            return _getReservationList(roomId);
        },
        saveRoomReservation: function() {
            var form_ = modalRoomManage.getRoomReservationForm();
            var formDataStatusVal = form_.find('[name="status"]').val();
            
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
                    url: _CONTEXT_PATH_ + '/room_reservation_save.html',
                    data: form_.serialize(),
                    cache: false,
                    success: function(response) {
                        response = app.convertToJsonObject(response);
                        
                        if(response.result === SUCCESS_STRING) {
                            app.showNotice({
                                message: app.translate('common.save_success'),
                                type: response.result
                            });

                            if(response.id != undefined) {
                                form_.find('[name="id"]').val(response.id);
                                latestRoomIdProcess = response.roomId;
                            }

                            if(formDataStatusVal != '1') {
                                app.clearFormData(form_);
                                form_.find('[name="id"]').val('');
                                modalRoomManage.preProcessForNewReservation(response.roomId);
                            }
                            else {
                                form_.find('#reserve-status-form-group').show();
                            }
                            
                            page.getRoom();
                            modalRoomManage.getReservationList(response.roomId);
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
        saveCurrentCheckIn: function() {
            roomManageCheckIn.saveCurrentCheckIn();
        },
        saveNoticeCheckOut: function() {
            noticeCheckOut.saveNoticeCheckOut();
        }
        
    };
})();