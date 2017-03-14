package my.apartment.model;

import java.io.Serializable;

public class Users implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private Integer isAdmin;
    private Integer status;

    public Users() {
    }

    public Users(Integer id, String firstname, String lastname, String email, String password, Integer isAdmin, Integer status) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.isAdmin = isAdmin;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Integer isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    

    @Override
    public String toString() {
        return "Users{" + "id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + ", password=" + password + ", isAdmin=" + isAdmin + ", status=" + status + '}';
    }
    
}
