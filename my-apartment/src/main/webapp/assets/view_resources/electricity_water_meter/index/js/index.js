/* global app, _DELAY_PROCESS_, SESSION_EXPIRE_STRING, _CONTEXT_PATH_ */

jQuery(document).ready(function() {
    page.initialProcess();
    page.addEvent();
});

var page = (function() {
    
    return {
        initialProcess: function() {
            jQuery('#electriccity-water-month-year').datepicker({
                format:'yyyy-mm',
                minViewMode: 'months',
                autoclose: true
            }).on('changeDate',function(e) {
                page.getRoom();
                /*setTimeout(function() {
                    render_chart_3();
                },500);*/
            });
        },
        addEvent: function() {
            page.getElement.getBuildingList().change(function() {
                var thisElement = jQuery(this);
                
                if(app.valueUtils.isEmptyValue(thisElement.val())) {
                    page.boxRoomContainer.empty();
                }
                else {
                    page.getRoom();
                }
            });
        },
        getElement: {
            getBuildingList: function() {
                return jQuery('#building-list');
            },
            getBoxRoomContainer: function() {
                return jQuery('#box-room-container');
            },
            getBoxRoom_: function() {
                return jQuery('.box-room_');
            },
            getElectricityWaterMonthYear: function() {
                return jQuery('#electriccity-water-month-year');
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
        electTricityWaterMonthYear: {
            getMonth: function() {
                var monthYear = page.getElement.getElectricityWaterMonthYear().val();
                var splitText = monthYear.split('-');
                
                return splitText[1];
            },
            getYear: function() {
                var monthYear = page.getElement.getElectricityWaterMonthYear().val();
                var splitText = monthYear.split('-');
                
                return splitText[0];
            }
        },
        getRoom: function() {
            var buildingList = page.getElement.getBuildingList();
            var buildingId = buildingList.val();
            var contentBox = buildingList.closest('.box-primary');
            
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
                            var boxRoomElement_ = boxRoomElement.closest('.box-room_');
                            
                            boxRoomElement.find('[name="id"]').val(currentData.id);
                            
                            boxRoomElement.find('.box-room-name').html(
                                '<i class="fa fa-bed"></i> '
                                + app.translate('building.room')
                                + ' : '
                                + currentData.roomNo
                            );
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

                        app.loadingInElement('remove', contentBox);
                        
                        page.getElectricWaterMeter();
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
        getElectricWaterMeter: function() {
            var contentBox = page.getElement.getBuildingList().closest('.box-primary');
            var buildingList = page.getElement.getBuildingList();
            var boxRoom_ = page.getElement.getBoxRoom_();
            
            var _setData = function(response) {
                var dataElectricity = response.data.electricityMeter;
                var dataWater = response.data.waterMeter;

                /** set electricity meter data */
                for(var index in dataElectricity) {
                    var dataElectricityEachRoom = dataElectricity[index];
                    
                    if(Object.keys(dataElectricityEachRoom).length > 0) {
                        var currentBoxRoom_ = boxRoom_.find('.box-room').find('input[name="id"][value="' + dataElectricityEachRoom.roomId + '"]').closest('.box-room_');
                        var previousElectricityMeter = app.valueUtils.undefinedToEmpty(dataElectricityEachRoom.previousMeter);
                        var presentElectricityMeter = app.valueUtils.undefinedToEmpty(dataElectricityEachRoom.presentMeter);
                        
                        currentBoxRoom_.find('.previous-electric').html(previousElectricityMeter);
                        currentBoxRoom_.find('[name="present_electric_meter"]').val(presentElectricityMeter);
                    }
                }
                
                /** set water meter data */
                for(var indexWater in dataWater) {
                    var dataWaterEachRoom = dataWater[indexWater];
                    
                    if(Object.keys(dataWaterEachRoom).length > 0) {
                        var currentBoxRoom_ = boxRoom_.find('.box-room').find('input[name="id"][value="' + dataWaterEachRoom.roomId + '"]').closest('.box-room_');
                        var previousWaterMeter = app.valueUtils.undefinedToEmpty(dataWaterEachRoom.previousMeter);
                        var presentWaterMeter = app.valueUtils.undefinedToEmpty(dataWaterEachRoom.presentMeter);
                        
                        currentBoxRoom_.find('.previous-water').html(previousWaterMeter);
                        currentBoxRoom_.find('[name="present_water_meter"]').val(presentWaterMeter);
                    }
                }
                
            };
            
            if(!app.valueUtils.isEmptyValue(buildingList.val())) {
                jQuery.ajax({
                    type: 'get',
                    url: _CONTEXT_PATH_ + '/get_room_electric_water_meter_by_building_id.html',
                    data: {
                        building_id: buildingList.val(),
                        year: page.electTricityWaterMonthYear.getYear(),
                        month: page.electTricityWaterMonthYear.getMonth()
                    },
                    cache: false,
                    success: function(response) {
                        response = app.convertToJsonObject(response);
                        
                        if(response.message == SESSION_EXPIRE_STRING) {
                            app.alertSessionExpired();
                            app.loadingInElement('remove', contentBox);
                        }
                        else {
                            _setData(response);
                        }
                    },
                    error: function() {
                        app.alertSomethingError();
                        app.loadingInElement('remove', contentBox);
                    }
                });
            }
        }
    };
})();