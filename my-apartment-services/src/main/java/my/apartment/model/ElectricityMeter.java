package my.apartment.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class ElectricityMeter implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer roomId;
    private Integer month;
    private Integer year;
    private String previousMeter;
    private String presentMeter;
    private BigDecimal chargePerUnit;
    private Integer usageUnit;
    private BigDecimal value;
    private Boolean useMinimunUnitCalculate;
    private Date createdDate;
    private String createdDateString;
    private Date updatedDate;
    private String updatedDateString;

    public ElectricityMeter() {
    }

    public ElectricityMeter(Integer roomId, Integer month, Integer year, String previousMeter, String presentMeter, BigDecimal chargePerUnit, Integer usageUnit, BigDecimal value, Boolean useMinimunUnitCalculate, Date createdDate, String createdDateString, Date updatedDate, String updatedDateString) {
        this.roomId = roomId;
        this.month = month;
        this.year = year;
        this.previousMeter = previousMeter;
        this.presentMeter = presentMeter;
        this.chargePerUnit = chargePerUnit;
        this.usageUnit = usageUnit;
        this.value = value;
        this.useMinimunUnitCalculate = useMinimunUnitCalculate;
        this.createdDate = createdDate;
        this.createdDateString = createdDateString;
        this.updatedDate = updatedDate;
        this.updatedDateString = updatedDateString;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getPreviousMeter() {
        return previousMeter;
    }

    public void setPreviousMeter(String previousMeter) {
        this.previousMeter = previousMeter;
    }

    public String getPresentMeter() {
        return presentMeter;
    }

    public void setPresentMeter(String presentMeter) {
        this.presentMeter = presentMeter;
    }

    public BigDecimal getChargePerUnit() {
        return chargePerUnit;
    }

    public void setChargePerUnit(BigDecimal chargePerUnit) {
        this.chargePerUnit = chargePerUnit;
    }

    public Integer getUsageUnit() {
        return usageUnit;
    }

    public void setUsageUnit(Integer usageUnit) {
        this.usageUnit = usageUnit;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Boolean getUseMinimunUnitCalculate() {
        return useMinimunUnitCalculate;
    }

    public void setUseMinimunUnitCalculate(Boolean useMinimunUnitCalculate) {
        this.useMinimunUnitCalculate = useMinimunUnitCalculate;
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
        return "ElectricityMeter{" + "roomId=" + roomId + ", month=" + month + ", year=" + year + ", previousMeter=" + previousMeter + ", presentMeter=" + presentMeter + ", chargePerUnit=" + chargePerUnit + ", usageUnit=" + usageUnit + ", value=" + value + ", useMinimunUnitCalculate=" + useMinimunUnitCalculate + ", createdDate=" + createdDate + ", createdDateString=" + createdDateString + ", updatedDate=" + updatedDate + ", updatedDateString=" + updatedDateString + '}';
    }
    
}
