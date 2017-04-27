/* global INPUT_ERROR_CLASS, app, WARNING_STRING, modalRoomDetail, _DELAY_PROCESS_, _CONTEXT_PATH_, EDIT_ANIMATED_CLASS, alertUtil, buildingIdString, HAS_ANY_DATA_STRING, SUCCESS_STRING, modalRoomManage, roomManageCheckIn */

var latestRoomIdProcess = null;

jQuery(document).ready(function() {
    page.initialProcess();
    page.addEvent();
});

var page = (function() {
    var _getBuildingListElement = function() {
        return jQuery('#building-list');
    };
    
    var _deleteRoom = function(buttonDelete) {
        var id = buttonDelete.attr('data-id');
      
        buttonDelete.bootstrapBtn('loading');
        
        setTimeout(function() {
            jQuery.ajax({
                type: 'post',
                url: _CONTEXT_PATH_ + '/room_delete_by_id.html',
                data: {
                    id: id
                },
                cache: false,
                success: function(response) {
                    response = app.convertToJsonObject(response);
                    
                    if(response.result == SUCCESS_STRING) {
                        app.showNotice({
                            message: app.translate('common.delete_success'),
                            type: response.result
                        });
                    }
                    else {
                        if(response.message == HAS_ANY_DATA_STRING) {
                            app.showNotice({
                                message: app.translate('room.cannot_delete_room_has_any_data'),
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
                    
                    buttonDelete.bootstrapBtn('reset');

                    page.getRoom();
                },
                error: function() {
                    app.alertSomethingError();

                    buttonDelete.bootstrapBtn('reset');
                }
            });
        }, _DELAY_PROCESS_);
    };
    
    return {
        initialProcess: function() {
            page.setBuildingList();
            
            jQuery('.input-datepicker').datepicker({
                format:'yyyy-mm-dd',
                autoclose: true,
                weekStart: 0,
                language: localeForDatepicker()
            }).on('changeDate',function(e) {
                
            });
            
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
                latestRoomIdProcess = null;
                page.getRoom();
            });
            
            page.getElement.getBoxRoomContainer().on('click', '.box-room', function() {
                var thisElement = jQuery(this);
                
                page.showRoomDetail('edit', thisElement.attr('data-id'));
            });
            
            page.getElement.getBoxRoomContainer().on('click', '.button-room-manage', function() {
                page.showRoomManage(jQuery(this));
            });
            
            page.getElement.getBoxRoomContainer().on('click', '.button-delete', function() {
                var thisElement = jQuery(this);
                
                page.deleteRoom(thisElement);
            });
            
            modalRoomDetail.getForm().submit(function(e) {
                e.preventDefault();
                
                modalRoomDetail.save();
            });

            modalRoomManage.getModal().on('shown.bs.modal', function(e) {
                page.getElement.getModalRoomManageTabs().find('a:first').tab('show');
            });
            
            
            /* begin room reservation tab */
            page.getElement.getModalRoomManage().on('click', '#new-room-reservation', function() {
                var thisElement = jQuery(this);
                
                thisElement.hide();
                
                modalRoomManage.getRoomReservationForm().show();
            });
            
            modalRoomManage.getRoomReservationForm().submit(function(e) {
                e.preventDefault();
                
                modalRoomManage.saveRoomReservation();
            });
            /* end room reservation tab */
            
            
            /* begin current check in tab */
            page.getElement.getModalRoomManage().on('click', '#new-current-check-in', function() {
                var thisElement = jQuery(this);
                
                thisElement.hide();
                
                modalRoomManage.getCurrentCheckInForm().show();
                
                roomManageCheckIn.checkOutRoomButton.hide();
            });
            
            modalRoomManage.getCurrentCheckInForm().submit(function(e) {
                e.preventDefault();
                
                modalRoomManage.saveCurrentCheckIn();
            });
            
            page.getElement.getCheckOutRoomButton().click(function() {
                roomManageCheckIn.checkOut();
            });
            /* end current check in tab */


            /* begin notice check out tab */
            page.getElement.getModalRoomManage().on('click', '#new-notice-check-out', function() {
                var thisElement = jQuery(this);
                
                thisElement.hide();
                
                modalRoomManage.getNoticeCheckOutForm().show();
            });
            
            modalRoomManage.getNoticeCheckOutForm().submit(function(e) {
                e.preventDefault();
                
                modalRoomManage.saveNoticeCheckOut();
            });
            /* end notice check out tab */
        },
        getCurrentDateString: function() {
            return jQuery('#current-date-string').val();
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
        setModalRoomNoLabel: function(roomNoLabel) {
            page.getElement.getModalRoomNoLabel().html(roomNoLabel);
        },
        showRoomManage: function(buttonRoomManage) {
            var roomId = buttonRoomManage.attr('data-id');
            
            modalRoomManage.getRoomManage(roomId, buttonRoomManage);
        },
        deleteRoom: function(buttonDelete) {
            alertUtil.confirmAlert(app.translate('common.please_confirm_to_process'), function() {
                _deleteRoom(buttonDelete);
            }, function() {
                
            },{
                animation: false,
                type: null
            });
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
                            
                            boxRoomElement_.attr('data-room-no', currentData.roomNo);
                            
                            boxRoomElement.attr('data-id', currentData.id);
                             
                            boxRoomElement.find('.box-room-name').html(
                                app.translate('building.room')
                                + ' : '
                                + currentData.roomNo  
                            );

                            boxRoomElement.find('.label-room-status')
                                    .addClass(__getRoomStatusColorClass(currentData.roomStatusId))
                                    .html(app.translate(currentData.roomStatusText));
                            
                            boxRoomElement_.find('.button-room-manage').attr('data-id', currentData.id);
                            
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
                        
                        var latestFloorSeq = 0;
                        var htmlFloorSeq = '';
                        var htmlFloorSeqTemplate = function(floorSeq) {
                            return '<div style="clear: both;"></div>'
                                    + '<div class="floor-separate"> ' 
                                        + app.translate('room.floor') 
                                        + ' : ' + floorSeq +
                                    '</div>';
                        };
                        for(var index in roomData) {
                            var currentData = roomData[index];

                            if(latestFloorSeq != currentData.floorSeq) {
                                htmlFloorSeq = htmlFloorSeqTemplate(currentData.floorSeq);
                                latestFloorSeq = currentData.floorSeq;
                            }
                            else {
                                htmlFloorSeq = '';
                            }

                            html = htmlFloorSeq + __getTemplate();

                            boxRoomContainer.append(html);
                            var currentBoxRoom = boxRoomContainer.find('.box-room').last();
                            
                            __setData(currentBoxRoom, currentData);
                        }
                        
                        boxRoomContainer.append('<div style="clear: both;"></div>');
                        
                        if(roomData.length == 0) {
                            page.boxRoomContainer.noDataFound();
                        }

                        app.loadingInElement('remove', contentBox);
                        
                        __setAnimateBoxRoom();
                        
                        page.getRoomManageDetailList();
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
                            
                            if(response.message == SESSION_EXPIRE_STRING) {
                                app.alertSessionExpired();
                                app.loadingInElement('remove', contentBox);
                            }
                            else {
                                _renderBoxRoom(response);
                            }
                        },
                        error: function() {
                            app.loadingInElement('remove', contentBox);

                            app.alertSomethingError();
                        }
                    });
                }, _DELAY_PROCESS_);
            }
        },
        getRoomManageDetailList: function() {
            var _setReserveLabel = function(response) {
                var reserveData = response.data.reserve;
                var labelTemplate = '<span class="label label-warning">' + app.translate('room.reserve') + '</span>';
                
                for(var index in reserveData) {
                    var currentData = reserveData[index];
                    var boxRoom = jQuery('.box-room[data-id="' + currentData.roomId + '"]');
                    
                    boxRoom.find('.room-manage-detail-label').html(labelTemplate);
                }
            };
            
            var _setCurrentCheckInLabel = function(response) {
                var currentCheckInData = response.data.currentCheckIn;
                var labelTemplate = '<span class="label label-info">' + app.translate('room.check_in') + '</span>';

                for(var index in currentCheckInData) {
                    var currentData =  currentCheckInData[index];
                    var boxRoom = jQuery('.box-room[data-id="' + currentData.roomId + '"]');
                    var currentLabelHtml =  boxRoom.find('.room-manage-detail-label').html();
                    
                    boxRoom.find('.room-manage-detail-label').html(currentLabelHtml + labelTemplate);
                }
            };
            
            var _setCurrentNoticeCheckOut = function(response) {
                var currentNoticeCheckOutData = response.data.noticeCheckOut;
                var labelTemplate = '<span class="label label-danger">' + app.translate('room.notice_check_out') + '</span>';
                
                for(var index in currentNoticeCheckOutData) {
                    var currentData =  currentNoticeCheckOutData[index];
                    var boxRoom = jQuery('.box-room[data-id="' + currentData.roomId + '"]');
                    var currentLabelHtml =  boxRoom.find('.room-manage-detail-label').html();
                    
                    boxRoom.find('.room-manage-detail-label').html(currentLabelHtml + labelTemplate);
                }
            };
            
            var _closeWithClearFix = function() {
                jQuery('.box-room_ .box-room').each(function() {
                    var thisElement = jQuery(this);
                    
                    thisElement.find('.room-manage-detail-label').append('<div class="clearfix"></div>');
                });
            };
            
            jQuery.ajax({
                type: 'post',
                url: _CONTEXT_PATH_ + '/room/get_room_manage_detail_list.html',
                cache: false,
                success: function(response) {
                    response = app.convertToJsonObject(response);
                    
                    _setReserveLabel(response);
                    _setCurrentCheckInLabel(response);
                    _setCurrentNoticeCheckOut(response);
                    _closeWithClearFix();
                },
                error: function() {
                    app.showNotice({
                        type: WARNING_STRING,
                        message: app.translate('room.cannot_get_room_manage_detail_list'),
                        addclass: 'notice-get-room-manage-detail'
                    });
                }
                
            });
        },
        getElement: {
            getCheckOutRoomButton: function() {
                return jQuery('#check-out-current-check-in');
            },
            getNewNoticeCheckOutButton: function() {
                return jQuery('#new-notice-check-out');
            },
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
            },
            getModalRoomNo: function() {
                return modalRoomDetail.getForm().find('[name="room_no"]');
            },
            getModalRoomManage: function() {
                return modalRoomManage.getModal();
            },
            getModalRoomNoLabel: function() {
                return jQuery('.modal-room-no-label');
            },
            getModalRoomManageTabs: function() {
                return page.getElement.getModalRoomManage().find('#tabs');
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
                    app.clearAllInputErrorClass(modalRoomDetail.getForm());
                    modalRoomDetail.clearInputId();
                    modalRoomDetail.setBuildingId();
                    
                    modalRoomDetail.getModal().modal('show');
                    
                    app.modalUtils.bodyScrollTop(modalRoomDetail.getModal());
                    
                    modalRoomDetail.setFocusAfterOpenModal();
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

