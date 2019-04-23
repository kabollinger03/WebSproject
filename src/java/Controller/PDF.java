package Controller;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import javax.sql.DataSource;
import org.apache.pdfbox.pdmodel.PDPage;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;
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
    
    private final DefaultCategoryDataset dataset;

	private final static BaseFont font = BaseFont.Helvetica;
	private PDPage page = new PDPage();
    private PDFinfo pdfinfo;

	private String filePath = "C:/Users/syntel/Music/"; //use getfilepath in the email logic?
    private final static String imagePath = "C:\\Examples\\WebSproject\\web\\resources\\img\\pdf_banner.png"; //path to AS logo

    //java.io.File.separator
    public PDF(){this.dataset = new DefaultCategoryDataset();}
    
	public PDF(DataSource dataSource) throws ClassNotFoundException {
        this.dataset = new DefaultCategoryDataset();
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
    
	    public void generate(String empid) throws Exception{
        //init config
        njdbc = new NamedParameterJdbcTemplate(dataSource);
        PDFinfo info = new PDFinfo(dataSource.getConnection());
        info.setDataSource(dataSource);
        info.setNjdbc(njdbc);
        
        String name = info.getEmployeeName(empid);
        
        if(name.equals("")){
            System.out.println("Employee ID not found. PDF has not been generated.");
            return;
        }

        //define output vars
        String gradeNumbers = "Overall Grade: " + info.getAverageScore(empid) + "\n|  ";
        
        ArrayList<String> stream = info.getStreamIDName(empid);
       
        ArrayList<String[]> cats = new ArrayList<String[]>();
        cats.addAll(info.getCategoryNameID());
 
        //define a <hr /> --------------------------------
        char[] charArray = new char[1835];
        Arrays.fill(charArray, ' ');
        String hrHelp = new String(charArray);
        
        Paragraph hRule = new Paragraph();
        hRule.addMarkup("__" + hrHelp + " __", 1, font);
        //------------------------------------------------
                
        //create PDF
        float linspace = 10;
        float sectionBreak = 15;
        float textSize = 10;
        float leftMargin = 40;
        float rightMargin = 40;
        float topMargin = 20;
        float bottomMargin = 20;
        Document document = new Document(leftMargin, rightMargin, topMargin, bottomMargin);
        
        document.add(new ImageElement(imagePath), new VerticalLayoutHint(Alignment.Left, 0, 0,
		0, 0, true)); 
        document.add(new VerticalSpacer(90));
              
        
        document.add(new ImageElement("C:\\Examples\\WebSproject\\src\\java\\Controller\\performica.png"), new VerticalLayoutHint(Alignment.Left, 0, 0,
		0, 0, true)); 
        document.add(new VerticalSpacer(50)); //font: Vivaldi
   
        Paragraph par = new Paragraph();
        
        par = new Paragraph();
        par.addMarkup( "{color:#0066a1}*NAME*{color:#000000}: " + name + "            " +
                "{color:#0066a1}*EMPLOYEE ID*{color:#000000}: " + empid, 13, font);
        par.setAlignment(Alignment.Center);
        document.add(par);
        document.add(hRule);
        document.add(hRule);
        
        document.add(new VerticalSpacer(sectionBreak));
        
        par = new Paragraph();
        par.addMarkup("{color:#0066a1}__*My Trainings*__:", textSize + 1, font);
        document.add(par);
        document.add(new VerticalSpacer(0.25f*linspace));
        
        par = new Paragraph();
        par.addMarkup("{color:#000000}*Stream*: " + stream.get(0) + " - " + stream.get(1), textSize + 1, font);
        document.add(par);
        document.add(new VerticalSpacer(1.5f*linspace));
        
        document.add(hRule);
        document.add(new VerticalSpacer(linspace));
        for (String[] cat : cats){
            ArrayList<String[]> scores = new ArrayList<String[]>();
            scores.addAll(info.getModuleScoresByCategory(empid, cat[1]));
            
            if (scores.size() > 0){
                gradeNumbers += cat[0] + " Grade: " + info.getAverageScoreByCategory(empid, cat[1]) + "  |  ";
                
                par = new Paragraph();
                par.addMarkup("{color:#0066a1}*" + cat[0] + "*{color:#000000}:  ", textSize, font);
                
                for(int i = 0; i < scores.size() - 1; i++){
                    String[] score = scores.get(i);
                    par.addMarkup(score[0] + ",  ", textSize, font);         
                    String classScore = info.getClassModuleScores(empid, score[0]);
                    //set up for chart: add number, series label, bar label
                    dataset.addValue( Float.parseFloat(score[1]), name , score[0]);
                    dataset.addValue( Float.parseFloat(classScore), "Class Average" , score[0]);
                }
                String[] score = scores.get(scores.size() - 1);
                par.addMarkup(score[0], textSize, font);
                           
                String classScore = info.getClassModuleScores(empid, score[0]);
                //set up for chart: add number, series label, bar label
                dataset.addValue( Float.parseFloat(score[1]), name , score[0]);
                dataset.addValue( Float.parseFloat(classScore), "Class Average" , score[0]);
                
                document.add(par, VerticalLayoutHint.LEFT);
                document.add(hRule);
                document.add(new VerticalSpacer(linspace));
            }
        }
                
        par = new Paragraph();
        par.addMarkup("{color:#0066a1}__*My Performence*__:", textSize + 1, font);
        document.add(new VerticalSpacer(linspace));
        document.add(par);
        
        
        String chartFileName = "graph.png";
        makeChart(dataset, chartFileName);
        document.add(new ImageElement(chartFileName), new VerticalLayoutHint(Alignment.Left, 0, 0,
		0, 0, true)); 
        
        document.add(new VerticalSpacer(285));

        char[] Array = new char[1040];
        Arrays.fill(Array, ' ');
        String line = new String(Array);
        
        par = new Paragraph();
        par.addMarkup("__" + line + "__\n", 1, font);
        par.addMarkup("|Grade Range  |  > 90.00  |  80.00 - 89.99  |  70.00 - 79.99 | < 70  |\n", textSize, font);
        par.addMarkup("__" + line + "__\n", 1, font);
        par.addMarkup("|        Letter      |       A       |           B            |           C          |    F    |\n", textSize, font);
        par.addMarkup("__" + line + "__", 1, font);
        
        par.setAlignment(Alignment.Center);
        document.add(par);
        document.add(new VerticalSpacer(sectionBreak));

        par = new Paragraph();
        par.addMarkup(gradeNumbers, textSize, font);
        par.setAlignment(Alignment.Center);
        document.add(par);
        document.add(new VerticalSpacer(linspace));
        
        par = new Paragraph();
        par.addMarkup("{color:#000000}_*\"Learning is the lifelong process of transforming information and experience into knowledge, skills, behaviours and attitudes\"*_\n", 10, font);
        par.addMarkup(" - Jeff Cobb, _10 Ways to be a Better Learner_", textSize - 2, font);
        par.setAlignment(Alignment.Center);
        document.add(new VerticalSpacer(linspace));
        document.add(par);
        
        final OutputStream outputStream = new FileOutputStream(filePath + empid + ".pdf");
        document.save(outputStream);

	}	

    public static void makeChart(DefaultCategoryDataset dataset, String filename){
       JFreeChart barChart = ChartFactory.createBarChart(
            "",
            "", "",
            //"Module", "Assessment Score", 
            dataset,PlotOrientation.VERTICAL, 
            true, true, false);

        CategoryPlot plot = barChart.getCategoryPlot();
        plot.setBackgroundPaint(null);
        plot.setDomainGridlinePaint(Color.black);
        plot.setRangeGridlinePaint(Color.black);

        ValueAxis yAxis = plot.getRangeAxis();
        yAxis.setAutoRange(false);
        yAxis.setRange(0.00, 100.00);

        CategoryAxis axis = plot.getDomainAxis();     
        axis.setLowerMargin(.01);
        axis.setUpperMargin(.01);
        axis.setMaximumCategoryLabelLines(3);

        BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setBarPainter(new StandardBarPainter());
        renderer.setSeriesPaint(0, new Color(0, 102, 161)); //from website
        renderer.setSeriesPaint(1, new Color(106, 206, 242));  //from banner
        renderer.setShadowVisible(false);
        renderer.setDrawBarOutline(false);
        renderer.setItemMargin(0.0); //space between bars
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        renderer.setBaseItemLabelsVisible(true);
        renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE9, TextAnchor.BASELINE_LEFT));

        int width = 525;    /* Width of the image */
        int height = 275;   /* Height of the image */ 
        File BarChart = new File( filename ); 
        try {
            ChartUtilities.saveChartAsJPEG( BarChart , barChart , width , height );
        } catch (IOException ex) {
            System.out.println("Cannot save chart:\n" + ex.toString());
        }
   }
}
/* "Real Way" to underline Do not use - inserts new, completely blank, page before rest of content.
//Saved for possible later use and as a reminder to not Do The Thing
Stroke stroke = new Stroke();
RenderContext rc = new RenderContext(document, document.getPDDocument());
stroke.applyTo(rc.getContentStream());
rc.close();
*/
