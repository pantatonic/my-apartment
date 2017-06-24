package my.apartment.services;

import java.util.List;
import my.apartment.model.RoomInvoice;


public interface RoomInvoiceDao {
    
    public Boolean create(RoomInvoice roomInvoice);
    
    public List<RoomInvoice> getRoomInvoiceByRoomIdMonthYear(Integer roomId, Integer month, Integer year);
    
    public List<RoomInvoice> getRoomInvoiceMonthYear(Integer buildingId, Integer month, Integer year);
    
    public Boolean cancel(RoomInvoice roomInvoice);
    
}
