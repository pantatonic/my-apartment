/* global _DELAY_PROCESS_, _CONTEXT_PATH_, app */

jQuery(document).ready(function () {
    facade.initialProcess();
    facade.addEvent();
});

var facade = (function() {
    
    return {
        initialProcess: function() {
            page.initialProcess.initDatePicker();
            page.initialProcess.initChart();
            page.initialProcess.initDataList();
        },
        addEvent: function() {
            page.addEvent.refreshChartData();
            page.addEvent.getInvoiceByBuildingMonthChart();
            page.addEvent.getReceiptByBuildingMonthChart();
            page.addEvent.getNoticeCheckOutByBuilding();
            page.addEvent.getRoomDataByBuilding();
            page.addEvent.infoChartDataList();
        }
    };
})();

var page = (function () {

    return {
        initialProcess: {
            initDatePicker: function() {
                jQuery('#invoice-by-building-chart-month-year').datepicker({
                    format:'yyyy-mm',
                    minViewMode: 'months',
                    autoclose: true
                }).on('changeDate',function(e) {
                    myCharts.invoiceByBuildingMonth();
                });
                
                jQuery('#receipt-by-building-chart-month-year').datepicker({
                    format:'yyyy-mm',
                    minViewMode: 'months',
                    autoclose: true
                }).on('changeDate',function(e) {
                    myCharts.receiptByBuildingMonth();
                });
            },
            initChart: function() {
                myCharts.roomByBuilding();
                myCharts.invoiceByBuildingMonth();
                myCharts.receiptByBuildingMonth();
            },
            initDataList: function() {
                dataList.getNoticeCheckOutByBuilding();
                dataList.getRoomDataByBuilding();
            }
        },
        addEvent: {
            refreshChartData: function() {
                jQuery('.refresh-chart-data').click(function() {
                    var targetOperation = jQuery(this).attr('target-operation');

                    eval(targetOperation)();
                });
            },
            getInvoiceByBuildingMonthChart: function() {                
                page.getElement.invoiceByBuildingMonthChart.buildingList().change(function() {
                    myCharts.invoiceByBuildingMonth();
                });
            },
            getReceiptByBuildingMonthChart: function() {
                page.getElement.receiptByBuildingMonthChart.buildingList().change(function() {
                    myCharts.receiptByBuildingMonth();
                });
            },
            getNoticeCheckOutByBuilding: function() {
                page.getElement.noticeCheckOutDataList.buildingList().change(function() {
                    dataList.getNoticeCheckOutByBuilding();
                });
            },
            getRoomDataByBuilding: function() {
                page.getElement.roomDataList.buildingList().change(function() {
                    dataList.getRoomDataByBuilding();
                });
            },
            infoChartDataList: function() {
                jQuery('.info-chart-data-list').click(function() {
                    var thisElement = jQuery(this);
                    
                    jQuery(thisElement.attr('target-modal')).modal('show');
                });
            }
        },
        getElement:{
            getRoomByBuildingChartContent: function() {
                return jQuery('#room-by-building-chart');
            },
            invoiceByBuildingMonthChart: {
                content: function() {
                    return jQuery('#invoice-by-building-month-chart');
                },
                chartBox: function() {
                    return jQuery('#invoice-by-building-month-chart-box');
                },
                buildingList: function() {
                    return page.getElement.invoiceByBuildingMonthChart.chartBox().find('.building-list');
                },
                monthYear: function() {
                    return page.getElement.invoiceByBuildingMonthChart.chartBox().find('#invoice-by-building-chart-month-year');
                },
                getMonth: function() {
                    var monthYear = page.getElement.invoiceByBuildingMonthChart.monthYear().val();
                    var splitText = monthYear.split('-');
                
                    return splitText[1];
                },
                getYear: function() {
                    var monthYear = page.getElement.invoiceByBuildingMonthChart.monthYear().val();
                    var splitText = monthYear.split('-');
                
                    return splitText[0];
                },
                getTableChartDetailList: function() {
                    return page.getElement.invoiceByBuildingMonthChart.chartBox().find('#invoice-by-building-month-chart-detail-list') ;
                }
            },
            receiptByBuildingMonthChart: {
                content: function() {
                    return jQuery('#receipt-by-building-month-chart');
                },
                chartBox: function() {
                    return jQuery('#receipt-by-building-month-chart-box');
                },
                buildingList: function() {
                    return page.getElement.receiptByBuildingMonthChart.chartBox().find('.building-list');
                },
                monthYear: function() {
                    return page.getElement.receiptByBuildingMonthChart.chartBox().find('#receipt-by-building-chart-month-year');
                },
                getMonth: function() {
                    var monthYear = page.getElement.receiptByBuildingMonthChart.monthYear().val();
                    var splitText = monthYear.split('-');
                
                    return splitText[1];
                },
                getYear: function() {
                    var monthYear = page.getElement.receiptByBuildingMonthChart.monthYear().val();
                    var splitText = monthYear.split('-');
                
                    return splitText[0];
                },
                getTableChartDetailList: function() {
                    return page.getElement.receiptByBuildingMonthChart.chartBox().find('#receipt-by-building-month-chart-detail-list') ;
                }
            },
            noticeCheckOutDataList: {
                content: function() {
                    return jQuery('#notice-check-out-list-table-content');
                },
                box: function() {
                    return jQuery('#notice-check-out-by-building-list-box');
                },
                buildingList: function() {
                    return page.getElement.noticeCheckOutDataList.box().find('.building-list');
                },
                tableList: function() {
                    return page.getElement.noticeCheckOutDataList.box().find('table#notice-check-out-list-table');
                }
            },
            roomDataList: {
                content: function() {
                    return jQuery('#room-data-list-table-content');
                },
                box: function() {
                    return jQuery('#room-data-by-building-list-box');
                },
                buildingList: function() {
                    return page.getElement.roomDataList.box().find('.building-list');
                },
                tableList: function() {
                    return page.getElement.roomDataList.box().find('table#room-data-list-table');
                }
            }
        }
    };
})();

