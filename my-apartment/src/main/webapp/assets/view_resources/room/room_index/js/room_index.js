/* global buildingIdString, app */

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
            
            jQuery('body').dblclick(function() {
                modalRoomDetail.getModal().modal('show');
                modalRoomDetail.getForm();
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
            
            if(!app.valueUtils.isEmptyValue(buildingId)) {
                alert('To get room');
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