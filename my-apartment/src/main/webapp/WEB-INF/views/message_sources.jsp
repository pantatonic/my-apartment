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
    
    "common.person_name":"<spring:message code="common.person_name" />",
    "common.person_lastname":"<spring:message code="common.person_lastname" />",
    "common.remark":"<spring:message code="common.remark" />",
    "common.status":"<spring:message code="common.status" />",
    
    "account_not_found":"<spring:message code="account_not_found" />",
    "account_is_disabled":"<spring:message code="account_is_disabled" />",
    
    "dashboard.dashboard.page_title":"<spring:message code="dashboard.dashboard.page_title" />",
    "dashboard.dashboard.page_sub_title":"<spring:message code="dashboard.dashboard.page_sub_title" />",
    
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
    "room.check_in_date":"<spring:message code="room.check_in_date" />",
    "room.create_check_in":"<spring:message code="room.create_check_in" />",
    "room.check_out":"<spring:message code="room.check_out" />",
    "room.check_out_date":"<spring:message code="room.check_out_date" />",
    
    "room.notice_check_out":"<spring:message code="room.notice_check_out" />",
    "room.notice_check_out_date":"<spring:message code="room.notice_check_out_date" />",
    "room.create_notice_check_out":"<spring:message code="room.create_notice_check_out" />",
    "room.remove_notice":"<spring:message code="room.remove_notice" />"
    }
</div>
