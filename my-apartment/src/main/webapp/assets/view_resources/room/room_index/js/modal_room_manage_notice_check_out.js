/* global modalRoomManage, _DELAY_PROCESS_, app, _CONTEXT_PATH_, page */

var noticeCheckOut = (function() {
    
    return {
        newNoticeCheckOut: {
            show: function() {
                page.getElement.getNewNoticeCheckOutButton().closest('div.form-group').show();
            },
            hide: function() {
                page.getElement.getNewNoticeCheckOutButton().closest('div.form-group').hide();
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