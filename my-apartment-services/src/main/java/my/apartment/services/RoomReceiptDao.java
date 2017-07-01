package my.apartment.services;

import my.apartment.model.RoomReceipt;


public interface RoomReceiptDao {
    
    public Boolean create(RoomReceipt roomReceipt);
    
    public Boolean isAreadyReceiptOfInvoice(Integer roomInvoiceId);
    
    public Boolean cancel(RoomReceipt roomInvoice);
    
}
