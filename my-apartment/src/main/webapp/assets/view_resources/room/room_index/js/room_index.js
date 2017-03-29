/* global buildingIdString, app, INPUT_ERROR_CLASS, WARNING_STRING, _CONTEXT_PATH_, _DELAY_PROCESS_, SESSION_EXPIRE_STRING, SUCCESS_STRING, DATA_NOT_FOUND_STRING, REQUIRED_CLASS, EDIT_ANIMATED_CLASS */

var latestRoomIdProcess = null;

jQuery(document).ready(function() {
    page.initialProcess();
    page.addEvent();
});

var page = (function() {
    var _getBuildingListElement = function() {
        return jQuery('#building-list');
    };
    
    return {
        initialProcess: function() {
            page.setBuildingList();
        },
        addEvent: function() {
            var buildingList = _getBuildingListElement();
            
            buildingList.change(function() {
                if(jQuery(this).val() == '') {
                    page.boxRoomContainer.empty();
                }
                else {
                    page.getRoom();
                }
            });
            
            page.getElement.getAddButton().click(function() {
                page.showRoomDetail();
            });
            
            jQuery('.refresh-room-list').click(function() {
                page.getRoom();
            });
            
            page.getElement.getBoxRoomContainer().on('click', '.box-room', function() {
                var thisElement = jQuery(this);
                
                page.showRoomDetail('edit', thisElement.attr('data-id'));
            });
            
            page.getElement.getBoxRoomContainer().on('click', '.button-delete', function() {
                var thisElement = jQuery(this);
                
                alert('to delete room');
            });
            
            modalRoomDetail.getForm().submit(function(e) {
                e.preventDefault();
                
                modalRoomDetail.save();
            });
        },
        setBuildingList: function() {
            if(!app.valueUtils.isEmptyValue(buildingIdString)) {
                var buildingList = _getBuildingListElement();
                
                buildingList.val(buildingIdString);
                
                page.getRoom();
            }
            else {
                page.boxRoomContainer.empty();
            }
        },
        boxRoomContainer: {
            noDataFound: function() {
                var html = '<div id="box-room-container-no-data">'
                    + '-- ' +app.translate('common.data_not_found') + ' --'
                    + '</div>';

                page.getElement.getBoxRoomContainer().html(html);
            },
            empty: function() {
                var html = '<div id="box-room-container-select-data">'
                    + '-- ' +app.translate('common.please_select_data') + ' --'
                    + '</div>';
            
                page.getElement.getBoxRoomContainer().html(html);
            }
        },
        getRoom: function() {
            var buildingList = _getBuildingListElement();
            var buildingId = buildingList.val();
            var contentBox = page.getElement.getContentBox();
            
            if(!app.valueUtils.isEmptyValue(buildingId)) {
                app.loadingInElement('show', contentBox);
                
                setTimeout(function() {
                    var _renderBoxRoom = function(response) {
                        var roomData = response.data;
                        var html = '';
                        var __getTemplate = function() {
                            return jQuery('#box-room-template').val();
                        };
                        var boxRoomContainer = page.getElement.getBoxRoomContainer();
                        var __setData = function(boxRoomElement, currentData) {
                            var __getRoomStatusColorClass = function(roomStatusId) {
                                var color = {
                                    '1': 'label-success',
                                    '2': 'label-danger',
                                    '3': 'label-warning'
                                };

                                return color[roomStatusId];
                            };
                            var boxRoomElement_ = boxRoomElement.closest('.box-room_');
                             
                            boxRoomElement.attr('data-id', currentData.id);
                             
                            boxRoomElement.find('.box-room-name').html(
                                app.translate('building.room')
                                + ' : '
                                + currentData.roomNo  
                            );

                            boxRoomElement.find('.label-room-status')
                                    .addClass(__getRoomStatusColorClass(currentData.roomStatusId))
                                    .html(app.translate(currentData.roomStatusText));
                    
                            boxRoomElement_.find('.button-delete').attr('data-id', currentData.id);
                            
                            //boxRoomElement_.find('.button-room').attr('data-id', currentData.id);
                        };
                        var __setAnimateBoxRoom = function() {
                            if(latestRoomIdProcess != null) {
                                var boxRoomToAnimate = jQuery('.box-room').filter(function() {
                                    if(latestRoomIdProcess.toString() == jQuery(this).attr('data-id')) {
                                        return true;
                                    }
                                    else {
                                        return false;
                                    }
                                });

                                app.setAnimateCustom(boxRoomToAnimate, EDIT_ANIMATED_CLASS);
                            }
                        };
                        
                        boxRoomContainer.html(html);
                        
                        for(var index in roomData) {
                            var currentData = roomData[index];
                            html = __getTemplate();

                            boxRoomContainer.append(html);
                            var currentBoxRoom = boxRoomContainer.find('.box-room').last();
                            
                            __setData(currentBoxRoom, currentData);
                        }
                        
                        if(roomData.length == 0) {
                            page.boxRoomContainer.noDataFound();
                        }

                        app.loadingInElement('remove', contentBox);
                        
                        __setAnimateBoxRoom();
                    };
                    
                    jQuery.ajax({
                        type: 'get',
                        url: _CONTEXT_PATH_ + '/room_get_by_building_id.html',
                        data: {
                            building_id: buildingId
                        },
                        cache: false,
                        success: function(response) {
                            response = app.convertToJsonObject(response);

                            _renderBoxRoom(response);
                        },
                        error: function() {
                            app.loadingInElement('remove', contentBox);

                            app.alertSomethingError();
                        }
                    });
                }, _DELAY_PROCESS_);
            }
        },
        getElement: {
            getAddButton: function() {
                return jQuery('.add-button');
            },
            getBuildingListElement: function() {
                return _getBuildingListElement();
            },
            getContentBox: function() {
                return jQuery('.box-primary');
            },
            getBoxRoomContainer: function() {
                return jQuery('#box-room-container');;
            }
        },
        showRoomDetail: function(type, roomId) {
            var buildingList = page.getElement.getBuildingListElement();
            
            if(type == undefined) {
                type = 'add';
            }
            
            buildingList.removeClass(INPUT_ERROR_CLASS);
            
            if(!app.valueUtils.isEmptyValue(buildingList.val())) {
                if(type == 'add') {
                    app.clearFormData(modalRoomDetail.getForm());
                    modalRoomDetail.clearInputId();
                    modalRoomDetail.setBuildingId();
                    
                    modalRoomDetail.getModal().modal('show');
                    
                    app.modalUtils.bodyScrollTop(modalRoomDetail.getModal());
                }
                else {
                    modalRoomDetail.getRoomDetail(roomId);
                }
            }
            else {
                if(!app.checkNoticeExist('notice-select-data')) {
                    app.showNotice({
                        type: WARNING_STRING,
                        message: app.translate('common.please_enter_data'),
                        addclass: 'notice-select-data'
                    });
                }
                
                buildingList.addClass(INPUT_ERROR_CLASS);
            }            
        }
    };
})();

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
        getRoomDetail: function(roomId) {
            var _setData = function(response) {
                var data = response.data[0];
                var modal = _getModal();
                var modalBody = modal.find('.modal-body');
                
                //app.clearAllInputErrorClass(modalBody);
                //app.clearFormData(modalBuildingDetail.getForm());
                modalRoomDetail.clearInputId();
                
                for(var key in data) {
                    var key_ = app.camelToUnderScore(key);
                    modalBody.find('[name="' + key_ + '"]').val(data[key]);

                    app.triggerCtrl(modalBody.find('[name="' + key_ + '"]'));
                }
                
                modal.modal('show');
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