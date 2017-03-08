<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        
        <!-- parameter for jquery ui datepicker extension -->
        <c:set var="randomTextVersion" scope="application"><%= java.lang.Math.random() %></c:set>
        
        <c:set var="req" value="${pageContext.request}" />
        <c:set var="url">${req.requestURL}</c:set>
        <c:set var="uri" value="${req.requestURI}" />
        <c:set var="baseUrl" value="${fn:substring(url, 0, fn:length(url) - fn:length(uri))}${req.contextPath}/" 
               scope="application" />
        
        <%-- ${fn:substring(url, 0, fn:length(url) - fn:length(uri))} --%>
        
        <c:set var="appAssetsString" value="assets" scope="application" />
        <c:set var="appAssets" value="${baseUrl}${appAssetsString}" scope="application" />
        <c:set var="appViewResources" value="${baseUrl}${appAssetsString}/view_resources" scope="application" />
        <c:set var="appCssResource" value="${appViewResources}/css" scope="application" />
        <c:set var="appJsResource" value="${appViewResources}/js" scope="application" />

        <title><tiles:insertAttribute name="title" /></title>
        
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        
        <!-- Font Awesome -->
        <link rel="stylesheet" href="${appAssets}/font_awesome/css/font-awesome.css">
        
        <!-- animate css -->
        <link rel="stylesheet" href="${appAssets}/animate_css/animate.css">
        
        <!-- jQuery -->
        <script type="text/javascript" src="${appAssets}/plugins/jQuery/jQuery-2.1.4.min.js"></script>
        
        <!-- Bootstrap 3.3.4 -->
        <link rel="stylesheet" href="${appAssets}/bootstrap/css/bootstrap.css">
        <script type="text/javascript" src="${appAssets}/bootstrap/js/bootstrap.min.js"></script>
        <script type="text/javascript">
            var bootstrapButton = $.fn.button.noConflict();
            $.fn.bootstrapBtn = bootstrapButton;

            /*var bootstrapTooltip = $.fn.tooltip.noConflict();
            $.fn.bootstrapTooltip = bootstrapTooltip;*/
        </script>
        
        <!-- SlimScroll -->
        <script src="${appAssets}/plugins/slimScroll/jquery.slimscroll.min.js"></script>

        <!-- FastClick -->
        <script src="${appAssets}/plugins/fastclick/fastclick.min.js"></script>

        <!-- AdminLTE App -->
        <script src="${appAssets}/dist/js/app.min.js"></script>
        
        <!-- AdminLTE for demo purposes -->
        <script src="${appAssets}/dist/js/demo.js"></script>
        
        <!-- Theme style -->
        <link rel="stylesheet" href="${appAssets}/dist/css/AdminLTE.css">
        
        <!-- AdminLTE Skins. Choose a skin from the css/skins folder instead of downloading all of them to reduce the load. -->
        <link rel="stylesheet" href="${appAssets}/dist/css/skins/_all-skins.css">
        
        <script type="text/javascript" src="${appAssets}/bootstrap-datetimepicker/js/moment-with-locales.js"></script>
        <link rel="stylesheet" href="${appAssets}/bootstrap-datetimepicker/css/bootstrap-datetimepicker.css">
        <script type="text/javascript" src="${appAssets}/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script>
        
        <!-- pnotify -->
        <link rel="stylesheet" href="${appAssets}/pnotify/pnotify.custom.css">
        <link rel="stylesheet" href="${appAssets}/pnotify/pnotify.override.css?v=${randomTextVersion}">
        <script type="text/javascript" src="${appAssets}/pnotify/pnotify.custom.js"></script>
        
        <!-- data table -->
        <link rel="stylesheet" href="${appAssets}/data_table/media/css/dataTables.bootstrap.css">
        <link rel="stylesheet" href="${appAssets}/data_table/extensions/Responsive/css/responsive.bootstrap.css">
        <script type="text/javascript" src="${appAssets}/data_table/media/js/jquery.dataTables.js"></script>
        <script type="text/javascript" src="${appAssets}/data_table/media/js/dataTables.bootstrap.js"></script>
        <script type="text/javascript" src="${appAssets}/data_table/extensions/Responsive/js/dataTables.responsive.js"></script>
        
        <!-- table auto row -->
        <script type="text/javascript" src="${appAssets}/plugins/jquery-table-auto-row.js"></script>
        
        <!-- sweet alert 2 -->
        <link rel="stylesheet" href="${appAssets}/sweetalert2/sweetalert2.min.css">
        <script type="text/javascript" src="${appAssets}/sweetalert2/sweetalert2.min.js"></script>
        <script type="text/javascript" src="${appAssets}/sweetalert2/promise.min.js"></script>

        <!-- ------------------------------- -->
        
        <link rel="stylesheet" href="<c:url value="${appCssResource}/app.css?v=${randomTextVersion}" />" />
        <tiles:insertAttribute name="css" ignore="true" />
        
        <!-- final override -->
        <link rel="stylesheet" href="${appCssResource}/final_override.css?v=${randomTextVersion}">

        <script type="text/javascript" src="<c:url value="${appJsResource}/common_constant.js?v=${randomTextVersion}" />"></script>
        <script type="text/javascript" src="<c:url value="${appJsResource}/app.js?v=${randomTextVersion}" />"></script>
        <script type="text/javascript" src="<c:url value="${appJsResource}/script.js?v=${randomTextVersion}" />"></script>
        <script type="text/javascript" src="${appAssets}/view_resources/js/alert_util.js?v=${randomTextVersion}"></script>
        <tiles:insertAttribute name="js" ignore="true" />

    </head>

    <body>

        <tiles:insertAttribute name="header" />
        <tiles:insertAttribute name="body" />
        <tiles:insertAttribute name="footer" />
    </body>
</html>
