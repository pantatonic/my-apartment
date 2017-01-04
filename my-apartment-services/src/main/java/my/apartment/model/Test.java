package my.apartment.model;

import java.math.BigInteger;


public class Test {
    
    private BigInteger id;
    private String name;
    private String surname;

    public Test() {
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "Test{" + "id=" + id + ", name=" + name + ", surname=" + surname + '}';
    }
    
}
