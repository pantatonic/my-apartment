<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<tiles:insertDefinition name="defaultTemplate">
    <tiles:putAttribute name="title" value="Test Tiles" />
    <tiles:putAttribute name="css">
        <link rel="stylesheet" href="${appViewResources}/test/test_tiles/css/test_tiles.css?v=${randomTextVersion}">
    </tiles:putAttribute>
    <tiles:putAttribute name="js">
        <script type="text/javascript" src="${appViewResources}/test/test_tiles/js/test_tiles.js?v=${randomTextVersion}"></script>
    </tiles:putAttribute>
    <tiles:putAttribute name="body">
        Test tiles 
        <br>
        <spring:message code="test.message" />
    </tiles:putAttribute>
</tiles:insertDefinition>