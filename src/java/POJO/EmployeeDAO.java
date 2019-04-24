package POJO;

import ExcelUpload.Employee;
import java.util.ArrayList;
import javax.sql.DataSource;

public interface EmployeeDAO {
    public void setDataSource(DataSource ds);
    public void insertEmployee(String employeeID, String name, String email, String classID, String managerID);
    public Employee readEmployee(String employeeID);
    public ArrayList<Employee> readAllEmployee();
    public ArrayList<Employee> readAllEmployeeFromCol(String col, String str);
    public void updateEmployee(String employeeID, String name, String email, String managerID);
    public void updateEmployeeModule(String score, String employeeID, String name, String moduleID);
    public void deleteEmployee(String employeeID);
}
