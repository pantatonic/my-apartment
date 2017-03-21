package my.apartment.common;

import org.springframework.util.MultiValueMap;


public class CommonUtils {
    
    public static String getFormData(MultiValueMap formData) {
        System.out.println("aaa");
        System.out.println(formData);
        System.out.println("aaa");
        return "xxx";
    }
    
}
