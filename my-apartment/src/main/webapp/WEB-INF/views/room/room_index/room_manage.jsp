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
                        <a href="#red" data-toggle="tab">
                            <spring:message code="room.reservation" />
                        </a>
                    </li>
                    <li><a href="#orange" data-toggle="tab">Orange</a></li>
                    <li><a href="#yellow" data-toggle="tab">Yellow</a></li>
                    <li><a href="#green" data-toggle="tab">Green</a></li>
                    <li><a href="#blue" data-toggle="tab">Blue</a></li>
                </ul>
                <div id="my-tab-content" class="tab-content">
                    <div class="tab-pane fade in active" id="red">
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
                                    <th class="text-center" colspan="6">_Reservation History_</th>
                                </tr>
                                <tr>
                                    <th>_Reserve Date_</th>
                                    <th>_Reserve Expired_</th>
                                    <th>_Room_</th>
                                    <th>_ID Card_</th>
                                    <th>_Name Lastname_</th>
                                    <th>_Status</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                </tr>
                                <tr>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                </tr>
                                <tr>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                </tr>
                                <tr>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                </tr>
                                <tr>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                </tr>
                                <tr>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                </tr>
                                <tr>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                </tr>
                                <tr>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                </tr>
                                <tr>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                </tr>
                                <tr>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                </tr>
                                <tr>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                </tr>
                                <tr>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                </tr>
                                <tr>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                </tr>
                                <tr>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                </tr>
                                <tr>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                </tr>
                                <tr>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                    <td>...</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                    <div class="tab-pane fade" id="orange">
                        <h1>Orange</h1>
                        <p>orange orange orange orange orange</p>
                    </div>
                    <div class="tab-pane fade" id="yellow">
                        <h1>Yellow</h1>
                        <p>yellow yellow yellow yellow yellow</p>
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