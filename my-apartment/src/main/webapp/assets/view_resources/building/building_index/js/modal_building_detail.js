/* global _DELAY_PROCESS_, _CONTEXT_PATH_, app, SUCCESS_STRING, alert_util, SESSION_EXPIRE_STRING, REQUIRED_CLASS, INPUT_ERROR_CLASS, WARNING_STRING, page */

var modalBuildingDetail = (function() {
    var _getModalBuildingDetail = function() {
        return jQuery('#modal-building-detail');
    };
    
    var _getBuildingForm = function() {
        return jQuery('form[name="building_form"]');
    };
    
    return {
        getModal: function() {
            return _getModalBuildingDetail();
        },
        getForm: function() {
            return _getBuildingForm();
        },
        clearInputId: function() {
            _getBuildingForm().find('[name="id"]').val('');
        },
        checkUseElectricityWater: function() {
            var modal = _getModalBuildingDetail();
            var useMinElectricity = modal.find('#use-min-electricity');
            var useMinWater = modal.find('#use-min-water');
            
            if(useMinElectricity.is(':checked')) {
                modalBuildingDetail.minElectricityBlog.show();
            }
            else {
                modalBuildingDetail.minElectricityBlog.hide();
            }
            
            if(useMinWater.is(':checked')) {
                modalBuildingDetail.minWaterBlog.show();
            }
            else {
                modalBuildingDetail.minWaterBlog.hide();
            }
        },
        minElectricityBlog: {
            show: function() {
                _getModalBuildingDetail()
                        .find('#use-min-electricity-blog')
                        .show();
            },
            hide: function() {
                _getModalBuildingDetail()
                        .find('#use-min-electricity-blog')
                        .hide();
            }
        },
        minWaterBlog: {
            show: function() {
                _getModalBuildingDetail()
                        .find('#use-min-water-blog')
                        .show();
            },
            hide: function() {
                _getModalBuildingDetail()
                        .find('#use-min-water-blog')
                        .hide();
            }
        },
        save: function() {
            var form_ = _getBuildingForm();
            var submitButton = form_.find('[type="submit"]');
            var _validate = function() {
                var validatePass = true;
                
                form_.find('.' + REQUIRED_CLASS).each(function() {
                    var thisElement = jQuery(this);
                    
                    if(app.valueUtils.isEmptyValue(thisElement.val())) {
                        validatePass = false;
                        
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
                    url: _CONTEXT_PATH_ + '/building_save.html',
                    data: form_.serialize(),
                    cache: false,
                    success: function(response) {
                        response = app.convertToJsonObject(response);
                        
                        if(response.result === SUCCESS_STRING) {
                            app.showNotice({
                                message: response.message,
                                type: response.result
                            });
                            
                            if(response.id != undefined) {
                                form_.find('[name="id"]').val(response.id);
                            }
                            
                            page.getBuilding();
                        }
                        else {
                            if(response.message == SESSION_EXPIRE_STRING) {
                                app.alertSessionExpired();
                            }
                        }
                        
                        submitButton.bootstrapBtn('reset');
                    },
                    error: function() {
                        submitButton.bootstrapBtn('reset');
                    }
                });
            }, _DELAY_PROCESS_);
        }
    };
})();