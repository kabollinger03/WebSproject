package Controller;
//You will need apache pdfbox, apache fontbox, apache commonsloggings, jdbc JARs

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import javax.sql.DataSource;
import org.apache.pdfbox.pdmodel.PDPage;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

//pdf-box layout used under MIT license. Found at https://github.com/ralfstuckert/pdfbox-layout
import rst.pdfbox.layout.elements.Document;
import rst.pdfbox.layout.elements.Paragraph;
import rst.pdfbox.layout.elements.VerticalSpacer;
import rst.pdfbox.layout.elements.render.VerticalLayoutHint;
import rst.pdfbox.layout.text.Alignment;
import rst.pdfbox.layout.text.BaseFont;

public class PDF {
    public static String id;
    private static DataSource dataSource;
    private NamedParameterJdbcTemplate njdbc;

	private BaseFont font;
	private PDPage page = new PDPage();
    private PDFinfo pdfinfo;

	private String filePath = "C:/Users/syntel/Music/";//Path to save the pdf
    
    public PDF(){this.font = BaseFont.Times;
}
    
	public PDF(DataSource dataSource) throws ClassNotFoundException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        this.font = BaseFont.Times;
        //super();
        System.out.println("Constructor Called");
        PDF.dataSource=dataSource;
        njdbc = new NamedParameterJdbcTemplate(dataSource);
	}
   
    public void setPdfinfo(PDFinfo pdfinfo) {
        this.pdfinfo = pdfinfo;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return id;
    }

    public PDFinfo getPdfinfo() {
        return pdfinfo;
    }
	
    public DataSource getDataSource() {
        return dataSource;
    }

    public NamedParameterJdbcTemplate getNjdbc() {
        return njdbc;
    }

    public void setDataSource(DataSource dataSource) {
        PDF.dataSource = dataSource;
    }

    public void setNjdbc(NamedParameterJdbcTemplate njdbc) {
        this.njdbc = njdbc;
    }
    
	public PDPage getPage() {
		return page;
	}
    
	public void setPage(PDPage page) {
		this.page = page;
	}
 
	public String getFilePath() {
		return filePath;
	}
    
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
    
	public String generate(String empid) throws Exception{ //empid not implemented yet, should be able to get everything you will need from it
        //define output vars
        id=empid;
        ArrayList<String> stream = new ArrayList();
        ArrayList<String> foundations = new ArrayList();
        ArrayList<String> specializations = new ArrayList();
        ArrayList<String> domains = new ArrayList();         
        String name, avgGrade, fGrade, sGrade, dGrade;
        System.out.println(empid + " EmpID");
        //init config
        njdbc = new NamedParameterJdbcTemplate(dataSource);
        PDFinfo info = new PDFinfo(dataSource.getConnection());
        info.setDataSource(dataSource);
        info.setNjdbc(njdbc);
        
        if(!info.getAllIds().contains(empid)){ //this is O(n^2) methinks. make the query return one think and do bool return.
            System.out.println("Employee ID not found. PDF has not been generated.");
            return("");
        }

        //populate output variables
        name=info.getEmployeeName(empid);
        stream=info.getStreamIDName(empid);
        avgGrade=info.getAverageScoresByEmployeeID(empid);
        foundations.addAll(info.getModScoreByFoundation(empid));
        specializations.addAll(info.getModScoreBySpecialization(empid));
        domains.addAll(info.getModScoreByProcessDomain(empid));
        fGrade=info.getAverageScoresByFoundationEmployeeID(empid);
        sGrade=info.getAverageScoresBySpecializationEmployeeID(empid);
        dGrade=info.getAverageScoresByProcessDomainEmployeeID(empid);
		
        Document document = new Document(40, 50, 40, 60);
        float linspace = 4;
        float sectionBreak = 6.5f;
        
        Paragraph title = new Paragraph();
        title.addMarkup("*PERFORMICA REPORT*", 20, font);
       	document.add(title, VerticalLayoutHint.CENTER);
        document.add(new VerticalSpacer(5));
   
        Paragraph emp = new Paragraph();
        emp.addMarkup( "*NAME*: " + name + "            " +
                "*EMPLOYEE ID*: " + empid, 14, font);
        emp.setAlignment(Alignment.Center);
        document.add(emp);
        
        document.add(new VerticalSpacer(6.5f));
        
        System.out.println("stream is " + stream.size());
        Paragraph strm = new Paragraph();
        strm.addMarkup("*INDUCTION*: " + stream.get(0) + " - " + stream.get(1), 14, font);
        strm.setAlignment(Alignment.Center);
        document.add(strm);
		
        document.add(new VerticalSpacer(15.5f));
        
        Paragraph p1 = new Paragraph();
        p1.addMarkup("__*Training Modules Completed*__", 12, font);
        document.add(p1);
        document.add(new VerticalSpacer(linspace));
        
        Paragraph found = new Paragraph();
        found.addMarkup("*Foundations*:  ", 12, font);
        for(int i = 0; i < foundations.size() - 2; i+=2){
            
            found.addMarkup(foundations.get(i) + ",  ", 12, font);
        }
        
        found.addMarkup(foundations.get(foundations.size() - 2), 12, font);
        document.add(found);
        document.add(new VerticalSpacer(linspace));

        Paragraph spec = new Paragraph();
        spec.addMarkup("*Specializations*: ", 12, font);
        for(int i = 0; i < specializations.size() - 2; i+=2){
            
            spec.addMarkup(specializations.get(i) + ",  ", 12, font);
        }
        
        spec.addMarkup(specializations.get(specializations.size() - 2), 12, font);  
        document.add(spec);
        document.add(new VerticalSpacer(linspace));
        
        Paragraph pd = new Paragraph();
        pd.addMarkup("*Process / Domain*: ", 12, font);
        for(int i = 0; i < domains.size() - 2; i+=2){
            pd.addMarkup(domains.get(i) + ",  ", 12, font);
        }
        pd.addMarkup(domains.get(domains.size() - 2), 12, font);  
        document.add(pd);
        
        document.add(new VerticalSpacer(sectionBreak));
        
        Paragraph p2 = new Paragraph();
        p2.addMarkup("__*Performence Report*__\nThis is where we would put the bargraph if we had it", 12, font);
        document.add(new VerticalSpacer(linspace));
        document.add(p2);
        document.add(new VerticalSpacer(350));
        
        String gradeNumbers = "Overall Grade: "+avgGrade+"  |  "
                + "Foundation Grade: "+fGrade+"  |  "
                + "Specialization Grade: "+sGrade+"  |  "
                + "Process/Domain Grade: "+dGrade+"   ";
        Paragraph grades = new Paragraph();
        grades.addMarkup(gradeNumbers, 12, font);
        document.add(grades);
        document.add(new VerticalSpacer(linspace));
        
        final OutputStream outputStream = new FileOutputStream(
		empid + ".pdf");
        document.save(outputStream);
        outputStream.close();
        return(empid+".pdf");
        //BarChartEx bc = new BarChartEx();
	}	
}