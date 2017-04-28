/* global _DELAY_PROCESS_, app, SESSION_EXPIRE_STRING, INPUT_ERROR_CLASS, page, DATA_DUPLICATED_STRING, SUCCESS_STRING, _CONTEXT_PATH_, WARNING_STRING, REQUIRED_CLASS, DATA_NOT_FOUND_STRING */

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
        },
        setBuildingId: function() {
            var buildingList = page.getElement.getBuildingListElement();
            var form_ = modalRoomDetail.getForm();
            
            if(!app.valueUtils.isEmptyValue(buildingList.val())) {
                form_.find('[name="building_id"]').val(buildingList.val());
            }
        },
        clearInputId: function() {
            var form = _getForm();
            
            form.find('[name="id"]').val('');
            form.find('[name="building_id"]').val('');
        },
        setFocusAfterOpenModal: function() {
            setTimeout(function() {
                _getModal().find('[name="room_no"]').focus();
            }, _DELAY_PROCESS_);
        },
        getRoomDetail: function(roomId) {
            var _setData = function(response) {
                var data = response.data[0];
                var modal = _getModal();
                var modalBody = modal.find('.modal-body');
                var __setMaxlengthMeterDigit = function(element) {
                    if(element.attr('name') == 'startup_electricity_meter') {
                        element.attr('maxlength', data.electricityMeterDigit);
                    }
                    
                    if(element.attr('name') == 'startup_water_meter') {
                        element.attr('maxlength', data.waterMeterDigit);
                    }
                };

                app.clearAllInputErrorClass(modalBody);
                app.clearFormData(modalRoomDetail.getForm());
                modalRoomDetail.clearInputId();
                
                for(var key in data) {
                    var key_ = app.camelToUnderScore(key);
                    modalBody.find('[name="' + key_ + '"]').val(data[key]);

                    app.triggerCtrl(modalBody.find('[name="' + key_ + '"]'));
                    
                    if(jQuery.inArray(key_, ['startup_electricity_meter', 'startup_water_meter']) != -1) {
                        __setMaxlengthMeterDigit(modalBody.find('[name="' + key_ + '"]'));
                    }
                }
                
                modal.modal('show');
                
                modalRoomDetail.setFocusAfterOpenModal();
            };
            
            app.loading('show');
            
            setTimeout(function() {
                jQuery.ajax({
                    type: 'get',
                    url: _CONTEXT_PATH_ + '/room_get_by_id.html',
                    data: {
                        id: roomId
                    },
                    cache: false,
                    success: function(response) {
                        response = app.convertToJsonObject(response);
                        
                        app.loading('remove');
                        
                        if(response.result == SUCCESS_STRING) {
                            _setData(response);
                        }
                        else {
                            if(response.message == SESSION_EXPIRE_STRING) {
                                app.alertSessionExpired();
                            }
                            else
                            if(response.message == DATA_NOT_FOUND_STRING) {
                                app.showNotice({
                                    message: app.translate('common.data_not_found'),
                                    type: response.result
                                });
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
        save: function() {
            var modal = modalRoomDetail.getModal();
            var form_ = _getForm();
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
            
            var resultValidate = _validate();
            
            if(!resultValidate) {
                return false;
            }
            
            submitButton.bootstrapBtn('loading');
            
            setTimeout(function() {
                jQuery.ajax({
                    type: 'post',
                    url: _CONTEXT_PATH_ + '/room_save.html',
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
                            
                            modal.modal('hide');
                            page.getRoom();
                        }
                        else {
                            if(response.message == DATA_DUPLICATED_STRING) {
                                app.showNotice({
                                    message: app.translate('room.room_no_is_duplicated'),
                                    type: response.result
                                });
                                
                                page.getElement.getModalRoomNo()
                                        .focus()
                                        .addClass(INPUT_ERROR_CLASS);
                            }
                            else 
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