package apiClass;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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

import modal.Constants;
import modal.PointXY;

	
public class TemplateMatching {
	
	static {
		//LOAD THE LIBRARY
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	
	public Mat loadOpenCvImage(final String filePath) {
		//LOAD IMAGE IN GRAYSCALE
	    Mat imgMat = Imgcodecs.imread(filePath, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
	    return imgMat;
	}
	
	
	public Mat loadOriginalImage(final String filePath) {		
		//LOAD IMAGE IN color
	    Mat imgMat = Imgcodecs.imread(filePath, Imgcodecs.CV_LOAD_IMAGE_COLOR);
	    return imgMat;
	}
	
	
	public JSONObject run(String inFile, String templateFile, String outFile, int match_method, double threshold) {
	        System.out.println("\nRunning Template Matching");
	        
	        Mat img = loadOriginalImage(inFile);
	        Mat templ = loadOriginalImage(templateFile);
	        
	        JSONObject bubbleData = new JSONObject();
	        JSONArray coordinatesOfBubbles = new JSONArray();
	        JSONArray coordinatesOfValues = new JSONArray();
	        List<PointXY> detectedPoints = new LinkedList<PointXY>();
	        
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
	        	//System.out.println("detectedPoints"+(Arrays.toString(detectedPoints.toArray()) ));
	        	//System.out.println("Size : "+detectedPoints.size());
	        	
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
		        
		        if(mmr.maxVal >= threshold){
		        	// / Show me what you got
		        	//Core.rectangle(img, matchLoc, new Point(matchLoc.x + templ.cols(), matchLoc.y + templ.rows()), new Scalar(0, 255, 0));
		        
		        	PointXY currentPoint = new PointXY(matchLoc.x, matchLoc.y);

		        	//System.out.println("currentpoint : "+currentPoint);
		        			
		        	//System.out.println("isUnique : "+ isUnique(currentPoint, detectedPoints) );
		        	
		        	if(isUnique(currentPoint, detectedPoints)) {
		        		System.out.println("Unique : "+currentPoint);
		        		
			        	JSONObject pointValue = new JSONObject();
			        	pointValue.put("x1", (matchLoc.x - Constants.bubbleMarginX));
			        	pointValue.put("y1", (matchLoc.y - Constants.bubbleMarginY));
			        	pointValue.put("x2", (matchLoc.x + templ.cols() + Constants.bubbleMarginWidth));
			        	pointValue.put("y2", (matchLoc.y + templ.rows() + Constants.bubbleMarginHeight));
			        	

			        	JSONObject pointBubble = new JSONObject();
			        	pointBubble.put("x1", (matchLoc.x - 10));
			        	pointBubble.put("y1", (matchLoc.y - 10));
			        	pointBubble.put("x2", (matchLoc.x + templ.cols() + 10));
			        	pointBubble.put("y2", (matchLoc.y + templ.rows() + 10));
			        	
			        	
		        		coordinatesOfBubbles.put(pointBubble);
		        		coordinatesOfValues.put(pointValue);
		        		detectedPoints.add(currentPoint);
		        		
			        	count++;
			        	
			        	//System.out.println(matchLoc.x +"  "+ matchLoc.y+"  "+(matchLoc.x + templ.cols())+"  "+(matchLoc.y + templ.rows()));
			        	Imgproc.rectangle(img, new Point(matchLoc.x - Constants.bubbleMarginX, matchLoc.y - Constants.bubbleMarginY),
			        							new Point(matchLoc.x + templ.cols() + Constants.bubbleMarginWidth, matchLoc.y + templ.rows() + Constants.bubbleMarginHeight),
			        							new Scalar(0, 0, 255),2);
			        }
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
	        bubbleData.put("coordinatesOfValues", coordinatesOfValues);
	        
	        return bubbleData;
	   }
	
	 
	public static JSONObject getBubbleLocation(String inFile, double threshold) {
		
		TemplateMatching tool = new TemplateMatching();
	
		
		String templateFile = "bubble_oval_08.png";
		
		/*URL resource = TemplateMatching.class.getResource("/bubble.png");
		try {
			File file = Paths.get(resource.toURI()).toFile();
			templateFile = file.getAbsolutePath();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}*/
		
		//String templateFile = TemplateMatching.class.getResource("/bubble.png").toString();
		
		String outFile = "output.png";
		int match_method = Imgproc.TM_CCOEFF_NORMED;
		
		JSONObject bubbleData = tool.run(inFile, templateFile, outFile, match_method, threshold);
		System.out.println(bubbleData);
		return bubbleData;
		
	}
	

	public static boolean isUnique(PointXY currentPoint, List<PointXY> detectedPoints) {
		
		Iterator<PointXY> iterator = detectedPoints.iterator();

		while(iterator.hasNext()) {
			
			PointXY pointi = iterator.next();

			//System.out.println("compare : "+currentPoint + " & "+pointi );
			
			if(currentPoint.equals(pointi)) 
				return false;		
		}
		
		return true;
	}
	
	public static void main(String[] args) {
		
		TemplateMatching tool = new TemplateMatching();
	
		String inFile = "D:\\GCP\\Porite\\New Set of ducuments\\updated\\Set-1\\Compaction.jpg";
		String templateFile = "D:\\GCP\\Porite\\New Set of ducuments\\updated\\bubble template\\bubble_oval_08.png";
		String outFile = "D:\\GCP\\Porite\\New Set of ducuments\\updated\\Set-1\\Compaction_output_oval_08.png";
		double threshold = 0.60;
		/*
		String inFile = args[0];
		String templateFile = args[1];
		String outFile = "output.png";
		double threshold = Double.parseDouble(args[2]);
		*/
		int match_method = Imgproc.TM_CCOEFF_NORMED;
			
		JSONObject bubbleData = tool.run(inFile, templateFile, outFile, match_method, threshold);
		System.out.println(bubbleData);
		
		//tool.getBubbleLocation(inFile);
		
	}

		
}

	

