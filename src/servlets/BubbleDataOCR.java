package servlets;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import apiClass.VisionAPICall;
import modal.Constants;

/**
 * Servlet implementation class BubbleDataOCR
 */
@WebServlet("/BubbleDataOCR")
public class BubbleDataOCR extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BubbleDataOCR() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String imageBase64 = request.getParameter("croppedImageBase64");
		String bubbleBase64 = request.getParameter("croppedBubbleBase64");
		
		JSONObject bubbleData = new JSONObject();
		
		JSONObject visionBubbleResponse = new VisionAPICall().performOCR(bubbleBase64.substring(22), Constants.VisionRequest.documentTextDetection);		
		String bubbleNumber = getDescription(visionBubbleResponse);
		System.out.println("bubble number "+bubbleNumber);
		if(bubbleNumber.contains("Z0"))
			bubbleNumber = "07";
		if(bubbleNumber.contains("90"))
			bubbleNumber = "06";
		if(bubbleNumber.isEmpty())
			bubbleNumber = "11";
		bubbleData.put("bubbleNumber", bubbleNumber);
		
		JSONObject visionImageResponse = new VisionAPICall().performOCR(imageBase64.substring(22), Constants.VisionRequest.textDetection);		
		String bubbleValue = getDescription(visionImageResponse);
		bubbleValue = parseBubbleValue(bubbleValue);
		System.out.println("bubble value "+bubbleValue);
		bubbleData.put("bubbleValue", bubbleValue);
				
		response.getWriter().print(bubbleData);
	}
	
	private String parseBubbleValue(String descriptionStr) {
		String extract = "";
		int numberOfDecimalValues = getNumberOfDecimalValue(descriptionStr);
		System.out.println("number of decimal values "+numberOfDecimalValues);
		if(numberOfDecimalValues==2)
			extract = getDecimalValueRange(descriptionStr);
		else {
			String splitDesc[] = descriptionStr.split("\\n");
			for(int index=0;index<splitDesc.length;index++){
				extract = extract + " "+splitDesc[index];
			}
		}
		return extract.trim();
	}
	
	
	private String getDecimalValueRange(String descriptionStr) {
		String extract = "";
		int number=0;
		String splitDesc[] = descriptionStr.split("\\n");
		for(int index=0;index<splitDesc.length;index++){
			String value = getDecimalValue(splitDesc[index]);
			if(!value.isEmpty()){
				splitDesc[index] = splitDesc[index].replace(value, "").trim();
				if(number==0)
					extract = value + " - ";
				else
					extract = extract + value;
				number++;					
			}
		}			return extract;
	}

	private String getDecimalValue(String content) {
		String decimalValue = "";
		Pattern pattern = Pattern.compile("[0-9]{1,2}.[0-9]{1,3}");
		Matcher matcher = pattern.matcher(content);
		if (matcher.find())
		{
			try{
				decimalValue = matcher.group(0);
			}catch(Exception e){}
		}
		
		return decimalValue;
	}
	
	private int getNumberOfDecimalValue(String content) {
		Pattern pattern = Pattern.compile("[0-9]{1,2}.[0-9]{1,3}");
		Matcher matcher = pattern.matcher(content);
		int count = 0;
		while(matcher.find())
			count++;
		return count;
	}

	private static String getDescription(JSONObject visionResponse) {
		JSONArray textAnnotationArray = new JSONArray();
		try {
			JSONObject body = new JSONObject(visionResponse.get(Constants.VisionResponse.body));
			String bodystring=visionResponse.getString(Constants.VisionResponse.body);
			JSONObject bodyObject=new JSONObject(bodystring);
			JSONArray responsesArray=(JSONArray) bodyObject.getJSONArray(Constants.VisionResponse.responses);
			JSONObject textAnnotaionsDict=responsesArray.getJSONObject(0);
			textAnnotationArray=(JSONArray)textAnnotaionsDict.getJSONArray(Constants.VisionResponse.textAnnotations);			
			
			JSONObject firstObj=(JSONObject) textAnnotationArray.get(0);
			String descriptionStr=firstObj.getString(Constants.VisionResponse.description);
			descriptionStr = descriptionStr.replaceAll("[^\\x00-\\x7F]+", "");			
			return descriptionStr;
		}catch (JSONException e) {
			e.printStackTrace();
			return "";
		}
	}

}
