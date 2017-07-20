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
        },
        addEvent: function() {
            page.addEvent.refreshChartData();
            page.addEvent.getInvoiceByBuildingMonthChart();
            page.addEvent.getReceiptByBuildingMonthChart();
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
                jQuery('#invoice-by-building-month-chart-box').find('.building-list').change(function() {
                    myCharts.invoiceByBuildingMonth();
                });
            },
            getReceiptByBuildingMonthChart: function() {
                jQuery('#receipt-by-building-month-chart-box').find('.building-list').change(function() {
                    myCharts.receiptByBuildingMonth();
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
            var _renderChart = function(dataChart) {
                var counter = 0;
                var _countData = function(dataChart) {
                    var data_ = dataChart.data;
                    var countData_ = 0;
                    for(var i in data_) {
                        countData_ = countData_ + (data_[i][1]);
                    }
                    
                    return countData_;
                };

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
                        text: app.translate('common.total') + ' : ' + _countData(dataChart) + ' ' + app.translate('room.invoice'),
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
                                enabled: _countData(dataChart) > 0 ? true : false,
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
                        data: dataChart.data
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
        receiptByBuildingMonth: function() {
            var _chartContent = page.getElement.receiptByBuildingMonthChart.content();
            var _renderChart = function(dataChart) {
                var counter = 0;
                var _countData = function(dataChart) {
                    var data_ = dataChart.data;
                    var countData_ = 0;
                    for(var i in data_) {
                        countData_ = countData_ + (data_[i][1]);
                    }
                    
                    return countData_;
                };

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
                        text: app.translate('dashboard.chart.receipt_by_building'),
                        align: 'center'
                    },
                    subtitle: {
                        text: app.translate('common.total') + ' : ' + _countData(dataChart) + ' ' + app.translate('room.room_receipt'),
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
                                enabled: _countData(dataChart) > 0 ? true : false,
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
                        data: dataChart.data
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
        }
    };
})();


