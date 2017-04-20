package my.apartment.model;

import java.io.Serializable;
import java.util.Date;


public class RoomReservation implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private Date reserveDate;
    private String reserveDateString;
    private Date reserveExpired;
    private String reserveExpiredString;
    private Integer roomId;
    private String idCard;
    private String reserveName;
    private String reserveLastname;
    private String remark;
    private Date createdDate;
    private String createdDateString;
    private Date updatedDate;
    private String updatedDateString;
    private Integer status;
    
    private String roomNo;

    public RoomReservation() {
    }

    public RoomReservation(Integer id, Date reserveDate, String reserveDateString, Date reserveExpired, String reserveExpiredString, Integer roomId, String idCard, String reserveName, String reserveLastname, String remark, Date createdDate, String createdDateString, Date updatedDate, String updatedDateString, Integer status, String roomNo) {
        this.id = id;
        this.reserveDate = reserveDate;
        this.reserveDateString = reserveDateString;
        this.reserveExpired = reserveExpired;
        this.reserveExpiredString = reserveExpiredString;
        this.roomId = roomId;
        this.idCard = idCard;
        this.reserveName = reserveName;
        this.reserveLastname = reserveLastname;
        this.remark = remark;
        this.createdDate = createdDate;
        this.createdDateString = createdDateString;
        this.updatedDate = updatedDate;
        this.updatedDateString = updatedDateString;
        this.status = status;
        this.roomNo = roomNo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getReserveDate() {
        return reserveDate;
    }

    public void setReserveDate(Date reserveDate) {
        this.reserveDate = reserveDate;
    }

    public String getReserveDateString() {
        return reserveDateString;
    }

    public void setReserveDateString(String reserveDateString) {
        this.reserveDateString = reserveDateString;
    }

    public Date getReserveExpired() {
        return reserveExpired;
    }

    public void setReserveExpired(Date reserveExpired) {
        this.reserveExpired = reserveExpired;
    }

    public String getReserveExpiredString() {
        return reserveExpiredString;
    }

    public void setReserveExpiredString(String reserveExpiredString) {
        this.reserveExpiredString = reserveExpiredString;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getReserveName() {
        return reserveName;
    }

    public void setReserveName(String reserveName) {
        this.reserveName = reserveName;
    }

    public String getReserveLastname() {
        return reserveLastname;
    }

    public void setReserveLastname(String reserveLastname) {
        this.reserveLastname = reserveLastname;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    @Override
    public String toString() {
        return "RoomReservation{" + "id=" + id + ", reserveDate=" + reserveDate + ", reserveDateString=" + reserveDateString + ", reserveExpired=" + reserveExpired + ", reserveExpiredString=" + reserveExpiredString + ", roomId=" + roomId + ", idCard=" + idCard + ", reserveName=" + reserveName + ", reserveLastname=" + reserveLastname + ", remark=" + remark + ", createdDate=" + createdDate + ", createdDateString=" + createdDateString + ", updatedDate=" + updatedDate + ", updatedDateString=" + updatedDateString + ", status=" + status + ", roomNo=" + roomNo + '}';
    }
    
}
