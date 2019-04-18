/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJO;

public class ResetPassForm {
    private String email;

    public ResetPassForm() {
    }

    public ResetPassForm(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "ResetPassForm: " + "email=" + email;
    }
    
    
}
