package my.apartment.services;

import java.math.BigDecimal;
import java.util.List;
import my.apartment.model.ElectricityMeter;
import my.apartment.model.Room;
import my.apartment.model.RoomForDashboard;
import my.apartment.model.RoomNoticeCheckOut;
import my.apartment.model.RoomNoticeCheckOutForDashboard;
import my.apartment.model.WaterMeter;


public interface RoomDao {
    
    public List<Room> getByBuildingId(Integer buildingId);
    
    public List<Room> getById(Integer roomId);
    
    public Room save(Room room);
    
    public Boolean checkRoomNoDuplicated(Room room);
    
    public Boolean deleteById(Integer roomId);
    
    public Boolean checkOut(Integer roomId, String numberCode);
    
    public List<RoomNoticeCheckOut> getCurrentNoticeCheckOut();
    
    public List<RoomNoticeCheckOut> getCurrentNoticeCheckOutByRoomId(Integer roomId);
    
    public RoomNoticeCheckOut saveNoticeCheckOut(RoomNoticeCheckOut roomNoticeCheckOut);
    
    public Boolean removeNoticeCheckOut(Integer roomId);
    
    public List<ElectricityMeter> getElectricityMeterByBuildingIdMonthYear(Integer buildingId, Integer month, Integer year);
    
    public List<ElectricityMeter> getElectricityMeterByRoomIdMonthYear(Integer roomId, Integer month, Integer year);
    
    public List<WaterMeter> getWaterMeterByBuildingIdMonthYear(Integer buildingId, Integer month, Integer year);
    
    public List<WaterMeter> getWaterMeterByRoomIdMonthYear(Integer roomId, Integer month, Integer year);
    
    public BigDecimal getElectricityChargePerUnitByRoomId(Integer roomId);
    
    public Boolean getIsUseElectricityMinimunUnitCalculateByRoomId(Integer roomId);
    
    public BigDecimal getWaterChargePerUnitByRoomId(Integer roomId);
    
    public Boolean getIsUseWaterMinimunUnitCalculateByRoomId(Integer roomId);
    
    public List<RoomNoticeCheckOutForDashboard> getCurrentNoticeCheckOutByBuildingId(Integer buildingId);
    
    public Boolean checkRoomHaveElectricityAndWaterMeter(Integer roomId);
    
    public List<RoomForDashboard> getRoomDataByBuilding(Integer buildingId);
    
}
