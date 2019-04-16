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
import rst.pdfbox.layout.elements.ImageElement;


public class PDF {
    
    private static DataSource dataSource;
    private NamedParameterJdbcTemplate njdbc;

	private final static BaseFont font = BaseFont.Helvetica;
	private PDPage page = new PDPage();
    private PDFinfo pdfinfo;

	private String filePath = "C:/Users/syntel/Music/"; //use getfilepath in the email logic?
    private final static String imagePath = "C:\\trainingkb\\WebSproject\\web\\resources\\img\\logo-from-site.jpg"; //path to AS logo

    //java.io.File.separator
    public PDF(){}
    
	public PDF(DataSource dataSource) throws ClassNotFoundException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        System.out.println("Constructor Called");
        PDF.dataSource=dataSource;
        njdbc = new NamedParameterJdbcTemplate(dataSource);
	}
   
    public void setPdfinfo(PDFinfo pdfinfo) {
        this.pdfinfo = pdfinfo;
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
    
	public void generate(String empid) throws Exception{ //empid not implemented yet, should be able to get everything you will need from it
        //define output vars
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
            return;
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
		
        Document document = new Document(40, 50, 20, 60);
        float linspace = 4;
        float sectionBreak = 7;
        
        ImageElement img = new ImageElement(imagePath);
        document.add(img, new VerticalLayoutHint(Alignment.Left, 0, 0,
		0, 0, true));
        document.add(new VerticalSpacer(40));
                        
        Paragraph title = new Paragraph();
        title.addMarkup("{color:#0066a1}__***PERFORMICA REPORT***__", 20, font);
       	document.add(title, VerticalLayoutHint.CENTER);
        document.add(new VerticalSpacer(5));
   
        Paragraph emp = new Paragraph();
        emp.addMarkup( "{color:#0066a1}*NAME*{color:#000000}: " + name + "            " +
                "{color:#0066a1}*EMPLOYEE ID*{color:#000000}: " + empid, 14, font);
        emp.setAlignment(Alignment.Center);
        document.add(emp);
        
        document.add(new VerticalSpacer(6.5f));
        
        Paragraph strm = new Paragraph();
        strm.addMarkup("{color:#0066a1}*INDUCTION*{color:#000000}: " + stream.get(0) + " - " + stream.get(1), 14, font);
        strm.setAlignment(Alignment.Center);
        document.add(strm);
		
        document.add(new VerticalSpacer(15.5f));
        
        Paragraph p1 = new Paragraph();
        p1.addMarkup("{color:#0066a1}__*Training Modules Completed*__", 12, font);
        document.add(p1);
        document.add(new VerticalSpacer(linspace));
        
        Paragraph found = new Paragraph();
        found.addMarkup("{color:#0066a1}*Foundations*{color:#000000}:  ", 11, font);
        for(int i = 0; i < foundations.size() - 2; i+=2){
            
            found.addMarkup(foundations.get(i) + ",  ", 11, font);
        }
        
        found.addMarkup(foundations.get(foundations.size() - 2), 11, font);
        document.add(found);
        document.add(new VerticalSpacer(linspace));

        Paragraph spec = new Paragraph();
        spec.addMarkup("{color:#0066a1}*Specializations*{color:#000000}: ", 11, font);
        for(int i = 0; i < specializations.size() - 2; i+=2){
            
            spec.addMarkup(specializations.get(i) + ",  ", 11, font);
        }
        
        spec.addMarkup(specializations.get(specializations.size() - 2), 11, font);  
        document.add(spec);
        document.add(new VerticalSpacer(linspace));
        
        Paragraph pd = new Paragraph();
        pd.addMarkup("{color:#0066a1}*Process / Domain*{color:#000000}: ", 11, font);
        for(int i = 0; i < domains.size() - 2; i+=2){
            pd.addMarkup(domains.get(i) + ",  ", 11, font);
        }
        pd.addMarkup(domains.get(domains.size() - 2), 11, font);  
        document.add(pd);
         
        document.add(new VerticalSpacer(sectionBreak));
        
        Paragraph p2 = new Paragraph();
        p2.addMarkup("{color:#0066a1}__*Performence Report*__\nTODO: bargraph and grade-letter map. Also dynamic categories.", 12, font);
        document.add(new VerticalSpacer(linspace));
        document.add(p2);
        document.add(new VerticalSpacer(350));
        
        
        Paragraph overall = new Paragraph();
        overall.addMarkup("Overall Grade: " + avgGrade, 12, font);
        overall.setAlignment(Alignment.Center);
        document.add(overall);
        
        String gradeNumbers = "Foundation Grade: "+fGrade+"  |  "
                + "Specialization Grade: "+sGrade+"  |  "
                + "Process/Domain Grade: "+dGrade;
        Paragraph grades = new Paragraph();
        grades.addMarkup(gradeNumbers, 12, font);
        document.add(grades);
        document.add(new VerticalSpacer(linspace));
        
        Paragraph quote = new Paragraph();
        quote.addMarkup("{color:#000000}_*\"Learning is the lifelong process of transforming information and experience into knowledge, skills, behaviours and attitudes\"*_\n", 10, font);
        quote.addMarkup(" - Jeff Cobb, _10 Ways to be a Better Learner_", 10, font);
        document.add(new VerticalSpacer(5*linspace));
        document.add(quote);
        //document.add(new VerticalSpacer(linspace));
        
        final OutputStream outputStream = new FileOutputStream(
		filePath + empid + ".pdf");
        document.save(outputStream);
    
        //BarChartEx bc = new BarChartEx();
	}	
}