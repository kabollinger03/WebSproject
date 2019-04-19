package Controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.sql.DataSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

/*
 * PDF functions to get relevent data for the report 
 */

public class PDFinfo {
    private Connection c1;
    private NamedParameterJdbcTemplate njdbc;
    private DataSource dataSource;
    
    public PDFinfo(Connection conn){
        this.c1 = conn;
    }
    
    public PDFinfo() {
    }
    
    public void setC1(Connection c1) {
        this.c1 = c1;
    }

    public void setNjdbc(NamedParameterJdbcTemplate njdbc) {
        this.njdbc = njdbc;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getC1() {
        return c1;
    }

    public NamedParameterJdbcTemplate getNjdbc() {
        return njdbc;
    }

    public DataSource getDataSource() {
        return dataSource;
    }
     
    /**
    * Get employee ID of all employees
    * @return ArrayList of all employee IDs
    * @throws java.lang.Exception
    */
    public ArrayList<String> getAllIds() throws Exception{
        ArrayList<String> list = new ArrayList();
        Statement s1= c1.createStatement();
        ResultSet r1=s1.executeQuery("select employee_id from employees");
        while(r1.next()){
            list.add(r1.getString(1));
        }
        return list;
    } 
    
    /**
    * Get employee name for a given Employee ID
    * @param empID - the employee id of the employee
    * @return employee name
    * @throws java.lang.Exception
    */
    public String getEmployeeName(String empID) throws Exception{
        String SQL = "SELECT name FROM Employees WHERE employee_id = :id";

        SqlParameterSource namedParams= new MapSqlParameterSource("id",empID);
        String str = njdbc.queryForObject(SQL, namedParams, String.class);
        return str;
    }
    
    /**
    * Get stream ID and name of streams employee has undertaken
    * @param empID - the employee id of the employee
    * @return a list of strings, formatted as [id1, name1, id2, name2, ...]
    * @throws java.lang.Exception
    */
    public ArrayList<String> getStreamIDName(String empID) throws Exception{
        ArrayList<String> list = new ArrayList();
        Statement s1 = c1.createStatement();
     
        ResultSet r1=s1.executeQuery("select s.stream_id, s.stream_name from Stream s, Class c, Employees e where s.stream_id=c.stream_id and c.class_id=e.class_id and e.employee_id='"+empID+"'");
        while(r1.next()){
            list.add(r1.getString(1));
            list.add(r1.getString(2));
        }
              
        r1.close();
        s1.close();

        return list;
    }
    
    /**
    * Get overall average score of an employee
    * @param empID - the employee id of the employee
    * @return a string representation of the average score
    * @throws java.lang.Exception
    */

	public String getAverageScore(String empID) throws Exception{
       return getAverageScoreByCategory(empID, "");
    }
    
    /**
    * Get average score of an employee by category
    * @param empID - the employee id of the employee
    * @param category - desired category
    * @return a string representation of the average score
    * @throws java.lang.Exception
    */
	public String getAverageScoreByCategory(String empID, String category) throws Exception{
        SqlParameterSource namedParams;
        String SQL = "SELECT AVG(s.scores) "
                + "FROM employees_take_modules s INNER JOIN modules m ON m.module_id = s.module_ID "
                + "WHERE s.employee_id = :id";
        namedParams = new MapSqlParameterSource("id",empID);

        if (category.length() > 0){
            SQL += " AND m.category_id = :category";
            namedParams= new MapSqlParameterSource("id",empID).addValue("category", category);
        }
        
        String str = njdbc.queryForObject(SQL, namedParams, String.class);

		return String.format("%.2f", Float.parseFloat(str));
	}

  /**
    * Get list of module scores of an employee for a given category
    * @param empID - the employee id of the employee
    * @param category - the desired category
    * @return a string representation of the average score
    * @throws java.lang.Exception
    */
	public ArrayList<String> getModuleScoresByCategory(String empID, String category) throws Exception{
		ArrayList<String> list = new ArrayList<String>();
		Statement s1 = c1.createStatement();
        
  		ResultSet r1=s1.executeQuery("SELECT m.module_name, s.scores "
                + "FROM modules m INNER JOIN employees_take_modules s ON m.module_id=s.module_id "
                + "WHERE employee_id = '" + empID + "' AND category_id='" + category + "'");
		
        while(r1.next()){
			list.add(r1.getString(1));
			list.add(r1.getString(2));	
		}
		
		return list;
	}
}