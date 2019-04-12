package Controller;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.sql.DataSource;
import java.util.List;
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
	public String getAverageScoresByEmployeeID(String empID) throws Exception{
        String SQL = "select avg(e.scores) from Employees_take_Modules e, Modules m where m.module_id=e.module_id and e.employee_id= :id";
        SqlParameterSource namedParams= new MapSqlParameterSource("id",empID);
        String str = njdbc.queryForObject(SQL, namedParams, String.class);

		return str;
	}
	
    /**
    * Get average foundation score of an employee
    * @param empID - the employee id of the employee
    * @return a string representation of the average score
    * @throws java.lang.Exception
    */
	public String getAverageScoresByFoundationEmployeeID(String empID) throws Exception{
        String SQL = "select avg(e.scores) from Employees_take_Modules e, Modules m where m.category='Foundation' and m.module_id=e.module_id and e.employee_id= :id";
        SqlParameterSource namedParams= new MapSqlParameterSource("id",empID);
        String str = njdbc.queryForObject(SQL, namedParams, String.class);

		return str;
	}
	
    /**
    * Get average spec score of an employee
    * @param empID - the employee id of the employee
    * @return a string representation of the average score
    * @throws java.lang.Exception
    */
	public String getAverageScoresBySpecializationEmployeeID(String empID) throws Exception{
       String SQL = "select avg(e.scores) from Employees_take_Modules e, Modules m where m.category='Specialization' and m.module_id=e.module_id and e.employee_id= :id";
       SqlParameterSource namedParams= new MapSqlParameterSource("id",empID);
       String str = njdbc.queryForObject(SQL, namedParams, String.class);

		return str;
	}
	
    /**
    * Get average process/domain score of an employee
    * @param empID - the employee id of the employee
    * @return a string representation of the average score
    * @throws java.lang.Exception
    */
	public String getAverageScoresByProcessDomainEmployeeID(String empID) throws Exception{
            String SQL = "select avg(e.scores) from Employees_take_Modules e, Modules m where m.category='ProcessDomain' and m.module_id=e.module_id and e.employee_id=:id";
            SqlParameterSource namedParams= new MapSqlParameterSource("id",empID);
			String str = njdbc.queryForObject(SQL, namedParams, String.class);

		return str;
	}
	
    /**
    * Get list of foundation scores of an employee
    * @param empID - the employee id of the employee
    * @return a string representation of the average score
    * @throws java.lang.Exception
    */
	public ArrayList<String> getModScoreByFoundation(String empID) throws Exception{
		ArrayList<String> list = new ArrayList<String>();
		//Class.forName("oracle.jdbc.driver.OracleDriver");
		// c1 = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "Student_Performance", "Student_Performance");
		Statement s1 = c1.createStatement();
		ResultSet r1=s1.executeQuery("select m.module_name,e.scores from modules m, employees_take_modules e where m.module_id=e.module_id and category='Foundation' and e.employee_id='"+empID+"'");
		while(r1.next()){
			list.add(r1.getString(1));
			list.add(r1.getString(2));
			
		}
		
		return list;
	}
	
    /**
    * Get list of spec scores of an employee
    * @param empID - the employee id of the employee
    * @return a string representation of the average score
    * @throws java.lang.Exception
    */
	public ArrayList<String> getModScoreBySpecialization(String empID) throws Exception{
		ArrayList<String> list = new ArrayList<String>();
		//Class.forName("oracle.jdbc.driver.OracleDriver");
		//c1 = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "Student_Performance", "Student_Performance");
		Statement s1 = c1.createStatement();
		ResultSet r1=s1.executeQuery("select m.module_name,e.scores from modules m, employees_take_modules e where m.module_id=e.module_id and category='Specialization' and e.employee_id='"+empID+"'");
		while(r1.next()){
			list.add(r1.getString(1));
			list.add(r1.getString(2));
			
		}
		
		return list;
	}
	
    /**
    * Get list of p&d scores of an employee
    * @param empID - the employee id of the employee
    * @return a string representation of the average score
    * @throws java.lang.Exception
    */
	public ArrayList<String> getModScoreByProcessDomain(String empID) throws Exception{
		ArrayList<String> list = new ArrayList<String>();
		Statement s1 = c1.createStatement();
		ResultSet r1=s1.executeQuery("select m.module_name,e.scores from modules m, employees_take_modules e where m.module_id=e.module_id and category='ProcessDomain' and e.employee_id='"+empID+"'");
		while(r1.next()){
			list.add(r1.getString(1));
			list.add(r1.getString(2));
		}
		
		return list;
	}   
}