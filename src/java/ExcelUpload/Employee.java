package ExcelUpload;
import java.util.*;
public class Employee {
	
	private String employeeID;
	private String employeeName;
	private String employeeEmail;
	private String classID;
        private String managerID;
	private ArrayList<Module> modScores = new ArrayList<>();	
	
	public Employee() {
		super();
		this.employeeID = "N/A";
		this.employeeName = "N/A";
		this.employeeEmail = "N/A";
		this.classID = "N/A";
                this.managerID = "N/A";
		
	}
  
	public Employee(String employeeID, String employeeName, String employeeEmail, String classID, String managerID) {
		super();
		this.employeeID = employeeID;
		this.employeeName = employeeName;
		this.employeeEmail = employeeEmail;
		this.classID = classID;
                this.managerID = managerID;
	}

	
	public String getEmployeeName() {
		return employeeName;
	}
	
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	
	public ArrayList<Module> getModScores() {
		return modScores;
	}
	
	public void setModScores(ArrayList<Module> modScores) {
		this.modScores = modScores;
	}
	
	public String getEmployeeEmail() {
		return employeeEmail;
	}
	
	public void setEmployeeEmail(String employeeEmail) {
		this.employeeEmail = employeeEmail;
	}
	
	public String getClassID() {
		return classID;
	}
	
	public void setClassID(String classID) {
		this.classID = classID;
	}
	
	public String getEmployeeID() {
		return employeeID;
	}
	
	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}
	
	public void addScore(String modName, double sco) {
		 Module mod = new Module(modName, sco);
		 this.modScores.add(mod);
	}
        
        public void setManagerID(String managerID){
            this.managerID = managerID;
        }

        public String getManagerID(){            
            return managerID;
        }
	
	@Override
	public String toString() {
		return "Employee [employeeID=" + employeeID + ", employeeName=" + employeeName + ", employeeEmail="
				+ employeeEmail + ", classID=" + classID + ", modScores=" + modScores + ", managerID="
                                + managerID + "]";
	}	
}
