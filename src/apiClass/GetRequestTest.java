package apiClass;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;


public class GetRequestTest {

	public static JSONObject pythonApiCall(String fileUrl) {
		JSONObject responseData = new JSONObject();
		try{
			
			URL url = new URL("http://localhost:9000/?filePath="+fileUrl);
			
			//setProxy();
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Length", "0");
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "application/json");
			
			BufferedReader bufferedReaderObject = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));			
			StringBuilder output = new StringBuilder();			
			
			String op;
			while ((op = bufferedReaderObject.readLine()) != null) {
				output.append(op);
			}

			//JSONParser parser = new JSONParser();
			responseData = new JSONObject(output.toString()); //parser.parse(output.toString());
			
			conn.disconnect();
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return responseData;
	}

	
	public static void setProxy(){
		try{
			System.setProperty("https.proxyHost", "ptproxy.persistent.co.in");
			System.setProperty("https.proxyPort", "8080");
		}catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
}
