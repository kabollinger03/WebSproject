package Controller;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
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

//import org.jfree.chart.ChartUtilities;

public class PDF {
    
    private static DataSource dataSource;
    private NamedParameterJdbcTemplate njdbc;

	private final static BaseFont font = BaseFont.Helvetica;
	private PDPage page = new PDPage();
    private PDFinfo pdfinfo;

	private String filePath = "C:/Users/syntel/Music/"; //use getfilepath in the email logic?
    private final static String imagePath = "C:\\trainingkb\\WebSproject\\web\\resources\\img\\pdf_banner.png"; //path to AS logo

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
        avgGrade=info.getAverageScore(empid);
        foundations.addAll(info.getModuleScoresByCategory(empid, "FOUND01"));
        specializations.addAll(info.getModuleScoresByCategory(empid, "SPEC01"));
        domains.addAll(info.getModuleScoresByCategory(empid, "PD01"));
        fGrade=info.getAverageScoreByCategory(empid, "FOUND01");
        sGrade=info.getAverageScoreByCategory(empid, "SPEC01");
        dGrade=info.getAverageScoreByCategory(empid, "PD01");
		
        //define a <hr />
        char[] charArray = new char[1835];
        Arrays.fill(charArray, ' ');
        String hrHelp = new String(charArray);
        
        Paragraph hRule = new Paragraph();
        hRule.addMarkup("__" + hrHelp + " __", 1, font); 
        //because the "real way" is being diffuclt, cheat by adding a realy tiny empty string that is underlined.
        
        //create PDF
        float linspace = 12;
        float sectionBreak = 18;
        float leftMargin = 40;
        float rightMargin = 40;
        float topMargin = 20;
        float bottomMargin = 20;
        Document document = new Document(leftMargin, rightMargin, topMargin, bottomMargin);
        
        document.add(new ImageElement(imagePath), new VerticalLayoutHint(Alignment.Left, 0, 0,
		0, 0, true));
        document.add(new VerticalSpacer(100));
                        
        Paragraph title = new Paragraph();
        title.addMarkup("{color:#0066a1}__***PERFORMICA REPORT***__", 20, font);
       	document.add(title, VerticalLayoutHint.CENTER);
        document.add(new VerticalSpacer(sectionBreak));
   
        Paragraph emp = new Paragraph();
        emp.addMarkup( "{color:#0066a1}*NAME*{color:#000000}: " + name + "            " +
                "{color:#0066a1}*EMPLOYEE ID*{color:#000000}: " + empid, 14, font);
        emp.setAlignment(Alignment.Center);
        document.add(emp);
        
        //document.add(new VerticalSpacer(6.5f));
        
        document.add(new VerticalSpacer(2*sectionBreak));
        
        Paragraph p1 = new Paragraph();
        p1.addMarkup("{color:#0066a1}__*My Trainings*__:", 12, font);
        document.add(p1);
        document.add(new VerticalSpacer(linspace));
        
        Paragraph pur = new Paragraph();
        pur.addMarkup("{color:#000000}*Stream*: " + stream.get(0) + " - " + stream.get(1), 12, font);
        document.add(pur);
        document.add(new VerticalSpacer(linspace));
        
        /*Paragraph strm = new Paragraph();
        strm.addMarkup("{color:#0066a1}*INDUCTION*{color:#000000}: " + stream.get(0) + " - " + stream.get(1), 14, font);
        strm.setAlignment(Alignment.Center);
        document.add(strm);
        
		
        document.add(new VerticalSpacer(15.5f));
        
        Paragraph p1 = new Paragraph();
        p1.addMarkup("{color:#0066a1}*Training Modules Completed*", 12, font);
        document.add(p1);
        document.add(new VerticalSpacer(linspace));
        */
        
        document.add(hRule);
        document.add(new VerticalSpacer(5));
        Paragraph found = new Paragraph();
        found.addMarkup("{color:#0066a1}*Foundations*{color:#000000}:  ", 11, font);
        for(int i = 0; i < foundations.size() - 2; i+=2){
            
            found.addMarkup(foundations.get(i) + ",  ", 11, font);
        }
        found.addMarkup(foundations.get(foundations.size() - 2), 11, font);

        document.add(found);
        document.add(hRule);
        document.add(new VerticalSpacer(linspace));

        Paragraph spec = new Paragraph();
        spec.addMarkup("{color:#0066a1}*Specializations*{color:#000000}: ", 11, font);
        for(int i = 0; i < specializations.size() - 2; i+=2){
            
            spec.addMarkup(specializations.get(i) + ",  ", 11, font);
        }
        
        spec.addMarkup(specializations.get(specializations.size() - 2), 11, font);  
        document.add(spec);
        document.add(hRule);
        document.add(new VerticalSpacer(linspace));
        
        Paragraph pd = new Paragraph();
        pd.addMarkup("{color:#0066a1}*Process / Domain*{color:#000000}: ", 11, font);
        for(int i = 0; i < domains.size() - 2; i+=2){
            pd.addMarkup(domains.get(i) + ",  ", 11, font);
        }
        pd.addMarkup(domains.get(domains.size() - 2), 11, font);  
        document.add(pd);
        document.add(hRule);
        document.add(new VerticalSpacer(sectionBreak));
        
        Paragraph p2 = new Paragraph();
        p2.addMarkup("{color:#0066a1}__*My Performence*__\n\n<<bargraph goes here>>", 12, font); //http://www.java2s.com/Code/Java/Chart/JFreeChartHorizontalBarChartDemo2.htm
        document.add(new VerticalSpacer(linspace));
        document.add(p2);
        document.add(new VerticalSpacer(50));
        
        String gradeNumbers = "Overall Grade: " + avgGrade + "\n"
                + "Foundation Grade: "+fGrade+"  |  "
                + "Specialization Grade: "+sGrade+"  |  "
                + "Process/Domain Grade: "+dGrade;
        Paragraph grades = new Paragraph();
        grades.addMarkup(gradeNumbers, 11, font);
        grades.setAlignment(Alignment.Center);
        document.add(grades);
        document.add(new VerticalSpacer(linspace));
        
        Paragraph quote = new Paragraph();
        quote.addMarkup("{color:#000000}_*\"Learning is the lifelong process of transforming information and experience into knowledge, skills, behaviours and attitudes\"*_\n", 10, font);
        quote.addMarkup(" - Jeff Cobb, _10 Ways to be a Better Learner_", 10, font);
        quote.setAlignment(Alignment.Center);
        document.add(new VerticalSpacer(5*linspace));
        document.add(quote);
        //document.add(new VerticalSpacer(linspace));
        
        final OutputStream outputStream = new FileOutputStream(
		filePath + empid + ".pdf");
        document.save(outputStream);

	}	
}

        /* "Real Way" to underline Do not use - inserts new, completely blank, page before rest of content.
        * Saved for possible later use and as a reminder to not Do The Thing
        Stroke stroke = new Stroke();
        RenderContext rc = new RenderContext(document, document.getPDDocument());
        stroke.applyTo(rc.getContentStream());
        rc.close();
        */