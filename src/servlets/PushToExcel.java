package servlets;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
 


import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONObject;


/**
 * Servlet implementation class PushToExcel
 */
@WebServlet("/PushToExcel")
public class PushToExcel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PushToExcel() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String partno = request.getParameter("partno");
		String partname = request.getParameter("partname");
		String processname = request.getParameter("processname");
		String customerpartno = request.getParameter("customerpartno");
		String releasedate = request.getParameter("releasedate");
		        
        int numberOfBubbles = Integer.parseInt(request.getParameter("numberOfBubbles"));
        String bubbleJsonString = request.getParameter("bubbleValueString");
        System.out.println("bubbleValue "+bubbleJsonString);
        
        
        System.out.println(partno+"  "+partname+"   ");
        JSONObject bubbleValue = new JSONObject(bubbleJsonString);
		/*try {
			bubbleValue = (JSONObject)new JSONParser().parse(bubbleJsonString);
		} catch (ParseException e) {
			bubbleValue = new JSONObject();
			e.printStackTrace();
		}*/
        
		
	
				
		String excelFilePath = "ExcelFiles/ControlDemo.xlsx"; //
        
        try {
            FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
	        XSSFSheet sheet = workbook.getSheetAt(0);
            
            Cell cellPartNo= sheet.getRow(2).getCell(0);
            String partNo = cellPartNo.getStringCellValue();
            cellPartNo.setCellValue(partNo+" "+partno);
            
            Cell cellPartName= sheet.getRow(3).getCell(0);
            String partName = cellPartName.getStringCellValue();
            cellPartName.setCellValue(partName+" "+partname);          
            
            Cell cellCustomerPartNo= sheet.getRow(2).getCell(9);
            String customerPartNo = cellCustomerPartNo.getStringCellValue();
            cellCustomerPartNo.setCellValue(customerPartNo+" "+customerpartno);
            
            Cell cellReleaseDate = sheet.getRow(1).getCell(9);
            String releaseDate = cellReleaseDate.getStringCellValue();
            cellReleaseDate.setCellValue(releaseDate+" "+releasedate);
            
            Cell cellRevDate = sheet.getRow(1).getCell(11);
            String revDate = cellRevDate.getStringCellValue();
            cellRevDate.setCellValue(revDate+" "+releasedate);
            
            Cell cellRevNo = sheet.getRow(1).getCell(12);
            String revNo = cellRevNo.getStringCellValue();
            cellRevNo.setCellValue(revNo+" 00");
                        
            
            int lastRow = sheet.getLastRowNum();
            
            Row processRow = sheet.createRow(++lastRow);
            Cell cellProcessName= processRow.createCell(1);
            cellProcessName.setCellValue(processname);  
            int count=0;
            
            if(processname.equals("COMPACTION"))
            {
            	Cell cellProcessDescription= processRow.createCell(2);
            	cellProcessDescription.setCellValue("Compacting \nPress: "+request.getParameter("Press"));  
            }
            
            
            @SuppressWarnings("unchecked")
            Iterator<String> bubbleIterator = bubbleValue.keys();
            
            while(bubbleIterator.hasNext()) {
            	String number = bubbleIterator.next();
            	String value = (String) bubbleValue.get(number);
            	
            	Row bubbleRow;
            	if(count==0)
            		{
            			bubbleRow = processRow;
            			count++;            		
            		}
            	else
            		bubbleRow = sheet.createRow(++lastRow);
            		
            	Cell cellBubbleNo = bubbleRow.createCell(3);
            	cellBubbleNo.setCellValue(number);
            	
            	if(value.contains("USL")) {
            		Cell cellBubbleProduct = bubbleRow.createCell(4);
            		cellBubbleProduct.setCellValue("Weight \nUSL MID LSL");
            		
            		Cell cellBubbleValue = bubbleRow.createCell(7);
                	cellBubbleValue.setCellValue(value.substring(value.indexOf("LSL ")+4));
            	}
            	else if(value.contains(":")) {
            		Cell cellBubbleProduct = bubbleRow.createCell(4);
            		cellBubbleProduct.setCellValue(value.substring(0,value.indexOf(":")));
            		
            		Cell cellBubbleValue = bubbleRow.createCell(7);
                	cellBubbleValue.setCellValue(value.substring(value.indexOf(":")+1));
            	}
            	else {
            		Cell cellBubbleProduct = bubbleRow.createCell(4);
            		cellBubbleProduct.setCellValue("Length/ Width");
            		            		
            		Cell cellBubbleValue = bubbleRow.createCell(7);
            		cellBubbleValue.setCellValue(value);
            	}
            	
            	Cell cellSize = bubbleRow.createCell(9);
            	cellSize.setCellValue("5pc");
            	
            	Cell cellFreq = bubbleRow.createCell(10);
            	cellFreq.setCellValue("First off");
            	
            	Cell cellControlMethod = bubbleRow.createCell(11);
            	cellControlMethod.setCellValue("First off approval");
            	
            	Cell cellReactionPlan = bubbleRow.createCell(12);
            	cellReactionPlan.setCellValue("Quarantine, Reset, follow QA/P05/WI01");
            	
            	
            	System.out.println(number + "  "+ value);
            }
            
           /* for(int i=0;i<numberOfBubbles;i++)
            {
            	int bubbleNo = 2;
            	String bubbleValue = request.getParameter("bubbleValue["+i+"]");
            	if(!bubbleValue.isEmpty())
            	{
            		 Cell cellBubbleNo = sheet.createRow(++lastRow).createCell(3);
                     cellBubbleNo.setCellValue(++bubbleNo);

                     Cell cellBubble = sheet.getRow(lastRow).createCell(4);
                     cellBubble.setCellValue("Length");

                     Cell cellBubbleValue = sheet.getRow(lastRow).createCell(7);
                     cellBubbleValue.setCellValue(bubbleValue);
            	}
            		
            }*/
                      
            
            inputStream.close();
            
            FileOutputStream outputStream = new FileOutputStream(excelFilePath);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
            
        }catch (IOException | EncryptedDocumentException ex) { 
            ex.printStackTrace();
        }		
		
		response.getWriter().print("Appended Data to Excel");
		
	}

}
