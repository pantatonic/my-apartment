<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="commonHtml" class="my.apartment.common.CommonHtml" scope="page" />

<div class="modal fade modal-scroll" id="modal-receipt-chart-data-list" tabindex="-1" role="dialog" aria-labelledby="modal-receipt-chart-data-lis-labelt">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" 
                        data-dismiss="modal" 
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">
                    &nbsp;
                </h4>
            </div>
            <div class="modal-body">
                <table class="table table-bordered table-striped table-hover">
                    <thead>
                        <tr>
                            <th><spring:message code="room.receipt_no" /></th>
                            <th><spring:message code="room.receipt_date" /></th>
                            <th><spring:message code="room.invoice.value" /></th>
                            <th><spring:message code="common.status" /></th>
                        </tr>
                    </thead>
                    <tbody>
                        
                    </tbody>
                </table>
            </div>
            <div class="modal-footer">
                <spring:message code="common.close" var="msgCloseModalButton"/>
                ${commonHtml.getCloseModalButton(msgCloseModalButton)}
            </div>
        </div>
    </div>
</div>