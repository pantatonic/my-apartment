package my.apartment.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class RoomReceiptPdf implements Serializable {
    
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
    
    private String invoiceNo;
    private Integer roomId;
    private BigDecimal roomPricePerMonth;
    private String electricityPreviousMeter;
    private String electricityPresentMeter;
    private BigDecimal electricityChargePerUnit;
    private Integer electricityUsageUnit;
    private BigDecimal electricityValue;
    private Boolean electricityUseMinimunUnitCalculate;
    private String waterPreviousMeter;
    private String waterPresentMeter;
    private BigDecimal waterChargePerUnit;
    private Integer waterUsageUnit;
    private BigDecimal waterValue;
    private Boolean waterUseMinimunUnitCalculate;
    
    private Integer buildingId;
    private String buildingName;
    private String buildingAddress;
    private String buildingTel;
    
    private Integer minElectricityUnit;
    private BigDecimal minElectricityCharge;
    private Integer minWaterUnit;
    private BigDecimal minWaterCharge;
    
    private String roomNo;

    public RoomReceiptPdf() {
    }

    public RoomReceiptPdf(Integer id, String receiptNo, Integer invoiceId, String payer, Integer status, String description, Date createdDate, String createdDateString, Date updatedDate, String updatedDateString, String invoiceNo, Integer roomId, BigDecimal roomPricePerMonth, String electricityPreviousMeter, String electricityPresentMeter, BigDecimal electricityChargePerUnit, Integer electricityUsageUnit, BigDecimal electricityValue, Boolean electricityUseMinimunUnitCalculate, String waterPreviousMeter, String waterPresentMeter, BigDecimal waterChargePerUnit, Integer waterUsageUnit, BigDecimal waterValue, Boolean waterUseMinimunUnitCalculate, Integer buildingId, String buildingName, String buildingAddress, String buildingTel, Integer minElectricityUnit, BigDecimal minElectricityCharge, Integer minWaterUnit, BigDecimal minWaterCharge, String roomNo) {
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
        this.invoiceNo = invoiceNo;
        this.roomId = roomId;
        this.roomPricePerMonth = roomPricePerMonth;
        this.electricityPreviousMeter = electricityPreviousMeter;
        this.electricityPresentMeter = electricityPresentMeter;
        this.electricityChargePerUnit = electricityChargePerUnit;
        this.electricityUsageUnit = electricityUsageUnit;
        this.electricityValue = electricityValue;
        this.electricityUseMinimunUnitCalculate = electricityUseMinimunUnitCalculate;
        this.waterPreviousMeter = waterPreviousMeter;
        this.waterPresentMeter = waterPresentMeter;
        this.waterChargePerUnit = waterChargePerUnit;
        this.waterUsageUnit = waterUsageUnit;
        this.waterValue = waterValue;
        this.waterUseMinimunUnitCalculate = waterUseMinimunUnitCalculate;
        this.buildingId = buildingId;
        this.buildingName = buildingName;
        this.buildingAddress = buildingAddress;
        this.buildingTel = buildingTel;
        this.minElectricityUnit = minElectricityUnit;
        this.minElectricityCharge = minElectricityCharge;
        this.minWaterUnit = minWaterUnit;
        this.minWaterCharge = minWaterCharge;
        this.roomNo = roomNo;
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

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public BigDecimal getRoomPricePerMonth() {
        return roomPricePerMonth;
    }

    public void setRoomPricePerMonth(BigDecimal roomPricePerMonth) {
        this.roomPricePerMonth = roomPricePerMonth;
    }

    public String getElectricityPreviousMeter() {
        return electricityPreviousMeter;
    }

    public void setElectricityPreviousMeter(String electricityPreviousMeter) {
        this.electricityPreviousMeter = electricityPreviousMeter;
    }

    public String getElectricityPresentMeter() {
        return electricityPresentMeter;
    }

    public void setElectricityPresentMeter(String electricityPresentMeter) {
        this.electricityPresentMeter = electricityPresentMeter;
    }

    public BigDecimal getElectricityChargePerUnit() {
        return electricityChargePerUnit;
    }

    public void setElectricityChargePerUnit(BigDecimal electricityChargePerUnit) {
        this.electricityChargePerUnit = electricityChargePerUnit;
    }

    public Integer getElectricityUsageUnit() {
        return electricityUsageUnit;
    }

    public void setElectricityUsageUnit(Integer electricityUsageUnit) {
        this.electricityUsageUnit = electricityUsageUnit;
    }

    public BigDecimal getElectricityValue() {
        return electricityValue;
    }

    public void setElectricityValue(BigDecimal electricityValue) {
        this.electricityValue = electricityValue;
    }

    public Boolean getElectricityUseMinimunUnitCalculate() {
        return electricityUseMinimunUnitCalculate;
    }

    public void setElectricityUseMinimunUnitCalculate(Boolean electricityUseMinimunUnitCalculate) {
        this.electricityUseMinimunUnitCalculate = electricityUseMinimunUnitCalculate;
    }

    public String getWaterPreviousMeter() {
        return waterPreviousMeter;
    }

    public void setWaterPreviousMeter(String waterPreviousMeter) {
        this.waterPreviousMeter = waterPreviousMeter;
    }

    public String getWaterPresentMeter() {
        return waterPresentMeter;
    }

    public void setWaterPresentMeter(String waterPresentMeter) {
        this.waterPresentMeter = waterPresentMeter;
    }

    public BigDecimal getWaterChargePerUnit() {
        return waterChargePerUnit;
    }

    public void setWaterChargePerUnit(BigDecimal waterChargePerUnit) {
        this.waterChargePerUnit = waterChargePerUnit;
    }

    public Integer getWaterUsageUnit() {
        return waterUsageUnit;
    }

    public void setWaterUsageUnit(Integer waterUsageUnit) {
        this.waterUsageUnit = waterUsageUnit;
    }

    public BigDecimal getWaterValue() {
        return waterValue;
    }

    public void setWaterValue(BigDecimal waterValue) {
        this.waterValue = waterValue;
    }

    public Boolean getWaterUseMinimunUnitCalculate() {
        return waterUseMinimunUnitCalculate;
    }

    public void setWaterUseMinimunUnitCalculate(Boolean waterUseMinimunUnitCalculate) {
        this.waterUseMinimunUnitCalculate = waterUseMinimunUnitCalculate;
    }

    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getBuildingAddress() {
        return buildingAddress;
    }

    public void setBuildingAddress(String buildingAddress) {
        this.buildingAddress = buildingAddress;
    }

    public String getBuildingTel() {
        return buildingTel;
    }

    public void setBuildingTel(String buildingTel) {
        this.buildingTel = buildingTel;
    }

    public Integer getMinElectricityUnit() {
        return minElectricityUnit;
    }

    public void setMinElectricityUnit(Integer minElectricityUnit) {
        this.minElectricityUnit = minElectricityUnit;
    }

    public BigDecimal getMinElectricityCharge() {
        return minElectricityCharge;
    }

    public void setMinElectricityCharge(BigDecimal minElectricityCharge) {
        this.minElectricityCharge = minElectricityCharge;
    }

    public Integer getMinWaterUnit() {
        return minWaterUnit;
    }

    public void setMinWaterUnit(Integer minWaterUnit) {
        this.minWaterUnit = minWaterUnit;
    }

    public BigDecimal getMinWaterCharge() {
        return minWaterCharge;
    }

    public void setMinWaterCharge(BigDecimal minWaterCharge) {
        this.minWaterCharge = minWaterCharge;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    @Override
    public String toString() {
        return "RoomReceiptPdf{" + "id=" + id + ", receiptNo=" + receiptNo + ", invoiceId=" + invoiceId + ", payer=" + payer + ", status=" + status + ", description=" + description + ", createdDate=" + createdDate + ", createdDateString=" + createdDateString + ", updatedDate=" + updatedDate + ", updatedDateString=" + updatedDateString + ", invoiceNo=" + invoiceNo + ", roomId=" + roomId + ", roomPricePerMonth=" + roomPricePerMonth + ", electricityPreviousMeter=" + electricityPreviousMeter + ", electricityPresentMeter=" + electricityPresentMeter + ", electricityChargePerUnit=" + electricityChargePerUnit + ", electricityUsageUnit=" + electricityUsageUnit + ", electricityValue=" + electricityValue + ", electricityUseMinimunUnitCalculate=" + electricityUseMinimunUnitCalculate + ", waterPreviousMeter=" + waterPreviousMeter + ", waterPresentMeter=" + waterPresentMeter + ", waterChargePerUnit=" + waterChargePerUnit + ", waterUsageUnit=" + waterUsageUnit + ", waterValue=" + waterValue + ", waterUseMinimunUnitCalculate=" + waterUseMinimunUnitCalculate + ", buildingId=" + buildingId + ", buildingName=" + buildingName + ", buildingAddress=" + buildingAddress + ", buildingTel=" + buildingTel + ", minElectricityUnit=" + minElectricityUnit + ", minElectricityCharge=" + minElectricityCharge + ", minWaterUnit=" + minWaterUnit + ", minWaterCharge=" + minWaterCharge + ", roomNo=" + roomNo + '}';
    }

}
