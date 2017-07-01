<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="commonHtml" class="my.apartment.common.CommonHtml" scope="page" />

<div class="modal fade modal-scroll" id="modal-cancel-room-receipt" tabindex="-1" role="dialog" aria-labelledby="modal-cancel-room-receipt">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" 
                        data-dismiss="modal" 
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title text-danger" id="modal-building-detail-label">
                    <spring:message code="common.cancel" /> 
                    : 
                    <span id="receipt-display"></span>
                </h4>
            </div>
            <div class="modal-body">
                <input type="hidden" name="room_receipt_id" value="">
                <textarea name="cancel_description" class="form-control" 
                          placeholder="<spring:message code="room.invoice.description" />"></textarea>

            </div>
            <div class="modal-footer">
                <spring:message code="common.close" var="msgCloseModalButton"/>
                ${commonHtml.getCloseModalButton(msgCloseModalButton)}

                <button type="button" id="cancel-receipt-process-button" 
                        class="btn btn-danger btn-flat" data-loading-text="<spring:message code="common.now_processing" />">
                    <spring:message code="common.cancel" /> 
                    <spring:message code="room.receipt" />
                </button>
            </div>
        </div>
    </div>
</div>