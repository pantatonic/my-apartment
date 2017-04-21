
/* global app, SESSION_EXPIRE_STRING, _CONTEXT_PATH_, SUCCESS_STRING, modalRoomManage, _DELAY_PROCESS_, page */

var roomManageCheckIn = (function() {
    
    return {
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