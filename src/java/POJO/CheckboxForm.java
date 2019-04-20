/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJO;

/**
 *
 * @author LS5028230
 */
public class CheckboxForm {
    String []userNames;
    boolean isPreview;

    public void setIsPreview(boolean isPreview) {
        this.isPreview = isPreview;
    }

    public boolean isPreview() {
        return isPreview;
    }

    public CheckboxForm() {
    }    

    public CheckboxForm(String[] userNames) {
        this.userNames = userNames;
    }


    public String[] getUserNames() {
        return userNames;
    }

    public void setUserNames(String[] userNames) {
        this.userNames = userNames;
    }

    @Override
    public String toString() {
        return "User{" + "userNames=" + userNames + '}';
    }
    
    
    
    

}
