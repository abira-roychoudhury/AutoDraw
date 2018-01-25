package servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import modal.Constants;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import apiClass.DocumentTemplating;
import apiClass.GetRequestTest;
import apiClass.ImageEnhancement;
import apiClass.PdfToImage;
import apiClass.TemplateMatching;
import apiClass.TimestampLogging;
import apiClass.VisionAPICall;

/**
 * Servlet implementation class Processing
 */
@WebServlet("/Processing")
public class Processing extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public Processing() {
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
		//Initialization
		Date start = new Date(),end = new Date();
		TimestampLogging  timelogging = new TimestampLogging();	
		String fileName = "",fileType = "Drawing",filePath="";
		String imgName = Constants.imgFile+start.getTime()+Constants.dot+Constants.jpg;
		File imgFile = new File(imgName);
		String pdfName = Constants.imgFile+start.getTime()+Constants.dot+Constants.pdf;
		File pdfFile = new File(pdfName);
		
		
		//uploading the image file
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);  // Create a new file upload handler
		try{ 
			List fileItems = upload.parseRequest(request);  // Parse the request to get file items.
			Iterator fileItemsIterator = fileItems.iterator();  // Process the uploaded file items
			while ( fileItemsIterator.hasNext () ) 
			{
				FileItem fileItem = (FileItem)fileItemsIterator.next();
				if ( !fileItem.isFormField () )	
				{
					fileName = fileItem.getName();					 
					start = new Date();
					/*fileItem.write(pdfFile);      //writing a temporary pdf file
					PdfToImage.convert(pdfFile, imgFile);*/
					
					fileItem.write(imgFile);
					fileItem.delete();					
					filePath = imgFile.getAbsolutePath();
					end = new Date();			          
				}
			}
		}
		catch(Exception ex) 
		{
			System.out.println(ex);
			ex.printStackTrace();
		} 
		System.out.println("imgFile "+imgFile);
		
		
		//uploading image completed logging upload image
		timelogging.fileDesc(fileName, fileType);
		int timeDifference = timelogging.fileLog(Constants.uploadImage, start, end);
		request.setAttribute(Constants.uploadImage, timeDifference);
		
		
		//Calling ImageEnhancement and getting back a preprocessed base64 image string
		start = new Date();		          
		ImageEnhancement ie = new ImageEnhancement();
		String processedImgBase64= ie.convertToBase64(filePath);	
		end = new Date();
		timeDifference = timelogging.fileLog(Constants.base64conversion, start, end);
		request.setAttribute(Constants.base64conversion, timeDifference);
		
		//Get Location of bubble using template matching
		JSONObject bubbleData = TemplateMatching.getBubbleLocation(filePath,Constants.bubbleDetectionThreshold);
		int numberOfBubbles = bubbleData.getInt("numberOfBubbles");
		System.out.println("numberOfBubbles : "+numberOfBubbles);
		request.setAttribute("numberOfBubbles", numberOfBubbles);		
		request.setAttribute("coordinatesOfBubbles",bubbleData.get("coordinatesOfBubbles"));
		request.setAttribute("coordinatesOfValues", bubbleData.get("coordinatesOfValues"));

		
		//compressed image property 
		byte[] decoded = Base64.decodeBase64(processedImgBase64.getBytes());
        ImageIcon img = new ImageIcon(decoded);
        request.setAttribute(Constants.compressWidth, img.getIconWidth());
        request.setAttribute(Constants.compressHeight, img.getIconHeight());      
       
		
		//Creating Base64 of original Image
		FileInputStream fileInputStreamReader = new FileInputStream(imgFile);
		byte[] bytes = new byte[(int)imgFile.length()];
		fileInputStreamReader.read(bytes);
		String imgBase64 = new String(Base64.encodeBase64(bytes), "UTF-8");
		String imgBase64Jsp = "data:image/jpg;base64,"+imgBase64;
		request.setAttribute(Constants.imgBase64, imgBase64Jsp);
		
		//Closing ImageFile
		fileInputStreamReader.close();	
		
		String textDetectionType = Constants.VisionRequest.documentTextDetection;
		JSONObject visionResponse = new VisionAPICall().performOCR(imgBase64, textDetectionType);
		String descriptionStr = getDescription(visionResponse);
		LinkedHashMap<String, String> displayDocument = new DocumentTemplating().parseContent(descriptionStr);
		request.setAttribute("displayDocument", displayDocument);
		

		//dispatching request to jsp page
		RequestDispatcher dispatcher = request.getServletContext().getRequestDispatcher("/ViewImage.jsp");
		dispatcher.forward(request, response);		
	}
	
	private String getDescription(JSONObject visionResponse) {
		JSONArray textAnnotationArray = new JSONArray();
		try {
			JSONObject body = new JSONObject(visionResponse.get(Constants.VisionResponse.body));
			String bodystring=visionResponse.getString(Constants.VisionResponse.body);
			JSONObject bodyObject=new JSONObject(bodystring);
			JSONArray responsesArray=(JSONArray) bodyObject.getJSONArray(Constants.VisionResponse.responses);
			JSONObject textAnnotaionsDict=responsesArray.getJSONObject(0);
			textAnnotationArray=(JSONArray)textAnnotaionsDict.getJSONArray(Constants.VisionResponse.textAnnotations);			
			
		} catch (Exception e) {		
			e.printStackTrace();
			return "Could not scan any value. Please try some different selection";
		}
		try {			  			
			JSONObject firstObj=(JSONObject) textAnnotationArray.get(0);
			String descriptionStr=firstObj.getString(Constants.VisionResponse.description);
			descriptionStr = descriptionStr.replaceAll("[^\\x00-\\x7F]+", "");			
			return descriptionStr;
		}catch (JSONException e) {
			e.printStackTrace();
			return "Could not scan any value. Please try some different selection";
		}
	}
	

}
