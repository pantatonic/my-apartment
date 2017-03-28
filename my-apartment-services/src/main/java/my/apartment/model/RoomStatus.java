package my.apartment.model;

import java.io.Serializable;


public class RoomStatus implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private String status;

    public RoomStatus() {
    }

    public RoomStatus(Integer id, String status) {
        this.id = id;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "RoomStatus{" + "id=" + id + ", status=" + status + '}';
    }
    
}
