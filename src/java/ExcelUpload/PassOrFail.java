/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ExcelUpload;

import java.util.ArrayList;

/**
 *
 * @author syntel
 */
public class PassOrFail {
    
    
    public ArrayList<Module> passingOrFailing(Employee emp){
        boolean passing = true;
        int moduleRepeats = 0;
        
        ArrayList<Module> checkScores = new ArrayList<>();
        ArrayList<Module> finalScores = new ArrayList<>();
        
        checkScores = emp.getModScores();
        
        String currentModule = "";
        String previousModule = "";
        
        
        for(Module mod: checkScores){
            
                currentModule = mod.getModuleID();
                
                if(!previousModule.equals(currentModule) && mod.getmoduleScore() >= 70){
                    finalScores.add(new Module(currentModule, mod.getmoduleScore()));
                    moduleRepeats = 0;
                    
                } else if (previousModule.equals(currentModule) && mod.getmoduleScore() >= 70) {
                    finalScores.add(new Module(currentModule, 70));
                    moduleRepeats = 0;
                    
                } else if (previousModule.equals(currentModule) && mod.getmoduleScore() <70){
                    moduleRepeats++;
                    if(moduleRepeats == 2){
                        passing = false;
                    }
                } else if (!previousModule.equals(currentModule) && mod.getmoduleScore() < 70 ) {
                    moduleRepeats++;
                    if(moduleRepeats == 2){
                        passing = false;
                    }
                }
                previousModule = currentModule;
            }
        
        
        
        if (passing == false){
            return null;
        } else{
            return finalScores;
        }
        
        
        
    }
    
}
