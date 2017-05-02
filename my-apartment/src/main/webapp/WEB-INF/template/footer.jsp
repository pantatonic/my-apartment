<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<footer class="main-footer">
    <div class="pull-right hidden-xs">
        <b>&nbsp;</b>
    </div>
    <strong><spring:message code="application.footer" /></strong>
</footer>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="modal fade" id="modal-project-simple-structure" tabindex="-1" role="dialog" aria-labelledby="modal-project-simple-structure">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-body" style="padding: 0;">

                <img src="<c:url value="/assets/dist/img/diagram.png" />" id="image-project-simple-structure" class="img-responsive">

            </div>
        </div>
    </div>
</div>
