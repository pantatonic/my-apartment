<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="commonHtml" class="my.apartment.common.CommonHtml" scope="page" />

<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="title" value="Test Tiles" />
    <tiles:putAttribute name="css">
        <%--<link rel="stylesheet" 
           href="<c:url value="/assets/view_resources/test/test_tiles/css/test_tiles.css?v=${randomTextVersion}"/>">--%>
    </tiles:putAttribute>
    <tiles:putAttribute name="js">
        <script type="text/javascript" 
        src="<c:url value="/assets/view_resources/building/building_index/js/building_index.js?v=${randomTextVersion}"/>"></script>
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
                <div class="box box-primary">
                    <div class="box-header with-border">
                        <h3 class="box-title">
                            &nbsp;
                        </h3>
                        <spring:message code="apartment.add_building" var="msgAddBuilding" />
                        ${commonHtml.getAddButton(msgAddBuilding)}
                    </div>

                    <div class="box-body">

                    </div>

                    <div class="box-footer">
                        &nbsp;

                    </div>
                </div>

            </section>
        </div>

        <jsp:include page="building_detail.jsp" flush="true" />

        

    </tiles:putAttribute>
</tiles:insertDefinition>