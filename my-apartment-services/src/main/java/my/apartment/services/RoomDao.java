package my.apartment.services;

import java.util.List;
import my.apartment.model.Room;
import my.apartment.model.RoomNoticeCheckOut;


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
    
}
