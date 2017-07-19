/* global _DELAY_PROCESS_, _CONTEXT_PATH_, app */

jQuery(document).ready(function () {
    page.initialProcess();
    page.addEvent();

});

var page = (function () {

    return {
        initialProcess: function () {
            myCharts.roomByBuilding();
            myCharts.invoiceByBuildingMonth();
            myCharts.receiptByBuildingMonth();
            
            jQuery('#invoice-by-biulding-chart-month-year').datepicker({
                format:'yyyy-mm',
                minViewMode: 'months',
                autoclose: true
            }).on('changeDate',function(e) {
                myCharts.invoiceByBuildingMonth();
            });
        },
        addEvent: function () {
            jQuery('.refresh-chart-data').click(function() {
                var targetOperation = jQuery(this).attr('target-operation');
                
                eval(targetOperation)();
            });
            
            jQuery('#invoice-by-building-month-chart-box').find('.building-list').change(function() {
                myCharts.invoiceByBuildingMonth();
            });
        },
        getElement:{
            getRoomByBuildingChartContent: function() {
                return jQuery('#room-by-building-chart');
            },
            getInvoiceByBuildingMonthChartContent: function() {
                return jQuery('#invoice-by-building-month-chart');
            },
            getReceiptByBuildingMonthChartContent: function() {
                return jQuery('#receipt-by-building-month-chart');
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
                        bar: {
                            dataLabels: {
                                enabled: true
                            }
                        },
                        series: {
                            pointWidth: 40
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

                        shadow: true
                    },
                    credits: {
                        enabled: false
                    },
                    series: [{
                        showInLegend: false,
                        name: app.translate('dashboard.chart.number_of_room'),
                        //data: [107, 31]
                        data: dataChart.data
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
                        dataChart = app.convertToJsonObject(response);
                        
                        _loading(_chartContent, 'remove');
                        
                        _renderChart(dataChart);
                    },
                    error: function() {
                        _loading(_chartContent, 'remove');

                        app.alertSomethingError();
                    }
                });
            }, _DELAY_PROCESS_);
        },
        invoiceByBuildingMonth: function() {
            var _chartContent = page.getElement.getInvoiceByBuildingMonthChartContent();
            var _renderChart = function(dataChart) {
                var counter = 0;
                
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
                        height: 500
                    },
                    title: {
                        text: app.translate('dashboard.chart.invoice_by_building'),
                        align: 'center'
                    },
                    subtitle: {
                        text: app.translate('common.total') + ' : Xxx ' + app.translate('room.invoice'),
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
                                enabled: true,
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
                var chartBox = _chartContent.closest('#invoice-by-building-month-chart-box');
                var buildingList = chartBox.find('.building-list');
                
                
                
                var tempDataSet = {
                    data: [
                        ['Cancel', 3],
                        ['Unbilled', 7],
                        ['Billed', 9]
                    ]
                };

                _renderChart(tempDataSet);
                _loading(_chartContent, 'remove');
            }, _DELAY_PROCESS_);
        },
        receiptByBuildingMonth: function() {
            var _chartContent = page.getElement.getReceiptByBuildingMonthChartContent();
            var _renderChart = function(dataChart) {
                var counter = 0;
                
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
                        height: 500
                    },
                    title: {
                        text: app.translate('dashboard.chart.receipt_by_building'),
                        align: 'center'
                    },
                    subtitle: {
                        text: app.translate('common.total') + ' : Xxx ' + app.translate('room.room_receipt'),
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
                                enabled: true,
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
                var tempDataSet = {
                    data: [
                        ['Cancel', 3],
                        ['Receipt', 7]
                    ]
                };

                _renderChart(tempDataSet);
                _loading(_chartContent, 'remove');
            }, _DELAY_PROCESS_);
        }
    };
})();


