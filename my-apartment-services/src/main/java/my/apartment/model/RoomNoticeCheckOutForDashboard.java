package my.apartment.model;

import java.io.Serializable;
import java.util.Date;


public class RoomNoticeCheckOutForDashboard implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer roomId;
    private Date noticeCheckOutDate;
    private String noticeCheckOutDateString;
    private String remark;
    private Date createdDate;
    private String createdDateString;
    private Date updatedDate;
    private String updatedDateString;
    
    private String roomNo;
    private Integer buildingId;
    private String buildingName;

    public RoomNoticeCheckOutForDashboard() {
    }

    public RoomNoticeCheckOutForDashboard(Integer roomId, Date noticeCheckOutDate, String noticeCheckOutDateString, String remark, Date createdDate, String createdDateString, Date updatedDate, String updatedDateString, String roomNo, Integer buildingId, String buildingName) {
        this.roomId = roomId;
        this.noticeCheckOutDate = noticeCheckOutDate;
        this.noticeCheckOutDateString = noticeCheckOutDateString;
        this.remark = remark;
        this.createdDate = createdDate;
        this.createdDateString = createdDateString;
        this.updatedDate = updatedDate;
        this.updatedDateString = updatedDateString;
        this.roomNo = roomNo;
        this.buildingId = buildingId;
        this.buildingName = buildingName;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Date getNoticeCheckOutDate() {
        return noticeCheckOutDate;
    }

    public void setNoticeCheckOutDate(Date noticeCheckOutDate) {
        this.noticeCheckOutDate = noticeCheckOutDate;
    }

    public String getNoticeCheckOutDateString() {
        return noticeCheckOutDateString;
    }

    public void setNoticeCheckOutDateString(String noticeCheckOutDateString) {
        this.noticeCheckOutDateString = noticeCheckOutDateString;
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

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
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

    @Override
    public String toString() {
        return "RoomNoticeCheckOutForDashboard{" + "roomId=" + roomId + ", noticeCheckOutDate=" + noticeCheckOutDate + ", noticeCheckOutDateString=" + noticeCheckOutDateString + ", remark=" + remark + ", createdDate=" + createdDate + ", createdDateString=" + createdDateString + ", updatedDate=" + updatedDate + ", updatedDateString=" + updatedDateString + ", roomNo=" + roomNo + ", buildingId=" + buildingId + ", buildingName=" + buildingName + '}';
    }
    
}
