package my.apartment.model;

import java.io.Serializable;
import java.util.Date;


public class RoomNoticeCheckOut implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer roomId;
    private Date noticeCheckOutDate;
    private String noticeCheckOutDateString;
    private String remark;
    private Date createdDate;
    private String createdDateString;
    private Date updatedDate;
    private String updatedDateString;

    public RoomNoticeCheckOut() {
    }

    public RoomNoticeCheckOut(Integer roomId, Date noticeCheckOutDate, String noticeCheckOutDateString, String remark, Date createdDate, String createdDateString, Date updatedDate, String updatedDateString) {
        this.roomId = roomId;
        this.noticeCheckOutDate = noticeCheckOutDate;
        this.noticeCheckOutDateString = noticeCheckOutDateString;
        this.remark = remark;
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

    @Override
    public String toString() {
        return "NoticeCheckOut{" + "roomId=" + roomId + ", noticeCheckOutDate=" + noticeCheckOutDate + ", noticeCheckOutDateString=" + noticeCheckOutDateString + ", remark=" + remark + ", createdDate=" + createdDate + ", createdDateString=" + createdDateString + ", updatedDate=" + updatedDate + ", updatedDateString=" + updatedDateString + '}';
    }
    
}
