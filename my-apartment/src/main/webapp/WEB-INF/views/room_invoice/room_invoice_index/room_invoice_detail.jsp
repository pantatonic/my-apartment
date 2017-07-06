<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="commonHtml" class="my.apartment.common.CommonHtml" scope="page" />

<div class="modal fade modal-scroll" id="modal-room-invoice-detail" tabindex="-1" role="dialog" aria-labelledby="modal-room-invoice-detail">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" 
                        data-dismiss="modal" 
                        aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">
                    <spring:message code="room.room_no" /> 
                    : 
                    <span id="room-no-display"></span>
                </h4>
            </div>
            <div class="modal-body">
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th><spring:message code="electricity_water_meter.electricity_meter" /></th>
                            <th><spring:message code="common.total" /></th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>
                                <spring:message code="electricity_water_meter.previous_meter" /> : 
                                <span id="electricity-prevoius-meter-display" class="data-display" data-key="electricityPreviousMeter"></span>, 
                                &nbsp;&nbsp;&nbsp;
                                <spring:message code="electricity_water_meter.electricity_meter" /> : 
                                <span id="electricity-present-meter-display" class="data-display" data-key="electricityPresentMeter"></span>
                            </td>
                            <td>&nbsp;</td>
                        </tr>
                        <tr>
                            <td>
                                <spring:message code="room.invoice.price_per_unit" /> : 
                                <span id="electricity-price-per-unit-display" class="data-display" data-key="electricityChargePerUnit"></span>
                            </td>
                            <td>&nbsp;</td>
                        </tr>
                        <tr>
                            <td>
                                <spring:message code="room.invoice.usage_unit" /> : 
                                <span id="electricity-usage-unit-display" class="data-display" data-key="electricityUsageUnit"></span>
                            </td>
                            <td>&nbsp;</td>
                        </tr>
                        <tr style="background-color: #f4f4f4;">
                            <td>
                                <spring:message code="room.invoice.value" />
                                <span id="electricity-min-calculate-message" class="text-success text-bold">
                                    (
                                    <spring:message code="room.invoice.if_electricity_usage_unit_less_than_" /> 
                                    <span id="min-electricity-unit-display"></span>.
                                    <spring:message code="room.invoice.value_is_" /> 
                                    <span id="min-electricity-charge-display"></span>
                                    )
                                </span>
                            </td>
                            <td><span id="electricity-value-display" class="data-display" data-key="electricityValue"></span></td>
                        </tr>
                    </tbody>
                </table>
                            
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th><spring:message code="electricity_water_meter.water_meter" /></th>
                            <th><spring:message code="common.total" /></th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr>
                            <td>
                                <spring:message code="electricity_water_meter.previous_meter" /> : 
                                <span id="water-prevoius-meter-display" class="data-display" data-key="waterPreviousMeter"></span>, 
                                &nbsp;&nbsp;&nbsp;
                                <spring:message code="electricity_water_meter.electricity_meter" /> : 
                                <span id="water-present-meter-display" class="data-display" data-key="waterPresentMeter"></span>
                            </td>
                            <td>&nbsp;</td>
                        </tr>
                        <tr>
                            <td>
                                <spring:message code="room.invoice.price_per_unit" /> : 
                                <span id="water-price-per-unit-display" class="data-display" data-key="waterChargePerUnit"></span>
                            </td>
                            <td>&nbsp;</td>
                        </tr>
                        <tr>
                            <td>
                                <spring:message code="room.invoice.usage_unit" /> : 
                                <span id="water-usage-unit-display" class="data-display" data-key="waterUsageUnit"></span>
                            </td>
                            <td>&nbsp;</td>
                        </tr>
                        <tr style="background-color: #f4f4f4;">
                            <td>
                                <spring:message code="room.invoice.value" />
                                <span id="water-min-calculate-message" class="text-success text-bold">
                                    (
                                    <spring:message code="room.invoice.if_water_usage_unit_less_than_" /> 
                                    <span id="min-water-unit-display"></span>.
                                    <spring:message code="room.invoice.value_is_" /> 
                                    <span id="min-water-charge-display"></span>
                                    )
                                </span>
                            </td>
                            <td><span id="water-value-display" class="data-display" data-key="waterValue"></span></td>
                        </tr>
                    </tbody>
                </table>
                            
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th><spring:message code="room.invoice.room_price" /></th>
                            <th><span id="room-price-display" class="data-display" data-key="roomPricePerMonth"></span></th>
                        </tr>
                    </thead>
                </table>
                            
                <table class="table table-bordered" id="grand-total-table">
                    <thead>
                        <tr>
                            <th><spring:message code="common.grand_total" /></th>
                            <th><span id="grand-total-display"></span></th>
                        </tr>
                    </thead>
                </table>

            </div>
            <div class="modal-footer">
                <spring:message code="common.close" var="msgCloseModalButton"/>
                ${commonHtml.getCloseModalButton(msgCloseModalButton)}
            </div>
        </div>
    </div>
</div>