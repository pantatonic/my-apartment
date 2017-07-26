package my.apartment.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class RoomReceipt implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private String receiptNo;
    private Integer invoiceId;
    private String payer;
    private Integer status;
    private String description;
    private Date createdDate;
    private String createdDateString;
    private Date updatedDate;
    private String updatedDateString;
    
    private BigDecimal roomPricePerMonth;
    private BigDecimal electricityValue;
    private BigDecimal waterValue ;

    public RoomReceipt() {
    }

    public RoomReceipt(Integer id, String receiptNo, Integer invoiceId, String payer, Integer status, String description, Date createdDate, String createdDateString, Date updatedDate, String updatedDateString, BigDecimal roomPricePerMonth, BigDecimal electricityValue, BigDecimal waterValue) {
        this.id = id;
        this.receiptNo = receiptNo;
        this.invoiceId = invoiceId;
        this.payer = payer;
        this.status = status;
        this.description = description;
        this.createdDate = createdDate;
        this.createdDateString = createdDateString;
        this.updatedDate = updatedDate;
        this.updatedDateString = updatedDateString;
        this.roomPricePerMonth = roomPricePerMonth;
        this.electricityValue = electricityValue;
        this.waterValue = waterValue;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public BigDecimal getRoomPricePerMonth() {
        return roomPricePerMonth;
    }

    public void setRoomPricePerMonth(BigDecimal roomPricePerMonth) {
        this.roomPricePerMonth = roomPricePerMonth;
    }

    public BigDecimal getElectricityValue() {
        return electricityValue;
    }

    public void setElectricityValue(BigDecimal electricityValue) {
        this.electricityValue = electricityValue;
    }

    public BigDecimal getWaterValue() {
        return waterValue;
    }

    public void setWaterValue(BigDecimal waterValue) {
        this.waterValue = waterValue;
    }

    @Override
    public String toString() {
        return "RoomReceipt{" + "id=" + id + ", receiptNo=" + receiptNo + ", invoiceId=" + invoiceId + ", payer=" + payer + ", status=" + status + ", description=" + description + ", createdDate=" + createdDate + ", createdDateString=" + createdDateString + ", updatedDate=" + updatedDate + ", updatedDateString=" + updatedDateString + ", roomPricePerMonth=" + roomPricePerMonth + ", electricityValue=" + electricityValue + ", waterValue=" + waterValue + '}';
    }

}
