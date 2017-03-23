<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="commonHtml" class="my.apartment.common.CommonHtml" scope="page" />

<form id="building-form" name="building_form" method="post" class="form-horizontal" 
      action="<c:url value="building/building_save.html" />">
    
    <div class="modal fade modal-scroll" id="modal-building-detail" tabindex="-1" role="dialog" aria-labelledby="modal-building-detail-label">
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
                        <label class="col-sm-3 control-label">
                            <spring:message code="building.name" />
                        </label>
                        <div class="col-sm-9">
                            <input type="hidden" name="id" value="">
                            <input type="text" name="name" class="form-control my-required-field" 
                                   placeholder="<spring:message code="building.name" />">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            <spring:message code="building.address" />
                        </label>
                        <div class="col-sm-9">
                            <textarea name="address" class="form-control" 
                                    placeholder="<spring:message code="building.address" />"></textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            <spring:message code="building.tel" />
                        </label>
                        <div class="col-sm-9">
                            <input type="text" name="tel" class="form-control" 
                                   placeholder="<spring:message code="building.tel" />">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            <spring:message code="building.electricity_charge_per_unit" />
                        </label>
                        <div class="col-sm-9">
                            <input type="text" name="electricity_charge_per_unit" 
                                   class="form-control force-inline my-required-field" 
                                   placeholder="">
                            
                            &nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="checkbox" id="use-min-electricity">
                            <label class="col-sm-3 control-label force-none-float force-text-left">
                                <spring:message code="building.use_min_electricity" />
                            </label>
                        </div>
                    </div>
                    <div class="form-group" id="use-min-electricity-blog">
                        <label class="col-sm-3 control-label">
                            <spring:message code="building.min_electricity_unit" />
                        </label>
                        <div class="col-sm-9">
                            <input type="text" name="min_electricity_unit" 
                                   class="form-control force-inline" 
                                   placeholder="">
                            
                            <label class="col-sm-4 control-label force-none-float">
                                <spring:message code="building.min_electricity_charge" />
                            </label>
                            <input type="text" name="min_electricity_charge" 
                                   class="form-control force-inline" 
                                   placeholder="">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">
                            <spring:message code="building.water_charge_per_unit" />
                        </label>
                        <div class="col-sm-9">
                            <input type="text" name="water_charge_per_unit" 
                                   class="form-control force-inline my-required-field" 
                                   placeholder="">
                            
                            &nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="checkbox" id="use-min-water">
                            <label class="col-sm-3 control-label force-none-float force-text-left">
                                <spring:message code="building.use_min_water" />
                            </label>
                        </div>
                    </div>
                    <div class="form-group" id="use-min-water-blog">
                        <label class="col-sm-3 control-label">
                            <spring:message code="building.min_water_unit" />
                        </label>
                        <div class="col-sm-9">
                            <input type="text" name="min_water_unit" 
                                   class="form-control force-inline" 
                                   placeholder="">
                            
                            <label class="col-sm-4 control-label force-none-float">
                                <spring:message code="building.min_water_charge" />
                            </label>
                            <input type="text" name="min_water_charge" 
                                   class="form-control force-inline" 
                                   placeholder="">
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
