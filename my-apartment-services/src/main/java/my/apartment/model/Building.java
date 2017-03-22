package my.apartment.model;

import java.io.Serializable;
import java.math.BigDecimal;


public class Building implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private String name;
    private String address;
    private String tel;
    private BigDecimal electricityChargePerUnit;
    private Integer minElectricityUnit;
    private BigDecimal minElectricityCharge;
    private BigDecimal waterChargePerUnit;
    private Integer minWaterUnit;
    private BigDecimal minWaterCharge;

    public Building() {
    }

    public Building(Integer id, String name, String address, String tel, BigDecimal electricityChargePerUnit, Integer minElectricityUnit, BigDecimal minElectricityCharge, BigDecimal waterChargePerUnit, Integer minWaterUnit, BigDecimal minWaterCharge) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.tel = tel;
        this.electricityChargePerUnit = electricityChargePerUnit;
        this.minElectricityUnit = minElectricityUnit;
        this.minElectricityCharge = minElectricityCharge;
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
        return "Buildings{" + "id=" + id + ", name=" + name + ", address=" + address + ", tel=" + tel + ", electricityChargePerUnit=" + electricityChargePerUnit + ", minElectricityUnit=" + minElectricityUnit + ", minElectricityCharge=" + minElectricityCharge + ", waterChargePerUnit=" + waterChargePerUnit + ", minWaterUnit=" + minWaterUnit + ", minWaterCharge=" + minWaterCharge + '}';
    }

}
