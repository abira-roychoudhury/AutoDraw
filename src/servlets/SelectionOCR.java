package servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import modal.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import apiClass.VisionAPICall;

/**
 * Servlet implementation class SelectionOCR
 */
@WebServlet("/SelectionOCR")
public class SelectionOCR extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SelectionOCR() {
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
		String base64 = request.getParameter("croppedImageBase64");
		System.out.println(base64.substring(22));
		String textDetectionType = Constants.VisionRequest.documentTextDetection;
		JSONObject visionResponse = new VisionAPICall().performOCR(base64.substring(22), textDetectionType);		
		String description = getDescription(visionResponse);		
		response.getWriter().print(description);
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
