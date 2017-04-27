<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="commonHtml" class="my.apartment.common.CommonHtml" scope="page" />

<br>
                        
<div style="text-align: center;">
    <button type="button" id="new-notice-check-out" class="btn btn-warning btn-flat">
        <spring:message code="room.notice_check_out_date" />
    </button>
</div>

<form id="notice-check-out-form" name="notice_check_out_form" method="post" class="form-horizontal" 
      action="<c:url value="notice_check_out_save.html" />">
    
    <div class="form-group col-xs-12 text-center">
        <button type="button" id="remove-notice-check-out" class="btn btn-warning btn-flat" 
                data-loading-text="<spring:message code="common.now_processing" />">
            <i class="fa fa-times"></i>
            <spring:message code="room.remove_notice" />
        </button>
    </div>
    <div class="clearfix"></div>
    
    <div class="form-group col-sm-6 col-md-6">
        <label class="col-sm-6 control-label">
            <spring:message code="room.notice_check_out_date" />
        </label>
        <div class="col-sm-6">
            <div class="input-group">
                <div class="input-group-addon">
                    <i class="glyphicon glyphicon-calendar"></i>
                </div>
                <input type="text" name="notice_check_out_date" value="" 
                    autocomplete="off" 
                    class="form-control input-datepicker datepicker-fixed-width my-required-field" 
                    readonly="readonly">
            </div>
        </div>
    </div>
    <div class="clearfix"></div>
    
    <div class="form-group">
        <label class="col-sm-3 control-label">
            <spring:message code="common.remark" />
        </label>
        <div class="col-sm-9" style="padding-left: 7px;">
            <input type="hidden" name="room_id" value="">
            <textarea name="remark" class="form-control" autocomplete="off"></textarea>
        </div>
    </div>
    <div class="clearfix"></div>
    
    <div class="text-right">
        <spring:message code="common.save" var="msgSavebuttonNoticeCheckOut" />
        ${commonHtml.getSaveButton(msgSavebuttonNoticeCheckOut, msgNowProcessing)}
    </div>
    
</form>
