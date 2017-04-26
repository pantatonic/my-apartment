<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="commonHtml" class="my.apartment.common.CommonHtml" scope="page" />


<div class="modal fade modal-scroll" id="modal-room-manage" tabindex="-1" role="dialog" aria-labelledby="modal-room-manage-label">
    <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" 
                        data-dismiss="modal" 
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="modal-room-manage-label">
                    <spring:message code="room.manage" /> 
                    : 
                    <span class="modal-room-no-label"></span>
                </h4>
            </div>
            <div class="modal-body">

                <ul id="tabs" class="nav nav-tabs" data-tabs="tabs">
                    <li class="active">
                        <a href="#reservation-tab" data-toggle="tab">
                            <spring:message code="room.reservation" />
                        </a>
                    </li>
                    <li>
                        <a href="#check-in-tab" data-toggle="tab">
                            <spring:message code="room.check_in" />
                        </a>
                    </li>
                    <li>
                        <a href="#notice-check-out-tab" data-toggle="tab">
                            <spring:message code="room.notice_check_out" />
                        </a>
                    </li>
                    <li><a href="#green" data-toggle="tab">Green</a></li>
                    <li><a href="#blue" data-toggle="tab">Blue</a></li>
                </ul>
                <div id="my-tab-content" class="tab-content">
                    <div class="tab-pane fade in active" id="reservation-tab">
                        <jsp:include page="room_manage_reservation_tab.jsp" flush="true" />
                    </div>
                    <div class="tab-pane fade" id="check-in-tab">
                        <jsp:include page="room_manage_check_in_tab.jsp" flush="true" />
                    </div>
                    <div class="tab-pane fade" id="notice-check-out-tab">
                        <jsp:include page="room_notice_check_out_tab.jsp" flush="true" />
                    </div>
                    <div class="tab-pane fade" id="green">
                        <h1>Green</h1>
                        <p>green green green green green</p>
                    </div>
                    <div class="tab-pane fade" id="blue">
                        <h1>Blue</h1>
                        <p>blue blue blue blue blue</p>
                    </div>
                </div>

            </div>
            <div class="modal-footer">
                <spring:message code="common.close" var="msgCloseModalButton"/>
                ${commonHtml.getCloseModalButton(msgCloseModalButton)}
            </div>
        </div>
    </div>
</div>