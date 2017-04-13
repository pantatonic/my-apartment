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
                </h4>
            </div>
            <div class="modal-body">

                <%--<div class="input-group">
                    <div class="input-group-addon">
                        <i class="glyphicon glyphicon-calendar"></i>
                    </div>
                    <input type="text" name="test_date" value="2017-04-13" 
                        autocomplete="off" class="form-control" readonly="readonly">
                </div>--%>

                <ul id="tabs" class="nav nav-tabs" data-tabs="tabs">
                    <li class="active"><a href="#red" data-toggle="tab">Red</a></li>
                    <li><a href="#orange" data-toggle="tab">Orange</a></li>
                    <li><a href="#yellow" data-toggle="tab">Yellow</a></li>
                    <li><a href="#green" data-toggle="tab">Green</a></li>
                    <li><a href="#blue" data-toggle="tab">Blue</a></li>
                </ul>
                <div id="my-tab-content" class="tab-content">
                    <div class="tab-pane fade in active" id="red">
                        <h1>Red</h1>
                        <p>red red red red red red</p>
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