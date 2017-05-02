<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="commonHtml" class="my.apartment.common.CommonHtml" scope="page" />

<tiles:insertDefinition name="defaultTemplate">
    <spring:message code="electricity_water_meter.electricity_water_meter" var="msgPageTitle" /> 
    <tiles:putAttribute name="title" value="${msgPageTitle}" />
    <tiles:putAttribute name="css">
        <link rel="stylesheet" 
           href="<c:url value="/assets/view_resources/electricity_water_meter/index/css/index.css?v=${randomTextVersion}"/>">
    </tiles:putAttribute>
    <tiles:putAttribute name="js">       
        <script type="text/javascript" 
            src="<c:url value="/assets/view_resources/electricity_water_meter/index/js/index.js?v=${randomTextVersion}"/>"></script>
    </tiles:putAttribute>
    <tiles:putAttribute name="body">

        <div class="content-wrapper">
            <section class="content-header">
                <h1>
                    <spring:message code="electricity_water_meter.electricity_water_meter" />
                    <!--<small></small>-->
                </h1>
            </section>

            <section class="content">
                <div class="box box-primary">
                    <div class="box-header with-border">
                        <h3 class="box-title">
                            &nbsp;
                        </h3>

                        <%--<div class="box-tools pull-right">
                            <button class="btn btn-box-tool refresh-room-list"
                                title="">
                                <i class="fa fa-refresh"></i>
                            </button>
                        </div>--%>
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

    </tiles:putAttribute>
</tiles:insertDefinition>