var myCharts = (function () {
    var _loading = function(jQueryChartElement, type) {
        if(type == 'show') {
            app.loadingInElement('show',
            jQueryChartElement.parent().parent().parent());
        }
        else {
            app.loadingInElement('remove',
            jQueryChartElement.parent().parent().parent());
        }
    };

    return {
        roomByBuilding: function () {
            var _chartContent = page.getElement.getRoomByBuildingChartContent();
            var _renderChart = function(dataChart) {
                _chartContent.highcharts({
                    chart: {
                        type: 'column'
                    },
                    title: {
                        text: app.translate('dashboard.chart.room_by_building')
                    },
                    subtitle: {
                        text: app.translate('dashboard.chart.room_in_building')
                    },
                    xAxis: {
                        //categories: ['_Building 1_', '_Building 2_'],
                        categories: dataChart.categories,
                        title: {
                            text: app.translate('dashboard.chart.building_name')
                        }
                    },
                    yAxis: {
                        min: 0,
                        allowDecimals: false,
                        title: {
                            text: ' ' + app.translate('dashboard.chart.number_of_room'),
                            align: 'high'
                        },
                        labels: {
                            overflow: 'justify'
                        }
                    },
                    tooltip: {
                        valueSuffix: ' ' + app.translate('building.rooms')
                    },
                    plotOptions: {
                        column: {
                            dataLabels: {
                                enabled: true
                            }
                        },
                        series: {
                            pointWidth: 25,
                            pointRange: 0.3
                        }
                    },
                    legend: {
                        layout: 'vertical',
                        align: 'right',
                        verticalAlign: 'top',
                        x: -40,
                        y: 80,
                        floating: true,
                        borderWidth: 1,

                        shadow: false
                    },
                    credits: {
                        enabled: false
                    },
                    series: [{
                        showInLegend: true,
                        name: app.translate('dashboard.chart.number_of_room'),
                        //data: [107, 31]
                        data: dataChart.data[0]
                    },{
                        showInLegend: true,
                        name: app.translate('room.check_in'),
                        data: dataChart.data[1]
                    },{
                        showInLegend: true,
                        name: app.translate('room.not_check_in'),
                        data: dataChart.data[2]
                    }],
                    exporting: {
                        enabled: false
                    }
                });
            };
            
            
            _loading(_chartContent, 'show');
            
            setTimeout(function() {
                jQuery.ajax({
                    type: 'get',
                    url: _CONTEXT_PATH_ + '/get_room_by_building_chart.html',
                    cache: false,
                    success:function(response) {
                        response = app.convertToJsonObject(response);
                        
                        if(response.message == SESSION_EXPIRE_STRING) {
                                app.alertSessionExpired();
                        }
                        else {
                            dataChart = app.convertToJsonObject(response);

                            _renderChart(dataChart);
                        }
                        
                        _loading(_chartContent, 'remove');
                    },
                    error: function() {
                        _loading(_chartContent, 'remove');

                        app.alertSomethingError();
                    }
                });
            }, _DELAY_PROCESS_);
        },
        invoiceByBuildingMonth: function() {
            var _chartContent = page.getElement.invoiceByBuildingMonthChart.content();
            var _renderChart = function(response) {
                var counter = 0;
                var _countData = function(response) {
                    var data_ = response.data.chartData;
                    var countData_ = 0;
                    for(var i in data_) {
                        countData_ = countData_ + (data_[i][1]);
                    }
                    
                    return countData_;
                };
                var _setChartDetailList = function() {
                    var dataChartDetailList = response.data.chartDetailList;
                    var tableChartDetailList = page.getElement.invoiceByBuildingMonthChart.getTableChartDetailList();
                    var countCancelValue = 0;
                    var countUnpaidValue = 0;
                    var countPaidValue = 0;
                    var calculateTotal = function(dt) {
                        return dt.electricityValue + dt.waterValue + dt.roomPricePerMonth;
                    };
                    //var tdCancel = tableChartDetailList.find('tbody tr:nth-child(1) td:nth-child(2)');
                    var tdUnpaid = tableChartDetailList.find('tbody tr:nth-child(1) td:nth-child(2)');
                    var tdPaid = tableChartDetailList.find('tbody tr:nth-child(2) td:nth-child(2)');
                    var tdAllValue = tableChartDetailList.find('tbody tr:nth-child(3) td:nth-child(2)');
                    
                    //tdCancel.html('');
                    tdUnpaid.html('');
                    tdPaid.html('');
                    tdAllValue.html('');
                    
                    for(var index in dataChartDetailList) {
                        /*if(dataChartDetailList[index].status == 0) { //cancel
                            countCancelValue = countCancelValue + calculateTotal(dataChartDetailList[index]);
                        }
                        else*/
                        if(dataChartDetailList[index].status == 1) { //unpaid
                            countUnpaidValue = countUnpaidValue + calculateTotal(dataChartDetailList[index]);
                        }
                        else 
                        if(dataChartDetailList[index].status == 2) { //paid
                            countPaidValue = countPaidValue + calculateTotal(dataChartDetailList[index]);
                        }
                    }
                    
                    //tdCancel.html(app.valueUtils.numberFormat(countCancelValue));
                    tdUnpaid.html(app.valueUtils.numberFormat(countUnpaidValue));
                    tdPaid.html(app.valueUtils.numberFormat(countPaidValue));
                    tdAllValue.html(app.valueUtils.numberFormat(countUnpaidValue + countPaidValue));
                };
                var _setModalDataList = function() {
                    var modalDataList = jQuery('#modal-invoice-chart-data-list');
                    var dataChartDetailList = response.data.chartDetailList;
                    var modalTableDataList = modalDataList.find('.modal-body table');
                    var html_ = '';
                    
                    for(var index in dataChartDetailList) {
                        var currentData = dataChartDetailList[index];
                        var total = currentData.roomPricePerMonth + currentData.electricityValue + currentData.waterValue;
                        
                        html_ += '<tr>'
                            + '<td>' + currentData.invoiceNo + '</td>'
                            + '<td>' + currentData.invoiceDate + '</td>'
                            + '<td>' + currentData.roomNo + '</td>'
                            + '<td>' + app.valueUtils.numberFormat(total) + '</td>'
                            + '<td>' + app.system.getInvoiceStatusLabel(currentData.status) + '</td>'
                            + '</tr>';
                    }
                    
                    if(dataChartDetailList.length === 0 ) {
                        html_ = '<tr><td colspan="5" class="text-center">' + app.translate('common.data_not_found') + '</td></tr>';
                    }

                    modalTableDataList.find('tbody').html(html_);
                };

                
                
                /** process of _renderChart */

                _setChartDetailList();
                _setModalDataList();

                _chartContent.highcharts({
                    chart: {
                        type: 'pie',
                        style: {
                            fontFamily: '"Source Sans Pro",sans-serif;'
                        },
                        plotBackgroundColor: null,
                        plotBorderWidth: 0,
                        plotShadow: false,
                        spacingBottom: 10,
                        spacingTop: 10,
                        spacingLeft: 0,
                        spacingRight: 0,
                        height: 350
                    },
                    title: {
                        text: app.translate('dashboard.chart.invoice_by_building'),
                        align: 'center'
                    },
                    subtitle: {
                        text: app.translate('common.total') + ' : ' + _countData(response) + ' ' + app.translate('room.invoice'),
                        useHTML: true
                    },
                    tooltip: {
                        enabled: true,
                        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>',
                        useHTML: true                        
                    },
                    plotOptions: {
                        pie: {
                            animation: true,
                            allowPointSelect: true,
                            cursor: 'pointer',
                            dataLabels: {
                                enabled: _countData(response) > 0 ? true : false,
                                useHTML: false,
                                distance: 20,
                                formatter: function() {
                                    counter++;
                                    return '<div class="datalabel">'+
                                                this.y + ' ' + app.translate('room.invoice') +
                                                '<br>['+this.percentage.toFixed(2)+'%]'+
                                            '</div>';
                                }
                            },
                            showInLegend: true
                        }
                    },
                    series: [{
                        type: 'pie',
                        name: 'Invoice By Building',
                        innerSize: '0%',
                        /*data: [
                            ['Spending type 1',   510.38],
                            ['Spending type 2',       456.33],
                            ['Spending type 3', 1214.03],
                            ['Spending type 4',    400.77],
                            ['Spending type 5',     402.91],
                            ['Spending type 6',     240]
                        ]*/
                        data: response.data.chartData
                    }],
                    colors: ['#c1c1c1', '#7cb5ec', '#90ed7d'],
                    /* #f7a35c #8085e9 #f15c80 */
                    exporting: {
                        enabled: false
                    },
                    credits: {
                        enabled :false
                    }
                });
            };
            
            
            _loading(_chartContent, 'show');
            
            setTimeout(function() {
                var buildingList = page.getElement.invoiceByBuildingMonthChart.buildingList();
                var month = page.getElement.invoiceByBuildingMonthChart.getMonth();
                var year = page.getElement.invoiceByBuildingMonthChart.getYear();
                
                jQuery.ajax({
                    type: 'get',
                    url: _CONTEXT_PATH_ + '/get_invoice_by_building_month_chart.html',
                    data: {
                        building_id: buildingList.val(),
                        month: month,
                        year: year
                    },
                    cache: false,
                    success:function(response) {
                        response = app.convertToJsonObject(response);
                        
                        if(response.message == SESSION_EXPIRE_STRING) {
                                app.alertSessionExpired();
                        }
                        else {
                            _renderChart(response);
                        }
                        
                        _loading(_chartContent, 'remove');
                    },
                    error: function() {
                        _loading(_chartContent, 'remove');

                        app.alertSomethingError();
                    }
                });
            }, _DELAY_PROCESS_);
        },
        receiptByBuildingMonth: function() {
            var _chartContent = page.getElement.receiptByBuildingMonthChart.content();
            var _renderChart = function(response) {
                var counter = 0;
                var _countData = function(response) {
                    var data_ = response.data.chartData;
                    var countData_ = 0;
                    for(var i in data_) {
                        countData_ = countData_ + (data_[i][1]);
                    }
                    
                    return countData_;
                };
                _setChartDetailList = function() {
                    var dataChartDetailList = response.data.chartDetailList;
                    var tableChartDetailList = page.getElement.receiptByBuildingMonthChart.getTableChartDetailList();
                    var countCancelValue = 0;
                    var countReceiptValue = 0;
                    var calculateTotal = function(dt) {
                        return dt.electricityValue + dt.waterValue + dt.roomPricePerMonth;
                    };
                    //var tdCancel = tableChartDetailList.find('tbody tr:nth-child(1) td:nth-child(2)');
                    var tdReceipt = tableChartDetailList.find('tbody tr:nth-child(1) td:nth-child(2)');
                    
                    //tdCancel.html('');
                    tdReceipt.html('');
                    
                    for(var index in dataChartDetailList) {
                        /*if(dataChartDetailList[index].status == 0) { //cancel
                            countCancelValue = countCancelValue + calculateTotal(dataChartDetailList[index]);
                        }
                        else*/
                        if(dataChartDetailList[index].status == 1) { //receipt
                            countReceiptValue = countReceiptValue + calculateTotal(dataChartDetailList[index]);
                        }
                    }
                    
                    //tdCancel.html(app.valueUtils.numberFormat(countCancelValue));
                    tdReceipt.html(app.valueUtils.numberFormat(countReceiptValue));
                };
                var _setModalDataList = function() {
                    var modalDataList = jQuery('#modal-receipt-chart-data-list');
                    var dataChartDetailList = response.data.chartDetailList;
                    var modalTableDataList = modalDataList.find('.modal-body table');
                    var html_ = '';
                    
                    for(var index in dataChartDetailList) {
                        var currentData = dataChartDetailList[index];
                        var total = currentData.roomPricePerMonth + currentData.electricityValue + currentData.waterValue;

                        html_ += '<tr>'
                            + '<td>' + currentData.receiptNo + '</td>'
                            + '<td>' + currentData.createdDate + '</td>'
                            + '<td>' + app.valueUtils.numberFormat(total) + '</td>'
                            + '<td>' + app.system.getReceiptStatusLabel(currentData.status) + '</td>'
                            + '</tr>';
                    }
                    
                    if(dataChartDetailList.length === 0 ) {
                        html_ = '<tr><td colspan="5" class="text-center">' + app.translate('common.data_not_found') + '</td></tr>';
                    }

                    modalTableDataList.find('tbody').html(html_);
                };
                
                
                /** process of _renderChart */
                
                _setChartDetailList();
                _setModalDataList();

                _chartContent.highcharts({
                    chart: {
                        type: 'pie',
                        style: {
                            fontFamily: '"Source Sans Pro",sans-serif;'
                        },
                        plotBackgroundColor: null,
                        plotBorderWidth: 0,
                        plotShadow: false,
                        spacingBottom: 10,
                        spacingTop: 10,
                        spacingLeft: 0,
                        spacingRight: 0,
                        height: 350
                    },
                    title: {
                        text: app.translate('dashboard.chart.receipt_of_invoice_by_building'),
                        align: 'center'
                    },
                    subtitle: {
                        text: app.translate('common.total') + ' : ' + _countData(response) + ' ' + app.translate('room.room_receipt'),
                        useHTML: true
                    },
                    tooltip: {
                        enabled: true,
                        pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>',
                        useHTML: true                        
                    },
                    plotOptions: {
                        pie: {
                            animation: true,
                            allowPointSelect: true,
                            cursor: 'pointer',
                            dataLabels: {
                                enabled: _countData(response) > 0 ? true : false,
                                useHTML: false,
                                distance: 20,
                                formatter: function() {
                                    counter++;
                                    return '<div class="datalabel">'+
                                                this.y + ' ' + app.translate('room.room_receipt') +
                                                '<br>['+this.percentage.toFixed(2)+'%]'+
                                            '</div>';
                                }
                            },
                            showInLegend: true
                        }
                    },
                    series: [{
                        type: 'pie',
                        name: 'Invoice By Building',
                        innerSize: '0%',
                        /*data: [
                            ['Spending type 1',   510.38],
                            ['Spending type 2',       456.33],
                            ['Spending type 3', 1214.03],
                            ['Spending type 4',    400.77],
                            ['Spending type 5',     402.91],
                            ['Spending type 6',     240]
                        ]*/
                        data: response.data.chartData
                    }],
                    colors: ['#c1c1c1', '#f39927'],
                    /* #f7a35c #8085e9 #f15c80 */
                    exporting: {
                        enabled: false
                    },
                    credits: {
                        enabled :false
                    }
                });
            };
            
            
            _loading(_chartContent, 'show');
            
            setTimeout(function() {
                var buildingList = page.getElement.receiptByBuildingMonthChart.buildingList();
                var month = page.getElement.receiptByBuildingMonthChart.getMonth();
                var year = page.getElement.receiptByBuildingMonthChart.getYear();
                
                jQuery.ajax({
                    type: 'get',
                    url: _CONTEXT_PATH_ + '/get_receipt_by_building_month_chart.html',
                    data: {
                        building_id: buildingList.val(),
                        month: month,
                        year: year
                    },
                    cache: false,
                    success:function(response) {
                        response = app.convertToJsonObject(response);
                        
                        if(response.message == SESSION_EXPIRE_STRING) {
                                app.alertSessionExpired();
                        }
                        else {
                            _renderChart(response);
                        }
                        
                        _loading(_chartContent, 'remove');
                    },
                    error: function() {
                        _loading(_chartContent, 'remove');

                        app.alertSomethingError();
                    }
                });
            }, _DELAY_PROCESS_);
        }
    };
})();

