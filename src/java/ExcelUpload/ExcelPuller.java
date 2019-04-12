package ExcelUpload;
import java.util.*;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;


import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
public class ExcelPuller {
	
            public ArrayList<Employee> generateEmployees(File FILE_PATH, String loc, String site, String stream) throws IOException{
      
            	Workbook workbook = null;                                          //used to open the excel file
            	ArrayList<String> columns = new ArrayList<>();                    // the column titles: "name", "email", "empID", "mod1Score"
            	ArrayList<Employee> emps = new ArrayList<>();                    // used to store all of the employees genereated from the excel
                String classID = generateClassID(loc, site, stream);            // stores the auto generate class id
            	
                try {

                    FileInputStream excelFile = new FileInputStream(FILE_PATH);          //Opens the stream for the excel file
                    workbook = new XSSFWorkbook(excelFile);                              //The actual Excel workbook
                   Sheet datatypeSheet = workbook.getSheetAt(0);                         //The current sheet of the excel doc (Should only be 1 sheet)
                    Iterator<Row> iterator = datatypeSheet.iterator();                   //Used to traverse the rows in the excel file
                   
                    Row currentRow = iterator.next();                                    //Grabs the first row
                    Iterator<Cell> cellIterator = currentRow.iterator();                 //Grabs connects the first cell
                    
                    
                    /** Iterates through the first row, and retrieves all the column.
                     * Columns should be in order (Sutdent_ID, Name, Email, first Module ID, second Module ID, ...)
                     * Saves the column names in an array list to use as a reference later. 
                     */
                    while (cellIterator.hasNext() ) {
                    	Cell currentCell = cellIterator.next();
                    	if (currentCell.getCellType() == CellType.STRING) {
                            
                    		columns.add(currentCell.getStringCellValue());
                    	
                    	} else if (currentCell.getCellType() == CellType.NUMERIC) {
                           	
                    		columns.add(currentCell.getStringCellValue());
                    	
                    	}
                    }
                    
                    
                    // go through every row after the first row
                    // create employee and module classes from these rows
                    
                    int counter;
                    while (iterator.hasNext()) {
                    	counter = 0;
                      currentRow = iterator.next(); // goes to the next row
                      cellIterator = currentRow.iterator(); // moves to the first cell in that row
                      
                     
                      Employee newEmp = new Employee(); //Initializes an employee instance
                        while (cellIterator.hasNext()) {                           
                        	
                        	Cell currentCell = cellIterator.next();  // empid, name, email, classId, m1Score, m2Score
             
                        	
                        	if (counter == 0) {
                        		newEmp.setEmployeeID(currentCell.getStringCellValue());//Gets and sets Emp ID
                        		counter++;
                        	} else if(counter == 1){
                        		newEmp.setEmployeeName(currentCell.getStringCellValue());//Gets and sets Emp Name
                        		counter++;
                        	} else if(counter == 2) {
                        		newEmp.setEmployeeEmail(currentCell.getStringCellValue());//Gets and sets Emp Email
                        		counter++;
                        	} else if (counter >= 3){
                        		newEmp.addScore(columns.get(counter), currentCell.getNumericCellValue()); //Adds scores to an employee
                        		counter++;
                        		
                        	}else {
                        		System.out.println("All data entered");
                        	}
                        	            
                        	
                        	
                        	
                        	}
                        newEmp.setClassID(classID);
                        emps.add(newEmp);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } 
                workbook.close();
               return emps; 
            }
            /** used to auto generate the class ID upon upload
             * @return 
             */
            public String generateClassID(String location, String site, String stream){
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MMddyyyyHHmm");  
                LocalDateTime now = LocalDateTime.now();
            
                String classID = "";
              
                StringBuilder s = new StringBuilder();
                
                
                //Generates the first three letters of the stream
                s.append(stream.toUpperCase().charAt(0));
                s.append(stream.toUpperCase().charAt(1));
                s.append(stream.toUpperCase().charAt(2));
                
                //Generates the middle number section based MMddyyyyHHmm
                s.append(dtf.format(now));
                
                //Generates a middle section of a stream based off the location
                s.append(location.toUpperCase().charAt(0));
                s.append(location.toUpperCase().charAt(1));
                s.append(location.toUpperCase().charAt(2));
                
                //Generates the last part of the stream based off of on or offsite
                s.append(site.toUpperCase().charAt(0));
                s.append(site.toUpperCase().charAt(1));
                s.append(site.toUpperCase().charAt(2));
                
                
                classID = s.toString();
                return classID;
            }
}

