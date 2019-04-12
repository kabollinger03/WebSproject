package ExcelUpload;

public class Module {
	
	private String moduleID;
	private double moduleScore;
	
	public Module() {
		super();
		this.moduleID = "N/A";
		this.moduleScore = -1;
	}
	public Module(String moduleID, double moduleScore) {
		super();
		this.moduleID = moduleID;
		this.moduleScore = moduleScore;
	}

	
	public String getModuleID() {
		return moduleID;
	}
	public void setModuleID(String moduleID) {
		this.moduleID = moduleID;
	}

	public double getmoduleScore() {
		return moduleScore;
	}
	public void setmoduleScore(double moduleScore) {
		this.moduleScore = moduleScore;
	}

	@Override
	public String toString() {
		return "Module [moduleID=" + moduleID + ", moduleScore=" + moduleScore + "]";
	}	
}