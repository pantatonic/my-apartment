<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="commonHtml" class="my.apartment.common.CommonHtml" scope="page" />

<br>
                        
<div style="text-align: center;">
    <button type="button" id="new-room-reservation" class="btn btn-warning btn-flat">
        <spring:message code="room.create_reservation" />
    </button>
</div>

<form id="room-reservation-form" name="room_reservation_form" method="post" class="form-horizontal" 
    action="<c:url value="room/room_reservation_save.html" />">

    <div class="form-group col-sm-6 col-md-6">
        <label class="col-sm-6 control-label">
            <spring:message code="room.reserve_date" />
        </label>
        <div class="col-sm-6">
            <div class="input-group">
                <div class="input-group-addon">
                    <i class="glyphicon glyphicon-calendar"></i>
                </div>
                <input type="text" name="reserve_date" value="" 
                    autocomplete="off" 
                    class="form-control input-datepicker datepicker-fixed-width my-required-field" 
                    readonly="readonly">
            </div>
        </div>
    </div>

    <div class="form-group col-sm-6 col-md-6">
        <label class="col-sm-6 control-label">
            <spring:message code="room.reserve_expired" />
        </label>
        <div class="col-sm-6">
            <div class="input-group">
                <div class="input-group-addon">
                    <i class="glyphicon glyphicon-calendar"></i>
                </div>
                <input type="text" name="reserve_expired" value="" 
                    autocomplete="off" class="form-control input-datepicker datepicker-fixed-width" 
                    readonly="readonly">
            </div>
        </div>
    </div>
    <div class="clearfix"></div>

    <div class="form-group col-sm-6 col-md-6">
        <label class="col-sm-6 control-label">
            <spring:message code="room.id_card" />
        </label>
        <div class="col-sm-6 no-padding">
            <div class="col-sm-12">
                <input type="hidden" name="id" value="">
                <input type="hidden" name="room_id" value="">
                <input type="text" name="id_card" class="form-control my-required-field" 
                    value="" autocomplete="off">
            </div>
        </div>
    </div>
    <div class="clearfix"></div>

    <div class="form-group col-sm-6 col-md-6">
        <label class="col-sm-6 control-label">
            <spring:message code="common.person_name" />
        </label>
        <div class="col-sm-6">
            <input type="text" name="reserve_name" class="form-control my-required-field" 
                   value="" autocomplete="off">
        </div>
    </div>

    <div class="form-group col-sm-6 col-md-6">
        <label class="col-sm-6 control-label">
            <spring:message code="common.person_lastname" />
        </label>
        <div class="col-sm-6">
            <input type="text" name="reserve_lastname" class="form-control my-required-field" 
                   value="" autocomplete="off">
        </div>
    </div>
    <div class="clearfix"></div>

    <div class="form-group">
        <label class="col-sm-3 control-label">
            <spring:message code="common.remark" />
        </label>
        <div class="col-sm-9" style="padding-left: 7px;">
            <textarea name="remark" class="form-control"></textarea>
        </div>
    </div>
    <div class="clearfix"></div>

    <div class="form-group col-sm-6 col-md-6" id="reserve-status-form-group">
        <label class="col-sm-6 control-label">
            <spring:message code="common.status" />
        </label>
        <div class="col-sm-6">
            <select name="status" class="form-control my-required-field">
                <option value=""> -- <spring:message code="common.please_select_data" /> -- </option>
                <option value="1"><spring:message code="room.reserve" /></option>
                <option value="2"><spring:message code="room.close_reserve" /></option>
                <option value="3"><spring:message code="room.close_reserve_for_checkin" /></option>
            </select>
        </div>
    </div>
    <div class="clearfix"></div>

    <br>

    <div class="text-right">
        <spring:message code="common.save" var="msgSavebuttonReservation" />
        ${commonHtml.getSaveButton(msgSavebuttonReservation, msgNowProcessing)}
    </div>
</form>

<hr>

<table id="reservation-list" class="table table-bordered table-striped">
    <thead>
        <tr>
            <th class="text-center" colspan="6">
                <spring:message code="room.reservation_history" />
            </th>
        </tr>
        <tr>
            <th><spring:message code="room.reserve_date" /></th>
            <th><spring:message code="room.reserve_expired" /></th>
            <th><spring:message code="room.id_card" /></th>
            <th><spring:message code="common.person_name" /> <spring:message code="common.person_lastname" /></th>
            <th><spring:message code="common.status" /></th>
        </tr>
    </thead>
    <tbody>

    </tbody>
</table>