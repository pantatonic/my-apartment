package my.apartment.model;

import java.io.Serializable;
import java.math.BigDecimal;


public class Building implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private String name;
    private String address;
    private String tel;
    private Integer electricityMeterDigit;
    private BigDecimal electricityChargePerUnit;
    private Integer minElectricityUnit;
    private BigDecimal minElectricityCharge;
    private Integer waterMeterDigit;
    private BigDecimal waterChargePerUnit;
    private Integer minWaterUnit;
    private BigDecimal minWaterCharge;

    public Building() {
    }

    public Building(Integer id, String name, String address, String tel, Integer electricityMeterDigit, BigDecimal electricityChargePerUnit, Integer minElectricityUnit, BigDecimal minElectricityCharge, Integer waterMeterDigit, BigDecimal waterChargePerUnit, Integer minWaterUnit, BigDecimal minWaterCharge) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.tel = tel;
        this.electricityMeterDigit = electricityMeterDigit;
        this.electricityChargePerUnit = electricityChargePerUnit;
        this.minElectricityUnit = minElectricityUnit;
        this.minElectricityCharge = minElectricityCharge;
        this.waterMeterDigit = waterMeterDigit;
        this.waterChargePerUnit = waterChargePerUnit;
        this.minWaterUnit = minWaterUnit;
        this.minWaterCharge = minWaterCharge;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getElectricityMeterDigit() {
        return electricityMeterDigit;
    }

    public void setElectricityMeterDigit(Integer electricityMeterDigit) {
        this.electricityMeterDigit = electricityMeterDigit;
    }

    public BigDecimal getElectricityChargePerUnit() {
        return electricityChargePerUnit;
    }

    public void setElectricityChargePerUnit(BigDecimal electricityChargePerUnit) {
        this.electricityChargePerUnit = electricityChargePerUnit;
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

    public Integer getWaterMeterDigit() {
        return waterMeterDigit;
    }

    public void setWaterMeterDigit(Integer waterMeterDigit) {
        this.waterMeterDigit = waterMeterDigit;
    }

    public BigDecimal getWaterChargePerUnit() {
        return waterChargePerUnit;
    }

    public void setWaterChargePerUnit(BigDecimal waterChargePerUnit) {
        this.waterChargePerUnit = waterChargePerUnit;
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

    @Override
    public String toString() {
        return "Building{" + "id=" + id + ", name=" + name + ", address=" + address + ", tel=" + tel + ", electricityMeterDigit=" + electricityMeterDigit + ", electricityChargePerUnit=" + electricityChargePerUnit + ", minElectricityUnit=" + minElectricityUnit + ", minElectricityCharge=" + minElectricityCharge + ", waterMeterDigit=" + waterMeterDigit + ", waterChargePerUnit=" + waterChargePerUnit + ", minWaterUnit=" + minWaterUnit + ", minWaterCharge=" + minWaterCharge + '}';
    }

}
