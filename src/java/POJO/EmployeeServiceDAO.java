package POJO;

import ExcelUpload.Employee;
import ExcelUpload.Module;
import java.util.ArrayList;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class EmployeeServiceDAO implements EmployeeDAO{
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplateObject;
    
    @Override
    public void setDataSource(DataSource ds) {
        System.out.println("Setting the data source and creating JDBC Template interface");
        dataSource = ds;
        try { 
            jdbcTemplateObject = new JdbcTemplate(dataSource);
        } catch(Exception e) {
            System.out.println("EXCEPTION: [ " + e.getMessage() + " ]");
        }
    }

    @Override
    public void insertEmployee(String employeeID, String name, String email, String classID, String managerID) {
        String sql = "insert into employees values (?, ?, ?, ?, ?)";
        try {
            System.out.println("Insert into employees...");
            int row = jdbcTemplateObject.update(sql, employeeID, name, email, classID, managerID);
            System.out.println("* " + row + " row inserted.\n");
        } catch(Exception e) {
            System.out.println("EXCEPTION: [ " + e.getMessage() + " ]");
        }
    }

    //Returns Employee object
    @Override
    public Employee readEmployee(String employeeID) {
        String sql = "select * from employees where employee_id = ?";
        Employee emp = new Employee();
        try {
            System.out.println("Read from employees...");
            emp = (Employee) jdbcTemplateObject.queryForObject(sql, new Object[]{employeeID}, new EmployeeRowMapper());            
        } catch(Exception e) {
            System.out.println("EXCEPTION: [ " + e.getMessage() + " ]");
        }
        return emp;
    }
    
    @Override
    public ArrayList<Employee> readAllEmployee(){
        ArrayList<Employee> list = new ArrayList<>();
        String sql = "select * from employees";
        
        try {
            System.out.println("Read from employees...");
            list = (ArrayList<Employee>) jdbcTemplateObject.query(sql, new EmployeeRowMapper());
            //For each employee get modules and set employee scores.
            for(int i=0; i<list.size(); i++){
                list.get(i).setModScores(getModuleIDAndScore(list.get(i).getEmployeeID()));
            }            
        } catch(Exception e) {
            System.out.println("EXCEPTION: [ " + e.getMessage() + " ]");
        }
        return list;
    }
    
    //Get arraylist of module for employee.
    public ArrayList<Module> getModuleIDAndScore(String id){
        ArrayList<Module> list = new ArrayList<>();
        String sql = "select m.module_name, etm.scores, m.module_id from Employees e, "
                + "Modules m, Employees_take_Modules etm where e.employee_id = etm.employee_id "
                + "AND etm.module_id = m.module_id AND e.employee_id = ?";
        
        try {
            System.out.println("Get Employee Module and Scores...");
            list = (ArrayList<Module>) jdbcTemplateObject.query(sql, new Object[]{id}, new ModuleListRowMapper());
        } catch(Exception e) {
            System.out.println("EXCEPTION: [ " + e.getMessage() + " ]");
        }
        return list;        
    }
 
    @Override
    public ArrayList<Employee> readAllEmployeeFromCol(String col, String str){
        ArrayList<Employee> list = new ArrayList<>();
        String sql = "";
        switch(col){
            case "name":
                sql = "select * from employees where lower(name) like ?";
                break;
            case "email":
                sql = "select * from employees where lower(email) like ?";
                break;
            case "classID":
                sql = "select * from employees where lower(class_id) like ?";
                break;
        }
        try {
            System.out.println("Read from employees from searching a column...");
            list = (ArrayList<Employee>) jdbcTemplateObject.query(sql, 
                    new Object[]{"%"+str+"%"}, new EmployeeRowMapper());
                    
        } catch(Exception e) {
            System.out.println("EXCEPTION: [ " + e.getMessage() + " ]");
        }
        
        return list;
    }

    @Override
    public void updateEmployee(String employeeID, String name, String email, String managerID) {
        String sql = "update employees set employee_id = ?, name = ?, email = ?, manager_id = ? where employee_id = ?";
        try {
            System.out.println("Update employee...");
            int row = jdbcTemplateObject.update(sql, employeeID, name, email, managerID, employeeID);    
            System.out.println("* " + row + " row updated.\n");
        } catch(Exception e) {
            System.out.println("EXCEPTION: [ " + e.getMessage() + " ]");
        }
    }
    
    @Override
    public void updateEmployeeModule(String score, String employeeID, String name, String moduleID){
        String sql = "update employees_take_modules set scores = ? where employee_id = ? AND module_id = ?";
        try {
            int row = jdbcTemplateObject.update(sql, score, employeeID, moduleID);    
            System.out.println("* " + row + " row updated.\n");
        } catch(Exception e) {
            System.out.println("EXCEPTION: [ " + e.getMessage() + " ]");
        }        
    }

    @Override
    public void deleteEmployee(String employeeID) {
        String sql = "delete from employees where employee_id = ?";
        try {
            System.out.println("Delete employee...");
            int row = jdbcTemplateObject.update(sql, employeeID);
            System.out.println("* " + row + " row deleted.\n");
        } catch(Exception e) {
            System.out.println("EXCEPTION: [ " + e.getMessage() + " ]");
        }
    }
    
}
