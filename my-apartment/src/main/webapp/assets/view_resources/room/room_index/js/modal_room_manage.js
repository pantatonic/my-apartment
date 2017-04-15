

/* global _CONTEXT_PATH_, _DELAY_PROCESS_, app, SUCCESS_STRING, SESSION_EXPIRE_STRING, DATA_NOT_FOUND_STRING */

var modalRoomManage = (function() {
    var _getModal = function() {
        return jQuery('#modal-room-manage');
    };
    
    var _getRoomReservationForm = function() {
        return jQuery('[name="room_reservation_form"]');
    };
    
    return {
        getModal: function() {
            return _getModal();
        },
        getRoomReservationForm: function() {
            return _getRoomReservationForm();
        },
        getRoomManage: function(roomId) {
            var _setData = function(response) {
                var modal = _getModal();
                var roomReservationForm = _getRoomReservationForm();
                var setCurrentReservation = function() {
                    var data = response.data.roomReservarion;
                    var data_ = data[0];
                    var currentDateString = page.getCurrentDateString();
                    
                    app.clearFormData(roomReservationForm);
                    roomReservationForm.find('[name="id"]').val('');

                    if(data.length == 0) {
                        roomReservationForm.find('[name="room_id"]').val(roomId);
                        roomReservationForm.find('[name="status"]').val('1');
                        roomReservationForm.find('[name="reserve_date"]').val(currentDateString);
                        roomReservationForm.find('#reserve-status-form-group').hide();
                        
                        roomReservationForm.hide();
                        modal.find('#new-room-reservation').show();
                    }
                    else {
                        roomReservationForm.show();
                        modal.find('#new-room-reservation').hide();
                        roomReservationForm.find('#reserve-status-form-group').show();
                        
                        for(var key in data_) {
                            var key_ = app.camelToUnderScore(key);
                            roomReservationForm.find('[name="' + key_ + '"]').val(data_[key]);
                            
                            //app.triggerCtrl(modalBody.find('[name="' + key_ + '"]'));
                        }
                    }
                };
                
                /** begin main process */
                setCurrentReservation();
                
                modal.modal('show');
                
                app.loading('remove');
            };
            
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
        saveRoomReservation: function() {
            var form_ = modalRoomManage.getRoomReservationForm();
            var submitButton = form_.find('[type="submit"]');
            
            /** begin main process */
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
                                latestRoomIdProcess = response.id;
                            }
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
        }
        
    };
})();