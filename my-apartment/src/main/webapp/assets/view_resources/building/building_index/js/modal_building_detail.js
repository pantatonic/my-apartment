/* global _DELAY_PROCESS_, _CONTEXT_PATH_, app, SUCCESS_STRING, alertUtil, SESSION_EXPIRE_STRING, REQUIRED_CLASS, INPUT_ERROR_CLASS, WARNING_STRING, page, DATA_NOT_FOUND_STRING */

var modalBuildingDetail = (function() {
    var _getModalBuildingDetail = function() {
        return jQuery('#modal-building-detail');
    };
    
    var _getBuildingForm = function() {
        return jQuery('form[name="building_form"]');
    };
    
    var _save = function() {
        var modal = modalBuildingDetail.getModal();
        var form_ = _getBuildingForm();
        var submitButton = form_.find('[type="submit"]');
        var _validate = function() {
            var validatePass = true;
            var __validateMinelectricityWater = function() {
                var _validatePass = true;
                var _useMinElectricity = modal.find('#use-min-electricity');
                var _minElectricityUnit = modal.find('[name="min_electricity_unit"]');
                var _minElectricityCharge = modal.find('[name="min_electricity_charge"]');

                var _useMinWater = modal.find('#use-min-water');
                var _minWaterUnit  = modal.find('[name="min_water_unit"]');
                var _minWaterCharge = modal.find('[name="min_water_charge"]');

                if(_useMinElectricity.is(':checked')) {
                    if(app.valueUtils.isEmptyValue(_minElectricityUnit.val())) {
                        _minElectricityUnit.addClass(INPUT_ERROR_CLASS);
                        _validatePass = false;
                    }
                    else {
                        _minElectricityUnit.removeClass(INPUT_ERROR_CLASS);
                    }

                    if(app.valueUtils.isEmptyValue(_minElectricityCharge.val())) {
                        _minElectricityCharge.addClass(INPUT_ERROR_CLASS);
                        _validatePass = false;
                    }
                    else {
                         _minElectricityCharge.removeClass(INPUT_ERROR_CLASS);
                    }
                }

                if(_useMinWater.is(':checked')) {
                    if(app.valueUtils.isEmptyValue(_minWaterUnit.val())) {
                        _minWaterUnit.addClass(INPUT_ERROR_CLASS);
                        _validatePass = false;
                    }
                    else {
                        _minWaterUnit.removeClass(INPUT_ERROR_CLASS);
                    }

                    if(app.valueUtils.isEmptyValue(_minWaterCharge.val())) {
                        _minWaterCharge.addClass(INPUT_ERROR_CLASS);
                        _validatePass = false;
                    }
                    else {
                        _minWaterCharge.removeClass(INPUT_ERROR_CLASS);
                    }
                }

                if(!_validatePass) {
                    if(!app.checkNoticeExist('notice-enter-data')) {
                        app.showNotice({
                            type: WARNING_STRING,
                            message: app.translate('common.please_enter_data'),
                            addclass: 'notice-enter-data'
                        });
                    }
                }

                return _validatePass;
            };

            var __validateMeterDigit = function() {
                var _validatePass = true;
                var _electricityMeterDigit = modal.find('[name="electricity_meter_digit"]');
                var _waterMeterDigit = modal.find('[name="water_meter_digit"]');

                if(_electricityMeterDigit.val() == '0') {
                    _electricityMeterDigit.addClass(INPUT_ERROR_CLASS);
                    _validatePass = false;
                }

                if(_waterMeterDigit.val() == '0') {
                    _waterMeterDigit.addClass(INPUT_ERROR_CLASS);
                    _validatePass = false;
                }

                if(!_validatePass) {
                    if(!app.checkNoticeExist('notice-meter-digit-data')) {
                        app.showNotice({
                            type: WARNING_STRING,
                            message: app.translate('building.meter_digit_must_more_than_zero'),
                            addclass: 'notice-meter-digit-data'
                        });
                    }
                }

                return _validatePass;
            };

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

            if(!__validateMinelectricityWater() || !__validateMeterDigit()) {
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
                var modalBody = modal.find('.modal-body');
                var __checkedUseElectricityWater = function() {
                    var minElectricityUnit = modalBody.find('[name="min_electricity_unit"]');
                    var minElectricityCharge = modalBody.find('[name="min_electricity_charge"]');
                    
                    var minWaterUnit = modalBody.find('[name="min_water_unit"]');
                    var minWaterCharge = modalBody.find('[name="min_water_charge"]');
                    
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
                                
                app.clearAllInputErrorClass(modalBody);
                
                app.clearFormData(modalBuildingDetail.getForm());
                modalBuildingDetail.clearInputId();
                
                for(var key in data) {
                    var key_ = app.camelToUnderScore(key);
                    modalBody.find('[name="' + key_ + '"]').val(data[key]);

                    app.triggerCtrl(modalBody.find('[name="' + key_ + '"]'));
                }
                
                __checkedUseElectricityWater();
                
                modal.modal('show');
                
                app.modalUtils.bodyScrollTop(modal);
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
            },
            clearInputValue: function() {
                var modal = _getModalBuildingDetail();
                
                modal.find('[name="min_electricity_unit"]').val('');
                modal.find('[name="min_electricity_charge"]').val('');
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
            },
            clearInputValue: function() {
                var modal = _getModalBuildingDetail();
                
                modal.find('[name="min_water_unit"]').val('');
                modal.find('[name="min_water_charge"]').val('');
            }
        },
        save: function() {
            var form_ = modalBuildingDetail.getForm();
            var idInput = form_.find('[name="id"]');
            
            if(!app.valueUtils.isEmptyValue(idInput.val())) {
                alertUtil.confirmAlert(app.translate('common.confirm_change_important_data'), function() {
                    _save();
                }, function() {

                },{
                    animation: false,
                    type: null
                });
            }
            else {
                _save();
            }
        }
    };
})();