package apiClass;

import modal.Constants;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

public class ImageEnhancement {
	
	/* DESCRIPTION : Load the image file in Mat object
	 * INPUT : Image file path in String 
	 * OUTPUT : Mat Object
	 * */		
	public Mat loadOpenCvImage(final String filePath) {
		//LOAD THE LIBRARY
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		//LOAD IMAGE IN GRAYSCALE
	    Mat imgMat = Imgcodecs.imread(filePath, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
	    return imgMat;
	}
	
	/* DESCRIPTION : Load the image file in Mat object
	 * INPUT : Image file path in String 
	 * OUTPUT : Mat Object
	 * */	
	public Mat loadOriginalImage(final String filePath) {		
		//LOAD IMAGE IN color
	    Mat imgMat = Imgcodecs.imread(filePath, Imgcodecs.CV_LOAD_IMAGE_COLOR);
	    return imgMat;
	} 
	
	
	/* DESCRIPTION : Performs image preprocessing by applying thresholding based on Document type
	 * INPUT : Mat imgMat, String documentType
	 * OUTPUT : Mat Object
	 * */
	public Mat preprocess(Mat imgMat, String documentType){
		//THRESHOLDING PARAMETERS
	    final double min = 0, max = 255.0, threshold = 127.0;
	    //CREATE AN EMPTY IMAGE
	    Mat imgNew = imgMat;
	    if(documentType.equalsIgnoreCase(Constants.AadharCardPage1.aadharCard)){
	    	//PERFORM GLOBAL THRESHOLDING
		    Imgproc.threshold(imgMat, imgMat, threshold, max, Imgproc.THRESH_BINARY);
		    //PERFORM ADAPTIVE THRESHOLDING USING MEAN FILTER
		    Imgproc.adaptiveThreshold(imgMat, imgNew, max, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 15, 8);
		    
	    }
	    //PERFORM ADAPTIVE THRESHOLDING USING MEAN FILTER
	    //Imgproc.adaptiveThreshold(imgMat, imgNew, max, Imgproc.ADAPTIVE_THRESH_MEAN_C, Imgproc.THRESH_BINARY, 15, 8);
	    return imgNew;
	}
	
	
	/* DESCRIPTION : Saves image in mat form to File object
	 * INPUT : Mat 
	 * OUTPUT : File object
	 * */
	public File saveImage(Mat img){
		Date d = new Date();
		File processedFile = new File(Constants.imgFile+d.getTime()+Constants.dot+Constants.jpg);
        Imgcodecs.imwrite(processedFile.getAbsolutePath(), img);
		//System.out.println("New image saved at location : "+processedFile.getAbsolutePath());
		return processedFile;
	}
	
	
	/* DESCRIPTION : Wrapper function which loads image, perform compression, saves to file and converts to Base64 String
	 * INPUT : String filePathm String documentType
	 * OUTPUT : String ImageInBase64 
	 * */
	public String imagePreprocessing(String filepath, String documentType){
		Mat imgMat = loadOpenCvImage(filepath);
		//Mat imgMat2 = preprocess(imgMat, documentType);
		
		if(!(documentType.equalsIgnoreCase(Constants.VoterCard.voterCard) ||
				documentType.equalsIgnoreCase(Constants.ITReturn.iTReturn)))
			imgMat = imageCompress(imgMat);
		
		File img = saveImage(imgMat);
		String imgBase64 = convertToBase64(img.getAbsolutePath());
		
		img.delete();
		return imgBase64;		
	}
	
	/* DESCRIPTION : Converts image from file to base64 String
	 * INPUT : String filepath
	 * OUTPUT : String ImageInBase64
	 * */
	public String convertToBase64(String filePath){
		String imageData = "";
		try{			
			Path path = Paths.get(filePath);
			byte[] data = Files.readAllBytes(path);
			//Image
			imageData = Base64.encodeBase64URLSafeString(data);
		}catch(Exception e){
			System.err.println(e);
		}		
		return imageData;
	}
	
	/* DESCRIPTION : Performs image compression
	 * INPUT : Mat originalimage
	 * OUTPUT : Mat compressedImage
	 * */
	public Mat imageCompress(Mat img){
		long size = img.step1(0) * img.rows();
		//long size = img.total() * img.elemSize();
		
		while(size > Constants.DisplayImageParameters.minimumImageSize)
		{
			Imgproc.resize(img, img, new Size(img.width()/1.2, img.height()/1.2));
			size = img.step1(0) * img.rows();
			
			System.out.println("size : "+size);
		}				
		return img;
	} 
	
	/* DESCRIPTION :Resize the image
	 * INPUT : Mat originalimage
	 * OUTPUT : Mat compressedImage
	 * */
	public File imageResize(File imageFile){
		
		Mat imageMat = loadOriginalImage(imageFile.getAbsolutePath());
		Date d = new Date();
		
		if(imageMat.width() > Constants.DisplayImageParameters.maximumImageWidth){
			//calculate new size
			Size newSize = new Size(Constants.DisplayImageParameters.scaledImageWidth , (Constants.DisplayImageParameters.scaledImageWidth/imageMat.width()) * imageMat.height() );
			
			//resize the Mat to new size
			Imgproc.resize(imageMat, imageMat, newSize);
		}
		
		File processedFile = new File(Constants.imgFile+d.getTime()+Constants.dot+Constants.jpg);
        Imgcodecs.imwrite(processedFile.getAbsolutePath(), imageMat);
        
        imageFile.deleteOnExit();
        
        System.out.println("imageFile deleted : "+imageFile.delete());
        
        return processedFile;		
		
			
	}
	


}

