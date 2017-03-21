package my.apartment.common;


public class CommonHtml {
    
    private final String fontAddClass = "<i class=\"fa fa-plus\"></i>";
    private final String classButtonAdd = "btn btn-warning btn-flat add-button";
    
  
    public String getAddButton(String textHtml) {
        return "<button type=\"button\" class=\"" + this.classButtonAdd + "\">"
                    + this.fontAddClass + " "
                    + textHtml 
                + "</button>";
    }
    
    public String getAddButton(String textHtml, String optional) {
        return "<button type=\"button\" class=\"" + this.classButtonAdd + "\" " + optional + ">"
                    + this.fontAddClass + " "
                    + textHtml 
                + "</button>";
    }
    
    public String getCloseModalButton(String textHtml) {
        return "<button type=\"button\" class=\"btn btn-default btn-flat\" data-dismiss=\"modal\">"
                    + textHtml
                + "</button>";
    }
    
    public String getSaveButton(String textHtml) {
        return "<button type=\"submit\" class=\"btn btn-primary btn-flat\">" 
                    + textHtml 
                + "</button>";
    }
    
    
}
