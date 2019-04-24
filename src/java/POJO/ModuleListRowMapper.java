/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJO;

import ExcelUpload.Module;
import ExcelUpload.ModuleWithID;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;


public class ModuleListRowMapper implements RowMapper{

    @Override
    public Module mapRow(ResultSet rs, int i) throws SQLException {
        ModuleWithID m = new ModuleWithID();
        m.setModuleName(rs.getString(1));
        m.setmoduleScore(rs.getDouble(2));
        m.setModuleID(rs.getString(3));
        
        return m;
    }
    
}
