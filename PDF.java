package Controller;
//You will need apache pdfbox, apache fontbox, apache commonsloggings, jdbc JARs

import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

public class PDF {
    
    private static DataSource dataSource;

    private PDDocument document = new PDDocument();
	private PDFont font = PDType1Font.TIMES_ROMAN;
	private PDPage page = new PDPage();
    private PDFinfo pdfinfo;

	private String filePath = "C:/Users/syntel/Music/";//Path to save the pdf

    private NamedParameterJdbcTemplate njdbc;
    
    public PDF(){}
    
	public PDF(DataSource dataSource) {
        //super();
        System.out.println("Constructor Called");
        PDF.dataSource=dataSource;
	}

    @Override
    public String toString() {
        return "PDF{" + "document=" + document + ", font=" + font + ", page=" + page + ", pdfinfo=" + pdfinfo + ", filePath=" + filePath + ", njdbc=" + njdbc + '}';
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

    public PDDocument getDocument() {
		return document;
	}
    
	public void setDocument(PDDocument document) {
		this.document = document;
	}
    
	public PDFont getFont() {
		return font;
	}
    
	public void setFont(PDFont font) {
		this.font = font;
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
		System.out.println("Generating some stuff yo---------------------------------------------------------------------------------------------------");
        List<String> stream = new ArrayList();
            
        String name;
        String id;
        String foundation;
        String specialization;
        String domain;
        String avgGrade;
        String fGrade;
        String sGrade;
        String dGrade;
        String induction;
        System.out.println("Connection Established");
        njdbc = new NamedParameterJdbcTemplate(dataSource);
        PDFinfo info = new PDFinfo(dataSource.getConnection());
        info.setDataSource(dataSource);
        info.setNjdbc(njdbc);
        if(info.getAllIds().contains(empid)){
        System.out.println("Connection Established");
		document = new PDDocument();
		font = PDType1Font.TIMES_ROMAN;
		page = new PDPage();
		document.addPage( page );
		ArrayList <String>foundations = new ArrayList();
        ArrayList <String>specializations = new ArrayList();
        ArrayList <String>domains = new ArrayList();
		PDPageContentStream contentStream = new PDPageContentStream(document, page);
                
        name=info.getEmployeeName(empid);
        stream=info.getStreamIDName(empid);
        avgGrade=info.getAverageScoresByEmployeeID(empid);
        foundations.addAll(info.getModScoreByFoundation(empid));
        specializations.addAll(info.getModScoreBySpecialization(empid));
        domains.addAll(info.getModScoreByProcessDomain(empid));
        fGrade=info.getAverageScoresByFoundationEmployeeID(empid);
        sGrade=info.getAverageScoresBySpecializationEmployeeID(empid);
        dGrade=info.getAverageScoresByProcessDomainEmployeeID(empid);
		contentStream.setLeading(14.5f);
		contentStream.beginText();
		contentStream.setFont( font, 76 );
		contentStream.newLineAtOffset(100, 700);
        contentStream.showText("LOGO HERE");
        contentStream.newLine();
        contentStream.setFont( font, 12);
		contentStream.showText( "Training Report: "+name+"            ");//NAME select name from employees where employee_id=empid?
        contentStream.showText("Emp ID: "+empid);//Just use empid param
		contentStream.newLine();
		contentStream.showText("Induction: "+stream.toString());//Stream Code + Stream Name select s.stream_id, s.stream_name from Stream s, Class c, Employees e where s.stream_id=c.stream_id and c.class_id=e.class_id and e.employee_id='ab'
		contentStream.newLine();
        contentStream.newLine();
        contentStream.showText("Foundation: ");//select distinct c.course_name from 
        contentStream.newLine();
        for(int i = 0; i < foundations.size(); i+=2){
            contentStream.showText(foundations.get(i)+" "+foundations.get(i+1));
            contentStream.newLine();
        }
        contentStream.newLine();
        contentStream.showText("Specialization: ");
        contentStream.newLine();
          for(int i = 0; i < specializations.size(); i+=2){
            contentStream.showText(specializations.get(i)+" "+specializations.get(i+1));
            contentStream.newLine();
        }
        contentStream.newLine();
        contentStream.showText("Process/Domain: ");
        contentStream.newLine();
          for(int i = 0; i < domains.size(); i+=2){
            contentStream.showText(domains.get(i)+" "+domains.get(i+1));
            contentStream.newLine();
        }
        contentStream.newLine();
        contentStream.showText("Overall Grade:  "+avgGrade+"   ");//select avg(e.scores) from Employees_take_Modules e, Modules m where m.module_id=e.module_id and e.employee_id=?
        contentStream.showText("Foundation Grade: "+fGrade+"   ");//select avg(e.scores) from Employees_take_Modules e, Modules m where m.category='Foundation' and m.module_id=e.module_id and e.employee_id=?
        contentStream.showText("Specialization Grade: "+sGrade+"   ");//select avg(e.scores) from Employees_take_Modules e, Modules m where m.category='Specialization' and m.module_id=e.module_id and e.employee_id=?
        contentStream.showText("Process/Domain Grade: "+dGrade+"   ");//select avg(e.scores) from Employees_take_Modules e, Modules m where m.category='ProcessDomain' and m.module_id=e.module_id and e.employee_id=?
		contentStream.endText();
		contentStream.close();
        System.out.println("PDF Finished Writing");
		document.save(filePath + empid+".pdf"); //There is an issue here to work out
        System.out.println("PDF Saved");
		document.close();
		System.out.println("PDF Closed");
                
        }
        else{
            System.out.println("Employee ID not found. PDF has not been generated.");
        }
        //BarChartEx bc = new BarChartEx();
	}
		
}