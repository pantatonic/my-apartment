<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="commonHtml" class="my.apartment.common.CommonHtml" scope="page" />

<tiles:insertDefinition name="defaultTemplate">
    <spring:message code="room.invoice" var="msgPageTitle" /> 
    <tiles:putAttribute name="title" value="${msgPageTitle}" />
    <tiles:putAttribute name="css">
        <link rel="stylesheet" 
           href="<c:url value="/assets/view_resources/room_invoice/room_invoice/css/index.css?v=${randomTextVersion}"/>">
    </tiles:putAttribute>
    <tiles:putAttribute name="js">
        <script type="text/javascript" 
            src="<c:url value="/assets/view_resources/room_invoice/room_invoice/js/index.js?v=${randomTextVersion}"/>"></script>
        <script type="text/javascript">
            var iconRoom = '${commonHtml.getIconRoom()}';
        </script>
    </tiles:putAttribute>
    <tiles:putAttribute name="body">
        <div class="content-wrapper">
            <section class="content-header">
                <h1>
                    <spring:message code="room.invoice" />
                    <!--<small></small>-->
                </h1>
            </section>
                    
            <section class="content">
                <div class="box box-primary">
                    <div class="box-header with-border">
                        <%-- <h3 class="box-title">
                            &nbsp;
                        </h3> --%>
                        <button id="create-room-invoice" class="btn btn-warning btn-flat" 
                                data-loading-text="<spring:message code="common.now_processing" />">
                            <i class="fa fa-file-text-o"></i> 
                            <spring:message code="room.invoice.create_room_invoice" />
                        </button>
                        
                        <div class="col-xs-12 hidden-sm hidden-md hidden-lg clearfix" style="height: 50px;"><br></div>
                        
                        <div class="input-group" id="input-group-month-year">
                            <div class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></div>
                            <input type="text" id="room-invoice-month-year"  value="${currentYearMonth}"
                                    autocomplete="off" class="form-control text-center" readonly="readonly">
                        </div>
                    </div>
                    
                    <div class="box-body">
                        <select id="building-list" class="form-control">
                            <option value=""> -- <spring:message code="building.select_building" /> -- </option>
                            <c:forEach begin="0" end="${buildingList.length() - 1}" var="index">
                                <option value="${buildingList.getJSONObject(index).getInt("id")}" 
                                        data-electricity-meter-digit="${buildingList.getJSONObject(index).getInt("electricityMeterDigit")}" 
                                        data-water-meter-digit="${buildingList.getJSONObject(index).getInt("waterMeterDigit")}">
                                    ${buildingList.getJSONObject(index).getString("name")}
                                </option>
                            </c:forEach>
                        </select>
                            
                        <div id="box-room-container"></div>
                    </div>
                    
                    <div class="box-footer">
                        &nbsp;
                    </div>
                </div>
                    
            </section>
        </div>
                                    
        <textarea id="box-room-template">
            <div class="col-xs-12 col-sm-6 col-md-3 box-room_">
                <input type="checkbox" class="room-checkbox">
                <div class="box-room hover-box">
                    <div class="room-detail-label"></div>
                    <i class="${commonHtml.getIconRoom()} room-icon"></i>
                    <input type="hidden" name="id" value="">
                    <div class="clearfix"></div>
                    <span class="label label-room-status">Default Label</span>
                    <%--<span class="label label-invoiced">Default Label</span>--%>
                    <div class="box-room-name"></div>
                </div>
            </div>
        </textarea>
    </tiles:putAttribute>
</tiles:insertDefinition>    