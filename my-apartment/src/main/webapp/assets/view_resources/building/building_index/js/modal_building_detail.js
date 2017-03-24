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
        getBuildingDetail: function(buildingId) {
            var _setData = function(response) {
                var data = response.data[0];
                var modal = _getModalBuildingDetail();
                var modal_body = modal.find('.modal-body');
                var __checkedUseElectricityWater = function() {
                    var minElectricityUnit = modal_body.find('[name="min_electricity_unit"]');
                    var minElectricityCharge = modal_body.find('[name="min_electricity_charge"]');
                    
                    var minWaterUnit = modal_body.find('[name="min_water_unit"]');
                    var minWaterCharge = modal_body.find('[name="min_water_charge"]');
                    
                    if(!app.valueUtils.isEmptyValue( minElectricityUnit.val() ) 
                            || !app.valueUtils.isEmptyValue( minElectricityCharge.val() )
                    ) {
                        jQuery('#use-min-electricity').prop('checked', true);
                    }
                    
                    if(!app.valueUtils.isEmptyValue( minWaterUnit.val() ) 
                            || !app.valueUtils.isEmptyValue( minWaterCharge.val() )
                    ) {
                        jQuery('#use-min-water').prop('checked', true);
                    }
                    
                    modalBuildingDetail.checkUseElectricityWater();
                };
                
                app.clearAllInputErrorClass(modal_body);
                
                app.clearFormData(modalBuildingDetail.getForm());
                modalBuildingDetail.clearInputId();
                
                for(var key in data) {
                    var key_ = app.camelToUnderScore(key);
                    modal_body.find('[name="' + key_ + '"]').val(data[key]);
                }
                
                __checkedUseElectricityWater();
                
                modal.modal('show');
            };
            
            app.loading('show');
            
            setTimeout(function() {
                jQuery.ajax({
                    type: 'get',
                    url: _CONTEXT_PATH_ + '/building_get_by_id.html',
                    data: {
                        id: buildingId
                    },
                    cache: false,
                    success: function(response) {
                        response = app.convertToJsonObject(response);
                        
                        app.loading('remove');
                        
                        if(response.result == SUCCESS_STRING) {
                            _setData(response);
                        }
                       
                    },
                    error: function() {
                        app.alertSomethingError();
                        
                        app.loading('remove');
                    }
                });
            }, 500);
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
            var modal = modalBuildingDetail.getModal();
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
                                message: app.translate('common.save_success'),
                                type: response.result
                            });
                            
                            if(response.id != undefined) {
                                form_.find('[name="id"]').val(response.id);
                                latestBuildingIdProcess = response.id;
                            }

                            modal.modal('hide');
                            page.getBuilding();
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