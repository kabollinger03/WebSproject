/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExcelUpload;

/**
 *
 * @author syntel
 */
public class ModuleWithID extends Module {
    private String moduleID; 

    public ModuleWithID() {
        super();
    }
    
    public ModuleWithID(String moduleName, double moduleScore, String moduleID) {
            super(moduleName, moduleScore);
            this.moduleID = moduleID;
    }

    public String getModuleID() {
        return moduleID;
    }

    public void setModuleID(String moduleID) {
        this.moduleID = moduleID;
    }

    @Override
    public String toString() {
        return super.toString().substring(0, super.toString().length()-1) + ", moduleID=" + moduleID + "]";
    }

}
