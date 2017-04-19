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
    private Date updatedDate;
    private Integer status;
    
    private String roomNo;

    public RoomReservation() {
    }

    public RoomReservation(Integer id, Date reserveDate, String reserveDateString, Date reserveExpired, String reserveExpiredString, Integer roomId, String idCard, String reserveName, String reserveLastname, String remark, Date createdDate, Date updatedDate, Integer status, String roomNo) {
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
        this.updatedDate = updatedDate;
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

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
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
        return "RoomReservation{" + "id=" + id + ", reserveDate=" + reserveDate + ", reserveDateString=" + reserveDateString + ", reserveExpired=" + reserveExpired + ", reserveExpiredString=" + reserveExpiredString + ", roomId=" + roomId + ", idCard=" + idCard + ", reserveName=" + reserveName + ", reserveLastname=" + reserveLastname + ", remark=" + remark + ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", status=" + status + ", roomNo=" + roomNo + '}';
    }
    
}
