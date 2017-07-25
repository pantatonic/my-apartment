<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:insertDefinition name="defaultTemplate">
    <spring:message code="dashboard.dashboard.page_title" var="msgPageTitle" /> 
    <tiles:putAttribute name="title" value="${msgPageTitle}" />
    <tiles:putAttribute name="css">
        <link rel="stylesheet" 
              href="<c:url value="/assets/view_resources/dashboard/dashboard_index/css/dashboard_index.css?v=${randomTextVersion}"/>">
    </tiles:putAttribute>
    <tiles:putAttribute name="js">
        <script type="text/javascript" 
        src="<c:url value="/assets/highcharts/js/highcharts.js" />"></script>
        <script type="text/javascript" 
        src="<c:url value="/assets/highcharts/js/highcharts-3d.js" />"></script>
        <script type="text/javascript" 
        src="<c:url value="/assets/highcharts/js/modules/exporting.js" />"></script>


        <script type="text/javascript" 
        src="<c:url value="/assets/view_resources/dashboard/dashboard_index/js/dashboard_index.js?v=${randomTextVersion}"/>"></script>
        <%--<script type="text/javascript">
            $(document).ready(function() {
                $('body').dblclick(function() {
                    $.ajax({
                        type: 'get',
                        url: _CONTEXT_PATH_ + '/d.html',
                        data: {
                            xxx: 'yyy'
                        },
                        cache: false,
                        success: function(response) {
                            $('.content-wrapper').append(response);
                        }
                    });
                });
            });
        </script>--%>

    </tiles:putAttribute>
    <tiles:putAttribute name="body">

        <div class="content-wrapper">
            <section class="content-header">
                <h1>
                    <spring:message code="dashboard.dashboard.page_title" />
                    <%-- <small><spring:message code="dashboard.dashboard.page_sub_title" /></small> --%>
                </h1>
            </section>


            <section class="content">

                <div class="row">
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                        <div class="box">
                            <div class="box-header with-border">
                                <h3 class="box-title">&nbsp;</h3>
                                <div class="box-tools pull-right">
                                    <button class="btn btn-box-tool refresh-chart-data" 
                                            target-operation="myCharts.roomByBuilding" 
                                            title="<spring:message code="common.refresh_data" />">
                                        <i class="fa fa-refresh"></i>
                                    </button>
                                </div>
                            </div>
                            <div class="box-body">
                                <div class="col-xs-12">
                                    <div id="room-by-building-chart"></div>
                                </div>
                            </div>
                            <div class="box-footer">
                                &nbsp;
                            </div>
                        </div>
                    </div>
                </div>
                                        
                <div class="row">
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                        <div class="box" id="room-data-by-building-list-box">
                            <div class="box-header with-border">
                                <select class="building-list form-control">
                                    <c:forEach begin="0" end="${buildingList.length() - 1}" var="index">
                                        <option value="${buildingList.getJSONObject(index).getInt("id")}" 
                                                data-electricity-meter-digit="${buildingList.getJSONObject(index).getInt("electricityMeterDigit")}" 
                                                data-water-meter-digit="${buildingList.getJSONObject(index).getInt("waterMeterDigit")}">
                                            ${buildingList.getJSONObject(index).getString("name")}
                                        </option>
                                    </c:forEach>
                                </select>
                                <div class="box-tools pull-right">
                                    <button class="btn btn-box-tool refresh-chart-data" 
                                            target-operation="dataList.getRoomDataByBuilding" 
                                            title="<spring:message code="common.refresh_data" />">
                                        <i class="fa fa-refresh"></i>
                                    </button>
                                </div>
                            </div>
                            <div class="box-body">
                                <div id="room-data-list-table-content" class="table-responsive">
                                    <table id="room-data-list-table" class="table table-bordered table-striped">
                                        <thead>
                                            <tr class="data-list-top-head">
                                                <th colspan="5" class="text-center"><spring:message code="dashboard.data_list.room_data" /></th>
                                            </tr>
                                            <tr>
                                                <th><spring:message code="room.floor" /></th>
                                                <th><spring:message code="room.room_no" /></th>
                                                <th><spring:message code="room.price_per_month" /></th>
                                                <th><spring:message code="room.status" /></th>
                                                <th>&nbsp;</th>
                                            </tr>
                                        </thead>
                                        <tbody>

                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div class="box-footer">
                                &nbsp;
                            </div>
                        </div>
                    </div>
                </div>
                                                
                <hr class="hr-separate">
                                                
                <div class="row">
                    <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                        <div class="box" id="invoice-by-building-month-chart-box">
                            <div class="box-header with-border">
                                <select class="building-list form-control">
                                    <c:forEach begin="0" end="${buildingList.length() - 1}" var="index">
                                        <option value="${buildingList.getJSONObject(index).getInt("id")}" 
                                                data-electricity-meter-digit="${buildingList.getJSONObject(index).getInt("electricityMeterDigit")}" 
                                                data-water-meter-digit="${buildingList.getJSONObject(index).getInt("waterMeterDigit")}">
                                            ${buildingList.getJSONObject(index).getString("name")}
                                        </option>
                                    </c:forEach>
                                </select>
                                <div class="box-tools pull-right">
                                    <button class="btn btn-box-tool refresh-chart-data" 
                                            target-operation="myCharts.invoiceByBuildingMonth" 
                                            title="<spring:message code="common.refresh_data" />">
                                        <i class="fa fa-refresh"></i>
                                    </button>
                                </div>
                            </div>
                            <div class="box-body">
                                <div class="col-xs-12">
                                    <div class="input-group">
                                        <div class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></div>
                                        <input type="text" id="invoice-by-building-chart-month-year"  value="${currentYearMonth}"
                                                autocomplete="off" class="form-control text-center" readonly="readonly">
                                    </div>
                                    <br>
                                    <div id="invoice-by-building-month-chart"></div>
                                </div>
                            </div>
                            <div class="box-footer">
                                &nbsp;
                            </div>
                        </div>
                    </div>
                    <div class="col-xs-12 col-sm-12 col-md-6 col-lg-6">
                        <div class="box" id="receipt-by-building-month-chart-box">
                            <div class="box-header with-border">
                                <select class="building-list form-control">
                                    <c:forEach begin="0" end="${buildingList.length() - 1}" var="index">
                                        <option value="${buildingList.getJSONObject(index).getInt("id")}" 
                                                data-electricity-meter-digit="${buildingList.getJSONObject(index).getInt("electricityMeterDigit")}" 
                                                data-water-meter-digit="${buildingList.getJSONObject(index).getInt("waterMeterDigit")}">
                                            ${buildingList.getJSONObject(index).getString("name")}
                                        </option>
                                    </c:forEach>
                                </select>
                                <div class="box-tools pull-right">
                                    <button class="btn btn-box-tool refresh-chart-data" 
                                            target-operation="myCharts.receiptByBuildingMonth" 
                                            title="<spring:message code="common.refresh_data" />">
                                        <i class="fa fa-refresh"></i>
                                    </button>
                                </div>
                            </div>
                            <div class="box-body">
                                <div class="col-xs-12">
                                    <div class="input-group">
                                        <div class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></div>
                                        <input type="text" id="receipt-by-building-chart-month-year"  value="${currentYearMonth}"
                                                autocomplete="off" class="form-control text-center" readonly="readonly">
                                    </div>
                                    <br>
                                    <div id="receipt-by-building-month-chart"></div>
                                </div>
                            </div>
                            <div class="box-footer">
                                &nbsp;
                            </div>
                        </div>
                    </div>
                </div>
                                                
                <hr class="hr-separate">
                                                
                <div class="row">
                    <div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
                        <div class="box" id="notice-check-out-by-building-list-box">
                            <div class="box-header with-border">
                                <select class="building-list form-control">
                                    <c:forEach begin="0" end="${buildingList.length() - 1}" var="index">
                                        <option value="${buildingList.getJSONObject(index).getInt("id")}" 
                                                data-electricity-meter-digit="${buildingList.getJSONObject(index).getInt("electricityMeterDigit")}" 
                                                data-water-meter-digit="${buildingList.getJSONObject(index).getInt("waterMeterDigit")}">
                                            ${buildingList.getJSONObject(index).getString("name")}
                                        </option>
                                    </c:forEach>
                                </select>
                                <div class="box-tools pull-right">
                                    <button class="btn btn-box-tool refresh-chart-data" 
                                            target-operation="dataList.getNoticeCheckOutByBuilding" 
                                            title="<spring:message code="common.refresh_data" />">
                                        <i class="fa fa-refresh"></i>
                                    </button>
                                </div>
                            </div>
                            <div class="box-body">
                                <div id="notice-check-out-list-table-content" class="table-responsive">
                                    <table id="notice-check-out-list-table" class="table table-bordered table-striped">
                                        <thead>
                                            <tr class="data-list-top-head">
                                                <th colspan="4" class="text-center"><spring:message code="room.notice_check_out" /></th>
                                            </tr>
                                            <tr>
                                                <th><spring:message code="room.notice_check_out_date" /></th>
                                                <th><spring:message code="room.room_no" /></th>
                                                <th><spring:message code="dashboard.chart.building_name" /></th>
                                                <th><spring:message code="common.remark" /></th>
                                            </tr>
                                        </thead>
                                        <tbody>

                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div class="box-footer">
                                &nbsp;
                            </div>
                        </div>
                    </div>
                </div>

            </section>
        </div>


    </tiles:putAttribute>
</tiles:insertDefinition>