package servlets;

import java.io.IOException;

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

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String partno = request.getParameter("partno");
		String partname = request.getParameter("partname");
		String processname = request.getParameter("processname");
		String usl = request.getParameter("usl");
		String mid = request.getParameter("mid");
		String lsl = request.getParameter("lsl");
		String density = request.getParameter("density");
        String burr = request.getParameter("burr");
        
        int numberOfBubbles = Integer.parseInt(request.getParameter("numberOfBubbles"));
        //String bubbleValue = request.getParameter("bubbleValue");
		
		System.out.println(usl + " value "+mid);
				
		String excelFilePath = "C:/eclipse-jee-luna-R-win32-x86_64/eclipse/ExcelFiles/ControlDemo.xlsx"; //
        
        try {
            FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
	        XSSFSheet sheet = workbook.getSheetAt(0);
            
            Cell cellPartNo= sheet.getRow(2).getCell(0);
            String partNo = cellPartNo.getStringCellValue();
            System.out.println("partno text "+partNo);
            cellPartNo.setCellValue(partNo+" "+partno);
            
           Cell cellPartName= sheet.getRow(3).getCell(0);
            String partName = cellPartName.getStringCellValue();
            cellPartName.setCellValue(partName+" "+partname);
            
            int lastRow = sheet.getLastRowNum();
            
            Row compactionRow = sheet.createRow(++lastRow);
            Cell cellProcessName= compactionRow.createCell(1);
            cellProcessName.setCellValue(processname);   
            
            Cell cellWeightNo = compactionRow.createCell(3);
            cellWeightNo.setCellValue("1");            
            Cell cellWeight = compactionRow.createCell(4);
            cellWeight.setCellValue("Weight");            
            Cell cellWeightUsl = compactionRow.createCell(7);
            cellWeightUsl.setCellValue("USL : "+usl);            
            Cell cellWeightMid = sheet.createRow(++lastRow).createCell(7);
            cellWeightMid.setCellValue("MID : "+mid);            
            Cell cellWeightLsl = sheet.createRow(++lastRow).createCell(7);
            cellWeightLsl.setCellValue("LSL : "+lsl);            
            Cell cellDensity = sheet.createRow(++lastRow).createCell(4);
            cellDensity.setCellValue("Density");          
            Cell cellDensityValue = sheet.getRow(lastRow).createCell(7);
            cellDensityValue.setCellValue(density);

            
            Cell cellBurrNo = sheet.createRow(++lastRow).createCell(3);
            cellBurrNo.setCellValue("2");
            Cell cellBurr = sheet.getRow(lastRow).createCell(4);
            cellBurr.setCellValue("Compaction burr control");
            Cell cellBurrValue = sheet.getRow(lastRow).createCell(7);
            cellBurrValue.setCellValue(burr);
            
            
            for(int i=0;i<numberOfBubbles;i++)
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
            		
            }
            
           
            
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
