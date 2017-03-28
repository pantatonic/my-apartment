<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="commonHtml" class="my.apartment.common.CommonHtml" scope="page" />

<tiles:insertDefinition name="defaultTemplate">
    <spring:message code="building.room" var="msgPageTitle" /> 
    <tiles:putAttribute name="title" value="${msgPageTitle}" />
    <tiles:putAttribute name="title" value="Test Tiles" />
    <tiles:putAttribute name="css">
        <link rel="stylesheet" 
           href="<c:url value="/assets/view_resources/room/room_index/css/room_index.css?v=${randomTextVersion}"/>">
    </tiles:putAttribute>
    <tiles:putAttribute name="js">
        <script type="text/javascript">
            var buildingIdString = '${buildingIdString}';
        </script>
        
        <script type="text/javascript" 
            src="<c:url value="/assets/view_resources/room/room_index/js/room_index.js?v=${randomTextVersion}"/>"></script>
        <%--<script type="text/javascript" 
            src="<c:url value="/assets/view_resources/building/building_index/js/modal_building_detail.js?v=${randomTextVersion}"/>"></script>--%>
    </tiles:putAttribute>
    <tiles:putAttribute name="body">

        <div class="content-wrapper">
            <section class="content-header">
                <h1>
                    <spring:message code="building.room" />
                    <!--<small></small>-->
                </h1>
            </section>

            <section class="content">
                <div class="box box-primary">
                    <div class="box-header with-border">
                        <spring:message code="room.add_room" var="msgAddRoom" />
                        ${commonHtml.getAddButton(msgAddRoom)}
                    </div>

                    <div class="box-body">
                        <select id="building-list" class="form-control">
                            <option value=""> -- <spring:message code="building.select_building" /> -- </option>
                            <c:forEach begin="0" end="${buildingList.length() - 1}" var="index">
                                <option value="${buildingList.getJSONObject(index).getInt("id")}">
                                    ${buildingList.getJSONObject(index).getString("name")}
                                </option>
                            </c:forEach>
                        </select>
                            
                        <hr>
                        
                    </div>

                    <div class="box-footer">
                        
                    </div>
                </div>

            </section>
        </div>     

        <jsp:include page="room_detail.jsp" flush="true" />
                            
    </tiles:putAttribute>
</tiles:insertDefinition>