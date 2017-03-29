<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="commonHtml" class="my.apartment.common.CommonHtml" scope="page" />

<form id="room-form" name="room_form" method="post" class="form-horizontal" 
      action="<c:url value="room/room_save.html" />">
    
    <div class="modal fade modal-scroll" id="modal-room-detail" tabindex="-1" role="dialog" aria-labelledby="modal-room-detail-label">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" 
                            data-dismiss="modal" 
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="modal-room-detail-label">
                        <spring:message code="building.room" />
                    </h4>
                </div>
                <div class="modal-body">
                    
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            <spring:message code="room.room_no" />
                        </label>
                        <div class="col-sm-9">
                            <input type="hidden" name="id" value="">
                            <input type="hidden" name="building_id" value="">
                            <input type="text" name="room_no" class="form-control my-required-field" 
                                   placeholder="<spring:message code="room.room_no" />" 
                                   autocomplete="off">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            <spring:message code="room.name" />
                        </label>
                        <div class="col-sm-9">
                            <input type="text" name="name" class="form-control" 
                                   placeholder="<spring:message code="room.name" />" 
                                   autocomplete="off">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            <spring:message code="room.floor" />
                        </label>
                        <div class="col-sm-9">
                            <select name="floor_seq" class="form-control my-required-field">
                                <option value=""> -- <spring:message code="room.select_floor" /> -- </option>
                                <c:forEach var="i" begin="1" step="1" end="100">
                                    <option value="${i}">${i}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            <spring:message code="room.price_per_month" />
                        </label>
                        <div class="col-sm-9">
                            <input type="text" name="price_per_month" class="form-control my-required-field number-decimal-separate" 
                                   placeholder="<spring:message code="room.price_per_month" />" 
                                   autocomplete="off">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            <spring:message code="room.status" />
                        </label>
                        <div class="col-sm-9">
                            
                            <select name="room_status_id" class="form-control my-required-field">
                                <option value=""> -- <spring:message code="common.please_select_data" /> -- </option>
                                <c:forEach begin="0" end="${roomStatusList.length() - 1}" var="index">
                                    <option value="${roomStatusList.getJSONObject(index).getInt("id")}">
                                        <c:set var="dynamicMsgRoomStatus" value="${roomStatusList.getJSONObject(index).getString(\"status\")}" />
                                        <spring:message code="${dynamicMsgRoomStatus}" />
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                        
                </div>
                <div class="modal-footer">
                    <spring:message code="common.close" var="msgCloseModalButton"/>
                    ${commonHtml.getCloseModalButton(msgCloseModalButton)}

                    <spring:message code="common.save" var="msgSavebutton" />
                    ${commonHtml.getSaveButton(msgSavebutton, msgNowProcessing)}
                </div>
            </div>
        </div>
    </div>
                
</form>
