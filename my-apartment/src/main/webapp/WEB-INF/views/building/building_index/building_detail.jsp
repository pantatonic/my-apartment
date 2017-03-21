<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="commonHtml" class="my.apartment.common.CommonHtml" scope="page" />

<form id="building-form" name="building_form" method="post" class="form-horizontal"
      action="<c:url value="building/building_save.html" />">
    
    <div class="modal fade" id="modal-building-detail" tabindex="-1" role="dialog" aria-labelledby="modal-building-detail-label">
        <div class="modal-dialog modal-lg" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" 
                            data-dismiss="modal" 
                            aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                    <h4 class="modal-title" id="modal-building-detail-label">
                        <spring:message code="apartment.building" />
                    </h4>
                </div>
                <div class="modal-body">
                    
                    <div class="form-group">
                        <label class="col-sm-2 control-label">
                            <spring:message code="building.name" />
                        </label>
                        <div class="col-sm-10">
                            <input type="text" name="name" class="form-control" 
                                   placeholder="<spring:message code="building.name" />">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">
                            <spring:message code="building.address" />
                        </label>
                        <div class="col-sm-10">
                            <textarea name="address" class="form-control" 
                                    placeholder="<spring:message code="building.address" />"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox"> Remember me
                                </label>
                            </div>
                        </div>
                    </div>


                </div>
                <div class="modal-footer">
                    <spring:message code="common.close" var="msgCloseModalButton"/>
                    ${commonHtml.getCloseModalButton(msgCloseModalButton)}

                    <spring:message code="common.save" var="msgSavebutton" />
                    ${commonHtml.getSaveButton(msgSavebutton)}
                </div>
            </div>
        </div>
    </div>
                
</form>
