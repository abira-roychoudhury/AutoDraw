package apiClass;

import org.json.JSONArray;
import org.json.JSONObject;
import org.opencv.core.Core;
import org.opencv.core.Core.MinMaxLocResult;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

	
public class TemplateMatching {
	
	public Mat loadOpenCvImage(final String filePath) {
		//LOAD THE LIBRARY
		
		//LOAD IMAGE IN GRAYSCALE
	    Mat imgMat = Imgcodecs.imread(filePath, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
	    return imgMat;
	}
	
	
	public Mat loadOriginalImage(final String filePath) {		
		//LOAD IMAGE IN color
	    Mat imgMat = Imgcodecs.imread(filePath, Imgcodecs.CV_LOAD_IMAGE_COLOR);
	    return imgMat;
	}
	
	
	 public JSONObject run(String inFile, String templateFile, String outFile, int match_method) {
	        System.out.println("\nRunning Template Matching");
	        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
			
	        Mat img = loadOriginalImage(inFile);
	        Mat templ = loadOriginalImage(templateFile);
	        
	        JSONObject bubbleData = new JSONObject();
	        JSONArray coordinatesOfBubbles = new JSONArray();
	        int count = 0;
	        

	        // / Create the result matrix
	        int result_cols = img.cols() - templ.cols() + 1;
	        int result_rows = img.rows() - templ.rows() + 1;
	        Mat result = new Mat(result_rows, result_cols, CvType.CV_32FC1);
	        
	        // / Do the Matching and Normalize
	        Imgproc.matchTemplate(img, templ, result, match_method);
	        
	        //Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1, new Mat());
	
	        //Imgcodecs.imwrite("D:/GCP/Porite/out2.png", result);
			
	        while(true)
	        {
	        	// / Localizing the best match with minMaxLoc
		        MinMaxLocResult mmr = Core.minMaxLoc(result);
	
		        Point matchLoc;
		        if (match_method == Imgproc.TM_SQDIFF || match_method == Imgproc.TM_SQDIFF_NORMED) {
		            matchLoc = mmr.minLoc;
		            System.out.println(mmr.minVal);
		        } else {
		            matchLoc = mmr.maxLoc;
		            System.out.println(mmr.maxVal);
		        }
		        
		        if(mmr.maxVal >=0.85){
		        	// / Show me what you got
		        	//Core.rectangle(img, matchLoc, new Point(matchLoc.x + templ.cols(), matchLoc.y + templ.rows()), new Scalar(0, 255, 0));
		        	
		        	
		        	JSONObject point = new JSONObject();
		        	point.put("x1", (matchLoc.x - 300));
		        	point.put("y1", (matchLoc.y - 50));
		        	point.put("x2", (matchLoc.x + templ.cols() + 300));
		        	point.put("y2", (matchLoc.y + templ.rows() + 50));
		        	
		        	coordinatesOfBubbles.put(point);	
		        	count++;
		        	
		        	System.out.println(matchLoc.x +"  "+ matchLoc.y+"  "+(matchLoc.x + templ.cols())+"  "+(matchLoc.y + templ.rows()));
		        	Imgproc.rectangle(img, new Point(matchLoc.x - 75, matchLoc.y - 25),
		        							new Point(matchLoc.x + templ.cols() + 75, matchLoc.y + templ.rows() + 25),
		        							new Scalar(0, 0, 255),2);
		        	Imgproc.rectangle(result, matchLoc, new Point(matchLoc.x + templ.cols(),matchLoc.y + templ.rows()), new Scalar(0,255,0),2);
		        	
		        }
		        else
		        	break;
		    }
	        // Save the visualized detection.
	       // System.out.println("Writing " + outFile);
	        Imgcodecs.imwrite(outFile, img);
	        
	        bubbleData.put("numberOfBubbles", count);
	        bubbleData.put("coordinatesOfBubbles", coordinatesOfBubbles);
	        
	        return bubbleData;
	   }
	
	 
	public static JSONObject getBubbleLocation(String inFile) {
		
		TemplateMatching tool = new TemplateMatching();
	
		//String inFile = "D:/autocad draw/code/bubbleFinding/Production Drawing_Example_pdf-001.jpg";
		String templateFile = "D:/autocad draw/code/bubbleFinding/bubble2.png";
		String outFile = "D:/autocad draw/code/bubbleFinding/output.png";
		int match_method = Imgproc.TM_CCOEFF_NORMED;
		
		JSONObject bubbleData = tool.run(inFile, templateFile, outFile, match_method);
		System.out.println(bubbleData);
		return bubbleData;
		
	}
		
}

	

