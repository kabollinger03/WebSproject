package Controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

// creates template files for a specific stream
// it doesn't connect to the database, the data to be written is passed in
public class ExcelWriter {

    private String relativePath; // where the templates are saved
    private final String fileExtension;
    private final String fileTag; // appended to the stream name to complete the file name
    private ArrayList<String> columnTitles;
    
    /*
        constructor
        parameters - @relativePath: where the created files should be stored
                                it's relative to the project's root directory
                                (Ex.) "./downloadable/"
     */
    public ExcelWriter(String relativePath) {
        this.relativePath = relativePath;
        this.fileExtension = ".xlsx";
        this.fileTag = "-Template";
        
        this.columnTitles = new ArrayList<>();
        // all template files start with these columns
        columnTitles.add("Employee ID"); 
        columnTitles.add("Name");
        columnTitles.add("Email");   
    }
    
    public void createExcelTemplateFile(String streamName, ArrayList<String> moduleNames) {

        XSSFWorkbook workbook = new XSSFWorkbook(); // blank workbook

        // blank spreadsheet
        XSSFSheet spreadsheet = workbook.createSheet("Performance Reports Template");

        int firstRowIndex = 0; // only writing to the first row
        XSSFRow row = spreadsheet.createRow(firstRowIndex); // title row, indicates the data to be entered

        int columnIndex = 0;

        columnTitles.addAll(moduleNames); // append "[module] score" to the column titles
        
        // go through titles, write their values in consecutive cells
        for(String colTitle: columnTitles) {
                Cell cell = row.createCell(columnIndex++);
                cell.setCellValue(colTitle);
        }
        
        try {
            FileOutputStream out = createFileOutputStream(streamName);
            workbook.write(out);
            out.close();
            System.out.println("Template spreadsheet was written successfully");
        } catch (IOException e) {
            System.err.println("CreateExcelTemplateForStream: there was an issue creating the template file");
        }  
    }
    
    private FileOutputStream createFileOutputStream(String filename) throws FileNotFoundException {
        
        String fullPathFilename = createFullPathFilename(filename);
        
        return new FileOutputStream(new File(fullPathFilename));
    }
    
    private String createFullPathFilename(String filename) {
        StringBuilder fullPathBuilder = new StringBuilder(relativePath); // directory path
        fullPathBuilder.append(filename); // followed by the file name
        fullPathBuilder.append(fileTag); // and the tag "-template"
        fullPathBuilder.append(fileExtension); // and the extension ".xlsx"
        
        return fullPathBuilder.toString();
    }
    
    public static void main(String[] args) {
        
        ExcelWriter excelWriter = new ExcelWriter("./downloadable/");
        ArrayList<String> staticModuleNames = new ArrayList<>(2);
        staticModuleNames.add("web tech");
        staticModuleNames.add("bootstrap");
        
        excelWriter.createExcelTemplateFile("DB343", staticModuleNames);
    }
}