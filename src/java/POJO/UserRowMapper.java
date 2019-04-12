/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author syntel
 */
public class UserRowMapper implements RowMapper<User> {

    public UserRowMapper() {
    }

    @Override
    public User mapRow(ResultSet rs, int i) {
        User e = new User();
        try {
            e.setUserName(rs.getString(1));
            e.setPassword(rs.getString(2));
            e.setIsAdmin(rs.getString(3));
        } catch (SQLException ex) {
            Logger.getLogger(UserRowMapper.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return e;
        }
    }
    
}
