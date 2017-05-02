/* global _DELAY_PROCESS_, _CONTEXT_PATH_, app */

jQuery(document).ready(function () {
    page.initialProcess();
    page.addEvent();

});

var page = (function () {

    return {
        initialProcess: function () {
            myCharts.roomByBuilding();
        },
        addEvent: function () {
            jQuery('.refresh-chart-data').click(function() {
                var targetOperation = jQuery(this).attr('target-operation');
                
                eval(targetOperation)();
            });

        },
        getElement:{
            getRoomByBuildingChartContent: function() {
                return jQuery('#room-by-building-chart');
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
        }
    };
})();


