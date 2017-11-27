package apiClass;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

import modal.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

public class VisionAPICall {
	
	/* DESCRIPTION : Makes the call to Vision API and returns the response
	 * INPUT : String base64String of the image on which OCR is to be performed
	 * OUTPUT : JSONObject of the response from Vision API
	 * */
	public JSONObject performOCR(String base64String, String type){
		
		String apiUrl = Constants.VisionRequest.visionApiUrl;
		String apiKey = Constants.VisionRequest.visionApiKey;
		JSONObject result = new JSONObject();
		String stringBody = "";
		
		try{
			URL url = new URL(apiUrl+apiKey);
			
			System.setProperty("jdk.http.auth.tunneling.disabledSchemes",""); 
			try{
				System.setProperty("https.proxyHost", Constants.proxyHost);
				System.setProperty("https.proxyPort", Constants.proxyPort);
			}catch(Exception ex){
				System.out.println(ex.getMessage());
			}
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod(Constants.VisionRequest.requestMethod);
			conn.setRequestProperty(Constants.VisionRequest.contentType, Constants.VisionRequest.contentTypeValue);
			
			
			//strBody="{ \"requests\":[ { \"image\":{ \"content\":\""+base64String+"\" }, \"features\":[ { \"type\":\"TEXT_DETECTION\", \"maxResults\":1000 } ] } ] }";
			stringBody = getBody(base64String,type).toString();
			//System.out.println(stringBody);
			
			
			OutputStream outputStream = conn.getOutputStream();
			outputStream.write(stringBody.getBytes());
			outputStream.flush();
			
			
			BufferedReader bufferedReaderObject = new BufferedReader(new InputStreamReader((conn.getInputStream())));			
			StringBuilder output = new StringBuilder();			
			String op;
			while ((op = bufferedReaderObject.readLine()) != null) {
				output.append(op);
			}

			result.put(Constants.VisionResponse.body, output.toString());			
			conn.disconnect();
		}
		catch(Exception e){
			System.err.print("inside error in vision catch"+e);
		}
		return result;
	}
	
	/* DESCRIPTION : Creates the JSON body that needs to be sent to Vision API as Request
	 * INPUT : String base64String of the image on which OCR is to be performed
	 * OUTPUT : JSONObject of Request body
	 * */
	public JSONObject getBody(String base64String, String type){
        JSONObject Body = new JSONObject();
        JSONArray requests = new JSONArray();        
        JSONObject requestBody = new JSONObject();
        
        JSONObject content = new JSONObject();
        content.put(Constants.VisionRequest.content, base64String);
        requestBody.put(Constants.VisionRequest.image, content);
        
        
        JSONArray features = new JSONArray();
        features.put(new JSONObject().put(Constants.VisionRequest.type, type).put(Constants.VisionRequest.maxResults, 1000));
        requestBody.put(Constants.VisionRequest.features,features);
 
        requests.put(requestBody);
        
        Body.put(Constants.VisionRequest.requests, requests);
 
        return Body;
 }
	
	

}
