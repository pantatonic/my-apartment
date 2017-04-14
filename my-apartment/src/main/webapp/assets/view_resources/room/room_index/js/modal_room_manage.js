

/* global _CONTEXT_PATH_ */

var modalRoomManage = (function() {
    var _getModal = function() {
        return jQuery('#modal-room-manage');
    };
    
    var _getForm = function() {
        return jQuery('[name="room_reservation_form"]');
    };
    
    return {
        getModal: function() {
            return _getModal();
        },
        getForm: function() {
            return _getForm();
        },
        getRoomManage: function(roomId) {
            jQuery.ajax({
                type: 'get',
                url: _CONTEXT_PATH_ + '/room/get_room_manage.html',
                data: {
                    room_id: roomId
                },
                cache: false,
                success: function(response) {
                    console.log(response);
                }
            });
        }
        
    };
})();