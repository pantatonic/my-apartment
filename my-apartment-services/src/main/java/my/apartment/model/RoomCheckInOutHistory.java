package my.apartment.model;

import java.io.Serializable;
import java.util.Date;


public class RoomCheckInOutHistory implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private Integer roomId;
    private Date checkInDate;
    private String checkInDateString;
    private String idCard;
    private String name;
    private String lastname;
    private String address;
    private String remark;
    private String numberCode;
    private Date createdDate;
    private String createdDateString;
    private Date updatedDate;
    private String updatedDateString;

    public RoomCheckInOutHistory() {
    }

    public RoomCheckInOutHistory(Integer id, Integer roomId, Date checkInDate, String checkInDateString, String idCard, String name, String lastname, String address, String remark, String numberCode, Date createdDate, String createdDateString, Date updatedDate, String updatedDateString) {
        this.id = id;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkInDateString = checkInDateString;
        this.idCard = idCard;
        this.name = name;
        this.lastname = lastname;
        this.address = address;
        this.remark = remark;
        this.numberCode = numberCode;
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

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public String getCheckInDateString() {
        return checkInDateString;
    }

    public void setCheckInDateString(String checkInDateString) {
        this.checkInDateString = checkInDateString;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getNumberCode() {
        return numberCode;
    }

    public void setNumberCode(String numberCode) {
        this.numberCode = numberCode;
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
        return "RoomCheckInOutHistory{" + "id=" + id + ", roomId=" + roomId + ", checkInDate=" + checkInDate + ", checkInDateString=" + checkInDateString + ", idCard=" + idCard + ", name=" + name + ", lastname=" + lastname + ", address=" + address + ", remark=" + remark + ", numberCode=" + numberCode + ", createdDate=" + createdDate + ", createdDateString=" + createdDateString + ", updatedDate=" + updatedDate + ", updatedDateString=" + updatedDateString + '}';
    }

}
