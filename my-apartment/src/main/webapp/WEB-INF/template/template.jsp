<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">

        <c:set var="appCssResource" value="/assets/css" scope="application" />
        <c:set var="appJsResource" value="/assets/js" scope="application" />

        <title><tiles:insertAttribute name="title" /></title>

        <link rel="stylesheet" href="<c:url value="${appCssResource}/app.css" />" />
        <tiles:insertAttribute name="css" ignore="true" />

        <script type="text/javascript" src="<c:url value="${appJsResource}/jquery.1.11.0.min.js" />"></script>
        <script type="text/javascript" src="<c:url value="${appJsResource}/app.js" />"></script>
        <tiles:insertAttribute name="js" ignore="true" />

    </head>

    <body>

        <tiles:insertAttribute name="header" />
        <tiles:insertAttribute name="body" />
        <tiles:insertAttribute name="footer" />
    </body>
</html>
