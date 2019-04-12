package Controller;

//package extraction.sProject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.sql.DataSource;
import java.lang.String;
import java.util.List;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

/*
 * PDF functions that John asked for to display relevent data in the 
 * report 
 * 
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
    public ArrayList<String> getAllIds() throws Exception{
        ArrayList<String> list = new ArrayList();
        Statement s1= c1.createStatement();
        ResultSet r1=s1.executeQuery("select employee_id from employees");
        while(r1.next()){
            list.add(r1.getString(1));
        }
        return list;
    }
    public void setC1(Connection c1) {
        this.c1 = c1;
    }

    public void setNjdbc(NamedParameterJdbcTemplate njdbc) {
        //njdbc = new NamedParameterJdbcTemplate(dataSource);
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
	 
	//method to get just the employee name depending on the ID that you pass in
	public String getEmployeeName(String empID) throws Exception{
            //njdbc= new NamedParameterJdbcTemplate(dataSource);
            System.out.println("Getting Employee Name");
           // ArrayList<String> l = new ArrayList();
            String SQL = "SELECT name FROM Employees WHERE employee_id = :id";
			//Class.forName("oracle.jdbc.driver.OracleDriver");
			// c1 = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "Student_Performance", "Student_Performance");
			//Statement s1 = c1.createStatement();
			//ResultSet r1=s1.executeQuery("SELECT name FROM Employees WHERE employee_id = :?");//+empID+"'");
                        SqlParameterSource namedParams= new MapSqlParameterSource("id",empID);
			String str = njdbc.queryForObject(SQL, namedParams, String.class);
			//while(r1.next()){
			//	str = r1.getString(1);
			//}
			//r1.close();
                        //s1.close();
                        //c1.close();
			return str;
                        
                        
                        
		}
	
	public List<String> getStreamIDName(String empID) throws Exception{
		ArrayList<String> list = new ArrayList();
               //String SQL = "select s.stream_id, s.stream_name from Stream s, Class c, Employees e where s.stream_id=c.stream_id and c.class_id=e.class_id and e.employee_id= :id";
		//SqlParameterSource namedParams= new MapSqlParameterSource("id",empID);
            //List<String> s = njdbc.queryForList(SQL, namedParams);
//Class.forName("oracle.jdbc.driver.OracleDriver");
		// c1 = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "Student_Performance", "Student_Performance");
		Statement s1 = c1.createStatement();
		ResultSet r1=s1.executeQuery("select s.stream_id, s.stream_name from Stream s, Class c, Employees e where s.stream_id=c.stream_id and c.class_id=e.class_id and e.employee_id='"+empID+"'");
		while(r1.next()){
			list.add(r1.getString(1));
			list.add(r1.getString(2));
		}
              
		r1.close();
                s1.close();
                //c1.close();
		//return list;
                return list;
	}
	
	public String getAverageScoresByEmployeeID(String empID) throws Exception{
            String SQL = "select avg(e.scores) from Employees_take_Modules e, Modules m where m.module_id=e.module_id and e.employee_id= :id";
            SqlParameterSource namedParams= new MapSqlParameterSource("id",empID);
			String str = njdbc.queryForObject(SQL, namedParams, String.class);
		//Class.forName("oracle.jdbc.driver.OracleDriver");
		// c1 = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "Student_Performance", "Student_Performance");
		//Statement s1 = c1.createStatement();
		//ResultSet r1=s1.executeQuery("select avg(e.scores) from Employees_take_Modules e, Modules m where m.module_id=e.module_id and e.employee_id='"+empID+"'");
		//while(r1.next()){
		//	str = r1.getString(1);
		//	
		//}
		//r1.close();
                 //       s1.close();
//                        c1.close();
		return str;
	}
	
	public String getAverageScoresByFoundationEmployeeID(String empID) throws Exception{
             String SQL = "select avg(e.scores) from Employees_take_Modules e, Modules m where m.category='Foundation' and m.module_id=e.module_id and e.employee_id= :id";
            SqlParameterSource namedParams= new MapSqlParameterSource("id",empID);
			String str = njdbc.queryForObject(SQL, namedParams, String.class);
		//Class.forName("oracle.jdbc.driver.OracleDriver");
		// c1 = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "Student_Performance", "Student_Performance");
		//Statement s1 = c1.createStatement();
		//ResultSet r1=s1.executeQuery("select avg(e.scores) from Employees_take_Modules e, Modules m where m.category='Foundation' and m.module_id=e.module_id and e.employee_id='"+empID+"'");
		//String str = "";
		//while(r1.next()){
		//	str = r1.getString(1);
		//	
		//}
		
		return str;
	}
	
	public String getAverageScoresBySpecializationEmployeeID(String empID) throws Exception{
             String SQL = "select avg(e.scores) from Employees_take_Modules e, Modules m where m.category='Specialization' and m.module_id=e.module_id and e.employee_id= :id";
            SqlParameterSource namedParams= new MapSqlParameterSource("id",empID);
			String str = njdbc.queryForObject(SQL, namedParams, String.class);
		//Class.forName("oracle.jdbc.driver.OracleDriver");
		// c1 = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "Student_Performance", "Student_Performance");
		/*Statement s1 = c1.createStatement();
		ResultSet r1=s1.executeQuery("select avg(e.scores) from Employees_take_Modules e, Modules m where m.category='Specialization' and m.module_id=e.module_id and e.employee_id='"+empID+"'");
		String str = "";
		while(r1.next()){
			str = r1.getString(1);
		}
		*/
		return str;
	}
	
	public String getAverageScoresByProcessDomainEmployeeID(String empID) throws Exception{
            String SQL = "select avg(e.scores) from Employees_take_Modules e, Modules m where m.category='ProcessDomain' and m.module_id=e.module_id and e.employee_id=:id";
            SqlParameterSource namedParams= new MapSqlParameterSource("id",empID);
			String str = njdbc.queryForObject(SQL, namedParams, String.class);
		//Class.forName("oracle.jdbc.driver.OracleDriver");
		// c1 = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "Student_Performance", "Student_Performance");
                /*
		Statement s1 = c1.createStatement();
		ResultSet r1=s1.executeQuery("select avg(e.scores) from Employees_take_Modules e, Modules m where m.category='ProcessDomain' and m.module_id=e.module_id and e.employee_id='"+empID+"'");
		String str = "";
		while(r1.next()){
			str = r1.getString(1);
			
		}
		*/
		return str;
	}
	
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
	
	public ArrayList<String> getModScoreByProcessDomain(String empID) throws Exception{
		ArrayList<String> list = new ArrayList<String>();
		//Class.forName("oracle.jdbc.driver.OracleDriver");
		//c1 = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "Student_Performance", "Student_Performance");
		Statement s1 = c1.createStatement();
		ResultSet r1=s1.executeQuery("select m.module_name,e.scores from modules m, employees_take_modules e where m.module_id=e.module_id and category='ProcessDomain' and e.employee_id='"+empID+"'");
		while(r1.next()){
			list.add(r1.getString(1));
			list.add(r1.getString(2));
		}
		
		return list;
	}
        
}