/* global buildingIdString, app, INPUT_ERROR_CLASS, WARNING_STRING, _CONTEXT_PATH_, _DELAY_PROCESS_ */

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
                page.getRoom();
            });
            
            page.getElement.getAddButton().click(function() {
                page.showRoomDetail();
            });
        },
        setBuildingList: function() {
            if(!app.valueUtils.isEmptyValue(buildingIdString)) {
                var buildingList = _getBuildingListElement();
                
                buildingList.val(buildingIdString);
                
                page.getRoom();
            }
        },
        getRoom: function() {
            var buildingList = _getBuildingListElement();
            var buildingId = buildingList.val();
            var contentBox = page.getElement.getContentBox();
            
            if(!app.valueUtils.isEmptyValue(buildingId)) {
                app.loadingInElement('show', contentBox);
                
                setTimeout(function() {
                    jQuery.ajax({
                        type: 'get',
                        url: _CONTEXT_PATH_ + '/room_get_by_building_id.html',
                        data: {
                            building_id: buildingId
                        },
                        cache: false,
                        success: function(response) {
                            response = app.convertToJsonObject(response);

                            app.loadingInElement('remove', contentBox);
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

                    modalRoomDetail.getModal().modal('show');
                }
                else {

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
        }
    };
})();