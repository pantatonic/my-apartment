package my.apartment.services;

import java.math.BigInteger;
import java.util.List;
import my.apartment.model.Test;


public interface TestDao {

    public List<Test> getAllTests();
    
    public List<Test> getTestById(BigInteger id);
    
}
