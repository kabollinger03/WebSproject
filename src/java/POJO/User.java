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
public class User {
    String userName;
    String password;
    boolean isAdmin;

    public User() {
        this.userName = "NA";
        this.password = "NA";
        this.isAdmin = false;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
    
    

    public User(String userName, String password, boolean isAdmin) {
        this.userName = userName;
        this.password = password;
        this.isAdmin = isAdmin;
    }
    
    

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" + "userName=" + userName + ", password=" + password + ", isAdmin=" + isAdmin + '}';
    }

    void setIsAdmin(String string) {
        this.isAdmin = (string.trim().equalsIgnoreCase("y"));
    }

    public boolean isAdmin() {
        return isAdmin;
    }
    
    
}
