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
				
		//String excelFilePath = "C:/eclipse-jee-luna-R-win32-x86_64/eclipse/ControlPlanDemo.xlsx";
		String excelFilePath = "C:/eclipse-jee-luna-R-win32-x86_64/eclipse/ControlDemo.xlsx";
        
        try {
            FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
	        XSSFSheet sheet = workbook.getSheetAt(0);
            
	        //Cell cellPartNo= sheet.getRow(2).getCell(0);
            Cell cellPartNo= sheet.getRow(2).getCell(0);
            String partNo = cellPartNo.getStringCellValue();
            System.out.println("partno text "+partNo);
            cellPartNo.setCellValue(partNo+" "+partno);
            
            //Cell cellPartName= sheet.getRow(3).getCell(0);
           Cell cellPartName= sheet.getRow(3).getCell(0);
            String partName = cellPartName.getStringCellValue();
            cellPartName.setCellValue(partName+" "+partname);
            
           // Cell cellProcessName= sheet.getRow(9).createCell(1);
            Cell cellProcessName= sheet.createRow(9).createCell(1);
            cellProcessName.setCellValue(processname);    
            
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
