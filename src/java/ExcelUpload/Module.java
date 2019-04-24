package ExcelUpload;

public class Module {
	
	private String moduleName; 
	private double moduleScore;
	
	public Module() {
		super();
		this.moduleName = "N/A";
		this.moduleScore = -1;
	}
	public Module(String moduleName, double moduleScore) {
		super();
		this.moduleName = moduleName;
		this.moduleScore = moduleScore;
	}

	
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public double getmoduleScore() {
		return moduleScore;
	}
	public void setmoduleScore(double moduleScore) {
		this.moduleScore = moduleScore;
	}

	@Override
	public String toString() {
		return "Module [moduleID=" + moduleName + ", moduleScore=" + moduleScore + "]";
	}	
}