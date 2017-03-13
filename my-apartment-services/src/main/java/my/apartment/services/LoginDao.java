package my.apartment.services;

import java.util.List;
import my.apartment.model.User;

public interface LoginDao {
    
    public List<User> loginProcess(User user);
    
}
