<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div id="js-language-strings" style="display: none;">
    {
    "test.message":"<spring:message code="test.message" />",
    
    "application.session_expired":"<spring:message code="application.session_expired" />",
    "application.footer":"<spring:message code="application.footer" />",
    
    "common.ok":"<spring:message code="common.ok" />",
    "common.cancel":"<spring:message code="common.cancel" />",
    "common.message":"<spring:message code="common.message" />",
    "common.login":"<spring:message code="common.login" />",
    "common.user":"<spring:message code="common.user" />",
    "common.password":"<spring:message code="common.password" />",
    "common.now_processing":"<spring:message code="common.now_processing" />",
    "common.processing_failed":"<spring:message code="common.processing_failed" />",
    "common.please_enter_data":"<spring:message code="common.please_enter_data" />",
    "common.invalid_email_format":"<spring:message code="common.invalid_email_format" />",
    "common.something_error_try_again":"<spring:message code="common.something_error_try_again" />",
    "common.close":"<spring:message code="common.close" />",
    "common.save":"<spring:message code="common.save" />",
    "common.save_success":"<spring:message code="common.save_success" />",
    "common.delete":"<spring:message code="common.delete" />",
    "common.delete_success":"<spring:message code="common.delete_success" />",
    "common.please_confirm_to_process":"<spring:message code="common.please_confirm_to_process" />",
    "common.please_select_data":"<spring:message code="common.please_select_data" />",
    "common.data_not_found":"<spring:message code="common.data_not_found" />",
    "common.confirm_change_important_data":"<spring:message code="common.confirm_change_important_data" />",
    "common.total":"<spring:message code="common.total" />",
    "common.grand_total":"<spring:message code="common.grand_total" />",
    "common.baht":"<spring:message code="common.baht" />",
    "common.pay":"<spring:message code="common.pay" />",
    "common.checked_all":"<spring:message code="common.checked_all" />",
    "common.process_action":"<spring:message code="common.process_action" />",
    "common.pdf":"<spring:message code="common.pdf" />",
    
    "common.person_name":"<spring:message code="common.person_name" />",
    "common.person_lastname":"<spring:message code="common.person_lastname" />",
    "common.remark":"<spring:message code="common.remark" />",
    "common.status":"<spring:message code="common.status" />",
    "common.refresh_data":"<spring:message code="common.refresh_data" />",
    
    "account_not_found":"<spring:message code="account_not_found" />",
    "account_is_disabled":"<spring:message code="account_is_disabled" />",
    
    "dashboard.dashboard.page_title":"<spring:message code="dashboard.dashboard.page_title" />",
    "dashboard.dashboard.page_sub_title":"<spring:message code="dashboard.dashboard.page_sub_title" />",
    "dashboard.chart.room_by_building":"<spring:message code="dashboard.chart.room_by_building" />",
    "dashboard.chart.room_in_building":"<spring:message code="dashboard.chart.room_in_building" />",
    "dashboard.chart.number_of_room":"<spring:message code="dashboard.chart.number_of_room" />",
    "dashboard.chart.building_name":"<spring:message code="dashboard.chart.building_name" />",
    "dashboard.chart.invoice_by_building":"<spring:message code="dashboard.chart.invoice_by_building" />",
    "dashboard.chart.receipt_by_building":"<spring:message code="dashboard.chart.receipt_by_building" />",
    "dashboard.chart.receipt_of_invoice_by_building":"<spring:message code="dashboard.chart.receipt_of_invoice_by_building" />",
    
    "apartment":"<spring:message code="apartment" />",
    
    "apartment.building":"<spring:message code="apartment.building" />",
    "apartment.building.page_title":"<spring:message code="apartment.building.page_title" />",
    "apartment.building.page_sub_title":"<spring:message code="apartment.building.page_sub_title" />",
    "apartment.add_building":"<spring:message code="apartment.add_building" />",
    
    "building.name":"<spring:message code="building.name" />",
    "building.address":"<spring:message code="building.address" />",
    "building.tel":"<spring:message code="building.tel" />",
    "building.electricity_charge_per_unit":"<spring:message code="building.electricity_charge_per_unit" />",
    "building.min_electricity_unit":"<spring:message code="building.min_electricity_unit" />",
    "building.min_electricity_charge":"<spring:message code="building.min_electricity_charge" />",
    "building.min_water_unit":"<spring:message code="building.min_water_unit" />",
    "building.min_water_charge":"<spring:message code="building.min_water_charge" />",
    
    "building.use_min_electricity":"<spring:message code="building.use_min_electricity" />",
    "building.use_min_water":"<spring:message code="building.use_min_water" />",
    
    "building.meter_digit_must_more_than_zero":"<spring:message code="building.meter_digit_must_more_than_zero" />",
    "building.select_building":"<spring:message code="building.select_building" />",
    
    "building.cannot_delete_building_has_any_data":"<spring:message code="building.cannot_delete_building_has_any_data" />",
    
    "building.room":"<spring:message code="building.room" />",
    "building.rooms":"<spring:message code="building.rooms" />",
    
    "room.add_room":"<spring:message code="room.add_room" />",
    "room.status.enabled":"<spring:message code="room.status.enabled" />",
    "room.status.disabled":"<spring:message code="room.status.disabled" />",
    "room.status.maintenance":"<spring:message code="room.status.maintenance" />",
    
    "room.floor":"<spring:message code="room.floor" />",
    "room.room_no":"<spring:message code="room.room_no" />",
    "room.name":"<spring:message code="room.name" />",
    "room.price_per_month":"<spring:message code="room.price_per_month" />",
    "room.status":"<spring:message code="room.status" />",
    "room.startup_electricity_meter_digit":"<spring:message code="room.startup_electricity_meter_digit" />",
    "room.startup_water_meter_digit":"<spring:message code="room.startup_water_meter_digit" />",
    
    "room.select_floor":"<spring:message code="room.select_floor" />",
    "room.room_no_is_duplicated":"<spring:message code="room.room_no_is_duplicated" />",
    "room.cannot_delete_room_has_any_data":"<spring:message code="room.cannot_delete_room_has_any_data" />",
    "room.manage":"<spring:message code="room.manage" />",
    
    "room.reservation":"<spring:message code="room.reservation" />",
    "room.reserve_date":"<spring:message code="room.reserve_date" /> ",
    "room.reserve_expired":"<spring:message code="room.reserve_expired" />",
    "room.id_card":"<spring:message code="room.id_card" />",
    
    "room.create_reservation":"<spring:message code="room.create_reservation" />",
    "room.reserve":"<spring:message code="room.reserve" />",
    "room.close_reserve":"<spring:message code="room.close_reserve" />",
    "room.close_reserve_for_checkin":"<spring:message code="room.close_reserve_for_checkin" />",
    
    "room.cannot_get_room_manage_detail_list":"<spring:message code="room.cannot_get_room_manage_detail_list" />",
    
    "room.reservation_history":"<spring:message code="room.reservation_history" />",
    "room.check_in_out_history":"<spring:message code="room.check_in_out_history" />",
    
    "room.check_in":"<spring:message code="room.check_in" />",
    "room.not_check_in":"<spring:message code="room.not_check_in" />",
    "room.check_in_date":"<spring:message code="room.check_in_date" />",
    "room.create_check_in":"<spring:message code="room.create_check_in" />",
    "room.check_out":"<spring:message code="room.check_out" />",
    "room.check_out_date":"<spring:message code="room.check_out_date" />",
    
    "room.notice_check_out":"<spring:message code="room.notice_check_out" />",
    "room.notice_check_out_date":"<spring:message code="room.notice_check_out_date" />",
    "room.create_notice_check_out":"<spring:message code="room.create_notice_check_out" />",
    "room.remove_notice":"<spring:message code="room.remove_notice" />",
    
    "electricity_water_meter.electricity_water_meter":"<spring:message code="electricity_water_meter.electricity_water_meter" />",
    "electricity_water_meter.electricity_meter":"<spring:message code="electricity_water_meter.electricity_meter" />",
    "electricity_water_meter.water_meter":"<spring:message code="electricity_water_meter.water_meter" />",
    "electricity_water_meter.previous_meter":"<spring:message code="electricity_water_meter.previous_meter" />",
    "electricity_water_meter.allow_save_previous_and_present_month_only":"<spring:message code="electricity_water_meter.allow_save_previous_and_present_month_only" />",
    
    "room.invoice":"<spring:message code="room.invoice" />",
    "room.invoice.create_room_invoice":"<spring:message code="room.invoice.create_room_invoice" />",
    "room.invoice.please_checked_room":"<spring:message code="room.invoice.please_checked_room" />",
    "room.invoice.create_invoice_for_room":"<spring:message code="room.invoice.create_invoice_for_room" />",
    "room.invoice.already_invoice":"<spring:message code="room.invoice.already_invoice" />",
    "room.invoice.not_have_meter_of_this_month":"<spring:message code="room.invoice.not_have_meter_of_this_month" />",
    "room.invoice.description":"<spring:message code="room.invoice.description" />",
    "room.invoice.price_per_unit":"<spring:message code="room.invoice.price_per_unit" />",
    "room.invoice.usage_unit":"<spring:message code="room.invoice.usage_unit" />",
    "room.invoice.value":"<spring:message code="room.invoice.value" />",
    "room.invoice.room_price":"<spring:message code="room.invoice.room_price" />",
    "room.invoice.already_receipt":"<spring:message code="room.invoice.already_receipt" />",
    "room.invoice.if_electricity_usage_unit_less_than_":"<spring:message code="room.invoice.if_electricity_usage_unit_less_than_" />",
    "room.invoice.if_water_usage_unit_less_than_":"<spring:message code="room.invoice.if_water_usage_unit_less_than_" />",
    "room.invoice.value_is_":"<spring:message code="room.invoice.value_is_" />",
    "room.invoice.invoice_no":"<spring:message code="room.invoice.invoice_no" />",
    "room.invoice.invoice_date":"<spring:message code="room.invoice.invoice_date" />",
    "room.invoice.check_in_name":"<spring:message code="room.invoice.check_in_name" />",
    "room.invoice.unpaid":"<spring:message code="room.invoice.unpaid" />",
    "room.invoice.paid":"<spring:message code="room.invoice.paid" />",
    
    "room.receipt":"<spring:message code="room.receipt" />",
    "room.room_receipt":"<spring:message code="room.room_receipt" />",
    "room.receipt_no":"<spring:message code="room.receipt_no" />",
    "room.receipt_reference_invoice_no":"<spring:message code="room.receipt_reference_invoice_no" />",
    "room.receipt_date":"<spring:message code="room.receipt_date" />"
    }
</div>
