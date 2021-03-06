<%@ page contentType="text/html" pageEncoding="UTF-8" %>

<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="commonHtml" class="my.apartment.common.CommonHtml" scope="page" />

<tiles:insertDefinition name="defaultTemplate">
    <spring:message code="apartment.building.page_title" var="msgPageTitle" /> 
    <tiles:putAttribute name="title" value="${msgPageTitle}" />
    <tiles:putAttribute name="css">
        <link rel="stylesheet" 
           href="<c:url value="/assets/view_resources/building/building_index/css/building_index.css?v=${randomTextVersion}"/>">
    </tiles:putAttribute>
    <tiles:putAttribute name="js">
        <script type="text/javascript" 
            src="<c:url value="/assets/view_resources/building/building_index/js/building_index.js?v=${randomTextVersion}"/>"></script>
        <script type="text/javascript" 
            src="<c:url value="/assets/view_resources/building/building_index/js/modal_building_detail.js?v=${randomTextVersion}"/>"></script>
    </tiles:putAttribute>
    <tiles:putAttribute name="body">

        <div class="content-wrapper">
            <section class="content-header">
                <h1>
                    <spring:message code="apartment.building.page_title" />
                    <!--<small><spring:message code="apartment.building.page_sub_title" /></small>-->
                </h1>
            </section>


            <section class="content">
                <div id="parent-box-building-container" class="box box-primary">
                    <div class="box-header with-border">
                        <h3 class="box-title">
                            &nbsp;
                        </h3>
                        <spring:message code="apartment.add_building" var="msgAddBuilding" />
                        ${commonHtml.getAddButton(msgAddBuilding)}
                        
                        <div class="box-tools pull-right">
                            <button class="btn btn-box-tool refresh-building-list"
                                title="">
                                <i class="fa fa-refresh"></i>
                            </button>
                        </div>
                    </div>

                    <div id="box-building-container" class="box-body">
                        
                    </div>

                    <div class="box-footer">
                        &nbsp;
                    </div>
                </div>

            </section>
        </div>
                    
        <textarea id="box-building-template">
            <div class="col-xs-12 col-sm-6 col-md-3 box-building_">
                <div class="box-building hover-box">
                    <i class="fa fa-building building-icon"></i>
                    <div class="clearfix"></div>
                    <div class="box-building-name"></div>
                    
                </div>
                
                <div class="clearfix"></div>
                <button type="button" class="btn btn-primary btn-flat button-room" 
                        data-loading-text="<spring:message code="common.now_processing" />">
                    <i class="fa fa-columns"></i> 
                    <spring:message code="building.room" />
                </button>
                <spring:message code="common.delete" var="msgDeleteBuilding" />
                ${commonHtml.getDeleteButton(msgDeleteBuilding, msgNowProcessing)}
            </div>
        </textarea>

        <jsp:include page="building_detail.jsp" flush="true" />

        

    </tiles:putAttribute>
</tiles:insertDefinition>