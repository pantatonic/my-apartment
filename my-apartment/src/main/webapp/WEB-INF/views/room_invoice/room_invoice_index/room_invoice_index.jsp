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
                        <%--<button id="create-room-invoice" class="btn btn-warning btn-flat" 
                                data-loading-text="<spring:message code="common.now_processing" />">
                            <i class="fa fa-file-text-o"></i> 
                            <spring:message code="room.invoice.create_room_invoice" />
                        </button>--%>
                        
                        <div class="dropdown">
                            <button class="btn btn-warning btn-flat btn-sm dropdown-toggle" type="button" data-toggle="dropdown">
                                <spring:message code="common.process_action" /> &nbsp;
                                <span class="caret"></span>
                            </button>
                            <ul class="dropdown-menu">
                                <li id="create-room-invoice"><a href="javascript:void(0);"><i class="fa fa-file-o"></i> <spring:message code="room.invoice.create_room_invoice" /></a></li>
                                <li id="pdf-room-invoice">
                                    <a href="javascript:void(0);">
                                        <i class="fa fa-file-pdf-o"></i> 
                                        <spring:message code="common.pdf" /> <spring:message code="room.invoice" />
                                    </a>
                                </li>
                                <li id="pdf-room-receipt">
                                    <a href="javascript:void(0);">
                                        <i class="fa fa-file-pdf-o"></i> 
                                        <spring:message code="common.pdf" /> <spring:message code="room.receipt" />
                                    </a>
                                </li>
                            </ul>
                        </div>
                        
                        <div class="col-xs-12 hidden-sm hidden-md hidden-lg clearfix" style="height: 20px;"><br></div>
                        
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
                    <br><br><br>
                    <div class="clearfix"></div>
                    <span class="label label-room-status">Default Label</span>
                    <%--<span class="label label-invoiced">Default Label</span>--%>
                    <div class="box-room-name"></div>
                </div>
            </div>
        </textarea>
                    
        <textarea id="table-pdf-invoice-template">
            <table id="table-pdf-invoice" class="table table-bordered table-striped">
                <thead>
                    <tr>
                        <th class="text-center" style="width: 150px;">
                            <label><input type="checkbox" id="main-pdf-invoice-checkbox"> 
                                <spring:message code="common.checked_all" />
                            </label>
                        </th>
                        <th><spring:message code="building.room" /></th>
                    </tr>
                </thead>
                <tbody>

                </tbody>
            </table>
        </textarea>
                    
        <form id="pdf-invoice-form" method="post" target="room_invoice_pdf" action="<c:url value="pdf_room_invoice.html" />">
            
        </form>
        
        
        <textarea id="table-pdf-receipt-template">
            <table id="table-pdf-receipt" class="table table-bordered table-striped">
                <thead>
                    <tr>
                        <th class="text-center" style="width: 150px;">
                            <label><input type="checkbox" id="main-pdf-receipt-checkbox"> 
                                <spring:message code="common.checked_all" />
                            </label>
                        </th>
                        <th><spring:message code="building.room" /></th>
                    </tr>
                </thead>
                <tbody>

                </tbody>
            </table>
        </textarea>
                    
        <%-- use target same form #pdf-invoice-form --%>
        <form id="pdf-receipt-form" method="post" target="room_invoice_pdf" action="<c:url value="pdf_room_receipt.html" />">
            
        </form>
                    
                    
                    
        <jsp:include page="room_invoice_cancel.jsp" flush="true" />
        <jsp:include page="room_invoice_detail.jsp" flush="true" />
        <jsp:include page="room_pdf_invoice.jsp" flush="true" />
        
        <jsp:include page="room_receipt_cancel.jsp" flush="true" />
        <jsp:include page="room_receipt_detail.jsp" flush="true" />
        <jsp:include page="room_pdf_receipt.jsp" flush="true" />
        
    </tiles:putAttribute>
</tiles:insertDefinition>    