<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="title" value="Test Tiles" />
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
                    <small><spring:message code="dashboard.dashboard.page_sub_title" /></small>
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
                                            title="_Refresh Data_">
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

            </section>
        </div>


    </tiles:putAttribute>
</tiles:insertDefinition>