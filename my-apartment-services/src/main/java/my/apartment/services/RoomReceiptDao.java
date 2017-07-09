package my.apartment.services;

import java.util.List;
import my.apartment.model.RoomReceipt;
import my.apartment.model.RoomReceiptPdf;


public interface RoomReceiptDao {
    
    public Boolean create(RoomReceipt roomReceipt);
    
    public Boolean isAreadyReceiptOfInvoice(Integer roomInvoiceId);
    
    public Boolean cancel(RoomReceipt roomInvoice);
    
    public List<RoomReceiptPdf> getById(Integer roomInvoiceId);
    
}
