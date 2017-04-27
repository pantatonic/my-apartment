/* global modalRoomManage, _DELAY_PROCESS_, app, _CONTEXT_PATH_, page, alertUtil */

var noticeCheckOut = (function() {
    
    return {
        removeNoticeCheckOutButton: {
            show: function() {
                page.getElement.getRemoveNoticeCheckOutButton().closest('div.form-group').show();
            },
            hide: function() {
                page.getElement.getRemoveNoticeCheckOutButton().closest('div.form-group').hide();
            }
        },
        saveNoticeCheckOut: function() {
            var form_ = modalRoomManage.getNoticeCheckOutForm();
            var submitButton = form_.find('[type="submit"]');
            
            submitButton.bootstrapBtn('loading');
            
            setTimeout(function() {
                jQuery.ajax({
                    type: 'post',
                    url: _CONTEXT_PATH_ + '/notice_check_out_save.html',
                    data: form_.serialize(),
                    cache: false,
                    success: function(response) {
                        response = app.convertToJsonObject(response);
                        
                        if(response.result === SUCCESS_STRING) {
                            app.showNotice({
                                message: app.translate('common.save_success'),
                                type: response.result
                            });

                            latestRoomIdProcess = response.roomId;
                            
                            page.getRoom();
                            noticeCheckOut.removeNoticeCheckOutButton.show();
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
        removeNoticeCheckOut: function() {
            var form_ = modalRoomManage.getNoticeCheckOutForm();
            var roomId = form_.find('[name="room_id"]').val();
            var button = page.getElement.getRemoveNoticeCheckOutButton();
            
            alertUtil.confirmAlert(app.translate('common.please_confirm_to_process'), function() {
                button.bootstrapBtn('loading');
                
                setTimeout(function() {
                    jQuery.ajax({
                        type: 'post',
                        url: _CONTEXT_PATH_ + '/remove_notice_check_out.html',
                        data: {
                            room_id: roomId
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

                                modalRoomManage.preProcessForNoticeCheckOut(roomId);

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
                            
                            button.bootstrapBtn('reset');
                        },
                        error: function() {
                            app.alertSomethingError();

                            button.bootstrapBtn('reset');
                        }
                    });
                }, _DELAY_PROCESS_);
            }, function() {
                
            },{
                animation: false,
                type: null
            });
            
        }
    };
})();