/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package POJO;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 *
 * @author syntel
 */
public class UserServiceDAO implements UserDAO{

    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;
    
    @Override
    public void setDataSource(DataSource ds){
        System.out.println("Setting the Data Source and creating JDBC Template instance");
        dataSource = ds;
        jdbcTemplateObject = new JdbcTemplate(dataSource);
    }
    @Override
    public User getUser(String id){
        String SQL = "select * from users where user_id = ?";
        User emp = jdbcTemplateObject.queryForObject(SQL, new Object[]{id}, new UserRowMapper());
        return emp;
        
    }
    
    public DataSource getDataSource(){
        return dataSource;
    }
    
    

    
}
