package my.apartment.model;

import java.io.Serializable;
import java.math.BigDecimal;


public class Room implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private Integer buildingId;
    private Integer floorSeq;
    private String roomNo;
    private String name;
    private BigDecimal pricePerMonth;
    private Integer roomStatusId;
    private String startupElectricityMeter;
    private String startupWaterMeter;
    
    private String roomStatusText;
    private Integer electricityMeterDigit;
    private Integer waterMeterDigit;

    public Room() {
    }

    public Room(Integer id, Integer buildingId, Integer floorSeq, String roomNo, String name, BigDecimal pricePerMonth, Integer roomStatusId, String startupElectricityMeter, String startupWaterMeter, String roomStatusText, Integer electricityMeterDigit, Integer waterMeterDigit) {
        this.id = id;
        this.buildingId = buildingId;
        this.floorSeq = floorSeq;
        this.roomNo = roomNo;
        this.name = name;
        this.pricePerMonth = pricePerMonth;
        this.roomStatusId = roomStatusId;
        this.startupElectricityMeter = startupElectricityMeter;
        this.startupWaterMeter = startupWaterMeter;
        this.roomStatusText = roomStatusText;
        this.electricityMeterDigit = electricityMeterDigit;
        this.waterMeterDigit = waterMeterDigit;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(Integer buildingId) {
        this.buildingId = buildingId;
    }

    public Integer getFloorSeq() {
        return floorSeq;
    }

    public void setFloorSeq(Integer floorSeq) {
        this.floorSeq = floorSeq;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPricePerMonth() {
        return pricePerMonth;
    }

    public void setPricePerMonth(BigDecimal pricePerMonth) {
        this.pricePerMonth = pricePerMonth;
    }

    public Integer getRoomStatusId() {
        return roomStatusId;
    }

    public void setRoomStatusId(Integer roomStatusId) {
        this.roomStatusId = roomStatusId;
    }

    public String getStartupElectricityMeter() {
        return startupElectricityMeter;
    }

    public void setStartupElectricityMeter(String startupElectricityMeter) {
        this.startupElectricityMeter = startupElectricityMeter;
    }

    public String getStartupWaterMeter() {
        return startupWaterMeter;
    }

    public void setStartupWaterMeter(String startupWaterMeter) {
        this.startupWaterMeter = startupWaterMeter;
    }

    public String getRoomStatusText() {
        return roomStatusText;
    }

    public void setRoomStatusText(String roomStatusText) {
        this.roomStatusText = roomStatusText;
    }

    public Integer getElectricityMeterDigit() {
        return electricityMeterDigit;
    }

    public void setElectricityMeterDigit(Integer electricityMeterDigit) {
        this.electricityMeterDigit = electricityMeterDigit;
    }

    public Integer getWaterMeterDigit() {
        return waterMeterDigit;
    }

    public void setWaterMeterDigit(Integer waterMeterDigit) {
        this.waterMeterDigit = waterMeterDigit;
    }

    @Override
    public String toString() {
        return "Room{" + "id=" + id + ", buildingId=" + buildingId + ", floorSeq=" + floorSeq + ", roomNo=" + roomNo + ", name=" + name + ", pricePerMonth=" + pricePerMonth + ", roomStatusId=" + roomStatusId + ", startupElectricityMeter=" + startupElectricityMeter + ", startupWaterMeter=" + startupWaterMeter + ", roomStatusText=" + roomStatusText + ", electricityMeterDigit=" + electricityMeterDigit + ", waterMeterDigit=" + waterMeterDigit + '}';
    }
    
}
