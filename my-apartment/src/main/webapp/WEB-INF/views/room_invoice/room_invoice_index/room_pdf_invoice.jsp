<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="commonHtml" class="my.apartment.common.CommonHtml" scope="page" />

<div class="modal fade modal-scroll" id="modal-room-pdf-invoice" tabindex="-1" role="dialog" aria-labelledby="modal-room-pdf-invoice">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" 
                        data-dismiss="modal" 
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">
                    <spring:message code="common.pdf" /> <spring:message code="room.invoice" />
                </h4>
            </div>
            <div class="modal-body">
                
            </div>
            <div class="modal-footer">
                <spring:message code="common.close" var="msgCloseModalButton"/>
                ${commonHtml.getCloseModalButton(msgCloseModalButton)}

                <button type="button" id="pdf-invoice-process-button" 
                        class="btn btn-warning btn-flat" data-loading-text="<spring:message code="common.now_processing" />">
                    <i class="fa fa-file-pdf-o"></i> 
                    <spring:message code="common.pdf" /> <spring:message code="room.invoice" />
                </button>
            </div>
        </div>
    </div>
</div>