var dataList = (function() {
    var _loading = function(contentElement, type) {
        if(type == 'show') {
            app.loadingInElement('show',
            contentElement.parent().parent().parent());
        }
        else {
            app.loadingInElement('remove',
            contentElement.parent().parent().parent());
        }
    };
    
    return {
        getNoticeCheckOutByBuilding: function() {
            var _tableList = page.getElement.noticeCheckOutDataList.tableList();
            var _renderDataList = function(dataList) {
                var data_ = dataList.data;
                var html_ = '';
                
                for(var index in data_) {
                    var currentData = data_[index];
                    html_ += '<tr>'
                        + '<td>' + app.valueUtils.undefinedToEmpty(currentData.noticeCheckOutDate) + '</td>'
                        + '<td>' + app.valueUtils.undefinedToEmpty(currentData.roomNo) + '</td>'
                        + '<td>' + app.valueUtils.undefinedToEmpty(currentData.buildingName) + '</td>'
                        + '<td>' + app.valueUtils.undefinedToEmpty(currentData.remark) + '</td>'
                        + '</tr>';                    
                }
                
                if(data_.length === 0 ) {
                    html_ = '<tr><td colspan="4" class="text-center">' + app.translate('common.data_not_found') + '</td></tr>';
                }
                
                _tableList.find('tbody').html(html_);
            };
            
            _loading(_tableList, 'show');
            
            setTimeout(function() {
                var buildingList = page.getElement.noticeCheckOutDataList.buildingList();
                
                jQuery.ajax({
                    type: 'get',
                    url: _CONTEXT_PATH_ + '/get_notice_check_out_by_building_data_list.html',
                    data: {
                        building_id: buildingList.val()
                    },
                    cache: false,
                    success:function(response) {
                        response = app.convertToJsonObject(response);
                        
                        if(response.message == SESSION_EXPIRE_STRING) {
                                app.alertSessionExpired();
                        }
                        else {
                            _renderDataList(response);
                        }
                        
                        _loading(_tableList, 'remove');
                    },
                    error: function() {
                        _loading(_tableList, 'remove');

                        app.alertSomethingError();
                    }
                });
            }, _DELAY_PROCESS_);
        },
        getRoomDataByBuilding: function() {
            var _tableList = page.getElement.roomDataList.tableList();
            var _renderDataList = function(dataList) {
                var data_ = dataList.data.roomData;
                var html_ = '';
                var _display = {
                    pricePerMonth: function(pricePerMonth) {
                        var p = app.valueUtils.undefinedToEmpty(pricePerMonth);
                        
                        return app.valueUtils.isEmptyValue(p.toString()) ? '' : app.valueUtils.numberFormat(p);
                    },
                    statusText: function(statusText, roomStatusId) {
                        var s = app.valueUtils.undefinedToEmpty(statusText);
                        
                        return app.valueUtils.isEmptyValue(s) ? '' 
                        : '<span class="label ' + app.system.getRoomStatusColorClass(roomStatusId) + '">' 
                                + app.translate(s) + '</span>';
                    }
                };
                _setCheckInLabel = function() {
                    var dataCurrentCheckIn = dataList.data.currentCheckIn;
                    var table_ = page.getElement.roomDataList.tableList();
                    
                    for(var index in dataCurrentCheckIn) {
                        var currentData = dataCurrentCheckIn[index];
                        var td_ = table_.find('tr[data-room-id="' + currentData.roomId + '"]').find('td:last');
                        
                        td_.html('<span class="label label-info">' + app.translate('room.check_in') + '</span>');
                    }
                };
                
                for(var index in data_) {
                    var currentData = data_[index];
                    var pricePerMonth_ = _display.pricePerMonth(currentData.pricePerMonth);
                    var statusText_ = _display.statusText(currentData.roomStatusText, currentData.roomStatusId);
                    
                    html_ += '<tr data-room-id="' + currentData.id + '">'
                        + '<td>' + app.valueUtils.undefinedToEmpty(currentData.floorSeq) + '</td>'
                        + '<td>' + app.valueUtils.undefinedToEmpty(currentData.roomNo) + '</td>'
                        + '<td>' + pricePerMonth_ + '</td>'
                        + '<td>' + statusText_ + '</td>'
                        + '<td class="text-center">&nbsp;</td>'
                        + '</tr>';      
                }
                
                if(data_.length === 0 ) {
                    html_ = '<tr><td colspan="5" class="text-center">' + app.translate('common.data_not_found') + '</td></tr>';
                }
                
                _tableList.find('tbody').html(html_);
                
                _setCheckInLabel(dataList);
            };
            
            _loading(_tableList, 'show');
            
            setTimeout(function() {
                var buildingList = page.getElement.roomDataList.buildingList();
                
                jQuery.ajax({
                    type: 'get',
                    url: _CONTEXT_PATH_ + '/get_room_data_by_building_data_list.html',
                    data: {
                        building_id: buildingList.val()
                    },
                    cache: false,
                    success:function(response) {
                        response = app.convertToJsonObject(response);

                        if(response.message == SESSION_EXPIRE_STRING) {
                                app.alertSessionExpired();
                        }
                        else {
                            _renderDataList(response);
                        }

                        _loading(_tableList, 'remove');
                    },
                    error: function() {
                        _loading(_tableList, 'remove');

                        app.alertSomethingError();
                    }
                });
            }, _DELAY_PROCESS_);
        }
    };
})();


