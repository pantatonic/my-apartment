package my.apartment.services;

import java.util.List;
import my.apartment.model.Users;

public interface LoginDao {
    
    public List<Users> loginProcess(Users user);
    
}
