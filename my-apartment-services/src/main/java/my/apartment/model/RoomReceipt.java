package my.apartment.model;

import java.io.Serializable;
import java.util.Date;


public class RoomReceipt implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private String receiptNo;
    private Integer invoiceId;
    private String payer;
    private Integer status;
    private Date createdDate;
    private String createdDateString;
    private Date updatedDate;
    private String updatedDateString;

    public RoomReceipt() {
    }

    public RoomReceipt(Integer id, String receiptNo, Integer invoiceId, String payer, Integer status, Date createdDate, String createdDateString, Date updatedDate, String updatedDateString) {
        this.id = id;
        this.receiptNo = receiptNo;
        this.invoiceId = invoiceId;
        this.payer = payer;
        this.status = status;
        this.createdDate = createdDate;
        this.createdDateString = createdDateString;
        this.updatedDate = updatedDate;
        this.updatedDateString = updatedDateString;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public Integer getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Integer invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedDateString() {
        return createdDateString;
    }

    public void setCreatedDateString(String createdDateString) {
        this.createdDateString = createdDateString;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedDateString() {
        return updatedDateString;
    }

    public void setUpdatedDateString(String updatedDateString) {
        this.updatedDateString = updatedDateString;
    }

    @Override
    public String toString() {
        return "RoomReceipt{" + "id=" + id + ", receiptNo=" + receiptNo + ", invoiceId=" + invoiceId + ", payer=" + payer + ", status=" + status + ", createdDate=" + createdDate + ", createdDateString=" + createdDateString + ", updatedDate=" + updatedDate + ", updatedDateString=" + updatedDateString + '}';
    }

}
